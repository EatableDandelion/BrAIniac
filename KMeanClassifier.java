import java.util.*;

public class KMeanClassifier{
	private Vector[] centroids;
	private int nbClusters;
	private Vector average;
	private Vector stddev;
	
	public KMeanClassifier(int nbClusters){
		this.centroids = new Vector[nbClusters];
		this.nbClusters = nbClusters;
	}
	
	public int getGroup(Vector v){
		Vector standardizedV = new Vector(v.getSize());
		for(int i=0; i<v.getSize(); i++){
			standardizedV.set(i, (v.get(i)-average.get(i))/stddev.get(i));
		}
		return getCluster(standardizedV);
	}
	
	private int getCluster(Vector v){//Takes in a standardized vector, only internal method.
		return getCluster(v, this.centroids);
	}
	
	private int getCluster(Vector v, Vector[] centroids){//return the index of the cluster
		int kmin = 0;
		float minDistance = getDistance(centroids[0], v);
		for(int k = 1; k<nbClusters; k++){
			float d = getDistance(centroids[k], v);
			if(d<minDistance){
				kmin = k;
				minDistance = d;
			}
		}
		return kmin;
	}

	public void train(Matrix trainingSet0, int nbIterations, int nbRepeats){//5 iterations and 40 repeats works well
		//inputs standardization
		average = new Vector(trainingSet0.getNbColumns());
		stddev = new Vector(trainingSet0.getNbColumns());
		Matrix trainingSet = new Matrix(trainingSet0.getNbRows(), trainingSet0.getNbColumns());
		for(int j = 0; j<trainingSet.getNbColumns(); j++){
			Vector column = trainingSet0.getColumn(j);
			float avge = column.getAverage();
			float sd = (float)Math.sqrt(column.getVariance(avge));

			//Keep the average and stddev in memory for future queries.
			average.set(j, avge);
			stddev.set(j, sd);
			
			//Rescale the column
			column.standardize(avge, sd);
			trainingSet.setColumn(j, column);
		}
		
		
		float bestScore=0f;
		for(int j = 0; j<nbRepeats; j++){
			Vector[] newCentroids = new Vector[nbClusters];
			init(trainingSet, newCentroids);
			
			for(int i = 0; i<nbIterations; i++){
				Vector[] tempCentroids = new Vector[nbClusters];
				float[] count = new float[nbClusters];
				for(int v = 0; v<trainingSet.getNbRows(); v++){
					Vector row = trainingSet.getRow(v);
					int kmin = getCluster(row, newCentroids);
					count[kmin]++;
					if(tempCentroids[kmin]==null){
						tempCentroids[kmin]=row;
					}else{
						tempCentroids[kmin] = tempCentroids[kmin].addVector(row);
					}
					
				}
				for(int k =0; k<nbClusters; k++){
					newCentroids[k] = tempCentroids[k].multScalar(1f/count[k]);
				}
				
			}
			
			//If this set is better, keep it, otherwise try again
			float currentScore = getScore(trainingSet, newCentroids);
			if(currentScore<bestScore || j==0){
				bestScore = currentScore;
				this.centroids = newCentroids;
				System.out.println(j+" "+bestScore);
				for(int k=0; k<nbClusters; k++){
					System.out.println(this.centroids[k]);
				}
				System.out.println("");
			}
		}
	}
	
	private void init(Matrix trainingSet, Vector[] centroids){//k-mean++ initialization
		//init first centroid at random
		int index = (int)(Math.random()*trainingSet.getNbRows());
		centroids[0] = trainingSet.getRow(index);
		List<Integer> indices = new ArrayList<Integer>();
		indices.add(index);

		for(int k=1; k<nbClusters; k++){
			int v = (int)(Math.random()*trainingSet.getNbRows());
			boolean centerFound = false;
			while(!centerFound){
				if(v==trainingSet.getNbRows()){
					v = 0;
				}
				if(indices.contains(v)){
					v++;
					continue;
				}
				
				//Get min distance from the point to the previously found centroids.
				Vector vec = trainingSet.getRow(v);
				float minD = getDistance(vec, centroids[0]);
				for(int l=1; l<k; l++){
					minD = Math.min(minD, getDistance(vec, centroids[l]));
				}
				
				//Choose this centroid with the probability = distance square (input needs to be standardized for this reason)
				if(Math.random()<minD){
					centroids[k] = vec;
					indices.add(v);
					centerFound = true;
				}
				v++;
			}
		}
	}
	
	private float getDistance(Vector v1, Vector v2){//Note: returns distance square instead of distance itself.
		Vector diff = v1.subVector(v2);
		return diff.dot(diff);
	}
	
	private float getScore(Matrix trainingSet, Vector[] centroids){//Get the total variance of the set, as an indication on the level of clustering: the lowest, the better.
		float res = 0f;
		for(int v = 0; v<trainingSet.getNbRows(); v++){
			Vector row = trainingSet.getRow(v);
			res+=getDistance(row, centroids[getCluster(row, centroids)]);
		}
		return res/((float)trainingSet.getNbRows());
	}
}


public class Vector{
	private float[] data;
	
	public Vector(float[] data){
		this.data = data;
	}
	
	public Vector(int size){
		data = new float[size];
		for(int i = 0; i<size; i++){
			data[i] = 0f;
		}
	}
	
	public Vector(Vector v){
		data = v.data;
	}
	
	public Vector multVector(Vector v){
		float[] res = new float[data.length];
		for(int i = 0; i<data.length; i++){
			res[i] = data[i]*v.data[i];
		}
		return new Vector(res);
	}
	
	public Vector subVector(Vector v){
		float[] res = new float[data.length];
		for(int i = 0; i<data.length; i++){
			res[i] = data[i]-v.data[i];
		}
		return new Vector(res);
	}
	
	public Vector multMatrix(Matrix m){
		float[] res = new float[m.getNbColumns()];
		for(int j = 0; j<m.getNbColumns(); j++){
			float sum = 0f;
			for(int i = 0; i<data.length; i++){
				sum+=data[i]*m.get(i,j);
			}
			res[j] = sum;
		}
		return new Vector(res);
	}
	
	public Vector addVector(Vector v){
		float[] res = new float[data.length];
		for(int i = 0; i<data.length; i++){
			res[i] = data[i]+v.data[i];
		}
		return new Vector(res);
	}
	
	public Matrix outerProduct(Vector v){
		Matrix res = new Matrix(getSize(), v.getSize());
		for(int i = 0; i<getSize(); i++){
			for(int j = 0; j<v.getSize(); j++){
				res.set(i, j, get(i)*v.get(j));
			}
		}
		return res;
	}
	
	public Vector multScalar(float f){
		float[] res = new float[data.length];
		for(int i = 0; i<data.length; i++){
			res[i] = data[i]*f;
		}
		return new Vector(res);
	}
	
	public Vector subScalar(float f){
		float[] res = new float[data.length];
		for(int i = 0; i<data.length; i++){
			res[i] = data[i] - f;
		}
		return new Vector(res);
	}
	
	public float sum(){
		float res = 0f;
		for(int i = 0; i<data.length; i++){
			res+= data[i];
		}
		return res;
	}
	
	public float dot(Vector v){
		float res = 0f;
		for(int i = 0; i<data.length; i++){
			res+= data[i]*v.get(i);
		}
		return res;
	}
	
	public void normalize(){
		float norm = 0f;
		for(int i = 0; i<data.length; i++){
			norm+=data[i]*data[i];
		}
		norm = (float)Math.sqrt(norm);
		for(int i = 0; i<data.length; i++){
			data[i]/=norm;
		}
	}
	
	public void rescale(float minBound, float maxBound){
		float range = maxBound-minBound;
		for(int i = 0; i<data.length; i++){
			data[i] = (data[i]-minBound)/range;
		}
	}
	
	public float getAverage(){
		float res = 0f;
		float count = 0f;
		for(int i = 0; i<data.length; i++){
			res+=get(i);
			count++;
		}
		return res/count;
	}
	
	public float getVariance(float average){
		float res = 0f;
		float count = 0f;
		for(int i = 0; i<data.length; i++){
			res+=(get(i)-average)*(get(i)-average);
			count++;
		}
		if(count == 1)return get(0);
		return res/(count-1);
	}
	
	public void standardize(){
		this.standardize(getAverage(), (float)Math.sqrt(getVariance(getAverage())));
	}
	
	public void standardize(float average, float stddev){
		for(int i = 0; i<data.length; i++){
			data[i] = (data[i]-average)/stddev;
		}
	}
	
	public float getMax(){
		float max = data[0];
		for(int i = 1; i<data.length; i++){
			if(data[i] > max){
				max = data[i];
			}
		}
		return max;
	}
	
	public float get(int index){
		return data[index];
	}
	
	public Vector get(int start, int end){
		Vector res = new Vector(end-start+1);
		int k = 0;
		for(int i = start; i<end+1; i++){
			if(i >= getSize()){//padding at 0f
				res.set(k, 0f);
			}else{
				res.set(k, get(i));
			}
			
			k++;
		}
		return res;
	}
	
	public void set(int i, float value){
		data[i] = value;
	}
	
	public int getSize(){
		return data.length;
	}
	
	@Override
	public String toString(){
		String res = new String("[");
		for(int i = 0; i<getSize(); i++){
			res+=" "+data[i];
		}
		res+="]";
		return res;
	}
}

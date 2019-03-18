import java.util.*;

public class FFLayer extends Layer{ //standard feed-forward layer
	private Matrix W; //Weights from the previous layer to the current. The number of row is the nb of cell of the current layer.
	private Vector b; //Bias
	private Vector x; //input
	private Vector a; //uncorrected output, a = W*x+b
	
	public FFLayer(int nbCells){
		super(nbCells);
		b = new Vector(nbCells);
	}
	
	//Only for input layer
	public FFLayer(int previousNbCells, int nbCells){
		this(nbCells);
		this.init(previousNbCells);
	}
	
	
	@Override
	public void init(int previousNbCells){
		Random rng = new Random();
		W = new Matrix(nbCells, previousNbCells);
		for(int i = 0; i<W.getNbRows(); i++){
			for(int j = 0; j<W.getNbColumns(); j++){
				W.set(i, j, (float)(rng.nextGaussian()* Math.sqrt(2f/((float)super.nbCells))));
			}
		}
		for(int i = 0; i<nbCells; i++){
			b.set(i, (float)(rng.nextGaussian()* Math.sqrt(2f/((float)super.nbCells))));
		}		
	}
	
	@Override
	public Vector forward(Vector v){
		x = v;
		a = (W.multVector(v)).addVector(b);
		if(nextLayer != null){
			return nextLayer.forward(a);
		}else{
			return a;
		}	
	}
	
	@Override
	public Vector backward(Vector y, Vector o, float lambda){
		Vector error;
		if(nextLayer == null){
			// C = cost = sum_i(0.5f*(oi-yi)2)
			// cDeri = dC/dwi = dC/doi * doi/dwi
			// dC/doi = oi-yi
			// doi/dwi = sig' * dai/dwi;
			// => cDeri = (oi-yi) * sig' * oi = error * oi;
			error = (o.subVector(y));
		}else{
			// cDeri = dC/dwi = dC/doi * doi/dwi
			// dC/doi = sum_j(dC/daj * daj/doj) = sum_j(dC/doj * sig' * daj/doj);
			// daj/doj = wij
			// doi/dwi = oi
			// => cDeri = sum_j(wij*kronecker_j) * sig' * oi
			error = nextLayer.backward(y, o, lambda);
		}
		
		Vector res = new Vector(error.multMatrix(W)); //res has nbPreviousCells elements.
		error = error.multScalar(-lambda);
		
		W = W.addMatrix(error.outerProduct(x));
		b = b.addVector(error);
		
		return res;
	}
}

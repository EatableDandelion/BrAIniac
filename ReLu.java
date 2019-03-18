import java.util.*;

public class ReLu extends Layer{
	private Vector x; //input vector
	
	public ReLu(){
		super(0);
	}
	
	@Override
	public void init(int previousNbCells){
		super.nbCells = previousNbCells;
	}
	
	@Override
	public Vector forward(Vector v){
		x = new Vector(v);
		for(int i = 0; i<v.getSize(); i++){
			if(v.get(i)<0){
				x.set(i, v.get(i)*0.01f);
			}
		}
		return nextLayer.forward(x);
	}
	
	@Override
	public Vector backward(Vector y, Vector o, float lambda){
		Vector error = nextLayer.backward(y, o, lambda);
		return error.multVector(this.getDerivative(x));
	}
	
	private Vector getDerivative(Vector v){
		Vector res = new Vector(v.getSize());
		for(int i = 0; i<v.getSize(); i++){
			if(v.get(i)>0)res.set(i, 1f);
			else res.set(i, 0.01f*v.get(i));
		}
		return res;
	}
}

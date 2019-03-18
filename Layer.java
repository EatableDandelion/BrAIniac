
public abstract class Layer{
	protected Layer nextLayer;
	protected int nbCells;
		
	public Layer(int nbCells){
		this.nbCells = nbCells;
	}	
	
	public Layer addLayer(Layer layer)
	{
		nextLayer = layer;
		nextLayer.init(nbCells);
		return layer;
	}
	
	public abstract void init(int previousNbCells);
	
	public abstract Vector forward(Vector v);
	
	public Vector backward(Vector y, Vector o, float lambda){
		return nextLayer.backward(y, o, lambda);
	}
	
	public int getNbCells(){
		return nbCells;
	}
	
}

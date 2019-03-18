
public class ConvolutionalLayer extends Layer{
	private int offset;
	
	public ConvolutionalLayer(int nbCells, int offset){
		super(nbCells);
		this.offset = offset;
	}

	@Override
	public void init(int previousNbCells){}
	
	@Override
	public Vector forward(Vector v){
		return v.get(offset, offset+super.nbCells-1);
	}
}

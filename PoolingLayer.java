import java.util.*;

public class PoolingLayer extends Layer{//Input layer
	private int stride;
	private List<ConvolutionalLayer> kernels;
	
	public PoolingLayer(int stride){
		super(0);
		this.stride = stride;
		kernels = new ArrayList<ConvolutionalLayer>();
	}

	@Override
	public void init(int previousNbCells){
		int length = previousNbCells;
		while(length >= stride){
			int offset = 0;	
			while(offset < previousNbCells){//padding, hence not adding stride.
				kernels.add(new ConvolutionalLayer(length, offset));
				offset += stride;
			}			
			length-=stride;
		}		
		super.nbCells = kernels.size();
	}
	
	@Override
	public Vector forward(Vector v){// input Vector v as a developed 2D array, row after row
		Vector res = new Vector(super.nbCells);
		int i = 0;
		for(ConvolutionalLayer kernel : kernels){
			res.set(i, kernel.forward(v).get(0));
			i++;
		}
		return nextLayer.forward(res);
	}
}

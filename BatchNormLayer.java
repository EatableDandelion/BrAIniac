/* this class is faulty, the backward prop is wrong */
public class BatchNormLayer extends Layer{
	private float average;
	private float stddev2;
	private float gamma;
	private float beta;
	private Vector xNorm;
	private Vector x;
	
	public BatchNormLayer(){
		super(0);
	}	
	
	public void init(int previousNbCells){
		super.nbCells = previousNbCells;
		gamma = (float)Math.random()-0.5f;
		beta = (float)Math.random()-0.5f;
		xNorm = new Vector(nbCells);
		x = new Vector(nbCells);
	}
	
	public Vector forward(Vector v)
	{
		x = v;
		average = 0f;
		for(int i=0; i<v.getSize(); i++){
			average += v.get(i);
		}
		average/=v.getSize();
		
		stddev2 = 0f;
		for(int i=0; i<v.getSize(); i++){
			stddev2 += (v.get(i)-average)*(v.get(i)-average);
		}
		stddev2/=v.getSize();
		stddev2+=0.000001f;
		Vector res = new Vector(v.getSize());
		for(int i=0; i<v.getSize(); i++){
			float xNormi = (v.get(i)-average)/((float)Math.sqrt(stddev2));
			res.set(i, gamma*xNormi+beta);
			xNorm.set(i, xNormi);
		}
		return nextLayer.forward(res);
	}
	
	public Vector backward(Vector y, Vector o, float lambda){
		float m = super.nbCells;
		
		Vector dldy = nextLayer.backward(y, o, lambda);
		gamma -= lambda*dldy.dot(xNorm);
		beta -= lambda*dldy.sum(); //.sum() == sum of all the terms
		
		Vector dldxNorm = dldy.multScalar(gamma);
		float dldsigma2 = dldxNorm.dot(x.subScalar(average)) * (-0.5f*(float)Math.pow(stddev2, -1.5f));
		float dldmu = -dldxNorm.sum()/((float)Math.sqrt(stddev2)) - 2f/m*dldsigma2*(x.subScalar(average)).sum();
		Vector dldx = dldxNorm.multScalar(1f/((float)Math.sqrt(stddev2)));
		dldx = dldx.addVector( x.subScalar(average).multScalar(2f*dldmu/m) );
		dldx = dldx.subScalar(-dldmu/m);
		
		return dldx;
	}
}

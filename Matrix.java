
public class Matrix{
	private float[][] data;
	
	public Matrix(float[][] data){
		this.data = data;
	}
	
	public Matrix(int nbRows, int nbColumns){
		data = new float[nbRows][nbColumns];
		for(int i = 0; i<getNbRows(); i++){
			for(int j = 0; j<getNbColumns(); j++){
				data[i][j] = 0f;
			}
		}
	}
	
	public Vector multVector(Vector v){
		float[] res = new float[getNbRows()];
		for(int i = 0; i<getNbRows(); i++){
			float sum = 0f;
			for(int j = 0; j<getNbColumns(); j++){
				sum += data[i][j]*v.get(j);
			}
			res[i] = sum;
		}
		return new Vector(res);
	}
	
	public Matrix multMatrix(Matrix m){
		float[][] res = new float[getNbRows()][m.getNbColumns()];
		for(int i = 0; i<getNbRows(); i++){
			for(int j = 0; j<m.getNbColumns(); j++){
				float sum = 0f;
				for(int k = 0; k<getNbColumns(); k++){
					sum += get(i, k)*m.get(k, j);
				}
				res[i][j] = sum;
			}
		}
		return new Matrix(res);
	}
	
	public Matrix addMatrix(Matrix m){
		float[][] res = new float[getNbRows()][getNbColumns()];
		for(int i = 0; i<getNbRows(); i++){
			for(int j = 0; j<getNbColumns(); j++){
				res[i][j] = get(i, j) + m.get(i, j);
			}
		}
		return new Matrix(res);
	}
	
	public void normalizeRows(){
		for(int i = 0; i<getNbRows(); i++){
			float norm = 0f;
			for(int j = 0; j<getNbColumns(); j++){
				norm+=get(i,j)*get(i,j);
			}
			norm = (float)Math.sqrt(norm);
			
			for(int j = 0; j<getNbColumns(); j++){
				set(i, j, get(i,j)/norm);
			}
		}
	}
	
	public float get(int i, int j){
		return data[i][j];
	}
	
	public void set(int i, int j, float value){
		data[i][j] = value;
	}
	
	public int getNbRows(){
		return data.length;
	}
	
	public int getNbColumns(){
		return data[0].length;
	}
	
	@Override
	public String toString(){
		String res = new String("");
		for(int i = 0; i<getNbRows(); i++){
			res += "|";
			for(int j = 0; j<getNbColumns(); j++){
				res+=" "+data[i][j];
			}
			res += "|\n";
		}
		return res;
	}
	
	public Vector getRow(int i){
		Vector res = new Vector(getNbColumns());
		for(int j = 0; j<getNbColumns(); j++){
			res.set(j, this.get(i,j));
		}
		return res;
	}
	
	public Vector getColumn(int j){
		Vector res = new Vector(getNbRows());
		for(int i = 0; i<getNbRows(); i++){
			res.set(i, this.get(i,j));
		}
		return res;
	}
	
	public void setRow(int i, Vector v){
		for(int j = 0; j<getNbColumns(); j++){
			this.set(i, j, v.get(j));
		}
	}
	
	public void setColumn(int j, Vector v){
		for(int i = 0; i<getNbRows(); i++){
			this.set(i, j, v.get(i));
		}
	}
}

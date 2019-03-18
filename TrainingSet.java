import java.util.*;

public class TrainingSet{
	List<Vector> inputs;
	List<Vector> outputs;
	
	public TrainingSet(){
		inputs = new ArrayList<Vector>();
		outputs = new ArrayList<Vector>();
	}
	
	public void addSet(float[] input, float[] output)
	{
		inputs.add(new Vector(input));
		outputs.add(new Vector(output));
	}
	
	public void train(Layer neuralNetwork, int nbPass, float lambda){
		for(int k = 0; k<nbPass*inputs.size(); k++){
			int i = (int)Math.floor(Math.random()*inputs.size());		
			Vector input = inputs.get(i);
			input.normalize();
			Vector output = outputs.get(i);
			Vector result = neuralNetwork.forward(input);
			
			neuralNetwork.backward(output, result, lambda);	
		}
		for(int i = 0; i<inputs.size(); i++){
			Vector input = inputs.get(i);
			input.normalize();
			Vector output = outputs.get(i);
			Vector result = neuralNetwork.forward(input);
			result.normalize();
			System.out.println(output);
			System.out.println(result);
			System.out.println("");	
		}
	}
}

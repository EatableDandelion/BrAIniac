import java.util.*;
import java.io.*;

public class test{
	public static void main(String[] args){
		
		
		//FFLayer hidden = new FFLayer(3, 4);
		//hidden.addLayer(new ReLu()).addLayer(new BatchNormLayer()).addLayer(new FFLayer(3));
		/*hidden.addLayer(new ReLu()).addLayer(new FFLayer(3));
		
		TrainingSet trainer = new TrainingSet();
		trainer.addSet(new float[]{1f,0f,0f}, new float[]{0f,1f,1f});
		trainer.addSet(new float[]{0f,1f,0f}, new float[]{1f,0f,1f});
		trainer.addSet(new float[]{0f,0f,1f}, new float[]{1f,1f,0f});
		trainer.addSet(new float[]{1f,0f,0f}, new float[]{0f,1f,1f});
		trainer.addSet(new float[]{0f,0f,1f}, new float[]{1f,1f,0f});
		trainer.addSet(new float[]{0f,1f,0f}, new float[]{1f,0f,1f});
		
		trainer.train(hidden, 100000, 0.01f);*/
		
		
		/*PoolingLayer input = new PoolingLayer(3);
		input.init(9);
		input.addLayer(new FFLayer(8)).addLayer(new FFLayer(2));
		
		
		TrainingSet trainer = new TrainingSet();
		trainer.addSet(new float[]{1f,0f,0f, 1f,0f,0f, 0f,0f,0f}, new float[]{1f,0f});
		trainer.addSet(new float[]{0f,0f,0f, 1f,0f,0f, 0f,0f,0f}, new float[]{0f,1f});
		trainer.addSet(new float[]{0f,0f,0f, 1f,0f,0f, 1f,0f,0f}, new float[]{1f,0f});
		trainer.addSet(new float[]{1f,1f,0f, 1f,1f,0f, 0f,1f,0f}, new float[]{1f,0f});
		trainer.addSet(new float[]{1f,0f,0f, 0f,0f,1f, 0f,0f,0f}, new float[]{0f,1f});
		trainer.addSet(new float[]{0f,0f,0f, 0f,1f,0f, 0f,0f,0f}, new float[]{0f,1f});

		trainer.train(input, 50000, 0.1f);
		
		for(int i =0; i<10; i++){
			Vector res = new Vector(3);
			for(int i =0; i<res.getSize(); i++){
				res.set(i, (int)Math.floor(Math.random()*2));
			}
			System.out.println(res);
			System.out.println(network.forward(res));
			System.out.println("");
		}*/
			
		
		
		KMeanClassifier lc = new KMeanClassifier(8);
		int nbSamples = 1000;
		Matrix data = new Matrix(nbSamples,3);
		for(int i =0; i<nbSamples; i++){
			float x = (float)(Math.random()*360.0-180.0);
			float y = (float)(Math.random()*20.0-10.0);
			float z = (float)(Math.round(Math.random()));
			data.set(i, 0, x);
			data.set(i, 1, y);
			data.set(i, 2, z);
		}
		
		lc.train(data,5,50);
		
		//Print output test to a file.
		try{
			PrintWriter writer = new PrintWriter("outputfile.txt", "UTF-8");
			for(int i =0; i<100; i++){
				float x = (float)(Math.random()*360.0-180.0);
				float y = (float)(Math.random()*20.0-10.0);
				float z = (float)(Math.round(Math.random()));
				writer.println(x+" "+y+" "+z+" "+lc.getGroup(new Vector(new float[]{x,y,z})));
			}
			writer.close();
		}catch(Exception e){}
		
		
	}
}

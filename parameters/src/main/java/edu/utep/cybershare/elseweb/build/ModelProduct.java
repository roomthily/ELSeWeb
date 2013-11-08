package edu.utep.cybershare.elseweb.build;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.utep.cybershare.elseweb.model.Algorithm;
import edu.utep.cybershare.elseweb.model.Parameter;

public class ModelProduct {
	
	private HashMap<String, Algorithm> algorithms;
	private HashMap<String, Parameter> parameters;
	
	public ModelProduct(){
		algorithms = new HashMap<String, Algorithm>();
		parameters = new HashMap<String, Parameter>();
	}
		
	public Algorithm getAlgorithm(String key){
		Algorithm algorithm = algorithms.get(key);
		if(algorithm == null){
			algorithm = new Algorithm(key);
			algorithms.put(key, algorithm);
		}
		return algorithm;
	}

	public Parameter getParameter(String key){
		Parameter parameter = parameters.get(key);
		if(parameter == null){
			parameter = new Parameter(key);
			parameters.put(key, parameter);
		}
		return parameter;
	}
	
	//get model elements
	public List<Parameter> getParameters(){return new ArrayList<Parameter>(parameters.values());}
	public List<Algorithm> getAlgorithms(){return new ArrayList<Algorithm>(algorithms.values());}
}

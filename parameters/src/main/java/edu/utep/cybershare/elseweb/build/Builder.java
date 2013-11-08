package edu.utep.cybershare.elseweb.build;

import java.util.ArrayList;

import edu.utep.cybershare.elseweb.model.Algorithm;
import edu.utep.cybershare.elseweb.model.Parameter;

public class Builder {
	
	private Algorithm anAlgorithm;
	private ArrayList<Parameter> parameters;
	
	private ModelProduct product;
	
	public Builder(ModelProduct modelProduct){
		product = modelProduct;
		
		reset();
	}
		
	private void reset(){
		anAlgorithm = null;
		parameters = new ArrayList<Parameter>();
	}

	
	public void buildAlgorithm(String code, String name){
		anAlgorithm = product.getAlgorithm(code);
		anAlgorithm.setName(name);
		anAlgorithm.setCode(code);
	}

	public void buildParameter(String name, int min, int max, String type, String defaultValue){
		Parameter param = product.getParameter(name);
		param.setName(name);
		param.setMin(min);
		param.setMax(max);
		param.setType(type);
		param.setDefaultValue(defaultValue);
		
		parameters.add(param);
	}
		
	public void assemble(){
		for(Parameter param : parameters)
			anAlgorithm.addParameter(param);
		
		reset();
	}
}

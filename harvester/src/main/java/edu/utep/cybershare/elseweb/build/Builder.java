package edu.utep.cybershare.elseweb.build;

import edu.utep.cybershare.elseweb.model.Agent;
import edu.utep.cybershare.elseweb.model.Characteristic;
import edu.utep.cybershare.elseweb.model.Dataset;
import edu.utep.cybershare.elseweb.model.Entity;
import edu.utep.cybershare.elseweb.model.Measurement;
import edu.utep.cybershare.elseweb.model.Observation;

public class Builder {
	
	private Agent agent;
	private Characteristic characteristic;
	private Dataset dataset;
	private Entity entity;
	private Measurement measurement;
	private Observation observation;
	
	private ModelProduct product;
	public Builder(ModelProduct modelProduct){
		product = modelProduct;
	}
	
	public void buildAgent(String key){
		agent = product.getAgent(key);
	}
	public void buildCharacteristic(String key){
		characteristic = product.getCharacteristic(key);
	}
	public void buildDataset(String key){
		dataset = product.getDataset(key);
	}
	public void buildEntity(String key){
		entity = product.getEntity(key);
	}
	public void buildMeasurement(String key){
		measurement = product.getMeasurement(key);
	}
	public void buildObservation(String key){
		observation = product.getObservation(key);
	}
	
	public void assemble(){
		
		
	}
}

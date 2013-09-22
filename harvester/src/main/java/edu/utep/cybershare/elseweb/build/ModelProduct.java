package edu.utep.cybershare.elseweb.build;

import java.util.HashMap;

import edu.utep.cybershare.elseweb.model.Agent;
import edu.utep.cybershare.elseweb.model.Characteristic;
import edu.utep.cybershare.elseweb.model.Dataset;
import edu.utep.cybershare.elseweb.model.Entity;
import edu.utep.cybershare.elseweb.model.Measurement;
import edu.utep.cybershare.elseweb.model.Observation;

public class ModelProduct {
	
	private HashMap<String, Agent> agents;
	private HashMap<String, Characteristic> characteristics;
	private HashMap<String, Dataset> datasets;
	private HashMap<String, Entity> entities;
	private HashMap<String, Observation> observations;
	private HashMap<String, Measurement> measurements;
	
	public ModelProduct(){
		agents = new HashMap<String, Agent>();
		characteristics = new HashMap<String, Characteristic>();
		datasets = new HashMap<String, Dataset>();
		entities = new HashMap<String, Entity>();
		observations = new HashMap<String, Observation>();
		measurements = new HashMap<String, Measurement>();
	}
	
	public Agent getAgent(String key){
		Agent agent = agents.get(key);
		if(agent == null){
			agent = new Agent(key);
			agents.put(key, agent);
		}
		return agent;
	}
	
	public Characteristic getCharacteristic(String key){
		Characteristic characteristic = characteristics.get(key);
		if(characteristic == null){
			characteristic = new Characteristic(key);
			characteristics.put(key, characteristic);
		}
		return characteristic;
	}

	public Dataset getDataset(String key){
		Dataset dataset = datasets.get(key);
		if(dataset == null){
			dataset = new Dataset(key);
			datasets.put(key, dataset);
		}
		return dataset;
	}
	
	public Entity getEntity(String key){
		Entity entity = entities.get(key);
		if(entity == null){
			entity = new Entity(key);
			entities.put(key, entity);
		}
		return entity;
	}
	
	public Observation getObservation(String key){
		Observation observation = observations.get(key);
		if(observation == null){
			observation = new Observation(key);
			observations.put(key, observation);
		}
		return observation;
	}
	
	public Measurement getMeasurement(String key){
		Measurement measurement = measurements.get(key);
		if(measurement == null){
			measurement = new Measurement(key);
			measurements.put(key, measurement);
		}
		return measurement;
	}
}

package edu.utep.cybershare.elseweb.build;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.utep.cybershare.elseweb.model.Catalog;
import edu.utep.cybershare.elseweb.model.Characteristic;
import edu.utep.cybershare.elseweb.model.Dataset;
import edu.utep.cybershare.elseweb.model.Distribution;
import edu.utep.cybershare.elseweb.model.Duration;
import edu.utep.cybershare.elseweb.model.Entity;
import edu.utep.cybershare.elseweb.model.Measurement;
import edu.utep.cybershare.elseweb.model.Observation;
import edu.utep.cybershare.elseweb.model.Region;

public class ModelProduct {
	
	private HashMap<String, Characteristic> characteristics;
	private HashMap<String, Dataset> datasets;
	private HashMap<String, Entity> entities;
	private HashMap<String, Observation> observations;
	private HashMap<String, Measurement> measurements;
	private HashMap<String, Catalog> catalogs;
	private HashMap<String, Distribution> distributions;
	private HashMap<String, Duration> durations;
	private HashMap<String, Region> regions;
	
	public ModelProduct(){
		characteristics = new HashMap<String, Characteristic>();
		datasets = new HashMap<String, Dataset>();
		entities = new HashMap<String, Entity>();
		observations = new HashMap<String, Observation>();
		measurements = new HashMap<String, Measurement>();
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
	
	public Catalog getCatalog(String key){
		Catalog catalog = catalogs.get(key);
		if(catalog == null){
			catalog = new Catalog(key);
			catalogs.put(key, catalog);
		}
		return catalog;
	}
	
	public Measurement getMeasurement(String key){
		Measurement measurement = measurements.get(key);
		if(measurement == null){
			measurement = new Measurement(key);
			measurements.put(key, measurement);
		}
		return measurement;
	}
	
	public Duration getDuration(String key){
		Duration value = durations.get(key);
		if(value == null){
			value = new Duration(key);
			durations.put(key, value);
		}
		return value;
	}
	
	public Region getRegion(String key){		
		Region value = regions.get(key);
		if(value == null){
			value = new Region(key);
			regions.put(key, value);
		}
		return value;
	}
	
	public Distribution getDistribution(String key){
		Distribution value = distributions.get(key);
		if(value == null){
			value = new Distribution(key);
			distributions.put(key, value);
		}
		return value;
	}
	
	//get model elements
	public List<Characteristic> getCharacteristics(){return new ArrayList<Characteristic>(characteristics.values());}
	public List<Dataset> getDatasets(){return new ArrayList<Dataset>(datasets.values());}
	public List<Entity> getEntities(){return new ArrayList<Entity>(entities.values());}
	public List<Observation> getObservations(){return new ArrayList<Observation>(observations.values());}
	public List<Measurement> getMeasurements(){return new ArrayList<Measurement>(measurements.values());}
	public List<Catalog> getCatalogs(){return new ArrayList<Catalog>(catalogs.values());}
	public List<Distribution> getDistributions(){return new ArrayList<Distribution>(distributions.values());}
	public List<Duration> getDurations(){return new ArrayList<Duration>(durations.values());}
	public List<Region> getRegions(){return new ArrayList<Region>(regions.values());}
}

package edu.utep.cybershare.elseweb.edac.statistics;

import java.util.Hashtable;

import edu.utep.cybershare.elseweb.edac.edacDigest.WCSDigest;
import edu.utep.cybershare.elseweb.edac.edacDigest.WCSDigests;

public class EntityMeasurementTypes {

	private Hashtable<String,Integer> types;
	
	public EntityMeasurementTypes(){
		types = new Hashtable<String,Integer>();
		
		populateEntityMeasurementTypes();
	}

	private void populateEntityMeasurementTypes(){
		WCSDigests digests = new WCSDigests(500, 0);
		System.out.println("Number of WCS digests found: " + digests.size());
		
		for(WCSDigest digest : digests){
			addEntityMeasurementType(digest.getEntityMeasurementType());
		}
	}
	
	private void addEntityMeasurementType(String type){
		Integer count = types.get(type);
		if(count == null){
			count = new Integer(0);
			types.put(type, count);
		}
		count++;
	}
	
	public void printDistribution(){
		Integer count;
		for(String measurementEntityType : types.keySet()){
			count = types.get(measurementEntityType);
			System.out.println("EntityMeasurementType: " + measurementEntityType + ", Count: " + count);
		}
	}
	
	public static void main(String[] args){
		EntityMeasurementTypes types = new EntityMeasurementTypes();
		types.printDistribution();
	}
}

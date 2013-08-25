package edu.utep.cybershare.elseweb.edac.statistics;

import java.util.Hashtable;

import edu.utep.cybershare.elseweb.edac.edacDigest.WCSDigest;
import edu.utep.cybershare.elseweb.edac.edacDigest.WCSDigests;
import edu.utep.cybershare.elseweb.edac.fgdc.theme.Themes;

public class EntityMeasurementTypes {

	private Hashtable<String,Integer> types;
	
	public EntityMeasurementTypes(){
		types = new Hashtable<String,Integer>();
		
		populateEntityMeasurementTypes();
	}

	private void populateEntityMeasurementTypes(){
		WCSDigests digests = new WCSDigests(5000, 0);
		System.out.println("Number of WCS digests found: " + digests.size());
		
		for(WCSDigest digest : digests){
			addEntityMeasurementType(digest.getFGDCThemes());
		}
	}
	
	private String getThemekey(Themes themes){
		themes.get
	}
	
	private void addEntityMeasurementType(Themes themes){
		
		Integer count = types.get(type);
		int counter;
		if(count == null)
			count = new Integer(0);
		
		// need to use +1 rather than ++ since count is an Integer object
		counter = count + 1;
		types.put(type, new Integer(counter));
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

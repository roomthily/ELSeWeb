package edu.utep.cybershare.elseweb.ontology;

import java.util.Hashtable;

import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.rdf.model.Resource;

import edu.utep.cybershare.elseweb.oboe.Characteristic.Vocab;

public class ClassMapper {
	private static Hashtable<String, OntClass> themkeyToCharacteristic = new Hashtable<String, Resource>();

	
	private static void populateMappings(){
		
		themekeyToCharacteristic.put("dew_point_temperature", Vocab.Temperature);
		themekeyToChar.put("precipitation_amount", Vocab.Amount);
		themekeyToChar.put("air_temperature", Vocab.Temperature);
		themekeyToChar.put("LAND SURFACE TEMPERATURE", Vocab.Temperature);
		themekeyToChar.put("VEGETATION INDEX", Vocab.Amount);
	}

	private static Resource getCharacteristicClass(String themekey){
		if(themekeyToChar == null)
			populateMappings();
		
		return themekeyToChar.get(themekey);
	}
}

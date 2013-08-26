package edu.utep.cybershare.elseweb.oboe;

import java.util.Hashtable;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;

public class Characteristic {
	private static Hashtable<String, Resource> themekeyToChar;
	
	private static void populateMappings(){
		themekeyToChar = new Hashtable<String, Resource>();
		
		themekeyToChar.put("dew_point_temperature", Vocab.Temperature);
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
	
	public static Resource createCharacteristicResource(String baseURI, String themekey, Model aModel){
		Resource entityClassResource = getCharacteristicClass(themekey);
		
		String characteristicURI = baseURI + themekey + "_Characteristic_";
		Resource entityResource = aModel.createResource(characteristicURI, entityClassResource);
		return entityResource;
	}
	
	private static final class Vocab{
		
		private static Model m_model = ModelFactory.createDefaultModel();
	
		private static final Resource Temperature = m_model.createResource("http://ecoinformatics.org/oboe/oboe.1.0/oboe-characteristics.owl#Temperature");
		private static final Resource Amount = m_model.createResource("http://ecoinformatics.org/oboe/oboe.1.0/oboe-characteristics.owl#Amount");
	}
}

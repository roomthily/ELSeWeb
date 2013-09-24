package edu.utep.cybershare.elseweb.ontology.vocabulary;

import java.util.HashMap;

import com.hp.hpl.jena.ontology.OntClass;

public class OBOEOntClassMapper {
	
	private static HashMap<String, OntClass> themekeyToCharacteristic = new HashMap<String, OntClass>();
	private static HashMap<String, OntClass> themekeyToEntity = new HashMap<String, OntClass>();
	
	public OBOEOntClassMapper(OBOE oboe){
		populateCharacteristicMappings(oboe);
		populateEntityMappings(oboe);
	}
	
	private void populateEntityMappings(OBOE oboeVocab){
		themekeyToEntity.put("dew_point_temperature", oboeVocab.getOWLClass_AtmosphericFeature());
		themekeyToEntity.put("precipitation_amount", oboeVocab.getOWLClass_TerrestrialFeature());
		themekeyToEntity.put("air_temperature", oboeVocab.getOWLClass_AtmosphericFeature());
		themekeyToEntity.put("LAND SURFACE TEMPERATURE", oboeVocab.getOWLClass_TerrestrialFeature());
		themekeyToEntity.put("VEGETATION INDEX", oboeVocab.getOWLClass_EcologicalCommunity());
	}
	
	private void populateCharacteristicMappings(OBOE oboeVocab){
		themekeyToCharacteristic.put("dew_point_temperature", oboeVocab.getOWLClass_Temperature());
		themekeyToCharacteristic.put("precipitation_amount", oboeVocab.getOWLClass_Amount());
		themekeyToCharacteristic.put("air_temperature", oboeVocab.getOWLClass_Temperature());
		themekeyToCharacteristic.put("LAND SURFACE TEMPERATURE", oboeVocab.getOWLClass_Temperature());
		themekeyToCharacteristic.put("VEGETATION INDEX", oboeVocab.getOWLClass_Amount());
	}

	public OntClass getCharacteristicOntClass(String themekey){
		return themekeyToCharacteristic.get(themekey);
	}	
	public OntClass getEntityOntClass(String themekey){
		return themekeyToEntity.get(themekey);
	}
}

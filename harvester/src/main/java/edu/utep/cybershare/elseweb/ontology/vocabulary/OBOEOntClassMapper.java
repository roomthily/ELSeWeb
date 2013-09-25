package edu.utep.cybershare.elseweb.ontology.vocabulary;

import java.util.HashMap;

import org.semanticweb.owlapi.model.OWLClass;

public class OBOEOntClassMapper {
	
	private static HashMap<String, OWLClass> themekeyToCharacteristic = new HashMap<String, OWLClass>();
	private static HashMap<String, OWLClass> themekeyToEntity = new HashMap<String, OWLClass>();
	
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

	public OWLClass getCharacteristicOntClass(String themekey){
		return themekeyToCharacteristic.get(themekey);
	}	
	public OWLClass getEntityOntClass(String themekey){
		return themekeyToEntity.get(themekey);
	}
}

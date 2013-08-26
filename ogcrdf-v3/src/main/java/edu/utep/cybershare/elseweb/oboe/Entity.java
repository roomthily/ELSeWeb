package edu.utep.cybershare.elseweb.oboe;

import java.util.Hashtable;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;

public class Entity {

	private static Hashtable<String, Resource> themekeyToEntity;
	private static int counter = 0;
	
	private static void populateMappings(){
		themekeyToEntity = new Hashtable<String, Resource>();
		
		themekeyToEntity.put("dew_point_temperature", Vocab.AtmosphericFeature);
		themekeyToEntity.put("precipitation_amount", Vocab.TerrestrialFeature);
		themekeyToEntity.put("air_temperature", Vocab.AtmosphericFeature);
		themekeyToEntity.put("LAND SURFACE TEMPERATURE", Vocab.TerrestrialFeature);
		themekeyToEntity.put("VEGETATION INDEX", Vocab.EcologicalCommunity);
	}

	private static Resource getEntityClass(String themekey){
		if(themekeyToEntity == null)
			populateMappings();
		
		return themekeyToEntity.get(themekey);
	}
	
	public static Resource createEntityResource(String baseURI, String themekey, Model aModel){
		Resource entityClassResource = getEntityClass(themekey);
		
		String entityURI = baseURI + themekey + "_Entity_" + counter ++;
		Resource entityResource = aModel.createResource(entityURI, entityClassResource);
		return entityResource;
	}
	
	private static final class Vocab{
		
		private static Model m_model = ModelFactory.createDefaultModel();

		private static final Resource EcologicalCommunity = m_model.createResource("http://ecoinformatics.org/oboe/oboe.1.0/oboe-ecology.owl#EcologicalCommunity");
		private static final Resource TerrestrialFeature = m_model.createResource("http://ecoinformatics.org/oboe/oboe.1.0/oboe-environment.owl#TerrestrialFeature");
		private static final Resource AtmosphericFeature = m_model.createResource("http://ecoinformatics.org/oboe/oboe.1.0/oboe-environment.owl#AtmosphericFeature");
	}
}

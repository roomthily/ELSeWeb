package edu.utep.cybershare.elseweb.oboe;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;

public class Measurement {

	private static int counter = 0;
	
	public static Resource getMeasurementResource(String baseURI, Model aModel){
		String measurementURI = baseURI + "Measurement_" + counter ++;
		return aModel.createResource(measurementURI, Vocab.Measurement);
	}
	
	private static final class Vocab{
		
		private static Model m_model = ModelFactory.createDefaultModel();
		
		private static final Resource Measurement = m_model.createResource("http://ecoinformatics.org/oboe/oboe.1.0/oboe-core.owl#Measurement");
	}	
}

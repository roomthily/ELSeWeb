package edu.utep.cybershare.elseweb.oboe;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;

public class Observation {
	
	private static int counter = 0;
	
	public static Resource getObservationResource(String baseURI, Model aModel){
		String measurementURI = baseURI + "Observation_" + counter ++;
		return aModel.createResource(measurementURI, Vocab.Observation);
	}
	
	
	private static final class Vocab
	{
		private static Model m_model = ModelFactory.createDefaultModel();

		private static final String OBOE_CORE = "http://ecoinformatics.org/oboe/oboe.1.0/oboe-core.owl";

		public static final Resource Observation = m_model.createResource(OBOE_CORE + "#Observation");
	}
}

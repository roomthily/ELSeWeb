package edu.utep.cybershare.elseweb.prov;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;

public class SADIServiceProv {
	
	private Model model;
	private Resource activity;
	
	public SADIServiceProv(String baseURI, String serviceName, Model outputModel){
		model = outputModel;
		activity = model.createResource(baseURI + serviceName, Vocab.SadiService);
	}
	
	public void used(Resource anInput){
		model.add(activity, Vocab.used, anInput);
	}
	
	
	public void generates(Resource anOutput){
		model.add(anOutput, Vocab.wasGeneratedBy, activity);
	}
	
	
	private static final class Vocab {
		private static Model m_model = ModelFactory.createDefaultModel();
	
		public static Resource SadiService = m_model.createResource("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/prov/prov-sadi.owlSadiService");
		public static Resource SADI_Input = m_model.createResource("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/prov/SADI_Input");
		public static Resource SADI_Output = m_model.createResource("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/prov/SADI_Output");
		
		public static Property used = m_model.createProperty("http://www.w3.org/ns/prov#used");
		public static Property wasGeneratedBy = m_model.createProperty("http://www.w3.org/ns/prov#wasGeneratedBy");
	}
}


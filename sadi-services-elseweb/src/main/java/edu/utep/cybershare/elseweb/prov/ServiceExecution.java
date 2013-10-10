package edu.utep.cybershare.elseweb.prov;

import java.util.List;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;

public class ServiceExecution {
	
	private static final String baseURI = "http://elseweb.cybershare.utep.edu/";

	private Resource activity;
	private KnoweldgeBase kb;

	private String inputClassURI;
	private Resource input;
	private Resource output;
	
	private NamedGraph usedNamedGraph;
	private NamedGraph generatedNamedGraph;
	
	private Model model;
	
	public ServiceExecution(String serviceName, Resource input, Resource output, String inputClassURI){
		this.inputClassURI = inputClassURI;
		this.input = input;
		this.output = output;
		
		this.model = output.getModel();
		
		kb = new KnoweldgeBase();
		
		//create activity that represents service execution
		activity = getActivityResource(serviceName, output.getModel());		
	}
	
	private void linkUp(){
		
		NamedGraph existingInputNamedGraph = kb.getNamedGraph(input.getURI());
		if(existingInputNamedGraph != null)
			output.getModel().add(activity, Vocab.used, existingInputNamedGraph.getURI());

		NamedGraph newInputNamedGraphWithAntecedents = this.getNamedGraphWithAntecedents();		
		if(newInputNamedGraphWithAntecedents != null)
			output.getModel().add(activity, Vocab.used, newInputNamedGraphWithAntecedents.getURI());
		
		NamedGraph newInputNamedGraph = this.kb.getNamedGraph(input.getURI());
			output.getModel().add(activity, Vocab.used, newInputNamedGraph.getURI());		
	}
	
	private NamedGraph getNamedGraphWithAntecedents(){
		List<NamedGraph> namedGraphs = kb.getAntecedentNamedGraphs(input.getURI(), inputClassURI);
		NamedGraph serviceInputGraph = null;
		if(namedGraphs.size() > 0){
			serviceInputGraph = this.getNewInputNamedGraph();
			attachedDerivedFromGraphs(serviceInputGraph);
		}
		return serviceInputGraph;
	}
	
	private NamedGraph getNewInputNamedGraph(){
		//create new graph based on the root node URI
		
		NamedGraph serviceInputGraph = kb.getNewNamedGraph(input);
		output.getModel().add(activity, Vocab.used, serviceInputGraph.getURI());
		return serviceInputGraph;
	}
	
	private NamedGraph getExistingNamedGraph(){
		NamedGraph namedGraph = kb.getNamedGraph(input.getURI());
		if(namedGraph != null)
			output.getModel().add(activity, Vocab.used, namedGraph.getURI());
		
		return namedGraph;
	}
		
	public void attachedDerivedFromGraphs(NamedGraph serviceInputGraph){
		List<NamedGraph> antecedentGraphs = kb.getAntecedentNamedGraphs(input.getURI(), inputClassURI);
						
		//add antecedents to new graph
		for(NamedGraph antecedentGraph : antecedentGraphs)
			output.getModel().add(activity, Vocab.used, serviceInputGraph.getURI());
	}
	
	private Resource getActivityResource(String serviceName, Model model){
		return model.createResource(baseURI + serviceName, Vocab.SadiService);
	}
	
	
	private static final class Vocab {
		private static Model m_model = ModelFactory.createDefaultModel();
	
		public static Resource SadiService = m_model.createResource("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/prov/prov-sadi.owlSadiService");
		public static Resource SADI_Input = m_model.createResource("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/prov/SADI_Input");
		public static Resource SADI_Output = m_model.createResource("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/prov/SADI_Output");
		
		private static final String prov = "http://www.w3.org/ns/prov#";
		public static Property used = m_model.createProperty(prov + "used");
		public static Property wasGeneratedBy = m_model.createProperty(prov + "wasGeneratedBy");
		public static Property wasDerivedFrom = m_model.createProperty(prov + "wasDerivedFrom");
	}
}

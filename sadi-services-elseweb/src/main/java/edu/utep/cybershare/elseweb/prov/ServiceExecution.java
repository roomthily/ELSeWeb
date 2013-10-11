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
	
	public void linkUp(){
		this.linkUpInput();
		this.linkUpOutput();
	}
	
	private void linkUpOutput(){
		NamedGraph outputNamedGraph = kb.getNewNamedGraph(output);
		Resource namedGraphResource = output.getModel().getResource(outputNamedGraph.getURI());
		model.add(namedGraphResource, Vocab.wasGeneratedBy, activity);
	}
	
	private void linkUpInput(){
		NamedGraph namedGraph;
		
		if((namedGraph = kb.getNamedGraph(input.getURI())) != null)
			output.getModel().add(activity, Vocab.used, namedGraph.getURI());
		else if((namedGraph = this.getNamedGraphWithAntecedents()) != null)		
			output.getModel().add(activity, Vocab.used, namedGraph.getURI());
		else{
			namedGraph = this.kb.getNewNamedGraph(input);
			output.getModel().add(activity, Vocab.used, namedGraph.getURI());
		}		
	}
	
	private NamedGraph getNamedGraphWithAntecedents(){
		List<NamedGraph> namedGraphs = kb.getAntecedentNamedGraphs(inputClassURI);
		NamedGraph serviceInputGraph = null;
		if(namedGraphs.size() > 0){
			System.out.println("found antecedent graphs....");
			serviceInputGraph = this.getNewInputNamedGraph();
			attachedDerivedFromGraphs(serviceInputGraph, namedGraphs);
		}
		return serviceInputGraph;
	}
	
	private NamedGraph getNewInputNamedGraph(){
		NamedGraph serviceInputGraph = kb.getNewNamedGraph(input);
		output.getModel().add(activity, Vocab.used, serviceInputGraph.getURI());
		return serviceInputGraph;
	}
	
	public void close(){kb.close();}

		
	private void attachedDerivedFromGraphs(NamedGraph serviceInputGraph, List<NamedGraph> antecedentGraphs){
		//add antecedents to new graph
		for(NamedGraph antecedentGraph : antecedentGraphs){
			System.out.println("antecedent graph: " + antecedentGraph.getURI());
			model.add(antecedentGraph.getContents(), Vocab.wasDerivedFrom, antecedentGraph.getContents());
		}
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
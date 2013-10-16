package edu.utep.cybershare.elseweb.prov;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;

import edu.utep.cybershare.elseweb.prov.namedGraph.NamedGraph;
import edu.utep.cybershare.elseweb.prov.namedGraph.NamedGraphs;

public class ServiceExecution {
	
	private static final String baseURI = "http://elseweb.cybershare.utep.edu/";

	private Resource activity;
	private Antecedents antecedents;

	private String outputClassURI;
	private Resource output;
	private NamedGraphs existingGraphs;
	
	private Model model;
	
	public ServiceExecution(String serviceName, Resource input, Resource output, String inputClassURI, String outputClassURI){
		this.output = output;
		this.existingGraphs = new NamedGraphs();
		
		this.model = output.getModel();
		
		antecedents = new Antecedents(input, inputClassURI, existingGraphs, model);
		
		//create activity that represents service execution
		activity = getActivityResource(serviceName, output.getModel());		
	}
	
	public void linkUp(){
		this.linkUpInput();
		this.linkUpOutput();
	}
	
	private void linkUpOutput(){
		//create new graph to be dumped since it might end up being an antecedent of another input
		NamedGraph outputNamedGraph = this.existingGraphs.getNewNamedGraph(output, this.outputClassURI, true);		
		model.add(outputNamedGraph.getContents(), Vocab.wasGeneratedBy, activity);
	}
	
	private void linkUpInput(){		
		NamedGraph inputGraph = antecedents.getInputgraph();
		model.add(activity, Vocab.used, inputGraph.getContents());
	}
		
	public void close(){antecedents.close();}
		
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
package edu.utep.cybershare.elseweb.prov;

import java.util.ArrayList;
import java.util.List;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;

import edu.utep.cybershare.elseweb.prov.namedGraph.NamedGraph;
import edu.utep.cybershare.elseweb.prov.namedGraph.NamedGraphs;



public class Antecedents {

	private NamedGraphs namedGraphs;
	private NamedGraph inputNamedGraph;
	private Model outputModel;
	
	private List<NamedGraph> case1Antecedents;
	private List<NamedGraph> case2Antecedents;
	private List<NamedGraph> case3Antecedents;
	private boolean foundAntecedents;
	
	private boolean foundEquivalentGraph;
	
	public Antecedents(Resource inputResource, String inputClassURI, NamedGraphs existingGraphs, Model outputModel){		
		namedGraphs = existingGraphs;
		inputNamedGraph = namedGraphs.getNewNamedGraph(inputResource, inputClassURI);
		this.outputModel = outputModel;
		
		setAntecedents();
		
		if(!foundAntecedents)
			this.setEquivalentGraph();

		if(!foundEquivalentGraph)
			System.out.println("this is a newly encountered graph and has no antecedents, so do nothing");
	}
	
	public NamedGraph getInputgraph(){
		return this.inputNamedGraph;
	}
			
	private void setAntecedents(){
		setCase1Antecedents();
		setCase2Antecedents();
		setCase3Antecedents();
		
		if(case1Antecedents.size() > 0)
			linkUpAntecedents(case1Antecedents);
		else if(case2Antecedents.size() > 0)
			linkUpAntecedents(case2Antecedents);
		else if(case3Antecedents.size() > 0)
			linkUpAntecedents(case3Antecedents);
	}
	
	private void linkUpAntecedents(List<NamedGraph> antecedents){
		foundAntecedents = true;
		for(NamedGraph antecedent : antecedents)
			outputModel.add(this.inputNamedGraph.getContents(), Vocab.wasDerivedFrom, antecedent.getContents());
	}
		
	private void setCase1Antecedents(){
		case1Antecedents = new ArrayList<NamedGraph>();

		for(NamedGraph namedGraph : this.namedGraphs.values()){
			Individual individual = namedGraph.getIndividualOf(inputNamedGraph.getGraphClass());
			if(individual.getURI().equals(inputNamedGraph.getRootNodeURI()))
				case1Antecedents.add(namedGraph);
		}
	}
	
	private void setCase2Antecedents(){
		case2Antecedents = new ArrayList<NamedGraph>();
			
		for(NamedGraph namedGraph : this.namedGraphs.values()){
			if(inputNamedGraph.hasCommonComponents(namedGraph))
				case2Antecedents.add(namedGraph);
		}
	}	

	private void setCase3Antecedents(){
		case3Antecedents = new ArrayList<NamedGraph>();
		
		for(NamedGraph namedGraph : this.namedGraphs.values()){
			if(this.inputNamedGraph.hasComponent(namedGraph))
				case3Antecedents.add(namedGraph);
		}
	}

	private void setEquivalentGraph(){
		foundEquivalentGraph = false;
		for(NamedGraph namedGraph : this.namedGraphs.values()){
			if(!foundEquivalentGraph && namedGraph.getRootNodeURI().equals(this.inputNamedGraph.getRootNodeURI())){
				//remove input since we don't want to save it to file
				this.namedGraphs.remove(this.inputNamedGraph.getContents().getURI());

				//set the input named graph to the identical one we indentified
				this.inputNamedGraph = namedGraph;
				foundEquivalentGraph = true;
			}
		}
	}
	public void close(){namedGraphs.dump();}
	
	
	private static final class Vocab {
		private static Model m_model = ModelFactory.createDefaultModel();
		
		private static final String prov = "http://www.w3.org/ns/prov#";
		public static Property wasDerivedFrom = m_model.createProperty(prov + "wasDerivedFrom");
	}
}
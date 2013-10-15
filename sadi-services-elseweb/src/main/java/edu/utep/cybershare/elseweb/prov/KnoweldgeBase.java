package edu.utep.cybershare.elseweb.prov;

import java.util.ArrayList;
import java.util.List;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.rdf.model.Resource;



public class KnoweldgeBase {

	private NamedGraphs namedGraphs;
	private NamedGraph inputNamedGraph;

	public KnoweldgeBase(Resource inputResource, String inputClassURI){		
		namedGraphs = new NamedGraphs();
		inputNamedGraph = namedGraphs.getNewNamedGraph(inputResource, inputClassURI);
	}
	
	public List<NamedGraph> getAntecedents(){
		List<NamedGraph> antecedents;
		
		antecedents = this.getCase1Antecedents();
		if(antecedents.size() > 0)
			return antecedents;
		
		antecedents = this.getCase2Antecedents();
		if(antecedents.size() > 0)
			return antecedents;
		
		antecedents = this.getCase3Antecedents();
		if(antecedents.size() > 0)
			return antecedents;

		NamedGraph antecedent = this.getCase4Antecedent();
		if(antecedent != null){
			antecedents = new ArrayList<NamedGraph>();
			antecedents.add(antecedent);
			return antecedents;
		}
		
		antecedents = new ArrayList<NamedGraph>();
		antecedents.add(this.inputNamedGraph);
		return antecedents;
	}
	
	private List<NamedGraph> getCase1Antecedents(){
		ArrayList<NamedGraph> antecedentGraphs = new ArrayList<NamedGraph>();

		for(NamedGraph namedGraph : this.namedGraphs.values()){
			Individual individual = namedGraph.getIndividualOf(inputNamedGraph.getGraphClass());
			if(individual.getURI().equals(inputNamedGraph.getRootNodeURI()))
				antecedentGraphs.add(namedGraph);
		}
		return antecedentGraphs;
	}
	
	private List<NamedGraph> getCase2Antecedents(){
		ArrayList<NamedGraph> antecedentGraphs = new ArrayList<NamedGraph>();
			
		for(NamedGraph namedGraph : this.namedGraphs.values()){
			if(inputNamedGraph.hasCommonComponents(namedGraph))
				antecedentGraphs.add(namedGraph);
		}
		
		return antecedentGraphs;
	}	

	private NamedGraph getCase4Antecedent(){
		for(NamedGraph namedGraph : this.namedGraphs.values()){
			if(namedGraph.getRootNodeURI().equals(this.inputNamedGraph.getRootNodeURI()))
				return namedGraph;
		}
		return null;
	}
	
	private List<NamedGraph> getCase3Antecedents(){
		ArrayList<NamedGraph> antecedentGraphs = new ArrayList<NamedGraph>();
		
		for(NamedGraph namedGraph : this.namedGraphs.values()){
			if(this.inputNamedGraph.hasComponent(namedGraph))
				antecedentGraphs.add(namedGraph);
		}
		return antecedentGraphs;
	}
	
	public void close(){namedGraphs.dump();}
}
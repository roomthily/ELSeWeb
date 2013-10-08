package edu.utep.cybershare.elseweb.prov;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.mindswap.pellet.jena.PelletReasonerFactory;

import com.hp.hpl.jena.ontology.IntersectionClass;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.ontology.Restriction;
import com.hp.hpl.jena.ontology.SomeValuesFromRestriction;
import com.hp.hpl.jena.rdf.model.ModelFactory;

public class KnoweldgeBase {
	
	private NamedGraphs namedGraphs;
	
	private static final String literalURI = "http://www.w3.org/2000/01/rdf-schema#Literal";
	
	public KnoweldgeBase(){
		GraphsLog graphsLog = new GraphsLog();
		namedGraphs = graphsLog.loadNamedGraphs();
	}
	
	public List<NamedGraph> getNamedGraphs(String rootNodeURI){
		ArrayList<NamedGraph> namedGraphs = new ArrayList<NamedGraph>();
		for(NamedGraph namedGraph : this.namedGraphs.values()){
			if(namedGraph.getRootNodeURI().equals(rootNodeURI))
				namedGraphs.add(namedGraph);
		}
		return namedGraphs;
	}
	
	public List<NamedGraph> getAntecedentNamedGraphs(String rootNodeURI, String inputClassURI){
		ArrayList<NamedGraph> namedGraphs = new ArrayList<NamedGraph>();
		
		//create KB loaded with class extensions
		OntModel model = getModel(inputClassURI, rootNodeURI);
		List<OntClass> someValueFromClasses;
		for(NamedGraph namedGraph : this.namedGraphs.values()){			
			//check if named graph contained any individuals expressed in the loaded class extension
			someValueFromClasses = this.getSomeValuesFromClasses(inputClassURI, model);
			if(containsIndividualsOfClasses(model, someValueFromClasses))
				namedGraphs.add(namedGraph);
		}
		
		return namedGraphs;
	}
	
	private boolean containsIndividualsOfClasses(OntModel model, List<OntClass> targetClasses){		
		for(OntClass ontClass : targetClasses){
			if(ontClass.listInstances().hasNext())
				return true;
		}
		return false;
	}
	
	private List<OntClass> getSomeValuesFromClasses(String inputClassURI, OntModel model){
		ArrayList<OntClass> partOfIntersectionClasses = new ArrayList<OntClass>();
		OntClass inputClass = model.getOntClass(inputClassURI);
		
		IntersectionClass intersection = inputClass.getEquivalentClass().asIntersectionClass();
		 
		for (Iterator<? extends OntClass> i = intersection.listOperands(); i.hasNext(); ) {
		      OntClass partOfIntersectionClass = (OntClass) i.next();

		      if (partOfIntersectionClass.isRestriction()) {
		    	  Restriction res = partOfIntersectionClass.asRestriction();
		    	  SomeValuesFromRestriction restriction = res.asSomeValuesFromRestriction();
		    	  
		    	  OntClass valuesFromClass;
		    	  try{
		    		  valuesFromClass = restriction.getSomeValuesFrom().as(OntClass.class);
		    		  if(!valuesFromClass.getURI().equals(literalURI))
		    			  partOfIntersectionClasses.add(valuesFromClass);
		    	  }catch(Exception e){e.printStackTrace();}
		      }
		      //if is a named class rather than anonymous restriction class
		      else
		    	  partOfIntersectionClasses.add(partOfIntersectionClass);
		  }
		 return partOfIntersectionClasses;
	}
	
	private OntModel getModel(String inputClassURI, String rootNodeClassURI){
		OntModelSpec ontModelSpec = OntModelSpec.OWL_DL_MEM;
	    ontModelSpec.setReasoner(PelletReasonerFactory.theInstance().create());
	    
		OntModel model = ModelFactory.createOntologyModel(ontModelSpec);
		model.read(rootNodeClassURI);
		model.read(inputClassURI);
		return model;
	}
}
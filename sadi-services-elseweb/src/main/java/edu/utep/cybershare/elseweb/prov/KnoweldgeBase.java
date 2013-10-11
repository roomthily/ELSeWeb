package edu.utep.cybershare.elseweb.prov;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.hp.hpl.jena.ontology.IntersectionClass;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.ontology.Restriction;
import com.hp.hpl.jena.ontology.SomeValuesFromRestriction;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.reasoner.Reasoner;
import com.hp.hpl.jena.reasoner.ReasonerRegistry;


public class KnoweldgeBase {
	
	private NamedGraphs namedGraphs;
	
	private static final String literalURI = "http://www.w3.org/2000/01/rdf-schema#Literal";
	
	public KnoweldgeBase(){namedGraphs = new NamedGraphs();}
		
	public NamedGraph getNamedGraph(String rootNodeURI){return namedGraphs.get(rootNodeURI);}
	
	public List<NamedGraph> getAntecedentNamedGraphs(String inputClassURI){
		ArrayList<NamedGraph> namedGraphs = new ArrayList<NamedGraph>();
		
		//create KB loaded with class extensionsn
		OntModel model = getModel(inputClassURI);
		List<OntClass> someValueFromClasses;
		for(NamedGraph namedGraph : this.namedGraphs.values()){			
			//check if named graph contained any individuals expressed in the loaded class extension
			someValueFromClasses = this.getSomeValuesFromClasses(inputClassURI, model);
			if(containsIndividualsOfClasses(model, someValueFromClasses))
				namedGraphs.add(namedGraph);
		}
		
		return namedGraphs;
	}
	
	public NamedGraph getNewNamedGraph(Resource graphContents){
		return namedGraphs.getNewNamedGraph(graphContents);
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
	
	public void close(){namedGraphs.dump();}
	
	private OntModel getModel(String inputClassURI){
		//create Jena OWL Reasoner
		Reasoner reasoner = ReasonerRegistry.getOWLReasoner();
		
		OntModelSpec ontModelSpec = OntModelSpec.OWL_DL_MEM;
	    ontModelSpec.setReasoner(reasoner);
	    
		OntModel model = ModelFactory.createOntologyModel(ontModelSpec);
		model.read(inputClassURI);
		return model;
	}
}
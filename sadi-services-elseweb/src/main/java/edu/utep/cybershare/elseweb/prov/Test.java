package edu.utep.cybershare.elseweb.prov;

import java.util.Iterator;

import com.hp.hpl.jena.ontology.EnumeratedClass;
import com.hp.hpl.jena.ontology.IntersectionClass;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.Restriction;
import com.hp.hpl.jena.ontology.SomeValuesFromRestriction;
import com.hp.hpl.jena.rdf.model.ModelFactory;

public class Test {
	
	public static void main(String[] args){

		String classURI = "http://ontology.cybershare.utep.edu/ELSEWeb/lifemapper.owl#ExecutableExperiment";
		
		OntModel model = ModelFactory.createOntologyModel();
		model.read(classURI);
		
		OntClass inputClass = model.getOntClass(classURI);
		IntersectionClass intersection = inputClass.getEquivalentClass().asIntersectionClass();
		
		
		 for (Iterator<? extends OntClass> i = intersection.listOperands(); i.hasNext(); ) {
		      OntClass op = (OntClass) i.next();

		      if (op.isRestriction()) {
		    	  Restriction res = op.asRestriction();
		    	  SomeValuesFromRestriction res1 = res.asSomeValuesFromRestriction();
		    	  
		          System.out.println("Restriction on property " + res1.getOnProperty());
		          System.out.println("With some values from: " + res1.getSomeValuesFrom());
		          
		          OntClass valuesFromClass = res1.getSomeValuesFrom().as(OntClass.class);
		          System.out.println(valuesFromClass.getURI());
		          
		          String literalURI = "http://www.w3.org/2000/01/rdf-schema#Literal";
		          		
		      }
		      else {
		          System.out.println( "Named class " + op );
		      }
		  }
	}
}

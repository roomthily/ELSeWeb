package edu.utep.cybershare.elseweb.ontology;

import java.net.URI;
import java.util.Hashtable;

import com.hp.hpl.jena.ontology.Individual;

import edu.utep.cybershare.elseweb.model.Element;
import edu.utep.cybershare.elseweb.util.StringManipulation;


public class Individuals {

	private static Hashtable<String, Individual> individuals = new Hashtable<String, Individual>();
	
	public static boolean doesIndividualExist(Element element, OntologyToolset bundle){
		String individualName = StringManipulation.makeURICompliantFragment(element.getIdentification(), bundle.getOntologyIRI());
		String individualIRI = bundle.getIndividualIRI(individualName);
		return individuals.get(individualIRI) != null;
	}
	
	public static Individual getIndividual(URI uri, OntologyToolset bundle){
		return getIndividual(uri.toASCIIString(), bundle);
	}
	
	public static Individual getIndividual(Element element, OntologyToolset bundle){
		String individualName = StringManipulation.makeURICompliantFragment(element.getIdentification(), bundle.getOntologyIRI());
		String individualIRI = bundle.getIndividualIRI(individualName);
		
		return getIndividual(individualIRI, bundle);
	}
	
	private static Individual getIndividual(String uri, OntologyToolset bundle){		
		Individual individual = individuals.get(uri);
		
		if(individual == null){
			individual = bundle.getOntModel().getIndividual(uri);
			individuals.put(uri, individual);
		}
		return individual;
	}
}

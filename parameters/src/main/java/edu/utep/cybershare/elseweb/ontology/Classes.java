package edu.utep.cybershare.elseweb.ontology;

import java.net.URI;
import java.util.Hashtable;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;

import edu.utep.cybershare.elseweb.model.Element;
import edu.utep.cybershare.elseweb.util.StringManipulation;

public class Classes {

	private static Hashtable<String, OWLClass> classes = new Hashtable<String, OWLClass>();
	
	public static boolean doesIndividualExist(Element element, OntologyToolset bundle){
		String className = StringManipulation.makeURICompliantFragment(element.getIdentification(), bundle.getBaseIRI());
		String classIRI = bundle.getIndividualIRI(className);
		return classes.get(classIRI) != null;
	}
	
	public static OWLClass getOWLClass(URI uri, OntologyToolset bundle){
		return getClass(uri.toASCIIString(), bundle);
	}
	
	public static OWLClass getOWLClass(Element element, OntologyToolset bundle, String postfix){
		String className = StringManipulation.makeURICompliantFragment(element.getIdentification(), bundle.getBaseIRI());
		String classIRI = bundle.getIndividualIRI(className);
		
		if(postfix != null)
			classIRI += "_" + postfix;
		
		return getClass(classIRI, bundle);
	}
	
	private static OWLClass getClass(String uri, OntologyToolset bundle){		
		OWLClass owlClass = classes.get(uri);
		
		if(owlClass == null){
			owlClass = bundle.getDataFactory().getOWLClass(IRI.create(uri));
			classes.put(uri, owlClass);
		}
		return owlClass;
	}
	                                                     
}

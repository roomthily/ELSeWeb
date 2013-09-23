package edu.utep.cybershare.elseweb.ontology;

import java.io.File;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Statement;

import edu.utep.cybershare.elseweb.ontology.axioms.Axioms;

public class OntologyToolset {

	private OntModel model;
	private String ontologyIRI;
	
	public OntologyToolset(String ontologyIRI){
		this.ontologyIRI = ontologyIRI;
		
		model = ModelFactory.createOntologyModel();
		model.getOntology(ontologyIRI);
	}
	
	
	public String getIndividualIRI(String individualName){
		return ontologyIRI + "#" + individualName;
	}
	
	public void addStatements(Axioms statements){
		for(Statement statement : statements){
			model.add(statement);
		}
	}
	
	public void setOntologyIRI(String ontologyIRI){
		this.ontologyIRI = ontologyIRI;
	}
	
	public String getOntologyIRI(){
		return this.ontologyIRI;
	}
	
	public OntModel getOntModel() {
		return model;
	}
	public void setOntModel(OntModel model) {
		this.model = model;
	}

	public void dumpOntology(File aFile){
	}		
}

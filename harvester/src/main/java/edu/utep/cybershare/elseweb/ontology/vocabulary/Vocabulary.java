package edu.utep.cybershare.elseweb.ontology.vocabulary;

import com.hp.hpl.jena.ontology.OntModel;

public abstract class Vocabulary {
	protected OntModel model;
		
	public Vocabulary(OntModel model){
		this.model = model;
	}
		
	public abstract String getNamespace();
}

package edu.utep.cybershare.elseweb.ontology.axioms;

import java.util.ArrayList;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.impl.StatementImpl;

import edu.utep.cybershare.elseweb.ontology.OntologyToolset;
import edu.utep.cybershare.elseweb.ontology.vocabulary.DCAT;
import edu.utep.cybershare.elseweb.ontology.vocabulary.DCMI;
import edu.utep.cybershare.elseweb.ontology.vocabulary.EDAC;
import edu.utep.cybershare.elseweb.ontology.vocabulary.OBOE;
import edu.utep.cybershare.elseweb.ontology.vocabulary.OBOEOntClassMapper;
import edu.utep.cybershare.elseweb.ontology.vocabulary.PROVO;

public abstract class Axioms extends ArrayList<StatementImpl> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected OntologyToolset bundle;	
	protected Individual individual;
	
	protected DCAT vocabulary_DCAT;
	protected DCMI vocabulary_DCMI;
	protected OBOE vocabulary_OBOE;
	protected PROVO vocabulary_PROVO;
	protected EDAC vocabulary_EDAC;
	
	protected OBOEOntClassMapper mapper;
	
	protected Axioms(Individual individual, OntologyToolset bundle){
		this.individual = individual;
		this.bundle = bundle;
		initializeVocabularies();
	}
	
	private void initializeVocabularies(){
		OntModel model = bundle.getOntModel();
		vocabulary_DCMI = new DCMI(model);
		vocabulary_PROVO = new PROVO(model);
		vocabulary_DCAT = new DCAT(model);
		vocabulary_OBOE = new OBOE(model);
		vocabulary_EDAC = new EDAC(model);
		
		mapper = new OBOEOntClassMapper(vocabulary_OBOE);
	}
		
	protected void setTypeAxiom(OntClass owlClass){
		Property typeProperty = bundle.getOntModel().getProperty("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");
		StatementImpl axiom = new StatementImpl(individual, typeProperty, owlClass);
		add(axiom);
	}	
	
	public abstract void setAxioms();


}

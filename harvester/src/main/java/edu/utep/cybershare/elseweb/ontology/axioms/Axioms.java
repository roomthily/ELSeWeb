package edu.utep.cybershare.elseweb.ontology.axioms;

import java.util.ArrayList;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLIndividual;

import edu.utep.cybershare.elseweb.ontology.OntologyToolset;
import edu.utep.cybershare.elseweb.ontology.vocabulary.DCAT;
import edu.utep.cybershare.elseweb.ontology.vocabulary.DCMI;
import edu.utep.cybershare.elseweb.ontology.vocabulary.EDAC;
import edu.utep.cybershare.elseweb.ontology.vocabulary.OBOE;
import edu.utep.cybershare.elseweb.ontology.vocabulary.OBOEOntClassMapper;
import edu.utep.cybershare.elseweb.ontology.vocabulary.PROVO;
import edu.utep.cybershare.elseweb.ontology.vocabulary.XSD;

public abstract class Axioms extends ArrayList<OWLAxiom> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected OntologyToolset bundle;	
	protected OWLIndividual individual;
	
	protected DCAT vocabulary_DCAT;
	protected DCMI vocabulary_DCMI;
	protected OBOE vocabulary_OBOE;
	protected PROVO vocabulary_PROVO;
	protected EDAC vocabulary_EDAC;
	protected XSD vocabulary_XSD;
	
	protected OBOEOntClassMapper mapper;
	
	protected Axioms(OWLIndividual individual, OntologyToolset bundle){
		this.individual = individual;
		this.bundle = bundle;
		initializeVocabularies();
	}
	
	private void initializeVocabularies(){
		vocabulary_DCMI = new DCMI(bundle);
		vocabulary_PROVO = new PROVO(bundle);
		vocabulary_DCAT = new DCAT(bundle);
		vocabulary_OBOE = new OBOE(bundle);
		vocabulary_EDAC = new EDAC(bundle);
		vocabulary_XSD = new XSD(bundle);
		mapper = new OBOEOntClassMapper(vocabulary_OBOE);
	}
		
	protected void addTypeAxiom(OWLClass owlClass){
		OWLClassAssertionAxiom classAssertionAxiom = bundle.getDataFactory().getOWLClassAssertionAxiom(owlClass, individual);
		add(classAssertionAxiom);
	}	
	
	public abstract void setAxioms();

}

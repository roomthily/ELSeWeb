package edu.utep.cybershare.elseweb.ontology.axioms;

import java.util.ArrayList;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;

import edu.utep.cybershare.elseweb.ontology.OntologyToolset;
import edu.utep.cybershare.elseweb.ontology.vocabulary.Lifemapper;


public abstract class ClassAxioms extends ArrayList<OWLAxiom> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected OntologyToolset bundle;	
	protected OWLClass owlClass;
	
	protected Lifemapper vocabulary_Lifemapper;
		
	protected ClassAxioms(OWLClass owlClass, OntologyToolset bundle){
		this.owlClass = owlClass;
		this.bundle = bundle;
		initializeVocabularies();
	}
	
	private void initializeVocabularies(){
		vocabulary_Lifemapper = new Lifemapper(bundle);
	}
	
	protected void addSubClassOfAxiom(OWLClass superClass){
		OWLSubClassOfAxiom axiom1 = bundle.getDataFactory().getOWLSubClassOfAxiom(owlClass, superClass);
		OWLSubClassOfAxiom axiom2 = bundle.getDataFactory().getOWLSubClassOfAxiom(superClass, vocabulary_Lifemapper.getOWLClass_AlgorithmParameter());
		
		add(axiom1);
		add(axiom2);
	}
	
	public abstract void setAxioms();
}

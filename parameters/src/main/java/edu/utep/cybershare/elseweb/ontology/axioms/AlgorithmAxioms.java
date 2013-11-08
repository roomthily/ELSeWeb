package edu.utep.cybershare.elseweb.ontology.axioms;


import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;

import edu.utep.cybershare.elseweb.model.Algorithm;
import edu.utep.cybershare.elseweb.ontology.OntologyToolset;

public class AlgorithmAxioms extends Axioms {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Algorithm algorithm;
	public AlgorithmAxioms(Algorithm algorithm, OWLIndividual individual, OntologyToolset bundle) {
		super(individual, bundle);
		// TODO Auto-generated constructor stub
		this.algorithm = algorithm;
	}

	@Override
	public void setAxioms() {
		// TODO Auto-generated method stub
		this.addTypeAxiom(vocabulary_Lifemapper.getOWLClass_ModelingAlgorithm());
		
		addCode();
		addName();
	}
	
	private void addCode(){
		if(algorithm.isSet_code()){
			OWLLiteral codeLiteral = bundle.getDataFactory().getOWLLiteral(algorithm.getCode());
			OWLAxiom axiom = bundle.getDataFactory().getOWLDataPropertyAssertionAxiom(vocabulary_Lifemapper.getDataProperty_hasAlgorithmCode(), individual, codeLiteral);
			add(axiom);
		}
	}

	private void addName(){
		if(algorithm.isSet_name()){
			OWLLiteral nameLiteral = bundle.getDataFactory().getOWLLiteral(algorithm.getName());
			OWLAxiom axiom = bundle.getDataFactory().getOWLDataPropertyAssertionAxiom(vocabulary_Lifemapper.getDataProperty_hasAlgorithmName(), individual, nameLiteral);
			add(axiom);
		}
	}	
}

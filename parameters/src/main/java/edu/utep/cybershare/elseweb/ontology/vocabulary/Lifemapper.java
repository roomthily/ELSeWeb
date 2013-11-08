package edu.utep.cybershare.elseweb.ontology.vocabulary;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLObjectProperty;

import edu.utep.cybershare.elseweb.ontology.OntologyToolset;


public class Lifemapper extends Vocabulary {
	
	private static final String NAMESPACE = "http://ontology.cybershare.utep.edu/ELSEWeb/lifemapper.owl";
	
	private static final String OWLClass_ModelingAlgorithm = NAMESPACE + "#ModelingAlgorithm";
	private static final String OWLClass_AlgorithmParameter = NAMESPACE + "#AlgorithmParameter";
	
	private static final String DataProperty_hasDefaultValue = NAMESPACE + "#hasDefaultValue";
	private static final String DataProperty_hasInputValue = NAMESPACE + "#hasInputValue";
	
	private static final String DataProperty_hasAlgorithmCode = NAMESPACE + "#hasAlgorithmCode";
	private static final String DataProperty_hasAlgorithmName = NAMESPACE + "#hasAlgorithmName";
	
	private static final String ObjectProperty_hasParameter = NAMESPACE + "#hasParameter";
	
	public Lifemapper(OntologyToolset bundle) {
		super(bundle);
		// TODO Auto-generated constructor stub
	}
	
	public OWLClass getOWLClass_ModelingAlgorithm(){return this.bundle.getDataFactory().getOWLClass(IRI.create(OWLClass_ModelingAlgorithm));}
	public OWLClass getOWLClass_AlgorithmParameter(){return this.bundle.getDataFactory().getOWLClass(IRI.create(OWLClass_AlgorithmParameter));}
	
	public OWLDataProperty getDataProperty_hasDefaultValue(){return this.bundle.getDataFactory().getOWLDataProperty(IRI.create(DataProperty_hasDefaultValue));}
	public OWLDataProperty getDataProperty_hasInputValue(){return this.bundle.getDataFactory().getOWLDataProperty(IRI.create(DataProperty_hasInputValue));}
	
	public OWLDataProperty getDataProperty_hasAlgorithmCode(){return this.bundle.getDataFactory().getOWLDataProperty(IRI.create(DataProperty_hasAlgorithmCode));}
	public OWLDataProperty getDataProperty_hasAlgorithmName(){return this.bundle.getDataFactory().getOWLDataProperty(IRI.create(DataProperty_hasAlgorithmName));}
	
	public OWLObjectProperty getObjectProperty_hasParameter(){return this.bundle.getDataFactory().getOWLObjectProperty(IRI.create(ObjectProperty_hasParameter));}	
	
	@Override
	public String getNamespace() {
		// TODO Auto-generated method stub
		return NAMESPACE;
	}

}

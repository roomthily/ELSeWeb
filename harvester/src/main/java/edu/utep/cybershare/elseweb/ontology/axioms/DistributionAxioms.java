package edu.utep.cybershare.elseweb.ontology.axioms;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLIndividual;

import edu.utep.cybershare.elseweb.model.Distribution;
import edu.utep.cybershare.elseweb.ontology.Individuals;
import edu.utep.cybershare.elseweb.ontology.OntologyToolset;

public class DistributionAxioms extends Axioms {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Distribution distribution;
	public DistributionAxioms(Distribution distribution, OWLIndividual individual, OntologyToolset bundle) {
		super(individual, bundle);
		// TODO Auto-generated constructor stub
		this.distribution = distribution;
	}

	@Override
	public void setAxioms() {
		// TODO Auto-generated method stub
		this.addTypeAxiom(vocabulary_DCAT.getOntClass_Distribution());
		addAccessURI();
		addDownloadURI();
		addFormat();
		//mediatype
	}
	private void addAccessURI(){
		if(distribution.isSet_accessURI()){
			OWLIndividual accessIndividual = Individuals.getIndividual(distribution.getAccessURI(), bundle);
			OWLAxiom axiom = bundle.getDataFactory().getOWLObjectPropertyAssertionAxiom(vocabulary_DCAT.getDatatypeProperty_accessURL(), individual, accessIndividual);
			add(axiom);
		}
	}
	private void addDownloadURI(){
		if(distribution.isSet_downloadURI()){
			OWLIndividual downloadIndividual = Individuals.getIndividual(distribution.getDownloadURI(), bundle);
			OWLAxiom axiom = bundle.getDataFactory().getOWLObjectPropertyAssertionAxiom(vocabulary_DCAT.getDatatypeProperty_downloadURL(), individual, downloadIndividual);
			add(axiom);
		}
	}
	private void addFormat(){
		if(distribution.isSet_format()){
			OWLIndividual formatIndividual = Individuals.getIndividual(distribution.getFormat(), bundle);
			OWLAxiom axiom = bundle.getDataFactory().getOWLObjectPropertyAssertionAxiom(vocabulary_DCMI.getObjectProperty_format(), individual, formatIndividual);
			add(axiom);
		}
	}
}
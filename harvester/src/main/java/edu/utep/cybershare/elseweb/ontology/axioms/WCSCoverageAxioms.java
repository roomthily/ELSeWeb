package edu.utep.cybershare.elseweb.ontology.axioms;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLIndividual;

import edu.utep.cybershare.elseweb.model.WCSCoverage;
import edu.utep.cybershare.elseweb.ontology.Individuals;
import edu.utep.cybershare.elseweb.ontology.OntologyToolset;

public class WCSCoverageAxioms extends Axioms{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private WCSCoverage dataset;
	public WCSCoverageAxioms(WCSCoverage dataset, OWLIndividual individual, OntologyToolset bundle) {
		super(individual, bundle);
		// TODO Auto-generated constructor stub
		this.dataset = dataset;
	}

	@Override
	public void setAxioms() {
		// TODO Auto-generated method stub
		this.addTypeAxiom(this.vocabulary_EDAC.getOWLClass_WCSCoverage());
		addDistribution();
		addDuration();
		addMeasurement();
		addRegion();
	}
	
	private void addDistribution(){
		if(dataset.isSet_distribution()){
			OWLIndividual distributionIndividual = Individuals.getIndividual(dataset.getDistribution(), bundle);
			OWLAxiom axiom = bundle.getDataFactory().getOWLObjectPropertyAssertionAxiom(vocabulary_DCAT.getObjectProperty_distribution(), individual, distributionIndividual);
			add(axiom);
		}
	}
	private void addDuration(){
		if(dataset.isSet_duration()){
			OWLIndividual durationIndividual = Individuals.getIndividual(dataset.getDuration(), bundle);
			OWLAxiom axiom = bundle.getDataFactory().getOWLObjectPropertyAssertionAxiom(vocabulary_DCMI.getObjectProperty_temporal(), individual, durationIndividual);
			add(axiom);
		}
	}
	private void addMeasurement(){
		if(dataset.isSet_measurement()){
			OWLIndividual measurementIndividual = Individuals.getIndividual(dataset.getMeasurement(), bundle);
			OWLAxiom axiom = bundle.getDataFactory().getOWLObjectPropertyAssertionAxiom(vocabulary_PROVO.getObjectProperty_wasGeneratedBy(), individual, measurementIndividual);
			add(axiom);
		}
	}
	private void addRegion(){
		if(dataset.isSet_region()){
			OWLIndividual regionIndividual = Individuals.getIndividual(dataset.getRegion(), bundle);
			OWLAxiom axiom = bundle.getDataFactory().getOWLObjectPropertyAssertionAxiom(vocabulary_DCMI.getObjectProperty_spatial(), individual, regionIndividual);
			add(axiom);
		}
	}
}

package edu.utep.cybershare.elseweb.ontology.axioms;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.rdf.model.impl.StatementImpl;

import edu.utep.cybershare.elseweb.model.Dataset;
import edu.utep.cybershare.elseweb.ontology.Individuals;
import edu.utep.cybershare.elseweb.ontology.OntologyToolset;

public class DatasetAxioms extends Axioms{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Dataset dataset;
	public DatasetAxioms(Dataset dataset, Individual individual, OntologyToolset bundle) {
		super(individual, bundle);
		// TODO Auto-generated constructor stub
		this.dataset = dataset;
	}

	@Override
	public void setAxioms() {
		// TODO Auto-generated method stub
		this.addTypeAxiom(this.vocabulary_EDAC.getOntClass_WCSCoverage());
		addDistribution();
		addDuration();
		addMeasurement();
		addRegion();
	}
	
	private void addDistribution(){
		if(dataset.isSet_distribution()){
			Individual distributionIndividual = Individuals.getIndividual(dataset.getDistribution(), bundle);
			StatementImpl axiom = new StatementImpl(individual, vocabulary_DCAT.getObjectProperty_distribution(), distributionIndividual);
			add(axiom);
		}
	}
	private void addDuration(){
		if(dataset.isSet_duration()){
			Individual durationIndividual = Individuals.getIndividual(dataset.getDuration(), bundle);
			StatementImpl axiom = new StatementImpl(individual, vocabulary_DCMI.getObjectProperty_temporal(), durationIndividual);
			add(axiom);
		}
	}
	private void addMeasurement(){
		if(dataset.isSet_measurement()){
			Individual measurementIndividual = Individuals.getIndividual(dataset.getMeasurement(), bundle);
			StatementImpl axiom = new StatementImpl(individual, vocabulary_PROVO.getObjectProperty_wasGeneratedBy(), measurementIndividual);
			add(axiom);
		}
	}
	private void addRegion(){
		if(dataset.isSet_region()){
			Individual regionIndividual = Individuals.getIndividual(dataset.getRegion(), bundle);
			StatementImpl axiom = new StatementImpl(individual, vocabulary_DCMI.getObjectProperty_spatial(), regionIndividual);
			add(axiom);
		}
	}
}

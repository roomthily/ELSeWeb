package edu.utep.cybershare.elseweb.ontology.axioms;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.rdf.model.impl.StatementImpl;

import edu.utep.cybershare.elseweb.model.Measurement;
import edu.utep.cybershare.elseweb.ontology.Individuals;
import edu.utep.cybershare.elseweb.ontology.OntologyToolset;

public class MeasurementAxioms extends Axioms{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Measurement measurement;
	public MeasurementAxioms(Measurement measurement, Individual individual, OntologyToolset bundle) {
		super(individual, bundle);
		// TODO Auto-generated constructor stub
		this.measurement = measurement;
	}

	@Override
	public void setAxioms() {
		// TODO Auto-generated method stub
		this.addTypeAxiom(vocabulary_OBOE.getOWLClass_Measurement());
		this.addTypeAxiom(vocabulary_PROVO.getOntClass_Activity());
		
		addCharacteristic();
		addObservation();
	}
	
	private void addCharacteristic(){
		if(measurement.isSet_charactersitc()){
			Individual charIndividual = Individuals.getIndividual(measurement.getCharacteristic(), bundle);
			StatementImpl axiom = new StatementImpl(individual, vocabulary_OBOE.getObjectProperty_ofCharacteristic(), charIndividual);
			add(axiom);
		}
	}
	
	private void addObservation(){
		if(measurement.isSet_Observation()){
			Individual observationIndividual = Individuals.getIndividual(measurement.getObservation(), bundle);
			StatementImpl axiom = new StatementImpl(individual, vocabulary_OBOE.getObjectProperty_measurementFor(), observationIndividual);
			add(axiom);
		}
	}
}

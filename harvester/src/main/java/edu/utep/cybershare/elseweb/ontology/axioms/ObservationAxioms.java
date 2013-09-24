package edu.utep.cybershare.elseweb.ontology.axioms;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.rdf.model.impl.StatementImpl;

import edu.utep.cybershare.elseweb.model.Observation;
import edu.utep.cybershare.elseweb.ontology.Individuals;
import edu.utep.cybershare.elseweb.ontology.OntologyToolset;

public class ObservationAxioms extends Axioms {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Observation obs;
	protected ObservationAxioms(Observation obs, Individual individual, OntologyToolset bundle) {
		super(individual, bundle);
		// TODO Auto-generated constructor stub
		this.obs = obs;
	}

	@Override
	public void setAxioms() {
		// TODO Auto-generated method stub
		
		this.addTypeAxiom(vocabulary_OBOE.getOWLClass_Observation());
		
		addEntity();
	}
	
	private void addEntity(){
		if(obs.isSet_entity()){
			Individual entityIndividual = Individuals.getIndividual(obs.getEntity(), bundle);
			StatementImpl axiom = new StatementImpl(individual, vocabulary_OBOE.getObjectProperty_ofEntity(), entityIndividual);
			add(axiom);
		}
	}

}

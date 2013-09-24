package edu.utep.cybershare.elseweb.ontology.axioms;

import com.hp.hpl.jena.ontology.Individual;

import edu.utep.cybershare.elseweb.model.Entity;
import edu.utep.cybershare.elseweb.ontology.OntologyToolset;

public class EntityAxioms extends Axioms {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Entity entity;
	public EntityAxioms(Entity entity, Individual individual, OntologyToolset bundle) {
		super(individual, bundle);
		// TODO Auto-generated constructor stub
		this.entity = entity;
	}

	@Override
	public void setAxioms() {
		// TODO Auto-generated method stub
		this.addTypeAxiom(vocabulary_OBOE.getOWLClass_Entity());
		this.addTypeAxiom(mapper.getEntityOntClass(entity.getThemekey()));
	}
}

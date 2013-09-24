package edu.utep.cybershare.elseweb.ontology.axioms;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.impl.StatementImpl;

import edu.utep.cybershare.elseweb.model.Region;
import edu.utep.cybershare.elseweb.ontology.OntologyToolset;

public class RegionAxioms extends Axioms {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Region region;
	protected RegionAxioms(Region region, Individual individual, OntologyToolset bundle) {
		super(individual, bundle);
		// TODO Auto-generated constructor stub
		this.region = region;
	}

	@Override
	public void setAxioms() {
		// TODO Auto-generated method stub
		this.addTypeAxiom(vocabulary_EDAC.getOntClass_Region());
		
		addLlon();
		addRlon();
		addLlat();
		addUlat();
	}
	
	private void addLlon(){
		if(region.isSet_llon()){
			Literal llonLiteral = bundle.getOntModel().createTypedLiteral(region.getLlon());
			StatementImpl axiom = new StatementImpl(individual, vocabulary_EDAC.getDatatypeProperty_hasLeftLongitude(), llonLiteral);
			add(axiom);
		}
	}
	
	private void addRlon(){
		if(region.isSet_rlon()){
			Literal rlonLiteral = bundle.getOntModel().createTypedLiteral(region.getRlon());
			StatementImpl axiom = new StatementImpl(individual, vocabulary_EDAC.getDatatypeProperty_hasRightLongitude(), rlonLiteral);
			add(axiom);
		}
	}
	
	private void addLlat(){
		if(region.isSet_llat()){
			Literal llatLiteral = bundle.getOntModel().createTypedLiteral(region.getLlat());
			StatementImpl axiom = new StatementImpl(individual, vocabulary_EDAC.getDatatypeProperty_hasLowerLatitude(), llatLiteral);
			add(axiom);
		}
	}
	private void addUlat(){
		if(region.isSet_ulat()){
			Literal ulatLiteral = bundle.getOntModel().createTypedLiteral(region.getUlat());
			StatementImpl axiom = new StatementImpl(individual, vocabulary_EDAC.getDatatypeProperty_hasUpperLatitude(), ulatLiteral);
			add(axiom);
		}
	}
}

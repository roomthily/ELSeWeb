package edu.utep.cybershare.elseweb.ontology.axioms;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.impl.StatementImpl;

import edu.utep.cybershare.elseweb.model.Distribution;
import edu.utep.cybershare.elseweb.ontology.Individuals;
import edu.utep.cybershare.elseweb.ontology.OntologyToolset;

public class DistributionAxioms extends Axioms {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Distribution distribution;
	protected DistributionAxioms(Distribution distribution, Individual individual, OntologyToolset bundle) {
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
			Individual accessIndividual = Individuals.getIndividual(distribution.getAccessURI(), bundle);
			StatementImpl axiom = new StatementImpl(individual, vocabulary_DCAT.getDatatypeProperty_accessURL(), accessIndividual);
			add(axiom);
		}
	}
	private void addDownloadURI(){
		if(distribution.isSet_downloadURI()){
			Individual downloadIndividual = Individuals.getIndividual(distribution.getDownloadURI(), bundle);
			StatementImpl axiom = new StatementImpl(individual, vocabulary_DCAT.getDatatypeProperty_downloadURL(), downloadIndividual);
			add(axiom);
		}
	}
	private void addFormat(){
		if(distribution.isSet_format()){
			Individual formatIndividual = Individuals.getIndividual(distribution.getFormat(), bundle);
			StatementImpl axiom = new StatementImpl(individual, vocabulary_DCMI.getObjectProperty_format(), formatIndividual);
			add(axiom);
		}
	}
}

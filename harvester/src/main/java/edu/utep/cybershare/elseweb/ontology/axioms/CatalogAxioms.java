package edu.utep.cybershare.elseweb.ontology.axioms;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLIndividual;

import edu.utep.cybershare.elseweb.model.Catalog;
import edu.utep.cybershare.elseweb.model.Dataset;
import edu.utep.cybershare.elseweb.ontology.Individuals;
import edu.utep.cybershare.elseweb.ontology.OntologyToolset;

public class CatalogAxioms extends Axioms {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Catalog catalog;
	
	public CatalogAxioms(Catalog catalog, OWLIndividual individual, OntologyToolset bundle) {
		super(individual, bundle);
		// TODO Auto-generated constructor stub
		this.catalog = catalog;
	}

	@Override
	public void setAxioms() {
		// TODO Auto-generated method stub
		this.addTypeAxiom(vocabulary_DCAT.getOntClass_Catalog());
		addDatasets();
	}
	
	private void addDatasets(){
		OWLIndividual datasetIndividual;
		OWLAxiom axiom;
		for(Dataset dataset : catalog.getDatasets()){
			datasetIndividual = Individuals.getIndividual(dataset, bundle);
			axiom = bundle.getDataFactory().getOWLObjectPropertyAssertionAxiom(vocabulary_DCAT.getObjectProperty_dataset(), individual, datasetIndividual);
			add(axiom);
		}
	}
}

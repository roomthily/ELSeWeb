package edu.utep.cybershare.elseweb.prov.namedGraph;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;

import edu.utep.cybershare.elseweb.prov.ModelUtils;

public class NamedGraphComponent {
	
	private OntClass necessaryClass;
	private NamedGraph encapsulatingGraph;
	private Individual necessaryIndividual;
	
	public NamedGraphComponent(OntClass necessaryClass, NamedGraph encapsulatingGraph){
		this.necessaryClass = necessaryClass;
		this.encapsulatingGraph = encapsulatingGraph;
		
		setNecessaryIndividuals();
	}
	
	private void setNecessaryIndividuals(){
		OntModel queryingModel = ModelUtils.getEmptyReasoningModel();
		queryingModel.add(encapsulatingGraph.getContents().getModel());

		ExtendedIterator<Individual> individuals = queryingModel.listIndividuals(necessaryClass);
		if(individuals.hasNext()){
			necessaryIndividual = individuals.next();
		}
	}
	
	public OntClass getOntClass(){return necessaryClass;}
	public Individual getClassIndividual(){return necessaryIndividual;}
		
}

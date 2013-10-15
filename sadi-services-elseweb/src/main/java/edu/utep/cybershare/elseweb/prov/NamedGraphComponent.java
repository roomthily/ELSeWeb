package edu.utep.cybershare.elseweb.prov;

import java.util.ArrayList;
import java.util.List;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;

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

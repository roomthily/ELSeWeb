package edu.utep.cybershare.elseweb.ontology;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLIndividual;

import edu.utep.cybershare.elseweb.model.Algorithm;
import edu.utep.cybershare.elseweb.model.Parameter;
import edu.utep.cybershare.elseweb.model.Visitor;
import edu.utep.cybershare.elseweb.ontology.axioms.ParameterClassAxioms;

import edu.utep.cybershare.elseweb.ontology.axioms.AlgorithmAxioms;

public class OWLVisitor implements Visitor{

	private OntologyToolset bundle;
	
	public OWLVisitor(OntologyToolset bundle){
		this.bundle = bundle;
	}
	
	public void visit(Algorithm algorithm) {
		// TODO Auto-generated method stub
		OWLIndividual individual = Individuals.getIndividual(algorithm, bundle);
		AlgorithmAxioms algorithmAxioms = new AlgorithmAxioms(algorithm, individual, bundle);
		algorithmAxioms.setAxioms();
		
		bundle.addAxioms(algorithmAxioms);
		
		ParameterClassAxioms parameterClassAxioms;
		
		OWLClass groupingClass;
		OWLClass parameterClass;
		
		for(Parameter param : algorithm.getParameters()){
			groupingClass = Classes.getOWLClass(algorithm, bundle, "parameter");
			parameterClass = Classes.getOWLClass(param, bundle, null);
			parameterClassAxioms = new ParameterClassAxioms(groupingClass, parameterClass, param, bundle);
			parameterClassAxioms.setAxioms();
			
			bundle.addAxioms(parameterClassAxioms);
		}		
	}
}

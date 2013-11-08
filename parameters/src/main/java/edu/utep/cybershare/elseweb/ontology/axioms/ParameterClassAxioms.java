package edu.utep.cybershare.elseweb.ontology.axioms;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataExactCardinality;
import org.semanticweb.owlapi.model.OWLDataHasValue;
import org.semanticweb.owlapi.model.OWLDatatypeRestriction;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLLiteral;
import edu.utep.cybershare.elseweb.model.Parameter;
import edu.utep.cybershare.elseweb.ontology.OntologyToolset;

public class ParameterClassAxioms extends ClassAxioms {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Parameter parameter;
	private OWLClass parameterClass;
	private OWLClass algorithmClass;
	
	public ParameterClassAxioms(OWLClass algorithmClass, OWLClass parameterClass, Parameter parameter, OntologyToolset bundle) {
		super(parameterClass, bundle);
		// TODO Auto-generated constructor stub
		this.parameter = parameter;
		this.algorithmClass = algorithmClass;
		this.parameterClass = parameterClass;
	}

	@Override
	public void setAxioms() {
		// TODO Auto-generated method stub
		this.addSubClassOfAxiom(algorithmClass);

		addEquivalentClass();
	}
	
	private void addEquivalentClass(){
		
		OWLDataExactCardinality exactCardinality = null;
		OWLDatatypeRestriction intervalRestriction = null;
				
		if(parameter.isSet_min() && parameter.isSet_max()){
			if(parameter.isSet_type() && parameter.getType().equals(Parameter.Integer))
				intervalRestriction = bundle.getDataFactory().getOWLDatatypeMinMaxInclusiveRestriction(parameter.getIntegerMin(), parameter.getIntegerMax());
			else
				intervalRestriction = bundle.getDataFactory().getOWLDatatypeMinMaxInclusiveRestriction(parameter.getMin(), parameter.getMax());				
		}
		else if(parameter.isSet_min() && !parameter.isSet_max()){
			if(parameter.isSet_type() && parameter.getType().equals(Parameter.Integer))
				intervalRestriction = bundle.getDataFactory().getOWLDatatypeMinInclusiveRestriction(parameter.getIntegerMin());
			else
				intervalRestriction = bundle.getDataFactory().getOWLDatatypeMinInclusiveRestriction(parameter.getMin());
		}
		else if(!parameter.isSet_min() && parameter.isSet_max()){
			if(parameter.isSet_type() && parameter.getType().equals(Parameter.Integer))
				intervalRestriction = bundle.getDataFactory().getOWLDatatypeMinInclusiveRestriction(parameter.getIntegerMax());
			else
				intervalRestriction = bundle.getDataFactory().getOWLDatatypeMinInclusiveRestriction(parameter.getMax());
		}
		
		if(intervalRestriction != null){
			exactCardinality = bundle.getDataFactory().getOWLDataExactCardinality(1, vocabulary_Lifemapper.getDataProperty_hasInputValue(), intervalRestriction);
			
			OWLEquivalentClassesAxiom equivalentClassesAxiom = bundle.getDataFactory().getOWLEquivalentClassesAxiom(parameterClass, exactCardinality);
			add(equivalentClassesAxiom);
		}
		
		//add default value
		if(parameter.isSet_defaultValue()){
			OWLLiteral defaultValueLiteral = bundle.getDataFactory().getOWLLiteral(parameter.getDefaultValue());
			OWLDataHasValue hasValue = bundle.getDataFactory().getOWLDataHasValue(vocabulary_Lifemapper.getDataProperty_hasDefaultValue(), defaultValueLiteral);

			OWLEquivalentClassesAxiom equivalentClassesAxiom = bundle.getDataFactory().getOWLEquivalentClassesAxiom(parameterClass, hasValue);
			add(equivalentClassesAxiom);
		}
	}
}

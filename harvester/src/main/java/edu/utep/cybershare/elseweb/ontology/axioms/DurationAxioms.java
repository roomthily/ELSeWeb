package edu.utep.cybershare.elseweb.ontology.axioms;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.impl.StatementImpl;

import edu.utep.cybershare.elseweb.model.Duration;
import edu.utep.cybershare.elseweb.ontology.OntologyToolset;

@SuppressWarnings("serial")
public class DurationAxioms extends Axioms{

	private Duration duration;
	public DurationAxioms(Duration duration, Individual individual, OntologyToolset bundle) {
		super(individual, bundle);
		// TODO Auto-generated constructor stub
		this.duration = duration;
	}

	@Override
	public void setAxioms() {
		// TODO Auto-generated method stub
		this.addTypeAxiom(this.vocabulary_EDAC.getOntClass_Duration());
		addEndDate();
		addStartDate();
	}

	private void addEndDate(){
		if(duration.isSet_endDate()){
			Literal endDateLiteral = bundle.getOntModel().createTypedLiteral(duration.getEndDate());
			StatementImpl axiom = new StatementImpl(individual, vocabulary_EDAC.getDatatypeProperty_hasEndDate(), endDateLiteral);
			add(axiom);
		}
	}
	
	private void addStartDate(){
		if(duration.isSet_startDate()){
			Literal startDateLiteral = bundle.getOntModel().createTypedLiteral(duration.getStartDate());
			StatementImpl axiom = new StatementImpl(individual, vocabulary_EDAC.getDatatypeProperty_hasStartDate(), startDateLiteral);
			add(axiom);
		}
	}
}

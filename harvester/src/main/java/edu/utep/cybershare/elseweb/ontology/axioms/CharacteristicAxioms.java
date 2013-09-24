package edu.utep.cybershare.elseweb.ontology.axioms;

import com.hp.hpl.jena.ontology.Individual;

import edu.utep.cybershare.elseweb.model.Characteristic;
import edu.utep.cybershare.elseweb.ontology.OntologyToolset;

public class CharacteristicAxioms extends Axioms {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Characteristic characteristic;
	
	public CharacteristicAxioms(Characteristic characteristic, Individual individual, OntologyToolset bundle) {
		super(individual, bundle);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setAxioms() {
		// TODO Auto-generated method stub
		this.addTypeAxiom(vocabulary_OBOE.getOWLClass_Characteristic());
		
		if(characteristic.isSet_themekey()){
			this.addTypeAxiom(mapper.getCharacteristicOntClass(characteristic.getThemekey()));
		}
	}
}

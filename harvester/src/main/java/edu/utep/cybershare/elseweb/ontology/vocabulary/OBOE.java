package edu.utep.cybershare.elseweb.ontology.vocabulary;

import com.hp.hpl.jena.ontology.ObjectProperty;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;


/**
 * Observation Ontology for Environmental Science (OBOE)
 * <a href="https://semtools.ecoinformatics.org/oboe">https://semtools.ecoinformatics.org/oboe</a>
 * @author Nicholas Del Rio
 *
 */

public class OBOE extends Vocabulary {
	
	private static final String NAMESPACE = "http://ecoinformatics.org/oboe/oboe.1.0/oboe-core.owl";

	private static final String OntClass_Observation = NAMESPACE + "#Observation";
	private static final String OntClass_Measurement = NAMESPACE + "#Measurement";
	private static final String OntClass_Entity = NAMESPACE + "#Entity";
	private static final String OntClass_Characteristic = NAMESPACE + "#Characteristic";
	
	private static final String OntClass_Temperature = "http://ecoinformatics.org/oboe/oboe.1.0/oboe-characteristics.owl#Temperature";
	private static final String OntClass_Amount = "http://ecoinformatics.org/oboe/oboe.1.0/oboe-characteristics.owl#Amount";
	private static final String OntClass_EcologicalCommunity = "http://ecoinformatics.org/oboe/oboe.1.0/oboe-ecology.owl#EcologicalCommunity";
	private static final String OntClass_TerrestrialFeature = "http://ecoinformatics.org/oboe/oboe.1.0/oboe-environment.owl#TerrestrialFeature";
	private static final String OntClass_AtmosphericFeature = "http://ecoinformatics.org/oboe/oboe.1.0/oboe-environment.owl#AtmosphericFeature";

	
	private static final String ObjectProperty_measurementFor = NAMESPACE + "#measurementFor";
	private static final String ObjectProperty_ofEntity = NAMESPACE + "#ofEntity";
	private static final String ObjectProperty_ofCharacteristic = NAMESPACE + "#ofCharacteristic";
	
	public OBOE(OntModel model) {
		super(model);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getNamespace() {
		// TODO Auto-generated method stub
		return NAMESPACE;
	}
	
	public OntClass getOWLClass_Observation(){return this.model.getOntClass(OntClass_Observation);}
	public OntClass getOWLClass_Measurement(){return this.model.getOntClass(OntClass_Measurement);}
	public OntClass getOWLClass_Entity(){return this.model.getOntClass(OntClass_Entity);}
	public OntClass getOWLClass_Characteristic(){return this.model.getOntClass(OntClass_Characteristic);}
	public OntClass getOWLClass_Temperature(){return this.model.getOntClass(OntClass_Temperature);}
	public OntClass getOWLClass_Amount(){return this.model.getOntClass(OntClass_Amount);}
	public OntClass getOWLClass_EcologicalCommunity(){return this.model.getOntClass(OntClass_EcologicalCommunity);}
	public OntClass getOWLClass_TerrestrialFeature(){return this.model.getOntClass(OntClass_TerrestrialFeature);}
	public OntClass getOWLClass_AtmosphericFeature(){return this.model.getOntClass(OntClass_AtmosphericFeature);}
	
	public ObjectProperty getObjectProperty_measurementFor(){return this.model.getObjectProperty(ObjectProperty_measurementFor);}
	public ObjectProperty getObjectProperty_ofEntity(){return this.model.getObjectProperty(ObjectProperty_ofEntity);}
	public ObjectProperty getObjectProperty_ofCharacteristic(){return this.model.getObjectProperty(ObjectProperty_ofCharacteristic);}
}

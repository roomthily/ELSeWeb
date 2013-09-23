package edu.utep.cybershare.elseweb.ontology.vocabulary;

import com.hp.hpl.jena.ontology.DatatypeProperty;
import com.hp.hpl.jena.ontology.ObjectProperty;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;

public class EDAC extends Vocabulary {
	
	private static final String NAMESPACE = "http://ontology.cybershare.utep.edu/ELSEWeb/edac.owl";
	
	private static final String OntClass_WCSCoverageSet = NAMESPACE + "#WCSCoverageSet";
	private static final String OntClass_WCSCoverage = NAMESPACE + "#WCSCoverage";
	private static final String OntClass_Region = NAMESPACE + "#Region";
	private static final String OntClass_Duration = NAMESPACE + "#Duration";
	private static final String OntClass_Method = NAMESPACE + "#Method";
	private static final String OntClass_Sensor = NAMESPACE + "#Sensor";
	
	private static final String ObjectProperty_hasWCSCoverage = NAMESPACE + "#hasWCSCoverage";

	private static final String DatatypeProperty_hasStartDate = NAMESPACE + "#hasStartDate";
	private static final String DatatypeProperty_hasEndDate = NAMESPACE + "#hasEndDate";
	private static final String DatatypeProperty_hasLeftLongitude = NAMESPACE + "#hasLeftLongitude";
	private static final String DatatypeProperty_hasRightLongitude = NAMESPACE + "#hasRightLongitude";
	private static final String DatatypeProperty_hasLowerLatitude = NAMESPACE + "#hasLowerLatitude";
	private static final String DatatypeProperty_hasUpplerLatitude = NAMESPACE + "#hasUpperLatitude";

	public EDAC(OntModel model) {
		super(model);
		// TODO Auto-generated constructor stub
	}
	
	public OntClass getOntClass_WCSCoverageSet(){return this.model.getOntClass(OntClass_WCSCoverageSet);}
	public OntClass getOntClass_WCSCoverage(){return this.model.getOntClass(OntClass_WCSCoverage);}
	public OntClass getOntClass_Region(){return this.model.getOntClass(OntClass_Region);}
	public OntClass getOntClass_Duration(){return this.model.getOntClass(OntClass_Duration);}
	public OntClass getOntClass_Method(){return this.model.getOntClass(OntClass_Method);}
	public OntClass getOntClass_Sensor(){return this.model.getOntClass(OntClass_Sensor);}

	public ObjectProperty getObjectProperty_hasWCSCoverage(){return this.model.getObjectProperty(ObjectProperty_hasWCSCoverage);}
	
	public DatatypeProperty getDatatypeProperty_hasStartDate(){return this.model.getDatatypeProperty(DatatypeProperty_hasStartDate);}
	public DatatypeProperty getDatatypeProperty_hasEndDate(){return this.model.getDatatypeProperty(DatatypeProperty_hasEndDate);}
	public DatatypeProperty getDatatypeProperty_hasLeftLongitude(){return this.model.getDatatypeProperty(DatatypeProperty_hasLeftLongitude);}
	public DatatypeProperty getDatatypeProperty_hasRightLongitude(){return this.model.getDatatypeProperty(DatatypeProperty_hasRightLongitude);}
	public DatatypeProperty getDatatypeProperty_hasLowerLatitude(){return this.model.getDatatypeProperty(DatatypeProperty_hasLowerLatitude);}
	public DatatypeProperty getDatatypeProperty_hasUpperLatitude(){return this.model.getDatatypeProperty(DatatypeProperty_hasUpplerLatitude);}
	
	@Override
	public String getNamespace() {
		// TODO Auto-generated method stub
		return NAMESPACE;
	}

}

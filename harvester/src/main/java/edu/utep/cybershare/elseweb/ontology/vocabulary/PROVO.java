package edu.utep.cybershare.elseweb.ontology.vocabulary;

import com.hp.hpl.jena.ontology.ObjectProperty;
import com.hp.hpl.jena.ontology.OntModel;



public class PROVO extends Vocabulary{

	private static final String NAMESPACE = "http://www.w3.org/ns/prov";
	
	private static final String ObjectProperty_wasGeneratedBy = NAMESPACE + "#wasGeneratedBy";

	
	public PROVO(OntModel model) {
		super(model);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getNamespace() {
		// TODO Auto-generated method stub
		return NAMESPACE;
	}
	
	public ObjectProperty getObjectProperty_wasGeneratedBy(){return this.model.getObjectProperty(ObjectProperty_wasGeneratedBy);}	

}

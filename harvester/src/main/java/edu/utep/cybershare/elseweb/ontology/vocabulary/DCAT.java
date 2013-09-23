package edu.utep.cybershare.elseweb.ontology.vocabulary;

import com.hp.hpl.jena.ontology.DatatypeProperty;
import com.hp.hpl.jena.ontology.ObjectProperty;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;

/**
 * Data Catalog (DCAT)
 * <a href="http://www.w3.org/TR/vocab-dcat/">http://www.w3.org/TR/vocab-dcat/</a>
 * @author Nicholas Del Rio
 *
 */

public class DCAT extends Vocabulary{
	
	private static final String NAMESPACE = "http://www.w3.org/ns/dcat";
	
	private static final String OntClass_Distribution = NAMESPACE + "/Distribution";
	private static final String OntClass_Catalog = NAMESPACE + "/Catalog";
	
	private static final String ObjectProperty_distribution = NAMESPACE + "/distribution";
	private static final String ObjectProperty_dataset = NAMESPACE + "/dataset";
	
	private static final String DatatypeProperty_accessURL = NAMESPACE + "/accessURL";
	private static final String DatatypeProperty_downloadURL = NAMESPACE + "/downloadURL";

	public DCAT(OntModel model) {
		super(model);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getNamespace() {
		// TODO Auto-generated method stub
		return NAMESPACE;
	}

	public OntClass getOntClass_Distribution(){return this.model.getOntClass(OntClass_Distribution);}
	public OntClass getOntClass_Catalog(){return this.model.getOntClass(OntClass_Catalog);}

	public ObjectProperty getObjectProperty_distribution(){return this.model.getObjectProperty(ObjectProperty_distribution);}
	public ObjectProperty getObjectProperty_dataset(){return this.model.getObjectProperty(ObjectProperty_dataset);}

	public DatatypeProperty getDatatypeProperty_accessURL(){return this.model.getDatatypeProperty(DatatypeProperty_accessURL);}
	public DatatypeProperty getDatatypeProperty_downloadURL(){return this.model.getDatatypeProperty(DatatypeProperty_downloadURL);}

}

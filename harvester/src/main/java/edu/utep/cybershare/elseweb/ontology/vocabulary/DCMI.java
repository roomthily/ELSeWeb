package edu.utep.cybershare.elseweb.ontology.vocabulary;

import com.hp.hpl.jena.ontology.ObjectProperty;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;

/**
 * Dublin Core Metadata Initiative (DCMI)
 * <a href="http://dublincore.org/">http://dublincore.org/</a>
 * @author Nicholas Del Rio
 *
 */

public class DCMI extends Vocabulary{

	private static final String NAMESPACE = "http://purl.org/dc/terms";
	
	private static final String OntClass_FileFormat = NAMESPACE + "/FileFormat";
	
	private static final String ObjectProperty_format = NAMESPACE + "/format";
	private static final String ObjectProperty_spatial = NAMESPACE + "/spatial";
	private static final String ObjectProperty_temporal = NAMESPACE + "/temporal";
	
	
	public DCMI(OntModel model) {
		super(model);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getNamespace() {
		// TODO Auto-generated method stub
		return NAMESPACE;
	}
	
	public OntClass getOntClass_FileFormat(){return this.model.getOntClass(OntClass_FileFormat);}
	
	public ObjectProperty getObjectProperty_format(){return this.model.getObjectProperty(ObjectProperty_format);}
	public ObjectProperty getObjectProperty_spatial(){return this.model.getObjectProperty(ObjectProperty_spatial);}
	public ObjectProperty getObjectProperty_temporal(){return this.model.getObjectProperty(ObjectProperty_temporal);}
}

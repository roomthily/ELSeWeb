package edu.utep.cybershare.elseweb.ontology.vocabulary;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLObjectProperty;

import edu.utep.cybershare.elseweb.ontology.OntologyToolset;


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
	
	private static final String DatatypeProperty_accessURL = NAMESPACE + "#accessURL";
	private static final String DatatypeProperty_downloadURL = NAMESPACE + "#downloadURL";

	public DCAT(OntologyToolset bundle) {
		super(bundle);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getNamespace() {
		// TODO Auto-generated method stub
		return NAMESPACE;
	}

	public OWLClass getOntClass_Distribution(){return this.bundle.getDataFactory().getOWLClass(IRI.create(OntClass_Distribution));}
	public OWLClass getOntClass_Catalog(){return this.bundle.getDataFactory().getOWLClass(IRI.create(OntClass_Catalog));}

	public OWLObjectProperty getObjectProperty_distribution(){return this.bundle.getDataFactory().getOWLObjectProperty(IRI.create(ObjectProperty_distribution));}
	public OWLObjectProperty getObjectProperty_dataset(){return this.bundle.getDataFactory().getOWLObjectProperty(IRI.create(ObjectProperty_dataset));}
	public OWLObjectProperty getDatatypeProperty_accessURL(){return this.bundle.getDataFactory().getOWLObjectProperty(IRI.create(DatatypeProperty_accessURL));}
	public OWLObjectProperty getDatatypeProperty_downloadURL(){return this.bundle.getDataFactory().getOWLObjectProperty(IRI.create(DatatypeProperty_downloadURL));}
}

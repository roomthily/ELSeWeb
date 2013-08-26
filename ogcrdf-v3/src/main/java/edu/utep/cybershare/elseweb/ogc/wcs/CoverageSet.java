package edu.utep.cybershare.elseweb.ogc.wcs;

import java.io.File;
import java.io.FileWriter;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.Ontology;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;

public class CoverageSet {
	
	private OntModel model;
	private Resource coverageSetResource;
		
	public CoverageSet(String coverageSetURI, String ontologyURI, OntModel coverageSetModel){
		model = coverageSetModel;
		coverageSetResource = model.createResource(coverageSetURI, Vocab.WCSCoverageSet_Populated);
		
		setNamespacePrefix();
		Ontology dataOntology = addOntologyURI(ontologyURI);
		addImport(dataOntology);
	}
	
	private Ontology addOntologyURI(String ontologyURI){
		return model.createOntology(ontologyURI);
	}
	
	private void addImport(Ontology dataOntology){
		dataOntology.addImport(Vocab.EDAC_ONTOLOGY_Resource);
	}
		
	private void setNamespacePrefix(){
		model.setNsPrefix("edac-v3", Vocab.EDAC_ONTOLOGY_URL);
	}
	
	public void addCoverage(Resource coverageResource){
		model.add(coverageSetResource, Vocab.hasWCSCoverage, coverageResource);
	}
	
	public void dumpRDF(File file){
		try{
			FileWriter writer = new FileWriter(file);
			model.write(writer);
			writer.close();
		}catch(Exception e){e.printStackTrace();}
	}
	
	private static final class Vocab
	{
		private static Model m_model = ModelFactory.createDefaultModel();
		public static final String EDAC_ONTOLOGY_URL = "https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac-v3.owl";
		public static final String EDAC_ONTOLOGY_PREFIX = EDAC_ONTOLOGY_URL + "#";
		public static final Resource EDAC_ONTOLOGY_Resource = m_model.createResource(EDAC_ONTOLOGY_URL);
		public static final Resource WCSCoverageSet_Populated = m_model.createResource(EDAC_ONTOLOGY_PREFIX + "WCSCoverageSet_Populated");
		public static final Property hasWCSCoverage = m_model.createProperty(EDAC_ONTOLOGY_PREFIX + "hasWCSCoverage");						
	}	
}

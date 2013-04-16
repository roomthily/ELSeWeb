package edu.utep.cybershare.elseweb.ogc.wcs;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;

public class CoverageSet {
	
	private Model model;
	private Resource coverageSetResource;
	
	public CoverageSet(String coverageSetURI, Model coverageSetModel){
		model = coverageSetModel;
		coverageSetResource = model.createResource(coverageSetURI, Vocab.WCSCoverageSet_Populated);
		
		setNamespacePrefixe();
	}
	
	private void setNamespacePrefixe(){
		model.setNsPrefix("edac-v3", Vocab.PREFIX);
	}
	
	public void addCoverage(Resource coverageResource){
		model.add(coverageSetResource, Vocab.hasWCSCoverage, coverageResource);
	}
	
	private static final class Vocab
	{
		private static Model m_model = ModelFactory.createDefaultModel();		
	
		public static final String PREFIX = "https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac-v3.owl#";
		public static final Resource WCSCoverageSet_Populated = m_model.createResource(PREFIX + "WCSCoverageSet_Populated");
		public static final Property hasWCSCoverage = m_model.createProperty(PREFIX + "hasWCSCoverage");						
	}	
}

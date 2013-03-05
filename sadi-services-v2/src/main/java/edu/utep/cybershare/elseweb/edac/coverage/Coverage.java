package edu.utep.cybershare.elseweb.edac.coverage;

import java.net.URL;
import java.util.Date;

import javax.xml.datatype.XMLGregorianCalendar;

import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;

import edu.utep.cybershare.elseweb.util.XMLGregorianCalendarConverter;

public class Coverage {
	
	private Model model;
	private Resource coverageResource;
	
	public Coverage(String coverageURI, Model coverageModel){
		model = coverageModel;
		coverageResource = model.createResource(coverageURI, Vocab.OGCCoverage);	
	}

	public void addRequestDateTime(){
		XMLGregorianCalendar requestDateTime = XMLGregorianCalendarConverter.asXMLGregorianCalendar(new Date());
		Literal lit_requestDateTime = model.createTypedLiteral(requestDateTime.toXMLFormat());
		model.add(coverageResource, Vocab.hasRequestDateTime, lit_requestDateTime);
	}
	
	public void addGetCoverageRequestURL(URL getCoverageURL){
		Literal lit_getCoverageURL = model.createTypedLiteral(getCoverageURL);
		model.add(coverageResource, Vocab.hasWCSGetCoverageURL, lit_getCoverageURL);
	}
	
	public void addMIMEFormat(){
		model.add(coverageResource, Vocab.hasFormat, Vocab.MIXED);
	}
	
	public void addData(Resource dataResource){
		model.add(coverageResource, Vocab.containsData, dataResource);
	}
	
	public void addHasCoverageToScenarioLayers(Resource scenarioLayersResource, String date){
		if(date.equals("06/18/2002"))
			scenarioLayersResource.addProperty(Vocab.hasCoverage_FractionalSnowCover_06182002, coverageResource);
		else if(date.equals("07/12/2002"))
			scenarioLayersResource.addProperty(Vocab.hasCoverage_FractionalSnowCover_07122002, coverageResource);
		else if(date.equals("07/13/2002"))
			scenarioLayersResource.addProperty(Vocab.hasCoverage_FractionalSnowCover_07132002, coverageResource);
		else if(date.equals("07/29/2002"))
			scenarioLayersResource.addProperty(Vocab.hasCoverage_FractionalSnowCover_07292002, coverageResource);
		else if(date.equals("12/01/1981-12/01/2010"))
			scenarioLayersResource.addProperty(Vocab.hasCoverage_MinTemperatureNormals_121981_122010, coverageResource);
		else
			scenarioLayersResource.addProperty(Vocab.hasCoverage, coverageResource);
	}
	
	private static final class Vocab
	{
		private static Model m_model = ModelFactory.createDefaultModel();
		
		public static final Property hasCoverage = m_model.createProperty("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/lifemapper.owl#hasCoverage");
		public static final Property hasCoverage_FractionalSnowCover_06182002 = m_model.createProperty("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac.owl#hasCoverage_FractionalSnowCover_06182002");
		public static final Property hasCoverage_FractionalSnowCover_07122002 = m_model.createProperty("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac.owl#hasCoverage_FractionalSnowCover_07122002");
		public static final Property hasCoverage_FractionalSnowCover_07132002 = m_model.createProperty("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac.owl#hasCoverage_FractionalSnowCover_07132002");
		public static final Property hasCoverage_FractionalSnowCover_07292002 = m_model.createProperty("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac.owl#hasCoverage_FractionalSnowCover_07292002");
		public static final Property hasCoverage_MinTemperatureNormals_121981_122010 = m_model.createProperty("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac.owl#hasCoverage_MinTemperatureNormals_121981_122010");
		
		public static final Property containsData = m_model.createProperty("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac.owl#containsData");
		public static final Property hasFormat = m_model.createProperty("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac.owl#hasFormat");
		public static final Property hasWCSGetCoverageURL = m_model.createProperty("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac.owl#hasWCSGetCoverageURL");
		public static final Property hasRequestDateTime = m_model.createProperty("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac.owl#hasRequestDateTime");

		public static final Resource MIXED = m_model.createResource("http://openvisko.org/rdf/pml2/formats/MIXED.owl#MIXED");
		public static final Resource OGCCoverage = m_model.createResource("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac.owl#OGCCoverage");
	}	
}

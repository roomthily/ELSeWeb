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
		coverageResource = model.createResource("http://edac.elseweb.cybershare.utep.edu#FractionalSnowCoverData_06182002_Coverage", Vocab.OGCCoverage);	
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
	
	private static final class Vocab
	{
		private static Model m_model = ModelFactory.createDefaultModel();
		
		public static final Property containsData = m_model.createProperty("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac.owl#containsData");
		public static final Property hasFormat = m_model.createProperty("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac.owl#hasFormat");
		public static final Property hasWCSGetCoverageURL = m_model.createProperty("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac.owl#hasWCSGetCoverageURL");
		public static final Property hasRequestDateTime = m_model.createProperty("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac.owl#hasRequestDateTime");

		public static final Resource MIXED = m_model.createResource("http://openvisko.org/rdf/pml2/formats/MIXED.owl#MIXED");
		public static final Resource OGCCoverage = m_model.createResource("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac.owl#OGCCoverage");
	}
}

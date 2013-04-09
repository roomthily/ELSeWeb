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

	private String coverageURI;
	private String regionURI;
	private String durationURI;
	
	public enum Source {MODIS, PRISM};
	public enum Measurement {FractionalSnowData, MinTemperatureNormals};
	
	public Coverage(String baseURI, Model coverageModel){
		model = coverageModel;		

		coverageURI = baseURI + "_Coverage";
		regionURI = baseURI + "_Region";
		durationURI = baseURI + "_Duration";
		
		coverageResource = model.createResource(coverageURI, Vocab.WCSCoverage);			
	}
	
	public void addMeasurement(Measurement measurement){
		switch(measurement){
			case FractionalSnowData:
				model.add(coverageResource, Vocab.hasMeasurement, Vocab.FractionalSnowData);
				break;
			case MinTemperatureNormals:
				model.add(coverageResource, Vocab.hasMeasurement, Vocab.MinTemperatureNormals);
				break;
			default:
				model.add(coverageResource, Vocab.hasMeasurement, Vocab.MinTemperatureNormals);
		}
	}
	
	public void addSource(Source source){
		switch(source){
			case MODIS:
				model.add(coverageResource, Vocab.hasSource, Vocab.MODIS);
				break;
			case PRISM:
				model.add(coverageResource, Vocab.hasSource, Vocab.PRISM);
				break;
			default:
				model.add(coverageResource, Vocab.hasSource, Vocab.PRISM);
		}
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
			
	public void addRegion(double llon, double rlon, double llat, double ulat){
		Resource regionResource = model.createResource(regionURI, Vocab.Region);
		Literal lit_llon = model.createTypedLiteral(llon);
		Literal lit_rlon = model.createTypedLiteral(rlon);
		Literal lit_llat = model.createTypedLiteral(llat);
		Literal lit_ulat = model.createTypedLiteral(ulat);
		
		model.add(regionResource, Vocab.hasLeftLongitude, lit_llon);
		model.add(regionResource, Vocab.hasRightLongitude, lit_rlon);
		model.add(regionResource, Vocab.hasLowerLatitude, lit_llat);
		model.add(regionResource, Vocab.hasUpperLatitude, lit_ulat);
		model.add(coverageResource, Vocab.hasRegion, regionResource);
	}
	
	public void addDuration(String startDate, String endDate){
		Resource durationResource = model.createResource(durationURI, Vocab.Duration);
		Literal lit_startDate = model.createTypedLiteral(startDate);
		Literal lit_endDate = model.createTypedLiteral(endDate);
		
		model.add(durationResource, Vocab.hasStartDate, lit_startDate);
		model.add(durationResource, Vocab.hasEndDate, lit_endDate);
		model.add(coverageResource, Vocab.hasDuration, durationResource);
	}
	
	public void addHasWCSCoverage(Resource coverageSetResource){
		model.add(coverageSetResource, Vocab.hasWCSCoverage, coverageResource);
	}
	
	private static final class Vocab
	{
		private static Model m_model = ModelFactory.createDefaultModel();
		
		public static final Property hasWCSCoverage = m_model.createProperty("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac-v3.owl#hasWCSCoverage");
		
		// OGCCoverage and associated properties
		public static final Resource WCSCoverage = m_model.createResource("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac-v3.owl#WCSCoverage");
		public static final Property hasRegion = m_model.createProperty("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac-v3.owl#hasRegion");
		public static final Property hasDuration = m_model.createProperty("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac-v3.owl#hasDuration");
		public static final Property hasSource = m_model.createProperty("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac-v3.owl#hasSource");
		public static final Property hasFormat = m_model.createProperty("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac-v3.owl#hasFormat");
		public static final Property hasWCSGetCoverageURL = m_model.createProperty("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac-v3.owl#hasWCSGetCoverageURL");
		public static final Property hasRequestDateTime = m_model.createProperty("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac-v3.owl#hasRequestDateTime");
		public static final Property hasMeasurement = m_model.createProperty("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac-v3.owl#hasMeasurement");
		
		// Region and associated properties
		public static final Resource Region = m_model.createResource("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac-v3.owl#Region");
		public static final Property hasUpperLatitude = m_model.createProperty("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac-v3.owl#hasUpperLatitude");
		public static final Property hasLeftLongitude = m_model.createProperty("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac-v3.owl#hasLeftLongitude");
		public static final Property hasLowerLatitude = m_model.createProperty("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac-v3.owl#hasLowerLatitude");
		public static final Property hasRightLongitude = m_model.createProperty("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac-v3.owl#hasRightLongitude");

		// Duration and associated properties
		public static final Resource Duration = m_model.createResource("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac-v3.owl#Duration");
		public static final Property hasEndDate = m_model.createProperty("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac-v3.owl#hasEndDate");
		public static final Property hasStartDate = m_model.createProperty("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac-v3.owl#hasStartDate");

		// Measurement Individuals
		public static final Resource FractionalSnowData = m_model.createResource("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac-v3.owl#FractionalSnowCover");
		public static final Resource MinTemperatureNormals = m_model.createResource("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac-v3.owl#MinTemperatureNormals");
		
		// Measurement Source Individuals
		public static final Resource MODIS = m_model.createResource("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac-v3.owl#MODIS");
		public static final Resource PRISM = m_model.createResource("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac-v3.owl#PRISM");
				
		// Mixed Multi-part MIME Format Individual
		public static final Resource MIXED = m_model.createResource("http://openvisko.org/rdf/pml2/formats/MIXED.owl#MIXED");
	}	
}

package edu.utep.cybershare.elseweb.edac;

import java.net.URL;
import java.util.Date;

import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.log4j.Logger;

import ca.wilkinsonlab.sadi.service.annotations.Name;
import ca.wilkinsonlab.sadi.service.annotations.ContactEmail;
import ca.wilkinsonlab.sadi.service.annotations.InputClass;
import ca.wilkinsonlab.sadi.service.annotations.OutputClass;
import ca.wilkinsonlab.sadi.service.simple.SimpleSynchronousServiceServlet;

import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
//import com.hp.hpl.jena.rdf.model.Statement;
//import com.hp.hpl.jena.rdf.model.StmtIterator;

import edu.utep.cybershare.elseweb.edac.wcs.WCSGetCoverageParameters;
import edu.utep.cybershare.elseweb.edac.wcs.WCSGetCoverageURL;
import edu.utep.cybershare.elseweb.util.XMLGregorianCalendarConverter;

@Name("FractionalSnowCover06182002")
@ContactEmail("nicholas.delrio@gmail.com")
@InputClass("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/lifemapper.owl#ScenarioLayerSet")
@OutputClass("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/lifemapper.owl#PopulatedScenarioLayerSet")
public class FractionalSnowCover06182002 extends SimpleSynchronousServiceServlet
{
	private static final Logger log = Logger.getLogger(FractionalSnowCover06182002.class);
	private static final long serialVersionUID = 1L;

	@Override
	public void processInput(Resource input, Resource output)
	{
		// set wcs getCoverage parameters
		WCSGetCoverageParameters params = new WCSGetCoverageParameters();
		double llon = -107;
		double llat = 35;
		double rlon = -105;
		double ulat = 38;
		params.setBBox(llon, llat, rlon, ulat);
		
		double width = 600;
		double height = 600;
		params.setWidth(width);
		params.setHeight(height);
		
		String format = "image/tiff";
		params.setFormat(format);
		
		String coverage = "mod10a1_a2002169.fractional_snow_cover";
		params.setCoverage(coverage);
		
		String startDate = "06/18/2002";
		String endDate = "06/18/2002";
		
		//construct the parameterized URL from the wcs endpoint and the parameters
		String endpoint = "http://gstore.unm.edu/apps/rgis/datasets/8c53a01e-c8bf-40de-a9b9-3c2e3f037ebb/services/ogc/wcs";
		WCSGetCoverageURL getCoverage = new WCSGetCoverageURL(endpoint, params);
		URL getCoverageURL = getCoverage.getURL();
			
		/* Generate Data
		 * requires:
		 *	-	Region via hasRegion
		 *	-	Duration via hasDuration
		 *	-	Sensor or Method via hasSourceSensor or hasSourceMethod 
		 */
		
		// Construct Data
		Resource dataResource = output.getModel().createResource("http://edac.elseweb.cybershare.utep.edu#FractionalSnowCoverData_06182002", Vocab.FractionalSnowData);
		
		// Add Region
		Resource regionResource = output.getModel().createResource("http://edac.elseweb.cybershare.utep.edu#FractionalSnowCoverData_06182002_Region", Vocab.Region);
		Literal lit_llon = output.getModel().createTypedLiteral(llon);
		Literal lit_rlon = output.getModel().createTypedLiteral(rlon);
		Literal lit_llat = output.getModel().createTypedLiteral(llat);
		Literal lit_ulat = output.getModel().createTypedLiteral(llat);
		output.getModel().add(regionResource, Vocab.hasLeftLongitude, lit_llon);
		output.getModel().add(regionResource, Vocab.hasRightLongitude, lit_rlon);
		output.getModel().add(regionResource, Vocab.hasLowerLatitude, lit_llat);
		output.getModel().add(regionResource, Vocab.hasUpperLatitude, lit_ulat);		
		output.getModel().add(dataResource, Vocab.hasRegion, regionResource);
		
		// Add Duration
		Resource durationResource = output.getModel().createResource("http://edac.elseweb.cybershare.utep.edu#FractionalSnowCoverData_06182002_Duration", Vocab.Duration);
		Literal lit_startDate = output.getModel().createTypedLiteral(startDate);
		Literal lit_endDate = output.getModel().createTypedLiteral(endDate);
		output.getModel().add(durationResource, Vocab.hasStartDate, lit_startDate);
		output.getModel().add(durationResource, Vocab.hasEndDate, lit_endDate);
		output.getModel().add(dataResource, Vocab.hasDuration, durationResource);
		
		// Add Source
		output.getModel().add(dataResource, Vocab.hasSourceSensor, Vocab.MODIS);
	
		/* Generate Coverage.
		 * requires:
		 *	-	DateTime via hasRequestDateTime
		 *	-	WCSGetCoverageURL via hasWCSGetCoverageURL
		 *	-	MIXED MIME Format via hasFormat
		 *	-	Data via containsData
		 */

		// Construct OGCCoverage
		Resource ogcCoverageResource = output.getModel().createResource("http://edac.elseweb.cybershare.utep.edu#FractionalSnowCoverData_06182002_Coverage", Vocab.OGCCoverage);
		
		// Add DateTime
		XMLGregorianCalendar requestDateTime = XMLGregorianCalendarConverter.asXMLGregorianCalendar(new Date());
		Literal lit_requestDateTime = output.getModel().createTypedLiteral(requestDateTime.toXMLFormat());
		output.getModel().add(ogcCoverageResource, Vocab.hasRequestDateTime, lit_requestDateTime);
		
		// Add GetCoverage Request URL
		Literal lit_getCoverageURL = output.getModel().createTypedLiteral(getCoverageURL);
		output.getModel().add(ogcCoverageResource, Vocab.hasWCSGetCoverageURL, lit_getCoverageURL);
		
		// Add MIXED MIME Format
		output.getModel().add(ogcCoverageResource, Vocab.hasFormat, Vocab.MIXED);

		// Add Data
		output.getModel().add(ogcCoverageResource, Vocab.containsData, dataResource);
	}

	@SuppressWarnings("unused")
	private static final class Vocab
	{
		private static Model m_model = ModelFactory.createDefaultModel();
		
		public static final Property hasUpperLatitude = m_model.createProperty("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac.owl#hasUpperLatitude");
		public static final Property hasEndDate = m_model.createProperty("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac.owl#hasEndDate");
		public static final Property containsData = m_model.createProperty("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac.owl#containsData");
		public static final Property hasDuration = m_model.createProperty("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac.owl#hasDuration");
		public static final Property hasLeftLongitude = m_model.createProperty("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac.owl#hasLeftLongitude");
		public static final Property hasLowerLatitude = m_model.createProperty("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac.owl#hasLowerLatitude");
		public static final Property hasSourceSensor = m_model.createProperty("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac.owl#hasSourceSensor");
		public static final Property hasCoverage = m_model.createProperty("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/lifemapper.owl#hasCoverage");
		public static final Property hasRegion = m_model.createProperty("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac.owl#hasRegion");
		public static final Property hasStartDate = m_model.createProperty("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac.owl#hasStartDate");
		public static final Property hasFormat = m_model.createProperty("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac.owl#hasFormat");
		public static final Property hasSourceMethod = m_model.createProperty("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac.owl#hasSourceMethod");
		public static final Property hasRightLongitude = m_model.createProperty("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac.owl#hasRightLongitude");
		public static final Property hasWCSGetCoverageURL = m_model.createProperty("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac.owl#hasWCSGetCoverageURL");
		public static final Property hasRequestDateTime = m_model.createProperty("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac.owl#hasRequestDateTime");

		public static final Resource MIXED = m_model.createResource("http://openvisko.org/rdf/pml2/formats/MIXED.owl#MIXED");
		public static final Resource PopulatedScenarioLayerSet = m_model.createResource("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/lifemapper.owl#PopulatedScenarioLayerSet");
		public static final Resource MODIS = m_model.createResource("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac.owl#MODIS");
		public static final Resource Literal = m_model.createResource("http://www.w3.org/2000/01/rdf-schema#Literal");
		public static final Resource OGCCoverage = m_model.createResource("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac.owl#OGCCoverage");
		public static final Resource Double = m_model.createResource("http://www.w3.org/2001/XMLSchema#double");
		public static final Resource Region = m_model.createResource("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac.owl#Region");
		public static final Resource Duration = m_model.createResource("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac.owl#Duration");
		public static final Resource Method = m_model.createResource("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac.owl#Method");
		public static final Resource Data = m_model.createResource("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac.owl#Data");
		public static final Resource FractionalSnowData = m_model.createResource("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac.owl#FractionalSnowCover");
		public static final Resource ScenarioLayerSet = m_model.createResource("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/lifemapper.owl#ScenarioLayerSet");
		public static final Resource Sensor = m_model.createResource("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac.owl#Sensor");
	}
}

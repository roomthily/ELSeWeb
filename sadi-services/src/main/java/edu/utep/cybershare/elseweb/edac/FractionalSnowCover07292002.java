package edu.utep.cybershare.elseweb.edac;

import java.net.URL;
import java.util.Date;

import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.log4j.Logger;

import ca.wilkinsonlab.sadi.service.annotations.Description;
import ca.wilkinsonlab.sadi.service.annotations.Name;
import ca.wilkinsonlab.sadi.service.annotations.ContactEmail;
import ca.wilkinsonlab.sadi.service.annotations.InputClass;
import ca.wilkinsonlab.sadi.service.annotations.OutputClass;
import ca.wilkinsonlab.sadi.service.simple.SimpleSynchronousServiceServlet;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
//import com.hp.hpl.jena.rdf.model.Statement;
//import com.hp.hpl.jena.rdf.model.StmtIterator;

import edu.utep.cybershare.elseweb.edac.wcs.WCSGetCoverageParameters;
import edu.utep.cybershare.elseweb.edac.wcs.WCSGetCoverageURL;
import edu.utep.cybershare.elseweb.util.XMLGregorianCalendarConverter;

@Name("FractionalSnowCover07292002")
@ContactEmail("nicholas.delrio@gmail.com")
@InputClass("http://openvisko.org/rdf/ontology-elseweb/elseweb.owl#ScenarioLayerSet")
@OutputClass("http://openvisko.org/rdf/ontology-elseweb/elseweb.owl#PopulatedScenarioLayerSet_07292002")
@Description("EDAC Fractional Snow Cover Data 07292002")

public class FractionalSnowCover07292002 extends SimpleSynchronousServiceServlet
{
	private static final Logger log = Logger.getLogger(FractionalSnowCover07292002.class);
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

		String coverage = "mod10a1_a2002210.fractional_snow_cover";
		params.setCoverage(coverage);
				
		//construct the parameterized URL from the wcs endpoint and the parameters
		String endpoint = "http://gstore.unm.edu/apps/rgis/datasets/a427563f-3c7e-44a2-8b35-68ce2a78001a/services/ogc/wcs";
		WCSGetCoverageURL getCoverage = new WCSGetCoverageURL(endpoint, params);
		URL getCoverageURL = getCoverage.getURL();
		
		//generate output RDF
		Resource wcsResponse = output.getModel().createResource("http://edac.elseweb.cybershare.utep.edu#FractionalSnowCoverData_07292002", Vocab.FractionalSnowCover_07292002);
		XMLGregorianCalendar xmlDateTime = XMLGregorianCalendarConverter.asXMLGregorianCalendar(new Date());
		
		output.getModel().add(wcsResponse, Vocab.hasRequestDateTime, xmlDateTime.toXMLFormat());
		output.getModel().add(wcsResponse, Vocab.hasWCSGetCoverageURL, getCoverageURL.toString());
		output.getModel().add(wcsResponse, Vocab.hasFormat, Vocab.Mixed);		
		output.addProperty(Vocab.hasWCSCoverage, wcsResponse);
	}

	@SuppressWarnings("unused")
	private static final class Vocab
	{
		private static Model m_model = ModelFactory.createDefaultModel();

		public static final Property hasFormat = m_model.createProperty("http://openvisko.org/rdf/ontology-elseweb/elseweb.owl#hasFormat");
		public static final Property hasFractionalSnowCover_07292002 = m_model.createProperty("http://openvisko.org/rdf/ontology-elseweb/elseweb.owl#hasFractionalSnowCover_07292002");
		public static final Property hasWCSCoverage = m_model.createProperty("http://openvisko.org/rdf/ontology-elseweb/elseweb.owl#hasWCSCoverage");		
		public static final Property hasWCSGetCoverageURL = m_model.createProperty("http://openvisko.org/rdf/ontology-elseweb/elseweb.owl#hasWCSGetCoverageURL");
		public static final Property hasRequestDateTime = m_model.createProperty("http://openvisko.org/rdf/ontology-elseweb/elseweb.owl#hasRequestDateTime");
		
		public static final Resource FractionalSnowCover_07292002 = m_model.createResource("http://openvisko.org/rdf/ontology-elseweb/elseweb.owl#FractionalSnowCover_07292002");
		public static final Resource Mixed = m_model.createResource("http://openvisko.org/rdf/pml2/formats/MIXED.owl#MIXED");	
	}
}

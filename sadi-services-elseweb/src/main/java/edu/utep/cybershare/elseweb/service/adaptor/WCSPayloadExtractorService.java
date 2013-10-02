package edu.utep.cybershare.elseweb.service.adaptor;

import java.net.URL;

import org.apache.log4j.Logger;

import ca.wilkinsonlab.sadi.service.annotations.Description;
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

import edu.utep.cybershare.elseweb.util.Printing;

@Name("WCSPayloadExtractorService")
@ContactEmail("nicholas.delrio@gmail.com")
@InputClass("http://ontology.cybershare.utep.edu/ELSEWeb/elsewebdata.owl#WCSCoverageDistribution")
@OutputClass("http://ontology.cybershare.utep.edu/ELSEWeb/elsewebdata.owl#WCSCoverageDistributionTiff")
@Description("WCS Multipart MIME Payload Extractor Service")

public class WCSPayloadExtractorService extends SimpleSynchronousServiceServlet{
	
	private static final Logger log = Logger.getLogger(WCSPayloadExtractorService.class);
	private static final long serialVersionUID = 1L;
	
	@Override
	public void processInput(Resource input, Resource output){	
		Printing.print(input.getModel());
		
		//get wcs data service call
		String wcsGetCoverageURLString = input.getProperty(Vocab.downloadURL).getLiteral().getString();

		//get payload URL from wcs service data url
		URL payloadURL = this.getCoveragePayloadURL(wcsGetCoverageURLString);

		//create payload url literal
		Literal payloadURLLiteral = output.getModel().createLiteral(payloadURL.toString());

		//add payload url to output
		output.addProperty(Vocab.downloadTiffURL, payloadURLLiteral);
		
		Printing.print(output.getModel());
	}
	
	private URL getCoveragePayloadURL(String wcsGetCoverageURLString){
		PayloadExtractor extractor = new PayloadExtractor();
		
		System.out.println("Extracting payload from: " + wcsGetCoverageURLString);
		URL wcsGetCoverageURL = null;
		try{wcsGetCoverageURL = new URL(wcsGetCoverageURLString);}
		catch(Exception e){e.printStackTrace();}

		URL coveragePayloadURL = extractor.getPayload(wcsGetCoverageURL);
		return coveragePayloadURL;
	}
	
	private static final class Vocab{
		
		private static Model m_model = ModelFactory.createDefaultModel();
		
		private static final String dcat = "http://www.w3.org/ns/dcat#";
		public static final Property downloadURL = m_model.createProperty(dcat + "downloadURL");
		
		private static final String elseweb = "http://ontology.cybershare.utep.edu/ELSEWeb/elsewebdata.owl#";
		public static final Property downloadTiffURL = m_model.createProperty(elseweb + "downloadTiffURL");
	}
}

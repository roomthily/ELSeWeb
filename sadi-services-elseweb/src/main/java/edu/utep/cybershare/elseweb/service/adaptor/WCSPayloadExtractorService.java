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
@OutputClass("http://ontology.cybershare.utep.edu/ELSEWeb/scenario.owl#ExtractedWCSCoverageDistribution")
@Description("Extracts Payload from WCS Multipart MIME Response")

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

		//create TiffDistribution
		String randomID = String.valueOf(Math.random());
		String tiffDistributionURI = "http://ontology.cybershare.utep.edu/ELSEWeb/tiffDistribution_" + randomID;
		Resource tiffDistributionResource = output.getModel().createResource(tiffDistributionURI, Vocab.TiffDistribution);

		
		//add associated properties to TiffDistribution individual
		output.getModel().addLiteral(tiffDistributionResource, Vocab.downloadURL, payloadURLLiteral);
		output.getModel().add(tiffDistributionResource, Vocab.format, Vocab.tiff);
		
		//add TiffDistribution individual to output
		output.addProperty(Vocab.hasTiffPayload, tiffDistributionResource);
		
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
		
		//dcat properties
		private static final String dcat = "http://www.w3.org/ns/dcat#";
		public static final Property downloadURL = m_model.createProperty(dcat + "downloadURL");
		
		//elseweb properties
		private static final String elseweb = "http://ontology.cybershare.utep.edu/ELSEWeb/elsewebdata.owl#";
		public static final Resource TiffDistribution = m_model.createResource(elseweb + "TiffDistribution");
		
		//dublin core properties
		private static final String dcmi = "http://purl.org/dc/terms/";
		public static final Property format = m_model.createProperty(dcmi + "format");
	
		//scenario properties
		private static final String scenario = "http://ontology.cybershare.utep.edu/ELSEWeb/scenario.owl#";
		public static final Property hasTiffPayload = m_model.createProperty(scenario + "hasTiffPayload");
		
		//tiff dc:FileFormat
		public static final Resource tiff = m_model.createResource("http://provenanceweb.org/format/mime/image/tiff");
	}
}

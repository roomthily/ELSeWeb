package edu.utep.cybershare.elseweb.adaptor;

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
@InputClass("http://ontology.cybershare.utep.edu/ELSEWeb/edac.owl#WCSCoverageQualified")
@OutputClass("http://ontology.cybershare.utep.edu/ELSEWeb/edac.owl#WCSCoveragePayload")
@Description("WCS Multipart MIME Payload Extractor Service")

public class WCSPayloadExtractorService extends SimpleSynchronousServiceServlet
{
	private static final Logger log = Logger.getLogger(WCSPayloadExtractorService.class);
	private static final long serialVersionUID = 1L;

	@Override
	public void processInput(Resource input, Resource output)
	{	
		Printing.print(input.getModel());

		//get distribution
		Resource distributionResource = getDistribution(input);
		
		//get WCSGetCoverageURL from Distribution	
		String wcsGetCoverageURLString = distributionResource.getProperty(Vocab.downloadURL).getLiteral().getString();
		
		//extract payload
		System.out.println("Extracting payload from: " + wcsGetCoverageURLString);
		String payloadURLString = this.getCoveragePayloadURL(wcsGetCoverageURLString).toString();
		
		//create literal from payloadURLString
		Literal payloadURLLiteral = output.getModel().createLiteral(payloadURLString);
		
		//add payload to distribution
		output.addLiteral(Vocab.hasWCSCoveragePayloadURL, payloadURLLiteral);
	}
	
	private URL getCoveragePayloadURL(String wcsGetCoverageURLString){
		URL wcsGetCoverageURL = null;
		try{wcsGetCoverageURL = new URL(wcsGetCoverageURLString);}
		catch(Exception e){e.printStackTrace();}

		PayloadExtractor extractor = new PayloadExtractor();
		URL coveragePayloadURL = extractor.getPayload(wcsGetCoverageURL);
		return coveragePayloadURL;
	}
	
	private Resource getDistribution(Resource wcsCoverageResource){
		return wcsCoverageResource.getPropertyResourceValue(Vocab.distribution);
	}
	
	private static final class Vocab
	{
		public static Model m_model = ModelFactory.createDefaultModel();
				
		public static final Property distribution = m_model.createProperty("http://www.w3.org/ns/dcat#distribution");
		public static final Property downloadURL = m_model.createProperty("http://www.w3.org/ns/dcat#downloadURL");
		public static final Property hasWCSCoveragePayloadURL = m_model.createProperty("http://ontology.cybershare.utep.edu/ELSEWeb/edac.owl#hasWCSCoveragePayloadURL");		
	}
}

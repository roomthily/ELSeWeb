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

@Name("WCSPayloadExtractor")
@ContactEmail("nicholas.delrio@gmail.com")
@InputClass("http://ontology.cybershare.utep.edu/ELSEWeb/edac.owl#DistributionQualified")
@OutputClass("http://ontology.cybershare.utep.edu/ELSEWeb/edac.owl#WCSCoveragePayloadDistribution")
@Description("WCS Multipart MIME Payload Extractor")

public class WCSPayloadExtractor extends SimpleSynchronousServiceServlet
{
	private static final Logger log = Logger.getLogger(WCSPayloadExtractor.class);
	private static final long serialVersionUID = 1L;

	@Override
	public void processInput(Resource input, Resource output)
	{	
		Printing.print(input.getModel());

		//get WCSGetCoverageURL from Distribution	
		String wcsGetCoverageURLString = input.getProperty(Vocab.downloadURL).getLiteral().getString();
		
		//extract payload
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
	
	private static final class Vocab
	{
		public static Model m_model = ModelFactory.createDefaultModel();
				
		public static final Property downloadURL = m_model.createProperty("http://www.w3.org/ns/dcat#downloadURL");
		public static final Property hasWCSCoveragePayloadURL = m_model.createProperty("hhttp://ontology.cybershare.utep.edu/ELSEWeb/edac.owl#hasWCSCoveragePayloadURL");		
	}
}

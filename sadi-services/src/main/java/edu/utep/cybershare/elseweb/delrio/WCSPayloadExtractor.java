package edu.utep.cybershare.elseweb.delrio;

import java.net.URL;

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

import edu.utep.cybershare.elseweb.delrio.multipartMIME.PayloadExtractor;
//import com.hp.hpl.jena.rdf.model.Statement;
//import com.hp.hpl.jena.rdf.model.StmtIterator;

@Name("WCSPayloadExtractor")
@ContactEmail("nicholas.delrio@gmail.com")
@InputClass("http://openvisko.org/rdf/ontology-elseweb/elseweb.owl#WCSCoverage")
@OutputClass("http://openvisko.org/rdf/ontology-elseweb/elseweb.owl#WCSCoveragePayload")
@Description("WCS Multipart MIME Payload Extractor")

public class WCSPayloadExtractor extends SimpleSynchronousServiceServlet
{
	private static final Logger log = Logger.getLogger(WCSPayloadExtractor.class);
	private static final long serialVersionUID = 1L;

	@Override
	public void processInput(Resource input, Resource output)
	{
		String wcsGetCoverageURLString = input.getProperty(Vocab.hasWCSGetCoverageURL).getLiteral().getString();
		
		URL wcsGetCoverageURL = null;
		try{wcsGetCoverageURL = new URL(wcsGetCoverageURLString);}
		catch(Exception e){
			log.error("Error creating URL from input resource");
			log.error(e.getMessage());
		}
		
		if(wcsGetCoverageURL != null){
			PayloadExtractor extractor = new PayloadExtractor();
			log.debug("Extracting payload from: " + wcsGetCoverageURL);
			URL coveragePayloadURL = extractor.getPayload(wcsGetCoverageURL);
			log.debug("Extracted payload located at: " + coveragePayloadURL);
			output.addProperty(Vocab.hasWCSCoveragePayloadURL, coveragePayloadURL.toString());
		}
	}

	@SuppressWarnings("unused")
	private static final class Vocab
	{
		private static Model m_model = ModelFactory.createDefaultModel();
		
		public static final Property hasWCSCoveragePayloadURL = m_model.createProperty("http://openvisko.org/rdf/ontology-elseweb/elseweb.owl#hasWCSCoveragePayloadURL");
		public static final Property hasFormat = m_model.createProperty("http://openvisko.org/rdf/ontology-elseweb/elseweb.owl#hasFormat");
		public static final Property hasWCSGetCoverageURL = m_model.createProperty("http://openvisko.org/rdf/ontology-elseweb/elseweb.owl#hasWCSGetCoverageURL");
		public static final Resource WCSCoverage = m_model.createResource("http://openvisko.org/rdf/ontology-elseweb/elseweb.owl#WCSCoverage");
		public static final Resource Literal = m_model.createResource("http://www.w3.org/2000/01/rdf-schema#Literal");
		public static final Resource WCSCoveragePayload = m_model.createResource("http://openvisko.org/rdf/ontology-elseweb/elseweb.owl#WCSCoveragePayload");
	}
}

package edu.utep.cybershare.elseweb.delrio;

import java.io.StringWriter;
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
//import com.hp.hpl.jena.rdf.model.Statement;
//import com.hp.hpl.jena.rdf.model.StmtIterator;

import edu.utep.cybershare.elseweb.delrio.multipartMIME.PayloadExtractor;

@Name("WCSPayloadExtractor")
@ContactEmail("nicholas.delrio@gmail.com")
@InputClass("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac-v3.owl#WCSCoverageSequence")
@OutputClass("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac-v3.owl#WCSPayloadSequence")
@Description("WCS Multipart MIME Payload Extractor")

public class WCSPayloadExtractor extends SimpleSynchronousServiceServlet
{
	private static final Logger log = Logger.getLogger(WCSPayloadExtractor.class);
	private static final long serialVersionUID = 1L;

	@Override
	public void processInput(Resource input, Resource output)
	{
		StringWriter wtr = new StringWriter();
		input.getModel().write(wtr, "RDF/XML-ABBREV");
		System.out.println(wtr.toString());
		
		Resource coverage1Resource = input.getProperty(Vocab.hasWCSCoverage1).getResource();
		Resource coverage2Resource = input.getProperty(Vocab.hasWCSCoverage2).getResource();
		Resource coverage3Resource = input.getProperty(Vocab.hasWCSCoverage3).getResource();
		Resource coverage4Resource = input.getProperty(Vocab.hasWCSCoverage4).getResource();
		Resource coverage5Resource = input.getProperty(Vocab.hasWCSCoverage5).getResource();
		
		Literal wcsCoveragePayloadURLLiteral1 = getCoveragePayloadURLLiteral(coverage1Resource);
		Literal wcsCoveragePayloadURLLiteral2 = getCoveragePayloadURLLiteral(coverage2Resource);
		Literal wcsCoveragePayloadURLLiteral3 = getCoveragePayloadURLLiteral(coverage3Resource);
		Literal wcsCoveragePayloadURLLiteral4 = getCoveragePayloadURLLiteral(coverage4Resource);
		Literal wcsCoveragePayloadURLLiteral5 = getCoveragePayloadURLLiteral(coverage5Resource);
		
		String baseURI = "http://edac.elseweb.cybershare.utep.edu#WCSPayloadExtractor_WCSCoveragePayload_";
		Resource wcsCoveragePayload1 = output.getModel().createResource(baseURI + "1", Vocab.WCSCoveragePayload);
		output.getModel().addLiteral(wcsCoveragePayload1, Vocab.hasWCSCoveragePayloadURL, wcsCoveragePayloadURLLiteral1);
		
		Resource wcsCoveragePayload2 = output.getModel().createResource(baseURI + "2", Vocab.WCSCoveragePayload);
		output.getModel().addLiteral(wcsCoveragePayload2, Vocab.hasWCSCoveragePayloadURL, wcsCoveragePayloadURLLiteral2);

		Resource wcsCoveragePayload3 = output.getModel().createResource(baseURI + "3", Vocab.WCSCoveragePayload);
		output.getModel().addLiteral(wcsCoveragePayload3, Vocab.hasWCSCoveragePayloadURL, wcsCoveragePayloadURLLiteral3);

		Resource wcsCoveragePayload4 = output.getModel().createResource(baseURI + "4", Vocab.WCSCoveragePayload);
		output.getModel().addLiteral(wcsCoveragePayload4, Vocab.hasWCSCoveragePayloadURL, wcsCoveragePayloadURLLiteral4);

		Resource wcsCoveragePayload5 = output.getModel().createResource(baseURI + "5", Vocab.WCSCoveragePayload);
		output.getModel().addLiteral(wcsCoveragePayload5, Vocab.hasWCSCoveragePayloadURL, wcsCoveragePayloadURLLiteral5);

		output.addProperty(Vocab.hasWCSCoveragePayload1, wcsCoveragePayload1);
		output.addProperty(Vocab.hasWCSCoveragePayload2, wcsCoveragePayload2);
		output.addProperty(Vocab.hasWCSCoveragePayload3, wcsCoveragePayload3);
		output.addProperty(Vocab.hasWCSCoveragePayload4, wcsCoveragePayload4);
		output.addProperty(Vocab.hasWCSCoveragePayload5, wcsCoveragePayload5);
	}
	
	private Literal getCoveragePayloadURLLiteral(Resource wcsCoverageResource){
		String wcsGetCoverageURLString = wcsCoverageResource.getProperty(Vocab.hasWCSGetCoverageURL).getLiteral().getString();
		
		URL wcsGetCoverageURL = null;
		try{wcsGetCoverageURL = new URL(wcsGetCoverageURLString);}
		catch(Exception e){e.printStackTrace();}

		PayloadExtractor extractor = new PayloadExtractor();
		URL coveragePayloadURL = extractor.getPayload(wcsGetCoverageURL);
		
		return Vocab.m_model.createLiteral(coveragePayloadURL.toString());		
	}

	private static final class Vocab
	{
		public static Model m_model = ModelFactory.createDefaultModel();

		public static final Resource WCSCoveragePayload = m_model.createResource("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac-v3.owl#WCSCoveragePayload");
		
		public static final Property hasWCSCoveragePayloadURL = m_model.createProperty("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac-v3.owl#hasWCSCoveragePayloadURL");		
		
		public static final Property hasWCSCoveragePayload1 = m_model.createProperty("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac-v3.owl#hasWCSCoveragePayload1");
		public static final Property hasWCSCoveragePayload2 = m_model.createProperty("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac-v3.owl#hasWCSCoveragePayload2");
		public static final Property hasWCSCoveragePayload3 = m_model.createProperty("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac-v3.owl#hasWCSCoveragePayload3");
		public static final Property hasWCSCoveragePayload4 = m_model.createProperty("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac-v3.owl#hasWCSCoveragePayload4");
		public static final Property hasWCSCoveragePayload5 = m_model.createProperty("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac-v3.owl#hasWCSCoveragePayload5");		
		
		public static final Property hasWCSCoverage1 = m_model.createProperty("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac-v3.owl#hasWCSCoverage1");
		public static final Property hasWCSCoverage2 = m_model.createProperty("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac-v3.owl#hasWCSCoverage2");
		public static final Property hasWCSCoverage3 = m_model.createProperty("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac-v3.owl#hasWCSCoverage3");
		public static final Property hasWCSCoverage4 = m_model.createProperty("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac-v3.owl#hasWCSCoverage4");
		public static final Property hasWCSCoverage5 = m_model.createProperty("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac-v3.owl#hasWCSCoverage5");

		public static final Property hasWCSGetCoverageURL = m_model.createProperty("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac-v3.owl#hasWCSGetCoverageURL");
	}
}

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

@Name("WCSPayloadExtractor1")
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

		output.addLiteral(Vocab.hasWCSCoveragePayloadURL1, wcsCoveragePayloadURLLiteral1);
		output.addLiteral(Vocab.hasWCSCoveragePayloadURL2, wcsCoveragePayloadURLLiteral2);
		output.addLiteral(Vocab.hasWCSCoveragePayloadURL3, wcsCoveragePayloadURLLiteral3);
		output.addLiteral(Vocab.hasWCSCoveragePayloadURL4, wcsCoveragePayloadURLLiteral4);
		output.addLiteral(Vocab.hasWCSCoveragePayloadURL5, wcsCoveragePayloadURLLiteral5);
	}
	
	private Literal getCoveragePayloadURLLiteral(Resource wcsCoverageResource){
		String wcsGetCoverageURLString = wcsCoverageResource.getProperty(Vocab.hasWCSGetCoverageURL).getLiteral().getString();
		
		URL wcsGetCoverageURL = null;
		try{wcsGetCoverageURL = new URL(wcsGetCoverageURLString);}
		catch(Exception e){e.printStackTrace();}

		PayloadExtractor extractor = new PayloadExtractor();
		URL coveragePayloadURL = extractor.getPayload(wcsGetCoverageURL);
		
		return Vocab.m_model.createTypedLiteral(coveragePayloadURL);		
	}

	private static final class Vocab
	{
		public static Model m_model = ModelFactory.createDefaultModel();
		
		public static final Property hasWCSCoveragePayloadURL1 = m_model.createProperty("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac-v3.owl#hasWCSCoveragePayloadURL1");
		public static final Property hasWCSCoveragePayloadURL2 = m_model.createProperty("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac-v3.owl#hasWCSCoveragePayloadURL2");
		public static final Property hasWCSCoveragePayloadURL3 = m_model.createProperty("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac-v3.owl#hasWCSCoveragePayloadURL3");
		public static final Property hasWCSCoveragePayloadURL4 = m_model.createProperty("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac-v3.owl#hasWCSCoveragePayloadURL4");
		public static final Property hasWCSCoveragePayloadURL5 = m_model.createProperty("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac-v3.owl#hasWCSCoveragePayloadURL5");		
		
		public static final Property hasWCSCoverage1 = m_model.createProperty("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac-v3.owl#hasWCSCoverage1");
		public static final Property hasWCSCoverage2 = m_model.createProperty("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac-v3.owl#hasWCSCoverage2");
		public static final Property hasWCSCoverage3 = m_model.createProperty("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac-v3.owl#hasWCSCoverage3");
		public static final Property hasWCSCoverage4 = m_model.createProperty("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac-v3.owl#hasWCSCoverage4");
		public static final Property hasWCSCoverage5 = m_model.createProperty("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac-v3.owl#hasWCSCoverage5");

		public static final Property hasWCSGetCoverageURL = m_model.createProperty("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac-v3.owl#hasWCSGetCoverageURL");
	}
}

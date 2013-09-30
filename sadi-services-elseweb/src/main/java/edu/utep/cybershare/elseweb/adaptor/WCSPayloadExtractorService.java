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
@InputClass("http://ontology.cybershare.utep.edu/ELSEWeb/edac.owl#WCSCoverageSequencePopulated")
@OutputClass("http://ontology.cybershare.utep.edu/ELSEWeb/edac.owl#WCSCoveragePayloadSequence")

@Description("WCS Multipart MIME Payload Extractor Service")

public class WCSPayloadExtractorService extends SimpleSynchronousServiceServlet
{
	private static final Logger log = Logger.getLogger(WCSPayloadExtractorService.class);
	private static final long serialVersionUID = 1L;
	
	private PayloadExtractor extractor;

	@Override
	public void processInput(Resource input, Resource output)
	{	
		//Printing.print(input.getModel());

		extractor = new PayloadExtractor();
		String baseURI = "http://cybershare.utep.edu/ELSEWeb/";

		Resource dataset1 = input.getPropertyResourceValue(Vocab.dataset1);
		Resource dataset2 = input.getPropertyResourceValue(Vocab.dataset2);
		Resource dataset3 = input.getPropertyResourceValue(Vocab.dataset3);
		Resource dataset4 = input.getPropertyResourceValue(Vocab.dataset4);
		Resource dataset5 = input.getPropertyResourceValue(Vocab.dataset5);
		
		Resource distribution1 = dataset1.getPropertyResourceValue(Vocab.distribution);
		Resource distribution2 = dataset2.getPropertyResourceValue(Vocab.distribution);
		Resource distribution3 = dataset3.getPropertyResourceValue(Vocab.distribution);
		Resource distribution4 = dataset4.getPropertyResourceValue(Vocab.distribution);
		Resource distribution5 = dataset5.getPropertyResourceValue(Vocab.distribution);
		
		String wcsGetCoverageURLString1 = distribution1.getProperty(Vocab.downloadURL).getLiteral().getString();
		String wcsGetCoverageURLString2 = distribution2.getProperty(Vocab.downloadURL).getLiteral().getString();
		String wcsGetCoverageURLString3 = distribution3.getProperty(Vocab.downloadURL).getLiteral().getString();
		String wcsGetCoverageURLString4 = distribution4.getProperty(Vocab.downloadURL).getLiteral().getString();
		String wcsGetCoverageURLString5 = distribution5.getProperty(Vocab.downloadURL).getLiteral().getString();

		String payloadURLString1 = this.getCoveragePayloadURL(wcsGetCoverageURLString1).toString();
		String payloadURLString2 = this.getCoveragePayloadURL(wcsGetCoverageURLString2).toString();
		String payloadURLString3 = this.getCoveragePayloadURL(wcsGetCoverageURLString3).toString();
		String payloadURLString4 = this.getCoveragePayloadURL(wcsGetCoverageURLString4).toString();
		String payloadURLString5 = this.getCoveragePayloadURL(wcsGetCoverageURLString5).toString();

		//create WCSCoveragePayloadDistributions
		Model model = output.getModel();
		Resource wcsCoveragePayloadDistribution1 = this.getCSCoveragePayloadDistribution(1, baseURI, model, payloadURLString1);
		Resource wcsCoveragePayloadDistribution2 = this.getCSCoveragePayloadDistribution(2, baseURI, model, payloadURLString2);
		Resource wcsCoveragePayloadDistribution3 = this.getCSCoveragePayloadDistribution(3, baseURI, model, payloadURLString3);
		Resource wcsCoveragePayloadDistribution4 = this.getCSCoveragePayloadDistribution(4, baseURI, model, payloadURLString4);
		Resource wcsCoveragePayloadDistribution5 = this.getCSCoveragePayloadDistribution(5, baseURI, model, payloadURLString5);

		//create WCSCoveragePayloads
		Resource wcsCoveragePayload1 = output.getModel().createResource(baseURI + "wcsCoveragePayload_" + 1, Vocab.WCSCoveragePayload);
		output.getModel().add(wcsCoveragePayload1, Vocab.distribution, wcsCoveragePayloadDistribution1);

		Resource wcsCoveragePayload2 = output.getModel().createResource(baseURI + "wcsCoveragePayload_" + 2, Vocab.WCSCoveragePayload);
		output.getModel().add(wcsCoveragePayload2, Vocab.distribution, wcsCoveragePayloadDistribution2);

		Resource wcsCoveragePayload3 = output.getModel().createResource(baseURI + "wcsCoveragePayload_" + 3, Vocab.WCSCoveragePayload);
		output.getModel().add(wcsCoveragePayload3, Vocab.distribution, wcsCoveragePayloadDistribution3);

		Resource wcsCoveragePayload4 = output.getModel().createResource(baseURI + "wcsCoveragePayload_" + 4, Vocab.WCSCoveragePayload);
		output.getModel().add(wcsCoveragePayload4, Vocab.distribution, wcsCoveragePayloadDistribution4);

		Resource wcsCoveragePayload5 = output.getModel().createResource(baseURI + "wcsCoveragePayload_" + 5, Vocab.WCSCoveragePayload);
		output.getModel().add(wcsCoveragePayload5, Vocab.distribution, wcsCoveragePayloadDistribution5);

		//add WCSCoveragePayloads to Output sequence		
		output.addProperty(Vocab.payloadDataset1, wcsCoveragePayload1);
		output.addProperty(Vocab.payloadDataset2, wcsCoveragePayload2);
		output.addProperty(Vocab.payloadDataset3, wcsCoveragePayload3);
		output.addProperty(Vocab.payloadDataset4, wcsCoveragePayload4);
		output.addProperty(Vocab.payloadDataset5, wcsCoveragePayload5);
		
		//Printing.print(output.getModel());
	}
	
	public Resource getCSCoveragePayloadDistribution(int label, String baseURI, Model model, String payloadURLString){
		Resource coveragePayloadDistribution = model.createResource(baseURI + "wcsCoveragePayloadDistribution_" + label, Vocab.WCSCoveragePayloadDistribution);

		//create literal from payloadURLString
		Literal payloadURLLiteral = model.createLiteral(payloadURLString);
		model.add(coveragePayloadDistribution, Vocab.hasWCSCoveragePayloadURL, payloadURLLiteral);
		return coveragePayloadDistribution;
	}
	
	private URL getCoveragePayloadURL(String wcsGetCoverageURLString){
		System.out.println("Extracting payload from: " + wcsGetCoverageURLString);
		URL wcsGetCoverageURL = null;
		try{wcsGetCoverageURL = new URL(wcsGetCoverageURLString);}
		catch(Exception e){e.printStackTrace();}

		URL coveragePayloadURL = extractor.getPayload(wcsGetCoverageURL);
		return coveragePayloadURL;
	}
	
	private static final class Vocab
	{
		public static Model m_model = ModelFactory.createDefaultModel();
				
		public static final Property dataset1 = m_model.createProperty("http://ontology.cybershare.utep.edu/ELSEWeb/edac.owl#dataset1");
		public static final Property dataset2 = m_model.createProperty("http://ontology.cybershare.utep.edu/ELSEWeb/edac.owl#dataset2");
		public static final Property dataset3 = m_model.createProperty("http://ontology.cybershare.utep.edu/ELSEWeb/edac.owl#dataset3");
		public static final Property dataset4 = m_model.createProperty("http://ontology.cybershare.utep.edu/ELSEWeb/edac.owl#dataset4");
		public static final Property dataset5 = m_model.createProperty("http://ontology.cybershare.utep.edu/ELSEWeb/edac.owl#dataset5");
		
		public static final Property payloadDataset1 = m_model.createProperty("http://ontology.cybershare.utep.edu/ELSEWeb/edac.owl#payloadDataset1");
		public static final Property payloadDataset2 = m_model.createProperty("http://ontology.cybershare.utep.edu/ELSEWeb/edac.owl#payloadDataset2");
		public static final Property payloadDataset3 = m_model.createProperty("http://ontology.cybershare.utep.edu/ELSEWeb/edac.owl#payloadDataset3");
		public static final Property payloadDataset4 = m_model.createProperty("http://ontology.cybershare.utep.edu/ELSEWeb/edac.owl#payloadDataset4");
		public static final Property payloadDataset5 = m_model.createProperty("http://ontology.cybershare.utep.edu/ELSEWeb/edac.owl#payloadDataset5");

		public static final Resource WCSCoveragePayload = m_model.createResource("http://ontology.cybershare.utep.edu/ELSEWeb/edac.owl#WCSCoveragePayload");
		public static final Resource WCSCoveragePayloadDistribution = m_model.createResource("http://ontology.cybershare.utep.edu/ELSEWeb/edac.owl#WCSCoveragePayloadDistribution");
		public static final Property hasWCSCoveragePayloadURL = m_model.createProperty("http://ontology.cybershare.utep.edu/ELSEWeb/edac.owl#hasWCSCoveragePayloadURL");

		public static final Property distribution = m_model.createProperty("http://www.w3.org/ns/dcat#distribution");
		public static final Property downloadURL = m_model.createProperty("http://www.w3.org/ns/dcat#downloadURL");
	}
}

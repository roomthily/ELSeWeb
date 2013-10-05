package edu.utep.cybershare.elseweb.service.adaptor;

import java.net.URL;

import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;

import edu.utep.cybershare.elseweb.util.FileUtils;
import edu.utep.cybershare.elseweb.util.URLUtils;

public class TiffDistributionMapper {
	
	private URL payloadURL;
	
	public void setWCSDistributionResource(Resource wcsDistributionResource){
		//get wcs data service call
		String wcsGetCoverageURLString = wcsDistributionResource.getProperty(Vocab.downloadURL).getLiteral().getString();
		
		//get payload URL from wcs service data url
		payloadURL = this.getCoveragePayloadURL(wcsGetCoverageURLString);
	}

	public Resource getTiffDistributionResource(Model model){
		
		//create payload url literal
		Literal payloadURLLiteral = model.createLiteral(payloadURL.toString());

		//create TiffDistribution
		String randomName = FileUtils.getRandomFileNameFromFileName("tiffDistribution");
		String tiffDistributionURI = URLUtils.BASE_URI + randomName;
		Resource tiffDistributionResource = model.createResource(tiffDistributionURI, Vocab.TiffDistribution);

		//add associated properties to TiffDistribution individual
		model.addLiteral(tiffDistributionResource, Vocab.downloadURL, payloadURLLiteral);
		model.add(tiffDistributionResource, Vocab.format, Vocab.tiff);
		
		//add TiffDistribution individual to output
		tiffDistributionResource.addProperty(Vocab.hasTiffPayload, tiffDistributionResource);
		
		return tiffDistributionResource;
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

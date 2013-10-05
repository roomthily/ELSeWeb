package edu.utep.cybershare.elseweb.service.adaptor;

import java.util.ArrayList;

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

import edu.utep.cybershare.elseweb.util.Printing;
import edu.utep.cybershare.elseweb.util.URLUtils;

@Name("TiffScenarioExtractionService")
@ContactEmail("nicholas.delrio@gmail.com")
@InputClass("http://ontology.cybershare.utep.edu/ELSEWeb/scenario.owl#WCSScenario")
@OutputClass("http://ontology.cybershare.utep.edu/ELSEWeb/scenario.owl#TiffExtractedWCSScenario")
@Description("Extracts Payload from WCS Multipart MIME Response")

public class TiffScenarioExtractionService extends SimpleSynchronousServiceServlet{
	
	private static final Logger log = Logger.getLogger(TiffScenarioExtractionService.class);
	private static final long serialVersionUID = 1L;
	
	private ArrayList<Resource> wcsCoverageDistributions;
	
	@Override
	public void processInput(Resource input, Resource output){	
		Printing.print(input.getModel());
		
		//populate distributions list
		setWCSCoverageDistributions(input);

		//create TiffScenario
		String tiffScenarioURI = URLUtils.BASE_URI + "tiffScenario";
		Resource tiffScenarioResource = output.getModel().createResource(tiffScenarioURI, Vocab.TiffScenario);
		
		//map from coverage distribution to tiff distribution
		TiffDistributionMapper mapper = new TiffDistributionMapper();

		Resource wcsCoverageDistribution;
		for(int i = 0; i < wcsCoverageDistributions.size(); i ++){
			
			wcsCoverageDistribution = wcsCoverageDistributions.get(i);
			mapper.setWCSDistributionResource(wcsCoverageDistribution);

			// get mapped distribution
			Resource wcsTiffDistribution = mapper.getTiffDistributionResource(output.getModel());

			//add to wcsScenario
			if(i == 0) output.getModel().add(tiffScenarioResource, Vocab.hasTiffDistribution1, wcsTiffDistribution);
			if(i == 1) output.getModel().add(tiffScenarioResource, Vocab.hasTiffDistribution2, wcsTiffDistribution);
			if(i == 2) output.getModel().add(tiffScenarioResource, Vocab.hasTiffDistribution3, wcsTiffDistribution);
			if(i == 3) output.getModel().add(tiffScenarioResource, Vocab.hasTiffDistribution4, wcsTiffDistribution);
			if(i == 4) output.getModel().add(tiffScenarioResource, Vocab.hasTiffDistribution5, wcsTiffDistribution);
		}
		
		//add tiffScenario to output
		output.addProperty(Vocab.hasExtractedTiffScenario, tiffScenarioResource);
		
		Printing.print(output.getModel());
	}
	
	private void setWCSCoverageDistributions(Resource wcsScenarioRequirements){
		wcsCoverageDistributions = new ArrayList<Resource>();
		Resource wcsCoverageDistribution;
		
		//get first requirement
		wcsCoverageDistribution = wcsScenarioRequirements.getPropertyResourceValue(Vocab.hasWCSCoverageDistribution1);
		addWCSCoverageDistribution(wcsCoverageDistribution);

		//get second requirement
		wcsCoverageDistribution = wcsScenarioRequirements.getPropertyResourceValue(Vocab.hasWCSCoverageDistribution2);
		addWCSCoverageDistribution(wcsCoverageDistribution);
		
		//get second requirement
		wcsCoverageDistribution = wcsScenarioRequirements.getPropertyResourceValue(Vocab.hasWCSCoverageDistribution3);
		addWCSCoverageDistribution(wcsCoverageDistribution);
		
		//get second requirement
		wcsCoverageDistribution = wcsScenarioRequirements.getPropertyResourceValue(Vocab.hasWCSCoverageDistribution4);
		addWCSCoverageDistribution(wcsCoverageDistribution);
		
		//get second requirement
		wcsCoverageDistribution = wcsScenarioRequirements.getPropertyResourceValue(Vocab.hasWCSCoverageDistribution5);
		addWCSCoverageDistribution(wcsCoverageDistribution);
	}
	
	private void addWCSCoverageDistribution(Resource wcsCoverageDistribution){
		if(wcsCoverageDistribution != null)
			wcsCoverageDistributions.add(wcsCoverageDistribution);
	}
	
	private static final class Vocab{
		
		private static Model m_model = ModelFactory.createDefaultModel();
	
		//scenario properties
		private static final String scenario = "http://ontology.cybershare.utep.edu/ELSEWeb/scenario.owl#";
		private static final Property hasWCSCoverageDistribution1 = m_model.createProperty(scenario + "hasWCSCoverageDistribution1");
		private static final Property hasWCSCoverageDistribution2 = m_model.createProperty(scenario + "hasWCSCoverageDistribution2");
		private static final Property hasWCSCoverageDistribution3 = m_model.createProperty(scenario + "hasWCSCoverageDistribution3");
		private static final Property hasWCSCoverageDistribution4 = m_model.createProperty(scenario + "hasWCSCoverageDistribution4");
		private static final Property hasWCSCoverageDistribution5 = m_model.createProperty(scenario + "hasWCSCoverageDistribution5");
		
		private static final Property hasTiffDistribution1 = m_model.createProperty(scenario + "hasTiffDistribution1");
		private static final Property hasTiffDistribution2 = m_model.createProperty(scenario + "hasTiffDistribution2");
		private static final Property hasTiffDistribution3 = m_model.createProperty(scenario + "hasTiffDistribution3");
		private static final Property hasTiffDistribution4 = m_model.createProperty(scenario + "hasTiffDistribution4");
		private static final Property hasTiffDistribution5 = m_model.createProperty(scenario + "hasTiffDistribution5");
		
		//scenario Classes
		private static final Resource TiffScenario = m_model.createResource(scenario + "TiffScenario");
		
		//output property
		private static final Property hasExtractedTiffScenario = m_model.createProperty(scenario + "hasExtractedTiffScenario");
	}
}

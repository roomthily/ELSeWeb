package edu.utep.cybershare.elseweb.service.requirements;

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

@Name("WCSSCenarioRequirementsService")
@ContactEmail("nicholas.delrio@gmail.com")
@InputClass("http://ontology.cybershare.utep.edu/ELSEWeb/scenario.owl#WCSScenarioRequirements")
@OutputClass("http://ontology.cybershare.utep.edu/ELSEWeb/scenario.owl#SatisfiedScenarioRequirements")
@Description("Generates WCSScenario from WCSScenarioRequirements")

public class WCSScenarioRequirementsService extends SimpleSynchronousServiceServlet{
	
	private static final Logger log = Logger.getLogger(WCSScenarioRequirementsService.class);
	private static final long serialVersionUID = 1L;
	
	private ArrayList<Resource> wcsDistributionRequirements;

	@Override
	public void processInput(Resource input, Resource output){
		Printing.print(input.getModel());
		
		//populate requirement list
		setWCSDistributionReqirements(input);

		//create WCSScenario
		String wcsScenarioURI = URLUtils.BASE_URI + "wcsScenario";
		Resource wcsScenarioResource = output.getModel().createResource(wcsScenarioURI, Vocab.WCSScenario);
		
		//map from specification to distribution
		WCSCoverageDistributionMapper mapper = new WCSCoverageDistributionMapper();

		Resource wcsDistributionRequirement;
		for(int i = 0; i < wcsDistributionRequirements.size(); i ++){
			
			wcsDistributionRequirement = wcsDistributionRequirements.get(i);
			mapper.setWCSDistributionRequirement(wcsDistributionRequirement);

			// get mapped distribution and related components
			Resource wcsCoverageDistribution = mapper.getDistribution(output.getModel());

			//add to wcsScenario
			if(i == 0) output.getModel().add(wcsScenarioResource, Vocab.hasWCSCoverageDistribution1, wcsCoverageDistribution);
			if(i == 1) output.getModel().add(wcsScenarioResource, Vocab.hasWCSCoverageDistribution2, wcsCoverageDistribution);
			if(i == 2) output.getModel().add(wcsScenarioResource, Vocab.hasWCSCoverageDistribution3, wcsCoverageDistribution);
			if(i == 3) output.getModel().add(wcsScenarioResource, Vocab.hasWCSCoverageDistribution4, wcsCoverageDistribution);
			if(i == 4) output.getModel().add(wcsScenarioResource, Vocab.hasWCSCoverageDistribution5, wcsCoverageDistribution);
		}
		
		//add wcsScenario to output
		output.addProperty(Vocab.hasSatisfactoryWCSScenario, wcsScenarioResource);
		Printing.print(output.getModel());
	}
	
	private void setWCSDistributionReqirements(Resource wcsScenarioRequirements){
		wcsDistributionRequirements = new ArrayList<Resource>();
		Resource wcsDistributionRequirement;
		
		//get first requirement
		wcsDistributionRequirement = wcsScenarioRequirements.getPropertyResourceValue(Vocab.hasWCSDistributionRequirement1);
		addWCSDistributionRequirement(wcsDistributionRequirement);

		//get second requirement
		wcsDistributionRequirement = wcsScenarioRequirements.getPropertyResourceValue(Vocab.hasWCSDistributionRequirement2);
		addWCSDistributionRequirement(wcsDistributionRequirement);
		
		//get second requirement
		wcsDistributionRequirement = wcsScenarioRequirements.getPropertyResourceValue(Vocab.hasWCSDistributionRequirement3);
		addWCSDistributionRequirement(wcsDistributionRequirement);
		
		//get second requirement
		wcsDistributionRequirement = wcsScenarioRequirements.getPropertyResourceValue(Vocab.hasWCSDistributionRequirement4);
		addWCSDistributionRequirement(wcsDistributionRequirement);
		
		//get second requirement
		wcsDistributionRequirement = wcsScenarioRequirements.getPropertyResourceValue(Vocab.hasWCSDistributionRequirement5);
		addWCSDistributionRequirement(wcsDistributionRequirement);
	}
	
	private void addWCSDistributionRequirement(Resource wcsDistributionRequirement){
		if(wcsDistributionRequirement != null)
			wcsDistributionRequirements.add(wcsDistributionRequirement);
	}
		
	private static final class Vocab
	{
		private static Model m_model = ModelFactory.createDefaultModel();

		//scenario properties
		private static final String scenario = "http://ontology.cybershare.utep.edu/ELSEWeb/scenario.owl#";
		private static final Property hasWCSDistributionRequirement1 = m_model.createProperty(scenario + "hasWCSDistributionRequirement1");
		private static final Property hasWCSDistributionRequirement2 = m_model.createProperty(scenario + "hasWCSDistributionRequirement2");
		private static final Property hasWCSDistributionRequirement3 = m_model.createProperty(scenario + "hasWCSDistributionRequirement3");
		private static final Property hasWCSDistributionRequirement4 = m_model.createProperty(scenario + "hasWCSDistributionRequirement4");
		private static final Property hasWCSDistributionRequirement5 = m_model.createProperty(scenario + "hasWCSDistributionRequirement5");
		
		private static final Property hasWCSCoverageDistribution1 = m_model.createProperty(scenario + "hasWCSCoverageDistribution1");
		private static final Property hasWCSCoverageDistribution2 = m_model.createProperty(scenario + "hasWCSCoverageDistribution2");
		private static final Property hasWCSCoverageDistribution3 = m_model.createProperty(scenario + "hasWCSCoverageDistribution3");
		private static final Property hasWCSCoverageDistribution4 = m_model.createProperty(scenario + "hasWCSCoverageDistribution4");
		private static final Property hasWCSCoverageDistribution5 = m_model.createProperty(scenario + "hasWCSCoverageDistribution5");
		
		//scenario Classes
		private static final Resource WCSScenario = m_model.createResource(scenario + "WCSScenario");
		
		private static final Property hasSatisfactoryWCSScenario = m_model.createProperty(scenario + "hasSatisfactoryWCSScenario");
	}
}

package edu.utep.cybershare.elseweb.service.mapper;

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

@Name("WCSCoverageDatasetMapper")
@ContactEmail("nicholas.delrio@gmail.com")
@InputClass("http://ontology.cybershare.utep.edu/ELSEWeb/scenario.owl#DistributionSpecification")
@OutputClass("http://ontology.cybershare.utep.edu/ELSEWeb/scenario.owl#SatisfiedDistributionSpecification")
@Description("Maps DistributionsSpecifications to WCSCoverageDistributions")

public class WCSCoverageDistributionService extends SimpleSynchronousServiceServlet{
	
	private static final Logger log = Logger.getLogger(WCSCoverageDistributionService.class);
	private static final long serialVersionUID = 1L;
	
	@Override
	public void processInput(Resource input, Resource output){
		Printing.print(input.getModel());

		//map from specification to distribution
		WCSCoverageDistributionMapper mapper = new WCSCoverageDistributionMapper();
		mapper.setWCSDistributionSpecification(input);

		// get mapped distribution and related components
		Resource wcsCoverageDistribution = mapper.getDistribution();
		Resource format = mapper.getFormat();
		Literal accessURL = mapper.getAccessURL();
		Literal downloadURL = mapper.getDownloadURL();
		
		//add components to distribution
		if(wcsCoverageDistribution == null)
			System.out.println("distribution is null");
		
		output.getModel().add(wcsCoverageDistribution, Vocab.format, format);
		output.getModel().add(wcsCoverageDistribution, Vocab.accessURL, accessURL);
		output.getModel().add(wcsCoverageDistribution, Vocab.downloadURL, downloadURL);
		
		//add distribution to output
		output.addProperty(Vocab.distribution, wcsCoverageDistribution);
		
		Printing.print(output.getModel());
	}
	
		
	private static final class Vocab
	{
		private static Model m_model = ModelFactory.createDefaultModel();

		//DCAT properties
		private static final String dcat = "http://www.w3.org/ns/dcat#";
		public static final Property distribution = m_model.createProperty(dcat + "distribution");
		public static final Property accessURL = m_model.createProperty(dcat + "accessURL");
		public static final Property downloadURL = m_model.createProperty(dcat + "downloadURL");
		
		//DCMI properties
		private static final String dcmi = "http://purl.org/dc/terms/";
		public static final Property format = m_model.createProperty(dcmi + "format");
	}
}

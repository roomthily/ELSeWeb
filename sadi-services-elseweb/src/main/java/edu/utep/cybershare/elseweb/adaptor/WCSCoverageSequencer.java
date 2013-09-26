package edu.utep.cybershare.elseweb.adaptor;

import java.util.ArrayList;
import java.util.List;

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
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;

import edu.utep.cybershare.elseweb.util.Printing;

@Name("WCSPayloadExtractor")
@ContactEmail("nicholas.delrio@gmail.com")
@InputClass("http://ontology.cybershare.utep.edu/ELSEWeb/edac.owl#WCSCoverageSetQualified")
@OutputClass("http://ontology.cybershare.utep.edu/ELSEWeb/edac.owl#WCSCoverageSequence")
@Description("WCS Multipart MIME Payload Extractor")

public class WCSCoverageSequencer extends SimpleSynchronousServiceServlet
{
	private static final Logger log = Logger.getLogger(WCSPayloadExtractor.class);
	private static final long serialVersionUID = 1L;

	@Override
	public void processInput(Resource input, Resource output)
	{
		Printing.print(input.getModel());
	
		List<Resource> orderedWCSCoverages = this.getWCSCoveragesInOrder(input);
		for(int i = 0; i < 5; i ++){
			if(i == 0)
				output.addProperty(Vocab.dataset1, orderedWCSCoverages.get(i));
			else if(i == 2)
				output.addProperty(Vocab.dataset2, orderedWCSCoverages.get(i));
			else if(i == 3)
				output.addProperty(Vocab.dataset3, orderedWCSCoverages.get(i));
			else if(i == 4)
				output.addProperty(Vocab.dataset4, orderedWCSCoverages.get(i));
			else if(i == 5)
				output.addProperty(Vocab.dataset5, orderedWCSCoverages.get(i));
		}		
	}
		
	private List<Resource> getWCSCoveragesInOrder(Resource input){
		StmtIterator iterator = input.listProperties(Vocab.dataset);
		Statement statement;
		ArrayList<Resource> orderedWCSCoverages = new ArrayList<Resource>();
		for(int i = 0; i < 5; i ++){
			statement = iterator.next();
			if(i == 0)
				orderedWCSCoverages.add(this.getResourceOfWCSCoverage(statement, Vocab.WCSCoverage1));
			else if(i == 2)
				orderedWCSCoverages.add(this.getResourceOfWCSCoverage(statement, Vocab.WCSCoverage2));
			else if(i == 3)
				orderedWCSCoverages.add(this.getResourceOfWCSCoverage(statement, Vocab.WCSCoverage3));
			else if(i == 4)
				orderedWCSCoverages.add(this.getResourceOfWCSCoverage(statement, Vocab.WCSCoverage4));
			else if(i == 5)
				orderedWCSCoverages.add(this.getResourceOfWCSCoverage(statement, Vocab.WCSCoverage5));
		}
		return orderedWCSCoverages;
	}
	
	private Resource getResourceOfWCSCoverage(Statement statement, Resource wcsCoverageClass){
		Resource resource = statement.getSubject();
		Resource resourceClass = resource.getPropertyResourceValue(Vocab.type);
		if(resourceClass != null && resourceClass.getURI().equals(wcsCoverageClass))
			return resource;
		return null;
	}

	private static final class Vocab
	{
		public static Model m_model = ModelFactory.createDefaultModel();

		public static final Property type = m_model.createProperty("http://www.w3.org/1999/02/22-rdf-syntax-ns#");
		
		public static final Resource WCSCoverage1 = m_model.createResource("http://ontology.cybershare.utep.edu/ELSEWeb/edac.owl#WCSCoverage1");		
		public static final Resource WCSCoverage2 = m_model.createResource("http://ontology.cybershare.utep.edu/ELSEWeb/edac.owl#WCSCoverage2");		
		public static final Resource WCSCoverage3 = m_model.createResource("http://ontology.cybershare.utep.edu/ELSEWeb/edac.owl#WCSCoverage3");		
		public static final Resource WCSCoverage4 = m_model.createResource("http://ontology.cybershare.utep.edu/ELSEWeb/edac.owl#WCSCoverage4");		
		public static final Resource WCSCoverage5 = m_model.createResource("http://ontology.cybershare.utep.edu/ELSEWeb/edac.owl#WCSCoverage5");		
		
		public static final Property dataset = m_model.createProperty("http://www.w3.org/ns/dcat#dataset");
		public static final Property dataset1 = m_model.createProperty("http://ontology.cybershare.utep.edu/ELSEWeb/edac.owl#dataset1");
		public static final Property dataset2 = m_model.createProperty("http://ontology.cybershare.utep.edu/ELSEWeb/edac.owl#dataset2");
		public static final Property dataset3 = m_model.createProperty("http://ontology.cybershare.utep.edu/ELSEWeb/edac.owl#dataset3");
		public static final Property dataset4 = m_model.createProperty("http://ontology.cybershare.utep.edu/ELSEWeb/edac.owl#dataset4");
		public static final Property dataset5 = m_model.createProperty("http://ontology.cybershare.utep.edu/ELSEWeb/edac.owl#dataset5");
	}
}

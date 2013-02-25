package edu.utep.cybershare.elseweb.lifemapper;

import java.io.StringWriter;
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
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
//import com.hp.hpl.jena.rdf.model.Statement;
//import com.hp.hpl.jena.rdf.model.StmtIterator;

import edu.utep.cybershare.elseweb.lifemapper.client.LifemapperExperiment;

@Name("Lifemapper")
@ContactEmail("nicholas.delrio@gmail.com")
@InputClass("http://openvisko.org/rdf/ontology-elseweb/elseweb.owl#FullySpecifiedLifemapperExperiment")
@OutputClass("http://openvisko.org/rdf/ontology-elseweb/elseweb.owl#CompletedLifemapperExperiment")
@Description("Lifemapper Species Modeling")

public class Lifemapper extends SimpleSynchronousServiceServlet
{
	private static final Logger log = Logger.getLogger(Lifemapper.class);
	private static final long serialVersionUID = 1L;

	@Override
	public void processInput(Resource input, Resource output)
	{
		
		StringWriter wtr = new StringWriter();
		input.getModel().write(wtr, "RDF/XML-ABBREV");
		System.out.println(wtr.toString());
		
		String uname = "elseweb";
		String pword = "elseweb1";
		
		LifemapperExperiment experiment = new LifemapperExperiment(uname, pword);
		
		//extract layers from experiment
		Resource scenarioLayerSet = input.getProperty(Vocab.hasExperimentalScenarioLayerSet).getResource();
		StmtIterator coverageStatements = scenarioLayerSet.listProperties(Vocab.hasWCSCoverage);
		Statement coverageStatement;
		Resource coverageResource;
		String coveragePayloadURLString;
		URL coveragePayloadURL;

		while(coverageStatements.hasNext()){
			coverageStatement = coverageStatements.next();
			coverageResource = coverageStatement.getResource();
			coveragePayloadURLString = coverageResource.getProperty(Vocab.hasWCSCoveragePayloadURL).getLiteral().getString();

			
			try{
				coveragePayloadURL = new URL(coveragePayloadURLString);
				log.debug("Adding scenario layer: " + coveragePayloadURL);
				System.out.println("Adding scenario layer: " + coveragePayloadURL);
				experiment.addScenarioLayer(coveragePayloadURL);
			}
			catch(Exception e){
				log.error(e.getMessage());
			}
		}
		
		//extract algorithm
		String algorithmName = input.getProperty(Vocab.hasModelingAlgorithm).getLiteral().getString();
		log.debug("Setting algorithm: " + algorithmName);
		experiment.setAlgorithm(algorithmName);
		
		//extract units
		String scenarioLayerUnits = input.getProperty(Vocab.hasScenarioLayerUnits).getLiteral().getString();
		log.debug("Setting layer units: " + scenarioLayerUnits);
		experiment.setScenarioLayerUnits(scenarioLayerUnits);
		
		//submitt experiment
		URL experimentResultURL = experiment.submitExperiment();

		log.debug("Experiment result can be found at: " + experimentResultURL);
		//create output
		try{output.addProperty(Vocab.hasLifemapperModelURL, experimentResultURL.toString());}
		catch(Exception e){log.error(e.getMessage());}
	}

	@SuppressWarnings("unused")
	private static final class Vocab
	{
		private static Model m_model = ModelFactory.createDefaultModel();
		
		public static final Property hasFormat = m_model.createProperty("http://openvisko.org/rdf/ontology-elseweb/elseweb.owl#hasFormat");
		public static final Property hasScenarioLayerUnits = m_model.createProperty("http://openvisko.org/rdf/ontology-elseweb/elseweb.owl#hasScenarioLayerUnits");
		public static final Property hasWCSCoveragePayloadURL = m_model.createProperty("http://openvisko.org/rdf/ontology-elseweb/elseweb.owl#hasWCSCoveragePayloadURL");
		public static final Property hasWCSCoverage = m_model.createProperty("http://openvisko.org/rdf/ontology-elseweb/elseweb.owl#hasWCSCoverage");
		public static final Property hasExperimentalScenarioLayerSet = m_model.createProperty("http://openvisko.org/rdf/ontology-elseweb/elseweb.owl#hasExperimentalScenarioLayerSet");
		public static final Property hasLifemapperModelURL = m_model.createProperty("http://openvisko.org/rdf/ontology-elseweb/elseweb.owl#hasLifemapperModelURL");
		public static final Property hasModelingAlgorithm = m_model.createProperty("http://openvisko.org/rdf/ontology-elseweb/elseweb.owl#hasModelingAlgorithm");
		public static final Property hasWCSGetCoverageURL = m_model.createProperty("http://openvisko.org/rdf/ontology-elseweb/elseweb.owl#hasWCSGetCoverageURL");
		public static final Resource CompletedLifemapperExperiment = m_model.createResource("http://openvisko.org/rdf/ontology-elseweb/elseweb.owl#CompletedLifemapperExperiment");
		public static final Resource WCSCoverage = m_model.createResource("http://openvisko.org/rdf/ontology-elseweb/elseweb.owl#WCSCoverage");
		public static final Resource Literal = m_model.createResource("http://www.w3.org/2000/01/rdf-schema#Literal");
		public static final Resource ExperimentalScenarioLayerSet = m_model.createResource("http://openvisko.org/rdf/ontology-elseweb/elseweb.owl#ExperimentalScenarioLayerSet");
		public static final Resource FullySpecifiedLifemapperExperiment = m_model.createResource("http://openvisko.org/rdf/ontology-elseweb/elseweb.owl#FullySpecifiedLifemapperExperiment");
	}
}

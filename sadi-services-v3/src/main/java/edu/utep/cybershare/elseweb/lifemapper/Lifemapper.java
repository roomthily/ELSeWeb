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
@InputClass("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/lifemapper-v3.owl#Experiment_Stage3")
@OutputClass("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/lifemapper-v3.owl#Experiment_Stage4")
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
		Resource scenarioLayerSet = input.getProperty(Vocab.hasWCSCoverageSet).getResource();

		Resource layer1 = scenarioLayerSet.getProperty(Vocab.hasWCSCoveragePayload1).getResource();
		Resource layer2 = scenarioLayerSet.getProperty(Vocab.hasWCSCoveragePayload2).getResource();
		Resource layer3 = scenarioLayerSet.getProperty(Vocab.hasWCSCoveragePayload3).getResource();
		Resource layer4 = scenarioLayerSet.getProperty(Vocab.hasWCSCoveragePayload4).getResource();
		Resource layer5 = scenarioLayerSet.getProperty(Vocab.hasWCSCoveragePayload5).getResource();
		
		String layer1URLString = layer1.getProperty(Vocab.hasWCSCoveragePayloadURL).getString();
		String layer2URLString = layer1.getProperty(Vocab.hasWCSCoveragePayloadURL).getString();
		String layer3URLString = layer1.getProperty(Vocab.hasWCSCoveragePayloadURL).getString();
		String layer4URLString = layer1.getProperty(Vocab.hasWCSCoveragePayloadURL).getString();
		String layer5URLString = layer1.getProperty(Vocab.hasWCSCoveragePayloadURL).getString();
		
		URL layerURL1;
		URL layerURL2;
		URL layerURL3;
		URL layerURL4;
		URL layerURL5;
		
		try{
			layerURL1 = new URL(layer1URLString);
			layerURL2 = new URL(layer2URLString);
			layerURL3 = new URL(layer3URLString);
			layerURL4 = new URL(layer4URLString);
			layerURL5 = new URL(layer5URLString);
			
			experiment.addScenarioLayer(layerURL1);
			experiment.addScenarioLayer(layerURL2);
			experiment.addScenarioLayer(layerURL3);
			experiment.addScenarioLayer(layerURL4);
			experiment.addScenarioLayer(layerURL5);
		}
		catch(Exception e){e.printStackTrace();}
		
		//extract algorithm
		Resource algorithmResource = input.getProperty(Vocab.hasModelingAlgorithm).getResource();
		String algorithmName = algorithmResource.getProperty(Vocab.hasName).getLiteral().getString();
		experiment.setAlgorithm(algorithmName);

		//extract occurrenceSetID
		String occurrenceSetIDString = input.getProperty(Vocab.hasOccurrenceSetID).getLiteral().getString();
		int occurrenceSetID = Integer.parseInt(occurrenceSetIDString);
		experiment.setOccurrenceSetID(occurrenceSetID);
		
		//extract units
		String scenarioLayerUnits = input.getProperty(Vocab.hasScenarioLayerUnits).getLiteral().getString();
		experiment.setScenarioLayerUnits(scenarioLayerUnits);
		
		//submit experiment
		URL experimentResultURL = experiment.submitExperiment();

		//create output
		String modelURI = "http://edac.elseweb.cybershare.utep.edu#Lifemapper_Model";
		Resource lifemapperModel = output.getModel().createResource(modelURI, Vocab.Model);		

		try{
			lifemapperModel.addProperty(Vocab.hasModelURL, experimentResultURL.toString());
			output.addProperty(Vocab.hasModel, lifemapperModel);
		}
		catch(Exception e){log.error(e.getMessage());}
	}

	private static final class Vocab
	{
		private static Model m_model = ModelFactory.createDefaultModel();
		
		public static final Property hasOccurrenceSetID = m_model.createProperty("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/lifemapper-v3.owl#hasOccurrenceSetID");
		public static final Property hasName = m_model.createProperty("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/lifemapper-v3.owl#hasName");
		public static final Property hasModelingAlgorithm = m_model.createProperty("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/lifemapper-v3.owl#hasModelingAlgorithm");
		public static final Property hasModelURL = m_model.createProperty("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/lifemapper-v3.owl#hasModelURL");
		public static final Property hasModel = m_model.createProperty("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/lifemapper-v3.owl#hasModel");
		public static final Property hasScenarioLayerUnits = m_model.createProperty("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/lifemapper-v3.owl#hasScenarioLayerUnits");

		public static final Resource Model = m_model.createResource("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/lifemapper-v3.owl#Model");
		
		public static final Property hasWCSCoverageSet = m_model.createProperty("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac-v3.owl#hasWCSCoverageSet");

		public static final Property hasWCSCoveragePayloadURL = m_model.createProperty("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac-v3.owl#hasWCSCoveragePayloadURL");
		public static final Property hasWCSCoveragePayload1 = m_model.createProperty("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac-v3.owl#hasWCSCoveragePayload1");
		public static final Property hasWCSCoveragePayload2 = m_model.createProperty("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac-v3.owl#hasWCSCoveragePayload2");
		public static final Property hasWCSCoveragePayload3 = m_model.createProperty("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac-v3.owl#hasWCSCoveragePayload3");
		public static final Property hasWCSCoveragePayload4 = m_model.createProperty("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac-v3.owl#hasWCSCoveragePayload4");
		public static final Property hasWCSCoveragePayload5 = m_model.createProperty("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac-v3.owl#hasWCSCoveragePayload5");		
	}
}

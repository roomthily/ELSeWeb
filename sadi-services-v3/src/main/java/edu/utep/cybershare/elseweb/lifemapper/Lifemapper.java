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
		String layerURL1String = scenarioLayerSet.getProperty(Vocab.hasWCSCoveragePayloadURL1).getString();
		String layerURL2String = scenarioLayerSet.getProperty(Vocab.hasWCSCoveragePayloadURL2).getString();
		String layerURL3String = scenarioLayerSet.getProperty(Vocab.hasWCSCoveragePayloadURL3).getString();
		String layerURL4String = scenarioLayerSet.getProperty(Vocab.hasWCSCoveragePayloadURL4).getString();
		String layerURL5String = scenarioLayerSet.getProperty(Vocab.hasWCSCoveragePayloadURL5).getString();
		
		URL layerURL1;
		URL layerURL2;
		URL layerURL3;
		URL layerURL4;
		URL layerURL5;
		
		try{
			layerURL1 = new URL(layerURL1String);
			layerURL2 = new URL(layerURL2String);
			layerURL3 = new URL(layerURL3String);
			layerURL4 = new URL(layerURL4String);
			layerURL5 = new URL(layerURL5String);
			
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
		try{output.addProperty(Vocab.hasLifemapperModelURL, experimentResultURL.toString());}
		catch(Exception e){log.error(e.getMessage());}
	}

	private static final class Vocab
	{
		private static Model m_model = ModelFactory.createDefaultModel();
		
		public static final Property hasOccurrenceSetID = m_model.createProperty("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/lifemapper-v3.owl#hasOccurrenceSetID");
		public static final Property hasName = m_model.createProperty("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/lifemapper-v3.owl#hasName");
		public static final Property hasModelingAlgorithm = m_model.createProperty("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/lifemapper-v3.owl#hasModelingAlgorithm");
		public static final Property hasLifemapperModelURL = m_model.createProperty("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/lifemapper-v3.owl#hasLifemapperModelURL");
		public static final Property hasScenarioLayerUnits = m_model.createProperty("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/lifemapper-v3.owl#hasScenarioLayerUnits");

		public static final Property hasWCSCoverageSet = m_model.createProperty("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac-v3.owl#hasWCSCoverageSet");
		public static final Property hasWCSCoveragePayloadURL1 = m_model.createProperty("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac-v3.owl#hasWCSCoveragePayloadURL1");
		public static final Property hasWCSCoveragePayloadURL2 = m_model.createProperty("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac-v3.owl#hasWCSCoveragePayloadURL2");
		public static final Property hasWCSCoveragePayloadURL3 = m_model.createProperty("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac-v3.owl#hasWCSCoveragePayloadURL3");
		public static final Property hasWCSCoveragePayloadURL4 = m_model.createProperty("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac-v3.owl#hasWCSCoveragePayloadURL4");
		public static final Property hasWCSCoveragePayloadURL5 = m_model.createProperty("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac-v3.owl#hasWCSCoveragePayloadURL5");		
	}
}

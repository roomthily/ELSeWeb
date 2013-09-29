package edu.utep.cybershare.elseweb.lifemapper;

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

import edu.utep.cybershare.elseweb.lifemapper.proxy.LifemapperExperiment;

import edu.utep.cybershare.elseweb.util.Printing;

@Name("LifemapperService")
@ContactEmail("nicholas.delrio@gmail.com")
@InputClass("http://ontology.cybershare.utep.edu/ELSEWeb/lifemapper.owl#FullySpecifiedExperiment")
@OutputClass("http://ontology.cybershare.utep.edu/ELSEWeb/lifemapper.owl#CompletedExperiment")
@Description("Lifemapper Species Modeling Service")

public class LifemapperService extends SimpleSynchronousServiceServlet
{
	private static final Logger log = Logger.getLogger(LifemapperService.class);
	private static final long serialVersionUID = 1L;

	@Override
	public void processInput(Resource input, Resource output)
	{
		String baseURI = "http://lifemapper.org/";
	
		Printing.print(input.getModel());
		
		String uname = "elseweb";
		String pword = "elseweb1";
		
		LifemapperExperiment experiment = new LifemapperExperiment(uname, pword);
		
		//extract layers from experiment
		Resource scenarioLayers = input.getPropertyResourceValue(Vocab.hasScenarioLayers);

		Resource distribution1 = getDistribution(scenarioLayers.getPropertyResourceValue(Vocab.dataset1));
		Resource distribution2 = getDistribution(scenarioLayers.getPropertyResourceValue(Vocab.dataset2));
		Resource distribution3 = getDistribution(scenarioLayers.getPropertyResourceValue(Vocab.dataset3));
		Resource distribution4 = getDistribution(scenarioLayers.getPropertyResourceValue(Vocab.dataset4));
		Resource distribution5 = getDistribution(scenarioLayers.getPropertyResourceValue(Vocab.dataset5));
		
		String layer1URLString = distribution1.getProperty(Vocab.hasWCSCoveragePayloadURL).getString();
		String layer2URLString = distribution2.getProperty(Vocab.hasWCSCoveragePayloadURL).getString();
		String layer3URLString = distribution3.getProperty(Vocab.hasWCSCoveragePayloadURL).getString();
		String layer4URLString = distribution4.getProperty(Vocab.hasWCSCoveragePayloadURL).getString();
		String layer5URLString = distribution5.getProperty(Vocab.hasWCSCoveragePayloadURL).getString();
		
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
		String algorithmName = algorithmResource.getProperty(Vocab.hasAlgorithmName).getLiteral().getString();
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
		Resource lifemapperModel = output.getModel().createResource(baseURI + "Model", Vocab.Model);
		lifemapperModel.addProperty(Vocab.hasModelURL, experimentResultURL.toString());
		output.addProperty(Vocab.hasModel, lifemapperModel);
	}
	
	private Resource getDistribution(Resource wcsCoveragePayload){
		return wcsCoveragePayload.getPropertyResourceValue(Vocab.distribution);
	}

	private static final class Vocab
	{
		private static Model m_model = ModelFactory.createDefaultModel();
		
		//FullySpecifiedExperiment Properties
		public static final Property hasOccurrenceSetID = m_model.createProperty("http://ontology.cybershare.utep.edu/ELSEWeb/lifemapper.owl#hasOccurrenceSetID");
		public static final Property hasAlgorithmName = m_model.createProperty("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/lifemapper-v3.owl#hasAlgorithmName");
		public static final Property hasModelingAlgorithm = m_model.createProperty("http://ontology.cybershare.utep.edu/ELSEWeb/lifemapper.owl#hasModelingAlgorithm");
		public static final Property hasModelURL = m_model.createProperty("http://ontology.cybershare.utep.edu/ELSEWeb/lifemapper.owl#hasModelURL");
		public static final Property hasModel = m_model.createProperty("http://ontology.cybershare.utep.edu/ELSEWeb/lifemapper.owl#hasModel");
		public static final Property hasScenarioLayerUnits = m_model.createProperty("http://ontology.cybershare.utep.edu/ELSEWeb/lifemapper.owl#hasScenarioLayerUnits");

		//WCSCoverageSequence Properties
		public static final Property hasScenarioLayers = m_model.createProperty("http://ontology.cybershare.utep.edu/ELSEWeb/lifemapper.owl#hasScenarioLayers");
		public static final Property hasWCSCoveragePayloadURL = m_model.createProperty("http://ontology.cybershare.utep.edu/ELSEWeb/edac.owl#hasWCSCoveragePayloadURL");
		public static final Property dataset1 = m_model.createProperty("http://ontology.cybershare.utep.edu/ELSEWeb/edac.owl#dataset1");
		public static final Property dataset2 = m_model.createProperty("http://ontology.cybershare.utep.edu/ELSEWeb/edac.owl#dataset2");
		public static final Property dataset3 = m_model.createProperty("http://ontology.cybershare.utep.edu/ELSEWeb/edac.owl#dataset3");
		public static final Property dataset4 = m_model.createProperty("http://ontology.cybershare.utep.edu/ELSEWeb/edac.owl#dataset4");
		public static final Property dataset5 = m_model.createProperty("http://ontology.cybershare.utep.edu/ELSEWeb/edac.owl#dataset5");
	
		public static final Property distribution = m_model.createProperty("http://www.w3.org/ns/dcat#");
		
		//CompletedExperiment Properties
		public static final Resource Model = m_model.createResource("http://ontology.cybershare.utep.edu/ELSEWeb/lifemapper.owl#Model");	
	}
}

package edu.utep.cybershare.elseweb.service.lifemapper;

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

import edu.utep.cybershare.elseweb.service.lifemapper.proxy.Lifemapper;
import edu.utep.cybershare.elseweb.util.Printing;

@Name("LifemapperService")
@ContactEmail("nicholas.delrio@gmail.com")
@InputClass("http://ontology.cybershare.utep.edu/ELSEWeb/lifemapper.owl#FullySpecifiedExperiment")
@OutputClass("http://ontology.cybershare.utep.edu/ELSEWeb/lifemapper.owl#CompletedExperiment")
@Description("Lifemapper Species Modeling Service")

public class LifemapperService extends SimpleSynchronousServiceServlet{
	
	private static final Logger log = Logger.getLogger(LifemapperService.class);
	private static final long serialVersionUID = 1L;

	@Override
	public void processInput(Resource input, Resource output){
		
		String baseURI = "http://lifemapper.org/";
	
		Printing.print(input.getModel());
		
		String uname = "elseweb";
		String pword = "elseweb1";
		
		Lifemapper experiment = new Lifemapper(uname, pword);
		
		//extract LifemapperScenario from experiment
		Resource scenarioLayers = input.getProperty(Vocab.hasScenario).getResource();

		Resource distribution1 = getDistribution(scenarioLayers.getPropertyResourceValue(Vocab.dataset1));
		Resource distribution2 = getDistribution(scenarioLayers.getPropertyResourceValue(Vocab.dataset2));
		Resource distribution3 = getDistribution(scenarioLayers.getPropertyResourceValue(Vocab.dataset3));
		Resource distribution4 = getDistribution(scenarioLayers.getPropertyResourceValue(Vocab.dataset4));
		Resource distribution5 = getDistribution(scenarioLayers.getPropertyResourceValue(Vocab.dataset5));
		
		String layer1URLString = distribution1.getProperty(Vocab.downloadTiffURL).getString();
		String layer2URLString = distribution2.getProperty(Vocab.downloadTiffURL).getString();
		String layer3URLString = distribution3.getProperty(Vocab.downloadTiffURL).getString();
		String layer4URLString = distribution4.getProperty(Vocab.downloadTiffURL).getString();
		String layer5URLString = distribution5.getProperty(Vocab.downloadTiffURL).getString();
		
		URL layerURL1 = null;
		URL layerURL2 = null;
		URL layerURL3 = null;
		URL layerURL4 = null;
		URL layerURL5 = null;
		
		try{
			layerURL1 = new URL(layer1URLString);
			layerURL2 = new URL(layer2URLString);
			layerURL3 = new URL(layer3URLString);
			layerURL4 = new URL(layer4URLString);
			layerURL5 = new URL(layer5URLString);			
		}
		catch(Exception e){e.printStackTrace();}

		//add scenario layers to proxy
		experiment.addScenarioLayer(layerURL1);
		experiment.addScenarioLayer(layerURL2);
		experiment.addScenarioLayer(layerURL3);
		experiment.addScenarioLayer(layerURL4);
		experiment.addScenarioLayer(layerURL5);
		
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

	private static final class Vocab{
		
		private static Model m_model = ModelFactory.createDefaultModel();
				
		//Lifemapper properties and resources
		private static final String lifemapper = "http://ontology.cybershare.utep.edu/ELSEWeb/lifemapper.owl#";
		public static final Property hasOccurrenceSetID = m_model.createProperty(lifemapper + "hasOccurrenceSetID");
		public static final Property hasAlgorithmName = m_model.createProperty(lifemapper + "hasAlgorithmName");
		public static final Property hasModelingAlgorithm = m_model.createProperty(lifemapper + "hasModelingAlgorithm");
		public static final Property hasModelURL = m_model.createProperty(lifemapper + "hasModelURL");
		public static final Property hasModel = m_model.createProperty(lifemapper + "hasModel");
		public static final Property hasScenarioLayerUnits = m_model.createProperty(lifemapper + "hasScenarioLayerUnits");
		public static final Property hasScenario = m_model.createProperty(lifemapper + "hasScenario");
		public static final Resource Model = m_model.createResource(lifemapper + "Model");
		
		//ELSEWEB ontology properties
		private static final String elseweb = "http://ontology.cybershare.utep.edu/ELSEWeb/elsewebdata.owl#";
		public static final Property downloadTiffURL = m_model.createProperty(elseweb + "downloadTiffURL");
		public static final Property dataset1 = m_model.createProperty(elseweb + "dataset1");
		public static final Property dataset2 = m_model.createProperty(elseweb + "dataset2");
		public static final Property dataset3 = m_model.createProperty(elseweb + "dataset3");
		public static final Property dataset4 = m_model.createProperty(elseweb + "dataset4");
		public static final Property dataset5 = m_model.createProperty(elseweb + "dataset5");
	
		//DCAT properties
		private static final String dcat = "http://www.w3.org/ns/dcat#";
		public static final Property distribution = m_model.createProperty(dcat + "distribution");	
	}
}

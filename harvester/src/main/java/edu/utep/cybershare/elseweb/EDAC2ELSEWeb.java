package edu.utep.cybershare.elseweb;

import java.io.File;
import java.io.FileWriter;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;

import edu.utep.cybershare.elseweb.build.WCSGetCoverageParameters;
import edu.utep.cybershare.elseweb.build.WCSGetCoverageURL;
import edu.utep.cybershare.elseweb.build.source.edac.WCSDigest;
import edu.utep.cybershare.elseweb.build.source.edac.WCSDigests;
import edu.utep.cybershare.elseweb.oboe.Characteristic;
import edu.utep.cybershare.elseweb.oboe.Entity;
import edu.utep.cybershare.elseweb.oboe.Measurement;
import edu.utep.cybershare.elseweb.oboe.Observation;
import edu.utep.cybershare.elseweb.ogc.wcs.Coverage;
import edu.utep.cybershare.elseweb.prov.Agent;

public class EDAC2ELSEWeb {

	private static final String BASE_URL = "https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/data/";
	private static final String DOCUMENT_NAME = "edac-data.owl";
	private static final String DOCUMENT_URL = BASE_URL + DOCUMENT_NAME;
	
	private static final String DUMP_DIR = "../documents/semantic-web/rdf/data/";

	
	public static void main(String[] args){
		WCSDigests wcsDigests = new WCSDigests(10, 0);
		
		String baseURI = DOCUMENT_URL + "#";
		
		OntModel aModel = ModelFactory.createOntologyModel();
		for(WCSDigest digest : wcsDigests){
			
			//create Entity
			Resource entityResource = Entity.createEntityResource(baseURI, digest.getThemekey(), aModel);
			
			//create Observation
			Resource observationResource = Observation.getObservationResource(baseURI, aModel);
			
			//create Measurement
			Resource measurementResource = Measurement.getMeasurementResource(baseURI, aModel);
			
			//create Characteristic
			Resource characteristicResource = Characteristic.createCharacteristicResource(baseURI, digest.getThemekey(), aModel);
			
			//create Coverage (to be replaced by DCAT:Dataset) soon
			Resource coverageResource = getCoverageFromDigest(digest, aModel);
			
			//create Agent
			Resource agentResource = Agent.getAgentResource(baseURI, aModel, digest);
			
			//connect Observation to Entity
			aModel.add(observationResource, Vocab.ofEntity, entityResource);
			
			//connect Observation to Measurement
			aModel.add(observationResource, Vocab.hasMeasurement, measurementResource);
			
			//connect Measurement to Characteristic
			aModel.add(measurementResource, Vocab.ofCharacteristic, characteristicResource);
			
			//connect Measurement to Agent
			aModel.add(measurementResource, Vocab.wasAssociatedWith, agentResource);
			
			//connect Coverage to Measurement
			aModel.add(coverageResource, Vocab.wasGeneratedBy, measurementResource);
		}	
		
		File dumpFile = new File(DUMP_DIR + DOCUMENT_NAME);
		dumpRDF(dumpFile, aModel);
		
		System.out.println("dumped coverage set data at: " + dumpFile.getAbsolutePath());
	}
	
	public static Resource getCoverageFromDigest(WCSDigest wcsDigest, Model model){	
		// set wcs getCoverage parameters
		WCSGetCoverageParameters params = new WCSGetCoverageParameters();
		params.setBBox(
				wcsDigest.getLeftLongitude(),
				wcsDigest.getUpperLatitude(),
				wcsDigest.getRightLongitude(),
				wcsDigest.getUpperLatitude());
		
		double width = 600;
		double height = 600;
		params.setWidth(width);
		params.setHeight(height);
		
		String format = "image/tiff";
		params.setFormat(format);
		
		String coverage = wcsDigest.getName();
		params.setCoverage(coverage);
		
		//construct the parameterized URL from the wcs endpoint and the parameters
		WCSGetCoverageURL getCoverage = new WCSGetCoverageURL(wcsDigest.getWcsServiceEndpoint().toString(), params);
					
		String baseURI = DOCUMENT_URL + "#" + wcsDigest.getName();
		
		Coverage ogcCoverage = new Coverage(baseURI, model);
		ogcCoverage.addSource(Coverage.Source.PRISM);
		ogcCoverage.addMeasurement(Coverage.Measurement.MinTemperatureNormals);
		ogcCoverage.addGetCoverageRequestURL(getCoverage);
		ogcCoverage.addMIMEFormat();
		ogcCoverage.addDuration(wcsDigest.getStartDate(), wcsDigest.getEndDate());
		ogcCoverage.addRegion(
				wcsDigest.getLeftLongitude(),
				wcsDigest.getRightLongitude(),
				wcsDigest.getLowerLatitude(),
				wcsDigest.getUpperLatitude());
		
		return ogcCoverage.getWCSCoverage();
	}
	
	private static void dumpRDF(File file, Model aModel){
		try{
			FileWriter writer = new FileWriter(file);
			aModel.write(writer);
			writer.close();
		}catch(Exception e){e.printStackTrace();}
	}
	
	private static final class Vocab{
		
		private static Model m_model = ModelFactory.createDefaultModel();
		
		// OBOE properties
		private static final Property ofEntity = m_model.createProperty("http://ecoinformatics.org/oboe/oboe.1.0/oboe-core.owl#ofEntity");
		private static final Property hasMeasurement = m_model.createProperty("http://ecoinformatics.org/oboe/oboe.1.0/oboe-core.owl#hasMeasurement");
		private static final Property ofCharacteristic = m_model.createProperty("http://ecoinformatics.org/oboe/oboe.1.0/oboe-core.owl#ofCharacteristic");

		// PROV properties
		private static final Property wasGeneratedBy = m_model.createProperty("http://www.w3.org/ns/prov-o/wasGeneratedBy");
		private static final Property wasAssociatedWith = m_model.createProperty("http://www.w3.org/ns/prov-o/wasAssociatedWith");
	}
}

package edu.utep.cybershare.elseweb.simulation.service;

import com.hp.hpl.jena.rdf.model.Resource;

import edu.utep.cybershare.elseweb.prov.ServiceExecution;

public class TiffScenarioExtractionService implements Service {

	private static final String inputClassURI = "http://ontology.cybershare.utep.edu/ELSEWeb/scenario.owl#WCSScenario";
	private static final String outputClassURI = "http://ontology.cybershare.utep.edu/ELSEWeb/scenario.owl#TiffExtractedWCSScenario";
	private static final String serviceName = "TiffScenarioExtractionService";
	
	@Override
	public void processInput(Resource input, Resource output) {
		ServiceExecution anExecution = new ServiceExecution(serviceName, input, output, inputClassURI, outputClassURI);
		anExecution.linkUp();
		anExecution.close();
	}
}

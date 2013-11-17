package edu.utep.cybershare.elseweb.simulation.service;

import com.hp.hpl.jena.rdf.model.Resource;

import edu.utep.cybershare.elseweb.prov.ServiceExecution;

public class TiffScenarioExtractionService implements Service {

	public static final String inputClassURI = "file:///Users/nick/Documents/git-repos/ELSeWeb/ontology.cybershare.utep.edu/ELSEWeb/scenario.owl#WCSScenario";
	public static final String outputClassURI = "file:///Users/nick/Documents/git-repos/ELSeWeb/ontology.cybershare.utep.edu/ELSEWeb/scenario.owl#TiffExtractedWCSScenario";
	public static final String serviceName = "TiffScenarioExtractionService";
	
	@Override
	public void processInput(Resource input, Resource output) {
		ServiceExecution anExecution = new ServiceExecution(serviceName, input, output, inputClassURI, outputClassURI);
		anExecution.linkUp();
		anExecution.close();
	}
}

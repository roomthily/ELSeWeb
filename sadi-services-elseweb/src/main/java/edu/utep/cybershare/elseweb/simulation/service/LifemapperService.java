package edu.utep.cybershare.elseweb.simulation.service;

import com.hp.hpl.jena.rdf.model.Resource;

import edu.utep.cybershare.elseweb.prov.ServiceExecution;

public class LifemapperService implements Service {

	private static final String inputClassURI = "file:///Users/nick/Documents/git-repos/ELSeWeb/ontology.cybershare.utep.edu/ELSEWeb/lifemapper.owl#ExecutableExperiment";
	private static final String outputClassURI = "file:///Users/nick/Documents/git-repos/ELSeWeb/ontology.cybershare.utep.edu/ELSEWeb/lifemapper.owl#CompletedExperiment";
	private static final String serviceName = "WCSCoverageRequirementsService";
	
	@Override
	public void processInput(Resource input, Resource output) {
		ServiceExecution anExecution = new ServiceExecution(serviceName, input, output, inputClassURI, outputClassURI);
		anExecution.linkUp();
		anExecution.close();
	}
}

package edu.utep.cybershare.elseweb.simulation.service;

import com.hp.hpl.jena.rdf.model.Resource;

import edu.utep.cybershare.elseweb.prov.ServiceExecution;

public class LifemapperService implements Service {

	private static final String inputClassURI = "http://ontology.cybershare.utep.edu/ELSEWeb/lifemapper.owl#ExecutableExperiment";
	private static final String serviceName = "WCSCoverageRequirementsService";
	
	@Override
	public void processInput(Resource input, Resource output) {
		ServiceExecution anExecution = new ServiceExecution(serviceName, input, output, inputClassURI);
		anExecution.linkUp();
		anExecution.close();
	}
}

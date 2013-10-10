package edu.utep.cybershare.elseweb.prov.driver.data;

public class WCSScenarioRequirementsServiceData extends ServiceData {
	@Override
	protected void setFields(){
		inputURI = "http://ontology.cybershare.utep.edu/ELSEWeb/experiments/experiment-1.owl#wcsScenarioRequirements";
		inputClassURI = "http://ontology.cybershare.utep.edu/ELSEWeb/scenario.owl#WCSScenarioRequirements";
		inputRDFFile = "./example-data/requirementsService-input.rdf";
	
		outputRDFFile = "./example-data/requirementsService-output.rdf";
		outputURI = "http://ontology.cybershare.utep.edu/ELSEWeb/experiments/experiment-1.owl#wcsScenarioRequirements";
	}
}

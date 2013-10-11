package edu.utep.cybershare.elseweb.simulation.data;

public class WCSScenarioRequirementsServiceData extends ServiceData {
	@Override
	protected void setFields(){
		uri = "http://ontology.cybershare.utep.edu/ELSEWeb/experiments/experiment-1.owl#wcsScenarioRequirements";
		inputRDFFile = "./example-data/requirementsService-input.rdf";
		outputRDFFile = "./example-data/requirementsService-output.rdf";
	}
}

package edu.utep.cybershare.elseweb.prov.driver.data;

public class LifemapperServiceData extends ServiceData {

	@Override
	protected void setFields() {
		uri = "http://ontology.cybershare.utep.edu/ELSEWeb/experiments/experiment-1.owl#experiment";
		inputClassURI = "http://ontology.cybershare.utep.edu/ELSEWeb/lifemapper.owl#ExecutableExperiment";
		inputRDFFile = "./example-data/lifemapperService-input.rdf";
	
		outputRDFFile = "./example-data/lifemapperService-output.rdf";
	}
}

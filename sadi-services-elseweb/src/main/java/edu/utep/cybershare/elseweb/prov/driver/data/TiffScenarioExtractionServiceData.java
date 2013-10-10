package edu.utep.cybershare.elseweb.prov.driver.data;

public class TiffScenarioExtractionServiceData extends ServiceData{

	@Override
	protected void setFields() {
		uri = "http://ontology.cybershare.utep.edu/ELSEWeb/wcsScenario";
		inputRDFFile = "./example-data/extractionService-input.rdf";
		outputRDFFile = "./example-data/extractionService-output.rdf";
	}
}

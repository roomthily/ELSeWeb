package edu.utep.cybershare.elseweb.harvest;

import java.io.File;

import edu.utep.cybershare.elseweb.build.Builder;
import edu.utep.cybershare.elseweb.build.Director;
import edu.utep.cybershare.elseweb.build.ModelProduct;
import edu.utep.cybershare.elseweb.build.source.edac.WCSDigests;
import edu.utep.cybershare.elseweb.model.WCSCoverageSet;
import edu.utep.cybershare.elseweb.model.Characteristic;
import edu.utep.cybershare.elseweb.model.WCSCoverage;
import edu.utep.cybershare.elseweb.model.Distribution;
import edu.utep.cybershare.elseweb.model.Duration;
import edu.utep.cybershare.elseweb.model.Entity;
import edu.utep.cybershare.elseweb.model.Measurement;
import edu.utep.cybershare.elseweb.model.Observation;
import edu.utep.cybershare.elseweb.model.Region;
import edu.utep.cybershare.elseweb.ontology.OWLVisitor;
import edu.utep.cybershare.elseweb.ontology.OntologyToolset;

public class Harvester {

	private static final String BASE_URL = "https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/data/";
	private static final String DOCUMENT_NAME = "edac-data.owl";
	private static final String DOCUMENT_URL = BASE_URL + DOCUMENT_NAME;
	private static final String DUMP_DIR = "../documents/semantic-web/rdf/data/";
	private static final String DUMP_PATH = DUMP_DIR + DOCUMENT_NAME;
	
	public static void main(String[] args){
	
		//setup the empty product
		ModelProduct product = new ModelProduct();
		
		//setup the builder of the product
		Builder builder = new Builder(product);
		
		//setup the builder directory
		Director directory = new Director(builder);
		
		//get our data source and pass to director
		WCSDigests digests = new WCSDigests(10, 0);
		directory.construct(digests);
		
		//create visitor to convert model product to axioms
		OntologyToolset bundle = new OntologyToolset(DOCUMENT_URL);
		OWLVisitor visitor = new OWLVisitor(bundle);
		
		//visit all model elements
		for(WCSCoverageSet catalog : product.getCatalogs())
			visitor.visit(catalog);
		for(Characteristic characteristic : product.getCharacteristics())
			visitor.visit(characteristic);
		for(WCSCoverage dataset : product.getDatasets())
			visitor.visit(dataset);
		for(Distribution distribution : product.getDistributions())
			visitor.visit(distribution);
		for(Duration duration : product.getDurations())
			visitor.visit(duration);
		for(Entity entity : product.getEntities())
			visitor.visit(entity);
		for(Measurement measurement : product.getMeasurements())
			visitor.visit(measurement);
		for(Observation obs : product.getObservations())
			visitor.visit(obs);
		for(Region region :product.getRegions())
			visitor.visit(region);
	
		//dump file
		bundle.dumpOntology(new File(DUMP_PATH));
	}
}

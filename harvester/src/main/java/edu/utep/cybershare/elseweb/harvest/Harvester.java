package edu.utep.cybershare.elseweb.harvest;

import java.io.File;

import edu.utep.cybershare.elseweb.build.Builder;
import edu.utep.cybershare.elseweb.build.Director;
import edu.utep.cybershare.elseweb.build.MaxDiversityMinSizeDirector;
import edu.utep.cybershare.elseweb.build.ModelProduct;
import edu.utep.cybershare.elseweb.build.source.edac.WCSDigests;
import edu.utep.cybershare.elseweb.model.WCSCoverageSet;
import edu.utep.cybershare.elseweb.model.Characteristic;
import edu.utep.cybershare.elseweb.model.WCSCoverageDataset;
import edu.utep.cybershare.elseweb.model.WCSCoverageDistribution;
import edu.utep.cybershare.elseweb.model.Duration;
import edu.utep.cybershare.elseweb.model.Entity;
import edu.utep.cybershare.elseweb.model.Measurement;
import edu.utep.cybershare.elseweb.model.Observation;
import edu.utep.cybershare.elseweb.model.Region;
import edu.utep.cybershare.elseweb.ontology.OWLVisitor;
import edu.utep.cybershare.elseweb.ontology.OntologyToolset;
import edu.utep.cybershare.elseweb.util.FilePath;

public class Harvester {
	
	public static void main(String[] args){
	
		//setup the empty product
		ModelProduct product = new ModelProduct();
		
		//setup the builder of the product
		Builder builder = new Builder(product);
		
		//setup the builder directory
		MaxDiversityMinSizeDirector directory = new MaxDiversityMinSizeDirector(builder);
		
		//get our data source and pass to director
		WCSDigests digests = new WCSDigests(5, 0);
		directory.construct(digests);
		
		//create visitor to convert model product to axioms
		OntologyToolset bundle = new OntologyToolset(FilePath.DOCUMENT_URL);
		OWLVisitor visitor = new OWLVisitor(bundle);
		
		//visit all model elements
		for(WCSCoverageSet catalog : product.getCatalogs())
			visitor.visit(catalog);
		for(Characteristic characteristic : product.getCharacteristics())
			visitor.visit(characteristic);
		for(WCSCoverageDataset dataset : product.getDatasets())
			visitor.visit(dataset);
		for(WCSCoverageDistribution distribution : product.getDistributions())
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
		bundle.dumpOntology(new File(FilePath.DUMP_PATH));
	}
}

package edu.utep.cybershare.elseweb.harvest;

import java.io.File;

import edu.utep.cybershare.elseweb.build.Builder;
import edu.utep.cybershare.elseweb.build.Director;
import edu.utep.cybershare.elseweb.build.ModelProduct;
import edu.utep.cybershare.elseweb.build.source.lifemapper.ParametersXML;
import edu.utep.cybershare.elseweb.model.Algorithm;
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
		Director director = new Director(builder);
		
		//get our data source and pass to director
		ParametersXML paramsXML = new ParametersXML();
		director.construct(paramsXML.getDOMObject());
		
		//create visitor to convert model product to axioms
		OntologyToolset bundle = new OntologyToolset(FilePath.DOCUMENT_URL);
		OWLVisitor visitor = new OWLVisitor(bundle);
		
		//visit all model elements
		for(Algorithm algorithm : product.getAlgorithms())
			visitor.visit(algorithm);
	
		//dump file
		bundle.dumpOntology(new File(FilePath.DUMP_PATH));
	}
}

package edu.utep.cybershare.elseweb.prov;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.reasoner.Reasoner;
import com.hp.hpl.jena.reasoner.ReasonerRegistry;

public class ModelUtils {
	
	public static OntModel getEmptyReasoningModel(){
		//create Jena OWL Reasoner
		Reasoner reasoner = ReasonerRegistry.getOWLReasoner();
		
		OntModelSpec ontModelSpec = OntModelSpec.OWL_DL_MEM;
	    //ontModelSpec.setReasoner(reasoner);
	    
		OntModel model = ModelFactory.createOntologyModel(ontModelSpec);
		return model;
	}

}

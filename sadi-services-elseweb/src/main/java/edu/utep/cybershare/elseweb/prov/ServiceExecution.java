package edu.utep.cybershare.elseweb.prov;

import com.hp.hpl.jena.rdf.model.Resource;

public class ServiceExecution {
	
	private KnoweldgeBase manager;
	
	public ServiceExecution(Resource input, Resource output){
		manager = new KnoweldgeBase();

		NamedGraph graphToLinkTo;

		//check if input was an output of some previous execution
	}
	
	private boolean linkToExitingGraph(Resource input){
		return false;
	}
	
	private boolean linkToDerivedFromGraph(){
		return false;
	}
	
	private void setNewInputGraph(){
		
	}
}

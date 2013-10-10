package edu.utep.cybershare.elseweb.prov.driver.services;

import com.hp.hpl.jena.rdf.model.Resource;

public interface Service {
	public void processInput(Resource input, Resource output);
}

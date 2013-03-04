package edu.utep.cybershare.elseweb.edac.data;

import com.hp.hpl.jena.rdf.model.Model;

public class MinimumTemperatureNormals extends Data {

	public MinimumTemperatureNormals(String uri, Model model){
		super(uri, model, Vocab.MinTemperatureNormals, Vocab.PRISM);
	}
}

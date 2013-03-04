package edu.utep.cybershare.elseweb.edac.data;

import com.hp.hpl.jena.rdf.model.Model;

public class MinTemperatureNormals extends Data {

	public MinTemperatureNormals(String uri, Model model){
		super(uri, model, Vocab.MinTemperatureNormals, Vocab.PRISM);
	}
}

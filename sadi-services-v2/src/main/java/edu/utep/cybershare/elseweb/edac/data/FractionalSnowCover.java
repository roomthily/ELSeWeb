package edu.utep.cybershare.elseweb.edac.data;

import com.hp.hpl.jena.rdf.model.Model;

public class FractionalSnowCover extends Data {

	public FractionalSnowCover(String uri, Model model){
		super(uri, model, Vocab.FractionalSnowData, Vocab.MODIS);
	}
}

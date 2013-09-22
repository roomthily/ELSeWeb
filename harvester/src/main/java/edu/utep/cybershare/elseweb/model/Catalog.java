package edu.utep.cybershare.elseweb.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Catalog extends Element{

	HashMap<String, Dataset> datasets;
	
	public Catalog(String identification) {
		super(identification);
		// TODO Auto-generated constructor stub
		datasets = new HashMap<String, Dataset>();
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
	
	public void addDataset(Dataset dataset){
		datasets.put(dataset.getIdentification(), dataset);
	}
	
	public List<Dataset> getDatasets(){
		return new ArrayList<Dataset>(datasets.values());
	}
}

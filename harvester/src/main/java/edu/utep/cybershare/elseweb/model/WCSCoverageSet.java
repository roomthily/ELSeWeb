package edu.utep.cybershare.elseweb.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WCSCoverageSet extends Element{

	HashMap<String, WCSCoverage> datasets;
	
	public WCSCoverageSet(String identification) {
		super(identification);
		// TODO Auto-generated constructor stub
		datasets = new HashMap<String, WCSCoverage>();
	}
	
	public boolean isSet_datasets(){return this.getDatasets().size() > 0;}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
	
	public void addDataset(WCSCoverage dataset){
		datasets.put(dataset.getIdentification(), dataset);
	}
	
	public List<WCSCoverage> getDatasets(){
		return new ArrayList<WCSCoverage>(datasets.values());
	}
}

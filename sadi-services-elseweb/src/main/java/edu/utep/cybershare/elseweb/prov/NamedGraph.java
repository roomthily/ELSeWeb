package edu.utep.cybershare.elseweb.prov;

import com.hp.hpl.jena.rdf.model.Resource;

public class NamedGraph {
	
	private Resource contents;
	private String graphURI;
	private String graphFilePath;
	
	private boolean isDumped;
	
	public NamedGraph(Resource graphContents, String graphURI, String graphFilePath){
		this.contents = graphContents;
		this.graphURI = graphURI;
		this.graphFilePath = graphFilePath;
		
		this.isDumped = false;
	}
	public void setDumped(){isDumped = false;}
	public boolean isDumped(){return isDumped;}
	public Resource getContents(){return contents;}
	public String getURI(){return graphURI;}
	public String getGraphFilePath(){return graphFilePath;}
}

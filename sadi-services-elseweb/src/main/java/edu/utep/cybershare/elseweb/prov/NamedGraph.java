package edu.utep.cybershare.elseweb.prov;

import com.hp.hpl.jena.rdf.model.Resource;

import edu.utep.cybershare.elseweb.util.FileUtils;

public class NamedGraph {
	
	private String uri;
	private String rootNodeURI;
	private String rootNodeClassURI;
	
	public NamedGraph(String name, Resource resource, String classURI){
		uri = FileUtils.getGraphsURL() + name;
		rootNodeURI = resource.getURI();
		rootNodeClassURI = classURI;
	}
	
	public String getURI(){
		return uri;
	}
	public String getRootNodeURI(){
		return rootNodeURI;
	}
	public String getRootNodeClassURI(){
		return rootNodeClassURI;
	}
}

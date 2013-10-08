package edu.utep.cybershare.elseweb.prov;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class GraphsLog {

	private Document doc;
	
	public NamedGraphs loadNamedGraphs(){
		return null;
	}
	
	
	public boolean saveNamedGraphs(NamedGraphs graphs){
		Element namedGraphs = doc.createElement("namedGraphs");
		doc.appendChild(namedGraphs);
	
		for(NamedGraph graph : graphs.values()){
		}
		return false;
	}
	
	private void buildDocument(){
		try {
			 DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			 DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			 doc = docBuilder.newDocument();
		 }catch(Exception e){e.printStackTrace();}
	}
	
	private Element getNamedGraphElement(NamedGraph graph){
		Element namedGraph = doc.createElement("namedGraph");
		return null;
	}
}

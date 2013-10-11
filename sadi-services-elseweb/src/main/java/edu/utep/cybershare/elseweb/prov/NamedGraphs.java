package edu.utep.cybershare.elseweb.prov;

import java.io.File;
import java.io.FileReader;
import java.io.StringWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;

import edu.utep.cybershare.elseweb.util.FileUtils;

public class NamedGraphs extends HashMap<String, NamedGraph>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Document doc;
	
	public NamedGraphs(){
		loadNamedGraphs();
	}
	
	private void loadNamedGraphs(){
		doc = getExistingDocument();
		if(doc != null)
			populate();
		else
			doc = getNewDocument();
	}
	
	private Document getNewDocument(){
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder;
		try {
			docBuilder = docFactory.newDocumentBuilder();
			doc = docBuilder.newDocument();
			return doc;
		}
		catch(Exception e){e.printStackTrace();}
		return null;
	}

	private Document getExistingDocument(){
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder;
		try {
			docBuilder = docFactory.newDocumentBuilder();
			doc = docBuilder.parse(FileUtils.getGraphsLogPath());
			return doc;
		}
		catch(Exception e){System.out.println("first time looking for log, so it doesn't yet exist...");}
		return null;
	}
	
	private void populate(){
		NodeList namedGraphEntries = doc.getElementsByTagName("namedGraph");
		Node namedGraphEntry;
		String uri;
		String rootNodeURI;
		String namedGraphFilePath;
		for(int i = 0; i < namedGraphEntries.getLength(); i ++){
			namedGraphEntry = namedGraphEntries.item(i);
			uri = namedGraphEntry.getAttributes().getNamedItem("uri").getNodeValue();
			rootNodeURI = namedGraphEntry.getAttributes().getNamedItem("rootNodeURI").getNodeValue();
			namedGraphFilePath = namedGraphEntry.getTextContent();
			
			loadNamedGraph(uri, rootNodeURI, namedGraphFilePath);
		}
	}	

		
	private void loadNamedGraph(String uri, String rootNodeURI, String graphFilePath){
		Model model = ModelFactory.createDefaultModel();
		Resource namedGraphContents;
		NamedGraph namedGraph;
		
		File filePath = new File(graphFilePath);
		FileReader reader;
		
		try{
			reader = new FileReader(filePath);
			model.read(reader, null);
			namedGraphContents = model.getResource(rootNodeURI);
			
			//create named graph
			namedGraph = new NamedGraph(namedGraphContents, uri, graphFilePath);
			namedGraph.setDumped();
			
			//add to this table
			this.put(namedGraph.getContents().getURI(), namedGraph);
			
		}catch(Exception e){e.printStackTrace();}
	}
	
	public NamedGraph getNewNamedGraph(Resource graphContents){
		String graphFileName = FileUtils.getRandomFileNameFromFileName("namedGraph.rdf");

		System.out.println("file name: " + graphFileName);
		
		//get graph file path and URL
		URL graphURL = FileUtils.getGraphsURL(graphFileName);
		System.out.println("graph url: " + graphURL.toString());
		
		File graphFilePath = FileUtils.getGraphsDirPath(graphFileName);
		System.out.println("graph path: " + graphFilePath.getAbsolutePath());
		
		//create new NamedGraph Object
		NamedGraph namedGraph = new NamedGraph(graphContents, graphURL.toString(), graphFilePath.getAbsolutePath());
		put(namedGraph.getContents().getURI(), namedGraph);
		return namedGraph;
	}
	
	public void dump(){
		//dump named graphs
		dumpNamedGraphs();
		
		//dump log
		dumpLog();
	}
	
	private void dumpLog(){
		NodeList namedGraphsElements = doc.getElementsByTagName("namedGraphs");
		Element namedGraphsElement;
		if(namedGraphsElements.getLength() > 0){namedGraphsElement = (Element) namedGraphsElements.item(0);}
		else{
			namedGraphsElement = doc.createElement("namedGraphs");
			doc.appendChild(namedGraphsElement);
		}
		
		ArrayList<NamedGraph> namedGraphs = new ArrayList<NamedGraph>(values());
		for(NamedGraph aNamedGraph : namedGraphs){
			if(!aNamedGraph.isDumped())
				addLogEntry(aNamedGraph, namedGraphsElement);
		}				
	}
	
	private void addLogEntry(NamedGraph namedGraph, Element namedGraphsElement){
		Element namedGraphElement = doc.createElement("namedGraph");
		
		//add file path to RDF of named graph
		namedGraphElement.appendChild(doc.createTextNode(namedGraph.getGraphFilePath()));
		
		//add attribute for URI of the named graph
		Attr uriAttr = doc.createAttribute("uri");
		uriAttr.setValue(namedGraph.getURI());
		namedGraphElement.setAttributeNode(uriAttr);
		
		//add attribute for rootNodeURI of the named graph
		Attr rootNodeURIAttr = doc.createAttribute("rootNodeURI");
		rootNodeURIAttr.setValue(namedGraph.getContents().getURI());
		namedGraphElement.setAttributeNode(rootNodeURIAttr);
	}
	
	private void dumpNamedGraphs(){
		ArrayList<NamedGraph> namedGraphs = new ArrayList<NamedGraph>(values());
		for(NamedGraph aNamedGraph : namedGraphs){
			if(!aNamedGraph.isDumped())
				dumpNamedGraph(aNamedGraph);
		}		
	}
	
	private void dumpNamedGraph(NamedGraph namedGraph){
		namedGraph.setDumped();
		String serializedGraph = serialize(namedGraph.getContents());
		FileUtils.writeTextFile(serializedGraph, namedGraph.getGraphFilePath());
	}
	
	private String serialize(Resource graphContents){
		StringWriter wtr = new StringWriter();
		graphContents.getModel().write(wtr, "RDF/XML-ABBREV");
		return wtr.toString();
	}
}
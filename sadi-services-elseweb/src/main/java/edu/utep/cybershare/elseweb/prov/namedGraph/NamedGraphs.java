package edu.utep.cybershare.elseweb.prov.namedGraph;

import java.io.File;
import java.io.FileReader;
import java.io.StringWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

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
		String classURI;
		String namedGraphFilePath;
		for(int i = 0; i < namedGraphEntries.getLength(); i ++){
			namedGraphEntry = namedGraphEntries.item(i);
			uri = namedGraphEntry.getAttributes().getNamedItem("uri").getNodeValue();
			rootNodeURI = namedGraphEntry.getAttributes().getNamedItem("rootNodeURI").getNodeValue();
			classURI = namedGraphEntry.getAttributes().getNamedItem("classURI").getNodeValue();
			namedGraphFilePath = namedGraphEntry.getTextContent();
			
			loadNamedGraph(uri, rootNodeURI, classURI, namedGraphFilePath);
		}
	}	

		
	private void loadNamedGraph(String uri, String rootNodeURI, String classURI, String graphFilePath){
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
			namedGraph = new NamedGraph(namedGraphContents, uri, classURI,  graphFilePath);
			namedGraph.setDumped();
			
			//add to this table
			this.put(namedGraph.getContents().getURI(), namedGraph);
			
		}catch(Exception e){e.printStackTrace();}
	}
	
	public NamedGraph getNewNamedGraph(Resource graphContents, String classURI, boolean addToGraphList){
		String graphFileName = FileUtils.getRandomFileNameFromFileName("namedGraph.rdf");
		
		//get graph file path and URL
		URL graphURL = FileUtils.getGraphsURL(graphFileName);
		File graphFilePath = FileUtils.getGraphsDirPath(graphFileName);
		
		//create new NamedGraph Object
		NamedGraph namedGraph = new NamedGraph(graphContents, graphURL.toString(), classURI, graphFilePath.getAbsolutePath());

		if(addToGraphList)
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
		
		// write the content into xml file
		try{
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(FileUtils.getGraphsLogPath());
			transformer.transform(source, result);
			System.out.println("Log saved!");
		}catch(Exception e){
			e.printStackTrace();
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
		
		//add attribute for classURI
		Attr classURIAttr = doc.createAttribute("classURI");
		classURIAttr.setValue(namedGraph.getGraphClassURI());
		namedGraphElement.setAttributeNode(classURIAttr);
		
		//add to namedGraphsElement
		namedGraphsElement.appendChild(namedGraphElement);
	}
	
	private void dumpNamedGraphs(){
		ArrayList<NamedGraph> namedGraphs = new ArrayList<NamedGraph>(values());
		for(NamedGraph aNamedGraph : namedGraphs){
			if(!aNamedGraph.isDumped())
				dumpNamedGraph(aNamedGraph);
		}		
	}
	
	public void addNamedGraph(NamedGraph graph){
		this.put(graph.getContents().getURI(), graph);
	}
	
	private void dumpNamedGraph(NamedGraph namedGraph){
		String serializedGraph = serialize(namedGraph.getContents());
		FileUtils.writeTextFile(serializedGraph, namedGraph.getGraphFilePath());
	}
	
	private String serialize(Resource graphContents){
		StringWriter wtr = new StringWriter();
		graphContents.getModel().write(wtr, "RDF/XML-ABBREV");
		return wtr.toString();
	}
}
package edu.utep.cybershare.elseweb.prov;

import java.io.File;
import java.io.FileReader;
import java.io.StringWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
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
		catch(Exception e){e.printStackTrace();}
		return null;
	}
	
	private void populate(){
		NodeList namedGraphEntries = doc.getElementsByTagName("namgedGraph");
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

		//get graph file path and URL
		URL graphURL = FileUtils.getGraphsURL(graphFileName);
		File graphFilePath = FileUtils.getGraphsDirPath(graphFileName);

		//create new NamedGraph Object
		NamedGraph namedGraph = new NamedGraph(graphContents, graphURL.toString(), graphFilePath.getAbsolutePath());
		put(namedGraph.getContents().getURI(), namedGraph);
		return namedGraph;
	}
	
	public void dump(){
		ArrayList<NamedGraph> namedGraphs = new ArrayList<NamedGraph>(values());
		for(NamedGraph aNamedGraph : namedGraphs){
			if(!aNamedGraph.isDumped())
				dumpNamedGraph(aNamedGraph);
		}
	}
	
	private void dumpNamedGraph(NamedGraph namedGraph){
		namedGraph.setDumped();
		String serializedGraph = serialize(namedGraph.getContents());
		FileUtils.writeOutputTextFile(serializedGraph, namedGraph.getGraphFilePath());
	}
	
	private String serialize(Resource graphContents){
		StringWriter wtr = new StringWriter();
		graphContents.getModel().write(wtr, "RDF/XML-ABBREV");
		return wtr.toString();
	}
}
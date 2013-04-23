package edu.utep.cybershare.elseweb.lifemapper.client;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;

import edu.utep.cybershare.elseweb.util.CommandRunner;
import edu.utep.cybershare.elseweb.util.FileUtils;

public class LifemapperExperiment {
	
	private static final String pythonEXE = "python";
	private static final String pythonEXE_IW = " /opt/local/bin/python2.7";
	
	private static final String lifeMapperScript = "\"" + FileUtils.getScriptsDir().getAbsolutePath() + "/" + "client.py\"";
	private static final String lifeMapperScript_IW = FileUtils.getScriptsDir().getAbsolutePath() + "/" + "client.py";
	
	private static String resultBaseURL = "http://lifemapper.org/services/sdm/experiments/";

	private ArrayList<URL> scenarioLayers;
	private String algorithm;
	
	private String uname;
	private String pword;
	private String units;
	private int occurrenceSetID;
	private File outputFilePath;
	
	public LifemapperExperiment(String username, String password){
		scenarioLayers = new ArrayList<URL>();
		uname = username;
		pword = password;
		outputFilePath = FileUtils.getOutputFilePath("lifemapperResults.txt");
	}

	public void setScenarioLayerUnits(String scenarioLayerUnits){
		units = scenarioLayerUnits;
	}
	
	public void setOccurrenceSetID(int id){
		occurrenceSetID = id;
	}
	
	public boolean addScenarioLayer(URL layerURL){
		boolean added = false;
		if(scenarioLayers.size() < 10)
			added = scenarioLayers.add(layerURL);
		return added;
	}
	
	public void setAlgorithm(String algorithmName){
		algorithm = algorithmName;
	}
		
	public URL submitExperiment(){
		
		String outputPath = "\"" + outputFilePath.getAbsolutePath() + "\" ";
		String outputPath_iw = outputFilePath.getAbsolutePath() + " ";
		
		String command =	pythonEXE_IW + " " +
							lifeMapperScript_IW + " " +
							uname + " " +
							pword + " " +
							occurrenceSetID + " " +
							units + " " +
							algorithm + " " +
							outputPath_iw;

		for(URL scenarioLayerURL : scenarioLayers)
			command += scenarioLayerURL + " ";
		
		System.out.println("going to dump file at: " + outputFilePath);
		
		CommandRunner.run(command);
		String experimentID = FileUtils.readTextFile(outputFilePath).trim();
		
		URL resultURL = null;
		try{resultURL = new URL(resultBaseURL + experimentID);}
		catch(Exception e){e.printStackTrace();}
		
		return resultURL;
	}
}
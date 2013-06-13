package edu.utep.cybershare.elseweb.ogc.edacDigest;
import static org.apache.commons.io.FileUtils.copyURLToFile;
import java.io.File;
import java.io.FileInputStream;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class WCSDigestFactory {
	
	private static final String JSON_URL = "http://gstore.unm.edu/apps/elseweb/search/datasets.json?version=3";	
	
	
	public WCSDigestFactory(){
		JSONArray array = getJSONDigestsArray();
		
		
	}
	

	private JSONArray getJSONDigestsArray(){
		JSONObject jsonDigests = getJSONDigests();
		JSONArray jsonDigestsArray = null;
		try{jsonDigestsArray = (JSONArray)jsonDigests.get("results");}
		catch(Exception e){e.printStackTrace();}
		return jsonDigestsArray;
	}
	
	private JSONObject getJSONDigests(){
		String jsonDigestString = getJSONDigestString();
		JSONObject jsonDigests = null;
		try{jsonDigests = new JSONObject(jsonDigestString);}
		catch(Exception e){e.printStackTrace();}
		return jsonDigests;
	}
	
	private String getJSONDigestString(){
	    URL jsonURL = null;
        File jsonFile = null;
        String jsonString = null;
        try {
            jsonFile = new File("./EDAC-JSON/services.json");
            jsonURL = new URL(JSON_URL);
            copyURLToFile(jsonURL, jsonFile);
            
            FileInputStream fisTargetFile = new FileInputStream(jsonFile);
            jsonString = IOUtils.toString(fisTargetFile, "UTF-8");
        }
        catch (Exception e){
            System.out.println(e);
        }
        return jsonString;
	}
	
	public static void main(String[] args){
		WCSDigestFactory factory = new WCSDigestFactory();
	}
}

package edu.utep.cybershare.elseweb.edac.edacDigest;
import static org.apache.commons.io.FileUtils.copyURLToFile;
import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.ArrayList;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class WCSDigests extends ArrayList<WCSDigest>{
	
	private static final String JSON_URL = "http://gstore.unm.edu/apps/elseweb/search/datasets.json?version=3";	
	
	public WCSDigests(){
		JSONArray array = getJSONDigestsArray();
		
		try{
			for(int i = 0; i < array.length(); i ++)
				addWCSDigest(array.getJSONObject(i));
		}catch(Exception e){e.printStackTrace();}
	}
	
	private void addWCSDigest(JSONObject wcsJSONDigest){
		WCSDigest wcsDigest = new WCSDigest(wcsJSONDigest);
		add(wcsDigest);
	}

	private JSONArray getJSONDigestsArray(){
		JSONObject jsonDigests = getJSONDigests();
		JSONArray jsonDigestsArray = null;
		try{jsonDigestsArray = jsonDigests.getJSONArray("results");}
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
		WCSDigests digestList = new WCSDigests();
		System.out.println(digestList);
	}
}

package edu.utep.cybershare.elseweb.build.source.edac;
import static org.apache.commons.io.FileUtils.copyURLToFile;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.ArrayList;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class WCSDigests extends ArrayList<WCSDigest>{
	
	private static final String JSON_DIGEST_BASE_URL = "http://gstore.unm.edu/apps/elseweb/search/datasets.json";
	
	public WCSDigests(int limit, int offset){
		if(limit < 1)
			limit = 1;
		if(offset < 0)
			offset = 0;
		
		String jsonURL = getURL(limit, offset);
		JSONArray array  = getJSONDigestsArray(jsonURL);
		addJSONDigests(array);
	}
	
	
	private void addJSONDigests(JSONArray array){
		try{
			for(int i = 0; i < array.length(); i ++){
				System.out.println("processing: " + array.getJSONObject(i));
				addWCSDigest(array.getJSONObject(i));
			}
		}catch(Exception e){e.printStackTrace();}		
	}
	
	private String getURL(int limit, int offset){
		String queryString = 
				"?version=3" +
				"&limit=" + limit +
				"&offset=" + offset;
		return JSON_DIGEST_BASE_URL + queryString;
	}
		
	private void addWCSDigest(JSONObject wcsJSONDigest){
		WCSDigest wcsDigest = new WCSDigest(wcsJSONDigest);
		add(wcsDigest);
	}

	private JSONArray getJSONDigestsArray(String jsonURLString){
		JSONObject jsonDigests = getJSONDigests(jsonURLString);
		JSONArray jsonDigestsArray = null;
		try{jsonDigestsArray = jsonDigests.getJSONArray("results");}
		catch(Exception e){e.printStackTrace();}
		return jsonDigestsArray;
	}
	
	private JSONObject getJSONDigests(String jsonURLString){
		String jsonDigestString = getJSONDigestString(jsonURLString);
		JSONObject jsonDigests = null;
		try{jsonDigests = new JSONObject(jsonDigestString);}
		catch(Exception e){e.printStackTrace();}
		return jsonDigests;
	}
	
	private String getJSONDigestString(String jsonURLString){
	    URL jsonURL = null;
        File jsonFile = null;
        String jsonString = null;
        try {
            jsonFile = new File("./EDAC-JSON/services.json");
            jsonURL = new URL(jsonURLString);
            copyURLToFile(jsonURL, jsonFile);
            
            FileInputStream fisTargetFile = new FileInputStream(jsonFile);
            jsonString = IOUtils.toString(fisTargetFile, "UTF-8");
        }
        catch (Exception e){
            System.out.println(e);
        }
        return jsonString;
	}
}

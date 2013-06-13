package edu.utep.cybershare.elseweb.ogc.edacDigest;

import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

public class WCSDigest {
	
	private int lastUpdate;
	private String uuid;
	private String taxonomy;
	private URL tiffDownload;
	private String description;
	private URL FGDC_metadataXML;
	private URL FGDC_metadataHTML;
	private int epsg;
	private double leftLongitude;
	private double rightLongitude;
	private double lowerLatitude;
	private double upperLatitude;
	private URL wmsService;
	private URL wcsService;
	private URL preview;
	private int gr;
	private String subtheme;
	private String theme;
	private String groupname;
	
	private JSONObject jsonDigest;
	
	public WCSDigest(JSONObject wcsJSONDigest){
		jsonDigest = wcsJSONDigest;
		
		this.setCategories();
		this.setDescription();
		this.setLastUpdate();
		this.setMetadata();
		this.setPreview();
		this.setServices();
		this.setSpatial();
		this.setTaxonomy();
		this.setDownloads();
		this.setUuid();
	}
	
	public int getLastUpdate() {
		return lastUpdate;
	}
	private void setLastUpdate() {
		try{this.lastUpdate =  Integer.valueOf(jsonDigest.getString("lastupdate"));}
		catch(Exception e){e.printStackTrace();}
	}
	public String getUuid() {
		return uuid;
	}
	private void setUuid() {
		try{this.uuid =  jsonDigest.getString("lastupdate");}
		catch(Exception e){e.printStackTrace();}
	}

	public String getTaxonomy() {
		return taxonomy;
	}
	private void setTaxonomy() {
		try{this.taxonomy =  jsonDigest.getString("lastupdate");}
		catch(Exception e){e.printStackTrace();}
	}

	public URL getTiffDownload() {
		return tiffDownload;
	}

	private void setDownloads() {
		try {
			JSONArray downloads = jsonDigest.getJSONArray("downloads");
			JSONObject download0 = downloads.getJSONObject(0);
			tiffDownload = new URL(download0.getString("tif"));
		}
		catch(Exception e){e.printStackTrace();}
	}

	public String getDescription() {
		return description;
	}

	private void setDescription() {
		try{this.description =  jsonDigest.getString("description");}
		catch(Exception e){e.printStackTrace();}
	}
	
	private void setMetadata() {
		try {
			JSONArray metadata = jsonDigest.getJSONArray("metadata");
			JSONObject metadata0 = metadata.getJSONObject(0);
			JSONObject fgdc = metadata0.getJSONObject("fgdc");
			FGDC_metadataXML = new URL(fgdc.getString("xml"));
			FGDC_metadataHTML = new URL(fgdc.getString("html"));
		}
		catch(Exception e){e.printStackTrace();}
	}

	public URL getFGDC_metadataXML() {
		return FGDC_metadataXML;
	}

	public URL getFGDC_metadataHTML() {
		return FGDC_metadataHTML;
	}
	
	private void setSpatial() {
		try {
			JSONObject spatial = jsonDigest.getJSONObject("spatial");
			epsg = spatial.getInt("epsg");
			JSONArray bbox = spatial.getJSONArray("bbox");
			
			leftLongitude = bbox.getDouble(0);
			lowerLatitude = bbox.getDouble(1);
			rightLongitude = bbox.getDouble(2);
			upperLatitude = bbox.getDouble(3);			
		}
		catch(Exception e){e.printStackTrace();}
	}

	public int getEPSG() {
		return epsg;
	}
	
	public double getLeftLongitude() {
		return leftLongitude;
	}

	public double getRightLongitude() {
		return rightLongitude;
	}
	
	public double getLowerLatitude() {
		return lowerLatitude;
	}

	public double getUpperLatitude() {
		return upperLatitude;
	}

	private void setServices() {

		try{
			JSONArray services = jsonDigest.getJSONArray("services");
			JSONObject services0 = services.getJSONObject(0);
			JSONObject services1 = services.getJSONObject(1);
			
			wmsService = new URL(services0.getString("wms"));
			wcsService = new URL(services1.getString("wcs"));
			
		}catch(Exception e){
			e.printStackTrace();
		}		
	}
	public URL getWcsService() {
		return wcsService;
	}
	
	public URL getWmsService() {
		return wmsService;
	}

	public URL getPreview() {
		return preview;
	}
	
	private void setPreview() {
		try{
			preview = new URL(jsonDigest.getString("preview"));
		}catch(Exception e){e.printStackTrace();}
	}
	
	public int getGr() {
		return gr;
	}
	protected void setGr(int gr) {
		try{
			gr = jsonDigest.getInt("gr");
		}catch(Exception e){e.printStackTrace();}
	}
		
	private void setCategories() {
		try{
			JSONArray categories = jsonDigest.getJSONArray("categories");
			JSONObject categories0 = categories.getJSONObject(0);
			theme = categories0.getString("theme");
			groupname = categories0.getString("groupname");
			subtheme = categories0.getString("subtheme");			
		}catch(Exception e){e.printStackTrace();}
	}
	
	public String getSubtheme() {
		return subtheme;
	}
	
	public String getTheme() {
		return theme;
	}
	public String getGroupName() {
		return groupname;
	}
	
	public String toString(){
		return jsonDigest.toString();
	}
}
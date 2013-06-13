package edu.utep.cybershare.elseweb.ogc.edacDigest;

import java.net.URL;

import org.json.JSONObject;

public class WCSDigest {
	
	private int lastUpdate;
	private String uuid;
	private String taxonomy;
	private URL tiffDownload;
	private String description;
	private URL FGDC_metadataXML;
	private URL FGDC_metadataHTML;
	private String projectionCode;
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
	private String groupName;
	
	public WCSDigest(JSONObject wcsJSONDigest){
		
	}
	
	public int getLastUpdate() {
		return lastUpdate;
	}
	protected void setLastUpdate(int lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	public String getUuid() {
		return uuid;
	}
	protected void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getTaxonomy() {
		return taxonomy;
	}
	protected void setTaxonomy(String taxonomy) {
		this.taxonomy = taxonomy;
	}
	public URL getTiffDownload() {
		return tiffDownload;
	}
	protected void setTiffDownload(URL tiffDownload) {
		this.tiffDownload = tiffDownload;
	}
	public String getDescription() {
		return description;
	}
	protected void setDescription(String description) {
		this.description = description;
	}
	public URL getFGDC_metadataXML() {
		return FGDC_metadataXML;
	}
	protected void setFGDC_metadataXML(URL fGDC_metadataXML) {
		FGDC_metadataXML = fGDC_metadataXML;
	}
	public URL getFGDC_metadataHTML() {
		return FGDC_metadataHTML;
	}
	protected void setFGDC_metadataHTML(URL fGDC_metadataHTML) {
		FGDC_metadataHTML = fGDC_metadataHTML;
	}
	public String getProjectionCode() {
		return projectionCode;
	}
	protected void setProjectionCode(String projectionCode) {
		this.projectionCode = projectionCode;
	}
	public double getLeftLongitude() {
		return leftLongitude;
	}
	protected void setLeftLongitude(double leftLongitude) {
		this.leftLongitude = leftLongitude;
	}
	public double getRightLongitude() {
		return rightLongitude;
	}
	protected void setRightLongitude(double rightLongitude) {
		this.rightLongitude = rightLongitude;
	}
	public double getLowerLatitude() {
		return lowerLatitude;
	}
	protected void setLowerLatitude(double lowerLatitude) {
		this.lowerLatitude = lowerLatitude;
	}
	public double getUpperLatitude() {
		return upperLatitude;
	}
	protected void setUpperLatitude(double upperLatitude) {
		this.upperLatitude = upperLatitude;
	}
	public URL getWmsService() {
		return wmsService;
	}
	protected void setWmsService(URL wmsService) {
		this.wmsService = wmsService;
	}
	public URL getWcsService() {
		return wcsService;
	}
	protected void setWcsService(URL wcsService) {
		this.wcsService = wcsService;
	}
	public URL getPreview() {
		return preview;
	}
	protected void setPreview(URL preview) {
		this.preview = preview;
	}
	public int getGr() {
		return gr;
	}
	protected void setGr(int gr) {
		this.gr = gr;
	}
	public String getSubtheme() {
		return subtheme;
	}
	protected void setSubtheme(String subtheme) {
		this.subtheme = subtheme;
	}
	public String getTheme() {
		return theme;
	}
	protected void setTheme(String theme) {
		this.theme = theme;
	}
	public String getGroupName() {
		return groupName;
	}
	protected void setGroupName(String groupName) {
		this.groupName = groupName;
	}
}
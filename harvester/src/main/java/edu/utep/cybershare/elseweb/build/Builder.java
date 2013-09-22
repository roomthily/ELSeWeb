package edu.utep.cybershare.elseweb.build;

import java.net.URI;
import java.util.Calendar;

import edu.utep.cybershare.elseweb.build.source.edac.fgdc.theme.Theme;
import edu.utep.cybershare.elseweb.model.Catalog;
import edu.utep.cybershare.elseweb.model.Characteristic;
import edu.utep.cybershare.elseweb.model.Dataset;
import edu.utep.cybershare.elseweb.model.Distribution;
import edu.utep.cybershare.elseweb.model.Duration;
import edu.utep.cybershare.elseweb.model.Entity;
import edu.utep.cybershare.elseweb.model.Measurement;
import edu.utep.cybershare.elseweb.model.Observation;
import edu.utep.cybershare.elseweb.model.Region;
import edu.utep.cybershare.elseweb.ogc.wcs.url.WCSGetCoverageParameters;
import edu.utep.cybershare.elseweb.ogc.wcs.url.WCSGetCoverageURL;

public class Builder {
	
	private static final String characteristicLabel = "characteristic";
	private static final String datasetLabel = "dataset";
	private static final String entityLabel = "entity";
	private static final String observationLabel = "observation";
	private static final String measurementLabel = "measurement";
	private static final String durationLabel = "duration";
	private static final String distributionLabel = "distribution";
	private static final String separator = "_";
	
	private String baseID;
	private Characteristic characteristic;
	private Dataset dataset;
	private Entity entity;
	private Measurement measurement;
	private Observation observation;
	private Catalog catalog;
	private Region region;
	private Duration duration;
	private Distribution distribution;
	private WCSGetCoverageURL wcsCoverageURL;
	private URI agent;
	
	private ModelProduct product;
	
	private static URI dataClearingHouseURI;
	private static URI modisURI;
	private static URI prismURI;
	private static final String catalogName = "EDAC ELSEWeb Environment Datasets";
	
	public Builder(ModelProduct modelProduct){
		product = modelProduct;
		
		try{
			dataClearingHouseURI = new URI("http://rgis.unm.edu/browsedata");
			modisURI = new URI("http://modis.gsfc.nasa.gov/");
			prismURI = new URI("http://www.prism.oregonstate.edu/");
		}
		catch(Exception e){e.printStackTrace();}
	}
	
	public void setBaseID(String id){
		baseID = id;
	}
	
	private void reset(){
		characteristic = null;
		dataset = null;
		entity = null;
		measurement = null;
		observation = null;
		catalog = null;
		region = null;
		duration = null;
		distribution = null;
		wcsCoverageURL = null;
		agent = null;
	}

	private String buildName(String label){
		return baseID + separator + label;
	}
	
	public void buildCharacteristic(String themekey){
		characteristic = product.getCharacteristic(buildName(characteristicLabel));
		characteristic.setThemekey(themekey);
	}

	public void buildDataset(){
		dataset = product.getDataset(buildName(datasetLabel));	
	}
	public void buildEntity(String themekey){
		entity = product.getEntity(buildName(entityLabel));
		entity.setThemekey(themekey);
	}
	public void buildMeasurement(){
		measurement = product.getMeasurement(buildName(measurementLabel));
	}
	public void buildObservation(){
		observation = product.getObservation(buildName(observationLabel));
	}
	public void buildRegion(double llon, double rlon, double llat, double ulat){
		String name = String.valueOf(llon) + String.valueOf(rlon) + String.valueOf(llat) + String.valueOf(ulat);
		region = product.getRegion(name);
		region.setLlon(llon);
		region.setRlon(rlon);
		region.setLlat(llat);
		region.setUlat(ulat);
		
		
	}
	
	private void buildWCSGetCoverageURL(String coverageName, String serviceEndpoint){
		// set wcs getCoverage parameters
		WCSGetCoverageParameters params = new WCSGetCoverageParameters();
		params.setBBox(region.getLlon(), region.getRlon(), region.getLlat(), region.getUlat());
		
		double width = 600;
		double height = 600;
		params.setWidth(width);
		params.setHeight(height);
		
		String format = "image/tiff";
		params.setFormat(format);
		
		params.setCoverage(coverageName);
		
		//construct the parameterized URL from the wcs endpoint and the parameters
		wcsCoverageURL = new WCSGetCoverageURL(serviceEndpoint, params);
	}
	
	public void buildDuration(Calendar startDate, Calendar endDate){
		duration = product.getDuration(buildName(durationLabel));
		duration.setStartDate(startDate);
		duration.setEndDate(endDate);
	}
	
	private URI getMultipartFormatURI(){
		URI formatURI = null;
		try{formatURI = new URI("http://provenanceweb.org/format/mime/multipart/mixed");}
		catch(Exception e){e.printStackTrace();}
		return formatURI;
	}
	
	private URI getWCSGetCoverageURI(){
		URI getCoverageURI = null;
		try{getCoverageURI = new URI(this.wcsCoverageURL.getURL().toString());}
		catch(Exception e){e.printStackTrace();}
		return getCoverageURI;
	}
	
	public void buildDistribution(String coverageName, String serviceEndpoint){
		this.buildWCSGetCoverageURL(coverageName, serviceEndpoint);
		
		distribution = product.getDistribution(buildName(distributionLabel));
		distribution.setAccessURI(dataClearingHouseURI);
		distribution.setFormat(getMultipartFormatURI());
		distribution.setDownloadURI(getWCSGetCoverageURI());
		distribution.setMediaType("multipart/mixed");
	}
	
	public void buildAgent(Theme prismTheme){
		if(prismTheme != null)
			agent = prismURI;
		else
			agent = modisURI;
	}
	
	private void buildCatalog(){
		catalog = product.getCatalog(catalogName);
	}
	
	public void assemble(){
		buildCatalog();
		
		//connect up Dataset
		dataset.setDuration(duration);
		dataset.setRegion(region);
		dataset.setDistribution(distribution);
		dataset.setMeasurement(measurement);
		
		//connect catalog to dataset
		catalog.addDataset(dataset);
		
		//connect up measurement to character and agent
		measurement.setCharacteristic(characteristic);
		measurement.setResponsibleAgent(agent);
		
		//connect observation to entity and measurement
		observation.setEntity(entity);
		observation.setMeasurement(measurement);
		
		reset();
	}
}

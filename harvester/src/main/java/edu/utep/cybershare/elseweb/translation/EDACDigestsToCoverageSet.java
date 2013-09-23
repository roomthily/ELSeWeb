package edu.utep.cybershare.elseweb.translation;

import java.io.File;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;

import edu.utep.cybershare.elseweb.build.WCSGetCoverageParameters;
import edu.utep.cybershare.elseweb.build.WCSGetCoverageURL;
import edu.utep.cybershare.elseweb.build.source.edac.WCSDigest;
import edu.utep.cybershare.elseweb.build.source.edac.WCSDigests;
import edu.utep.cybershare.elseweb.ogc.wcs.Coverage;
import edu.utep.cybershare.elseweb.ogc.wcs.CoverageSet;

public class EDACDigestsToCoverageSet {
	
	private static final String BASE_URL = "https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/data/";
	private static final String DOCUMENT_NAME = "edac-data.owl";
	private static final String DOCUMENT_URL = BASE_URL + DOCUMENT_NAME;
	
	private static final String DUMP_DIR = "../documents/semantic-web/rdf/data/";
	public static void main(String[] args){
		
		OntModel model = ModelFactory.createOntologyModel();
		CoverageSet coverageSet = new CoverageSet(DOCUMENT_URL + "#ACoverageSet_Populated", DOCUMENT_URL, model);
		
		coverageSet.addCoverage(getFractionalSnowCover06182002(model));
		coverageSet.addCoverage(getFractionalSnowCover07122002(model));
		coverageSet.addCoverage(getFractionalSnowCover07132002(model));
		coverageSet.addCoverage(getFractionalSnowCover07292002(model));
		coverageSet.addCoverage(getMinimumTemperatureNormals_December_1981_2010(model));
		coverageSet.addCoverage(getMinimumTemperatureNormals_May_1981_2010(model));
		coverageSet.addCoverage(getMinimumTemperatureNormals_September_1981_2010(model));
		
		
		WCSDigests digests = new WCSDigests(5000, 0);
		System.out.println("Number of WCS digests found: " + digests.size());
		for(WCSDigest aDigest : digests)
			coverageSet.addCoverage(getCoverageFromDigest(aDigest, model));
			
		File dumpFile = new File(DUMP_DIR + DOCUMENT_NAME);
		coverageSet.dumpRDF(dumpFile);
		
		System.out.println("dumped coverage set data at: " + dumpFile.getAbsolutePath());
	}
	
	public static Resource getMinimumTemperatureNormals_September_1981_2010(Model model){
		// set wcs getCoverage parameters
		WCSGetCoverageParameters params = new WCSGetCoverageParameters();
		
		double llon = -125.020833333333;
		double llat = 24.0625;
		double rlon = -66.47916757;
		double ulat = 49.9375;
		params.setBBox(llon, llat, rlon, ulat);
		
		double width = 600;
		double height = 600;
		params.setWidth(width);
		params.setHeight(height);
		
		String format = "image/tiff";
		params.setFormat(format);
		
		String coverage = "us_tmin_1981_2010_05";
		params.setCoverage(coverage);
		
		//construct the parameterized URL from the wcs endpoint and the parameters
		String endpoint = "http://gstore.unm.edu/apps/elseweb/datasets/a45326da-063c-4baf-98e4-d0eb8ec92b3c/services/ogc/wcs?SERVICE=wcs&REQUEST=GetCapabilities&VERSION=1.1.2";

		WCSGetCoverageURL getCoverage = new WCSGetCoverageURL(endpoint, params);
					
		String baseURI = DOCUMENT_URL + "#MTNData_September_1981_2010";
		
		Coverage ogcCoverage = new Coverage(baseURI, model);
		ogcCoverage.addSource(Coverage.Source.PRISM);
		ogcCoverage.addMeasurement(Coverage.Measurement.MinTemperatureNormals);
		ogcCoverage.addGetCoverageRequestURL(getCoverage);
		ogcCoverage.addMIMEFormat();
		ogcCoverage.addDuration(1981, 9, 1, 2010, 9, 1);
		ogcCoverage.addRegion(llon, rlon, llat, ulat);
		return ogcCoverage.getWCSCoverage();
	}
	
	public static Resource getMinimumTemperatureNormals_May_1981_2010(Model model){
		// set wcs getCoverage parameters
		WCSGetCoverageParameters params = new WCSGetCoverageParameters();
		
		double llon = -125.020833333333;
		double llat = 24.0625;
		double rlon = -66.47916757;
		double ulat = 49.9375;
		params.setBBox(llon, llat, rlon, ulat);
		
		double width = 600;
		double height = 600;
		params.setWidth(width);
		params.setHeight(height);
		
		String format = "image/tiff";
		params.setFormat(format);
		
		String coverage = "us_tmin_1981_2010_05";
		params.setCoverage(coverage);
		
		//construct the parameterized URL from the wcs endpoint and the parameters
		String endpoint = "http://gstore.unm.edu/apps/elseweb/datasets/7a16679d-2e40-4905-b016-61a5dfda655e/services/ogc/wcs";

		WCSGetCoverageURL getCoverage = new WCSGetCoverageURL(endpoint, params);
					
		String baseURI = DOCUMENT_URL + "#MTNData_May_1981_2010";
		Coverage ogcCoverage = new Coverage(baseURI, model);
		ogcCoverage.addSource(Coverage.Source.PRISM);
		ogcCoverage.addMeasurement(Coverage.Measurement.MinTemperatureNormals);
		ogcCoverage.addGetCoverageRequestURL(getCoverage);
		ogcCoverage.addMIMEFormat();
		ogcCoverage.addDuration(1981, 5, 1, 2010, 5, 1);
		ogcCoverage.addRegion(llon, rlon, llat, ulat);
		
		return ogcCoverage.getWCSCoverage();
	}
	
	public static Resource getMinimumTemperatureNormals_December_1981_2010(Model model){
		// set wcs getCoverage parameters
		WCSGetCoverageParameters params = new WCSGetCoverageParameters();
				
		double llon = -125.020833333333;
		double llat = 24.0625;
		double rlon = -66.47916757;
		double ulat = 49.9375;
		params.setBBox(llon, llat, rlon, ulat);
		
		double width = 600;
		double height = 600;
		params.setWidth(width);
		params.setHeight(height);
		
		String format = "image/tiff";
		params.setFormat(format);
		
		String coverage = "us_tmin_1981_2010_12";
		params.setCoverage(coverage);

		
		//construct the parameterized URL from the wcs endpoint and the parameters
		String endpoint = "http://gstore.unm.edu/apps/elseweb/datasets/1dd490e4-9a5e-48a6-a593-b2bd11f63cad/services/ogc/wcs";
		WCSGetCoverageURL getCoverage = new WCSGetCoverageURL(endpoint, params);
					
		String baseURI = DOCUMENT_URL + "#MTNData_Dec_1981_2010";
		Coverage ogcCoverage = new Coverage(baseURI, model);
		ogcCoverage.addSource(Coverage.Source.PRISM);
		ogcCoverage.addMeasurement(Coverage.Measurement.MinTemperatureNormals);
		ogcCoverage.addGetCoverageRequestURL(getCoverage);
		ogcCoverage.addMIMEFormat();
		ogcCoverage.addDuration(1981, 12, 1, 2010, 12, 1);
		ogcCoverage.addRegion(llon, rlon, llat, ulat);
		return ogcCoverage.getWCSCoverage();

	}
	
	public static Resource getFractionalSnowCover07292002(Model model){
		// set wcs getCoverage parameters
		WCSGetCoverageParameters params = new WCSGetCoverageParameters();
		double llon = -107;
		double llat = 35;
		double rlon = -105;
		double ulat = 38;
		params.setBBox(llon, llat, rlon, ulat);
		
		double width = 600;
		double height = 600;
		params.setWidth(width);
		params.setHeight(height);
		
		String format = "image/tiff";
		params.setFormat(format);

		String coverage = "mod10a1_a2002210.fractional_snow_cover";
		params.setCoverage(coverage);

		//construct the parameterized URL from the wcs endpoint and the parameters
		String endpoint = "http://gstore.unm.edu/apps/rgis/datasets/a427563f-3c7e-44a2-8b35-68ce2a78001a/services/ogc/wcs";
		WCSGetCoverageURL getCoverage = new WCSGetCoverageURL(endpoint, params);
			
		String baseURI = DOCUMENT_URL + "#FractionalSnowCoverData_07292002";
		
		Coverage ogcCoverage = new Coverage(baseURI, model);
		ogcCoverage.addSource(Coverage.Source.MODIS);
		ogcCoverage.addMeasurement(Coverage.Measurement.FractionalSnowData);
		ogcCoverage.addGetCoverageRequestURL(getCoverage);
		ogcCoverage.addMIMEFormat();
		ogcCoverage.addDuration(2002, 7, 29, 2002, 7, 29);
		ogcCoverage.addRegion(llon, rlon, llat, ulat);
		return ogcCoverage.getWCSCoverage();
	}
	
	public static Resource getFractionalSnowCover07132002(Model model){
		// set wcs getCoverage parameters
		WCSGetCoverageParameters params = new WCSGetCoverageParameters();
		double llon = -107;
		double llat = 35;
		double rlon = -105;
		double ulat = 38;
		params.setBBox(llon, llat, rlon, ulat);
		
		double width = 600;
		double height = 600;
		params.setWidth(width);
		params.setHeight(height);
		
		String format = "image/tiff";
		params.setFormat(format);

		String coverage = "mod10a1_a2002210.fractional_snow_cover";
		params.setCoverage(coverage);

		//construct the parameterized URL from the wcs endpoint and the parameters
		String endpoint = "http://gstore.unm.edu/apps/rgis/datasets/a427563f-3c7e-44a2-8b35-68ce2a78001a/services/ogc/wcs";
		WCSGetCoverageURL getCoverage = new WCSGetCoverageURL(endpoint, params);
		
		String baseURI = DOCUMENT_URL + "#FractionalSnowCoverData_07132002";
		Coverage ogcCoverage = new Coverage(baseURI, model);
		ogcCoverage.addSource(Coverage.Source.MODIS);
		ogcCoverage.addMeasurement(Coverage.Measurement.FractionalSnowData);
		ogcCoverage.addGetCoverageRequestURL(getCoverage);
		ogcCoverage.addMIMEFormat();
		ogcCoverage.addDuration(2002, 7, 13, 2002, 7, 13);
		ogcCoverage.addRegion(llon, rlon, llat, ulat);

		return ogcCoverage.getWCSCoverage();
	}
	
	public static Resource getFractionalSnowCover07122002(Model model){
		// set wcs getCoverage parameters
		WCSGetCoverageParameters params = new WCSGetCoverageParameters();
		double llon = -107;
		double llat = 35;
		double rlon = -105;
		double ulat = 38;
		params.setBBox(llon, llat, rlon, ulat);
		
		double width = 600;
		double height = 600;
		params.setWidth(width);
		params.setHeight(height);

		String format = "image/tiff";
		params.setFormat(format);
		
		String coverage = "mod10a1_a2002193.fractional_snow_cover";
		params.setCoverage(coverage);

		//construct the parameterized URL from the wcs endpoint and the parameters
		String endpoint = "http://gstore.unm.edu/apps/rgis/datasets/0f3ca80c-2d50-4a33-8df8-c80ff9e94588/services/ogc/wcs";
		WCSGetCoverageURL getCoverage = new WCSGetCoverageURL(endpoint, params);
				
		String baseURI = DOCUMENT_URL + "#FractionalSnowCoverDataS_07122002";

		Coverage ogcCoverage = new Coverage(baseURI, model);
		ogcCoverage.addSource(Coverage.Source.MODIS);
		ogcCoverage.addMeasurement(Coverage.Measurement.FractionalSnowData);
		ogcCoverage.addGetCoverageRequestURL(getCoverage);
		ogcCoverage.addMIMEFormat();
		ogcCoverage.addDuration(2002, 7, 12, 2002, 7, 12);
		ogcCoverage.addRegion(llon, rlon, llat, ulat);
		
		return ogcCoverage.getWCSCoverage();

	}
	
	public static Resource getCoverageFromDigest(WCSDigest wcsDigest, Model model){	
		// set wcs getCoverage parameters
		WCSGetCoverageParameters params = new WCSGetCoverageParameters();
		params.setBBox(
				wcsDigest.getLeftLongitude(),
				wcsDigest.getUpperLatitude(),
				wcsDigest.getRightLongitude(),
				wcsDigest.getUpperLatitude());
		
		double width = 600;
		double height = 600;
		params.setWidth(width);
		params.setHeight(height);
		
		String format = "image/tiff";
		params.setFormat(format);
		
		String coverage = wcsDigest.getName();
		params.setCoverage(coverage);
		
		//construct the parameterized URL from the wcs endpoint and the parameters
		WCSGetCoverageURL getCoverage = new WCSGetCoverageURL(wcsDigest.getWcsServiceEndpoint().toString(), params);
					
		String baseURI = DOCUMENT_URL + "#" + wcsDigest.getName();
		
		Coverage ogcCoverage = new Coverage(baseURI, model);
		ogcCoverage.addSource(Coverage.Source.PRISM);
		ogcCoverage.addMeasurement(Coverage.Measurement.MinTemperatureNormals);
		ogcCoverage.addGetCoverageRequestURL(getCoverage);
		ogcCoverage.addMIMEFormat();
		ogcCoverage.addDuration(wcsDigest.getStartDate(), wcsDigest.getEndDate());
		ogcCoverage.addRegion(
				wcsDigest.getLeftLongitude(),
				wcsDigest.getRightLongitude(),
				wcsDigest.getLowerLatitude(),
				wcsDigest.getUpperLatitude());
		
		return ogcCoverage.getWCSCoverage();
	}
	
	public static Resource getFractionalSnowCover06182002(Model model){		
		// set wcs getCoverage parameters
		WCSGetCoverageParameters params = new WCSGetCoverageParameters();
		double llon = -107;
		double llat = 35;
		double rlon = -105;
		double ulat = 38;
		params.setBBox(llon, llat, rlon, ulat);
		
		double width = 600;
		double height = 600;
		params.setWidth(width);
		params.setHeight(height);
		
		String format = "image/tiff";
		params.setFormat(format);
		
		String coverage = "mod10a1_a2002169.fractional_snow_cover";
		params.setCoverage(coverage);
		
		//construct the parameterized URL from the wcs endpoint and the parameters
		String endpoint = "http://gstore.unm.edu/apps/rgis/datasets/8c53a01e-c8bf-40de-a9b9-3c2e3f037ebb/services/ogc/wcs";
		WCSGetCoverageURL getCoverage = new WCSGetCoverageURL(endpoint, params);
					
		String baseURI = DOCUMENT_URL + "#FractionalSnowCoverData_06182002";
		
		Coverage ogcCoverage = new Coverage(baseURI, model);
		ogcCoverage.addSource(Coverage.Source.MODIS);
		ogcCoverage.addMeasurement(Coverage.Measurement.FractionalSnowData);
		ogcCoverage.addGetCoverageRequestURL(getCoverage);
		ogcCoverage.addMIMEFormat();
		ogcCoverage.addDuration(2002, 6, 18, 2002, 6, 18);
		ogcCoverage.addRegion(llon, rlon, llat, ulat);
		
		return ogcCoverage.getWCSCoverage();
	}
}

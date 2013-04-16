package edu.utep.cybershare.elseweb.ogc.wcs;

import java.io.StringWriter;
import java.net.URL;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;

import edu.utep.cybershare.elseweb.ogc.wcs.Coverage;
import edu.utep.cybershare.elseweb.ogc.wcs.url.WCSGetCoverageParameters;
import edu.utep.cybershare.elseweb.ogc.wcs.url.WCSGetCoverageURL;

public class CoverageSet_EDAC {
	
	private static final String DOCUMENT_URL = "https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac-data.owl";
	
	public static void main(String[] args){
		
		Model model = ModelFactory.createDefaultModel();
		CoverageSet coverageSet = new CoverageSet(DOCUMENT_URL + "#ACoverageSet_Populated", model);
		
		coverageSet.addCoverage(getFractionalSnowCover06182002(model));
		coverageSet.addCoverage(getFractionalSnowCover07122002(model));
		coverageSet.addCoverage(getFractionalSnowCover07132002(model));
		coverageSet.addCoverage(getFractionalSnowCover07292002(model));
		coverageSet.addCoverage(getMinimumTemperatureNormals_December_1981_2010(model));
		
		StringWriter wtr = new StringWriter();
		model.write(wtr);
		System.out.println(wtr.toString());
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
		
		String startDate = "12/01/1981";
		String endDate = "12/01/2010";
		String dateRange = startDate + "-" + endDate;
		
		//construct the parameterized URL from the wcs endpoint and the parameters
		String endpoint = "http://gstore.unm.edu/apps/elseweb/datasets/1dd490e4-9a5e-48a6-a593-b2bd11f63cad/services/ogc/wcs";
		WCSGetCoverageURL getCoverage = new WCSGetCoverageURL(endpoint, params);
					
		String baseURI = DOCUMENT_URL + "#MTNData_Dec_1981_2010";
		Coverage ogcCoverage = new Coverage(baseURI, model);
		ogcCoverage.addSource(Coverage.Source.PRISM);
		ogcCoverage.addMeasurement(Coverage.Measurement.MinTemperatureNormals);
		ogcCoverage.addGetCoverageRequestURL(getCoverage);
		ogcCoverage.addMIMEFormat();
		ogcCoverage.addDuration(startDate, endDate);
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
		
		String startDate = "07/29/2002";
		String endDate = "07/29/2002";
				
		//construct the parameterized URL from the wcs endpoint and the parameters
		String endpoint = "http://gstore.unm.edu/apps/rgis/datasets/a427563f-3c7e-44a2-8b35-68ce2a78001a/services/ogc/wcs";
		WCSGetCoverageURL getCoverage = new WCSGetCoverageURL(endpoint, params);
			
		String baseURI = DOCUMENT_URL + "#FractionalSnowCoverData_07292002";
		
		Coverage ogcCoverage = new Coverage(baseURI, model);
		ogcCoverage.addSource(Coverage.Source.MODIS);
		ogcCoverage.addMeasurement(Coverage.Measurement.FractionalSnowData);
		ogcCoverage.addGetCoverageRequestURL(getCoverage);
		ogcCoverage.addMIMEFormat();
		ogcCoverage.addDuration(startDate, endDate);
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

		String startDate = "07/13/2002";
		String endDate = "07/13/2002";
		
		//construct the parameterized URL from the wcs endpoint and the parameters
		String endpoint = "http://gstore.unm.edu/apps/rgis/datasets/a427563f-3c7e-44a2-8b35-68ce2a78001a/services/ogc/wcs";
		WCSGetCoverageURL getCoverage = new WCSGetCoverageURL(endpoint, params);
		
		String baseURI = DOCUMENT_URL + "#FractionalSnowCoverData_07132002";
		Coverage ogcCoverage = new Coverage(baseURI, model);
		ogcCoverage.addSource(Coverage.Source.MODIS);
		ogcCoverage.addMeasurement(Coverage.Measurement.FractionalSnowData);
		ogcCoverage.addGetCoverageRequestURL(getCoverage);
		ogcCoverage.addMIMEFormat();
		ogcCoverage.addDuration(startDate, endDate);
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

		String startDate = "07/12/2002";
		String endDate = "07/12/2002";
		
		//construct the parameterized URL from the wcs endpoint and the parameters
		String endpoint = "http://gstore.unm.edu/apps/rgis/datasets/0f3ca80c-2d50-4a33-8df8-c80ff9e94588/services/ogc/wcs";
		WCSGetCoverageURL getCoverage = new WCSGetCoverageURL(endpoint, params);
				
		String baseURI = DOCUMENT_URL + "#FractionalSnowCoverDataS_07122002";

		Coverage ogcCoverage = new Coverage(baseURI, model);
		ogcCoverage.addSource(Coverage.Source.MODIS);
		ogcCoverage.addMeasurement(Coverage.Measurement.FractionalSnowData);
		ogcCoverage.addGetCoverageRequestURL(getCoverage);
		ogcCoverage.addMIMEFormat();
		ogcCoverage.addDuration(startDate, endDate);
		ogcCoverage.addRegion(llon, rlon, llat, ulat);
		
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
		
		String startDate = "06/18/2002";
		String endDate = "06/18/2002";
		
		//construct the parameterized URL from the wcs endpoint and the parameters
		String endpoint = "http://gstore.unm.edu/apps/rgis/datasets/8c53a01e-c8bf-40de-a9b9-3c2e3f037ebb/services/ogc/wcs";
		WCSGetCoverageURL getCoverage = new WCSGetCoverageURL(endpoint, params);
					
		String baseURI = DOCUMENT_URL + "#FractionalSnowCoverData_06182002";
		
		Coverage ogcCoverage = new Coverage(baseURI, model);
		ogcCoverage.addSource(Coverage.Source.MODIS);
		ogcCoverage.addMeasurement(Coverage.Measurement.FractionalSnowData);
		ogcCoverage.addGetCoverageRequestURL(getCoverage);
		ogcCoverage.addMIMEFormat();
		ogcCoverage.addDuration(startDate, endDate);
		ogcCoverage.addRegion(llon, rlon, llat, ulat);
		
		return ogcCoverage.getWCSCoverage();
	}
}

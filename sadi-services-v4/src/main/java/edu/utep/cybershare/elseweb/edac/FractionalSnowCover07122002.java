package edu.utep.cybershare.elseweb.edac;

import java.net.URL;

import org.apache.log4j.Logger;

import ca.wilkinsonlab.sadi.service.annotations.Description;
import ca.wilkinsonlab.sadi.service.annotations.Name;
import ca.wilkinsonlab.sadi.service.annotations.ContactEmail;
import ca.wilkinsonlab.sadi.service.annotations.InputClass;
import ca.wilkinsonlab.sadi.service.annotations.OutputClass;
import ca.wilkinsonlab.sadi.service.simple.SimpleSynchronousServiceServlet;

import com.hp.hpl.jena.rdf.model.Resource;
//import com.hp.hpl.jena.rdf.model.Statement;
//import com.hp.hpl.jena.rdf.model.StmtIterator;

import edu.utep.cybershare.elseweb.edac.coverage.Coverage;
import edu.utep.cybershare.elseweb.edac.wcs.WCSGetCoverageParameters;
import edu.utep.cybershare.elseweb.edac.wcs.WCSGetCoverageURL;

@Name("FractionalSnowCover07122002")
@ContactEmail("nicholas.delrio@gmail.com")
@InputClass("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac-v3.owl#WCSCoverageSet")
@OutputClass("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac-v3.owl#WCSCoverageSet_Populated")
@Description("EDAC Fractional Snow Cover Data 07122002")

public class FractionalSnowCover07122002 extends SimpleSynchronousServiceServlet
{
	private static final Logger log = Logger.getLogger(FractionalSnowCover07122002.class);
	private static final long serialVersionUID = 1L;

	@Override
	public void processInput(Resource input, Resource output)
	{
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
		URL getCoverageURL = getCoverage.getURL();
				
		String baseURI = "http://edac.elseweb.cybershare.utep.edu#FractionalSnowCoverDataService_07122002";

		Coverage ogcCoverage = new Coverage(baseURI, output.getModel());
		ogcCoverage.addSource(Coverage.Source.MODIS);
		ogcCoverage.addMeasurement(Coverage.Measurement.FractionalSnowData);
		ogcCoverage.addRequestDateTime();
		ogcCoverage.addGetCoverageRequestURL(getCoverageURL);
		ogcCoverage.addMIMEFormat();
		ogcCoverage.addDuration(startDate, endDate);
		ogcCoverage.addRegion(llon, rlon, llat, ulat);
		ogcCoverage.addHasWCSCoverage(output);
	}
}

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
import edu.utep.cybershare.elseweb.edac.data.MinimumTemperatureNormals;
import edu.utep.cybershare.elseweb.edac.wcs.WCSGetCoverageParameters;
import edu.utep.cybershare.elseweb.edac.wcs.WCSGetCoverageURL;

@Name("MinimumTemperatureNormals_December_1981_2010")
@ContactEmail("nicholas.delrio@gmail.com")
@InputClass("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/lifemapper.owl#ScenarioLayerSet")
@OutputClass("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/lifemapper.owl#PopulatedScenarioLayerSet_MinTemperatureNormals_121981_122010")
@Description("EDAC Minimum Temperature Normals December 1981 - 2010")

public class MinimumTemperatureNormals_December_1981_2010 extends SimpleSynchronousServiceServlet
{
	private static final Logger log = Logger.getLogger(MinimumTemperatureNormals_December_1981_2010.class);
	private static final long serialVersionUID = 1L;

	@Override
	public void processInput(Resource input, Resource output)
	{
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
		URL getCoverageURL = getCoverage.getURL();
			
		/* Generate Data
		 * requires:
		 *	-	Region via hasRegion
		 *	-	Duration via hasDuration
		 *	-	Sensor or Method via hasSourceSensor or hasSourceMethod 
		 */
		
		String baseURI = "http://edac.elseweb.cybershare.utep.edu#MTN_Service_Dec_1981_2010";
		String dataURI = baseURI + "_Data";
		String regionURI = baseURI + "_Region";
		String durationURI = baseURI + "_Duration";
		
		MinimumTemperatureNormals data = new MinimumTemperatureNormals(dataURI, output.getModel());
		data.addRegion(llon, rlon, llat, ulat, regionURI);
		data.addDuration(startDate, endDate, durationURI);
		data.addSource(false);
		
		/* Generate Coverage.
		 * requires:
		 *	-	DateTime via hasRequestDateTime
		 *	-	WCSGetCoverageURL via hasWCSGetCoverageURL
		 *	-	MIXED MIME Format via hasFormat
		 *	-	Data via containsData
		 */

		String coverageURI = baseURI + "_Coverage";
		Coverage ogcCoverage = new Coverage(coverageURI, output.getModel());
		ogcCoverage.addRequestDateTime();
		ogcCoverage.addGetCoverageRequestURL(getCoverageURL);
		ogcCoverage.addMIMEFormat();
		ogcCoverage.addData(data.getDataResource());
		
		ogcCoverage.addHasCoverageToScenarioLayers(output, dateRange);
	}
}

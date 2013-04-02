package edu.utep.cybershare.elseweb.edac;

import java.net.URL;
import org.apache.log4j.Logger;

import ca.wilkinsonlab.sadi.service.annotations.Description;
import ca.wilkinsonlab.sadi.service.annotations.Name;
import ca.wilkinsonlab.sadi.service.annotations.ContactEmail;
import ca.wilkinsonlab.sadi.service.annotations.InputClass;
import ca.wilkinsonlab.sadi.service.annotations.OutputClass;
import ca.wilkinsonlab.sadi.service.simple.SimpleSynchronousServiceServlet;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
//import com.hp.hpl.jena.rdf.model.Statement;
//import com.hp.hpl.jena.rdf.model.StmtIterator;

import edu.utep.cybershare.elseweb.edac.coverage.Coverage;
import edu.utep.cybershare.elseweb.edac.data.FractionalSnowCover;
import edu.utep.cybershare.elseweb.edac.wcs.WCSGetCoverageParameters;
import edu.utep.cybershare.elseweb.edac.wcs.WCSGetCoverageURL;

@Name("FractionalSnowCover06182002")
@ContactEmail("nicholas.delrio@gmail.com")
@InputClass("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/lifemapper-v3.owl#ScenarioLayerSet")
@OutputClass("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/lifemapper-v3.owl#PopulatedScenarioLayerSet_FractionalSnowCover_06182002")
@Description("EDAC Fractional Snow Cover Data 06182002")

public class FractionalSnowCover06182002 extends SimpleSynchronousServiceServlet
{
	private static final Logger log = Logger.getLogger(FractionalSnowCover06182002.class);
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
		
		String coverage = "mod10a1_a2002169.fractional_snow_cover";
		params.setCoverage(coverage);
		
		String startDate = "06/18/2002";
		String endDate = "06/18/2002";
		
		//construct the parameterized URL from the wcs endpoint and the parameters
		String endpoint = "http://gstore.unm.edu/apps/rgis/datasets/8c53a01e-c8bf-40de-a9b9-3c2e3f037ebb/services/ogc/wcs";
		WCSGetCoverageURL getCoverage = new WCSGetCoverageURL(endpoint, params);
		URL getCoverageURL = getCoverage.getURL();
			
		/* Generate Data
		 * requires:
		 *	-	Region via hasRegion
		 *	-	Duration via hasDuration
		 *	-	Sensor or Method via hasSourceSensor or hasSourceMethod 
		 */
		
		String baseURI = "http://edac.elseweb.cybershare.utep.edu#FractionalSnowCoverDataService_06182002";
		String dataURI = baseURI + "_Data";
		String regionURI = baseURI + "_Region";
		String durationURI = baseURI + "_Duration";
		
		FractionalSnowCover data = new FractionalSnowCover(dataURI, output.getModel());
		data.addRegion(llon, rlon, llat, ulat, regionURI);
		data.addDuration(startDate, endDate, durationURI);
		data.addSource();
		
	
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
		
		ogcCoverage.addHasCoverageToScenarioLayers(output, startDate);
	}
}
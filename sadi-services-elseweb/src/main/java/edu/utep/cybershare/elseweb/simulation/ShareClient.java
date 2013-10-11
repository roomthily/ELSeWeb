package edu.utep.cybershare.elseweb.simulation;

import edu.utep.cybershare.elseweb.simulation.data.LifemapperServiceData;
import edu.utep.cybershare.elseweb.simulation.data.ServiceData;
import edu.utep.cybershare.elseweb.simulation.data.TiffScenarioExtractionServiceData;
import edu.utep.cybershare.elseweb.simulation.data.WCSScenarioRequirementsServiceData;
import edu.utep.cybershare.elseweb.simulation.service.LifemapperService;
import edu.utep.cybershare.elseweb.simulation.service.Service;
import edu.utep.cybershare.elseweb.simulation.service.TiffScenarioExtractionService;
import edu.utep.cybershare.elseweb.simulation.service.WCSCoverageRequirementsService;


public class ShareClient {

	//input/output data
	private ServiceData lifemapperServiceData;
	private ServiceData tiffScenarioExtractionServiceData;
	private ServiceData wcsScenarioRequirementsServiceData;
	
	//services
	private Service lifemapperService;
	private Service tiffScenarioExtractionService;
	private Service wcsCoverageRequirementsService;
	
	public ShareClient(){
		//build input/output data
		lifemapperServiceData = new LifemapperServiceData();
		tiffScenarioExtractionServiceData = new TiffScenarioExtractionServiceData();
		wcsScenarioRequirementsServiceData = new WCSScenarioRequirementsServiceData();
		
		//build services
		lifemapperService = new LifemapperService();
		tiffScenarioExtractionService = new TiffScenarioExtractionService();
		wcsCoverageRequirementsService = new WCSCoverageRequirementsService();
	}
	
	public void simulate(){
		//execute first service
		wcsCoverageRequirementsService.processInput(wcsScenarioRequirementsServiceData.getInput(), wcsScenarioRequirementsServiceData.getOutput());
		
		//execute second service
		tiffScenarioExtractionService.processInput(tiffScenarioExtractionServiceData.getInput(), tiffScenarioExtractionServiceData.getOutput());
		
		//execute final
		lifemapperService.processInput(lifemapperServiceData.getInput(), lifemapperServiceData.getOutput());
	}
	
	public static void main(String[] args){


	}
}

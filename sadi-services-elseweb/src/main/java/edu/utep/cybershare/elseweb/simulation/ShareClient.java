package edu.utep.cybershare.elseweb.simulation;

import edu.utep.cybershare.elseweb.simulation.data.LifemapperServiceData;
import edu.utep.cybershare.elseweb.simulation.data.ServiceData;
import edu.utep.cybershare.elseweb.simulation.data.TiffScenarioExtractionServiceData;
import edu.utep.cybershare.elseweb.simulation.data.WCSScenarioRequirementsServiceData;
import edu.utep.cybershare.elseweb.simulation.service.LifemapperService;
import edu.utep.cybershare.elseweb.simulation.service.Service;
import edu.utep.cybershare.elseweb.simulation.service.TiffScenarioExtractionService;
import edu.utep.cybershare.elseweb.simulation.service.WCSCoverageRequirementsService;
import edu.utep.cybershare.elseweb.util.Printing;


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
		System.out.println("after 1st service execution, the output is:");
		Printing.print(wcsScenarioRequirementsServiceData.getOutput().getModel());
		
		//execute second service
		tiffScenarioExtractionService.processInput(tiffScenarioExtractionServiceData.getInput(), tiffScenarioExtractionServiceData.getOutput());
		System.out.println("after 2nd service execution, the output is:");
		Printing.print(tiffScenarioExtractionServiceData.getOutput().getModel());
		
		//execute final
		lifemapperService.processInput(lifemapperServiceData.getInput(), lifemapperServiceData.getOutput());
		System.out.println("after 3rd service execution, the output is:");
		Printing.print(lifemapperServiceData.getOutput().getModel());
	}
	
	public static void main(String[] args){
		ShareClient simulator = new ShareClient();
		simulator.simulate();
	}
}

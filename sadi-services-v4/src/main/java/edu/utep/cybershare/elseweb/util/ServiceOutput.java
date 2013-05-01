package edu.utep.cybershare.elseweb.util;

import java.io.File;
import java.net.URL;

public class ServiceOutput {

	private String randomFileName;
	
	public File getOutputFilePath(String fileName){
		randomFileName = FileUtils.getRandomFileNameFromFileName(fileName);
		File outputFilePath = new File(FileUtils.getOutputDir(), randomFileName);
		return outputFilePath;
	}
	
	public URL getOutputURL(){
		URL outputURL = null;
		String stringURL;
		try{
			stringURL = FileUtils.getOutputURL().toString() + randomFileName;
			outputURL = new URL(stringURL);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return outputURL;
	}
}

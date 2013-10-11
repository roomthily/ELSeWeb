package edu.utep.cybershare.elseweb.simulation.data;

import java.io.File;
import java.io.FileReader;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;

public abstract class ServiceData {
	
	protected String uri;
	protected String inputRDFFile;	
	protected String outputRDFFile;
		
	private Resource input;
	private Resource output;

	public ServiceData(){
		setFields();
		setInput();
		setOutput();
	}

	public Resource getInput(){return input;}
	public Resource getOutput(){return output;}
	
	private void setInput(){
		Model loadingModel = ModelFactory.createDefaultModel();
		File inputFile = new File(inputRDFFile);
		try{
			FileReader reader = new FileReader(inputFile);
			loadingModel.read(reader, null);
			input =  loadingModel.getResource(uri);
		}catch(Exception e){e.printStackTrace();}
	}
	
	private void setOutput(){
		Model loadingModel = ModelFactory.createDefaultModel();
		File outputFile = new File(outputRDFFile);
		try{
			FileReader reader = new FileReader(outputFile);
			loadingModel.read(reader, null);
			output = loadingModel.getResource(uri);
		}catch(Exception e){e.printStackTrace();}
	}	
	
	protected abstract void setFields();

}

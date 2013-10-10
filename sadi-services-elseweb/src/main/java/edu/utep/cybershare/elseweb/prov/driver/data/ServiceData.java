package edu.utep.cybershare.elseweb.prov.driver.data;

import java.io.File;
import java.io.FileReader;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;

public abstract class ServiceData {
	
	protected String uri;
	protected String inputClassURI;
	protected String inputRDFFile;
	
	protected String outputRDFFile;
	
	public ServiceData(){setFields();}
	
	protected abstract void setFields();
	
	public Resource getInput(){
		Model loadingModel = ModelFactory.createDefaultModel();
		File inputFile = new File(inputRDFFile);
		try{
			FileReader reader = new FileReader(inputFile);
			loadingModel.read(reader, null);
			return loadingModel.getResource(uri);
		}catch(Exception e){e.printStackTrace();}
		return null;
	}
	public String getClassURI(){return this.inputClassURI;}
	
	public Resource getOutput(){
		Model loadingModel = ModelFactory.createDefaultModel();
		File outputFile = new File(outputRDFFile);
		try{
			FileReader reader = new FileReader(outputFile);
			loadingModel.read(reader, null);
			return loadingModel.getResource(uri);
		}catch(Exception e){e.printStackTrace();}
		return null;
	}
}

package edu.utep.cybershare.elseweb.edac.edacDigest;

import java.util.Calendar;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;

public class FGDCData {
	
	private Document fgdcDoc;
	
	public FGDCData(String fgdcXMLURL){
		try{
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			fgdcDoc = dBuilder.parse(fgdcXMLURL);
			fgdcDoc.getDocumentElement().normalize();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public Calendar getStartDate(){
		String fgdcDate = fgdcDoc.getElementsByTagName("begdate").item(0).getTextContent();
		return FGDCDataUtils.getDate(fgdcDate);
	}
	
	public Calendar getEndDate(){
		String fgdcDate = fgdcDoc.getElementsByTagName("enddate").item(0).getTextContent();
		return FGDCDataUtils.getDate(fgdcDate);
	}
	
	public String getEntityMeasurementVocabularyName(){
		String entityMeasurementVocabularyName = fgdcDoc.getElementsByTagName("themekt").item(0).getTextContent();
		return entityMeasurementVocabularyName;
	}
	
	public String getEntityMeasurementType(){
		String entityMeasurementType = fgdcDoc.getElementsByTagName("themekey").item(0).getTextContent();
		return entityMeasurementType;
	}
}
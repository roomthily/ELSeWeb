package edu.utep.cybershare.elseweb.build.source.edac.fgdc;


import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;

import edu.utep.cybershare.elseweb.build.source.edac.fgdc.theme.Themes;

public class FGDCDocument {
	
	private static final int MAX_ATTEMPTS = 5;
	
	private static DocumentBuilder dBuilder;
	
	private Themes themes;
	
	private static void setDocumentBuilder(){
		if(dBuilder == null){
			try{
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			dBuilder = dbFactory.newDocumentBuilder();
			}catch(Exception e){e.printStackTrace();}
		}
	}
	
	public FGDCDocument(String fgdcXMLURL){
		setDocumentBuilder();
		Document fgdcDoc;
		int counter = 0;
		while(counter < MAX_ATTEMPTS){
			try{
				fgdcDoc = dBuilder.parse(fgdcXMLURL);
				fgdcDoc.getDocumentElement().normalize();
				setFields(fgdcDoc);
				
				//end loop and clean up resources
				counter = MAX_ATTEMPTS;
				dBuilder.reset();
			}catch(Exception e){
				e.printStackTrace();
				counter ++;
			}
		}
	}
	
	private void setFields(Document fgdcDoc){		
		themes = new Themes(fgdcDoc);
	}	

	public Themes getThemes(){
		return themes;
	}
}
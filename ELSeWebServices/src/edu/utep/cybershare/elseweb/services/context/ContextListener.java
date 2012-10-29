package edu.utep.cybershare.elseweb.services.context;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import edu.utep.cybershare.elseweb.services.util.FileUtils;


public class ContextListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		// TODO Auto-generated method stub
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		ServletContext context = event.getServletContext();
		setOutputFilePath(context);
		setOutputFileURL(context);
	}
		
	private static void setOutputFilePath(ServletContext context){
		String serverFilePath = context.getInitParameter("server-base-path");
		String outputFilePath;
		
		if(!serverFilePath.endsWith("/"))
			outputFilePath = serverFilePath + "/webapps/ELSeWebServices/output/";
		else
			outputFilePath = serverFilePath + "webapps/ELSeWebServices/output/";
		
		FileUtils.setOutputFilePath(outputFilePath);
	}
	
	private static void setOutputFileURL(ServletContext context){
		String serverBaseURL = context.getInitParameter("server-url");
		String outputFileURL;
		
		if(!serverBaseURL.endsWith("/"))
			outputFileURL = serverBaseURL + "/ELSeWebServices/output/";
		else
			outputFileURL = serverBaseURL + "ELSeWebServices/output/";
		
		FileUtils.setOutputFileURL(outputFileURL);
	}
}

package edu.utep.cybershare.elseweb.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.Properties;
import java.util.StringTokenizer;


public class FileUtils{
	private static URL SERVER_URL;
	private static String WEBAPP_NAME;
	private static File WEBAPP_DIR_PATH;
	private static File OUTPUT_DIR_PATH;
	private static File SCRIPTS_DIR_PATH;
	private static URL OUTPUT_URL;
	private static URL ONTOLOGY_URL;
	
	private static final String ONTOLOGY_NAME = "elseweb.owl";
	private static final String SCRIPTS_DIR_NAME = "scripts";
	private static final String OUTPUT_DIR_NAME = "output";
	private static final String WEBAPP = "webapps";
	static {
		try {
			// Initialize properties in this order
			Properties properties = getServiceProperties();
			setServerURL(properties);
			setWebappName(properties);
			setWebappPath(properties);
			setOutputPath();
			setScriptsPath();
			setOutputURL();
			setOntologyURL(properties);
			
			System.out.println("server url: " + SERVER_URL);
			System.out.println("webapp name: " + WEBAPP_NAME);
			System.out.println("webapp dir path: " + WEBAPP_DIR_PATH);
			System.out.println("output dir path: " + OUTPUT_DIR_PATH);
			System.out.println("scripts dir path: " + SCRIPTS_DIR_PATH);
			System.out.println("output url: " + OUTPUT_URL);
			System.out.println("ontology url: " + ONTOLOGY_URL);
		}
		catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	private static void setOutputURL() throws MalformedURLException {
		String url = ensureTrailingForwardSlash(SERVER_URL + WEBAPP_NAME + "/" + OUTPUT_DIR_NAME);
		OUTPUT_URL = new URL(url);
	}
	
	private static void setOutputPath(){
		OUTPUT_DIR_PATH = new File(WEBAPP_DIR_PATH, OUTPUT_DIR_NAME);
	}

	private static void setScriptsPath(){
		SCRIPTS_DIR_PATH = new File(WEBAPP_DIR_PATH, SCRIPTS_DIR_NAME);
	}
	
	private static void setWebappPath(Properties properties){
		String tomcatHomePath = properties.getProperty("service.server.tomcat.home");
		tomcatHomePath = ensureTrailingForwardSlash(tomcatHomePath);
		WEBAPP_DIR_PATH = new File(tomcatHomePath + WEBAPP +"/" + WEBAPP_NAME);

	}

	private static void setOntologyURL(Properties properties) throws MalformedURLException{
		String ontologyPrefixURL = properties.getProperty("service.ontology.prefix");
		ontologyPrefixURL = ensureTrailingForwardSlash(ontologyPrefixURL);
		ONTOLOGY_URL = new URL(ontologyPrefixURL + ONTOLOGY_NAME);
		
	}
	
	private static void setWebappName(Properties properties){
		WEBAPP_NAME = properties.getProperty("service.server.webapp.name");
	}
	
	private static void setServerURL(Properties properties) throws MalformedURLException {
		String serverURL = properties.getProperty("service.server.url");
		SERVER_URL = new URL(ensureTrailingForwardSlash(serverURL));
	}
		
	private static String ensureTrailingForwardSlash(String path){
		String formattedPath = path;
		if(!path.endsWith("/"))
			formattedPath += "/";
		
		return formattedPath;
	}

	public static URL getOntologyURL(){
		return ONTOLOGY_URL;
	}
	
	public static URL getServerBaseURL(){
		return SERVER_URL;
	}
	
	public static URL getOutputURL(){
		return OUTPUT_URL;
	}
	
	public static String getWebappName(){
		return WEBAPP_NAME;
	}

	public static Properties getServiceProperties() throws Exception {
		Properties moduleProps = new Properties();
		InputStream propsFile = FileUtils.class.getResourceAsStream("/service.properties");
		moduleProps.load(propsFile);
		return moduleProps;
	}

	public static String getHostName() {
		String host = null;
		try {
			InetAddress addr = InetAddress.getLocalHost();
			host = addr.getHostName();

		} catch (UnknownHostException e) { 
			System.err.println(e); 
		}
		return host;
	}

	public static File getScriptsDir(){
		return SCRIPTS_DIR_PATH;
	}
	
	public static File getLoggingDir(){
		return new File(WEBAPP_DIR_PATH, "log");
	}

	public static File getOutputDir() {
		return OUTPUT_DIR_PATH;
	}
	
	public static File getWebappDir() {
		return WEBAPP_DIR_PATH;
	}

	public static File getOutputFilePath(String fileName){
		String randomFileName = FileUtils.getRandomFileNameFromFileName(fileName);
		File outputFilePath = new File(OUTPUT_DIR_PATH, randomFileName);
		return outputFilePath;
	}

	public static File writeOutputTextFile(String fileContents, String fileName){		
		File outputFilePath = getOutputFilePath(fileName);
		try{
			BufferedWriter out = new BufferedWriter(new FileWriter(outputFilePath));
			out.write(fileContents);
			out.close();
			return outputFilePath;
		}
		catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}

	public static boolean exists(String filePath){
		File file = new File(filePath);
		if (file.exists())
			return true;
		return false;
	}

	public static FileOutputStream getLoggingStream(){
		try{
			return new FileOutputStream(getLoggingDir() + "/log.txt");
		}
		catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}

	public static File writeOutputBinaryFile(byte[] fileContents, String fileName){
		File outputFilePath = getOutputFilePath(fileName);
		try{
			FileOutputStream out = new FileOutputStream(outputFilePath);
			out.write(fileContents);
			out.close();
			return outputFilePath;
		}
		catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}

	public static byte[] readBinaryFile(File file){
		try{
			FileInputStream in = new FileInputStream(file);
			byte[] fileContents = new byte[(int)file.length()];
			in.read(fileContents);
			in.close();
			return fileContents;
		}
		catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}

	public static String readTextFile(File file){
		try{
			BufferedReader in = new BufferedReader(new FileReader(file));
			String line, fileContents;

			fileContents = null;

			while ((line = in.readLine()) != null){
				if (fileContents == null)
					fileContents = line + "\n";
				else
					fileContents = fileContents + line + "\n";
			}

			in.close();
			return fileContents;
		}
		catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}

	public static String getRandomString(){
		// get the current time in milliseconds to use as data source temp file
		// name
		long miliseconds = (new Date()).getTime();
		String localFileName = new String("" + miliseconds);
		return localFileName;
	}

	public static void cleanWorkspace(File workspace){
		File[] wsFiles = workspace.listFiles();
		if (wsFiles != null){
			for (File aFile : wsFiles){
				aFile.delete();
			}
		}
	}

	public static String getRandomFileNameFromFileName(String fileName){
		String randomFileName = fileName + "_" + FileUtils.getRandomString();
		
		if(fileName.contains(".")){
			String[] fileNameParts = fileName.split("\\.");
			String name = fileNameParts[0];
			String extension = fileNameParts[1];

			randomFileName = name + "_" + FileUtils.getRandomString() + "." + extension;
		}
		return randomFileName;
	}

	public static String getNameFromFilename(String fileName) {
		StringTokenizer tokens = new StringTokenizer(fileName, ".");
		String output = tokens.nextToken();
		return output;
	}

	public static String getNameFromPath(String fileName) {
		String name = new File(fileName).getName();
		return name;
	}

	public static String getDirFromPath(String fileName) {
		String path = new File(fileName).getParent();
		return path;
	}

	public static boolean isStaticFileURL(URL url){
		if(url.toString().contains("?"))
			return false;
		return true;
	}
}

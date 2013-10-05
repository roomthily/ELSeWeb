package edu.utep.cybershare.elseweb.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.util.URIUtil;

public class URLUtils {
	
	public static final String BASE_URI = "http://ontology.cybershare.utep.edu/ELSEWeb/";
	
	public static String getUniqueFileNameFromURL(String url) {
		String fileName = url.substring(0, url.lastIndexOf("/"));
		return fileName + "-" + FileUtils.getRandomString() + ".nc";
	}

	/**
	 * Encode the path and query segments of the given URL.
	 * 
	 * @param url
	 * @return String encoded URL
	 */
	public static String encode(String url) {
		try {
			return URIUtil.encodePathQuery(url);
		} catch (URIException e) {
			throw new RuntimeException(e);
		}
	}

	public static byte[] downloadFile(String urlString){
		byte[] contents = null;
		try{
			URL url = new URL(urlString);
			contents = downloadFile(url);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return contents;
	}
	
	public static byte[] downloadFile(URL url) {
		try {
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			URLConnection fileLocation = url.openConnection();

			BufferedInputStream bis = new BufferedInputStream(
					fileLocation.getInputStream());
			byte[] buff = new byte[1024];
			int bytesRead;

			// Simple read/write loop
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				output.write(buff, 0, bytesRead);
			}

			// close inputstream
			bis.close();

			return output.toByteArray();

		} catch (MalformedURLException u) {
			u.printStackTrace();
			return null;

		} catch (IOException i) {

			i.printStackTrace();
			return null;
		}
	}

	public static String downloadText(String _uri) {
		try {
			return URLUtils.downloadText(new URL(_uri));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String downloadText(URL u) throws IOException {

		URLConnection conn = u.openConnection();
		conn.setUseCaches(true);
		Object resp = conn.getContent();
		InputStream body = (InputStream) resp;
		InputStreamReader isr = new InputStreamReader(body);
		LineNumberReader lr = new LineNumberReader(isr);
		StringBuffer ret = new StringBuffer();

		while (true) {
			String line = lr.readLine();
			// System.out.println("Line:" + line);
			if (line == null) {
				break;
			}
			// ret.append(line).append(System.getProperty("line.separator"));
			ret.append(line).append("\n");
		}
		lr.close();
		return ret.toString();
	}

	public static String downloadRawChars(String _uri) throws IOException,
			MalformedURLException {
		return URLUtils.downloadRawChars(new URL(_uri));
	}
	
	public static String downloadRawChars(URL u) throws IOException {

		URLConnection conn = u.openConnection();
		Object resp = conn.getContent();
		InputStream body = (InputStream) resp;
		StringBuffer ret = new StringBuffer();
		int currentChar;
		while ((currentChar = body.read()) != -1) {
			ret.append(Character.toString((char) currentChar));
		}
		body.close();
		return ret.toString();
	}

	public static String getFileProtocolURL(String inputPath) {
		String fileURL;

		if (inputPath.startsWith("c:\\"))
			fileURL = inputPath.replace("c:\\", "file:///");
		else if (inputPath.startsWith("C:\\"))
			fileURL = inputPath.replace("C:\\", "file:///");
		else
			// starts with /
			fileURL = "file://" + inputPath;

		return fileURL;
	}

	public static File downloadBinaryFile(URL url, String fileName) {
		byte[] fileContents = URLUtils.downloadFile(url);
		return FileUtils.writeOutputBinaryFile(fileContents, fileName);
	}
	
	public static File downloadBinaryFile(String url, String fileName) {
		byte[] fileContents = URLUtils.downloadFile(url);
		return FileUtils.writeOutputBinaryFile(fileContents, fileName);
	}

	public static File downloadTextFile(String url, String fileName) {
		String fileContents = URLUtils.downloadText(url);
		return FileUtils.writeOutputTextFile(fileContents, fileName);
	}

	public static String getContentType(String urlStr) throws IOException,
			MalformedURLException {
		String cType = null;
		URL url = new URL(urlStr);
		URLConnection conn = url.openConnection();
		cType = conn.getContentType();
		return cType;
	}

} /* END of GetURLContents */

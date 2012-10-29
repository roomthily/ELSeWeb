package edu.utep.cybershare.elseweb.services;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.activation.URLDataSource;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.utep.cybershare.elseweb.services.util.FileUtils;
import edu.utep.cybershare.elseweb.services.util.GetURLContents;


/**
 * Servlet implementation class OGCWCSDataExtractor
 */
public class OGCWCSDataExtractor extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public OGCWCSDataExtractor() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String wcsRequestURL = request.getParameter("wcsRequestURL");
		
		if(wcsRequestURL != null){			
			wcsRequestURL = wcsRequestURL.trim();
			
			System.out.println(wcsRequestURL);
			
			byte[] responseContents = GetURLContents.downloadFile(wcsRequestURL);
			String responseFileName = "wcs-response-" + FileUtils.getRandomFileName() + ".bin";
			String responseFilePath = FileUtils.writeBinaryFile(responseContents, FileUtils.getOutputFilePath(), responseFileName);
			
			System.out.println(responseFilePath);
			
			if(responseFilePath.startsWith("c:\\"))
				responseFilePath = responseFilePath.replace("c:\\", "file:///");
			else if(responseFilePath.startsWith("C:\\"))
				responseFilePath = responseFilePath.replace("C:\\", "file:///");
			else // starts with /
				responseFilePath = "file://" + responseFilePath;

			System.out.println(responseFilePath);
			
			try{
				URL url = new URL(responseFilePath);
				URLDataSource dataSource = new URLDataSource(url);

				MimeMultipart multipart = new MimeMultipart(dataSource);
				MimeBodyPart coverage = (MimeBodyPart) multipart.getBodyPart(1);

				String fileSuffix = ".";
				String contentType = coverage.getContentType();

				if(contentType.endsWith("jpeg"))
					fileSuffix += "jpeg";
				else if(contentType.endsWith("png"))
					fileSuffix += "png";
				else if(contentType.endsWith("gif"))
					fileSuffix += "gif";
				else if(contentType.endsWith("tiff"))
					fileSuffix += "tif";
				else //png; mode=8bit
					fileSuffix += "png";

				String coverageFileName = "coverage-" + FileUtils.getRandomFileName() + fileSuffix; 
				File coverageFilePath = new File(FileUtils.getOutputFilePath() + coverageFileName);
				coverage.saveFile(coverageFilePath);
				String coverageURL = FileUtils.getOutputFileURL() + coverageFileName;
				response.getWriter().write(coverageURL);
			}
			catch(Exception e){
				e.printStackTrace();
				response.getWriter().write(e.getMessage());
			}
		}
		
		else
			response.getWriter().write("ERROR");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}

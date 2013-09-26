package edu.utep.cybershare.elseweb.adaptor;

import java.io.File;
import java.net.URL;

import javax.activation.URLDataSource;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

import edu.utep.cybershare.elseweb.util.ServiceOutput;
import edu.utep.cybershare.elseweb.util.URLUtils;

public class PayloadExtractor {
		
	public URL getPayload(URL wcsGetCoverageURL){		
		File multipartResponseFile = URLUtils.downloadBinaryFile(wcsGetCoverageURL, "wcsResponse.bin");
		String fileURL = URLUtils.getFileProtocolURL(multipartResponseFile.getAbsolutePath());
		System.out.println(fileURL);
		
		ServiceOutput output = new ServiceOutput();
		File  outputFilePath = output.getOutputFilePath("coverage.tif");
		
		try{
			URL url = new URL(fileURL);
			URLDataSource dataSource = new URLDataSource(url);

			MimeMultipart multipart = new MimeMultipart(dataSource);
			MimeBodyPart coverage = (MimeBodyPart) multipart.getBodyPart(1);
			coverage.saveFile(outputFilePath);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return output.getOutputURL();
	}
}

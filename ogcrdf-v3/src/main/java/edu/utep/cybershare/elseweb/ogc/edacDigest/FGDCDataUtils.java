package edu.utep.cybershare.elseweb.ogc.edacDigest;

import java.util.Calendar;

import java.util.GregorianCalendar;

public class FGDCDataUtils {
	
	public static Calendar getDate(String fgdcDate){
		int year = Integer.parseInt(fgdcDate.substring(0, 3));
		int day = Integer.parseInt(fgdcDate.substring(4, 5));
		int month = Integer.parseInt(fgdcDate.substring(6, 7));
		
		GregorianCalendar date = new GregorianCalendar();
		date.set(year, month, day);
		
		return date;
	}
}

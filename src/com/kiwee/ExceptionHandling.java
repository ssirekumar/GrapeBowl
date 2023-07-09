package com.kiwee;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

public class ExceptionHandling {
	
	/**
	 * Method to add the exception print stack trace log to Report.
	 * @author 	Siri Kumar Puttagunta
	 * @param  	Exception - exception object
	 * @return 	String 
	 * */
	public static String addStackTraceToReport(Exception e){
		String s = "";
		Writer writer = new StringWriter();
		PrintWriter printWriter = new PrintWriter(writer);
		e.printStackTrace(printWriter);
		s = writer.toString();
		return s.replace(System.getProperty("line.separator"), System.lineSeparator());
	}
	
}

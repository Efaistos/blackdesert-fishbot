package ru.namibios.arduino.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionUtils {

	private ExceptionUtils() {}
	
	public static String getString(Exception e) {
		
		StringWriter message = new StringWriter();
		e.printStackTrace(new PrintWriter(message));
		
		return message.toString();
	}
}

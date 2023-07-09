package com.kiwee.console;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class PlatformUtils {
	
	private static String _osName = System.getProperty("os.name");
	private static final String OS_ARCH = System.getProperty("os.arch");
	private static final String OS_VERSION = System.getProperty("os.version");
	private static final String JAVA_VERSION = System.getProperty("java.version");
	@FXML	private static TextArea txt_statusArea;
				
	/**
     * Function to retrieve OS Name for execution machine
	 * @author Siri Kumar Puttagunta
	 * @return String name of the OS*/
	public static String getOSName() {
		return _osName;
	}
	  
    /**
	 * Function to replace last occurrence of any substring
	 * @param String - string, String substring, String replacement
	 * @return String after the replacement operation*/
	public static String replaceLast(String string, String substring,
			String replacement) {
		int lastIndex = string.lastIndexOf(substring);
		if (lastIndex == -1) {
			return string;
		} else {
			return string.substring(0, lastIndex) + replacement
					+ string.substring(lastIndex + substring.length());
		}
	}

	/**
	 * Function to check the architecture of the system (64/32 bit)
	 * @author Siri Kumar Puttagunta
	 * @return true if 64 bit else false*/
	public static boolean is64Bit() {
		String arch = System.getenv("PROCESSOR_ARCHITECTURE");
		if (arch.endsWith("64")) {
			return true;
		} else {
			return false;
		}
	}
    
	/**
	 * Function to give architecture of the system (64/32 bit)
	 * @author Siri Kumar Puttagunta
	 * @return String of 64 bit else 32 bit*/
	public static String osArch(){
		return OS_ARCH;
	}
	
	/**
	 * Function to give JAVA Version installed on the machine.
	 * @author Siri Kumar Puttagunta
	 * @return String of java Version*/
	public static String javaVersion(){
		return JAVA_VERSION;
	}
    
	/**
	 * Function to give OS Version installed on the machine.
	 * @author Siri Kumar Puttagunta
	 * @return String of java Version*/
	public static String osVersion(){
		return OS_VERSION;
	}

	/**
	 * Function to run any command in command prompt
	 * @author Siri Kumar Puttagunta
	 * @param String command to be executed
	 * @return Process object for the command to be executed*/
	public static Process runInCMD(String command) {
		ProcessBuilder _builder = null;
		Process _proc = null;
		try {
			//command = "\""+command+"\"";
			if (PlatformUtils.getOSName().toLowerCase().indexOf("win") >= 0) {
				_builder = new ProcessBuilder("cmd.exe", "/c", command);
			} else if(PlatformUtils.getOSName().equalsIgnoreCase("linux")) {
				_builder = new ProcessBuilder("/bin/bash","-c", command);
			}
			_proc = _builder.start();
			TerminalOperations.pause(500);
		} catch (IOException ioe) {
			System.out.println("Exception error Message: "+ioe.getMessage());
			ioe.printStackTrace();
		}
		return _proc;
	}
	
	/**
	 * Function to load the property file
	 * @author Ssire Kumar Puttagunta
	 * @param String - path to the property file
	 * @return Properties object if success else null*/
	public static Properties loadPropertyFile(String path){
		Properties _props = new Properties();
		try{
			_props.load(new FileInputStream(path));
		} catch(IOException ioe){
			System.err.println(ioe.getMessage());
		}
		return _props;
	}
	
	/**
	 * Function to load the property file
	 * @author Ssire Kumar Puttagunta
	 * @param String - path to the property file
	 * @return Properties object if success else null*/
	public static Properties loadPropertyFile(InputStream inputStream){
		Properties _props = new Properties();
		try{
			_props.load(inputStream);
		} catch(IOException ioe){
			System.err.println(ioe.getMessage());
		}
		return _props;
	}
	
	/**
	 * Function to retrieve value from property file
	 * @author Ssire Kumar Puttagunta
	 * @param Properties - object, String key
	 * @return String value if found else null*/
	public static String getValueFromProperty(Properties _props, String key){
		String value = "";
		if(_props != null){
			value = _props.getProperty(key);
			if(value == null) {
				value = "";
			}
		} else {
			System.err.println("Specified .properties file was not found");
		}
		return value;
	}

		
	/**
     * Export a resource embedded into a Jar file to the local file path.
     *
     * @param resourceName ie.: "/SmartLibrary.dll"
     * @return The path to the exported resource
     * @throws Exception
     */
    static public String ExportResource(String resourceName) {
        InputStream stream = null;
        OutputStream resStreamOut = null;
        String jarFolder = "";
        try {
            stream = ClassLoader.class.getResourceAsStream(resourceName);
            System.out.println("in side the jar");
            //note that each / is a directory down in the "jar tree" been the jar the root of the tree
            if(stream == null) {
                throw new Exception("Cannot get resource \"" + resourceName + "\" from Jar file.");
            }
            System.out.println("in side the jar -1");
            int readBytes;
            byte[] buffer = new byte[4096];
            jarFolder = new File(ClassLoader.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParentFile().getPath().replace('\\', '/');
            resStreamOut = new FileOutputStream(jarFolder + resourceName);
            while ((readBytes = stream.read(buffer)) > 0) {
                resStreamOut.write(buffer, 0, readBytes);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            try {
				stream.close();
				resStreamOut.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        return jarFolder + resourceName;
    }
    
    /**
	 * Function to provide some static pause in milli-seconds
	 * @author Ssire Kumar Puttagunta
	 * @param long static pause time in milli-seconds*/
	public static void pause(long timeInMilliSeconds) {
		try {
			Thread.sleep(timeInMilliSeconds);
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		}
	}
}

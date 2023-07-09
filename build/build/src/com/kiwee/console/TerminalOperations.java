package com.kiwee.console;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

import com.kiwee.SupportMethods;
import com.kiwee.controllers.MainController;

public class TerminalOperations {
		
	/**
	 * Function to run adb commands in command prompt
	 * @author Ssire(Siri) Kumar Puttagunta
	 * @param String command to be executed
	 * @return Process object for the command to be executed*/
 	public static Process adbCommandsRunInCMD(String command) {
		ProcessBuilder _builder = null;
		Process _proc = null;
		String WinOsCommandString = "", ADBParentPath= "", _commandCmd="";
		try {
			File _toolPath = new File(TerminalOperations.getAdbLocation());
			if(_toolPath != null) {
				if (PlatformUtils.getOSName().toLowerCase().indexOf("win") >= 0) {
					ADBParentPath = _toolPath.getParent() + "\\";
					_commandCmd = "\"" + command + "\"";
					WinOsCommandString = "cd /d \"" + ADBParentPath + "\" & cmd.exe /c " + _commandCmd;
					_builder = new ProcessBuilder("cmd.exe", "/c", WinOsCommandString);
				}else if (PlatformUtils.getOSName().startsWith("Mac")) {
					ADBParentPath = _toolPath.getParent() + "/";
					_commandCmd = "\"" + command + "\"";
					_builder = new ProcessBuilder("/bin/sh", "-c", "cd \"" + ADBParentPath + "\"; " + "./" + command);
				}else if (PlatformUtils.getOSName().equalsIgnoreCase("linux")) {
					ADBParentPath = _toolPath.getPath() +"\\";
					_commandCmd = "./" + command;
				}
			}
			_proc = _builder.start();
			TerminalOperations.pause(200);
		} catch (IOException ioe) {
			System.err.println(ioe.getMessage());
		}
		return _proc;
	}
 	
 	/**
	 * Function to run adb commands in command prompt
	 * @author Ssire(Siri) Kumar Puttagunta
	 * @param String command to be executed
	 * @return Process object for the command to be executed*/
 	public static Process JfrogCommandsRunInCMD(String command) {
		ProcessBuilder _builder = null;
		Process _proc = null;
		String WinOsCommandString = "", ADBParentPath= "", _commandCmd="";
		try {
			File _toolPath = new File(TerminalOperations.getJFrogLocation());
			if(_toolPath != null) {
				if (PlatformUtils.getOSName().toLowerCase().indexOf("win") >= 0) {
					ADBParentPath = _toolPath.getParent() + "\\";
					_commandCmd = "\"" + command + "\"";
					WinOsCommandString = "cd /d \"" + ADBParentPath + "\" & cmd.exe /c " + _commandCmd;
					_builder = new ProcessBuilder("cmd.exe", "/c", WinOsCommandString);
				}else if (PlatformUtils.getOSName().startsWith("Mac")) {
					ADBParentPath = _toolPath.getParent() + "/";
					_commandCmd = "\"" + command + "\"";
					_builder = new ProcessBuilder("/bin/sh", "-c", "cd \"" + ADBParentPath + "\"; " + "./" + command);
				}else if (PlatformUtils.getOSName().equalsIgnoreCase("linux")) {
					ADBParentPath = _toolPath.getPath() +"\\";
					_commandCmd = "./" + command;
					//_builder = new ProcessBuilder("/bin/sh", "-c", "cd \"" + ADBParentPath + "\"; " + "./" + command);
					//need to construct for Linux execution command.
				}
			}
			_proc = _builder.start();
			_proc.waitFor();
			TerminalOperations.pause(200);
		} catch (IOException ioe) {
			System.err.println(ioe.getMessage());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return _proc;
	}
 	
 	/**
	 * Function 
	 * @author Ssire(Siri) Kumar Puttagunta
	 * @param String command to be executed
	 * @return Process object for the command to be executed*/
	public static Process ideviceCommandsRun(String command) {
		ProcessBuilder _builder = null;
		Process _proc = null;
		String libimobiledevicePath="";
		try {
			libimobiledevicePath = TerminalOperations.getlibimobiledeviceLocation();
			if(libimobiledevicePath != null || libimobiledevicePath != "") {
		        if (PlatformUtils.getOSName().startsWith("Mac")) {
					_builder = new ProcessBuilder("/bin/sh", "-c", "cd \"" + libimobiledevicePath + "\"; " + "./" + command);
				}else if (PlatformUtils.getOSName().equalsIgnoreCase("linux")) {
					_builder = new ProcessBuilder("/bin/bash", "-c", "cd \"" + libimobiledevicePath + "\"; " + "./" + command);
					//need to construct for Linux execution command.
				}
			}
			_proc = _builder.start();
			TerminalOperations.pause(200);
		} catch (IOException ioe) {
			System.err.println(ioe.getMessage());
		}
		return _proc;
	}
	
	/**
	 * Function 
	 * @author Ssire(Siri) Kumar Puttagunta
	 * @param String command to be executed
	 * @return Process object for the command to be executed*/
	public static Process iOSDeployeeCommandsRun(String command) {
		ProcessBuilder _builder = null;
		Process _proc = null;
		String libimobiledevicePath="";
		try {
			libimobiledevicePath = TerminalOperations.getiOSDeployeLocation();
			if(libimobiledevicePath != null || libimobiledevicePath != "") {
		        if (PlatformUtils.getOSName().startsWith("Mac")) {
					_builder = new ProcessBuilder("/bin/sh", "-c", "cd \"" + libimobiledevicePath + "\"; " + "./" + command);
				}else if (PlatformUtils.getOSName().equalsIgnoreCase("linux")) {
					_builder = new ProcessBuilder("/bin/bash", "-c", "cd \"" + libimobiledevicePath + "\"; " + "./" + command);
					//need to construct for Linux execution command.
				}
			}
			_proc = _builder.start();
			TerminalOperations.pause(200);
		} catch (IOException ioe) {
			System.err.println(ioe.getMessage());
		}
		return _proc;
	}

	/**
	 * Function to return output from command prompt
	 * @author Siri Kumar Puttagunta
	 * @param Process - object
	 * @return ArrayList containing entire output from command prompt*/
	public static ArrayList<String> getadbCommandsOutputCMD(Process proc) {
		ArrayList<String> _output = new ArrayList<String>();
		boolean readInputlineFound = false;
		boolean readErrorlineFound = false;
		Scanner _sc = null;
		String lineRead = "";
		try {
			_sc = new Scanner(new InputStreamReader(proc.getInputStream()));
			while(_sc.hasNextLine()){
				readInputlineFound = true;
				if((lineRead = _sc.nextLine()) != null){
					_output.add(lineRead);
				}else{
					_output.add(null);
				}
			}
			if(!readInputlineFound || _output.contains("")){
				_sc = new Scanner(new InputStreamReader(proc.getErrorStream()));
				while(_sc.hasNextLine()){
					readErrorlineFound = true;
					if((lineRead = _sc.nextLine()) != null){
						_output.add(lineRead);
					}else{
						_output.add(null);
					}
				}
			}
			if((!readErrorlineFound && !readInputlineFound) || ((_output.get(0).indexOf("List of devices attached") != -1) && (_output.get(1).indexOf(" ") != -1))){
				_output.clear();
			}
			_sc.close();			
		}catch (NullPointerException ne) {
			System.err.println(ne.getMessage());
		}catch(Exception e){
			System.err.println(e.getMessage());
		}
		//_output.removeAll(Arrays.asList("", " ", null));
		return _output;
	}
    
	
	/**
	 * Function to return output from command prompt
	 * @author Siri Kumar Puttagunta
	 * @param Process - object
	 * @return ArrayList containing entire output from command prompt*/
	public static ArrayList<String> getConsoleCommandsOutput(Process proc) {
		ArrayList<String> _output = new ArrayList<String>();
		boolean readInputlineFound = false;
		boolean readErrorlineFound = false;
		Scanner _sc = null;
		String lineRead = "";
		try {
			_sc = new Scanner(new InputStreamReader(proc.getInputStream()));
			while(_sc.hasNextLine()){
				readInputlineFound = true;
				if((lineRead = _sc.nextLine()) != null){
					_output.add(lineRead);
				}else{
					_output.add(null);
				}
			}
			if(!readInputlineFound || _output.contains("")){
				_sc = new Scanner(new InputStreamReader(proc.getErrorStream()));
				while(_sc.hasNextLine()){
					readErrorlineFound = true;
					if((lineRead = _sc.nextLine()) != null){
						_output.add(lineRead);
					}else{
						_output.add(null);
					}
				}
			}
			if((!readErrorlineFound && !readInputlineFound)  && (_output.get(1).indexOf(" ") != -1)){
				_output.clear();
			}
			_sc.close();			
		}catch (NullPointerException ne) {
			System.err.println(ne.getMessage());
		}catch(Exception e){
			System.err.println(e.getMessage());
		}
		return _output;
	}
	
	/**
	 * Function to return output from command prompt
	 * @author Siri Kumar Puttagunta
	 * @param Process - object
	 * @return ArrayList containing entire output from command prompt*/
	public static ArrayList<String> getConsoleJFrogCommandsOutput(Process proc) {
		ArrayList<String> _output = new ArrayList<String>();
		boolean readInputlineFound = false;
		boolean readErrorlineFound = false;
		Scanner _sc = null;
		String lineRead = "";
		try {
			_sc = new Scanner(new InputStreamReader(proc.getInputStream()));
			while(_sc.hasNextLine()){
				readInputlineFound = true;
				if((lineRead = _sc.nextLine()) != null){
					_output.add(lineRead);
				}else{
					_output.add("");
				}
			}
			if(!readInputlineFound || _output.contains("")){
				_sc = new Scanner(new InputStreamReader(proc.getErrorStream()));
				while(_sc.hasNextLine()){
					readErrorlineFound = true;
					if((lineRead = _sc.nextLine()) != null){
						_output.add(lineRead);
					}else{
						_output.add("");
					}
				}
			}
			if((!readErrorlineFound && !readInputlineFound)  && (_output.get(1).indexOf(" ") != -1)){
				_output.clear();
			}
			_sc.close();			
		}catch (NullPointerException ne) {
			System.err.println(ne.getMessage());
		}catch(Exception e){
			System.err.println(e.getMessage());
		}
		return _output;
	}
	
	/**
	 * Function to provide some static pause in milli-seconds
	 * @author Siri Kumar Puttagunta
	 * @param long static pause time in milli-seconds*/
	public static boolean pause(long timeInMilliSeconds) {
		boolean pauseTime = false;
		try {
			Thread.sleep(timeInMilliSeconds);
			pauseTime = true;
		} catch (InterruptedException ie) {
			System.err.println(ie.getMessage());
		}
		return pauseTime;
	}

     
    public static String getAdbLocation(){
    	String fc = File.separator;
		String path = "";
		String properiesFile = System.getProperty("user.dir") + fc + "config" + fc + "config.properties";
		path = PlatformUtils.getValueFromProperty(PlatformUtils.loadPropertyFile(properiesFile), "ADB_PATH");
		if((new File(path).isFile() != true)) {
			return "";
		}
		return path;
    }
    
    public static String getJFrogLocation() {
    	String fc = File.separator;
    	String path = "";
    	try {
    		if (PlatformUtils.getOSName().toLowerCase().indexOf("win") >= 0) {
    			File jfrogObject = new File(System.getProperty("user.dir") + fc + "jfrogcli" + fc + "windows" + fc + "jfrog.exe");
    			path = jfrogObject.getCanonicalPath();
    		}else if (PlatformUtils.getOSName().startsWith("Mac")) {
    			File jfrogObject = new File(System.getProperty("user.dir") + fc + "jfrogcli" + fc + "mac" + fc + "jfrog");
    			path = jfrogObject.getCanonicalPath();
    		}else if (PlatformUtils.getOSName().equalsIgnoreCase("linux")) {
    			File jfrogObject = new File(System.getProperty("user.dir") + fc + "jfrogcli" + fc + "linux" + fc + "jfrog");
    			path = jfrogObject.getCanonicalPath();
    		}
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}	
		if((new File(path).isFile() != true)) {
			return "";
		}
		return path;
    }
    
    public static String getlibimobiledeviceLocation(){
    	String fc = File.separator;
		String path = "";
		String properiesFile = System.getProperty("user.dir") + fc + "config" + fc + "config.properties";
		path = PlatformUtils.getValueFromProperty(PlatformUtils.loadPropertyFile(properiesFile), "iOS_LIBMOBILE_PATH");
		if((new File(path).isDirectory() != true)) {
			return "";
		}
		return path;
    }
    
    public static String getiOSDeployeLocation(){
    	String fc = File.separator;
		String path = "";
		String properiesFile = System.getProperty("user.dir") + fc + "config" + fc + "config.properties";
		path = PlatformUtils.getValueFromProperty(PlatformUtils.loadPropertyFile(properiesFile), "iOS_DEPLOY_PATH");
		if((new File(path).isDirectory() != true)) {
			return "";
		}
		return path;
    }
    
    /**
     * Function to destroy the Process object created by CommonFunctions.runCMD method
     * @author Siri Kumar Puttagunta
     * @param Process - object*/
    public static void destroyCMDProcess(Process proc) {
    	try {
    		if (proc != null) {
    			proc.destroy();
    		}
    	} catch (Exception e) {
    		System.out.println(e.getMessage());
    	}
    }
    
    /**
     * Function to get all the list of android devices serial numbers. This method will take 
     * input of adb devices -l output as input.
     * @author Siri Kumar Puttagunta
     * @param ArrayList<String> - object*/
    public static ArrayList<String> getDeviceListFromAdbCommandOutput(ArrayList<String> cmdOutpur){
    	ArrayList<String> _ListDevices = new ArrayList<String>();
    	for(String _str : cmdOutpur){
			try {
				if(_str.endsWith("device")){
					String serialNumber = _str.split("device")[0].trim();
					_ListDevices.add(serialNumber);
				}else if (_str.endsWith("device:m0")){
					String serialNumber = _str.split("device product")[0].trim();
					_ListDevices.add(serialNumber);
				}
			} catch (NullPointerException nu) {
				if(_str == null){
					break;
				}
			}catch(Exception ex){
				System.err.println("Exception caught while finding the devices");
				System.err.println(ex.getMessage());
			}
		}
    	return _ListDevices;
    }
    
    /**
     * 
     * @param command
     * @return
     */
    public static Process commandsRunInCMD(String command) {
    	ProcessBuilder _builder = null;
		Process _proc = null;
		if (PlatformUtils.getOSName().toLowerCase().indexOf("win") >= 0) {
			_builder = new ProcessBuilder("cmd.exe", "/c", command);
		}else if (PlatformUtils.getOSName().startsWith("Mac")) {
			_builder = new ProcessBuilder("/bin/sh", "-c", command);
		}else if (PlatformUtils.getOSName().equalsIgnoreCase("linux")) {
			//_builder = new ProcessBuilder("/bin/bash", "-c", "cd \"" + ADBParentPath + "\"; " + "./" + command);
			//need to construct for Linux execution command.
		}
		try {
			_proc = _builder.start();
			_proc.waitFor();
			TerminalOperations.pause(200);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return _proc;
    }
    
    

}

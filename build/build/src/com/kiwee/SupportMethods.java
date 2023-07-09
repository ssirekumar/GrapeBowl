package com.kiwee;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

import com.kiwee.application.Kiwee;
import com.kiwee.console.PlatformUtils;
import com.kiwee.console.TerminalOperations;
import com.kiwee.controllers.MainController;
import au.com.bytecode.opencsv.CSVWriter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.FileChooser.ExtensionFilter;

public class SupportMethods {
	
	private static Process _procAdb = null;
	private static String timeStampString = "";
	private static Timestamp timestamp; 
	private static String fc = File.separator;
	private static String currentDir = System.getProperty("user.dir") + fc;
	private static String[] propertiesFilenames =  {
			"config.properties", 
			"global.properties", 
			"youradbs.properties", 
			"application.log"
			};
    private static String[] propertiesRootFolder = {"config","log"};

	public static boolean writeAdbPathIntoPropertiesFile(String keyName, String value, String fileName) {
		BufferedWriter output = null;
		boolean writtenThing = false;
		String properiesFile = currentDir + propertiesRootFolder[0] + fc + fileName;
		try {
			Properties props = PlatformUtils.loadPropertyFile(properiesFile);
			output = new BufferedWriter(new FileWriter(properiesFile));
			props.setProperty(keyName, value);
			props.store(output, null);
			writtenThing = true;
		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return writtenThing;
	}
	
	public static boolean writeAdbPathIntoPropertiesFile(String keyName, String value, String comment, String fileName) {
		BufferedWriter output = null;
		boolean writtenThing = false;
		String properiesFile = currentDir + propertiesRootFolder[0] + fc + fileName;
		try {
			Properties props = PlatformUtils.loadPropertyFile(properiesFile);
			output = new BufferedWriter(new FileWriter(properiesFile));
			props.setProperty(keyName, value);
			props.store(output, comment);
			writtenThing = true;
		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return writtenThing;
	}
	
	public static boolean writeAdbPathIntoPropertiesFile(Map<String, String> keyValuePairs, String fileName) {
		BufferedWriter output = null;
		boolean writtenThing = false;
		String properiesFile = currentDir + propertiesRootFolder[0] + fc + fileName;
		if (keyValuePairs != null) {
			try {
				Properties props = PlatformUtils.loadPropertyFile(properiesFile);
				output = new BufferedWriter(new FileWriter(properiesFile));
				props.putAll(keyValuePairs);
				props.store(output, null);
				writtenThing = true;
			} catch (IOException io) {
				io.printStackTrace();
			} finally {
				if (output != null) {
					try {
						output.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return writtenThing;
	}
	
	public static boolean removeIntoPropertiesFile(String keyName, String fileName) {
		BufferedWriter output = null;
		boolean deleteThing = false;
		String fc = File.separator;
		String properiesFile = currentDir + propertiesRootFolder[0] + fc + fileName;
		try {
			Properties props = PlatformUtils.loadPropertyFile(properiesFile);
			output = new BufferedWriter(new FileWriter(properiesFile));
			props.remove(keyName);
			props.store(output, null);
			deleteThing = true;
		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return deleteThing;
	}
	
	public static boolean deleteKeyValueFromYourAdbsPropertiesFile(String keyname) {
		String[] allStringArray = new String[]{propertiesFilenames[2].toString(), System.getProperty("user.dir"), File.separator};
		String properiesFile = allStringArray[1] + allStringArray[2] + propertiesRootFolder[0] + allStringArray[2] + allStringArray[0];
		return SupportMethods.removeIntoPropertiesFile(keyname, Paths.get(properiesFile).getFileName().toString());
	}
	
	public static ObservableList<String> getAllKeysFromYourAdbsPropertiesFile() {
		String[] allStringArray = new String[]{propertiesFilenames[2].toString(), System.getProperty("user.dir"), File.separator};
		String properiesFile = allStringArray[1] + allStringArray[2] + propertiesRootFolder[0] + allStringArray[2] + allStringArray[0];
		ObservableList<String> allKeys = SupportMethods.getAllTheKeysFromPropertyFile(PlatformUtils.loadPropertyFile(properiesFile));
		return allKeys;
	}
	
	public static String getKeyValueFromYourAdbsPropertiesFile(String key) {
		String[] allStringArray = new String[]{propertiesFilenames[2].toString(), System.getProperty("user.dir"), File.separator};
		String properiesFile = allStringArray[1] + allStringArray[2] + propertiesRootFolder[0] + allStringArray[2] + allStringArray[0];
		return PlatformUtils.getValueFromProperty(PlatformUtils.loadPropertyFile(properiesFile), key);
	}
	
	
	public static String getKeyValueFromConfigFile(String key) {
		String[] allStringArray = new String[]{propertiesFilenames[0].toString(), System.getProperty("user.dir"), File.separator};
		String properiesFile = allStringArray[1] + allStringArray[2] + propertiesRootFolder[0] + allStringArray[2] + allStringArray[0];
		return PlatformUtils.getValueFromProperty(PlatformUtils.loadPropertyFile(properiesFile), key);
	}
	
	public static ObservableList<String> getAllTheKeysFromPropertyFile(Properties file){
		Set<String> allkeysStrings = new HashSet<String>();
 		Set<Object> keys = file.keySet();
		for (Object object : keys) {
			allkeysStrings.add((String)object);
		}
		ObservableList<String> allKeys = FXCollections.observableArrayList(allkeysStrings);
		return allKeys;
	}
	
	public static Optional<ButtonType> alertExceptionMessage(){
		Alert alert = new Alert(AlertType.ERROR);
        alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.CANCEL);
        alert.setResizable(false);
        alert.setTitle("Exception occurred");
        alert.setHeaderText("Oops! Something went wrong\nThe application has encountered an unknown error.");
        alert.setContentText("To see entire exception stacktrace message in action log please click on Yes");
        alert.initStyle(StageStyle.UNIFIED);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(Object.class.getClass().getResource("/resources/images/kiwee_logo.png").toString())); 
        return alert.showAndWait();
	}
	
	public static Optional<ButtonType> alertMessage(String messageContext, AlertType type, String title, String headerString){
		Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setResizable(false);
        alert.setHeaderText(headerString);
        alert.setContentText(messageContext);
        alert.initStyle(StageStyle.UNIFIED);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(Object.class.getClass().getResource("/resources/images/kiwee_logo.png").toString()));
        stage.setAlwaysOnTop(true);
        //alert.setGraphic(new ImageView(Object.class.getResource("/resources/images/ane-dialog-icon.png").toString()));
        return alert.showAndWait();
	}
	
	public static Optional<ButtonType> alertMessage(String messageContext, AlertType type, String title, String headerString, boolean buttonYesNoType){
		Alert alert = new Alert(type);
		if(buttonYesNoType) {alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.CANCEL);}
        alert.setResizable(false);
        alert.setTitle(title);
        alert.setHeaderText(headerString);
        alert.setContentText(messageContext);
        alert.initStyle(StageStyle.UNIFIED);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(Object.class.getClass().getResource("/resources/images/kiwee_logo.png").toString())); 
        return alert.showAndWait();
	}
	
	/**
	 * Get the version number of the kiwee tool
	 * @return String
	 */
	public static String getVersionOfKiwee(){
		String vesionNumber = "";
		//String properiesFile = currentDir + propertiesRootFolder[0] + fc + propertiesFilenames[1];
		InputStream properiesFile = Object.class.getClass().getResourceAsStream("/resources/global.properties");
		vesionNumber = PlatformUtils.getValueFromProperty(PlatformUtils.loadPropertyFile(properiesFile), "KIWEE_TOOL_VERSION");
		return vesionNumber;
	}
	
	public static ObservableList<String> getAllAndroidPackagesNamesFromConfig(){
		String path = "";
		ObservableList<String> oListStavaka = FXCollections.observableArrayList();
		String properiesFile = currentDir + propertiesRootFolder[0] + fc + propertiesFilenames[0];
		path = PlatformUtils.getValueFromProperty(PlatformUtils.loadPropertyFile(properiesFile), "ANDROID_APP_PACKAGE_NAMES");
		if(path != null) {
			oListStavaka = FXCollections.observableArrayList(Arrays.asList(path.split("\\|")));
		}
		return oListStavaka;
	}
	
	public static ObservableList<String> getAllDeviceAttributeNames(){
		String attributes =  
				  "Device manufacturer|Android version|Android code name|Time Zone"
				+ "|Country code|Model number|Baseband version|Build number"
				+ "|Kernal version|Android Os language|Android SDK-API version|Device IMEI number"
				+ "|Device DPI(Dot Per Inch)|Is ARM or Intel x86|Sim operator|Test/Release keys";
		ObservableList<String> oListStavaka = FXCollections.observableArrayList(Arrays.asList(attributes.split("\\|")));
		return oListStavaka;
	}
	
	public static ArrayList<String> getConnectedDevices(){
		ArrayList<String> _strList = null;
		ArrayList<String> _devicesSerialNums = null;
		try {
			TerminalOperations.adbCommandsRunInCMD("adb start-server");
			_procAdb = TerminalOperations.adbCommandsRunInCMD("adb devices");
			_strList = TerminalOperations.getadbCommandsOutputCMD(_procAdb);
			_devicesSerialNums = TerminalOperations.getDeviceListFromAdbCommandOutput(_strList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return _devicesSerialNums;
	}
	
	public static ArrayList<String> getConnectediOSDevices(){
		ArrayList<String> _devicesSerialNums = null;
		try {
			_procAdb = TerminalOperations.ideviceCommandsRun("idevice_id -l");
			_devicesSerialNums = TerminalOperations.getConsoleCommandsOutput(_procAdb);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return _devicesSerialNums;
	}
	
	public static Map<String, String> getDeviceInfoAdbCommands(String deviceSerialNumber){
		LinkedHashMap<String, String> _map = new LinkedHashMap<String, String>();
		 _map.put("Device manufacturer", "adb -s " + deviceSerialNumber +" shell getprop ro.product.manufacturer");
		 _map.put("Android version", "adb -s " + deviceSerialNumber + " shell getprop ro.build.version.release");
		 _map.put("Android code name", "adb -s " + deviceSerialNumber + " shell getprop ro.build.id");
		 _map.put("Model number", "adb -s " + deviceSerialNumber + " shell getprop ro.product.model");
		 _map.put("Baseband version", "adb -s " + deviceSerialNumber + " shell getprop gsm.version.baseband");
		 _map.put("Build number", "adb -s " + deviceSerialNumber + " shell getprop ro.build.display.id");
		 _map.put("Kernal version", "adb -s " + deviceSerialNumber + " shell cat /proc/version");
		 _map.put("Android Os language", "adb -s " + deviceSerialNumber + " shell getprop ro.product.locale.language");
		 _map.put("Android SDK-API version", "adb -s " + deviceSerialNumber + " shell getprop ro.build.version.sdk");
		 _map.put("Device IMEI number", "adb -s " + deviceSerialNumber + " shell dumpsys iphonesubinfo");
		 //_map.put("Phone Type(GSM/CDMA)", "adb -s " + deviceSerialNumber + " shell getprop ro.build.version.release");
		 _map.put("Device DPI(Dot Per Inch)", "adb -s " + deviceSerialNumber + " shell getprop ro.sf.lcd_density");
		 _map.put("Time zone", "adb -s " + deviceSerialNumber + " shell getprop persist.sys.timezone");	
		 _map.put("Country code", "adb -s " + deviceSerialNumber + " shell getprop ro.csc.country_code");
		 _map.put("Is ARM or Intel x86", "adb -s " + deviceSerialNumber + " shell getprop ro.product.cpu.abi");
		 _map.put("Sim operator", "adb -s " + deviceSerialNumber + " shell getprop gsm.sim.operator.alpha");
		 _map.put("Test/Release keys", "adb -s " + deviceSerialNumber + " shell getprop ro.build.tags");		 
		// _map.put("GPU(Graphics Processing Unit)", "adb -s " + deviceSerialNumber + " shell getprop ro.build.version.release");
		return _map;
	 }
	
	public static boolean writeTestDataInFile(String filePath, ArrayList<ArrayList<String>> contents, boolean appendMode){
		  List<String[]> writeContents = new ArrayList<String[]>();
		  boolean returnValue = false;
		  try {
			  for (ArrayList<String> row : contents) {
				  writeContents.add(row.toArray(new String[row.size()]));
			  }
			  CSVWriter csvWriter = new CSVWriter(new FileWriter(filePath,appendMode));
			  csvWriter.writeAll(writeContents);
			  csvWriter.close();
			  returnValue = true;
		  } catch (FileNotFoundException fne) {
			  System.err.println(fne.getMessage());
		  } catch (IOException ioe) {
			  System.err.println(ioe.getMessage());
		  } catch (NullPointerException npe) {
			  System.err.println(npe.getMessage());
		  } catch (ArrayIndexOutOfBoundsException ae) {
			  System.err.println(ae.getMessage());
		  }
		  return returnValue;
	  }
	
	//non implemented method
	public static List<String> getFilteredAdbCommandOutput(List<String> output, String filterString){
	
		return output;
		
	}
	
	public static StringBuilder eventLogMessageformatter(List<String> data, String extratext){
		StringBuilder _builder = new StringBuilder();
		timestamp = new Timestamp(System.currentTimeMillis());
		int i=0;
		for (String string : data) {
			string = string.replace("\t", "");
			if(i==0){
				_builder.append("["+timestamp+"]: ").append(extratext).append(string);
			}else{
				_builder.append(System.lineSeparator()+" \t\t\t"+string);
			}
			i++;
		}
		_builder.append(System.lineSeparator());
		return _builder;
	}
	
	public static StringBuilder eventLogMessageformatter(String extratext){
		StringBuilder _builder = new StringBuilder();
		timestamp = new Timestamp(System.currentTimeMillis());
		_builder.append("["+timestamp+"]: ").append(extratext);
		_builder.append(System.lineSeparator());
		return _builder;
	}
	
	public static String getCurrentTimeStamp() {
		timestamp = new Timestamp(System.currentTimeMillis());
		return "[" + timestamp + "]: ";
	}
	
	public static File fileChooserDialog(ExtensionFilter fileFilter) {
		File returnFilePath = null;
		final FileChooser fileChooser = new FileChooser();
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		fileChooser.setTitle("Open File");
		fileChooser.getExtensionFilters().addAll(fileFilter);
		returnFilePath = fileChooser.showOpenDialog(Kiwee.getPrimaryStage());
		if (returnFilePath != null) {
			return returnFilePath;
		}
		return returnFilePath;
	}
	
	public void contextMenuForInstallApplication() {
		ContextMenu contextMenu = new ContextMenu();
        MenuItem item1 = new MenuItem("Menu Item 1");
        item1.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                
            }
        });
        
	}
	
	/**
	 * Get the application installed device list as per the given package name.
	 * @param packagename
	 * @return  String device list
	 */
	public static ObservableList<String> getAndroidApplicationInstalledDevice(ObservableList<String> packagenames, boolean searchMultiple) {
		TreeSet<String> _devi = new TreeSet<>();
		ArrayList<String> _multipleAppOndevices = new ArrayList<>();
		TreeSet<String> gatheredDevices = new TreeSet<>();
		ArrayList<String> _devices = SupportMethods.getConnectedDevices();
		ArrayList<String> _strList = null;
		int ifrequency = 0;
		
		for (String device : _devices) {
			for (String packagename : packagenames) {
				_strList = getAndroidApplicationIfInstalled(packagename, device);
				if(packagenames.size()>1) {
					if(!_strList.isEmpty()) {
						if (searchMultiple) {
							if (_strList.contains("package:" + packagename)) {
								_multipleAppOndevices.add(device);
							}
						}
					}
				}else {
					if (_strList.contains("package:" + packagename)) {
						_devi.add(device);
					}
				}
			}
		}
		if(searchMultiple) {
			for (String device : _devices) {
				ifrequency = Collections.frequency(_multipleAppOndevices, device);
				if(ifrequency > 1) {
					gatheredDevices.add(device);
				}
			}
			if(packagenames.size()>1) {
				if(!gatheredDevices.isEmpty()) {
					return FXCollections.observableArrayList(gatheredDevices);
				}else {
					_devi.clear();
					return FXCollections.observableArrayList(_devi);
				}
			}else {
				return FXCollections.observableArrayList(_devi);
			}
		}else {
			return FXCollections.observableArrayList(_devi);
		}
	}
	
	
	/**
	 * Get the application installed device list as per the given package name.
	 * @param packagename
	 * @return  String device list
	 */
	public static ObservableList<String> getiOSApplicationInstalledDevice(ObservableList<String> bundleidNames, boolean searchMultiple) {
		TreeSet<String> _devi = new TreeSet<>();
		ArrayList<String> _multipleAppOndevices = new ArrayList<>();
		TreeSet<String> gatheredDevices = new TreeSet<>();
		ArrayList<String> _devices = SupportMethods.getConnectediOSDevices();
		ArrayList<String> _strList = null;
		int ifrequency = 0;
		
		for (String device : _devices) {
			for (String bundleidName : bundleidNames) {
				_strList = getiOSApplicationIfInstalled(bundleidName, device);
				if(bundleidNames.size()>1) {
					if(!_strList.isEmpty()) {
						if (searchMultiple) {
							if (_strList.contains(bundleidName)) {
								_multipleAppOndevices.add(device);
							}
						}
					}
				}else {
					if (_strList.contains(bundleidName)) {
						_devi.add(device);
					}
				}
			}
		}
		if(searchMultiple) {
			for (String device : _devices) {
				ifrequency = Collections.frequency(_multipleAppOndevices, device);
				if(ifrequency > 1) {
					gatheredDevices.add(device);
				}
			}
			if(bundleidNames.size()>1) {
				if(!gatheredDevices.isEmpty()) {
					return FXCollections.observableArrayList(gatheredDevices);
				}else {
					_devi.clear();
					return FXCollections.observableArrayList(_devi);
				}
			}else {
				return FXCollections.observableArrayList(_devi);
			}
		}else {
			return FXCollections.observableArrayList(_devi);
		}
	}
	
	
	public static ArrayList<String> getAndroidApplicationIfInstalled(String packagename, String device) {
		ArrayList<String> _strList = new ArrayList<>();
		if (PlatformUtils.getOSName().equalsIgnoreCase("linux")) {
			_procAdb = TerminalOperations.adbCommandsRunInCMD("adb -s " + device
					+ " shell pm list packages | grep " + packagename.trim());
			_strList = TerminalOperations.getadbCommandsOutputCMD(_procAdb);
		} else if (PlatformUtils.getOSName().toLowerCase().indexOf("win") >= 0) {
			_procAdb = TerminalOperations.adbCommandsRunInCMD("adb -s " + device
					+ " shell pm list packages | findstr /r /c:\"\\<" + packagename + "\\>\"");
			_strList = TerminalOperations.getadbCommandsOutputCMD(_procAdb);
		}else if(PlatformUtils.getOSName().startsWith("Mac")){
			_procAdb = TerminalOperations.adbCommandsRunInCMD("adb -s " + device
					+ " shell pm list packages | grep " + packagename.trim());
			_strList = TerminalOperations.getadbCommandsOutputCMD(_procAdb);
		} else {
			SupportMethods.alertMessage("Curently Kiwee is not supporting for " + PlatformUtils.getOSName() +" OS",
					AlertType.WARNING, "Warning", "Not Supporting");
		}
		return _strList;
	}
	
	public static ArrayList<String> getiOSApplicationIfInstalled(String bundleid, String device) {
		ArrayList<String> _strList = new ArrayList<>();
		String iosFindpackageName = "ios-deploy --id " + device + " --list_bundle_id | grep " + bundleid.trim();
		if (PlatformUtils.getOSName().equalsIgnoreCase("linux")) {
			_procAdb = TerminalOperations.iOSDeployeeCommandsRun(iosFindpackageName);
			_strList = TerminalOperations.getConsoleCommandsOutput(_procAdb);
		}else if(PlatformUtils.getOSName().startsWith("Mac")){
			_procAdb = TerminalOperations.iOSDeployeeCommandsRun(iosFindpackageName);
			_strList = TerminalOperations.getConsoleCommandsOutput(_procAdb);
		} else {
			SupportMethods.alertMessage("Curently Kiwee is not supporting for " + PlatformUtils.getOSName() +" OS",
					AlertType.WARNING, "Warning", "Not Supporting");
		}
		return _strList;
	}
	
	/**
	 * Launch the android/iOS application once the instalation completed.
	 */
	public static void launchTheApplicationOnceItInstalationCompleted() {
		
	}
	
	/**
	 * 
	 * @param format
	 * @return
	 */
	public static String currentDate(String format) {
		SimpleDateFormat df = new SimpleDateFormat(format);
		return df.format(new Date());
	}
	
	public static boolean isCommandHaveConnectedDeviceName(TextArea txtArea, ListView<String> lstDevices) {
		boolean returnValue = false;
		int index =0;
		String adbCommand = txtArea.getText(); 
		ObservableList<String> listOfDevices = lstDevices.getItems();
		Boolean[] arrayOf = new Boolean[listOfDevices.size()];
		if(adbCommand.startsWith("adb")) {
			for (String deviceName : listOfDevices) {
				arrayOf[index] = Boolean.valueOf(adbCommand.contains("adb -s "+deviceName));
				index++;
			}
			if(Arrays.asList(arrayOf).contains(false)) {
				returnValue = false;
			}else {
				returnValue = true;
			}
		}
		return returnValue;
	}
	
	
	public static boolean isListContain(List<String> arraylist, String searchString) {
	    for (String str : arraylist) {
	        if (str.contains(searchString)) {
	            return true;
	        }
	    }
	    return false;
	}
	
	
	public static void giveExecutePermissionToYourScript(File pathOfTheScript) {
    	String Command = "";
		try {
			Command = "chmod +x " + pathOfTheScript.getCanonicalPath();
		} catch (IOException e) {
			e.printStackTrace();
		} 
    	TerminalOperations.commandsRunInCMD(Command);
    }
	
	/*
	public static void bringingExceptions(Exception e) {
		TextArea txt_statussArea = MainController.getInstance().getStatusTextArea();
		txt_statussArea.appendText(ExceptionHandling.addStackTraceToReport(e));
		txt_statussArea.setScrollLeft(0);
    }*/
}

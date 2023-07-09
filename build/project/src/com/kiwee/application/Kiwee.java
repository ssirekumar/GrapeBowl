package com.kiwee.application;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import com.kiwee.ModuleLoders;
import com.kiwee.SupportMethods;
import com.kiwee.console.TerminalOperations;
import com.kiwee.controllers.MainController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;


public class Kiwee extends Application {
	
	private static Stage pStage;
	private static Scene pScene;
	private static Object bugsaverObject, customExecuteCommand, JFrogObect, preExecuteCommand,
	                      imageComparision;
	private ModuleLoders<Parent> moduleloader;
	private String fc = File.separator;
	private String[] _filenames =  {
			"config.properties", 
			"global.properties", 
			"youradbs.properties", 
			"application.log"
			};
	private Map<String, String> _configKeyValues; 
	
	@Override
	public void start(Stage primaryStage) {
		try {
			//Load scene while loading main module.
			moduleloader = new ModuleLoders<>();
			bugsaverObject = moduleloader.moduleloader("/resources/BugReportSaverScene.fxml");
			preExecuteCommand = moduleloader.moduleloader("/resources/PreAdbCommandExecutorScene.fxml");
			customExecuteCommand = moduleloader.moduleloader("/resources/AdbCustomCommandsScene.fxml");
			imageComparision = moduleloader.moduleloader("/resources/ImageComaparision.fxml");
			JFrogObect = moduleloader.moduleloader("/resources/JFrogSceneController.fxml");
			//kiweePreferences = moduleloader.moduleloader("/resources/PropertiesValuesScene.fxml");
			

			//
			String currentDir = System.getProperty("user.dir") + fc;
			File[] folderCreation = {
					new File(currentDir + "config"), 
					new File(currentDir + "log")};
			FileFilter _propertiesFileFilter = new FileFilter() {
				@Override
				public boolean accept(File pathname) {
					return pathname.getName().endsWith(".properties");
				}
			}; 
			FileFilter _logFileFilter = new FileFilter() {
				@Override
				public boolean accept(File pathname) {
					return pathname.getName().endsWith(".log");
				}
			}; 
			FileFilter _configFileFilter = new FileFilter() {
				@Override
				public boolean accept(File pathname) {
					return pathname.getName().startsWith("config.properties");
				}
			}; 
			FileFilter _globalFileFilter = new FileFilter() {
				@Override
				public boolean accept(File pathname) {
					return pathname.getName().startsWith("global.properties");
				}
			}; 
			FileFilter _youradbsFileFilter = new FileFilter() {
				@Override
				public boolean accept(File pathname) {
					return pathname.getName().startsWith("youradbs.properties");
				}
			}; 
			for (File file : folderCreation) {
				String getTheCanonicalPath = file.getCanonicalPath() + fc;
				if(file != null) {
					if(!file.exists()) {
						file.mkdir();
						createConfigFileForKiweeApplication(file, getTheCanonicalPath, new FileFilter[] {_propertiesFileFilter, _logFileFilter});
						this.defautlKeyNamesForPropertiesFiles();
					}else {
						if(file.isDirectory() && ((file.equals(folderCreation[0]) && (file.listFiles(_propertiesFileFilter).length == 0)))) {
							createConfigFileForKiweeApplication(file, getTheCanonicalPath, new FileFilter[] {_propertiesFileFilter, _logFileFilter});
							this.defautlKeyNamesForPropertiesFiles();
						}else if(file.isDirectory() && ((file.equals(folderCreation[1]) && (file.listFiles(_logFileFilter).length == 0)))) {
							createConfigFileForKiweeApplication(file, getTheCanonicalPath, new FileFilter[] {_propertiesFileFilter, _logFileFilter});
							this.defautlKeyNamesForPropertiesFiles();
						}else if((file.equals(folderCreation[0]) && (file.listFiles(_configFileFilter).length == 0))){
							createSingleConfigFile(getTheCanonicalPath + _filenames[0]);
							this.defautlKeyNamesForPropertiesFiles();
						}else if((file.equals(folderCreation[0]) && (file.listFiles(_globalFileFilter).length == 0))){
							//createSingleConfigFile(getTheCanonicalPath + _filenames[1]);
						}else if((file.equals(folderCreation[0]) && (file.listFiles(_youradbsFileFilter).length == 0))){
							createSingleConfigFile(getTheCanonicalPath + _filenames[2]);
						}else if((file.equals(folderCreation[0]) && (file.listFiles(_youradbsFileFilter).length == 0))){
							createSingleConfigFile(getTheCanonicalPath + _filenames[2]);
						}
					}
				}
			}
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/resources/KiweetoolScene.fxml"));
			Parent root = loader.load(); //Parent root = FXMLLoader.load(getClass().getResource("/fx_resource/AiUitoolFxml.fxml"));
			pScene = new Scene(root);
			pScene.getStylesheets().add(getClass().getResource("/resources/Kiweetool.css").toExternalForm());
			setPrimaryStage(primaryStage);
			getPrimaryStage().setTitle("Grape Bowl");
			//primaryStage.setAlwaysOnTop(true);
			getPrimaryStage().getIcons().add(new Image("/resources/images/kiwee_logo.png"));
			getPrimaryStage().resizableProperty().setValue(Boolean.FALSE);
			getPrimaryStage().setScene(pScene);
			getPrimaryStage().sizeToScene();
			getPrimaryStage().show();
			SupportMethods.giveExecutePermissionToYourScript(new File(TerminalOperations.getJFrogLocation()));
			File configFile = new File(folderCreation[0] + fc + _filenames[0]);
	        if(configFile.isFile() && configFile.length() < 150) {
	        	Optional<ButtonType> result = SupportMethods.alertMessage(
	        			"To run all your things on Grape Bowl, preferences values are required", 
	        			AlertType.INFORMATION, "Information", 
	        			"Grape Bowl Application preferences are empty", 
	        			true);
				if(result.get().equals(ButtonType.YES)){
					MainController.getInstance().callPropertySaverDialog();
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static Object getJFrogObect() {
		return JFrogObect;
	}


	public static Scene getpScene() {
		return pScene;
	}


	public static void setpScene(Scene pScene) {
		Kiwee.pScene = pScene;
	}


	public static Stage getPrimaryStage() {
        return pStage;
    }

    private void setPrimaryStage(Stage pStage) {
    	Kiwee.pStage = pStage;
    }
    
	public static void main(String[] args) {
		launch(args);
	}
	
	public static Object getBugsaverLoderObject() {
		return bugsaverObject;
	}
	
	public static Object getPreExecuteCommandLoderObject() {
		return preExecuteCommand;
	}
	
	public static Object getCustomExecuteCommandLoderObject() {
		return customExecuteCommand;
	}
	
	public static Object getImageComparisionLoderObject() {
		return imageComparision;
	}
	
	private void createConfigFileForKiweeApplication(File file, String getTheCanonicalPath, FileFilter[] fileFilter) throws IOException {
		if (file.getName().contains("config")) {
			if(new File(getTheCanonicalPath).listFiles(fileFilter[0]).length == 0) {
				new File(getTheCanonicalPath + _filenames[0]).createNewFile();
				new File(getTheCanonicalPath + _filenames[1]).createNewFile();
				new File(getTheCanonicalPath + _filenames[2]).createNewFile();
			}
		} else if (file.getName().contains("log")) {
			if(new File(getTheCanonicalPath).listFiles(fileFilter[1]).length == 0) {
				new File(getTheCanonicalPath + _filenames[3]).createNewFile();
			}
		}
	}
	
	private void createSingleConfigFile(String fileName) throws IOException {
		new File(fileName).createNewFile();
	}
	
	private void defautlKeyNamesForPropertiesFiles() {
		SupportMethods.writeAdbPathIntoPropertiesFile(getAllConfigDefaultkeys(), _filenames[0]);
	}
	
	private Map<String, String> getAllConfigDefaultkeys(){
		_configKeyValues = new HashMap<>();
		_configKeyValues.put("ADB_PATH", "");
		_configKeyValues.put("ANDROID_APP_PACKAGE_NAMES", "");
		_configKeyValues.put("iOS_LIBMOBILE_PATH", "");
		_configKeyValues.put("iOS_DEPLOY_PATH", "");
		_configKeyValues.put("JFROG_BASE_ARTIFACT_DIRECTORY", "");
		_configKeyValues.put("ANDORID_APK_URL", "");
		_configKeyValues.put("JFROG_ARTIFACTORY_USERNAME", "");
		_configKeyValues.put("JFROG_ARTIFACTORY_APIKEY", "");
		_configKeyValues.put("JFROG_ARTIFACTORY_ANDROID_PATH", "");
		_configKeyValues.put("JFROG_ARTIFACTORY_IOS_PATH", "");
		return _configKeyValues;
	}
}

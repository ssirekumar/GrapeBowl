package com.kiwee.controllers;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import com.kiwee.SupportMethods;
import com.kiwee.console.PlatformUtils;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class AdbCommandToListController implements Initializable{
	
	private static AdbCommandToListController adbCommandToListController;
	@FXML private TextArea txt_adbCommandValue, txt_statusArea;
	@FXML private TextField txt_addCommandName;
	@FXML private Button btn_cancleaddCommandWindow, btn_addCommandToFile;
	
	public static AdbCommandToListController getInstance() {
		return adbCommandToListController;
	}
	
	public AdbCommandToListController() {
		super();
		AdbCommandToListController.adbCommandToListController = this;
	}
	
	public TextField getAdbCommandNameTextFieldInstance() {
		return txt_addCommandName;
	}
	
    public TextArea getAdbCommandValueTextAreaInstance() {
    	return txt_adbCommandValue;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	
	@FXML public void addAdbCommandToFile(ActionEvent event) {
		String adbCommand ="";
		String[] allStringArray = new String[]{"youradbs.properties", System.getProperty("user.dir"), File.separator, "config"};
		String properiesFile = allStringArray[1] + allStringArray[2] + allStringArray[3] + allStringArray[2] + allStringArray[0];
		PlatformUtils.loadPropertyFile(properiesFile);
		if((!txt_addCommandName.getText().isEmpty()) && (!txt_adbCommandValue.getText().isEmpty())){
			adbCommand = txt_adbCommandValue.getText(); 
			if(adbCommand.startsWith("adb") && !adbCommand.contains("adb -s")) {
				SupportMethods.writeAdbPathIntoPropertiesFile(txt_addCommandName.getText(), txt_adbCommandValue.getText(), allStringArray[0]);
				ObservableList<String> allKeys = SupportMethods.getAllTheKeysFromPropertyFile(PlatformUtils.loadPropertyFile(properiesFile));
				if(!allKeys.isEmpty()) {
					PreDefinedCommandController.getInstance().getPreDefinedCommandListBoxInstance().setItems(allKeys);
					((Node)(event.getSource())).getScene().getWindow().hide();
				}else {
					SupportMethods.alertMessage(
							"youradbs.properties file need some data(Key & Value) to add into the ADB Command list",
							AlertType.WARNING, "Warning", "youradbs.properties file is empty");
				}
			}else if(adbCommand.contains("adb -s")){
				SupportMethods.alertMessage(
						"Remove the device number from given command after adb '-s #devicenumber' ",
						AlertType.WARNING, "Warning", "Remove device number");
			}else {
				SupportMethods.alertMessage(
						"Proper ADB command need to be entered",
						AlertType.WARNING, "Warning", "Need a proper adb command");
			}
		}else {
			SupportMethods.alertMessage(
					"Enter proper adb command with ADB Command Name and Command Value in fields",
					AlertType.WARNING, "Warning", "Empty key and values are not allowed");
		}
	}
	
    @FXML public void cancelAdbCommandWindow(ActionEvent event) {
    	((Node)(event.getSource())).getScene().getWindow().hide();
	}
}

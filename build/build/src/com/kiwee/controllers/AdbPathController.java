package com.kiwee.controllers;

import java.io.File;

import com.kiwee.SupportMethods;
import com.kiwee.console.PlatformUtils;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AdbPathController {
	
	@FXML	private Label lbl_currentAdbPath;
	@FXML	private TextField txt_adbPathLocation;
	@FXML	private CheckBox chk_overwritePath;
	@FXML   private Button btn_adbOkButton;
	
	
	@FXML public void cancelAdbPathWindow(ActionEvent event){
		((Node)(event.getSource())).getScene().getWindow().hide();
    }
	
		
	@FXML public void setAdbPathTextboxToDisable(){
		if(lbl_currentAdbPath.getText().isEmpty()){
    		txt_adbPathLocation.setDisable(true);
		}
	}
	
	@FXML public void getAdbPathLocationIntoLabel(){
		String fc = File.separator;
		String path = "";
		String properiesFile = System.getProperty("user.dir") + fc + "config.properties";
		path = PlatformUtils.getValueFromProperty(PlatformUtils.loadPropertyFile(properiesFile), "ADB_PATH");
		lbl_currentAdbPath.setText(path);
	}
	
	
	@FXML public void overWriteAdbPath(ActionEvent event) {
		if (event.getSource() instanceof CheckBox) {
			CheckBox chk = (CheckBox) event.getSource();
			if (chk.isSelected()) {
				txt_adbPathLocation.setDisable(false);
				btn_adbOkButton.setDisable(false);
				// SupportMethods.writeAdbPathIntoPropertiesFile("ADB_PATH",
				// txt_adbPathLocation.getText());
			} else if (!chk.isSelected()) {
				txt_adbPathLocation.setDisable(true);
				btn_adbOkButton.setDisable(true);
			}
		}
	}
	
	@FXML public void setAdbPathLocationIntoFile(ActionEvent event) {
		if(!(txt_adbPathLocation.getText().isEmpty()) || !(txt_adbPathLocation.getText().matches("\\s+?"))){
			SupportMethods.writeAdbPathIntoPropertiesFile("ADB_PATH", txt_adbPathLocation.getText(), "config.properties");
		}
		((Node)(event.getSource())).getScene().getWindow().hide();
	}
}

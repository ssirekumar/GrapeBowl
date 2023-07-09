package com.kiwee.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.kiwee.SupportMethods;
import com.kiwee.console.TerminalOperations;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;

public class CustomCommandController implements Initializable{
	private Process _proc = null;
	private static CustomCommandController customCommandController;
	@FXML	private ListView<String> lst_CustomAdbCommandDeviceList;
	@FXML private TextArea txt_ExecuteCustomTextArea, txt_statusArea;
	@FXML private Button btn_ExecuteCustom, btn_ExecuteCustomAddDeviceId, btn_clearAdbCommandFromTxtArea;
	
	
	public static CustomCommandController getInstance() {
		return customCommandController;
	}
	
	public CustomCommandController() {
		super();
		CustomCommandController.customCommandController = this;
	}
	
	public ListView<String> getCustomCommandListBoxInstance(){
		return lst_CustomAdbCommandDeviceList;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		lst_CustomAdbCommandDeviceList.setOrientation(Orientation.VERTICAL);
		//lst_CustomAdbCommandDeviceList.setBackground(Background.EMPTY);
		lst_CustomAdbCommandDeviceList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		lst_CustomAdbCommandDeviceList.setPlaceholder(new Label("Devices"));
	}
	
	@FXML 
    public void getdeviceNamesIntoListBox(ActionEvent event) {
    	MainController.getInstance().getdeviceNamesIntoListBox(event);
    }
	
	@FXML public void clearCommandTextArea() {
		txt_ExecuteCustomTextArea.clear();
	}
	
	@FXML
	public void executeUserCustomCommand(ActionEvent event) {
		txt_statusArea = MainController.getInstance().getStatusTextArea();
		String adbCommand = "";
		if(!txt_ExecuteCustomTextArea.getText().isEmpty()) {
			if(!lst_CustomAdbCommandDeviceList.getSelectionModel().isEmpty()) {
				adbCommand = txt_ExecuteCustomTextArea.getText(); 
				if(adbCommand.startsWith("adb") && !adbCommand.contains("adb -s")) {
					ObservableList<String> listOfDevices = lst_CustomAdbCommandDeviceList.getSelectionModel().getSelectedItems();
					for (String deviceName : listOfDevices) {
						adbCommand	= "adb -s "+ deviceName + " " + adbCommand.split("adb")[1];
						txt_ExecuteCustomTextArea.clear();
						txt_ExecuteCustomTextArea.setText(adbCommand);
						_proc = TerminalOperations.adbCommandsRunInCMD(adbCommand);
			    		ArrayList<String> _strList = TerminalOperations.getadbCommandsOutputCMD(_proc);
			    		txt_statusArea.appendText(SupportMethods.eventLogMessageformatter(_strList, "").toString());
					}
				}else if(SupportMethods.isCommandHaveConnectedDeviceName(txt_ExecuteCustomTextArea, lst_CustomAdbCommandDeviceList)){
						_proc = TerminalOperations.adbCommandsRunInCMD(adbCommand);
			    		ArrayList<String> _strList = TerminalOperations.getadbCommandsOutputCMD(_proc);
			    		txt_statusArea.appendText(SupportMethods.eventLogMessageformatter(_strList, "").toString());
				}else {
					SupportMethods.alertMessage(
							"Proper ADB command need to be entered",
							AlertType.WARNING, "Warning", "Need a proper adb command");
				}
			}else {
				SupportMethods.alertMessage(
						"Select any one of the android device to execute above command",
						AlertType.WARNING, "Warning", "Select any one of the device");
			}
		}else {
			SupportMethods.alertMessage(
					"Adb command is not entered, Please enter your ADB command and execute",
					AlertType.WARNING, "Warning", "ADB command is not found");
		}
	}
	
}

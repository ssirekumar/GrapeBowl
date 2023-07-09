package com.kiwee.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import com.kiwee.ExceptionHandling;
import com.kiwee.SupportMethods;
import com.kiwee.console.TerminalOperations;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class PreDefinedCommandController implements Initializable{
	private Process _proc = null;
	private static PreDefinedCommandController PreDefiCommandController;
	@FXML private ListView<String> lst_PreAdbCommandDeviceList, lst_PreAdbCommandsList;
	@FXML private TextArea txt_statusArea, txt_PreCommandExecutionTxtArea;
	@FXML private Button btn_PreExecuteCommandDelete;
	
	public static PreDefinedCommandController getInstance() {
		return PreDefiCommandController;
	}
	
	public PreDefinedCommandController() {
		super();
		PreDefinedCommandController.PreDefiCommandController = this;
	}
	
	public ListView<String> getPreDefinedCommandListBoxInstance(){
		return lst_PreAdbCommandsList;
	}
	
	public ListView<String> getPreDefinedDevicesListBoxInstance(){
		return lst_PreAdbCommandDeviceList;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		lst_PreAdbCommandDeviceList.setOrientation(Orientation.VERTICAL);
		lst_PreAdbCommandDeviceList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		lst_PreAdbCommandDeviceList.setPlaceholder(new Label("Devices"));
		
		lst_PreAdbCommandsList.setOrientation(Orientation.VERTICAL);
		lst_PreAdbCommandsList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		lst_PreAdbCommandsList.setPlaceholder(new Label("ADB Commands"));
		
	}
	
	@FXML 
    public void getdeviceNamesIntoListBox(ActionEvent event) {
    	MainController.getInstance().getdeviceNamesIntoListBox(event);
    }
	
	@FXML public void addCommandSceneLauncher(ActionEvent event) {
		txt_statusArea = MainController.getInstance().getStatusTextArea();
		try {
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/AddAdbCommandToListDialogScene.fxml"));
			Parent root = (Parent)loader.load(); 
			Scene scene = new Scene(root);
			primaryStage.setTitle("Add ADB Command");
			primaryStage.setAlwaysOnTop(false);
			primaryStage.getIcons().add(new Image("/resources/images/kiwee_logo.png"));
			primaryStage.resizableProperty().setValue(Boolean.FALSE);
			primaryStage.initStyle(StageStyle.UTILITY);
			primaryStage.initModality(Modality.APPLICATION_MODAL);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			Optional<ButtonType> result = SupportMethods.alertExceptionMessage();
			if(result.get().equals(ButtonType.YES)){
				txt_statusArea.setText(ExceptionHandling.addStackTraceToReport(e));
			}
		}
	}
	
	@FXML public void deleteAdbPropertyFromFile(ActionEvent event) {
		if(!lst_PreAdbCommandsList.getSelectionModel().isEmpty()) {
			SupportMethods.deleteKeyValueFromYourAdbsPropertiesFile(lst_PreAdbCommandsList.getSelectionModel().getSelectedItem());
			lst_PreAdbCommandsList.getItems().remove(lst_PreAdbCommandsList.getSelectionModel().getSelectedItem());
			lst_PreAdbCommandsList.getSelectionModel().clearSelection();
			txt_PreCommandExecutionTxtArea.clear();
		}else {
			SupportMethods.alertMessage(
					"Atleast one command name/key should be selected for deletion of item",
					AlertType.WARNING, "Warning", "Command name/key is not selected");
		}
	}
	
	@FXML public void placeComamndValueIntotext() {
		if(!lst_PreAdbCommandsList.getSelectionModel().isEmpty()) {
			lst_PreAdbCommandsList.setOnMouseClicked(new EventHandler<MouseEvent>(){
		          @Override
		          public void handle(MouseEvent arg0) {
		        	  String key = lst_PreAdbCommandsList.getSelectionModel().getSelectedItem();
		        	  txt_PreCommandExecutionTxtArea.setText(SupportMethods.getKeyValueFromYourAdbsPropertiesFile(key));
		          }
		      });
		}else {
			SupportMethods.alertMessage(
					"Atleast one command name/key should be selected to get ADB command",
					AlertType.WARNING, "Warning", "Command name/key is not selected");
		}
	}
	
	@FXML public void clearAdbCommandFromtext() {
		txt_PreCommandExecutionTxtArea.clear();
		lst_PreAdbCommandsList.getSelectionModel().clearSelection();
	}
	
	@FXML public void executePreDefinedCommand() {
		txt_statusArea = MainController.getInstance().getStatusTextArea();
		String adbCommand = "";
		if(!txt_PreCommandExecutionTxtArea.getText().isEmpty()) {
			if(!lst_PreAdbCommandDeviceList.getSelectionModel().isEmpty()) {
				adbCommand = txt_PreCommandExecutionTxtArea.getText(); 
				if(adbCommand.startsWith("adb") && !adbCommand.contains("adb -s")) {
					ObservableList<String> listOfDevices = lst_PreAdbCommandDeviceList.getSelectionModel().getSelectedItems();
					for (String deviceName : listOfDevices) {
						String adbCommandModi	= "adb -s "+ deviceName + " " + adbCommand.split("adb")[1];
						txt_PreCommandExecutionTxtArea.clear();
						txt_PreCommandExecutionTxtArea.setText(adbCommandModi);
						_proc = TerminalOperations.adbCommandsRunInCMD(adbCommandModi);
			    		ArrayList<String> _strList = TerminalOperations.getadbCommandsOutputCMD(_proc);
			    		txt_statusArea.appendText(SupportMethods.eventLogMessageformatter(_strList, "").toString());
					}
				}else if(SupportMethods.isCommandHaveConnectedDeviceName(txt_PreCommandExecutionTxtArea,lst_PreAdbCommandDeviceList)){
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
	
	@FXML public void editPreDefinedAdbCommand() {
		txt_statusArea = MainController.getInstance().getStatusTextArea();
		if(!lst_PreAdbCommandsList.getSelectionModel().isEmpty()){
			try {
				Stage primaryStage = new Stage();
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/AddAdbCommandToListDialogScene.fxml"));
				Parent root = (Parent)loader.load(); 
				Scene scene = new Scene(root);
				primaryStage.setTitle("Edit ADB Command");
				primaryStage.setAlwaysOnTop(false);
				primaryStage.getIcons().add(new Image("/resources/images/kiwee_logo.png"));
				primaryStage.resizableProperty().setValue(Boolean.FALSE);
				primaryStage.initStyle(StageStyle.UTILITY);
				primaryStage.initModality(Modality.APPLICATION_MODAL);
				primaryStage.setScene(scene);
				String key = lst_PreAdbCommandsList.getSelectionModel().getSelectedItem();
	        	String value = SupportMethods.getKeyValueFromYourAdbsPropertiesFile(key);
				AdbCommandToListController.getInstance().getAdbCommandNameTextFieldInstance().setText(key);
				AdbCommandToListController.getInstance().getAdbCommandValueTextAreaInstance().setText(value);
				primaryStage.show();
			} catch (Exception e) {
				Optional<ButtonType> result = SupportMethods.alertExceptionMessage();
				if(result.get().equals(ButtonType.YES)){
					txt_statusArea.setText(ExceptionHandling.addStackTraceToReport(e));
				}
			}
		}else {
			SupportMethods.alertMessage(
					"Atleast one command name/key should be selected to edit item",
					AlertType.WARNING, "Warning", "Command name/key is not selected");
		}
	}
	
	
}

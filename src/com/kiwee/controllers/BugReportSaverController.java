package com.kiwee.controllers;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

import com.kiwee.SupportMethods;
import com.kiwee.application.Kiwee;
import com.kiwee.console.TerminalOperations;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;

public class BugReportSaverController implements Initializable{
	
	private Process _proc = null;
	private static BugReportSaverController bugReportController;
    
    @FXML	private ListView<String> lst_BugReportDeviceList;
    @FXML	private TextArea txt_statusArea;
    @FXML   private TextField txt_BugReportFileName, txt_BugReportDirectoryPath;
    @FXML	private Button btn_BugReportSelectPath, btn_getDevicesInstall, btn_BugReportSave;
    @FXML   private AnchorPane anchorPane_BugReportSaver;
    @FXML   private ComboBox<String> combo_BugReportDeviceOsType;
    
	
	public static BugReportSaverController getInstance() {
		return bugReportController;
	}
	
	public BugReportSaverController() {
		super();
		BugReportSaverController.bugReportController = this;
	}
	
	public ListView<String> getBugReportListBoxInstance(){
		return lst_BugReportDeviceList;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		combo_BugReportDeviceOsType.setItems(FXCollections.observableArrayList(Arrays.asList("Android")));
		combo_BugReportDeviceOsType.getSelectionModel().selectFirst();
		
		//
		lst_BugReportDeviceList.setOrientation(Orientation.VERTICAL);
		lst_BugReportDeviceList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		lst_BugReportDeviceList.setPlaceholder(new Label("Devices"));
	}
	
    @FXML 
    public void getdeviceNamesIntoListBox(ActionEvent event) {
    	MainController.getInstance().getdeviceNamesIntoListBox(event);
    }
    
    @FXML 
    public void showBugReportControlsAsPerSelection(ActionEvent event) {
    	
    }
    
    @FXML void setPathToSaveBugreportFile(ActionEvent event) {
    	String filePath = "";
    	File file = null;
    	txt_statusArea = MainController.getInstance().getStatusTextArea();
    	final DirectoryChooser fileChooser = new DirectoryChooser();
    	fileChooser.setTitle("Bug Report Saver path directory");
    	fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));  
    	file = fileChooser.showDialog(Kiwee.getPrimaryStage());
    	if (file != null && file.isDirectory()) { 
    		  filePath = file.getAbsolutePath().toString();
    		  txt_BugReportDirectoryPath.setText(filePath);
    		  txt_BugReportDirectoryPath.setFocusTraversable(true);
        	  txt_statusArea.appendText(SupportMethods.eventLogMessageformatter("Selected Directory is " + filePath).toString());
        	  txt_statusArea.setScrollLeft(0);
    	}else {
    		txt_statusArea.appendText(SupportMethods.eventLogMessageformatter("Directory is not selected").toString());
    		txt_statusArea.setScrollLeft(0);
    	}
    }
    
    @FXML void saveBugreportFile(ActionEvent event) {
    	txt_statusArea = MainController.getInstance().getStatusTextArea();
    	if(!lst_BugReportDeviceList.getSelectionModel().isEmpty()) {
    		if (!txt_BugReportDirectoryPath.getText().isEmpty()) {
    			executeBugReport(getBugReportFileName());
			} else {
				SupportMethods.alertMessage(
						"Please select directory path for saving the Bugreport",
						AlertType.WARNING, "Warning", "Directory path is not provided");
			}
    	}else {
    		SupportMethods.alertMessage(
					"Please select atleast one device to save BugReport of the appliction",
					AlertType.WARNING, "Warning", "Select any of the device which present in the list");
    	}
    }
    
    private String getBugReportFileName() {
    	String bugReportFileName = "BugReport_";
    	if (!txt_BugReportFileName.getText().isEmpty()) {
    		bugReportFileName = bugReportFileName + txt_BugReportFileName.getText() + "_" 
    	        + lst_BugReportDeviceList.getSelectionModel().getSelectedItem() + "_"
    			+ SupportMethods.currentDate("yyyy-MMM-dd-HH-mm-ss") + ".zip"; 
		} else {
			bugReportFileName = bugReportFileName + lst_BugReportDeviceList.getSelectionModel().getSelectedItem() + "_"
		            + SupportMethods.currentDate("yyyy-MMM-dd-HH-mm-ss") + ".zip"; 
		}
    	return bugReportFileName;
    }
    
    private void executeBugReport(String bugReportFileName) {
    	ObservableList<String> _listOfDevices = FXCollections.observableArrayList(lst_BugReportDeviceList.getSelectionModel().getSelectedItems());
    	for (String deviceNumber : _listOfDevices) {
    		_proc = TerminalOperations.adbCommandsRunInCMD("adb -s " + deviceNumber
    				+ " bugreport " + bugReportFileName);
    		ArrayList<String> _strList = TerminalOperations.getadbCommandsOutputCMD(_proc);
    		txt_statusArea.appendText(SupportMethods.eventLogMessageformatter(_strList, "").toString());
		}
    	
    }
    
    
    
    

}

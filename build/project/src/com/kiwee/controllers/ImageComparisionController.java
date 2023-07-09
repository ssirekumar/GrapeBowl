package com.kiwee.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

import com.kiwee.SupportMethods;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;

public class ImageComparisionController implements Initializable{

	private static ImageComparisionController imageComparisionController;
	
	@FXML private ChoiceBox<String> com_DeviceOsType_ImageComparision;
	@FXML private ComboBox<String> comb_imageComparisionFirstDevice,comb_imageComparisionSecondDevice;
	
	
	public static ImageComparisionController getInstance() {
		return imageComparisionController;
	}
	
	public ImageComparisionController() {
		super();
		ImageComparisionController.imageComparisionController = this;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//Initializer for device Os selection things.
		com_DeviceOsType_ImageComparision.setItems(FXCollections.observableArrayList(Arrays.asList("Android", "iOS")));
		com_DeviceOsType_ImageComparision.getSelectionModel().selectFirst();
		com_DeviceOsType_ImageComparision.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
		
		//Initializer for device selection things.
		comb_imageComparisionFirstDevice.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
		comb_imageComparisionSecondDevice.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
		
		
		
	}
	
	
	@FXML
	public void getAllDevicesAsPerDeviceOSSelection(ActionEvent event) {
		ArrayList<String> _devices = SupportMethods.getConnectedDevices();
		ArrayList<String> _iosDevices = SupportMethods.getConnectediOSDevices();
		if (!_devices.isEmpty() || !_iosDevices.isEmpty()) {
			if(com_DeviceOsType_ImageComparision.getSelectionModel().getSelectedItem().equalsIgnoreCase("android")) {
				comb_imageComparisionFirstDevice.setItems(FXCollections.observableArrayList(_devices));
				comb_imageComparisionSecondDevice.setItems(FXCollections.observableArrayList(_devices));
			}else if(com_DeviceOsType_ImageComparision.getSelectionModel().getSelectedItem().equalsIgnoreCase("ios")) {
				comb_imageComparisionFirstDevice.setItems(FXCollections.observableArrayList(_iosDevices));
				comb_imageComparisionSecondDevice.setItems(FXCollections.observableArrayList(_iosDevices));
			}
		} else {
			SupportMethods.alertMessage(
					"It could be either Android/iOS Devices are not connected\n" + "\t\t(Or)\t\t",
					AlertType.INFORMATION, "Information", "Android/iOS devices are not found");
			MainController.getInstance().getStatusTextArea().setText("[Fail]: Devices are not found");
		}
	}
	
	
	@FXML
	public void getSelectedDeviceScreen() {
		if(com_DeviceOsType_ImageComparision.getSelectionModel().getSelectedItem().equalsIgnoreCase("android")) {
			
		}else if(com_DeviceOsType_ImageComparision.getSelectionModel().getSelectedItem().equalsIgnoreCase("ios")) {
			
		}
	}

}

package com.kiwee.controllers;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Map;
import java.util.Optional;

import com.kiwee.console.PlatformUtils;
import com.kiwee.console.TerminalOperations;
import com.kiwee.modules.JFrogAccess;
import com.kiwee.DeviceInfoObject;
import com.kiwee.ExceptionHandling;
import com.kiwee.KiweePreferences;
import com.kiwee.PropertiesInfoObject;
import com.kiwee.SupportMethods;
import com.kiwee.application.Kiwee;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainController extends InitializerController {
	
	private Process _proc = null;
	private ArrayList<String> _devicesSerialNums = null;
    private static MainController mainController;
    private String userName, apiKey, artifactoryBaseURL, artifactConfigServerId;
	
	@FXML	private ListView<String> lst_InstallDevice_list, lst_InstallDevice_Sheetlist,
	                                 lst_DeviceInfoDevice_list, lst_UnInstallDevice_list, lst_InstallDeviceJFrog;
	@FXML	private ListView<String> lst_ClearLogCatDevice_list, lst_ClearAppDataDevice_list, lst_package_names, 
	                lst_pckNamesClear;
	@FXML	private TextArea txt_statusArea;
	@FXML   private Pane install_RadioSelectionPane,commandExe_RadioSelectionPane;
	@FXML	private MenuItem menu_setadbpath;
	@FXML	private Button cancelButton,btn_AppDataClear,btn_AppDataClearDeviceList,btn_UninstallClearDeviceList;
	@FXML	private RadioButton radio_SystemPathInstall, radio_GoogleSheetInstall,radio_UninstallSelectionList, 
	                radio_clearAppSelectionList, radio_UninstallSelectionCombo, radio_clearAppSelectionCombo,
	                deviceinfo_all, deviceinfo_attri, radio_SystemPathInstall_s, radio_GoogleSheetInstall_s,
					radio_JfrogInstall, radio_CustomCommandExecute, radio_PreDefinedCommands;
	@FXML   private TabPane mainTabPane;
	@FXML	private ComboBox<String> combotxt_addApkPath, combotxt_appUninstall, 
	                combo_clearAppDataSelection,com_deviceinfo, combobox_deviceOsType, combobox_UninstalldeviceOsType;
	@FXML   private VBox Vbox_SystemPath, Vbox_GoogleSheets;
	@FXML   private HBox Hbox_installOptions, Hbox_UninstallOptions;
	@FXML 	private TableView<DeviceInfoObject> tbl_DeviceInfo;
	@FXML   private CheckBox chk_r, chk_d, chk_l, chk_f, chk_s, chk_g, chk_k;
	@FXML   private ToggleGroup installRadioGroup,unInstallSelectioGroup, clearAppSelectioGroup, deviceInfoRadioGroup, executeCommandGroup;
	@FXML   private Tab installTab,installSheetTab,uninstallTab,clearlogTab, clearAppDataTab, 
	                    deviceInfoTab, appInfoTab, bugReportTab, commandExecutorTab,imageComparisionTab;
	@FXML private TableView<PropertiesInfoObject> table_PropertykeyValue;
	@FXML private TableColumn<PropertiesInfoObject, String> table_PropertySaverKeyColumn;
	@FXML private TableColumn<PropertiesInfoObject, TextField> table_PropertySaverValueColumn;
	@FXML private AnchorPane anchorPane_BugReportSaver, anchorPane_ImageComparision;
	
	
	public MainController() {
		MainController.mainController = this;
	} 
	
	public static MainController getInstance() {
		return mainController;
	}
	
	
	public ComboBox<String> getDeviceOSTypeObject(){
		return combobox_deviceOsType;
	}
	
	public TextArea getStatusTextArea() {
    	return txt_statusArea;
    }
	
	@FXML
	public void getdeviceNamesIntoListBox(ActionEvent event) {
		try {
			Button nodeToFind = ((Button) event.getSource());
			switch (nodeToFind.getId()) {
			case "btn_getDevicesInstall":
				if (combotxt_addApkPath.getValue() != null || !combotxt_addApkPath.isEditable()) {
					combotxt_addApkPath.getStyleClass().remove("field-error-border");
					lst_InstallDevice_list.setItems(this.getDeviceSerialNumberAsPerType());
				} else {
					combotxt_addApkPath.getStyleClass().add("field-error-border");
				}
				break;
			case "btn_getDevicesUnInstall":
				lst_UnInstallDevice_list.setItems(this.getDeviceSerialNumberAsPerType());
				break;
			case "btn_getDevicesClearLogcat":
				lst_ClearLogCatDevice_list.setItems(this.getDeviceSerialNumberOfAndroid());
				break;
			case "btn_getDevicesClearAppData":
				lst_ClearAppDataDevice_list.setItems(this.getDeviceSerialNumberOfAndroid());
				break;
			case "btn_getDevicesDeviceInfo":
				lst_DeviceInfoDevice_list.setItems(this.getDeviceSerialNumberOfAndroid());
				break;
			case "appInfoTab":
				// lst_InstallDevice_Sheetlist
				/*
				 * lst_InstallDevice_list.setItems(items); txt_statusArea.setText(timestamp +
				 * " [Success]: Devices are found");
				 */
				break;
			case "btn_getDevicesSheetsInstall":
				lst_InstallDevice_Sheetlist.setItems(this.getDeviceSerialNumberOfAndroid());
				break;
			case "btn_getDevicesInstallJFrog":
				JFrogSceneController.getInstance().getJFrogListBoxInstance()
						.setItems(this.getDeviceSerialNumberAsPerType());
				break;
			case "btn_getBugReportDevicesList":
				BugReportSaverController.getInstance().getBugReportListBoxInstance()
						.setItems(this.getDeviceSerialNumberOfAndroid());
				break;
			case "btn_getDevicesPreDevicesList":
				PreDefinedCommandController.getInstance().getPreDefinedDevicesListBoxInstance()
						.setItems(this.getDeviceSerialNumberOfAndroid());
				break;
			case "btn_getCustomCommandDevicesList":
				CustomCommandController.getInstance().getCustomCommandListBoxInstance()
						.setItems(this.getDeviceSerialNumberOfAndroid());
				break;
			default:
				break;
			}
		} catch (Exception e) {
			Optional<ButtonType> result = SupportMethods.alertExceptionMessage();
			if (result.get().equals(ButtonType.YES)) {
				txt_statusArea.setText(ExceptionHandling.addStackTraceToReport(e));
			}
		}
	}
	
    //Event for menu button handler
	@FXML public void tabSelectionFromToolBar(ActionEvent event){
		try {
			String toolbarButton = ((Button)event.getSource()).getId().toLowerCase();
			switch (toolbarButton) {
			case "installapp":
				mainTabPane.getSelectionModel().select(installTab);
				break;
			case "uninstallapp":
				mainTabPane.getSelectionModel().select(uninstallTab);
				break;
			case "clearlogcat":
				mainTabPane.getSelectionModel().select(clearlogTab);
				break;
			case "clearcache":
				mainTabPane.getSelectionModel().select(clearAppDataTab);
				break;
			case "deviceinfo":
				mainTabPane.getSelectionModel().select(deviceInfoTab);
				break;
			case "appinfo":
				mainTabPane.getSelectionModel().select(appInfoTab);
				break;
			case "bugreport":
				mainTabPane.getSelectionModel().select(bugReportTab);
				VBox vBoxloder = (VBox) Kiwee.getBugsaverLoderObject();
				anchorPane_BugReportSaver.getChildren().setAll(vBoxloder);
				break;
			case "executecommands":
				mainTabPane.getSelectionModel().select(commandExecutorTab);
				ObservableList<String> allKeys = SupportMethods.getAllKeysFromYourAdbsPropertiesFile();
				if(!allKeys.isEmpty()) {
					PreDefinedCommandController.getInstance().getPreDefinedCommandListBoxInstance().setItems(allKeys);
				}else {
					SupportMethods.alertMessage(
							"youradbs.properties file need some data(Key & Value) to add into the ADB Command list",
							AlertType.WARNING, "Warning", "youradbs.properties file is empty");
				}
				PreDefinedCommandController.getInstance().getPreDefinedCommandListBoxInstance().setItems(allKeys);
				
				break;
			case "imagecomparision":
				mainTabPane.getSelectionModel().select(imageComparisionTab);
				AnchorPane aPaneImageloder = (AnchorPane) Kiwee.getImageComparisionLoderObject();
				anchorPane_ImageComparision.getChildren().setAll(aPaneImageloder);
				break;
			default:
				System.out.println("Default");
				break;
			}
		} catch (Exception e) {
			Optional<ButtonType> result = SupportMethods.alertExceptionMessage();
			if(result.get().equals(ButtonType.YES)){
				txt_statusArea.setText(ExceptionHandling.addStackTraceToReport(e));
			}
		}
	}
	
	//Un selcting the android install options when select an ios
	@FXML
	public void unselectAndroidInstaltionOptions(ActionEvent event) {
		if(combobox_deviceOsType.getSelectionModel().getSelectedItem().equalsIgnoreCase("ios")) {
			Hbox_installOptions.setVisible(false);
			JFrogSceneController.getInstance().getJFrogInstallOptionObject().setVisible(false);
		}else if(combobox_deviceOsType.getSelectionModel().getSelectedItem().equalsIgnoreCase("android")) {
			Hbox_installOptions.setVisible(true);
			JFrogSceneController.getInstance().getJFrogInstallOptionObject().setVisible(true);
		}
	}
	
	// Un selcting the android uninstall options when select an ios
	@FXML
	public void unselectAndroidUnInstaltionOptions(ActionEvent event) {
		if (combobox_UninstalldeviceOsType.getSelectionModel().getSelectedItem().equalsIgnoreCase("ios")) {
			Hbox_UninstallOptions.setVisible(false);
		} else if (combobox_UninstalldeviceOsType.getSelectionModel().getSelectedItem().equalsIgnoreCase("android")) {
			Hbox_UninstallOptions.setVisible(true);
		}
	}
	
	//Event will Clear the log text
	@FXML public void clearLogActivity(ActionEvent event){
		try {
			txt_statusArea.clear();
		} catch (Exception e) {
			txt_statusArea.setText(ExceptionHandling.addStackTraceToReport(e));
		}
	}
	
	//Event will Copy the log to clipboard
	@FXML public void selectAllLog(ActionEvent event){
		try {
			 txt_statusArea.selectAll();
			 final Clipboard clipboard = Clipboard.getSystemClipboard();
			 final ClipboardContent content = new ClipboardContent();
		     content.putString(txt_statusArea.getText());
		     clipboard.setContent(content);
		} catch (Exception e) {
			txt_statusArea.setText(ExceptionHandling.addStackTraceToReport(e));
		}
	}
	
	@FXML public void communicateAlertScene(){
		Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Contact");
        alert.setHeaderText("For more details...!! \nUse mail subject line as [Grape Bowl <version number>]");
        alert.setContentText("email: ssirekumar@gmail.com \n linkedIn: https://in.linkedin.com/in/ssirekumar");
        alert.initStyle(StageStyle.UTILITY);
        alert.showAndWait();
	}
	
	@FXML public void toolVersionAlertScene(){
		Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("About Grape Bowl");
        Image image = new Image("/resources/images/kiwee_logo.png");
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(64);
        imageView.setFitHeight(64);
        alert.setGraphic(imageView);
        alert.setHeaderText("Grape Bowl Tool for Mobile QA and Developers.");
        alert.setContentText("Version: " + SupportMethods.getVersionOfKiwee() + "\n\n" +
        		"BSD 3-Clause License\n" +
				
				"Software License Agreement (BSD License)\n" +
				"Copyright (c) " + Calendar.getInstance().get(Calendar.YEAR)+"," + " Siri Kumar All rights reserved."
        		);
        alert.initStyle(StageStyle.UTILITY);
        alert.showAndWait();
	}
	
	@FXML public void showAdbPathLocationWindow(ActionEvent event){
		try {
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/AdbPathScene.fxml"));
			//loader.setLocation(getClass().getResource("/resources/AdbPathScene.fxml"));
			Parent root = (Parent)loader.load(); 
			AdbPathController secondC = (AdbPathController)loader.getController();
			Scene scene = new Scene(root);
			primaryStage.setTitle("Set ADB path location");
			primaryStage.setAlwaysOnTop(false);
			primaryStage.getIcons().add(new Image("/resources/images/logo.png"));
			primaryStage.resizableProperty().setValue(Boolean.FALSE);
			primaryStage.initStyle(StageStyle.UTILITY);
			primaryStage.initModality(Modality.APPLICATION_MODAL);
			primaryStage.setScene(scene);
			primaryStage.show();
			secondC.setAdbPathTextboxToDisable();
			secondC.getAdbPathLocationIntoLabel();
			/*primaryStage.addEventHandler(WindowEvent.WINDOW_SHOWING, new  EventHandler<WindowEvent>() {
		        @Override
		        public void handle(WindowEvent window) {
		        	if(secondC.lbl_currentAdbPathT.getText().isEmpty()){
		        		secondC.txt_adbPathLocationT.setDisable(true);
					}
		        }
		    });*/
		} catch (Exception e) {
			Optional<ButtonType> result = SupportMethods.alertExceptionMessage();
			if(result.get().equals(ButtonType.YES)){
				txt_statusArea.setText(ExceptionHandling.addStackTraceToReport(e));
			}
		}
	}
	
	@FXML public void installSelection(ActionEvent event){
		if(installRadioGroup.getSelectedToggle().equals(radio_SystemPathInstall)){
			install_RadioSelectionPane.getChildren().clear();
			install_RadioSelectionPane.getChildren().setAll(Vbox_SystemPath);
		}else if(installRadioGroup.getSelectedToggle().equals(radio_JfrogInstall)){
			install_RadioSelectionPane.getChildren().clear();
			VBox vBoxloder = (VBox) Kiwee.getJFrogObect(); 
			install_RadioSelectionPane.getChildren().setAll(vBoxloder);
			apiKey = SupportMethods.getKeyValueFromConfigFile("JFROG_ARTIFACTORY_APIKEY");
			userName = SupportMethods.getKeyValueFromConfigFile("JFROG_ARTIFACTORY_USERNAME");
			artifactoryBaseURL = SupportMethods.getKeyValueFromConfigFile("JFROG_BASE_ARTIFACT_DIRECTORY");
			if(!userName.isEmpty() && !apiKey.isEmpty() && !artifactoryBaseURL.isEmpty()) {
				try {
					JFrogAccess jfa = new JFrogAccess(userName, apiKey, new URL(artifactoryBaseURL), "tr-server-one");
					if(jfa.accessJFrogServer()) {
						JFrogSceneController.getInstance().getJFrogCconnectButton().setGraphic(new ImageView("/resources/images/JFrogGreenStatus.png"));
					}else {
						JFrogSceneController.getInstance().getJFrogCconnectButton().setGraphic(new ImageView("/resources/images/JFrogRedStatus.png"));
					}
				} catch (MalformedURLException e) {
					SupportMethods.alertMessage("Either no legal protocol could be found in a specification",
							AlertType.WARNING, "Information", "Malformed JFrog URL");
				}
			}else {
				SupportMethods.alertMessage("Either JFrog base URL or User name or API key is not added into the Kiwee preferences",
						AlertType.INFORMATION, "Information", "JFrog base URL, User name, API key may be empty");
			}
		}
	}
	
	@FXML public void unInstallSelection(ActionEvent event){
		if(unInstallSelectioGroup.getSelectedToggle().equals(radio_UninstallSelectionCombo)){
			lst_package_names.setDisable(true);
			combotxt_appUninstall.setDisable(false);
		}else if(unInstallSelectioGroup.getSelectedToggle().equals(radio_UninstallSelectionList)){
			combotxt_appUninstall.setDisable(true);
			lst_package_names.setDisable(false);
		}
	}
	
	@FXML public void getAppPackageDevices(ActionEvent event){
		try {
			ArrayList<String> _devices = SupportMethods.getConnectedDevices();
			ArrayList<String> _iosDevices = SupportMethods.getConnectediOSDevices();
			boolean isAppPackageNotSelected = true;
			if (!_devices.isEmpty() || !_iosDevices.isEmpty()) {
				if (((Button) event.getSource()).equals(btn_AppDataClear)) {
					if((!clearAppSelectioGroup.getToggles().get(0).isSelected()) && 
							(!clearAppSelectioGroup.getToggles().get(1).isSelected())) {
						SupportMethods.alertMessage("Please select the any one of selection criteria to clear Data & Cache of application",
								AlertType.WARNING, "Warning", "Required search selection criteria");
						return;
					}
					if (clearAppSelectioGroup.getSelectedToggle().equals(radio_clearAppSelectionCombo)) {
						if (combo_clearAppDataSelection.getSelectionModel().getSelectedIndex() != -1) {
							String packagename = combo_clearAppDataSelection.getSelectionModel().getSelectedItem()
									.trim();
							if(!packagename.isEmpty()) {
								ObservableList<String> devicesList =  SupportMethods.getAndroidApplicationInstalledDevice(FXCollections.observableArrayList(packagename), false);
								if(!devicesList.isEmpty()) {
									lst_ClearAppDataDevice_list.setItems(devicesList);
								}else {
									isAppPackageNotSelected = false;
								}
							}else {
								SupportMethods.alertMessage("Please select the package name to clear Data & Cache of the application",
										AlertType.WARNING, "Warning", "Seems like package name is not entered");
							}
						} 
					} else if (clearAppSelectioGroup.getSelectedToggle().equals(radio_clearAppSelectionList)) {
						if (lst_pckNamesClear.getSelectionModel().getSelectedIndex() != -1) {
							ObservableList<String> packagenames = lst_pckNamesClear.getSelectionModel().getSelectedItems();
							if(!packagenames.isEmpty()) {
								ObservableList<String> deviceList = SupportMethods.getAndroidApplicationInstalledDevice(FXCollections.observableArrayList(packagenames), true);
								if(!deviceList.isEmpty()) {
									lst_ClearAppDataDevice_list.setItems(deviceList);
								}else {
									isAppPackageNotSelected = false;
								}
							}else {
								SupportMethods.alertMessage("Please select the package name to clear Data & Cache of the application",
										AlertType.WARNING, "Warning", "Seems like package name is not entered");
							}
						}
					}
					if (!isAppPackageNotSelected) {
						SupportMethods.alertMessage("Application package is not found on connected devices",
								AlertType.INFORMATION, "Information", "Application package not found");
					}
				} else {   // Un-installing the application
					if((!unInstallSelectioGroup.getToggles().get(0).isSelected()) && 
							(!unInstallSelectioGroup.getToggles().get(1).isSelected())) {
						SupportMethods.alertMessage("Please select the any one of selection criteria for uninstalling the application",
								AlertType.WARNING, "Warning", "Required search selection criteria");
						return;
					}
					if(combobox_UninstalldeviceOsType.getSelectionModel().getSelectedItem().equalsIgnoreCase("android")) {
						if (unInstallSelectioGroup.getSelectedToggle().equals(radio_UninstallSelectionCombo)) {
							if (combotxt_appUninstall.getSelectionModel().getSelectedIndex() != -1) {
								String packagename = combotxt_appUninstall.getSelectionModel().getSelectedItem().trim();
								if(!packagename.isEmpty()) {
									ObservableList<String> devicesList =  SupportMethods.getAndroidApplicationInstalledDevice(FXCollections.observableArrayList(packagename), false);
									if(!devicesList.isEmpty()) {
										lst_UnInstallDevice_list.setItems(devicesList);
									}else {
										isAppPackageNotSelected = false;
									}
								}else {
									SupportMethods.alertMessage("Please select the package name to uninstall the appliction",
											AlertType.WARNING, "Warning", "Seems like package name is not entered");
								}
							}
						} else if (unInstallSelectioGroup.getSelectedToggle().equals(radio_UninstallSelectionList)) {
							if (lst_package_names.getSelectionModel().getSelectedIndex() != -1) {
								ObservableList<String> packagenames = lst_package_names.getSelectionModel().getSelectedItems();
								ObservableList<String> deviceList = SupportMethods.getAndroidApplicationInstalledDevice(FXCollections.observableArrayList(packagenames), true);
								if(deviceList.isEmpty()) {
									isAppPackageNotSelected = false;
								}else {
									lst_UnInstallDevice_list.setItems(deviceList);
								}
							} else {
								SupportMethods.alertMessage("Please select the package name to uninstall the appliction",
										AlertType.WARNING, "Warning", "Select package name");
								isAppPackageNotSelected = false;
							}
						}
						if (!isAppPackageNotSelected) {
							SupportMethods.alertMessage("Application package is not found on connected devices",
									AlertType.INFORMATION, "Information", "Application package is not found");
							lst_UnInstallDevice_list.getItems().clear();
						}
					}else if(combobox_UninstalldeviceOsType.getSelectionModel().getSelectedItem().equalsIgnoreCase("ios")) {
						if (unInstallSelectioGroup.getSelectedToggle().equals(radio_UninstallSelectionCombo)) {
							if (combotxt_appUninstall.getSelectionModel().getSelectedIndex() != -1) {
								String packagename = combotxt_appUninstall.getSelectionModel().getSelectedItem().trim();
								if(!packagename.isEmpty()) {
									ObservableList<String> devicesList =  SupportMethods.getiOSApplicationInstalledDevice(FXCollections.observableArrayList(packagename), false);
									if(!devicesList.isEmpty()) {
										lst_UnInstallDevice_list.setItems(devicesList);
									}else {
										isAppPackageNotSelected = false;
									}
								}else {
									SupportMethods.alertMessage("Please select the bundle identifier to uninstall the appliction",
											AlertType.WARNING, "Warning", "Seems like bundle identifier is not entered");
								}
							}
						}else if (unInstallSelectioGroup.getSelectedToggle().equals(radio_UninstallSelectionList)) {
							if (lst_package_names.getSelectionModel().getSelectedIndex() != -1) {
								ObservableList<String> packagenames = lst_package_names.getSelectionModel().getSelectedItems();
								ObservableList<String> deviceList = SupportMethods.getiOSApplicationInstalledDevice(FXCollections.observableArrayList(packagenames), true);
								if(deviceList.isEmpty()) {
									isAppPackageNotSelected = false;
								}else {
									lst_UnInstallDevice_list.setItems(deviceList);
								}
							} else {
								SupportMethods.alertMessage("Please select the bundle identifierto uninstall the appliction",
										AlertType.WARNING, "Warning", "Select bundle identifier");
								isAppPackageNotSelected = false;
							}
						}
						if (!isAppPackageNotSelected) {
							SupportMethods.alertMessage("Application package is not found on connected devices",
									AlertType.INFORMATION, "Information", "Application package is not found");
							lst_UnInstallDevice_list.getItems().clear();
						}
					}
				} // Uninstalling the application
			} else {
				SupportMethods.alertMessage(
						"It could be either Android/iOS Devices are not connected\n" + "\t\t(Or)\t\t",
						AlertType.INFORMATION, "Information", "Android/iOS devices are not found");
				txt_statusArea.setText("[Fail]: Devices are not found");
			}
		} catch (Exception e) {
			Optional<ButtonType> result = SupportMethods.alertExceptionMessage();
			if (result.get().equals(ButtonType.YES)) {
				txt_statusArea.setText(ExceptionHandling.addStackTraceToReport(e));
			}
		}
		// combotxt_appUninstall.getSelectionModel().clearSelection();
	}
	
	@FXML public void clearDeviceList(ActionEvent event){
		if(((Button)event.getSource()).equals(btn_AppDataClearDeviceList)){
			lst_ClearAppDataDevice_list.getItems().clear();
		}else if(((Button)event.getSource()).equals(btn_UninstallClearDeviceList)){
			lst_UnInstallDevice_list.getItems().clear();
		}
	}
	
	// Event will Install the App from system path
	@FXML
	public void installAppFromSystemPath(ActionEvent event) {
		ArrayList<String> _strList = null;
		try {
			if (!lst_InstallDevice_list.getItems().isEmpty()) {
				if (!lst_InstallDevice_list.getSelectionModel().isEmpty()) {
					ObservableList<String> selectedItems = lst_InstallDevice_list.getSelectionModel()
							.getSelectedItems();
					if (combobox_deviceOsType.getSelectionModel().getSelectedItem().equalsIgnoreCase("android")) {
						File _adbFile = new File(combotxt_addApkPath.getValue());
						for (String _deviceItem : selectedItems) {
							if (_adbFile.isFile()) {
								String optionText = "";
								if (chk_r.isSelected()) {
									optionText = optionText + " " + chk_r.getText();
								}
								if (chk_d.isSelected()) {
									optionText = optionText + " " + chk_d.getText();
								}
								if (chk_f.isSelected()) {
									optionText = optionText + " " + chk_f.getText();
								}
								if (chk_l.isSelected()) {
									optionText = optionText + " " + chk_l.getText();
								}
								if (chk_s.isSelected()) {
									optionText = optionText + " " + chk_s.getText();
								}
								if (chk_g.isSelected()) {
									optionText = optionText + " " + chk_g.getText();
								}
								optionText = optionText + " ";
								String instalationFile = "\"" + _adbFile.getCanonicalPath() + "\"";
								if (!chk_l.isSelected() && !chk_f.isSelected() && !chk_d.isSelected()
										&& !chk_r.isSelected() && !chk_s.isSelected() && !chk_g.isSelected()) {
									txt_statusArea.appendText(SupportMethods
											.eventLogMessageformatter(
													"adb -s " + _deviceItem + " install -r " + instalationFile)
											.toString());
									_proc = TerminalOperations.adbCommandsRunInCMD(
											"adb -s " + _deviceItem + " install -r " + instalationFile);
								} else {
									txt_statusArea.appendText(SupportMethods
											.eventLogMessageformatter(
													"adb -s " + _deviceItem + " install" + optionText + instalationFile)
											.toString());
									_proc = TerminalOperations.adbCommandsRunInCMD(
											"adb -s " + _deviceItem + " install" + optionText + instalationFile);
								}
								_strList = TerminalOperations.getadbCommandsOutputCMD(_proc);
								txt_statusArea
										.appendText(SupportMethods.eventLogMessageformatter(_strList, "").toString());
							} else {
								SupportMethods.alertMessage("Select application file path", AlertType.WARNING,
										"Warning", "App path is not found");
							}
						}
					} else if (combobox_deviceOsType.getSelectionModel().getSelectedItem().equalsIgnoreCase("ios")) {
						File _adbFile = new File(combotxt_addApkPath.getValue());
						for (String _deviceItem : selectedItems) {
							if (_adbFile.isFile()) {
								String instalationFile = "\"" + _adbFile.getCanonicalPath() + "\"";
								String iosInstallCommand = "ios-deploy --justlaunch --id " + _deviceItem + " --bundle " + instalationFile;
								txt_statusArea.appendText(
										SupportMethods.eventLogMessageformatter(iosInstallCommand).toString());
								_proc = TerminalOperations.iOSDeployeeCommandsRun(iosInstallCommand);
								_strList = TerminalOperations.getadbCommandsOutputCMD(_proc);
								txt_statusArea
										.appendText(SupportMethods.eventLogMessageformatter(_strList, "").toString());
							} else {
								SupportMethods.alertMessage("Select application file path", AlertType.WARNING,
										"Warning", "App path is not found");
							}
						}
					}
				} else {
					SupportMethods.alertMessage("Select any one of the device to install the appliction",
							AlertType.WARNING, "Warning", "Devices are not selected for installing app");
				}
			} else {
				SupportMethods.alertMessage(
						"Device list is empty, it could be device is not connected to the your machine",
						AlertType.WARNING, "Warning", "Devices are not found");
			}
		} catch (NullPointerException e) {
			SupportMethods.alertMessage("Select application file path", AlertType.WARNING, "Warning",
					"App path is not found");
		} catch (Exception e) {
			Optional<ButtonType> result = SupportMethods.alertExceptionMessage();
			if (result.get().equals(ButtonType.YES)) {
				txt_statusArea.setText(ExceptionHandling.addStackTraceToReport(e));
			}
		}
	}
    
	//need to write the code for After Un instalation message
	@FXML public void unInstallAppFromSelction(ActionEvent event){
		String packagename = "";
		String optionText = "";
		ObservableList<String> selectedItems = null;
		ArrayList<String> _strList = null;
    	try {
    		if (!lst_UnInstallDevice_list.getItems().isEmpty()) {
    			if (!lst_UnInstallDevice_list.getSelectionModel().isEmpty()) {
    				if(combobox_UninstalldeviceOsType.getSelectionModel().getSelectedItem().equalsIgnoreCase("android")) {
    					if(chk_k.isSelected()){
    						optionText = chk_k.getText() + " "; 
    					}
    					selectedItems = lst_UnInstallDevice_list.getSelectionModel()
    							.getSelectedItems();
    					if (!combotxt_appUninstall.isDisabled()) {
    						packagename = combotxt_appUninstall.getSelectionModel().getSelectedItem().trim();
    					} else if(!lst_package_names.isDisabled()){
    						packagename = lst_package_names.getSelectionModel().getSelectedItem().trim();
    					}
    					for (String _deviceItem : selectedItems) {
    						if(!chk_k.isSelected()){
    							_proc = TerminalOperations.adbCommandsRunInCMD("adb -s " + _deviceItem + " uninstall " + packagename);
    						}else{
    							_proc = TerminalOperations.adbCommandsRunInCMD("adb -s " + _deviceItem + " shell pm uninstall " + optionText + packagename);
    						}
    						_strList = TerminalOperations.getadbCommandsOutputCMD(_proc);
    						txt_statusArea.appendText(SupportMethods.eventLogMessageformatter(_strList,"Uninstaltion ").toString());
    					}
    				}else if(combobox_UninstalldeviceOsType.getSelectionModel().getSelectedItem().equalsIgnoreCase("ios")) {
    					selectedItems = lst_UnInstallDevice_list.getSelectionModel()
    							.getSelectedItems();
    					if (!combotxt_appUninstall.isDisabled()) {
    						packagename = combotxt_appUninstall.getSelectionModel().getSelectedItem().trim();
    					} else if(!lst_package_names.isDisabled()){
    						packagename = lst_package_names.getSelectionModel().getSelectedItem().trim();
    					}
    					for (String _deviceItem : selectedItems) {
    						String iosUnInstallCommand = "ios-deploy --id "+_deviceItem +" --uninstall_only --bundle_id " + packagename;
    						txt_statusArea.appendText(
									SupportMethods.eventLogMessageformatter(iosUnInstallCommand).toString());
							_proc = TerminalOperations.iOSDeployeeCommandsRun(iosUnInstallCommand);
							_strList = TerminalOperations.getConsoleCommandsOutput(_proc);
							txt_statusArea
									.appendText(SupportMethods.eventLogMessageformatter(_strList, "Uninstaltion ").toString());
    					}
    				}
    			}else{
    				SupportMethods.alertMessage("Please select the device to uninstall the app", AlertType.WARNING, "Warning",
							"Select Device");
    			}
    		}else{
    			SupportMethods.alertMessage("Devices are not found with respect to the app package for uninstall",
						AlertType.WARNING, "Warning", "Devices are not found");
    		}
		} catch (Exception e) {
			Optional<ButtonType> result = SupportMethods.alertExceptionMessage();
			if(result.get().equals(ButtonType.YES)){
				txt_statusArea.setText(ExceptionHandling.addStackTraceToReport(e));
			}
		}
    }
	
	//Event to clear logcat data of devices
    @ FXML public void clearLogCatDataOfdevices(ActionEvent event){
    	try {
			if (!lst_ClearLogCatDevice_list.getItems().isEmpty()) {
				if (!lst_ClearLogCatDevice_list.getSelectionModel().isEmpty()) {
					ObservableList<String> selectedItems = lst_ClearLogCatDevice_list.getSelectionModel()
							.getSelectedItems();
					for (String _deviceItem : selectedItems) {
						_proc = TerminalOperations.adbCommandsRunInCMD("adb -s " + _deviceItem + " logcat -c");
						ArrayList<String> _strList = TerminalOperations.getadbCommandsOutputCMD(_proc);
						if(_strList.isEmpty()){
							txt_statusArea.appendText( 
									SupportMethods.eventLogMessageformatter("[Success]: Log has been cleared on " + _deviceItem).toString());
						}else{
							txt_statusArea.appendText(SupportMethods.eventLogMessageformatter(_strList,"").toString());
						}
					}
				} else {
					SupportMethods.alertMessage("Select any one of the device to clear the log",
							AlertType.WARNING, "Warning", "Devices are not selected for clear the log");
				}
			} else {
				SupportMethods.alertMessage("Select any one of the device to clear the log", AlertType.WARNING,
						"Warning", "Devices are not found");
			}
		}catch(Exception e){
			Optional<ButtonType> result = SupportMethods.alertExceptionMessage();
			if(result.get().equals(ButtonType.YES)){
				txt_statusArea.setText(ExceptionHandling.addStackTraceToReport(e));
			}
		}
    }
    
    @FXML public void clearAppSelection(ActionEvent event){
    	if(clearAppSelectioGroup.getSelectedToggle().equals(radio_clearAppSelectionCombo)){
    		lst_pckNamesClear.setDisable(true);
    		combo_clearAppDataSelection.setDisable(false);
		}else if(clearAppSelectioGroup.getSelectedToggle().equals(radio_clearAppSelectionList)){
			combo_clearAppDataSelection.setDisable(true);
			lst_pckNamesClear.setDisable(false);
		}
    }
    
    @FXML public void clearAppData(ActionEvent event){
    	try {
    		if (!lst_ClearAppDataDevice_list.getItems().isEmpty()) {
    			if (!lst_ClearAppDataDevice_list.getSelectionModel().isEmpty()) {
    				ObservableList<String> selectedItems = lst_ClearAppDataDevice_list.getSelectionModel()
							.getSelectedItems();
    				String packagename = "";
					if (!combo_clearAppDataSelection.isDisabled()) {
						packagename = combo_clearAppDataSelection.getSelectionModel().getSelectedItem().trim();
					} else if(!lst_pckNamesClear.isDisabled()){
						packagename = lst_pckNamesClear.getSelectionModel().getSelectedItem().trim();
					}
					for (String _deviceItem : selectedItems) {
					    _proc = TerminalOperations.adbCommandsRunInCMD("adb -s " + _deviceItem + " shell pm clear " + packagename);
						ArrayList<String> _strList = TerminalOperations.getadbCommandsOutputCMD(_proc);
						//txt_statusArea.appendText(SupportMethods.eventLogMessageformatter(_strList, "App Data & Cache cleared on " + _deviceItem).toString());
						txt_statusArea.appendText(SupportMethods.eventLogMessageformatter("App Data & Cache cleared on " + _deviceItem).toString());
						txt_statusArea.appendText(SupportMethods.eventLogMessageformatter(_strList, "").toString());
					}
    			}else{
    				SupportMethods.alertMessage("Please select the device to clear app data", AlertType.WARNING, "Warning",
							"Select Device");
    			}
    		}else{
    			SupportMethods.alertMessage("Devices are not found with respect to the app package for clear app data",
						AlertType.WARNING, "Warning", "Devices are not found");
    		}
		} catch (Exception e) {
			Optional<ButtonType> result = SupportMethods.alertExceptionMessage();
			if(result.get().equals(ButtonType.YES)){
				txt_statusArea.setText(ExceptionHandling.addStackTraceToReport(e));
			}
		}
    }
    
    @FXML public void deviceInfoSelector(){
    	if(deviceInfoRadioGroup.getSelectedToggle().equals(deviceinfo_all)){
    		com_deviceinfo.setDisable(true);
		}else if(deviceInfoRadioGroup.getSelectedToggle().equals(deviceinfo_attri)){
			com_deviceinfo.setDisable(false);
		}
    }
    
    //Event handler for to get 'all' and 'attribute' specific device information
    //need to write the code for save into csv
    @FXML public void deviceInfoEvent(ActionEvent event){
    	try {
			if(deviceInfoRadioGroup.getSelectedToggle().equals(deviceinfo_all)){
				ObservableList<DeviceInfoObject> oListStavaka = null;
				ArrayList<DeviceInfoObject> infoList = new ArrayList<DeviceInfoObject>();
				ArrayList<ArrayList<String>> _addCSV = new ArrayList<ArrayList<String>>();
				if (lst_DeviceInfoDevice_list.getSelectionModel().getSelectedIndex() != -1) {
					if(com_deviceinfo.isDisabled()){
						String deviceSerialNumber = lst_DeviceInfoDevice_list.getSelectionModel().getSelectedItem().trim();
						Map<String, String> _mapObject = SupportMethods.getDeviceInfoAdbCommands(deviceSerialNumber);
						for (Map.Entry<String, String> m : _mapObject.entrySet()) {
							_proc = TerminalOperations.adbCommandsRunInCMD(m.getValue());
							ArrayList<String> _strList = TerminalOperations.getadbCommandsOutputCMD(_proc);
							if(!_strList.isEmpty()){
								infoList.add(new DeviceInfoObject(m.getKey(), _strList.get(0)));
							}else{
								infoList.add(new DeviceInfoObject(m.getKey(), "Null"));
							}
						}
						oListStavaka = FXCollections.observableArrayList(infoList);
						if(!oListStavaka.isEmpty()){
							tbl_DeviceInfo.setItems(oListStavaka);
						}else{
							tbl_DeviceInfo.setItems(FXCollections.observableArrayList(new DeviceInfoObject("Null", "Null")));
						}
						/*  //Save to CSV file
						if(((Button)event.getSource()).getId().equalsIgnoreCase("btn_deviceInfoSave")){
							FileChooser fileCho = new FileChooser();
							fileCho.setTitle("Save Device Information");
							fileCho.getExtensionFilters().add(new ExtensionFilter("CSV Files", "*.csv"));
							File _savedfile = fileCho.showSaveDialog(null);
							for (DeviceInfoObject arrayList : oListStavaka) {
								//_addCSV.add(arrayList.getAttributeName());
								//_addCSV.add(infoList);
							}
						}*/
					}else{
						SupportMethods.alertMessage("Please select any one of the attribute to know the device information",
								AlertType.WARNING, "Warning", "Should select any one of the attribute");
					}
				}else {
					SupportMethods.alertMessage("Android device is not selected from device list", 
							AlertType.INFORMATION, "Information", "Select android device from device list");
				}
			}else if(deviceInfoRadioGroup.getSelectedToggle().equals(deviceinfo_attri)){
				if (lst_DeviceInfoDevice_list.getSelectionModel().getSelectedIndex() != -1) {
					if(!com_deviceinfo.isDisabled() && com_deviceinfo.getSelectionModel().getSelectedIndex() != -1){
						String deviceSerialNumber = lst_DeviceInfoDevice_list.getSelectionModel().getSelectedItem().trim();
						String attributeName = com_deviceinfo.getSelectionModel().getSelectedItem().trim();
						Map<String, String> _mapObject = SupportMethods.getDeviceInfoAdbCommands(deviceSerialNumber);
						for (Map.Entry<String, String> m : _mapObject.entrySet()) {
							if (attributeName.equalsIgnoreCase(m.getKey())) {
								_proc = TerminalOperations.adbCommandsRunInCMD(m.getValue());
								ArrayList<String> _strList = TerminalOperations.getadbCommandsOutputCMD(_proc);
								if(!_strList.isEmpty()){
									ObservableList<DeviceInfoObject> oListStavaka = FXCollections.observableArrayList(new DeviceInfoObject(m.getKey(), _strList.get(0)));
					        		tbl_DeviceInfo.setItems(oListStavaka);
								}else{
									ObservableList<DeviceInfoObject> oListStavaka = FXCollections.observableArrayList(new DeviceInfoObject(m.getKey(),"Null"));
					        		tbl_DeviceInfo.setItems(oListStavaka);
								}
								break;
							}
						}
					}else{
						SupportMethods.alertMessage("Please select any one of the attribute to know the device information",
								AlertType.WARNING, "Warning", "Should select any one of the attribute");
					}
				} else {
					SupportMethods.alertMessage("Android device is not selected from device list", 
							AlertType.INFORMATION, "Information", "Select android device from device list");
				}
			}else{
				SupportMethods.alertMessage("Please provide your choice 'All' or 'Attribute' to know the device information",
						AlertType.WARNING, "Warning", "Select All or Attribute");
			}
		} catch (NullPointerException e) {
			SupportMethods.alertMessage("Please provide your choice 'All' or 'Attribute' to know the device information",
					AlertType.WARNING, "Warning", "Select All or Attribute");	
			e.printStackTrace();
		}catch (Exception e) {
			Optional<ButtonType> result = SupportMethods.alertExceptionMessage();
			if(result.get().equals(ButtonType.YES)){
				txt_statusArea.setText(ExceptionHandling.addStackTraceToReport(e));
			}
		}
    }
    
    @FXML
    public void getApplicationFile(ActionEvent event) {
    	String filePath = "", fileformat = "";
    	File file = null;
    	final FileChooser fileChooser = new FileChooser();
    	fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));   
    	if(combobox_deviceOsType.getValue().equalsIgnoreCase("android")){
    		fileformat = ".apk";
    		fileChooser.setTitle("Open " + fileformat + " File");
        	fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("APK (*.apk, *.APK)", "*.apk", "*.APK"));
        	file = fileChooser.showOpenDialog(Kiwee.getPrimaryStage());
    	}else if(combobox_deviceOsType.getValue().equalsIgnoreCase("ios")) {
    		fileformat = ".ipa";
    		fileChooser.setTitle("Open " + fileformat + " File");
        	fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("IPA (*.ipa, *.IPA)", "*.ipa", "*.IPA"));
        	file = fileChooser.showOpenDialog(Kiwee.getPrimaryStage());
    	}
    	if (file != null) {
			  ArrayList<String> fileObjet = new ArrayList<>(); 
			  fileObjet.add(file.getAbsolutePath()); 
    		  filePath = file.getAbsolutePath().toString();
        	combotxt_addApkPath.setItems(FXCollections.observableArrayList(fileObjet));
        	combotxt_addApkPath.getSelectionModel().selectFirst();
        	txt_statusArea.appendText(SupportMethods.eventLogMessageformatter("Selected " + fileformat + " file is " + filePath).toString());
        	txt_statusArea.setScrollLeft(0);
    	}else {
    		txt_statusArea.appendText(SupportMethods.eventLogMessageformatter(fileformat + " file is not selected").toString());
    		txt_statusArea.setScrollLeft(0);
    	}
    }
    
    @FXML public void callPropertySaverDialog() {
    	try {
    		ObservableList<KiweePreferences> data = FXCollections.observableArrayList();
			Stage propertySaverStage = new Stage();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/PropertiesValuesScene.fxml"));
			Parent root = (Parent)loader.load(); 
			//PropertiesSaverController secondC = (PropertiesSaverController)loader.getController();
			Scene scene = new Scene(root);
			propertySaverStage.setTitle("Grape Bowl Properties");
			propertySaverStage.setAlwaysOnTop(false);
			propertySaverStage.getIcons().add(new Image("/resources/images/kiwee_logo.png"));
			propertySaverStage.resizableProperty().setValue(Boolean.FALSE);
			propertySaverStage.initStyle(StageStyle.UTILITY);
			propertySaverStage.initModality(Modality.APPLICATION_MODAL);
			propertySaverStage.setScene(scene);
			propertySaverStage.sizeToScene();
			propertySaverStage.show();
		} catch (Exception e) {
			Optional<ButtonType> result = SupportMethods.alertExceptionMessage();
			if(result.get().equals(ButtonType.YES)){
				txt_statusArea.setText(ExceptionHandling.addStackTraceToReport(e));
			}
		}
    	
    }
    
    @FXML public void executeCommandSelection() {
    	if(executeCommandGroup.getSelectedToggle().equals(radio_PreDefinedCommands)){
    		HBox vBoxloderPreDefined = (HBox) Kiwee.getPreExecuteCommandLoderObject(); 
    		commandExe_RadioSelectionPane.getChildren().clear();
			commandExe_RadioSelectionPane.getChildren().setAll(vBoxloderPreDefined);
		}else if(executeCommandGroup.getSelectedToggle().equals(radio_CustomCommandExecute)){
			VBox vBoxloderCustom = (VBox) Kiwee.getCustomExecuteCommandLoderObject(); 
			commandExe_RadioSelectionPane.getChildren().clear();
			commandExe_RadioSelectionPane.getChildren().setAll(vBoxloderCustom);
		}
    }
    
    @FXML public void closeToolFromMenu(ActionEvent event) {
    	Kiwee.getPrimaryStage().close();
    }
    
    @FXML public void getAllConnectedAndroidDevicesIntoAllTabs(ActionEvent Event) {
    	if (!TerminalOperations.getAdbLocation().isEmpty()) {
			TerminalOperations.adbCommandsRunInCMD("adb start-server");
			_proc = TerminalOperations.adbCommandsRunInCMD("adb devices");
			ArrayList<String> _strList = TerminalOperations.getadbCommandsOutputCMD(_proc);
			_devicesSerialNums = TerminalOperations.getDeviceListFromAdbCommandOutput(_strList);
			if (!_devicesSerialNums.isEmpty()) {
				_devicesSerialNums.forEach(action -> action.trim());
			}
		} else {
			SupportMethods.alertMessage("To set adb path use File -> Preferences from menu options",
					AlertType.WARNING, "Information", "ADB Path is not found");
			_devicesSerialNums = new ArrayList<String>(Arrays.asList("NODEVICE")); 
		}
    	ObservableList<String> items = FXCollections.observableArrayList(_devicesSerialNums);
		if (items.contains("NODEVICE") || items.isEmpty()) {
				SupportMethods.alertMessage(
						"It could be either android Devices are not connected\n" + "                        (Or)"
								+ "\nDeveloper option is not enabled in a connected device",
						AlertType.INFORMATION, "Information", "Android devices are not found");
				txt_statusArea.appendText(SupportMethods.eventLogMessageformatter("[Fail]: Devices are not found").toString());
		} else {
			if (combotxt_addApkPath.getValue() != null || !combotxt_addApkPath.isEditable()) {
				combotxt_addApkPath.getStyleClass().remove("field-error-border");
				lst_InstallDevice_list.setItems(items);
			} else {
				combotxt_addApkPath.getStyleClass().add("field-error-border");
			}
			lst_ClearLogCatDevice_list.setItems(items);
			lst_DeviceInfoDevice_list.setItems(items);
			JFrogSceneController.getInstance().getJFrogListBoxInstance().setItems(items);
			BugReportSaverController.getInstance().getBugReportListBoxInstance().setItems(items);
			PreDefinedCommandController.getInstance().getPreDefinedDevicesListBoxInstance().setItems(items);
			CustomCommandController.getInstance().getCustomCommandListBoxInstance().setItems(items);
			txt_statusArea.appendText(
					SupportMethods.eventLogMessageformatter("[Success]: Devices are found").toString());
		}
    }
    
    @FXML public void getAllConnectediOSDevicesIntoAllTabs(ActionEvent Event) {
    	if (!TerminalOperations.getiOSDeployeLocation().isEmpty() && !TerminalOperations.getlibimobiledeviceLocation().isEmpty()) {
    		_proc = TerminalOperations.ideviceCommandsRun("idevice_id -l");
			_devicesSerialNums = TerminalOperations.getadbCommandsOutputCMD(_proc);
			if (!_devicesSerialNums.isEmpty()) {
				_devicesSerialNums.forEach(action -> action.trim());
			}
		} else {
			SupportMethods.alertMessage("To set libimobiledevice use File -> Preferenccesh from menu options",
					AlertType.WARNING, "Information", "libimobiledevice Path is not found");
			_devicesSerialNums = new ArrayList<String>(Arrays.asList("NODEVICE")); 
		}
    	ObservableList<String> items = FXCollections.observableArrayList(_devicesSerialNums);
		if (items.contains("NODEVICE") || items.isEmpty()) {
				SupportMethods.alertMessage(
						"It could be either iOS Devices are not connected\n",
						AlertType.INFORMATION, "Information", "iOS devices are not found");
				txt_statusArea.appendText(SupportMethods.eventLogMessageformatter("[Fail]: Devices are not found").toString());
		} else {
			if (combotxt_addApkPath.getValue() != null || !combotxt_addApkPath.isEditable()) {
				combotxt_addApkPath.getStyleClass().remove("field-error-border");
				combobox_deviceOsType.getSelectionModel().selectLast();
				lst_InstallDevice_list.setItems(items);
			} else {
				combotxt_addApkPath.getStyleClass().add("field-error-border");
			}
			combobox_UninstalldeviceOsType.getSelectionModel().selectLast();
			JFrogSceneController.getInstance().getJFrogListBoxInstance().setItems(items);
			txt_statusArea.appendText(
					SupportMethods.eventLogMessageformatter("[Success]: Devices are found").toString());
		}
    }
    
    /*@FXML
    public void setColorForWhenTabGetSelected(ActionEvent event) {
    	//Tab nodeToFind = ((Tab) event.getSource());
    	ArrayList<Tab> allTabs = new ArrayList<>(
    			Arrays.asList(installTab,installSheetTab,
    			uninstallTab,clearlogTab, clearAppDataTab, 
    			deviceInfoTab, appInfoTab, bugReportTab));
    	for(Tab eachtab: allTabs) {
    		eachtab.setStyle("-fx-border-color: red;");
    		
    	}
    }*/
    
    
    private ObservableList<String> getDeviceSerialNumberAsPerType() throws Exception{
		ObservableList<String> items = FXCollections.observableArrayList();
		if (combobox_deviceOsType.getValue().equalsIgnoreCase("android")) {
			if (!TerminalOperations.getAdbLocation().isEmpty()) {
				TerminalOperations.adbCommandsRunInCMD("adb start-server");
				_proc = TerminalOperations.adbCommandsRunInCMD("adb devices");
				ArrayList<String> _strList = TerminalOperations.getadbCommandsOutputCMD(_proc);
				_devicesSerialNums = TerminalOperations.getDeviceListFromAdbCommandOutput(_strList);
				if (!_devicesSerialNums.isEmpty()) {
					_devicesSerialNums.forEach(action -> action.trim());
				}
			} else {
				SupportMethods.alertMessage("To setup adb path use File -> Preferences from menu options",
						AlertType.WARNING, "Information", "ADB Path is not found");
				_devicesSerialNums = new ArrayList<String>(Arrays.asList("NODEVICE")); 
			}
		} else if (combobox_deviceOsType.getValue().equalsIgnoreCase("ios")) {
			if (!TerminalOperations.getlibimobiledeviceLocation().isEmpty()) {
				_proc = TerminalOperations.ideviceCommandsRun("idevice_id -l");
				_devicesSerialNums = TerminalOperations.getadbCommandsOutputCMD(_proc);
				if (!_devicesSerialNums.isEmpty()) {
					_devicesSerialNums.forEach(action -> action.trim());
				}
			} else {
				SupportMethods.alertMessage(
						"To setup libimobiledevice path use File -> Preferences from menu options",
						AlertType.WARNING, "Information", "libimobiledevice Path is not found");
				_devicesSerialNums = new ArrayList<String>(Arrays.asList("NODEVICE")); 
			}
		}
		items = FXCollections.observableArrayList(_devicesSerialNums);
		if (items.contains("NODEVICE") || items.isEmpty()) {
			if (combobox_deviceOsType.getValue().equalsIgnoreCase("android")) {
				SupportMethods.alertMessage(
						"It could be either android Devices are not connected\n" + "                         (Or)"
								+ "\nDeveloper option is not enabled in a connected device",
						AlertType.INFORMATION, "Information", "Android devices are not found");
				txt_statusArea.appendText(SupportMethods.eventLogMessageformatter("[Fail]: Devices are not found").toString());
			} else if (combobox_deviceOsType.getValue().equalsIgnoreCase("ios")){
				SupportMethods.alertMessage(
						"It could be either iOS devices are not connected\n",
						AlertType.INFORMATION, "Information", "iOS devices are not found");
				txt_statusArea.appendText(SupportMethods.eventLogMessageformatter("[Fail]: Devices are not found").toString());
			}
		}else {
			txt_statusArea.appendText(SupportMethods.eventLogMessageformatter("[Success]: Devices are found").toString());
		}
		return items;
	}
	
	private ObservableList<String> getDeviceSerialNumberOfAndroid() throws Exception{
		ObservableList<String> items = FXCollections.observableArrayList();
		if (!TerminalOperations.getAdbLocation().isEmpty()) {
			TerminalOperations.adbCommandsRunInCMD("adb start-server");
			_proc = TerminalOperations.adbCommandsRunInCMD("adb devices");
			ArrayList<String> _strList = TerminalOperations.getadbCommandsOutputCMD(_proc);
			_devicesSerialNums = TerminalOperations.getDeviceListFromAdbCommandOutput(_strList);
			if (!_devicesSerialNums.isEmpty()) {
				_devicesSerialNums.forEach(action -> action.trim());
			}
		} else {
			SupportMethods.alertMessage("To set adb path use File -> Set ADB path from menu options",
					AlertType.WARNING, "Information", "ADB Path is not found");
			_devicesSerialNums = new ArrayList<String>(Arrays.asList("NODEVICE")); 
		}
		items = FXCollections.observableArrayList(_devicesSerialNums);
		if (items.contains("NODEVICE") || items.isEmpty()) {
			SupportMethods.alertMessage(
					"It could be either android Devices are not connected\n" + "                         (Or)"
							+ "\nDeveloper option is not enabled in a connected device",
					AlertType.INFORMATION, "Information", "Android devices are not found");
			txt_statusArea.appendText(SupportMethods.eventLogMessageformatter("[Fail]: Devices are not found").toString());
		}
		return items;
	}
	
}

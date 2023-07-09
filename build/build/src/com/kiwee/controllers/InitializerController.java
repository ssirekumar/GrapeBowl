package com.kiwee.controllers;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

import com.kiwee.DeviceInfoObject;
import com.kiwee.SupportMethods;
import com.kiwee.application.Kiwee;

import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class InitializerController implements Initializable {
	
	@FXML private ListView<String> lst_InstallDevice_list, lst_package_names, lst_ClearLogCatDevice_list,
	              lst_UnInstallDevice_list, lst_pckNamesClear, lst_ClearAppDataDevice_list, lst_DeviceInfoDevice_list;
	@FXML private TextArea txt_statusArea;
	@FXML private ComboBox<String> combotxt_addApkPath, combotxt_appUninstall, combo_clearAppDataSelection, com_deviceinfo, 
	                               combobox_deviceOsType, combobox_UninstalldeviceOsType;
	@FXML private Button toolbarbtn_installApp, toolbarbtn_uninstallApp, toolbarbtn_clearlogcat, toolbarbtn_ImageComparision;
	@FXML private Button toolbarbtn_clearcache, toolbarbtn_deviceinfo, toolbarbtn_appinfo, toolbarbtn_bugreport, toolbarbtn_executeCommands;
    @FXML private TableView<DeviceInfoObject> tbl_DeviceInfo;
    @FXML private TableColumn<DeviceInfoObject, String> tblColu_DeviceInfoAttribute, tblColu_DeviceInfoValue;
    @FXML private Tab installTab, bugReportTab;
    @FXML private RadioButton radio_SystemPathInstall, radio_UninstallSelectionCombo,radio_PreDefinedCommands;
    @FXML private AnchorPane anchorPane_BugReportSaver;
    @FXML private ToggleGroup executeCommandGroup;
    @FXML private Pane commandExe_RadioSelectionPane;
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		//device list initializers
		lst_InstallDevice_list.setOrientation(Orientation.VERTICAL);
		lst_InstallDevice_list.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		//tabpane initializers
		//installTab.setStyle("-fx-border-color: darkgray;");
		
		//txt_statusArea
		txt_statusArea.setWrapText(false);
		
		//App install combobox
		combotxt_addApkPath.setEditable(true);
		//combotxt_addApkPath.setItems(SupportMethods.getAllTheAppPathsFromConfig());
		
		//App install combobox
		combotxt_appUninstall.setEditable(true);
		combotxt_appUninstall.setItems(SupportMethods.getAllAndroidPackagesNamesFromConfig());
		combotxt_appUninstall.setVisibleRowCount(3);
		combotxt_appUninstall.getSelectionModel().selectFirst();
		
		//app uninstall listBox
		lst_package_names.setOrientation(Orientation.VERTICAL);
		lst_package_names.setEditable(true);
		lst_package_names.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		lst_package_names.setItems(SupportMethods.getAllAndroidPackagesNamesFromConfig());
		
		//App clear combobox
		combo_clearAppDataSelection.setEditable(true);
		combo_clearAppDataSelection.setVisibleRowCount(3);
		combo_clearAppDataSelection.setItems(SupportMethods.getAllAndroidPackagesNamesFromConfig());
		combo_clearAppDataSelection.getSelectionModel().selectFirst();
		
		//app clear listBox
		lst_pckNamesClear.setOrientation(Orientation.VERTICAL);
		lst_pckNamesClear.setEditable(true);
		lst_pckNamesClear.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		lst_pckNamesClear.setItems(SupportMethods.getAllAndroidPackagesNamesFromConfig());
		
		//packaged app contains
		lst_UnInstallDevice_list.setOrientation(Orientation.VERTICAL);
		lst_UnInstallDevice_list.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		lst_UnInstallDevice_list.setPlaceholder(new Label("Devices"));
		
		//App data clear Listview
		lst_ClearAppDataDevice_list.setOrientation(Orientation.VERTICAL);
		lst_ClearAppDataDevice_list.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		lst_ClearAppDataDevice_list.setPlaceholder(new Label("Devices"));
		
		
		com_deviceinfo.setItems(SupportMethods.getAllDeviceAttributeNames());
		
		//Device Os selection Combobox
		combobox_deviceOsType.setItems(FXCollections.observableArrayList(Arrays.asList("Android", "iOS")));
		combobox_deviceOsType.getSelectionModel().selectFirst();
		
		//Device Os selection Combobox
		combobox_UninstalldeviceOsType.setItems(FXCollections.observableArrayList(Arrays.asList("Android", "iOS")));
		combobox_UninstalldeviceOsType.getSelectionModel().selectFirst();
		
		//Device info
		lst_DeviceInfoDevice_list.setPlaceholder(new Label("Devices"));
		
		//Instaltion radio buttons and its default bheaviour at application loads
		radio_SystemPathInstall.setSelected(true);
		radio_PreDefinedCommands.setSelected(true);
		HBox vBoxloderPreDefined = (HBox) Kiwee.getPreExecuteCommandLoderObject(); 
		commandExe_RadioSelectionPane.getChildren().clear();
		commandExe_RadioSelectionPane.getChildren().setAll(vBoxloderPreDefined);
		
		
		//
		lst_ClearLogCatDevice_list.setOrientation(Orientation.VERTICAL);
		lst_ClearLogCatDevice_list.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		
		//UnInstaltion radio buttons and its default bheaviour at application loads
		//radio_UninstallSelectionCombo.setSelected(true);
		
		/*
		combotxt_appUninstall.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (newValue){
					lst_package_names.setDisable(true);
					System.out.println("Textfield on focus");
		        }else{
		        	lst_package_names.setDisable(false);
		            System.out.println("Textfield out focus");
		        }
				
			}
		});
		
		lst_package_names.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (newValue){
					combotxt_appUninstall.setDisable(true);
					System.out.println("Textfield on focus");
		        }else{
		        	combotxt_appUninstall.setDisable(false);
		            System.out.println("Textfield out focus");
		        }
			}
		});*/
		
		bugReportTab.setOnSelectionChanged(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				VBox vBoxloder = (VBox) Kiwee.getBugsaverLoderObject();
				anchorPane_BugReportSaver.getChildren().setAll(vBoxloder);
			}
		});
		
		
		
		
		//Tableview Control ininitializers
		tbl_DeviceInfo.setCursor(Cursor.DEFAULT);
		tblColu_DeviceInfoAttribute.setCellValueFactory(new PropertyValueFactory<DeviceInfoObject, String>("AttributeName"));
		tblColu_DeviceInfoValue.setCellValueFactory(new PropertyValueFactory<DeviceInfoObject, String>("AttributeValue"));
		   
		
		//Toolbar button initializers
		toolbarbtn_uninstallApp.setId("uninstallApp");
		toolbarbtn_installApp.setId("installApp");
		toolbarbtn_uninstallApp.setId("uninstallApp");
		toolbarbtn_clearlogcat.setId("clearlogcat");
		toolbarbtn_clearcache.setId("clearcache");
		toolbarbtn_deviceinfo.setId("deviceinfo");
		toolbarbtn_appinfo.setId("appinfo"); 
		toolbarbtn_bugreport.setId("bugreport");
		toolbarbtn_executeCommands.setId("executecommands");
		toolbarbtn_ImageComparision.setId("imagecomparision");
	}

}

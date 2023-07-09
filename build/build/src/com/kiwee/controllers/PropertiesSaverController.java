package com.kiwee.controllers;

import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

import com.kiwee.KiweePreferences;
import com.kiwee.SupportMethods;
import com.kiwee.console.PlatformUtils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

public class PropertiesSaverController implements Initializable{
	
	@FXML private Button button_PropertySaverCancel,button_PropertySaverNewEntry, button_PropertySaverSave,
	button_PropertySaverEdit, btn_PreferencesApplied;
	@FXML private Label lbl_PreferencesSavedMessage;
	@FXML private TableView<KiweePreferences> table_PropertykeyValue;
	@FXML private TableColumn<KiweePreferences, String> table_PropertySaverKeyColumn;
	@FXML private TableColumn<KiweePreferences, String> table_PropertySaverValueColumn;
	private String configPropertyFileName, properiesFile;
	private String[] allStringArray;
	private Properties configProperties;
	private String[][] _allKeyNames;
	private Map<String, String> _writableValues = null;
	
	//Default Constructor,  if you add any new you have to modify this
	public PropertiesSaverController(){
		_allKeyNames = new String[][] {
				{"ADB_PATH", "Android ADB installed path"},
				{"ANDROID_APP_PACKAGE_NAMES", "Android & iOS Application Package/Bundle names"},
				{"iOS_LIBMOBILE_PATH", "iOS-libimobiledevice installed path"},
				{"iOS_DEPLOY_PATH", "iOS-ios-deploy installed path"},
				{"JFROG_BASE_ARTIFACT_DIRECTORY", "JFrog Artifactory base URL"},
				{"JFROG_ARTIFACTORY_USERNAME", "JFrog Artifactory user name"},
				{"JFROG_ARTIFACTORY_APIKEY", "JFrog Artifactory api key"},
				{"JFROG_ARTIFACTORY_ANDROID_PATH", "JFrog Artifactory Android build path"},
				{"JFROG_ARTIFACTORY_IOS_PATH", "JFrog Artifactory iOS build path"},
		};
		configPropertyFileName = "config.properties";
		allStringArray = new String[]{configPropertyFileName, System.getProperty("user.dir"), File.separator, "config"};
		properiesFile = allStringArray[1] + allStringArray[2] + allStringArray[3] + allStringArray[2] + allStringArray[0];
		configProperties = PlatformUtils.loadPropertyFile(properiesFile);
	}
	
	@FXML public void cancelPropertyPathWindow(ActionEvent event){
		((Node)(event.getSource())).getScene().getWindow().hide();
    }
	
	
	@FXML public void savePropertySaverIntoFile(ActionEvent event) {
		saveAllParameterValuesIntoConfig();
		((Node)(event.getSource())).getScene().getWindow().hide();
	}
	
    @FXML public void applyPropertySaverIntoFile(ActionEvent event) {
    	if(saveAllParameterValuesIntoConfig()) {
    		lbl_PreferencesSavedMessage.setText("All parameter values are saved into config.properties file");
    	}
	}
	
	
	//Need to modify if any new parameter is added into preferences table.
	private boolean saveAllParameterValuesIntoConfig() {
		_writableValues = new HashMap<>();
		for (int i = 0; i < table_PropertykeyValue.getItems().size(); i++) {
	    	if(table_PropertykeyValue.getItems().get(i).getAttributeName().equalsIgnoreCase(_allKeyNames[i][1])) {
	    		_writableValues.put(_allKeyNames[i][0].toString(),  table_PropertykeyValue.getItems().get(i).getAttributeValue());
	    	}
		}
		return SupportMethods.writeAdbPathIntoPropertiesFile(_writableValues, configPropertyFileName);
		
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		table_PropertySaverKeyColumn.setCellValueFactory(new PropertyValueFactory<>("attributeName"));
		table_PropertySaverValueColumn.setCellValueFactory(new PropertyValueFactory<>("attributeValue"));
		table_PropertySaverValueColumn.setCellFactory(TextFieldTableCell.<KiweePreferences>forTableColumn());
		table_PropertySaverValueColumn.setOnEditCommit(new EventHandler<CellEditEvent<KiweePreferences, String>>(){
            @Override
            public void handle(CellEditEvent<KiweePreferences, String> t) 
            {
                ((KiweePreferences) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        ).setAttributeValue(t.getNewValue());
            }
        });
		table_PropertykeyValue.setItems(getKiweePreferences());
	}
	
	//Need to add new object if you added and new parameter in preferences and align with array numbers.
	private ObservableList<KiweePreferences> getKiweePreferences() {
		KiweePreferences[] p1 = new KiweePreferences[]{
				new KiweePreferences(_allKeyNames[0][1], PlatformUtils.getValueFromProperty(configProperties, _allKeyNames[0][0])),
				new KiweePreferences(_allKeyNames[1][1], PlatformUtils.getValueFromProperty(configProperties, _allKeyNames[1][0])),
				new KiweePreferences(_allKeyNames[2][1], PlatformUtils.getValueFromProperty(configProperties, _allKeyNames[2][0])),
				new KiweePreferences(_allKeyNames[3][1], PlatformUtils.getValueFromProperty(configProperties, _allKeyNames[3][0])),
				new KiweePreferences(_allKeyNames[4][1], PlatformUtils.getValueFromProperty(configProperties, _allKeyNames[4][0])),
				new KiweePreferences(_allKeyNames[5][1], PlatformUtils.getValueFromProperty(configProperties, _allKeyNames[5][0])),
				new KiweePreferences(_allKeyNames[6][1], PlatformUtils.getValueFromProperty(configProperties, _allKeyNames[6][0])),
				new KiweePreferences(_allKeyNames[7][1], PlatformUtils.getValueFromProperty(configProperties, _allKeyNames[7][0])),
				new KiweePreferences(_allKeyNames[8][1], PlatformUtils.getValueFromProperty(configProperties, _allKeyNames[8][0])),
		};
        ObservableList<KiweePreferences> list = FXCollections.observableArrayList(Arrays.asList(p1));
        return list;
    }

}

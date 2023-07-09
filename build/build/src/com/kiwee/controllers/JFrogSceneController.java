package com.kiwee.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import com.kiwee.ExceptionHandling;
import com.kiwee.SupportMethods;
import com.kiwee.console.TerminalOperations;
import com.kiwee.modules.JFrogAccess;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;

public class JFrogSceneController implements Initializable {
	
	@FXML private ListView<String> Jfrog_DeviceListBox;
	@FXML private HBox JFrog_Hbox_installOptions;
	@FXML private Button Jfrog_ConnectToJfrogButton, btn_getDevicesInstallJFrog, Jfrog_DownloadAndInstallApp, Jfrog_DownloadApp;
	@FXML private TreeView<String> Jfrog_TreeViewForBuilds;
	@FXML private TextField Jfrog_SearchLimitText;
	@FXML   private CheckBox JFrog_chk_r, JFrog_chk_d, JFrog_chk_l, JFrog_chk_f, JFrog_chk_s, JFrog_chk_g, JFrog_chk_k;
	private String fc = File.separator;
	private static String userName, apiKey, artifactoryBaseURL, androidRepositoryPath, iOSRepositoryPath;
	private String jfrogServerID = "tr-server-one";
	private String JfrogAndroidDestPath = System.getProperty("user.dir") + fc + "downloads" + fc + "android" + fc + "temp";
	private String JfrogIosDestPath = System.getProperty("user.dir") + fc + "downloads" + fc + "ios" + fc + "temp";
	private static JFrogSceneController jFrogSceneController;
	
	public static JFrogSceneController getInstance() {
		return jFrogSceneController;
	}
	
	public JFrogSceneController() {
		super();
		JFrogSceneController.jFrogSceneController = this;
	}
	
	public Button getJFrogCconnectButton() {
		return Jfrog_ConnectToJfrogButton;
	}
	
	static{
		apiKey = SupportMethods.getKeyValueFromConfigFile("JFROG_ARTIFACTORY_APIKEY");
		userName = SupportMethods.getKeyValueFromConfigFile("JFROG_ARTIFACTORY_USERNAME");
		artifactoryBaseURL = SupportMethods.getKeyValueFromConfigFile("JFROG_BASE_ARTIFACT_DIRECTORY");
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Jfrog_DeviceListBox.setOrientation(Orientation.VERTICAL);
		Jfrog_DeviceListBox.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		Jfrog_DeviceListBox.setPlaceholder(new Label("Devices"));
		
		Jfrog_TreeViewForBuilds.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
		Jfrog_TreeViewForBuilds.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		
		//lst_InstallDeviceJFrog.setBackground(new Background(new BackgroundFill(javafx.scene.paint.Color.BLACK, null, null)));
	}

	@FXML
	public void getdeviceNamesIntoListBox(ActionEvent event) {
		MainController.getInstance().getdeviceNamesIntoListBox(event);
	}
	
	
	public ListView<String> getJFrogListBoxInstance(){
		return Jfrog_DeviceListBox;
	}
	
	public HBox getJFrogInstallOptionObject(){
		return JFrog_Hbox_installOptions;
	}
	
	@FXML
	public void unselectInstallOptions(ActionEvent event) {
		MainController.getInstance().unselectAndroidInstaltionOptions(event);
	}
	
	@FXML
	public void connectJfrogServer(ActionEvent event) {
		if(!userName.isEmpty() && !apiKey.isEmpty() && !artifactoryBaseURL.isEmpty()) {
			try {
				JFrogAccess jfa = new JFrogAccess(userName, apiKey, new URL(artifactoryBaseURL), jfrogServerID);
				jfa.accessJFrogServer();
				if(jfa.isJFrogServerConnectionEstablished()) {
					JFrogSceneController.getInstance().getJFrogCconnectButton().setGraphic(new ImageView("/resources/images/JFrogGreenStatus.png"));
				}else {
					if(jfa.accessJFrogServer()) {
						JFrogSceneController.getInstance().getJFrogCconnectButton().setGraphic(new ImageView("/resources/images/JFrogGreenStatus.png"));
					}else {
						JFrogSceneController.getInstance().getJFrogCconnectButton().setGraphic(new ImageView("/resources/images/JFrogRedStatus.png"));
						SupportMethods.alertMessage("Either JFrog base URL or User name or API key is not added into the Kiwee preferences",
								AlertType.ERROR, "Information", "Invalid/Empty JFrog base URL, User name, API key");
					}
				}
			} catch (MalformedURLException e) {
				SupportMethods.alertMessage("Either no legal protocol could be found in a specification",
						AlertType.WARNING, "Warning", "Malformed JFrog URL");
			}
		}else {
			SupportMethods.alertMessage("Either JFrog base URL or User name or API key is not added into the Kiwee preferences",
					AlertType.INFORMATION, "Information", "JFrog base URL, User name, API key may be empty");
		}
	}
	
	@FXML
	public void getBuildsAsPerDeviceNameSelection(ActionEvent event) {
		androidRepositoryPath = SupportMethods.getKeyValueFromConfigFile("JFROG_ARTIFACTORY_ANDROID_PATH");
		iOSRepositoryPath = SupportMethods.getKeyValueFromConfigFile("JFROG_ARTIFACTORY_IOS_PATH");
		int limit = validateTextinLimit(Jfrog_SearchLimitText.getText());
		if(!userName.isEmpty() && !apiKey.isEmpty() && !artifactoryBaseURL.isEmpty()) {
			try {
				JFrogAccess jfa = new JFrogAccess(userName, apiKey, new URL(artifactoryBaseURL), jfrogServerID);
				if(jfa.isJFrogServerConnectionEstablished()) {
					JFrogSceneController.getInstance().getJFrogCconnectButton().setGraphic(new ImageView("/resources/images/JFrogGreenStatus.png"));
					try {
						if(MainController.getInstance().getDeviceOSTypeObject().getSelectionModel().getSelectedItem().equalsIgnoreCase("android")){
							JSONArray jsonarr = jfa.searchForBuilds(androidRepositoryPath, limit);
							if(jsonarr != null) {
								TreeItem<String> webItem = getbuildsFrom(jsonarr, "Android");
								if (webItem != null) {
									Jfrog_TreeViewForBuilds.setRoot(webItem);
								} else {
									SupportMethods.alertMessage("Builds are not found on specified path of repository",
											AlertType.INFORMATION, "Information", "Builds are not found");
								}
							}else {
								SupportMethods.alertMessage("Need to add android repository path into kiwee preferences",
										AlertType.INFORMATION, "Information", "Android JFrog repository path is not found");
							}
						}else if(MainController.getInstance().getDeviceOSTypeObject().getSelectionModel().getSelectedItem().equalsIgnoreCase("ios")) {
							JSONArray jsonarr = jfa.searchForBuilds(iOSRepositoryPath, limit);
							if(jsonarr != null) {
								TreeItem<String> webItem = getbuildsFrom(jsonarr, "iOS");
								if (webItem != null) {
									Jfrog_TreeViewForBuilds.setRoot(webItem);
								} else {
									SupportMethods.alertMessage("Builds are not found on specified path of repository",
											AlertType.INFORMATION, "Information", "Builds are not found");
								}
							}else {
								SupportMethods.alertMessage("Need to add iOS repository path into kiwee preferences",
										AlertType.INFORMATION, "Information", "iOS JFrog repository path is not found");
							}
						}
					} catch (ParseException e) {
						SupportMethods.alertMessage("Content of 'jfrogcli/json/inputjfrog.json' does not conform to JSON syntax as per specification OR "+ e.getMessage(),
								AlertType.INFORMATION, "Information", "inputjfrog.json is not formated properlly");
					} catch (IOException e) {
						SupportMethods.alertMessage(e.getMessage(),
								AlertType.INFORMATION, "Information", "Failure during reading, writing and searching 'jfrogcli/json/inputjfrog.json' operations");
					}
				}else {
					if(jfa.accessJFrogServer()) {
						JFrogSceneController.getInstance().getJFrogCconnectButton().setGraphic(new ImageView("/resources/images/JFrogGreenStatus.png"));
						try {
							if(MainController.getInstance().getDeviceOSTypeObject().getSelectionModel().getSelectedItem().equalsIgnoreCase("android")){
								JSONArray jsonarr = jfa.searchForBuilds(androidRepositoryPath, limit);
								if(jsonarr != null) {
									TreeItem<String> webItem = getbuildsFrom(jsonarr, "Android");
									if(webItem != null) {
										Jfrog_TreeViewForBuilds.setRoot(webItem);
									}else {
										SupportMethods.alertMessage("Builds are not found on specified path of repository",
												AlertType.INFORMATION, "Information", "Builds are not found");
									}
								}else {
									SupportMethods.alertMessage("Need to add android JFrog repository path into kiwee preferences",
											AlertType.INFORMATION, "Information", "Android JFrog repository path is not found");
								}
							}else if(MainController.getInstance().getDeviceOSTypeObject().getSelectionModel().getSelectedItem().equalsIgnoreCase("ios")) {
								JSONArray jsonarr = jfa.searchForBuilds(iOSRepositoryPath, limit);
								if(jsonarr != null) {
									TreeItem<String> webItem = getbuildsFrom(jsonarr, "iOS");
									if(webItem != null) {
										Jfrog_TreeViewForBuilds.setRoot(webItem);
									}else {
										SupportMethods.alertMessage("Builds are not found on specified path of repository",
												AlertType.INFORMATION, "Information", "Builds are not found");
									}
								}else {
									SupportMethods.alertMessage("Need to add iOS JFrog repository path into kiwee preferences",
											AlertType.INFORMATION, "Information", "iOS JFrog repository path is not found");
								}
							}
						} catch (ParseException e) {
							SupportMethods.alertMessage("Content of 'jfrogcli/json/inputjfrog.json' does not conform to JSON syntax as per specification OR " +e.getMessage(),
									AlertType.INFORMATION, "Information", "inputjfrog.json is not formated properlly");
						} catch (IOException e) {
							SupportMethods.alertMessage(e.getMessage(),
									AlertType.INFORMATION, "Information", "Failure during reading, writing and searching 'jfrogcli/json/inputjfrog.json' operations");
						}
					}else {
						JFrogSceneController.getInstance().getJFrogCconnectButton().setGraphic(new ImageView("/resources/images/JFrogRedStatus.png"));
						SupportMethods.alertMessage("Either JFrog base URL or User name or API key is not added into the Kiwee preferences",
								AlertType.ERROR, "Error", "Invalid/Empty JFrog base URL, User name, API key");
					}
				}
			} catch (MalformedURLException e) {
				SupportMethods.alertMessage("Either no legal protocol could be found in a specification",
						AlertType.WARNING, "Warning", "Malformed JFrog base URL");
			}
		}else{
			SupportMethods.alertMessage("Either JFrog base URL or User name or API key is not added into the Kiwee preferences",
					AlertType.INFORMATION, "Information", "JFrog base URL, User name, API key may be empty");
		}
	}
	
	@FXML
	private URL selectCompleteBuildPath() {
		Jfrog_TreeViewForBuilds.getSelectionModel().getSelectedItem();
		System.out.println(Jfrog_TreeViewForBuilds.getSelectionModel().getSelectedItem());
		return null;
	}
	
	@FXML
	public void downloadAndInstallBuildAsPerDeviceSelection() {
		androidRepositoryPath = SupportMethods.getKeyValueFromConfigFile("JFROG_ARTIFACTORY_ANDROID_PATH");
		iOSRepositoryPath = SupportMethods.getKeyValueFromConfigFile("JFROG_ARTIFACTORY_IOS_PATH");
		if (!userName.isEmpty() && !apiKey.isEmpty() && !artifactoryBaseURL.isEmpty()) {
			try {
				JFrogAccess jfa = new JFrogAccess(userName, apiKey, new URL(artifactoryBaseURL), jfrogServerID);
				if (jfa.isJFrogServerConnectionEstablished()) {
					try {
						TreeItem<String> selecctedValeu = Jfrog_TreeViewForBuilds.getSelectionModel().getSelectedItem();
						if(MainController.getInstance().getDeviceOSTypeObject().getSelectionModel().getSelectedItem().equalsIgnoreCase("android")) {
							if (selecctedValeu != null) {
								JSONArray jsonarr = jfa.downloadApplicationFromJFrogRepo(
										new URI(selecctedValeu.getValue()), Paths.get(JfrogAndroidDestPath));
								Long sucessvalue = (Long) ((JSONObject)((JSONObject)jsonarr.get(0)).get("totals")).get("success");
								if(sucessvalue.longValue() == 1) {
									TextArea txt_statusArea = MainController.getInstance().getStatusTextArea();
									txt_statusArea.appendText(
											SupportMethods.eventLogMessageformatter("[Success]: "+selecctedValeu.getValue() + " file is downloded sucessfully" ).toString());
									this.installAndroidFileFromSystemPath(Paths.get(JfrogAndroidDestPath), selecctedValeu.getValue());
								}else {
									SupportMethods.alertMessage(
											"Something went wrong while downloading application",
											AlertType.ERROR, "Error", "OOps...!!");
								}
							} else {
								SupportMethods.alertMessage("Need to select any one of the build from treeview",
										AlertType.INFORMATION, "Information", "Build selection is empty");
							}
						}else if(MainController.getInstance().getDeviceOSTypeObject().getSelectionModel().getSelectedItem().equalsIgnoreCase("ios")) {
							if (selecctedValeu != null) {
								JSONArray jsonarr = jfa.downloadApplicationFromJFrogRepo(
										new URI(selecctedValeu.getValue()), Paths.get(JfrogIosDestPath));
								Long sucessvalue = (Long) ((JSONObject)((JSONObject)jsonarr.get(0)).get("totals")).get("success");
								if(sucessvalue.longValue() == 1) {
									TextArea txt_statusArea = MainController.getInstance().getStatusTextArea();
									txt_statusArea.appendText(
											SupportMethods.eventLogMessageformatter("[Success]: "+selecctedValeu.getValue() + " file is downloded sucessfully" ).toString());
									//need to update for ios
									this.installAndroidFileFromSystemPath(Paths.get(JfrogIosDestPath), selecctedValeu.getValue());
								}else {
									SupportMethods.alertMessage(
											"Something went wrong while downloading application",
											AlertType.ERROR, "Error", "OOps...!!");
								}
							} else {
								SupportMethods.alertMessage("Need to select any one of the build from treeview",
										AlertType.INFORMATION, "Information", "Build selection is empty");
							}
						}
					} catch (ParseException e) {
						SupportMethods.alertMessage(
								"Something went wrong while downloading application",
								AlertType.ERROR, "Error", "OOps...!!");
					} catch (URISyntaxException e) {
						SupportMethods.alertMessage(
								"Given artifactory build path indicate that a string could not be parsed as a URI reference.",
								AlertType.ERROR, "Error", "OOps...!!");
					}
				} else {
					if(jfa.accessJFrogServer()) {
						try {
							TreeItem<String> selecctedValeu = Jfrog_TreeViewForBuilds.getSelectionModel().getSelectedItem();
							if(MainController.getInstance().getDeviceOSTypeObject().getSelectionModel().getSelectedItem().equalsIgnoreCase("android")) {
								if (selecctedValeu != null) {
									JSONArray jsonarr = jfa.downloadApplicationFromJFrogRepo(
											new URI(selecctedValeu.getValue()), Paths.get(JfrogAndroidDestPath));
									Long sucessvalue = (Long) ((JSONObject)((JSONObject)jsonarr.get(0)).get("totals")).get("success");
									if(sucessvalue.longValue() == 1) {
										TextArea txt_statusArea = MainController.getInstance().getStatusTextArea();
										txt_statusArea.appendText(
												SupportMethods.eventLogMessageformatter("[Success]: "+selecctedValeu.getValue() + " file is downloded sucessfully" ).toString());
										this.installAndroidFileFromSystemPath(Paths.get(JfrogAndroidDestPath), selecctedValeu.getValue());
									}else {
										SupportMethods.alertMessage(
												"Something went wrong while downloading application",
												AlertType.ERROR, "Error", "OOps...!!");
									}
								} else {
									SupportMethods.alertMessage("Need to select any one of the build from treeview",
											AlertType.INFORMATION, "Information", "Build selection is empty");
								}
							}else if(MainController.getInstance().getDeviceOSTypeObject().getSelectionModel().getSelectedItem().equalsIgnoreCase("ios")) {
								if (selecctedValeu != null) {
									JSONArray jsonarr = jfa.downloadApplicationFromJFrogRepo(
											new URI(selecctedValeu.getValue()), Paths.get(JfrogIosDestPath));
									Long sucessvalue = (Long) ((JSONObject)((JSONObject)jsonarr.get(0)).get("totals")).get("success");
									if(sucessvalue.longValue() == 1) {
										TextArea txt_statusArea = MainController.getInstance().getStatusTextArea();
										txt_statusArea.appendText(
												SupportMethods.eventLogMessageformatter("[Success]: "+selecctedValeu.getValue() + " file is downloded sucessfully" ).toString());
										
										this.installAndroidFileFromSystemPath(Paths.get(JfrogIosDestPath), selecctedValeu.getValue());
									}else {
										SupportMethods.alertMessage(
												"Something went wrong while downloading application",
												AlertType.ERROR, "Error", "OOps...!!");
									}
								} else {
									SupportMethods.alertMessage("Need to select any one of the build from treeview",
											AlertType.INFORMATION, "Information", "Build selection is empty");
								}
							}
						} catch (ParseException e) {
							SupportMethods.alertMessage(
									"Something went wrong while downloading application",
									AlertType.ERROR, "Error", "OOps...!!");
						} catch (URISyntaxException e) {
							SupportMethods.alertMessage(
									"Given artifactory build path indicate that a string could not be parsed as a URI reference.",
									AlertType.ERROR, "Error", "OOps...!!");
						}
					}else {
						JFrogSceneController.getInstance().getJFrogCconnectButton().setGraphic(new ImageView("/resources/images/JFrogRedStatus.png"));
						SupportMethods.alertMessage("Either JFrog base URL or User name or API key is not added into the Kiwee preferences",
								AlertType.ERROR, "Error", "Invalid/Empty JFrog base URL, User name, API key");
					}
				}
			} catch (MalformedURLException e1) {
				SupportMethods.alertMessage("Either no legal protocol could be found in a specification",
						AlertType.WARNING, "Warning", "Malformed JFrog Base URL");
			}
		} else {
			SupportMethods.alertMessage(
					"Either JFrog base URL or User name or API key is not added into the Kiwee preferences",
					AlertType.INFORMATION, "Information", "JFrog base URL, User name, API key may be empty");
		}
	}
	
	
	private void installAndroidFileFromSystemPath(Path downlodedPath, String downlodedFilePath) {
		Process _proc = null;
		ArrayList<String> _strList = null;
		String fc = File.separator;
		TextArea txt_statusArea = MainController.getInstance().getStatusTextArea();
		try {
			if (!Jfrog_DeviceListBox.getItems().isEmpty()) {
				if (!Jfrog_DeviceListBox.getSelectionModel().isEmpty()) {
					if(MainController.getInstance().getDeviceOSTypeObject().getSelectionModel().getSelectedItem().equalsIgnoreCase("android")) {
						ObservableList<String> selectedItems = Jfrog_DeviceListBox.getSelectionModel()
								.getSelectedItems();
						
						//This logic will skip the jFrog project root folder
						String pathBeforeApk =  downlodedFilePath.split(".apk")[0];
						String skipFolder = downlodedFilePath.split("/")[0];
						skipFolder = pathBeforeApk.replace(skipFolder, "") + ".apk";
						
						File _newllyDownlodedFile = new File(downlodedPath.toString() + fc + skipFolder);
						for (String _deviceItem : selectedItems) {
							if (_newllyDownlodedFile.isFile()) {
								String optionText = "";
								if (JFrog_chk_r.isSelected()) {
									optionText = optionText + " " + JFrog_chk_r.getText();
								}
								if (JFrog_chk_d.isSelected()) {
									optionText = optionText + " " + JFrog_chk_d.getText();
								}
								if (JFrog_chk_f.isSelected()) {
									optionText = optionText + " " + JFrog_chk_f.getText();
								}
								if (JFrog_chk_l.isSelected()) {
									optionText = optionText + " " + JFrog_chk_l.getText();
								}
								if (JFrog_chk_s.isSelected()) {
									optionText = optionText + " " + JFrog_chk_s.getText();
								}
								if (JFrog_chk_g.isSelected()) {
									optionText = optionText + " " + JFrog_chk_g.getText();
								}
								optionText = optionText + " ";
								String instalationFile = "\"" + _newllyDownlodedFile.getAbsolutePath() + "\"";
								if (!JFrog_chk_l.isSelected() && !JFrog_chk_f.isSelected() && !JFrog_chk_d.isSelected() && !JFrog_chk_r.isSelected()
										&& !JFrog_chk_s.isSelected() && !JFrog_chk_g.isSelected()) {
									txt_statusArea.appendText(SupportMethods.eventLogMessageformatter("adb -s " + _deviceItem
											+ " install -r " + instalationFile).toString());
									_proc = TerminalOperations.adbCommandsRunInCMD(
											"adb -s " + _deviceItem + " install -r " + instalationFile);
								} else {
									txt_statusArea.appendText(SupportMethods.eventLogMessageformatter("adb -s " + _deviceItem + " install"
											+ optionText + instalationFile).toString());
									_proc = TerminalOperations.adbCommandsRunInCMD(
											"adb -s " + _deviceItem + " install" + optionText + instalationFile);
								}
								_strList = TerminalOperations.getadbCommandsOutputCMD(_proc);
								txt_statusArea.appendText(SupportMethods.eventLogMessageformatter(_strList, "").toString());
							} else {
								SupportMethods.alertMessage("Select an application file path", AlertType.WARNING, "Warning",
										"App path is not found");
							}
						}
					}else if(MainController.getInstance().getDeviceOSTypeObject().getSelectionModel().getSelectedItem().equalsIgnoreCase("ios")) {
						ObservableList<String> selectedItems = Jfrog_DeviceListBox.getSelectionModel()
								.getSelectedItems();
						//This logic will skip the jFrog project root folder
						String pathBeforeApk =  downlodedFilePath.split(".ipa")[0];
						String skipFolder = downlodedFilePath.split("/")[0];
						skipFolder = pathBeforeApk.replace(skipFolder, "") + ".ipa";
						
						File _adbFile = new File(downlodedPath.toString() + fc + skipFolder);
						for (String _deviceItem : selectedItems) {
							if (_adbFile.isFile()) {
								String instalationFile = "\"" + _adbFile.getCanonicalPath() + "\"";
								String iosInstallCommand = "ios-deploy --id " + _deviceItem + " --bundle " + instalationFile;
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
				}else {
					SupportMethods.alertMessage("Select any one of the device to install the appliction",
							AlertType.WARNING, "Warning", "Devices are not selected for installing app");
				}
			}else {
				SupportMethods.alertMessage("Select any one of the device to install the appliction", AlertType.WARNING,
						"Warning", "Devices are not found");
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
	
	private TreeItem<String> getbuildsFrom(JSONArray jsonarr, String buildType){
		JSONObject jb = null;
		TreeItem<String> webItem = new TreeItem<>(buildType);
		webItem.setExpanded(true);
		for (Object object : jsonarr) {
			for (int i = 0; i < ((JSONArray)object).size(); i++) {
				 jb =  (JSONObject)(((JSONArray)object).get(i));
				webItem.getChildren().add(new TreeItem<String>((String)jb.get("path")));
			}
		}
		return webItem;
	}
	
	private int validateTextinLimit(String limit) {
		int returnValue = 0;
		try {
			if(!limit.isEmpty()) {
				returnValue = Integer.parseInt(limit);
			}else {
				//If user not entered the default value would be 2
				returnValue = Integer.parseInt("2");
			}
		} catch (NumberFormatException e) {
			SupportMethods.alertMessage("Must enter only integer value in 'Build(s) search limit' field",
					AlertType.ERROR, "Error", "Enter integer value");
		}
		return returnValue;
	}
	
}

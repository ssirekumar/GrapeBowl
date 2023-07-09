package com.kiwee.modules;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.kiwee.SupportMethods;
import com.kiwee.console.TerminalOperations;

public class JFrogAccess {
	
	private String userName;
	private String apiKey;
	private URL artifactoryBaseURL;
	private String artifactConfigServerId;
	private URI iOSRepositoryPath;
	private URI androidRepositoryPath;
	
	public JFrogAccess(String userName, String apiKey, URL artifactoryBaseURL, String artifactConfigServerId) {
		super();
		this.userName = userName;
		this.apiKey = apiKey;
		this.artifactoryBaseURL = artifactoryBaseURL;
		this.artifactConfigServerId = artifactConfigServerId;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}



	public String getApiKey() {
		return apiKey;
	}



	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}



	public URL getArtifactoryBaseURL() {
		return artifactoryBaseURL;
	}



	public void setArtifactoryBaseURL(URL artifactoryBaseURL) {
		this.artifactoryBaseURL = artifactoryBaseURL;
	}



	public String getArtifactConfigServerId() {
		return artifactConfigServerId;
	}



	public void setArtifactConfigServerId(String artifactConfigServerId) {
		this.artifactConfigServerId = artifactConfigServerId;
	}



	public URI getiOSRepositoryPath() {
		return iOSRepositoryPath;
	}



	public void setiOSRepositoryPath(URI iOSRepositoryPath) {
		this.iOSRepositoryPath = iOSRepositoryPath;
	}



	public URI getAndroidRepositoryPath() {
		return androidRepositoryPath;
	}



	public void setAndroidRepositoryPath(URI androidRepositoryPath) {
		this.androidRepositoryPath = androidRepositoryPath;
	}
	
	public boolean accessJFrogServer(){
		boolean returnValue = false;
		Process jFrogProcess = null;
		ArrayList<String> jfrogCommandOut = null;
		StringBuilder jfrogAuth = new StringBuilder().append("jfrog rt config ").append(artifactConfigServerId).append(" ").append("--url=")
		                   .append(artifactoryBaseURL).append(" --user=").append(userName).append(" ").append("--apikey=").append(apiKey);
		String jFrogPingServer = "jfrog rt ping " + "--server-id=" + artifactConfigServerId;
		if(this.isValidArtifactoryURL()) {
			jFrogProcess = TerminalOperations.JfrogCommandsRunInCMD(jfrogAuth.toString());
			jfrogCommandOut = TerminalOperations.getConsoleJFrogCommandsOutput(jFrogProcess);
			if(!jfrogCommandOut.isEmpty() && !SupportMethods.isListContain(jfrogCommandOut, "[Error]")) {
				jFrogProcess = TerminalOperations.JfrogCommandsRunInCMD(jFrogPingServer);
				jfrogCommandOut = TerminalOperations.getConsoleJFrogCommandsOutput(jFrogProcess);
				if(!jfrogCommandOut.isEmpty() && !SupportMethods.isListContain(jfrogCommandOut, "[Error]")) {
					returnValue = true;
				}
			}
		}
		return returnValue;
	}
	
	public boolean isJFrogServerConnectionEstablished() {
		Process jFrogProcess = null;
		boolean returnValue = false;
		ArrayList<String> jfrogCommandOut = null;
		String jFrogPingServer = "jfrog rt ping " + "--server-id=" + artifactConfigServerId;
		jFrogProcess = TerminalOperations.JfrogCommandsRunInCMD(jFrogPingServer);
		jfrogCommandOut = TerminalOperations.getConsoleJFrogCommandsOutput(jFrogProcess);
		if(!jfrogCommandOut.isEmpty() && !SupportMethods.isListContain(jfrogCommandOut, "[Error]") && jfrogCommandOut.contains("OK")) {
			returnValue = true;
		}
		return returnValue;
	}
	
	public JSONArray searchForBuilds(String repositoryPath, int limit) throws ParseException, IOException {
		JSONArray jfrogSearchout = null;
		String jFrogSearchCommand = "jfrog rt search " + "--server-id=" + artifactConfigServerId + " " + "--spec=";
		File inputJSONFile = this.createJfrogSpecificationJSON(repositoryPath, limit);
		if(inputJSONFile != null) {
			jFrogSearchCommand = jFrogSearchCommand + inputJSONFile.getCanonicalPath();
			Process jfrogSearch = TerminalOperations.JfrogCommandsRunInCMD(jFrogSearchCommand);
			ArrayList<String> jfrogSearchCommandOut = TerminalOperations.getConsoleJFrogCommandsOutput(jfrogSearch);
			if (!jfrogSearchCommandOut.isEmpty() && !SupportMethods.isListContain(jfrogSearchCommandOut, "[Error]")) {
				jfrogSearchout = (JSONArray) new JSONParser().parse(jfrogSearchCommandOut.toString());
			}
		}
		return jfrogSearchout;
	}
	
	public File getJFrogInputFile() {
		String fc  = File.separator;
		return new File(
				System.getProperty("user.dir") + fc + "jfrogcli" + fc + "json" + fc + "inputjfrog.json");
	}
	
	private boolean isValidArtifactoryURL() {
		boolean returnValue = true;
		try {
			this.getArtifactoryBaseURL().toURI();
		} catch (URISyntaxException e) {
			returnValue =  false;
		}
		return returnValue;
	}
	
	
	public JSONArray downloadApplicationFromJFrogRepo(URI sourcePath, Path destiPath) throws ParseException {
		JSONArray jsonObject = null;
		String fc = File.separator;
		Process jFrogProcess = null;
		ArrayList<String> jfrogCommandOut = null;
		StringBuilder jfrogFileDownload = new StringBuilder().append("jfrog rt dl ").append(sourcePath.getPath())
				.append(" ").append(destiPath.toString() + fc).append(" ").append("--server-id=").append(artifactConfigServerId)
				.append(" --threads=5 ").append("--recursive=false ").append("--include-dirs=false");
		if(!destiPath.toFile().isDirectory()) {
			destiPath.toFile().mkdir();
			jFrogProcess = TerminalOperations.JfrogCommandsRunInCMD(jfrogFileDownload.toString());
			jfrogCommandOut = TerminalOperations.getConsoleJFrogCommandsOutput(jFrogProcess);
			if (!jfrogCommandOut.isEmpty() && !SupportMethods.isListContain(jfrogCommandOut, "[Error]")) {
				jsonObject = (JSONArray) new JSONParser().parse(jfrogCommandOut.toString());
			}
		}else {
			this.deleteFolder(destiPath.toFile());
			destiPath.toFile().mkdir();
			jFrogProcess = TerminalOperations.JfrogCommandsRunInCMD(jfrogFileDownload.toString());
			jfrogCommandOut = TerminalOperations.getConsoleJFrogCommandsOutput(jFrogProcess);
			if (!jfrogCommandOut.isEmpty() && !SupportMethods.isListContain(jfrogCommandOut, "[Error]")) {
				jsonObject = (JSONArray) new JSONParser().parse(jfrogCommandOut.toString());
			}
		}
		return jsonObject;
	}

	private File createJfrogSpecificationJSON(String pattern, int limit) throws IOException, ParseException {
		JSONObject jFrogJSONObject = null;
		BufferedWriter br = null;
		String jfrogSpecJSON = "{ \n" + 
				"   \"files\":[ \n" + 
				"      { \n" + 
				"         \"sortOrder\":\"desc\",\n" + 
				"         \"pattern\":\""+pattern+"\",\n" + 
				"         \"sortBy\":[ \n" + 
				"            \"created\"\n" + 
				"         ],\n" + 
				"         \"limit\":" + Math.abs(limit) + "\n" + 
				"      }\n" + 
				"   ]\n" + 
				"}";
		File inputJSONFile = this.getJFrogInputFile();
		try {
			if (!inputJSONFile.exists() && !inputJSONFile.isFile()) {
				inputJSONFile.createNewFile();
			}
			jFrogJSONObject = (JSONObject) new JSONParser().parse(jfrogSpecJSON);
			br = new BufferedWriter(new FileWriter(inputJSONFile));
			br.write(jFrogJSONObject.toJSONString().replace("\\",""));
		}finally {
			try {
				if(br != null) {
					br.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return inputJSONFile;
	}
	
	private void deleteFolder(File folder) {
		try {
			FileUtils.deleteDirectory(folder);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

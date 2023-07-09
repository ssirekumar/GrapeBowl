package com.kiwee.test;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.json.simple.parser.ParseException;

import com.kiwee.SupportMethods;
import com.kiwee.console.TerminalOperations;
import com.kiwee.modules.JFrogAccess;

public class TestMain {

	public static void main(String[] args) throws IOException {
		//SupportMethods.writeAdbPathIntoPropertiesFile("ADB_PATH","C:\\Android_Home\\sdk\\platform-toolsssss");
		//SupportMethods.writeAdbPathIntoPropertiesFile("ADB_PATH2","C:\\Android_Home\\sdk\\platform-tools");
		
		
		/*JFrogAccess acs = new JFrogAccess("212701448", "AKCp5buTzLe6QS1ECmgXuVteQJHQ7prEdQCRDxy2eAKtvjcMsRAP5cVWrrbZeAErJX8dZYGbY", new URL("https://devcloud.swcoe.ge.com/artifactory"));
		String fc = File.separator;
		File jfrogObject = new File(System.getProperty("user.dir") + fc + "JFrogCli" + fc + "json" + fc + "file.json");
	    Path file = Paths.get(System.getProperty("user.dir") + fc + "JFrogCli" + fc + "json" + fc + "file.json");
		TerminalOperations.getJFrogLocation();
		String jfrogCommand = "jfrog rt config server-tr-one-1 --url=https://devcloud.swcoe.ge./artifactory --user=212701448 --apikey=KCp5buTzLe6QS1ECmgXuVteQJHQ7prEdQCRDxy2eAKtvjcMsRAP5cVWrrbZeAErJX8dZYGbY";
		//String jfrogCommand = "jfrog";
		//System.out.println(jfrogCommand);
		//System.out.println(TerminalOperations.getadbCommandsOutputCMD(TerminalOperations.JfrogCommandsRunInCMD("adb devices")));
		System.out.println(TerminalOperations.getConsoleJFrogCommandsOutput(TerminalOperations.JfrogCommandsRunInCMD(jfrogCommand)));
		System.out.println(TerminalOperations.getConsoleJFrogCommandsOutput(TerminalOperations.JfrogCommandsRunInCMD("jfrog rt ping --server-id=tr-server-1")));
		ArrayList<String> list = TerminalOperations.getConsoleJFrogCommandsOutput(TerminalOperations.JfrogCommandsRunInCMD("jfrog rt search --server-id=tr-server-1 --spec=" + jfrogObject.getCanonicalPath()));
		System.out.println(TerminalOperations.getConsoleJFrogCommandsOutput(TerminalOperations.JfrogCommandsRunInCMD("jfrog rt search --server-id=tr-server-1 --spec=" + jfrogObject.getCanonicalPath())));
		Files.write(file,list,Charset.defaultCharset());*/


		JFrogAccess acs = new JFrogAccess("212701448", "AKCp5buTzLe6QS1ECmgXuVteQJHQ7prEdQCRDxy2eAKtvjcMsRAP5cVWrrbZeAErJX8dZYGbY", new URL("https://devcloud.swcoe.ge.com/artifactory"), "tr-server-1");
		if(acs.accessJFrogServer()) {
			
				try {
					acs.searchForBuilds("AGQBN/IOS/develop/WindowsFrontend/", 3);
					try {
						acs.downloadApplicationFromJFrogRepo(new URI("AGQBN/ANDROID/develop/AndroidFrontend/20191101.1/GEME-develop.apk"), Paths.get("/Users/puttaguntasiri/MES-puttagsi/"));
					} catch (URISyntaxException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
		}
	}
	
	
	public static int[] findEvenOrOdd(boolean flag, int[] elements) {
		int temp[] = null, vari;
		for (int i = 0; i < elements.length; i++) {
			if(flag) {
				if((vari=(elements[i] % 2)) == 0){
					temp[i] = vari;
				}
			}else {
				if((vari=(elements[i] % 2)) != 0){
					temp[i] = vari;
				}
			}
		}
		return elements;
		
	}

}

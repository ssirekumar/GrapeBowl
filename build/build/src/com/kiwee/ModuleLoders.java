package com.kiwee;

import java.io.IOException;

import javafx.fxml.FXMLLoader;

public class ModuleLoders<T> {
	
	private static FXMLLoader moduleloader;
	
	@SuppressWarnings("hiding")
	public <T> T moduleloader(String pathOfTheFxmlFile) throws IOException{
		moduleloader = new FXMLLoader(getClass().getResource(pathOfTheFxmlFile));
		return moduleloader.<T>load();
	}
}

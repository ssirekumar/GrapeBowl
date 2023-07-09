package com.kiwee;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TextField;

public class PropertiesInfoObject {

	private SimpleStringProperty propertyName;
	private TextField propertyValue;
	
	
	public PropertiesInfoObject() {}
	public PropertiesInfoObject(String propertyName, String propertyValue) {
		super();
		this.propertyName = new SimpleStringProperty(propertyName);
		this.propertyValue = new TextField(propertyValue);
	}
	
	public SimpleStringProperty getPropertyName() {
		return propertyName;
	}
	public void setPropertyName(SimpleStringProperty propertyName) {
		this.propertyName = propertyName;
	}
	public TextField getPropertyValue() {
		return propertyValue;
	}
	public void setPropertyValue(TextField propertyValue) {
		this.propertyValue = propertyValue;
	}

}

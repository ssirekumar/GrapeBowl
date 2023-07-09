package com.kiwee;

import javafx.beans.property.SimpleStringProperty;

public class DeviceInfoObject {
	
 private SimpleStringProperty AttributeName, AttributeValue;	
 
 public DeviceInfoObject(){}
 
 public DeviceInfoObject(String AttributeName, String AttributeValue){
	 this.AttributeName = new SimpleStringProperty(AttributeName);
     this.AttributeValue = new SimpleStringProperty(AttributeValue);
 }
 
 public String getAttributeName() {
     return AttributeName.get();
 }
 
 public void setAttributeName(String AttriName) {
	 AttributeName.set(AttriName);
 }
     
 public String getAttributeValue() {
     return AttributeValue.get();
 }
 public void setAttributeValue(String AttriValue) {
	 AttributeValue.set(AttriValue);
 }
 
 
 
 

}

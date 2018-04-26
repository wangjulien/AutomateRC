package com.telino.automateRC.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Dates {
	
	@XmlElement(name = "DateFacture")
	private String dateFacture;
	
	@XmlElement(name = "DateReception")
	private String dateReception;
	
	public Dates() {
		super();
	}
	public String getDateFacture() {
		return dateFacture;
	}
	public void setDateFacture(String dateFacture) {
		this.dateFacture = dateFacture;
	}
	public String getDateReception() {
		return dateReception;
	}
	public void setDateReception(String dateReception) {
		this.dateReception = dateReception;
	}
	@Override
	public String toString() {
		return "[dateFacture=" + dateFacture + ", dateReception=" + dateReception + "]";
	}		
}
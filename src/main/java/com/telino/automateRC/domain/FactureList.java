package com.telino.automateRC.domain;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Factures")
@XmlAccessorType(XmlAccessType.FIELD)
public class FactureList {

	@XmlElement(name = "Facture")
	private List<Facture> factureList;
	
	public FactureList() {
		super();
	}

	public List<Facture> getFactureList() {
		return factureList;
	}

	public void setFactureList(List<Facture> factureList) {
		this.factureList = factureList;
	}
}

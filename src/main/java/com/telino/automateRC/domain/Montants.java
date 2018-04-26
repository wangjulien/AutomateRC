package com.telino.automateRC.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Montants {
	
	@XmlElement(name = "MontantTTC")
	private Double montantTtc;

	public Montants() {
		super();
	}

	public Double getMontantTtc() {
		return montantTtc;
	}

	public void setMontantTtc(Double montantTtc) {
		this.montantTtc = montantTtc;
	}

	@Override
	public String toString() {
		return "[montantTtc=" + montantTtc + "]";
	}	
}

package com.telino.automateRC.domain;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class ImageFacture {
	
	@XmlElement(name = "Fichier")
	private List<Fichier> Fichiers;

	public ImageFacture() {
		super();
	}

	public List<Fichier> getFichiers() {
		return Fichiers;
	}

	public void setFichiers(List<Fichier> fichiers) {
		Fichiers = fichiers;
	}

	@Override
	public String toString() {
		return Fichiers.toString();
	}	
}

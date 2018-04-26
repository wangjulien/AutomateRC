package com.telino.automateRC.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@XmlRootElement(name = "Fichier")
@XmlAccessorType(XmlAccessType.FIELD)
public class Fichier {

	@XmlValue
	protected String nomFichier;

	@XmlAttribute(name = "Type")
	private TypeFichier typeFichier;

	public Fichier() {
		super();
	}

	public String getNomFichier() {
		return nomFichier;
	}

	public void setNomFichier(String nomFichier) {
		this.nomFichier = nomFichier;
	}

	public TypeFichier getTypeFichier() {
		return typeFichier;
	}

	public void setTypeFichier(TypeFichier typeFichier) {
		this.typeFichier = typeFichier;
	}

	@Override
	public String toString() {
		return "[nomFichier=" + nomFichier + ", typeFichier=" + typeFichier + "]";
	}	
}

package com.telino.automateRC.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class RoleExt {

	@XmlElement(name = "Nom")
	private String nom;
	
	@XmlElement(name = "NumeroExt")
	private String numeroExt;
	
	public RoleExt() {
		super();
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getNumeroExt() {
		return numeroExt;
	}
	public void setNumeroExt(String numeroExt) {
		this.numeroExt = numeroExt;
	}
	@Override
	public String toString() {
		return "[nom=" + nom + ", numeroExt=" + numeroExt + "]";
	}	
}
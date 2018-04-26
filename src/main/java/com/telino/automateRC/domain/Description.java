package com.telino.automateRC.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Description {
	
	@XmlElement(name = "NumeroFacture")
	private String numeroFacture;
	
	@XmlElement(name = "NumeroCockpit")
	private String numeroCockpit;
	
	@XmlElement(name = "NumeroFactureAcompte")
	private String numeroFactureAcompte;
	
	@XmlElement(name = "TypeDocument")
	private TypeDocument typeDocument;
	
	public Description() {
		super();
	}

	public String getNumeroFacture() {
		return numeroFacture;
	}

	public void setNumeroFacture(String numeroFacture) {
		this.numeroFacture = numeroFacture;
	}

	public String getNumeroCockpit() {
		return numeroCockpit;
	}

	public void setNumeroCockpit(String numeroCockpit) {
		this.numeroCockpit = numeroCockpit;
	}

	public String getNumeroFactureAcompte() {
		return numeroFactureAcompte;
	}

	public void setNumeroFactureAcompte(String numeroFactureAcompte) {
		this.numeroFactureAcompte = numeroFactureAcompte;
	}

	public TypeDocument getTypeDocument() {
		return typeDocument;
	}

	public void setTypeDocument(TypeDocument typeDocument) {
		this.typeDocument = typeDocument;
	}

	@Override
	public String toString() {
		return "[numeroFacture=" + numeroFacture + ", numeroCockpit=" + numeroCockpit
				+ ", numeroFactureAcompte=" + numeroFactureAcompte + ", typeDocument=" + typeDocument + "]";
	}		
}

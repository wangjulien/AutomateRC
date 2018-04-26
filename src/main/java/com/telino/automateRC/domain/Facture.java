package com.telino.automateRC.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Facture {

	@XmlElement(name = "Description")
	private Description description;

	@XmlElement(name = "Lot")
	private String lot;

	@XmlElement(name = "Dates")
	private Dates dates;

	@XmlElement(name = "Fournisseur")
	private RoleExt fournisseur;

	@XmlElement(name = "Acheteur")
	private RoleExt acheteur;

	@XmlElement(name = "Montants")
	private Montants montants;

	@XmlElement(name = "ImageFacture")
	private ImageFacture imageFacture;

	public Facture() {
		super();
	}

	public Description getDescription() {
		return description;
	}

	public void setDescription(Description description) {
		this.description = description;
	}

	public String getLot() {
		return lot;
	}

	public void setLot(String lot) {
		this.lot = lot;
	}

	public Dates getDates() {
		return dates;
	}

	public void setDates(Dates dates) {
		this.dates = dates;
	}

	public RoleExt getFournisseur() {
		return fournisseur;
	}

	public void setFournisseur(RoleExt fournisseur) {
		this.fournisseur = fournisseur;
	}

	public RoleExt getAcheteur() {
		return acheteur;
	}

	public void setAcheteur(RoleExt acheteur) {
		this.acheteur = acheteur;
	}

	public Montants getMontants() {
		return montants;
	}

	public void setMontants(Montants montants) {
		this.montants = montants;
	}

	public ImageFacture getImageFacture() {
		return imageFacture;
	}

	public void setImageFacture(ImageFacture imageFacture) {
		this.imageFacture = imageFacture;
	}

	@Override
	public String toString() {
		return "Facture [description=" + description + ", lot=" + lot + ", dates=" + dates + ", fournisseur="
				+ fournisseur + ", acheteur=" + acheteur + ", montants=" + montants + ", imageFacture=" + imageFacture
				+ "]";
	}

}

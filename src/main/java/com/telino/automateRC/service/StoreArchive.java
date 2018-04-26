package com.telino.automateRC.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.telino.automateRC.domain.Facture;

public class StoreArchive {
	
	private String directory;
	private String user;
	private String password;
	private String environnement;
	private String mail;
	private String nom_base;


	public StoreArchive(String directory, String user, String password, String environnement, String mail, String nom_base) {
		super();
		this.directory = directory;
		this.user = user;
		this.password = password;
		this.environnement = environnement;
		this.mail = mail;
		this.nom_base = nom_base;
	}

	public void archive(Facture facture){
		
	}
	
	public void verse(Facture facture, List<File> pdfs){
		LinkedList<HashMap<String,Object>> requests = new LinkedList<HashMap<String,Object>>();
		HashMap<String,String> specificData = genererSpecificMetadata(facture);
		
		Iterator<File> it = pdfs.iterator();
		while (it.hasNext()){
			File file = it.next();
			byte[] content = new byte[(int)(long)file.length()];
			InputStream is;
			try {
				is = new FileInputStream(file);
				is.read(content);
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			HashMap<String,Object> archivageRequest = new HashMap<String,Object>();
			archivageRequest.put("command","storeDraft");

    		archivageRequest.put("user",user);
    		archivageRequest.put("password",password);
    		archivageRequest.put("application","ADELIS");
    		archivageRequest.put("nomBase",nom_base);

    		archivageRequest.put("doctype",specificData.get("doctype"));
    		
    		archivageRequest.put("categorie",specificData.get("categorie"));
    		archivageRequest.put("domnnom",specificData.get("silo"));
    		archivageRequest.put("keywords",specificData.get("keywords"));
    		archivageRequest.put("title",file.getName());
//    		archivageRequest.put("content_type",Files.probeContentType(file.toPath()));
    		archivageRequest.put("content_length",file.length());
    		archivageRequest.put("archiver_id",user);
    		archivageRequest.put("mailowner",mail);
    		archivageRequest.put("organisationversante",environnement);
    		
    		archivageRequest.put("content", Base64.getEncoder().encodeToString(content));
    		archivageRequest.put("date", new Date());
    		
    		requests.add(archivageRequest);
		}
	}
	
	public HashMap<String,String> genererSpecificMetadata(Facture facture) {
		
		HashMap<String,String> specificData = new HashMap<String,String>();
		specificData.put("doctype", "Facture");
		String keywords = "<Type=Facture>";
		
		if (directory.equals("factures_fourn_papier")) {
			
			specificData.put("categorie", "Fournisseur papier");
			specificData.put("silo", facture.getAcheteur().getNom());
			keywords += "<Categorie=Fournisseur papier>";
			keywords += "<Référence de facture="+facture.getDescription().getNumeroFacture()+">";
			if (facture.getDescription().getNumeroCockpit() != null) keywords += "<Numéro de document SAP="+facture.getDescription().getNumeroCockpit()+">";
			keywords += "<Fournisseur="+facture.getFournisseur().getNom()+">";
			if (facture.getAcheteur().getNom()!= null) keywords += "<Acheteur="+facture.getAcheteur().getNom()+">";
			if ( facture.getLot()!=null) keywords += "<Lot de numérisation="+facture.getLot()+">";
			if ( facture.getDates().getDateFacture()!=null) keywords += "<Date de la facture="+facture.getDates().getDateFacture()+">";
			if ( facture.getDates().getDateReception()!=null) keywords += "<Date de réception="+facture.getDates().getDateReception()+">";
			if ( facture.getMontants().getMontantTtc()!=null ) keywords += "<Montant TTC="+facture.getMontants().getMontantTtc()+">";
			if ( facture.getDescription().getTypeDocument()!=null ) keywords += "<Type="+facture.getDescription().getTypeDocument().toString()+">";
			
		} else if (directory.equals("factures_fourn_mail")) {

			specificData.put("categorie", "Fournisseur mail");
			specificData.put("silo", facture.getAcheteur().getNom());
			keywords += "<Categorie=Fournisseur mail>";
			keywords += "<Référence de facture="+facture.getDescription().getNumeroFacture()+">";
			if (facture.getDescription().getNumeroCockpit() != null) keywords += "<Numéro de document SAP="+facture.getDescription().getNumeroCockpit()+">";
			keywords += "<Fournisseur="+facture.getFournisseur().getNom()+">";
			if (facture.getAcheteur().getNom()!= null) keywords += "<Acheteur="+facture.getAcheteur().getNom()+">";
			if ( facture.getLot()!=null) keywords += "<Identifiant du mail="+facture.getLot()+">";
			if ( facture.getDates().getDateFacture()!=null) keywords += "<Date de la facture="+facture.getDates().getDateFacture()+">";
			if ( facture.getDates().getDateReception()!=null) keywords += "<Date de réception="+facture.getDates().getDateReception()+">";
			if ( facture.getMontants().getMontantTtc()!=null ) keywords += "<Montant TTC="+facture.getMontants().getMontantTtc()+">";
			if ( facture.getDescription().getTypeDocument()!=null ) keywords += "<Type="+facture.getDescription().getTypeDocument().toString()+">";
			
		}  else if (directory.equals("factures_evac")) {

			String keywordsbis = "<Type=Facture>";

			specificData.put("categorie", "Fournisseur évacuation");
			specificData.put("categoriebis", "Client évacuation");
			specificData.put("silo", facture.getAcheteur().getNom());
			specificData.put("silobis", facture.getFournisseur().getNom());
			keywords += "<Categorie=Fournisseur évacuation>";
			keywordsbis += "<Categorie=Client évacuation>";
			keywords += "<Référence de facture="+facture.getDescription().getNumeroFacture()+">";
			keywordsbis += "<Référence de facture="+facture.getDescription().getNumeroFacture()+">";
			if (facture.getDescription().getNumeroCockpit() != null) {
				
				keywords += "<Numéro de document SAP="+facture.getDescription().getNumeroCockpit()+">";
				keywordsbis += "<Numéro de document SAP="+facture.getDescription().getNumeroCockpit()+">";
			}
			keywords += "<Fournisseur="+facture.getFournisseur().getNom()+">";
			keywordsbis += "<Fournisseur="+facture.getAcheteur().getNom()+">";
			keywordsbis += "<Acheteur="+facture.getFournisseur().getNom()+">";
			keywords += "<Acheteur="+facture.getAcheteur().getNom()+">";
			if ( facture.getDates().getDateFacture()!=null) {
				
				keywords += "<Date de la facture="+facture.getDates().getDateFacture()+">";
				keywordsbis += "<Date de la facture="+facture.getDates().getDateFacture()+">";
			}
			if ( facture.getDates().getDateReception()!=null) {
				
				keywords += "<Date de réception="+facture.getDates().getDateReception()+">";
				keywordsbis += "<Date de réception="+facture.getDates().getDateReception()+">";
			}
			if ( facture.getMontants().getMontantTtc()!=null ) {
				
				keywords += "<Montant TTC="+facture.getMontants().getMontantTtc()+">";
				keywordsbis += "<Montant TTC="+facture.getMontants().getMontantTtc()+">";
			}
			if ( facture.getDescription().getTypeDocument()!=null ) {
				
				keywords += "<Type="+facture.getDescription().getTypeDocument().toString()+">";
				keywordsbis += "<Type="+facture.getDescription().getTypeDocument().toString()+">";
			}
			
			specificData.put("keywordsbis",keywordsbis);
			
		}  else if (directory.equals("factures_autofac")) {

			specificData.put("categorie", "Autofacturation");
			specificData.put("silo", facture.getAcheteur().getNom());
			keywords += "<Categorie=Autofacturation>";
			keywords += "<Référence de facture="+facture.getDescription().getNumeroFacture()+">";
			if (facture.getDescription().getNumeroCockpit() != null) keywords += "<Numéro de document SAP="+facture.getDescription().getNumeroCockpit()+">";
			keywords += "<Fournisseur="+facture.getFournisseur().getNom()+">";
			keywords += "<Acheteur="+facture.getAcheteur().getNom()+">";
			if ( facture.getDates().getDateFacture()!=null) keywords += "<Date de la facture="+facture.getDates().getDateFacture()+">";
			if ( facture.getDates().getDateReception()!=null) keywords += "<Date de réception="+facture.getDates().getDateReception()+">";
			if ( facture.getMontants().getMontantTtc()!=null ) keywords += "<Montant TTC="+facture.getMontants().getMontantTtc()+">";
			if ( facture.getDescription().getTypeDocument()!=null ) keywords += "<Type="+facture.getDescription().getTypeDocument().toString()+">";
			
		} 
		
		specificData.put("keywords", keywords);
		return specificData;
		
	}

}

package com.telino.automateRC.util;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.telino.automateRC.domain.Dates;
import com.telino.automateRC.domain.Description;
import com.telino.automateRC.domain.Facture;
import com.telino.automateRC.domain.Fichier;
import com.telino.automateRC.domain.ImageFacture;
import com.telino.automateRC.domain.Montants;
import com.telino.automateRC.domain.RoleExt;
import com.telino.automateRC.domain.TypeDocument;
import com.telino.automateRC.domain.TypeFichier;

public class Test {
	
	private static final Logger  logger = LoggerFactory.getLogger(Test.class);

	   public static void main (String args[])
	   {
			
			Description description = new Description();
			description.setNumeroFacture("123456");
			description.setNumeroCockpit("0123456");
			description.setTypeDocument(TypeDocument.F);
			
			Dates dates = new Dates();
			dates.setDateFacture("2018-04-17");
			dates.setDateReception("2018-04-18");
			
			Fichier fichier = new Fichier();
			fichier.setNomFichier("test.pdf");
			fichier.setTypeFichier(TypeFichier.Facture);
			Fichier fichier2 = new Fichier();
			fichier2.setNomFichier("test2.pdf");
			fichier.setTypeFichier(TypeFichier.Facture);
			List<Fichier> listFichier = new ArrayList<Fichier>();
			listFichier.add(fichier);
			listFichier.add(fichier2);
			
			ImageFacture imageFacture = new ImageFacture();
			imageFacture.setFichiers(listFichier);
			
			Montants montants = new Montants();
			montants.setMontantTtc(3000.00);
			
			RoleExt fournisseur = new RoleExt();
			fournisseur.setNom("REMY COINTREAU S.A.");
			RoleExt acheteur = new RoleExt();
			acheteur.setNom("REMY COINTREAU SERV SAS");
			
			Facture facture = new Facture();
			facture.setAcheteur(acheteur);
			facture.setFournisseur(fournisseur);
			facture.setDates(dates);
			facture.setDescription(description);
			facture.setImageFacture(imageFacture);
			facture.setLot("lot123456");
		   
			try {
				FactureValidator factureValidator = new FactureValidator("factures_fourn_papier");
				
				Boolean flag = factureValidator.validateMandatory(facture);
				System.out.println(flag);
				
				if (flag) {
					
				}
				
			} catch (IOException e) {
				
				e.printStackTrace();
				logger.error("Erreur lors du chargement du fichier properties associ� aux champs obligatoires par r�pertoire de travail");
				
			} catch (NoSuchFieldException e) {
				
				logger.error("Mauvais param�trage l'attribut n'existe pas dans l'objet FACTURE");
				
			} catch (SecurityException e) {
				
				e.printStackTrace();
				logger.error("Erreur interne : on rejoue le fichier");
				
			} catch (NoSuchMethodException e) {
				
				logger.error("La m�thode get de l'attribut obligatoire n'existe pas dans l'objet FACTURE");
				
			} catch (IllegalAccessException e) {
				
				e.printStackTrace();
				logger.error("Erreur interne : on rejoue le fichier");
				
			} catch (IllegalArgumentException e) {
				
				e.printStackTrace();
				logger.error("Erreur interne : on rejoue le fichier");
				
			} catch (InvocationTargetException e) {
				
				e.printStackTrace();
				logger.error("Erreur interne : on rejoue le fichier");
				
			}
		   
	   }
	
}

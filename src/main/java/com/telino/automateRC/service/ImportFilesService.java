package com.telino.automateRC.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.servlet.ServletContext;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.telino.automateRC.domain.Facture;
import com.telino.automateRC.domain.FactureList;
import com.telino.automateRC.util.FactureValidator;
import com.telino.automateRC.util.XmlValidatorByXsd;

public class ImportFilesService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ImportFilesService.class);

	private static final String ERR_FOLDER = "/ERR";
	private static final String BKP_FOLDER = "/BKP";
	private static final String SEPARATOR = "/";

	private static final String EXT_XML = ".xml";

	public final static String USER = "user";
	public final static String PASSWORD = "password";
	public final static String MAIL = "mail";
	public final static String ENVIRONNEMENT = "environnement";
	public final static String NOM_BASE = "nombase";

	private static Pattern FICHIER_REGEX = Pattern.compile(".*>(.*?)</Fichier>");

	private final String folderPath;
	private final String errFolderPath;
	private final String bkpFolderPath;

	private Unmarshaller factureUnmarshaller;

	private XmlValidatorByXsd xmlValidator;
	private FactureValidator factureValidator;
	private StoreArchive storeArchive;

	public ImportFilesService(final String folderPath, final String folderName, final ServletContext ctx) {
		super();
		
		Objects.requireNonNull(ctx, "Servlet context can't be null");
		
		this.folderPath = folderPath;
		this.errFolderPath = folderPath + ERR_FOLDER;
		this.bkpFolderPath = folderPath + BKP_FOLDER;
		
		// Instanctier un Unmarshaller pour la class FactureList
		try {
			JAXBContext jc = JAXBContext.newInstance(FactureList.class);
			this.factureUnmarshaller = jc.createUnmarshaller();
		} catch (JAXBException e) {
			LOGGER.error("Can NOT initialize XML unmarshaller for class : {}", FactureList.class.getName());
			throw new RuntimeException(e);
		}

		// Creer 2 sous-repertoires
		File directory = new File(this.errFolderPath);
		if (!directory.exists()) {
			directory.mkdir();
		}

		directory = new File(this.bkpFolderPath);
		if (!directory.exists()) {
			directory.mkdir();
		}

		// Instantier les composants
		try {
			this.factureValidator = new FactureValidator(folderName);
		} catch (IOException e) {
			LOGGER.error("Can NOT load properties file : {}", folderName);
			throw new RuntimeException(e);
		}

		this.storeArchive = new StoreArchive(folderPath, ctx.getInitParameter(USER).toString(),
				ctx.getInitParameter(PASSWORD).toString(), ctx.getInitParameter(ENVIRONNEMENT).toString(),
				ctx.getInitParameter(MAIL).toString(), ctx.getInitParameter(NOM_BASE).toString());

	}

	public void setXmlValidator(XmlValidatorByXsd xmlValidator) {
		this.xmlValidator = xmlValidator;
	}
		
	public String getFolderPath() {
		return folderPath;
	}

	public void checkAndImportFiles() {

		List<File> errFiles = new ArrayList<>();
		List<File> bkpFiles = new ArrayList<>();

		for (File file : new File(folderPath).listFiles()) {

			if (file.isFile() && file.getName().endsWith(EXT_XML)) {

				Facture facture;

				try (FileInputStream fstream = new FileInputStream(file)) {
					FactureList factures = (FactureList) this.factureUnmarshaller.unmarshal(fstream);
					facture = factures.getFactureList().get(0);
					LOGGER.debug(facture.toString());
				} catch (JAXBException | IOException e) {
					LOGGER.info("{} can NOT be parsed, reason : ", file.getName(), e);

					// Extraction des fichier *.pdf, *.html et les deplacer vers ERR

					try (FileInputStream fstream = new FileInputStream(file);
							BufferedReader br = new BufferedReader(new InputStreamReader(fstream))) {
						String strLine;

						boolean anyMatched = false;
						while ((strLine = br.readLine()) != null) {

							Matcher matcher = FICHIER_REGEX.matcher(strLine);
							if (matcher.matches()) {
								LOGGER.info("File name parsed : {} ", matcher.group(1));
								errFiles.add(new File(folderPath + SEPARATOR + matcher.group(1)));
								anyMatched = true;
							}
						}

						if (!anyMatched) {
							LOGGER.error("Pas de nom de fichier parse !");
							continue;
						}

					} catch (IOException ex) {
						LOGGER.error("Can not read file {}, reason : ", file, ex);
						continue;
					}

					errFiles.add(file);

					continue;
				}

				// Les fichiers associes
				List<File> fichiers = facture.getImageFacture().getFichiers().stream()
						.map(e -> new File(folderPath + SEPARATOR + e.getNomFichier())).collect(Collectors.toList());

				// Check XML is valid et tous les PDF sont presents
				if (xmlValidator.checkXmlIsValid(file) && checkAllAttachedFilesExists(fichiers)) {

//					try {
//						LOGGER.debug("Verification des mentions obligatoire pour archivage direct");
//						
//						// Si mentions obligatoire sont valorise
//						if ( factureValidator.validateMandatory(facture) ) {
//							storeArchive.archive(facture);
//						} else {
//							storeArchive.verse(facture, fichiers);
//						}
//					} catch (NoSuchFieldException e) {
//						LOGGER.error("Mauvais parametrage l'attribut n'existe pas dans l'objet FACTURE");
//						continue;
//					} catch (NoSuchMethodException e) {
//						LOGGER.error("La methode get de l'attribut obligatoire n'existe pas dans l'objet FACTURE");
//						continue;
//					} catch (SecurityException | IllegalAccessException | IllegalArgumentException
//							| InvocationTargetException e1) {
//						LOGGER.error("Impossible de verifier les mentions obligatoire pour l'archivage direct : ", e1);
//						continue;
//					}

					bkpFiles.add(file);
					bkpFiles.addAll(fichiers);
				} else {
					// Xml non valid, il faut deplacer touts les fichier dans un
					// sous repertoir
					// <ERR>;
					errFiles.add(file);
					// add all PDF mentionne dans ce XML
					errFiles.addAll(fichiers);

				}
			}
		}

		// Move les fichiers
		try {
			for (File file : bkpFiles) {
				if (file.exists())
					Files.move(file.toPath(), Paths.get(bkpFolderPath).resolve(file.getName()));
			}

			for (File file : errFiles) {
				if (file.exists())
					Files.move(file.toPath(), Paths.get(errFolderPath).resolve(file.getName()));
			}
		} catch (IOException ex) {
			LOGGER.error("Can not move file reason : ", ex);
		}

	}

	private boolean checkAllAttachedFilesExists(List<File> pdfs) {
		for (File pdf : pdfs) {
			if (!pdf.exists()) {
				LOGGER.error("Un fichier mentioné dans XML n'existe pas : {}", pdf.getName());
				return false;
			}
		}
		return true;
	}

}

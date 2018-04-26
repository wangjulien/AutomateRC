package com.telino.automateRC.listener;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import com.telino.automateRC.service.ImportFilesService;
import com.telino.automateRC.util.XmlValidatorByXsd;

@WebListener
public class StartStopListener implements ServletContextListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(StartStopListener.class);

	private final static String FILE_PROPERTIES = "/config.properties";
	public final static String DIRECTORY_PROPERTIES = "directory";
	public final static String USER = "user";
	public final static String PASSWORD = "password";
	public final static String MAIL = "mail";
	public final static String ENVIRONNEMENT = "environnement";
	public final static String NOM_BASE = "nombase";

	private static final String SCHEMA_FOLDER = "/xsd/";
	private static final String ROOT_FOLDER_PATH = "rootpath";

	public final static List<ImportFilesService> importFilesServices = new ArrayList<>();

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		ServletContext ctx = servletContextEvent.getServletContext();

		Properties p = new Properties();

		try (InputStream input = getClass().getResourceAsStream(FILE_PROPERTIES)) {
			p.load(input);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		ctx.setInitParameter(DIRECTORY_PROPERTIES, p.getProperty(DIRECTORY_PROPERTIES));
		ctx.setInitParameter(USER, p.getProperty(USER));
		ctx.setInitParameter(PASSWORD, p.getProperty(PASSWORD));
		ctx.setInitParameter(MAIL, p.getProperty(MAIL));
		ctx.setInitParameter(ENVIRONNEMENT, p.getProperty(ENVIRONNEMENT));
		ctx.setInitParameter(NOM_BASE, p.getProperty(NOM_BASE));

		// instanctier les services (Stateless)
		String[] folderNames = p.getProperty(DIRECTORY_PROPERTIES).split(",");

		for (String folderName : folderNames) {
			ImportFilesService importFilesService = new ImportFilesService(p.getProperty(ROOT_FOLDER_PATH) + folderName,
					folderName, ctx);
			XmlValidatorByXsd xmlValidator = new XmlValidatorByXsd();

			File schemaFile = new File(getClass().getResource(SCHEMA_FOLDER + folderName + ".xsd").getFile());
			LOGGER.debug(SCHEMA_FOLDER + folderName + ".xsd");

			try {
				xmlValidator.init(schemaFile);
				importFilesService.setXmlValidator(xmlValidator);
			} catch (SAXException e) {
				LOGGER.error("XSD file {} is not valid : ", schemaFile.getName(), e);
				continue;
			}

			importFilesServices.add(importFilesService);
		}

		LOGGER.info("Servlet has been started.");
	}

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		importFilesServices.clear();
		LOGGER.info("Servlet has been stopped.");
	}

}
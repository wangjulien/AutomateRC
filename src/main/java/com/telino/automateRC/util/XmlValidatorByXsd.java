package com.telino.automateRC.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

public class XmlValidatorByXsd {

	private static final Logger LOGGER = LoggerFactory.getLogger(XmlValidatorByXsd.class);

	private Schema schema;
	private Validator validator;

	public XmlValidatorByXsd() {
		super();
	}

	public void init(final File xsd) throws SAXException {
		SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		this.schema = schemaFactory.newSchema(xsd);
		this.validator = schema.newValidator();
	}

	public boolean checkXmlIsValid(final File xml) {

		try (InputStream xmlFileStream = new FileInputStream(xml)) {
			Source xmlFile = new StreamSource(xmlFileStream);
			this.validator.validate(xmlFile);
			LOGGER.info("{} is valid", xml.getName());
			return true;
		} catch (SAXException | IOException e) {
			LOGGER.error("{} is NOT valid reason : ", xml.getName(), e);
			return false;
		}
	}
}

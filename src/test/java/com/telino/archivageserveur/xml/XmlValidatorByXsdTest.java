package com.telino.archivageserveur.xml;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;
import org.xml.sax.SAXException;

import com.telino.automateRC.util.XmlValidatorByXsd;

class XmlValidatorByXsdTest {

	@Test
	void test() {
		String schemaURL = "./src/main/resources/xml/factures_scan.xsd";
		File schemaFile = new File(schemaURL); // etc.
		File xmlFile = new File("./src/main/resources/xml/RC-exemple1.xml");
		
		XmlValidatorByXsd factureFournPapierValidator = new XmlValidatorByXsd();
		try {
			factureFournPapierValidator.init(schemaFile);
		} catch (SAXException e) {
			System.err.println("XSD file " + schemaFile.getName() + " is not valid : " + e);
			return;
		} 
		assertTrue(factureFournPapierValidator.checkXmlIsValid(xmlFile));
	}

}

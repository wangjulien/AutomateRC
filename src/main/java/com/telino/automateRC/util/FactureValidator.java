package com.telino.automateRC.util;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.telino.automateRC.domain.Facture;

public class FactureValidator {

	private Properties p = new Properties();
	public final static String METADATA_MANDATORY = "M";
	private static final Logger LOGGER = LoggerFactory.getLogger(FactureValidator.class);

	public FactureValidator(String directory) throws IOException {
		super();
		try(InputStream input = getClass().getResourceAsStream("/" + directory + ".properties")) {			
			Objects.requireNonNull(input, "Can not load properties file : " + directory);
			p.load(input);
		}
	}

	public boolean validateMandatory(Facture facture) throws NoSuchFieldException, SecurityException,
			NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		for (Object metadata : p.keySet()) {
			if (p.get(metadata).equals(METADATA_MANDATORY)) {
				LOGGER.debug(metadata.toString() + ": mention obligatoire");

				String[] attributs = metadata.toString().split("\\.");
				Field attribut = facture.getClass()
						.getDeclaredField(attributs[1].substring(0, 1).toLowerCase() + attributs[1].substring(1));
				if (attribut != null) {

					Method getAttribut = facture.getClass().getMethod("get" + attributs[1]);
					Object subClass = getAttribut.invoke(facture);
					if (attributs.length > 2) {
						Field attribut2 = subClass.getClass().getDeclaredField(
								attributs[2].substring(0, 1).toLowerCase() + attributs[2].substring(1));
						if (attribut2 != null) {
							getAttribut = subClass.getClass().getMethod("get" + attributs[2]);
							subClass = getAttribut.invoke(subClass);
						}
					}
					if (subClass == null) {
						LOGGER.debug(metadata.toString() + ": present dans le xml");
						return false;
					}
					LOGGER.debug(metadata.toString() + ": absent dans le xml");
				}
			}

		}

		return true;
	}

}

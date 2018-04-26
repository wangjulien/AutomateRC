package com.telino.automateRC.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HttpRequRespExtractor {

	private HttpRequRespExtractor() {
		super();
	}

	@SuppressWarnings("unchecked")
	public static Map<String, String> lecture(HttpServletRequest request) throws IOException {
		try (InputStream reqStream = request.getInputStream();
				ObjectInputStream inputFromApplet = new ObjectInputStream(reqStream)) {
			return (Map<String, String>) inputFromApplet.readObject();
		} catch (ClassNotFoundException e) {
			return Collections.emptyMap();
		} 
	}

	public static void ecriture(HttpServletResponse response, Object mareponse) throws IOException {
		try (OutputStream outstr = response.getOutputStream();
				ObjectOutputStream oos = new ObjectOutputStream(outstr);) {
			oos.writeObject(mareponse);
			oos.flush();
		}
	}
}

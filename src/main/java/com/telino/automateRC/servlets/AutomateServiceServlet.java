package com.telino.automateRC.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.telino.automateRC.listener.StartStopListener;
import com.telino.automateRC.service.ImportFilesService;
import com.telino.automateRC.util.HttpRequRespExtractor;

@WebServlet("/AutomateServiceServlet")
public class AutomateServiceServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AutomateServiceServlet.class);

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// Test la commande "importfiles"

		Map<String, String> input = HttpRequRespExtractor.lecture(req);
		
		LOGGER.info("Appel recu : {} ", input);

		if ("importfiles".equals((String)input.get("command"))) {
			for (ImportFilesService importFilesService : StartStopListener.importFilesServices) {
				LOGGER.info("Importer fichiers du repertoire {} ", importFilesService.getFolderPath());
				importFilesService.checkAndImportFiles();
			}
		}

		Map<String, String> mapResultat = new HashMap<>();
		mapResultat.put("codeRetour", "OK");
		mapResultat.put("message", "");
		HttpRequRespExtractor.ecriture(resp, mapResultat);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

}

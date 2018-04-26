package com.telino.automateRC.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class RemoteCall {
	/**
	 * 
	 */
	private URLConnection con = null;
	private ObjectOutputStream oos;
	private OutputStream outstream;
	static char pipe = '|';
	static char fermant = ']';
	static char ouvrant = '[';
	private String limit = "500";
	private String maxTime = "5";

	public Object Call(Object request, String tomcatlocation, String nomBase) {
		Object result = null;

		try {
			// if (tomcatlocation.startsWith("https:") &&
			// !GWT.getHostPageBaseURL().contains("gwt.codesvr"))
			// con = (HttpsURLConnection) getSSLServletConnection(tomcatlocation,nomBase);
			// else
			con = getServletConnection(tomcatlocation, nomBase);
			con.connect();
			outstream = con.getOutputStream();
			oos = new ObjectOutputStream(outstream);
			oos.writeObject(request);
			oos.flush();
			oos.close();

			// receive result from servlet
			InputStream instr = con.getInputStream();
			ObjectInputStream inputFromServlet = new ObjectInputStream(instr);
			result = inputFromServlet.readObject();
			inputFromServlet.close();
			instr.close();
			return (result);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}

	private URLConnection getServletConnection(String tomcatlocation, String nomBase)
			throws MalformedURLException, IOException {
		String sep = "?";
		if (tomcatlocation.contains("?"))
			sep = "&";
		String url = tomcatlocation + sep + "id=" + Thread.currentThread().getId() + "&nomBase=" + nomBase;
		url += "&limit=" + limit + "&maxtime=" + maxTime;
		URL urlServlet = new URL(url);
		URLConnection con = urlServlet.openConnection();

		con.setDoInput(true);
		con.setDoOutput(true);
		con.setUseCaches(false);
		// con.setReadTimeout(10000);

		con.setRequestProperty("Content-Type", "application/x-java-serialized-object");

		return con;
	}

//	private HttpsURLConnection getSSLServletConnection(String tomcatlocation, String nomBase)
//			throws MalformedURLException, IOException, NoSuchAlgorithmException, KeyManagementException {
//		String sep = "?";
//		if (tomcatlocation.contains("?"))
//			sep = "&";
//		String url = tomcatlocation + sep + "id=" + Thread.currentThread().getId() + "&nomBase=" + nomBase;
//		url += "&limit=" + limit + "&maxtime=" + maxTime;
//		TrustManager[] trustAllCerts = new X509TrustManager[] { new X509TrustManager() {
//			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
//				return null;
//			}
//
//			public void checkClientTrusted(X509Certificate[] certs, String authType) {
//			}
//
//			public void checkServerTrusted(X509Certificate[] certs, String authType) {
//			}
//		} };
//		// Install the all-trusting trust manager
//		final SSLContext sc = SSLContext.getInstance("SSL");
//		sc.init(null, trustAllCerts, new java.security.SecureRandom());
//		HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
//		// Create all-trusting host name verifier
//		HostnameVerifier allHostsValid = new HostnameVerifier() {
//
//			public boolean verify(String hostname, SSLSession session) {
//				return true;
//			}
//		};
//
//		// Install the all-trusting host verifier
//		HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
//
//		URL myurl = new URL(url);
//		HttpsURLConnection con = (HttpsURLConnection) myurl.openConnection();
//
//		con.setDoInput(true);
//		con.setDoOutput(true);
//		con.setUseCaches(false);
//
//		con.setRequestProperty("Content-Type", "application/x-java-serialized-object");
//
//		return con;
//	}

	public Object Call(String request, String string, String nomBase, String limit, String maxTime) {
		this.limit = limit;
		this.maxTime = maxTime;
		return Call(request, string, nomBase);

	}

}

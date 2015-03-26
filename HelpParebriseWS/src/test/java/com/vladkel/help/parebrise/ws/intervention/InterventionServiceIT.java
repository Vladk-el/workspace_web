package com.vladkel.help.parebrise.ws.intervention;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class InterventionServiceIT {

	private static final String URL_LOCAL = "http://localhost:8080/HelpParebriseWS/rest/intervention/";

	private static final String PATH_SET = "/set/add";

	private String url;

	@Test(enabled = false)
	public void run() {

	}

	@BeforeTest(enabled = false)
	public void configure() {

	}

	@Test(enabled = true)
	public boolean addIntervention() {
		
		url = URL_LOCAL + PATH_SET;
		
		HttpClient client = null;
		HttpPost post = null;
		List<BasicNameValuePair> nameValuePairs = null;
		
		try {
			client = new DefaultHttpClient();
	        post = new HttpPost(url);
	        
	        nameValuePairs = new ArrayList<BasicNameValuePair>();
	        	nameValuePairs.add(new BasicNameValuePair("indice_intervention", "52"));
	        	nameValuePairs.add(new BasicNameValuePair("indice_client", "22"));
	        	nameValuePairs.add(new BasicNameValuePair("indice_vehicule", "9"));
	        	nameValuePairs.add(new BasicNameValuePair("date_intervention", "12/08/2014"));
	        	nameValuePairs.add(new BasicNameValuePair("date_facture", "12/08/2014"));
	        	nameValuePairs.add(new BasicNameValuePair("numero_facture", "62"));
	        	nameValuePairs.add(new BasicNameValuePair("prix_HT", "300.0"));
	        	nameValuePairs.add(new BasicNameValuePair("prix_TTC", "360.0"));
	        	nameValuePairs.add(new BasicNameValuePair("id_tva", "2"));
	        	nameValuePairs.add(new BasicNameValuePair("acompte", "0.0"));
	        	nameValuePairs.add(new BasicNameValuePair("remise", "0.0"));
	        	nameValuePairs.add(new BasicNameValuePair("franchise", "0.0"));
	        	nameValuePairs.add(new BasicNameValuePair("indice_prestation", "4"));
	        	nameValuePairs.add(new BasicNameValuePair("indice_mode_paiement", "4"));
	        	nameValuePairs.add(new BasicNameValuePair("date_sinistre", "2/08/2014"));
	        	nameValuePairs.add(new BasicNameValuePair("cause_sinistre", "Projection de cailloux"));
	        	nameValuePairs.add(new BasicNameValuePair("adresse_intervention", "33 Avenue Francois MITTERAND 02500 HIRSON"));
	        	nameValuePairs.add(new BasicNameValuePair("bon_de_commande", ""));
	        	nameValuePairs.add(new BasicNameValuePair("date_echeance", ""));
	        	nameValuePairs.add(new BasicNameValuePair("indice_contact", "1"));
	        	nameValuePairs.add(new BasicNameValuePair("assurance_impression", "Y"));
	        
	        
	        post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	        HttpResponse response = client.execute(post);
	        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
	        String line = "";
	        while ((line = rd.readLine()) != null) {
	            System.out.println(line);
	        }
	        
	        return true;

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			
		}

		return false;
	}

}

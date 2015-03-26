package com.vladkel.help.parebrise.ws.xml.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vladkel.help.parebrise.ws.model.intervention.DocumentIntervention;
import com.vladkel.help.parebrise.ws.model.intervention.Intervention;
import com.vladkel.help.parebrise.ws.persistance.InterventionORM;

@Path("/intervention")
public class InterventionService {
	
	private static final Logger log = LoggerFactory.getLogger(InterventionService.class);
	
	private InterventionORM orm = new InterventionORM();

	
	@GET
	@Path("/get/{id}")
	@Produces("application/xml")
	public DocumentIntervention getIntervention(@PathParam("id") final int id){
		DocumentIntervention document = new DocumentIntervention();
		Intervention intervention = orm.getIntervention(id);
		document.addIntervention(intervention);
		document.addIntervention(intervention);
		document.addIntervention(intervention);
		return document;
	}
	
	
	@GET
	@Path("/getInter/{id}")
	@Produces("application/xml")
	public Intervention getInterventionSimple(@PathParam("id") final int id){
		Intervention intervention = orm.getIntervention(id);		
		return intervention;
	}
		
	/**
	 * 
	 * For manual tests
	 */
	@GET
	@Path("/getManualFill")
	@Produces("application/xml")
	public Intervention getManualFill(){
		int id = 52;
		Intervention intervention = new Intervention();
		intervention.setIndice_intervention(id);
		intervention.setIndice_client(22);
		intervention.setIndice_vehicule(9);
		intervention.setDate_intervention("12/08/2014");
		intervention.setDate_facture("12/08/2014");
		intervention.setNumero_facture("62");
		intervention.setPrix_HT(300.0);
		intervention.setPrix_TTC(360.0);
		intervention.setId_tva(2);
		intervention.setAcompte(0.0);
		intervention.setRemise(0.0);
		intervention.setFranchise(0.0);
		intervention.setIndice_prestation(4);
		intervention.setIndice_mode_paiement(4);
		intervention.setDate_sinistre("12/08/2014");
		intervention.setCause_sinistre("Projection de cailloux");
		intervention.setAdresse_intervention("33 Avenue Francois MITTERAND 02500 HIRSON");
		intervention.setBon_de_commande("");
		intervention.setDate_echeance("");
		intervention.setIndice_contact(1);
		intervention.setAssurance_impression("Y");
		
		log.info("getManualFill ==> indice intervention : ", intervention.getIndice_intervention());
		log.info(intervention.toString());
		
		return intervention;
	}
	
}

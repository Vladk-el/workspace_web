package com.vladkel.help.parebrise.ws.xml.service;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.jboss.resteasy.annotations.Form;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vladkel.help.parebrise.ws.abstracts.model.DocumentAbstract;
import com.vladkel.help.parebrise.ws.model.error.DocumentError;
import com.vladkel.help.parebrise.ws.model.error.XMLError;
import com.vladkel.help.parebrise.ws.model.intervention.DocumentIntervention;
import com.vladkel.help.parebrise.ws.model.intervention.Intervention;
import com.vladkel.help.parebrise.ws.persistance.ORM;

@Path("/intervention")
public class InterventionService {
	
	private static final Logger log = LoggerFactory.getLogger(InterventionService.class);
	
	private static final String matcher = "indice_intervention";
	
	private ORM<Intervention> orm = new ORM<Intervention>(Intervention.class, matcher);

	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GET
	@Path("/get/{id}")
	@Produces("application/xml")
	public DocumentAbstract getIntervention(@PathParam("id") final int id){
		DocumentAbstract document;
		
		try {
			document = new DocumentIntervention();
			Intervention intervention = (Intervention) orm.get(id);
			document.addObject(intervention);
		} catch(Exception e) {
			log.error("Error on GET INTERVENTION : " + e.getClass().getSimpleName());
			document = new DocumentError();
			XMLError error = new XMLError(e);
			document.addObject(error);
		}
		return document;
	}
	
	@GET
	@Path("/get/all")
	@Produces("application/xml")
	public DocumentIntervention getInterventions(){
		DocumentIntervention document = new DocumentIntervention();
		List<Intervention> interventions = orm.getAll();
		document.setObjets(interventions);
		return document;
	}
	
	@POST
	@Path("/set/add")
	public void addIntervention(@Form Intervention intervention){
		log.info(intervention.toString());
		if(orm.add(intervention))
			log.info("Intervention with id " + intervention.getIndice_intervention() + " added");
	}
	
	@POST
	@Path("/set/update")
	public void updateIntervention(@Form Intervention intervention){
		log.info(intervention.toString());
		if(orm.update(intervention))
			log.info("Intervention with id " + intervention.getIndice_intervention() + " updated");
	}
	
	@POST
	@Path("/set/are/you/sure/that/you/really/want/to/remove/this/intervention/{id}")
	public void removeIntervention(@PathParam("id") final int id){
		log.info("Intervention to remove : " + id);
		if(orm.remove(id))
			log.info("Intervention with id " + id + " removed");
	}
	
	
	@POST
	@Path("/getInter/{id}")
	@Produces("application/xml")
	public Intervention getInterventionSimple(@PathParam("id") final int id){
		Intervention intervention = null;
		
		try {
			intervention = (Intervention) orm.get(id);	
		} catch (Exception e) {
			
		}
			
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

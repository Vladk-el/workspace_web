package com.vladkel.help.parebrise.ws.xml.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.vladkel.help.parebrise.ws.model.Intervention;
import com.vladkel.help.parebrise.ws.persistance.InterventionORM;

@Path("/xml/intervention")
public class InterventionService {
	
	private InterventionORM orm = new InterventionORM();

	@GET
	@Path("/get")
	@Produces("application/xml")
	public Intervention getIntervention(){
		int id = 52;
		Intervention intervention = orm.getIntervention(id);
		return intervention;
	}
}

package com.vladkel.help.parebrise.ws.model.intervention;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "document")
public class DocumentIntervention {

	private Interventions interventions;
	
	public DocumentIntervention(){
		interventions = new Interventions();
	}

	@XmlElement
	public Interventions getInterventions() {
		return interventions;
	}

	public void setInterventions(Interventions interventions) {
		this.interventions = interventions;
	}
	
	public void addIntervention(Intervention intervention){
		interventions.addIntervention(intervention);
	}
	
}

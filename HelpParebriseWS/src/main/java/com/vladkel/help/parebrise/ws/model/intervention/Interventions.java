package com.vladkel.help.parebrise.ws.model.intervention;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "interventions")
public class Interventions {

	private List<Intervention> interventions;
	
	public Interventions(){
		interventions = new ArrayList<Intervention>();
	}
	
	@XmlElement(name = "intervention")
	public List<Intervention> getInterventions() {
		return interventions;
	}

	public void setInterventions(List<Intervention> interventions) {
		this.interventions = interventions;
	}
	
	public void addIntervention(Intervention intervention){
		interventions.add(intervention);
	}
	
}

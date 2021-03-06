package com.vladkel.help.parebrise.ws.model.intervention;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.vladkel.help.parebrise.ws.abstracts.model.DocumentAbstract;

@XmlRootElement(name = "document")
public class DocumentIntervention extends DocumentAbstract<Intervention>{

	@XmlElementWrapper(name = "interventions")
	@XmlElement(name = "intervention")
	public List<Intervention> getInterventions() {
		return getObjects();
	}
	
}

package com.vladkel.help.parebrise.ws.model.error;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import com.vladkel.help.parebrise.ws.model.error.XMLError;

import com.vladkel.help.parebrise.ws.abstracts.model.DocumentAbstract;

@XmlRootElement(name = "document")
public class DocumentError extends DocumentAbstract<XMLError>{

	@XmlElementWrapper(name = "errors")
	@XmlElement(name = "error")
	public List<XMLError> getErrors() {
		return getObjects();
	}
}

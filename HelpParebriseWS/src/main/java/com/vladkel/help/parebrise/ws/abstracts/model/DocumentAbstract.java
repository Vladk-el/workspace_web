package com.vladkel.help.parebrise.ws.abstracts.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "document")
public abstract class DocumentAbstract<T> {

	protected List<T> objects;
	
	public DocumentAbstract(){
		objects = new ArrayList<T>();
	}

	/**
	 * @XmlElementWrapper(name = "Ts")
	 * @XmlElement(name = "T")
	 */
	public List<T> getObjects() {
		return objects;
	}

	public void setObjets(List<T> objects) {
		this.objects = objects;
	}
	
	public void addObject(T object){
		objects.add(object);
	}
	
}

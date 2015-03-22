package com.vladkel.files.upload.beans;

public class Fichier {

	private String name;
	
	private String description;
	
	
	public Fichier(){
		super();
	}
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}

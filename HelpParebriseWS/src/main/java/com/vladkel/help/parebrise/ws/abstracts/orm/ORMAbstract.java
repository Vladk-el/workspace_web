package com.vladkel.help.parebrise.ws.abstracts.orm;

import java.util.List;

import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ORMAbstract <T> {

	protected static final Logger log = LoggerFactory.getLogger(ORMAbstract.class);

	protected static final String PATH = "com/vladkel/help/parebrise/ws/hibernate/";
	
	protected static final String CONFIG_GLOBAL_FILE_NAME = "hibernate.cfg.xml";
	
	protected static final String CONFIG_FILE_NAME = "conf/Intervention.hbm.xml";
	
	protected Configuration global;
	
	protected Configuration config;
	
	protected Class<T> clazz;
	
	protected String matcher;
	
	public ORMAbstract(){
		
		global = new Configuration();
		global.configure(PATH + CONFIG_GLOBAL_FILE_NAME);
		
		config = new Configuration();
		
		ClassLoader classLoader = this.getClass().getClassLoader();
		config.addFile(classLoader.getResource(PATH + CONFIG_FILE_NAME).getFile());
		
	}
	
	public abstract T get(int id);
	
	public abstract List<T> getAll();
	
	public abstract boolean add(T object);
	
	public abstract boolean update(T object);
	
	public abstract boolean remove(int id);
}

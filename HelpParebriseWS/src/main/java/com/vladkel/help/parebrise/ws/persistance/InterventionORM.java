package com.vladkel.help.parebrise.ws.persistance;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.vladkel.help.parebrise.ws.model.Intervention;

public class InterventionORM {

	private static final String CONFIG_FILE_NAME= "Intervention.hbm.xml";
	
	private Configuration config;
	
	public InterventionORM(){
		config = new Configuration();
		
		ClassLoader classLoader = getClass().getClassLoader();
		
		
		System.out.println("CONFIG FILE : " + classLoader.getResource(CONFIG_FILE_NAME));
		
		config.addFile(classLoader.getResource(CONFIG_FILE_NAME).getFile());
		
	}
	
	public Intervention getIntervention(int id){
		SessionFactory sessionFactory = config.buildSessionFactory();
		Session session = sessionFactory.openSession();
		Intervention intervention = null;
		
		try {
			intervention = (Intervention) session.load(Intervention.class, new Integer(id));
			System.out.println("Intervention id = " + intervention.getIndice_intervention());
		} finally {
			session.close();
		}
		
		sessionFactory.close();
		return intervention;
	}
}

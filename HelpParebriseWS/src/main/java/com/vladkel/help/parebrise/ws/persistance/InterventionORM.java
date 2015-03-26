package com.vladkel.help.parebrise.ws.persistance;

import java.util.List;

import org.hibernate.service.ServiceRegistry;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vladkel.help.parebrise.ws.model.intervention.Intervention;
import com.vladkel.help.parebrise.ws.utils.hibernate.InitializeAndUnproxy;

public class InterventionORM {
	
	private static final Logger log = LoggerFactory.getLogger(InterventionORM.class);

	private static final String PATH = "com/vladkel/help/parebrise/ws/hibernate/";
	
	private static final String CONFIG_GLOBAL_FILE_NAME = "hibernate.cfg.xml";
	
	private static final String CONFIG_FILE_NAME = "conf/Intervention.hbm.xml";
	
	private Configuration global;
	
	private Configuration config;
	
	public InterventionORM(){
		
		global = new Configuration();
		global.configure(PATH + CONFIG_GLOBAL_FILE_NAME);
		
		config = new Configuration();
		
		ClassLoader classLoader = this.getClass().getClassLoader();
		config.addFile(classLoader.getResource(PATH + CONFIG_FILE_NAME).getFile());
		
	}
	
	public Intervention getIntervention(int id){
		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(global.getProperties()).build();
		SessionFactory sessionFactory = config.buildSessionFactory(serviceRegistry);
		Session session = sessionFactory.openSession();
		Intervention intervention = null;
				
		try {
			intervention = InitializeAndUnproxy.initializeAndUnproxy(
							(Intervention) session.load(Intervention.class, new Integer(id))
						   );
			log.info("GET intervention n° ", intervention.getIndice_intervention());
		} catch(Exception e) {
			log.error("Error on GET INTERVENTION : ", e);
		} finally {
			session.close();
		}
		
		sessionFactory.close();
		return intervention;
	}
	
	public List<Intervention> getInterventions(){
		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(global.getProperties()).build();
		SessionFactory sessionFactory = config.buildSessionFactory(serviceRegistry);
		Session session = sessionFactory.openSession();
		List<Intervention> interventions = null;
		
		try{
			interventions = session.createCriteria(Intervention.class).list();
		} catch(Exception e) {
			log.error("Error on GET INTERVENTIONS : ", e);
		} finally {
			session.close();
		}
		
		sessionFactory.close();
		return interventions;
	}
}

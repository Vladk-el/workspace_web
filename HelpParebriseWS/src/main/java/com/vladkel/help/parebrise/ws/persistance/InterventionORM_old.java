package com.vladkel.help.parebrise.ws.persistance;

import java.util.List;

import org.hibernate.service.ServiceRegistry;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vladkel.help.parebrise.ws.model.intervention.Intervention;
import com.vladkel.help.parebrise.ws.utils.hibernate.InitializeAndUnproxy;

public class InterventionORM_old {
	
	private static final Logger log = LoggerFactory.getLogger(InterventionORM_old.class);

	private static final String PATH = "com/vladkel/help/parebrise/ws/hibernate/";
	
	private static final String CONFIG_GLOBAL_FILE_NAME = "hibernate.cfg.xml";
	
	private static final String CONFIG_FILE_NAME = "conf/Intervention.hbm.xml";
	
	private Configuration global;
	
	private Configuration config;
	
	public InterventionORM_old(){
		
		global = new Configuration();
		global.configure(PATH + CONFIG_GLOBAL_FILE_NAME);
		
		config = new Configuration();
		
		ClassLoader classLoader = this.getClass().getClassLoader();
		config.addFile(classLoader.getResource(PATH + CONFIG_FILE_NAME).getFile());
		
	}
	
	public Intervention get(int id){
		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(global.getProperties()).build();
		SessionFactory sessionFactory = config.buildSessionFactory(serviceRegistry);
		Session session = sessionFactory.openSession();
		Intervention intervention = null;
				
		try {
			intervention = InitializeAndUnproxy.initializeAndUnproxy(
							(Intervention) session.load(Intervention.class, new Integer(id))
						   );
			log.info("GET intervention with id " + intervention.getIndice_intervention());
		} catch(Exception e) {
			log.error("Error on GET INTERVENTION : ", e);
		} finally {
			session.close();
		}
		
		sessionFactory.close();
		return intervention;
	}
	
	public List<Intervention> getAll(){
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
	
	public boolean addIntervention(Intervention intervention){
		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(global.getProperties()).build();
		SessionFactory sessionFactory = config.buildSessionFactory(serviceRegistry);
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		
		try {
			transaction = session.beginTransaction();
			session.save(intervention);
			session.flush();
			transaction.commit();
			return true;
		} catch (Exception e) {
			if(transaction != null){
				transaction.rollback();
			}
			log.error("Error on ADD INTERVENTION : ", e);
		} finally {
			session.close();
		}
		
		sessionFactory.close();
		return false;
	}
	
	public boolean update(Intervention intervention){
		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(global.getProperties()).build();
		SessionFactory sessionFactory = config.buildSessionFactory(serviceRegistry);
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		
		try {
			transaction = session.beginTransaction();
			session.update(intervention);
			session.flush();
			transaction.commit();
			return true;
		} catch (Exception e) {
			if(transaction != null){
				transaction.rollback();
			}
			log.error("Error on UPDATE INTERVENTION : ", e);
		} finally {
			session.close();
		}
		
		sessionFactory.close();
		return false;
	}
	
	public boolean remove(int id){
		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(global.getProperties()).build();
		SessionFactory sessionFactory = config.buildSessionFactory(serviceRegistry);
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		
		try {
			transaction = session.beginTransaction();
			Query query = session.createQuery("from intervention i where i.indice_intervention = :id");
			query.setInteger("id", id);
			session.delete(query);
			session.flush();
			transaction.commit();
			return true;
		} catch (Exception e) {
			if(transaction != null){
				transaction.rollback();
			}
			log.error("Error on UPDATE INTERVENTION : ", e);
		} finally {
			session.close();
		}
		
		sessionFactory.close();
		return false;
	}
}

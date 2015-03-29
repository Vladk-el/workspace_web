package com.vladkel.help.parebrise.ws.persistance;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

import com.vladkel.help.parebrise.ws.abstracts.orm.ORMAbstract;
import com.vladkel.help.parebrise.ws.model.intervention.Intervention;
import com.vladkel.help.parebrise.ws.utils.hibernate.InitializeAndUnproxy;

public class ORMIntervention extends ORMAbstract <Intervention> {

	public ORMIntervention(){
		super();
	}
	
	@Override
	public Intervention get(int id) {
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

	@SuppressWarnings("unchecked")
	@Override
	public List<Intervention> getAll() {
		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(global.getProperties()).build();
		SessionFactory sessionFactory = config.buildSessionFactory(serviceRegistry);
		Session session = sessionFactory.openSession();
		List<Intervention> interventions = null;
		
		try{
			interventions = session
							.createCriteria(Intervention.class)
							.list();
		} catch(Exception e) {
			log.error("Error on GET ALL INTERVENTIONS : ", e);
		} finally {
			session.close();
		}
		
		sessionFactory.close();
		return interventions;
	}

	@Override
	public boolean add(Intervention intervention) {
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

	@Override
	public boolean update(Intervention intervention) {
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

	@Override
	public boolean remove(int id) {
		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(global.getProperties()).build();
		SessionFactory sessionFactory = config.buildSessionFactory(serviceRegistry);
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		
		try {
			transaction = session.beginTransaction();
			Query query = session.createQuery("delete from Intervention where indice_intervention = :id");
			query.setInteger("id", id);
			query.executeUpdate();
			session.flush();
			transaction.commit();
			return true;
		} catch (Exception e) {
			if(transaction != null){
				transaction.rollback();
			}
			log.error("Error on REMOVE INTERVENTION : ", e);
		} finally {
			session.close();
		}
		
		sessionFactory.close();
		return false;
	}

}

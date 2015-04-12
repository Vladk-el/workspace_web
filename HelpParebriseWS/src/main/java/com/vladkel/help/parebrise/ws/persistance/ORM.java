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

public class ORM<T> extends ORMAbstract<T>{

	public ORM(Class<T> clazz, String matcher){
		this.clazz = clazz;
		this.matcher = matcher;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public T get(int id) {
		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(global.getProperties()).build();
		SessionFactory sessionFactory = config.buildSessionFactory(serviceRegistry);
		Session session = sessionFactory.openSession();
		T t = null;
				
		try {
			t = InitializeAndUnproxy.initializeAndUnproxy(
							(T) session.load(clazz, new Integer(id))
						   );
		} catch(Exception e) {
			log.error("Error on GET " + clazz.toString() + " : ", e);
		} finally {
			session.close();
		}
		
		sessionFactory.close();
		return t;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> getAll() {
		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(global.getProperties()).build();
		SessionFactory sessionFactory = config.buildSessionFactory(serviceRegistry);
		Session session = sessionFactory.openSession();
		List<T> list = null;
		
		try{
			list = session
						  .createCriteria(Intervention.class)
						  .list();
		} catch(Exception e) {
			log.error("Error on GET ALL " + clazz.toString() + " : ", e);
		} finally {
			session.close();
		}
		
		sessionFactory.close();
		return list;
	}

	@Override
	public boolean add(T object) {
		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(global.getProperties()).build();
		SessionFactory sessionFactory = config.buildSessionFactory(serviceRegistry);
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		
		try {
			transaction = session.beginTransaction();
			session.save(object);
			session.flush();
			transaction.commit();
			return true;
		} catch (Exception e) {
			if(transaction != null){
				transaction.rollback();
			}
			log.error("Error on ADD " + clazz.toString() + " : ", e);
		} finally {
			session.close();
		}
		
		sessionFactory.close();
		return false;
	}

	@Override
	public boolean update(T object) {
		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(global.getProperties()).build();
		SessionFactory sessionFactory = config.buildSessionFactory(serviceRegistry);
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		
		try {
			transaction = session.beginTransaction();
			session.update(object);
			session.flush();
			transaction.commit();
			return true;
		} catch (Exception e) {
			if(transaction != null){
				transaction.rollback();
			}
			log.error("Error on UPDATE " + clazz.toString() + " : ", e);
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
			Query query = session.createQuery("delete from " + clazz.getSimpleName() + " where " + matcher + " = :id");
			query.setInteger("id", id);
			query.executeUpdate();
			session.flush();
			transaction.commit();
			return true;
		} catch (Exception e) {
			if(transaction != null){
				transaction.rollback();
			}
			log.error("Error on REMOVE " + clazz.toString() + " : ", e);
		} finally {
			session.close();
		}
		
		sessionFactory.close();
		return false;
	}

}

package com.vladkel.help.parebrise.ws.utils.hibernate;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Eliott
 *	Allow to make real objects with hibernate requests returns
 */
public class InitializeAndUnproxy {
	
	private static final Logger log = LoggerFactory.getLogger(InitializeAndUnproxy.class);

	public static <T> T initializeAndUnproxy(T entity) {
		if (entity == null) {
		    throw new 
		       NullPointerException("Entity passed for initialization is null");
		}
		Hibernate.initialize(entity);
		if (entity instanceof HibernateProxy) {
		    entity = (T) ((HibernateProxy) entity).getHibernateLazyInitializer().getImplementation();
		}
		return entity;
	}
	
}

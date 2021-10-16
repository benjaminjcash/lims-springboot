package com.mycompany.frs_maven.service.hibernate_svc;

import org.apache.logging.log4j.LogManager;

import org.apache.logging.log4j.Logger;
import org.hibernate.*;
import org.hibernate.cfg.*;

public abstract class BaseSvcHibernateImpl {
	private static SessionFactory sessionFactory = null;
	static private Logger logger = LogManager.getLogger();
	
	private static SessionFactory getSessionFactory() {
		if(sessionFactory == null) {
			try {
				sessionFactory = new Configuration().configure().buildSessionFactory();
			}
			catch(Exception e) {
				logger.error(e.getMessage());
			}
		}
		return sessionFactory;
	}
	
	protected static Session getSession() {
		SessionFactory factory = getSessionFactory();
		return factory != null ? factory.openSession() : null;
	}
}
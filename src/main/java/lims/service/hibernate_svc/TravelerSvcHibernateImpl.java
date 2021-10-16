package com.mycompany.frs_maven.service.hibernate_svc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.mycompany.frs_maven.domain.Traveler;
import com.mycompany.frs_maven.exceptions.RecordNotFoundException;
import com.mycompany.frs_maven.service.ITravelerSvc;
import com.mycompany.frs_maven.service.StringBuilder;

public class TravelerSvcHibernateImpl extends BaseSvcHibernateImpl implements ITravelerSvc {
	static private Logger logger = LogManager.getLogger();
	
	public Traveler fetchProfile(String username) {
		Traveler traveler = new Traveler();
		try {
			Session session = getSession();
			Transaction transaction = session.beginTransaction();
			CriteriaBuilder cb = session.getCriteriaBuilder();
			CriteriaQuery<Traveler> cq = cb.createQuery(Traveler.class);
			Root<Traveler> model = cq.from(Traveler.class);
			cq.where(cb.equal(model.get("username"), username));
			TypedQuery<Traveler> tq = session.createQuery(cq);
			traveler = tq.getSingleResult();
			transaction.commit();
			session.close();
		}
		catch(Exception e) {
			logger.error(e.getMessage());
		}
		return traveler;
	}

	public boolean createProfile(Traveler traveler) throws IOException, ClassNotFoundException {
		Boolean success = false;
		try {
			Session session = getSession();
			Transaction transaction = session.beginTransaction();
			session.save(traveler);
			transaction.commit();
			session.close();
			success = true;
		}
		catch(Exception e) {
			logger.error(e.getMessage());
		}
		return success;
	}

	public boolean deleteProfile(String username) {
		Boolean success = false;
		try {
			Session session = getSession();
			Transaction transaction = session.beginTransaction();
			Traveler traveler = session.load(Traveler.class, username);
			session.delete(traveler);
			transaction.commit();
			session.close();
			success = true;
		}
		catch(Exception e) {
			logger.error(e.getMessage());
		}
		return success;
	}

	public boolean updateProfile(Traveler traveler) throws RecordNotFoundException {
		// fetch traveler from db, hydrate null values in traveler to update
		Traveler fetchedTraveler = this.fetchProfile(traveler.getUsername());
		if(traveler.getName() == null) { traveler.setName(fetchedTraveler.getName()); }
		if(traveler.getAddress() == null) { traveler.setAddress(fetchedTraveler.getAddress()); }
		if(traveler.getPassword() == null) { traveler.setPassword(fetchedTraveler.getPassword()); }
		if(traveler.getCreditCardNumber() == null) { traveler.setCreditCardNumber(fetchedTraveler.getCreditCardNumber()); }
		if(traveler.getExpirationDate() == null) { traveler.setExpirationDate(fetchedTraveler.getExpirationDate()); }
		
		Boolean success = false;
		try {
			Session session = getSession();
			Transaction transaction = session.beginTransaction();
			session.update(traveler);
			transaction.commit();
			session.close();
			success = true;
		}
		catch(Exception e) {
			logger.error(e.getMessage());
		}
		return success;
	}

	public ArrayList<Traveler> fetchAllProfiles() {
		ArrayList<Traveler> travelerList = new ArrayList<Traveler>();
		try {
			Session session = getSession();
			Transaction transaction = session.beginTransaction();
			CriteriaBuilder cb = session.getCriteriaBuilder();
			CriteriaQuery<Traveler> cq = cb.createQuery(Traveler.class);
			cq.from(Traveler.class);
			travelerList = (ArrayList<Traveler>) session.createQuery(cq).getResultList();
			transaction.commit();
			session.close();
		}
		catch(Exception e) {
			logger.error(e.getMessage());
		}
		return travelerList;
	}

	public void printAllTravelers() throws IOException, ClassNotFoundException, RecordNotFoundException {
		ArrayList<Traveler> allTravelers = this.fetchAllProfiles();
		
		// define lambda expression
		StringBuilder builder = (String label, String value) -> {
			String str = label + ". " + value;
			return str;
		};
		
		// build list of usernames
		ArrayList<String> usernames = new ArrayList<String>();
		for(int i = 0; i < allTravelers.size(); i++) {
			Traveler traveler = allTravelers.get(i);
			usernames.add(traveler.getUsername());
		}
		
		// sort list
		Stream<String> stream = usernames.stream().sorted();
		List<String> list = stream.collect(Collectors.toList());
		
		// print list
		for(int i = 0; i < list.size(); i++) {
			String label = String.valueOf(i + 1);
			logger.info(builder.build(label, list.get(i)));
		}
	}

}

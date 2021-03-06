package com.soundtrip.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.soundtrip.dto.EventBanner;

/**
 * @author Dheeraj
 *
 */
@Repository
@Transactional
public class EventBannerDaoImpl implements EventBannerDao, Serializable {
	
	/**
	 * Generated Serial Id Version
	 */
	private static final long serialVersionUID = -854291642729262223L;

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public EventBanner getEventBannerById(int id) {
		return (EventBanner) sessionFactory.getCurrentSession().byId(EventBanner.class).load(id);
	}
	
	@Override
	public EventBanner saveEventBanner(EventBanner eventBanner) {
		String index = eventBanner.getName();
		
		Query query = sessionFactory.getCurrentSession().getNamedQuery("eventWithName");
		query.setParameter("name", index);
		List<?> results = query.list();
		if(results != null && !results.isEmpty() && results.get(0) != null && ((EventBanner) results.get(0)).getId() > 0) {
			EventBanner eventBannerExisting = (EventBanner) results.get(0);
			eventBannerExisting.setName(eventBanner.getName());
			eventBannerExisting.setUrl(eventBanner.getUrl());
			eventBannerExisting.setImage(eventBanner.getImage());
			sessionFactory.getCurrentSession().update(eventBannerExisting);
		} else {		
			if(eventBanner.getId() == 0) {
				eventBanner = (EventBanner) sessionFactory.getCurrentSession().merge(eventBanner);
			} else if(eventBanner.getId() > 0) {
				sessionFactory.getCurrentSession().update(eventBanner);
			}
		}
		return eventBanner;
	}

	/*
	 * Get all persons from database
	 * 
	 * @see com.soundtrip.dao.EventDao#getAllPersons()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<EventBanner> getAllEventBanners() {
		return (List<EventBanner>) sessionFactory.getCurrentSession().getNamedQuery("allEventBanners").list();		
	}

	@Override
	public void deleteEventBanner(int id) {
		EventBanner eventBanner = (EventBanner) sessionFactory.getCurrentSession().get(EventBanner.class, id);
		if (eventBanner != null) {
			sessionFactory.getCurrentSession().delete(eventBanner);
		}
	}

	/**
	 * @param sessionFactory
	 *            the sessionFactory to set
	 */
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}

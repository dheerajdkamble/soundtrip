package com.soundtrip.dao;

import java.io.Serializable;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.soundtrip.dto.City;
import com.soundtrip.dto.Event;
import com.soundtrip.service.impl.EventServiceImpl;

/**
 * @author Dheeraj
 *
 */
@Repository
@Transactional
public class EventDaoImpl implements EventDao, Serializable {
	
	/**
	 * Generated Serial Id Version
	 */
	private static final long serialVersionUID = -854291642729262223L;

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public Event getEventById(int id) {
		return (Event) sessionFactory.getCurrentSession().byId(Event.class).load(id);
	}
	
	@Override
	public Event saveEvent(Event event) {
		if(event.getId() == 0) {
			event = (Event) sessionFactory.getCurrentSession().merge(event);
		} else if(event.getId() > 0) {
			sessionFactory.getCurrentSession().update(event);
		}
		return event;
	}

	/*
	 * Get all persons from database
	 * 
	 * @see com.soundtrip.dao.EventDao#getAllPersons()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Event> getAllEvents() {
		return (List<Event>) sessionFactory.getCurrentSession().getNamedQuery("allEventsSortedByEventDate").list();		
	}

	@Override
	public void deleteEvent(int id) {
		Event event = (Event) sessionFactory.getCurrentSession().get(Event.class, id);
		if (event != null) {
			sessionFactory.getCurrentSession().delete(event);
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

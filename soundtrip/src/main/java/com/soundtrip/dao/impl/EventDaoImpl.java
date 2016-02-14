package com.soundtrip.dao.impl;

import java.io.Serializable;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.soundtrip.dao.EventDao;
import com.soundtrip.dto.Event;

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
	public void saveEvent(Event event) {
		sessionFactory.getCurrentSession().merge(event);		
	}

	/**
	 * @param sessionFactory the sessionFactory to set
	 */
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}

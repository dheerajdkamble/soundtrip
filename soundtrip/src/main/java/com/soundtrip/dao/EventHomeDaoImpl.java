package com.soundtrip.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.soundtrip.dto.Event;
import com.soundtrip.dto.EventRollingImage;

/**
 * @author Dheeraj
 *
 */
@Repository
@Transactional
public class EventHomeDaoImpl implements EventHomeDao, Serializable {

	/**
	 * Generated Serial Id Version
	 */
	private static final long serialVersionUID = -854291642729262223L;

	@Autowired
	SessionFactory sessionFactory;

	

	/*
	 * Get all persons from database
	 * 
	 * @see com.soundtrip.dao.EventDao#getAllPersons()
	 */
	@Override
	public List<EventRollingImage> getAllEventsRollingImages() {
		return (List<EventRollingImage>) sessionFactory.getCurrentSession().createCriteria(EventRollingImage.class).list();
	}
	
	/**
	 * @param sessionFactory
	 *            the sessionFactory to set
	 */
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}

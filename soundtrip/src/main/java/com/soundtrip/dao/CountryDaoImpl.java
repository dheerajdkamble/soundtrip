package com.soundtrip.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.soundtrip.dto.Country;

/**
 * @author Dheeraj
 * Dao Implementaion class for Countries
 */
@Repository
@Transactional
public class CountryDaoImpl implements CountryDao, Serializable {

	/**
	 * Generated serial id version
	 */
	private static final long serialVersionUID = -6870245936768997462L;
	
	@Autowired
	SessionFactory sessionFactory;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Country> getAllCountries() {
		return (List<Country>) sessionFactory.getCurrentSession().createCriteria(Country.class).list();		
	}
	
	/**
	 * @param sessionFactory
	 *            the sessionFactory to set
	 */
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}

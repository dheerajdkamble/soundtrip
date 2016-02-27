package com.soundtrip.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.soundtrip.dto.State;

/**
 * @author Dheeraj
 * Dao Implementaion class for States
 */
@Repository
@Transactional
public class StateDaoImpl implements StateDao, Serializable {
	/**
	 * Generated serial id version
	 */
	private static final long serialVersionUID = 2399971798245658820L;
	
	@Autowired
	SessionFactory sessionFactory;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<State> getStatesForCountryId(int id) {
		return (List<State>) sessionFactory.getCurrentSession().get(State.class, id);
	}

	/**
	 * @param sessionFactory
	 *            the sessionFactory to set
	 */
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}

package com.soundtrip.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.soundtrip.dto.City;
import com.soundtrip.dto.Event;

/**
 * @author Dheeraj Dao Implementaion class for Cities
 */
@Repository
@Transactional
public class CityDaoImpl implements CityDao, Serializable {
	/**
	 * Generated serial id version
	 */
	private static final long serialVersionUID = 3808131008584705310L;

	@Autowired
	SessionFactory sessionFactory;

	/**
	 * @param sessionFactory
	 *            the sessionFactory to set
	 */
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<City> getCitiesForStateId(int id) {
		Criteria cr = sessionFactory.getCurrentSession().createCriteria(City.class);
		if (id != 0) {
			cr.add(Restrictions.eq("state.id", id));
		}
		return cr.list();
	}

}

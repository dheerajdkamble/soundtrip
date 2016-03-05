package com.soundtrip.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.soundtrip.dao.CityDao;
import com.soundtrip.dto.City;
import com.soundtrip.service.CityService;

/**
 * @author Dheeraj
 *
 */
@Service
@Transactional
public class CityServiceImpl implements CityService {

	@Autowired
	CityDao cityDao;

	@Override
	public List<City> getCitiesForStateId(int id) {
		return cityDao.getCitiesForStateId(id);
	}

	@Override
	public City getCityForId(int id) {
		return cityDao.getCityForId(id);
	}
	
	/**
	 * @param cityDao the cityDao to set
	 */
	public void setCityDao(CityDao cityDao) {
		this.cityDao = cityDao;
	}

}

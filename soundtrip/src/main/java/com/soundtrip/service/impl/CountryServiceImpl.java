package com.soundtrip.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.soundtrip.dao.CountryDao;
import com.soundtrip.dto.Country;
import com.soundtrip.service.CountryService;

/**
 * @author Dheeraj
 *
 */
@Service
@Transactional
public class CountryServiceImpl implements CountryService {

	@Autowired
	CountryDao countryDao;

	@Override
	public List<Country> getAllCountries() {
		return countryDao.getAllCountries();
	}

	/**
	 * @param countryDao the countryDao to set
	 */
	public void setCountryDao(CountryDao countryDao) {
		this.countryDao = countryDao;
	}
}

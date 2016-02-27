package com.soundtrip.dao;

import java.util.List;

import com.soundtrip.dto.Country;

/**
 * @author Dheeraj
 * Dao class for countries
 */
public interface CountryDao {

	List<Country> getAllCountries();

}

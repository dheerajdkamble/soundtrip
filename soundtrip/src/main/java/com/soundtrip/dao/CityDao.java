package com.soundtrip.dao;

import java.util.List;

import com.soundtrip.dto.City;

/**
 * @author Dheeraj
 * Dao class for cities
 */
public interface CityDao {

	List<City> getCitiesForStateId(int id);

	City getCityForId(int id);

}

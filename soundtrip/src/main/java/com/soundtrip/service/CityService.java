package com.soundtrip.service;

import java.util.List;

import com.soundtrip.dto.City;

/**
 * @author Dheeraj
 * Service class for Cities
 */
public interface CityService {

	List<City> getCitiesForStateId(int id);

}

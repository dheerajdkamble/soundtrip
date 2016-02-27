package com.soundtrip.dao;

import java.util.List;

import com.soundtrip.dto.State;

/**
 * @author Dheeraj
 * Dao class for states
 */
public interface StateDao {

	List<State> getStatesForCountryId(int id);

}

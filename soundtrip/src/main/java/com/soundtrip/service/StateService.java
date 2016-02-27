package com.soundtrip.service;

import java.util.List;

import com.soundtrip.dto.State;

/**
 * @author Dheeraj
 * Service class for States
 */
public interface StateService {

	List<State> getStatesForCountryId(int id);

}

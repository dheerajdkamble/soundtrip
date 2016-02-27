package com.soundtrip.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.soundtrip.dao.StateDao;
import com.soundtrip.dto.State;
import com.soundtrip.service.StateService;

/**
 * @author Dheeraj
 *
 */
@Service
@Transactional
public class StateServiceImpl implements StateService {

	@Autowired
	StateDao stateDao;

	@Override
	public List<State> getStatesForCountryId(int id) {
		return stateDao.getStatesForCountryId(id);
	}

	/**
	 * @param stateDao the stateDao to set
	 */
	public void setStateDao(StateDao stateDao) {
		this.stateDao = stateDao;
	}	
}

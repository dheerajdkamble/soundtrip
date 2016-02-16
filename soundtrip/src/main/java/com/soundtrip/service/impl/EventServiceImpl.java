package com.soundtrip.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.soundtrip.dao.EventDao;
import com.soundtrip.dto.Event;
import com.soundtrip.service.EventService;

/**
 * @author Dheeraj
 *
 */
@Service
@Transactional
public class EventServiceImpl implements EventService {

	@Autowired
	EventDao eventDao;

	@Override
	public void addEvent(Event event) {
		eventDao.saveEvent(event);
	}

	/* 
	 * Method to gett all persons
	 * @see com.soundtrip.service.EventService#getAllPersons()
	 */
	@Override
	public List<Event> getAllEvents() {
		return eventDao.getAllEvents();
	}
	
	@Override
	public void deleteEvent(int id) {
		eventDao.deleteEvent(id);
	}
	
	/**
	 * @param eventDao the eventDao to set
	 */
	public void setEventDao(EventDao eventDao) {
		this.eventDao = eventDao;
	}


}

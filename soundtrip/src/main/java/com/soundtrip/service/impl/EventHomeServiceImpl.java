package com.soundtrip.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.soundtrip.dao.EventDao;
import com.soundtrip.dao.EventHomeDao;
import com.soundtrip.dto.Event;
import com.soundtrip.dto.EventRollingImage;
import com.soundtrip.service.EventHomeService;


/**
 * @author Admin
 *
 */



@Service
@Transactional
public class EventHomeServiceImpl implements EventHomeService {
	
	@Autowired
	EventHomeDao eventHomeDao;

	@Override
	public List<EventRollingImage> getAllEventsRollingImages() {
		System.out.println("Inside EventHomeService Impl");
		return eventHomeDao.getAllEventsRollingImages();
	}

	/**
	 * @param eventDao the eventHomeDao to set
	 */
	public void setEventHomeDao(EventHomeDao eventHomeDao) {
		this.eventHomeDao = eventHomeDao;
	}
}

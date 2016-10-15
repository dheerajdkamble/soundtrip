package com.soundtrip.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.soundtrip.dao.EventBannerDao;
import com.soundtrip.dto.EventBanner;
import com.soundtrip.service.EventBannerService;

/**
 * @author Dheeraj
 *
 */
@Service
@Transactional
public class EventBannerServiceImpl implements EventBannerService {

	@Autowired
	EventBannerDao eventBannerDao;
	
	@Override
	public EventBanner getEventBannerById(int id) {
		return eventBannerDao.getEventBannerById(id);
	}
	
	@Override
	public EventBanner saveEventBanner(EventBanner eventBanner) {
		return eventBannerDao.saveEventBanner(eventBanner);
	}

	/* 
	 * Method to gett all persons
	 * @see com.soundtrip.service.EventService#getAllPersons()
	 */
	@Override
	public List<EventBanner> getAllEventBanners() {
		return eventBannerDao.getAllEventBanners();
	}
	
	@Override
	public void deleteEventBanner(int id) {
		eventBannerDao.deleteEventBanner(id);
	}
	
	/**
	 * @param eventBannerDao the eventDao to set
	 */
	public void setEventDao(EventBannerDao eventBannerDao) {
		this.eventBannerDao = eventBannerDao;
	}
}

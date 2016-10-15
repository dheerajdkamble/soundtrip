package com.soundtrip.service;

import java.util.List;

import com.soundtrip.dto.EventBanner;

/**
 * @author Dheeraj
 *
 */
public interface EventBannerService {

	EventBanner saveEventBanner(EventBanner eventBanner);
	
	EventBanner getEventBannerById(int id);

	List<EventBanner> getAllEventBanners();

	void deleteEventBanner(int id);
}

package com.soundtrip.dao;

import java.util.List;

import com.soundtrip.dto.EventBanner;

public interface EventBannerDao {

	EventBanner saveEventBanner(EventBanner eventBanner);

	List<EventBanner> getAllEventBanners();

	void deleteEventBanner(int id);

	EventBanner getEventBannerById(int id);
}

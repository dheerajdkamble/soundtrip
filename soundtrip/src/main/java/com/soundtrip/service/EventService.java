package com.soundtrip.service;

import java.util.List;

import com.soundtrip.dto.Event;

/**
 * @author Dheeraj
 *
 */
public interface EventService {

	Event saveEvent(Event event);
	
	Event getEventById(int id);

	List<Event> getAllEvents();

	void deleteEvent(int id);
	
	void createFBEvent(Event event);
}

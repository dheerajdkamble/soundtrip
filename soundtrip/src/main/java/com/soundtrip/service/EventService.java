package com.soundtrip.service;

import java.util.List;

import com.soundtrip.dto.Event;

/**
 * @author Dheeraj
 *
 */
public interface EventService {

	void addEvent(Event event);

	List<Event> getAllEvents();

	void deleteEvent(int id);

}

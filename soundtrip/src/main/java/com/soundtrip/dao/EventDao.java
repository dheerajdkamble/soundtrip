package com.soundtrip.dao;

import java.util.List;

import com.soundtrip.dto.Event;

public interface EventDao {

	Event saveEvent(Event event);

	List<Event> getAllEvents();

	void deleteEvent(int id);
}

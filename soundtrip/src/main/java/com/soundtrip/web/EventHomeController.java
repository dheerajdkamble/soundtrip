package com.soundtrip.web;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.soundtrip.dto.City;
import com.soundtrip.dto.CityDTO;
import com.soundtrip.dto.Event;
import com.soundtrip.dto.EventDTO;
import com.soundtrip.dto.EventRollingImage;
import com.soundtrip.service.CityService;
import com.soundtrip.service.EventHomeService;
import com.soundtrip.service.EventService;

@Controller
public class EventHomeController {

	private final DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	
	@Inject
	EventHomeService eventHomeService;
	@Inject
	EventService eventService;

	@Inject
	CityService cityService;

	@RequestMapping(value = "/eventhome", method = RequestMethod.GET)
	@ResponseBody
	public List<EventRollingImage> getEventsRollingImages() {
		List<EventRollingImage> eventsRollingImages = new ArrayList<EventRollingImage>();
		eventsRollingImages = eventHomeService.getAllEventsRollingImages();
		return eventsRollingImages;
	}

	@RequestMapping(value = "/eventlisthome", method = RequestMethod.GET)
	@ResponseBody
	public List<EventDTO> getEvents() {
		List<Event> events = new ArrayList<Event>();
		events = eventService.getAllEvents();

		List<EventDTO> eventDTOs = new ArrayList<EventDTO>();
		for (Event event : events) {
			eventDTOs.add(new EventDTO(event.getId(), event.getName(), event.getDescription(), event.getAddressLine1(),
					event.getAddressLine2(), event.getArea(),
					new CityDTO(event.getCity().getId(), event.getCity().getName()), event.getState(),
					event.getPinCode(), event.getGenre(), event.getImage(), formatter.format(new Date())));
		}

		List<Event> eventsList = new ArrayList<Event>(events.size());
		for (Event event : events) {
			eventsList.add(event);
		}

		return eventDTOs;
	}

	@RequestMapping(value = "/getallcities", method = RequestMethod.GET)
	@ResponseBody
	public List<CityDTO> getCities() {
		List<City> cities = cityService.getCitiesForStateId(22);
		List<CityDTO> cityDTOs = new ArrayList<CityDTO>();
		for (City city : cities) {
			cityDTOs.add(new CityDTO(city.getId(), city.getName()));
		}
		return cityDTOs;
	}
}

package com.soundtrip.web;

import java.util.ArrayList;
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
		System.out.println("Inside getEvents");
		List<Event> events = new ArrayList<Event>();
		events = eventService.getAllEvents();

		List<EventDTO> eventDTOs = new ArrayList<EventDTO>();
		for (Event event : events) {
			
			System.out.println(">>>>>>>>>>>>>>>>>>>> Image ::::::::::::::::::::: " + event.getImage());
			eventDTOs.add(new EventDTO(event.getId(), event.getName(), event.getDescription(), event.getAddressLine1(),
					event.getAddressLine2(), event.getArea(),
					new CityDTO(event.getCity().getId(), event.getCity().getName()), event.getState(),
					event.getPinCode(), event.getGenre(), event.getImage()));
		}

		System.out.println("check the size:::" + events.size());
		List<Event> eventsList = new ArrayList<Event>(events.size());
		for (Event event : events) {

			// ByteArrayOutputStream baos=new ByteArrayOutputStream(1000);
			// try {
			// BufferedImage img=ImageIO.read(new File(event.getImage()));
			// ImageIO.write(img, "jpg", baos);
			// baos.flush();
			//
			// @SuppressWarnings("restriction")
			// String base64String =
			// com.sun.org.apache.xerces.internal.impl.dv.util.Base64.encode(baos.toByteArray());
			// event.setImage(base64String);
			// baos.close();
			// } catch (IOException e) {
			// e.printStackTrace();
			// }
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

package com.soundtrip.web;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.soundtrip.dto.Event;
import com.soundtrip.dto.ResponseMessage;
import com.soundtrip.service.EventService;

@Controller
public class EventController {

	@Inject
	EventService eventService;

	Gson gson = new Gson();

	@RequestMapping(value = "/eventmaster", method = RequestMethod.GET)
	@ResponseBody
	public List<Event> getEvents() {
		List<Event> events = new ArrayList<Event>();
		events = eventService.getAllEvents();
		return events;
	}

	@RequestMapping(value = "/eventmaster", method = RequestMethod.POST)
	@ResponseBody
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public ResponseMessage addEvent(@RequestBody String event) {
		// if (event.getName() == nul|| person.getLastName().length() <= 3) {
		// throw new IllegalArgumentException("moreThan3Chars");
		// }
		System.out.println("byte array :::::::::::: " + event);
		Event eventToSave = gson.fromJson(event, Event.class);
		System.out.println(event);
		System.out.println("Event Name : " + eventToSave.getAddressLine1());
		eventService.addEvent(eventToSave);
		return new ResponseMessage(ResponseMessage.Type.success, "eventAdded");
	}

	@RequestMapping(value = "/eventmaster/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public ResponseMessage deletePerson(@PathVariable int id) {
		eventService.deleteEvent(id);
		return new ResponseMessage(ResponseMessage.Type.success, "eventDeleted");
	}
}

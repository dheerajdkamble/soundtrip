package com.soundtrip.web;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.soundtrip.dto.EventRollingImage;
import com.soundtrip.service.EventHomeService;

@Controller
public class EventHomeController {

	@Inject
	EventHomeService eventHomeService;
	
	@RequestMapping(value = "/eventhome", method = RequestMethod.GET)
	@ResponseBody
	public List<EventRollingImage> getEvents() {
		List<EventRollingImage> eventsRollingImages = new ArrayList<EventRollingImage>();
		eventsRollingImages = eventHomeService.getAllEventsRollingImages();
		return eventsRollingImages;
	}
}

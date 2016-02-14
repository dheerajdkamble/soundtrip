package com.soundtrip.web;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.soundtrip.dto.Event;
import com.soundtrip.dto.ResponseMessage;
import com.soundtrip.service.EventService;

@Controller
public class EventController {

	@Inject
	EventService eventService;
	
	Gson gson = new Gson();
	
    @RequestMapping(value = "/event", method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public ResponseMessage addPerson(@RequestBody String event) {
//        if (event.getName() == nul|| person.getLastName().length() <= 3) {
//            throw new IllegalArgumentException("moreThan3Chars");
//        }
    	Event eventToSave = gson.fromJson(event, Event.class);
    	System.out.println(event);
    	System.out.println("Event Name : " + eventToSave.getAddressLine1());
    	eventService.addEvent(eventToSave);
        return new ResponseMessage(ResponseMessage.Type.success, "eventAdded");
    }
}

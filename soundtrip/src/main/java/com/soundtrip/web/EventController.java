package com.soundtrip.web;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.soundtrip.common.CommonConstants;
import com.soundtrip.dto.City;
import com.soundtrip.dto.CityDTO;
import com.soundtrip.dto.Country;
import com.soundtrip.dto.Event;
import com.soundtrip.dto.EventDTO;
import com.soundtrip.dto.ResponseMessage;
import com.soundtrip.service.CityService;
import com.soundtrip.service.CountryService;
import com.soundtrip.service.EventService;

@Controller
public class EventController {

	private final DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Inject
	EventService eventService;

	@Inject
	CountryService countryService;

	@Inject
	CityService cityService;

	Gson gson = new Gson();

	@RequestMapping(value = "/eventmaster", method = RequestMethod.GET)
	@ResponseBody
	public List<EventDTO> getEvents() {
		List<Event> events = new ArrayList<Event>();
		events = eventService.getAllEvents();
		List<EventDTO> eventDTOs = new ArrayList<EventDTO>(events.size());

		for (Event event : events) {
			System.out.println("Event date time ::::" + event.getDatetime());
			eventDTOs.add(new EventDTO(event.getId(), event.getName(), event.getDescription(), event.getAddressLine1(),
					event.getAddressLine2(), event.getArea(), copyCity(event.getCity()), event.getState(),
					event.getPinCode(), event.getGenre(), event.getImage(), (event.getDatetime() == null
							? formatter.format(new Date()) : formatter.format(event.getDatetime()))));
			/*
			 * ByteArrayOutputStream baos=new ByteArrayOutputStream(1000); try {
			 * BufferedImage img=ImageIO.read(new
			 * File(CommonConstants.IMAGE_DIRECTORY_LOCAL_PATH.concat(event.
			 * getImage()))); ImageIO.write(img, "jpg", baos); baos.flush();
			 * 
			 * @SuppressWarnings("restriction") String base64String =
			 * com.sun.org.apache.xerces.internal.impl.dv.util.Base64.encode(
			 * baos.toByteArray()); event.setImage(base64String); baos.close();
			 * } catch (IOException e) { e.printStackTrace(); }
			 * eventsList.add(event);
			 */
		}

		return eventDTOs;
	}

	private CityDTO copyCity(City city) {
		return new CityDTO(city.getId(), city.getName());
	}

	@RequestMapping(value = "/eventmaster", method = RequestMethod.POST)
	@ResponseBody
	@Transactional(propagation = Propagation.REQUIRED)
	public ResponseMessage addEvent(@RequestBody String eventJson) throws ParseException {
		System.out.println("Here... " + eventJson);
		gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		EventDTO eventToSave = gson.fromJson(eventJson, EventDTO.class);

		String data = eventToSave.getImage();
		eventToSave.setImage("");

		City city = cityService.getCityForId(eventToSave.getCity().getId());

		Event event = null;
		if(eventToSave.getId() > 0) {
			event = eventService.getEventById(eventToSave.getId());
			event.setName(eventToSave.getName());
			event.setDescription(eventToSave.getDescription());
			event.setAddressLine1(eventToSave.getAddressLine1());
			event.setAddressLine2(eventToSave.getAddressLine2());
			event.setArea(eventToSave.getArea());
			event.setCity(city);
			event.setState(eventToSave.getState());
			event.setPinCode(eventToSave.getPinCode());
			event.setGenre(eventToSave.getGenre());
			event.setImage(event.getImage());
			event.setDatetime((Date) new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(eventToSave.getDatetime()));
		} else {
			event = new Event(eventToSave.getName(), eventToSave.getDescription(), eventToSave.getAddressLine1(),
				eventToSave.getAddressLine2(), eventToSave.getArea(), city, eventToSave.getState(),
				eventToSave.getPinCode(), eventToSave.getGenre(), eventToSave.getImage(),
				(Date) new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(eventToSave.getDatetime()));
		}

		event = eventService.saveEvent(event);

		StringBuffer imageName = new StringBuffer();
		imageName.append(event.getId());
		imageName.append("_");
		imageName.append(event.getName().trim());
		imageName.append("_");
		imageName.append(event.getGenre().trim());
		imageName.append("_");
		imageName.append(event.getCity().getName().trim());
		imageName.append(".jpg");

		String base64Image = data.split(",")[1];
		byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(base64Image);

		ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
		Iterator<?> readers = ImageIO.getImageReadersByFormatName("jpg");
		ImageReader reader = (ImageReader) readers.next();
		Object source = bis;
		ImageInputStream iis = null;
		try {
			iis = ImageIO.createImageInputStream(source);
			reader.setInput(iis, true);
			ImageReadParam param = reader.getDefaultReadParam();

			Image image = reader.read(0, param);
			BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null),
					BufferedImage.TYPE_INT_RGB);
			Graphics2D g2 = bufferedImage.createGraphics();
			g2.drawImage(image, null, null);
			File imageFile = new File(CommonConstants.IMAGE_DIRECTORY_LOCAL_PATH.concat(imageName.toString()));
			ImageIO.write(bufferedImage, "jpg", imageFile);
		} catch (IOException e1) {
			e1.printStackTrace();
			return new ResponseMessage(ResponseMessage.Type.error, "technicalProblem");
		}

		event.setImage(imageName.toString());
		
		eventService.saveEvent(event);

		return new ResponseMessage(ResponseMessage.Type.success, "eventAdded");
	}
	
	@RequestMapping(value = "/eventmaster/fbEvent", method = RequestMethod.POST)
	@ResponseBody
	@Transactional(propagation = Propagation.REQUIRED)
	public ResponseMessage addFBEvent(@RequestBody String eventJson) throws ParseException {
		System.out.println("Here... " + eventJson);
		gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		EventDTO eventToSave = gson.fromJson(eventJson, EventDTO.class);

		Event event = null;
		if(eventToSave.getId() > 0) {
			event = eventService.getEventById(eventToSave.getId());
		}		
		eventService.createFBEvent(event);

		return new ResponseMessage(ResponseMessage.Type.success, "eventAdded");
	}

	@RequestMapping(value = "/eventmaster/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public ResponseMessage deletePerson(@PathVariable int id) {
		eventService.deleteEvent(id);
		return new ResponseMessage(ResponseMessage.Type.success, "eventDeleted");
	}
	
	@RequestMapping(value = "/eventdetails/{id}", method = RequestMethod.GET)
	@ResponseBody
	public EventDTO getEvent(@PathVariable int id) {
		Event event = eventService.getEventById(id);
		return new EventDTO(event.getId(), event.getName(), event.getDescription(), event.getAddressLine1(),
				event.getAddressLine2(), event.getArea(), copyCity(event.getCity()), event.getState(),
				event.getPinCode(), event.getGenre(), event.getImage(), (event.getDatetime() == null
						? formatter.format(new Date()) : formatter.format(event.getDatetime())));
	}

	@RequestMapping(value = "/eventmaster/countries", method = RequestMethod.GET)
	@ResponseBody
	public List<Country> getCountries() {
		return countryService.getAllCountries();
	}
	
	@RequestMapping(value = "/validateUser", method = RequestMethod.POST)
	@ResponseBody
	@Transactional(propagation = Propagation.REQUIRED)
	public ResponseMessage validateUser(@RequestBody String userName) throws ParseException {
		System.out.println("Here... " + userName);
		String[] list = userName.split("~");
		if(list[0].equals("Admin") && list[1].equals("Admin@123")){
			return new ResponseMessage(ResponseMessage.Type.success, "loginSuccess");
		}else{
			return new ResponseMessage(ResponseMessage.Type.error, "loginFail");
		}
		
	}

}

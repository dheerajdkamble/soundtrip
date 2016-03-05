package com.soundtrip.web;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
		System.out.println("Events size :::: " + events.size());
		List<EventDTO> eventDTOs = new ArrayList<EventDTO>(events.size());
		for (Event event : events) {

			eventDTOs.add(new EventDTO(event.getId(), event.getName(), event.getDescription(), event.getAddressLine1(),
					event.getAddressLine2(), event.getArea(), copyCity(event.getCity()), event.getState(),
					event.getPinCode(), event.getGenre(), event.getImage()));

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
	public ResponseMessage addEvent(@RequestBody String eventJson) {
		EventDTO eventToSave = gson.fromJson(eventJson, EventDTO.class);
		String data = eventToSave.getImage();
		eventToSave.setImage("");

		City city = cityService.getCityForId(eventToSave.getCity().getId());

		Event event = new Event(eventToSave.getName(), eventToSave.getDescription(), eventToSave.getAddressLine1(),
				eventToSave.getAddressLine2(), eventToSave.getArea(), city, eventToSave.getState(),
				eventToSave.getPinCode(), eventToSave.getGenre(), eventToSave.getImage());

		event = eventService.saveEvent(event);

		// StringBuffer imageName = new
		// StringBuffer(CommonConstants.IMAGE_DIRECTORY_LOCAL_PATH);
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

	@RequestMapping(value = "/eventmaster/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public ResponseMessage deletePerson(@PathVariable int id) {
		eventService.deleteEvent(id);
		return new ResponseMessage(ResponseMessage.Type.success, "eventDeleted");
	}

	@RequestMapping(value = "/eventmaster/countries", method = RequestMethod.GET)
	@ResponseBody
	public List<Country> getCountries() {
		return countryService.getAllCountries();
	}

}

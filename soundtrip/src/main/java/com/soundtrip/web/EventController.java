package com.soundtrip.web;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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
		List<Event> eventsList = new ArrayList<Event>(events.size());
		for (Event event : events) {
			
			ByteArrayOutputStream baos=new ByteArrayOutputStream(1000);
			try {
				BufferedImage img=ImageIO.read(new File(event.getImage()));
				ImageIO.write(img, "jpg", baos);
				baos.flush();
				
				@SuppressWarnings("restriction")
				String base64String = com.sun.org.apache.xerces.internal.impl.dv.util.Base64.encode(baos.toByteArray());
				event.setImage(base64String);
				baos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			eventsList.add(event);
		}
		
		return eventsList;
	}

	@RequestMapping(value = "/eventmaster", method = RequestMethod.POST)
	@ResponseBody
	@Transactional(propagation = Propagation.REQUIRED)
	public ResponseMessage addEvent(@RequestBody String event) {
		Event eventToSave = gson.fromJson(event, Event.class);
		String data = eventToSave.getImage();
		eventToSave.setImage("");
		eventToSave = eventService.saveEvent(eventToSave);
		
		StringBuffer stringBuffer = new StringBuffer(CommonConstants.IMAGE_DIRECTORY_LOCAL_PATH);
		stringBuffer.append(eventToSave.getId());
		stringBuffer.append("_");
		stringBuffer.append(eventToSave.getName());
		stringBuffer.append("_");
		stringBuffer.append(eventToSave.getGenre());
		stringBuffer.append("_");
		stringBuffer.append(eventToSave.getCity());
		stringBuffer.append(".jpg");

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
			BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);
			Graphics2D g2 = bufferedImage.createGraphics();
	        g2.drawImage(image, null, null);
	        File imageFile = new File(stringBuffer.toString());
	        ImageIO.write(bufferedImage, "jpg", imageFile);
		} catch (IOException e1) {
			e1.printStackTrace();
			return new ResponseMessage(ResponseMessage.Type.error, "technicalProblem");
		} 
		
		eventToSave.setImage(stringBuffer.toString());
		eventService.saveEvent(eventToSave);
		
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

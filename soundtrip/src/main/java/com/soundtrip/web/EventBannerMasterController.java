package com.soundtrip.web;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
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
import com.google.gson.GsonBuilder;
import com.soundtrip.common.CommonConstants;
import com.soundtrip.dto.EventBanner;
import com.soundtrip.dto.EventBannerDTO;
import com.soundtrip.dto.ResponseMessage;
import com.soundtrip.service.EventBannerService;

@Controller
public class EventBannerMasterController {

	@Inject
	EventBannerService eventBannerService;

	Gson gson = new Gson();

	@RequestMapping(value = "/eventbannermaster", method = RequestMethod.GET)
	@ResponseBody
	public List<EventBannerDTO> getBanners() {
		List<EventBanner> eventBanners = new ArrayList<EventBanner>();
		eventBanners = eventBannerService.getAllEventBanners();
		List<EventBannerDTO> eventBannerDTOs = new ArrayList<EventBannerDTO>(eventBanners.size());
		for (EventBanner eventBanner : eventBanners) {
			eventBannerDTOs.add(new EventBannerDTO(eventBanner.getId(), eventBanner.getName(), eventBanner.getUrl(), eventBanner.getImage()));
		}
		return eventBannerDTOs;
	}

	@RequestMapping(value = "/eventbannermaster", method = RequestMethod.POST)
	@ResponseBody
	@Transactional(propagation = Propagation.REQUIRED)
	public ResponseMessage addBanner(@RequestBody String bannerJson) throws ParseException {
		System.out.println("Here... " + bannerJson);
		gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		EventBanner bannerToSave = gson.fromJson(bannerJson, EventBanner.class);

		String data = bannerToSave.getImage();
		bannerToSave.setImage("");
		
		EventBanner eventBanner = null;
		if(bannerToSave.getId() > 0) {
			eventBanner = eventBannerService.getEventBannerById(bannerToSave.getId());
			eventBanner.setName(bannerToSave.getName());
			eventBanner.setUrl(bannerToSave.getUrl());
			eventBanner.setImage(eventBanner.getImage());
		} else {
			eventBanner = new EventBanner(bannerToSave.getName(), bannerToSave.getUrl(), bannerToSave.getImage());
		}

		eventBanner = eventBannerService.saveEventBanner(eventBanner);

		StringBuffer imageName = new StringBuffer();
		imageName.append(eventBanner.getName().trim());
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

		eventBanner.setImage(imageName.toString());
		
		eventBannerService.saveEventBanner(eventBanner);

		return new ResponseMessage(ResponseMessage.Type.success, "eventBannerAdded");
	}
	

	@RequestMapping(value = "/eventbannermaster/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public ResponseMessage deleteBanner(@PathVariable int id) {
		eventBannerService.deleteEventBanner(id);
		return new ResponseMessage(ResponseMessage.Type.success, "eventDeleted");
	}
	
	@RequestMapping(value = "/eventbannermaster/{id}", method = RequestMethod.GET)
	@ResponseBody
	public EventBannerDTO getEventBanner(@PathVariable int id) {
		EventBanner eventBanner = eventBannerService.getEventBannerById(id);
		return new EventBannerDTO(eventBanner.getId(), eventBanner.getName(), eventBanner.getUrl(), eventBanner.getImage());
	}

}

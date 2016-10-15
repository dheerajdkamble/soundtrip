package com.soundtrip.dto;

import java.util.Date;

/**
 * @author Dheeraj
 *
 */
public class EventBannerDTO {

	private int id;
	
	private String name;

	private String url;
	
	private String image;
	
	public EventBannerDTO() {
	}

	public EventBannerDTO(int id, String name, String url, String image) {
		super();
		this.id = id;
		this.name = name;
		this.url = url;
		this.image = image;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}


	/**
	 * @return the image
	 */
	public String getImage() {
		return image;
	}

	/**
	 * @param image the image to set
	 */
	public void setImage(String image) {
		this.image = image;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "EventDTO [id=" + id  + ", name=" + name + ", url=" + url+ ", image=" + image + "]";
	}
}

package com.soundtrip.dto;

import java.util.Date;

/**
 * @author Dheeraj
 *
 */
public class EventDTO {

	private int id;
	
	private String name;
	
	private String description;
	
	private String addressLine1;
	
	private String addressLine2;
	
	private String area;

	private CityDTO city;
	
	private String state;
	
	private String pinCode;
	
	private String genre;
	
	private String image;
	
	private String datetime;
	
	private String time;
	
	public EventDTO() {
	}

	public EventDTO(int id, String name, String description, String addressLine1, String addressLine2, String area,
			CityDTO city, String state, String pinCode, String genre, String image, String datetime) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.addressLine1 = addressLine1;
		this.addressLine2 = addressLine2;
		this.area = area;
		this.city = city;
		this.state = state;
		this.pinCode = pinCode;
		this.genre = genre;
		this.image = image;
		this.datetime = datetime;
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
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the addressLine1
	 */
	public String getAddressLine1() {
		return addressLine1;
	}

	/**
	 * @param addressLine1 the addressLine1 to set
	 */
	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	/**
	 * @return the addressLine2
	 */
	public String getAddressLine2() {
		return addressLine2;
	}

	/**
	 * @param addressLine2 the addressLine2 to set
	 */
	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	/**
	 * @return the area
	 */
	public String getArea() {
		return area;
	}

	/**
	 * @param area the area to set
	 */
	public void setArea(String area) {
		this.area = area;
	}

	/**
	 * @return the city
	 */
	public CityDTO getCity() {
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(CityDTO city) {
		this.city = city;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return the pinCode
	 */
	public String getPinCode() {
		return pinCode;
	}

	/**
	 * @param pinCode the pinCode to set
	 */
	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}

	/**
	 * @return the genre
	 */
	public String getGenre() {
		return genre;
	}

	/**
	 * @param genre the genre to set
	 */
	public void setGenre(String genre) {
		this.genre = genre;
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
	 * @return the time
	 */
	public String getTime() {
		return time;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(String time) {
		this.time = time;
	}

	/**
	 * @return the datetime
	 */
	public String getDatetime() {
		return datetime;
	}

	/**
	 * @param datetime the datetime to set
	 */
	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "EventDTO [id=" + id + ", name=" + name + ", description=" + description + ", addressLine1="
				+ addressLine1 + ", addressLine2=" + addressLine2 + ", area=" + area + ", city=" + city + ", state="
				+ state + ", pinCode=" + pinCode + ", genre=" + genre + ", image=" + image + ", datetime=" + datetime
				+ ", time=" + time + "]";
	}
}

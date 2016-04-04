package com.soundtrip.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "event")
@NamedQueries(value={
		@NamedQuery(name="allEventsSortedByEventDate", query="from Event order by datetime desc")
})
public class Event {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column
	private String name;

	@Column
	private String description;

	@Column
	private String addressLine1;

	@Column
	private String addressLine2;

	@Column
	private String area;

	@OneToOne
	@JoinColumn(name="city_id")
	private City city;

	@Column
	private String state;

	@Column
	private String pinCode;

	@Column
	private String genre;

	@Column
	private String image;
	
	@Column
	private Date datetime;

	/**
	 * Deparameterized constructor
	 */
	public Event() {
	}
	
	/**
	 * Parameterized constructor
	 * 
	 * @param name
	 * @param description
	 * @param addressLine1
	 * @param addressLine2
	 * @param area
	 * @param city
	 * @param state
	 * @param pinCode
	 * @param genre
	 * @param image
	 * @param datetime
	 */
	public Event(String name, String description, String addressLine1, String addressLine2, String area,
			City city, String state, String pinCode, String genre, String image, Date datetime) {
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
		this.datetime = (Date) datetime.clone();
	}

	/**
	 * @return id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *//*
		 * public void setId(int id) { this.id = id; }
		 */

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
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
	 * @param description
	 *            the description to set
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
	 * @param addressLine1
	 *            the addressLine1 to set
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
	 * @param addressLine2
	 *            the addressLine2 to set
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
	 * @param area
	 *            the area to set
	 */
	public void setArea(String area) {
		this.area = area;
	}

	/**
	 * @return the city
	 */
	public City getCity() {
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(City city) {
		this.city = city;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state
	 *            the state to set
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
	 * @param pinCode
	 *            the pinCode to set
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
	 * @param genre
	 *            the genre to set
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
	 * @param image
	 *            the image to set
	 */
	public void setImage(String image) {
		this.image = image;
	}

	/**
	 * @return the datetime
	 */
	public Date getDatetime() {
		return datetime;
	}

	/**
	 * @param datetime the datetime to set
	 */
	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Event [id=" + id + ", name=" + name + ", description=" + description + ", addressLine1=" + addressLine1
				+ ", addressLine2=" + addressLine2 + ", area=" + area + ", city=" + city + ", state=" + state
				+ ", pinCode=" + pinCode + ", genre=" + genre + ", image=" + image + ", datetime=" + datetime + "]";
	}	
}
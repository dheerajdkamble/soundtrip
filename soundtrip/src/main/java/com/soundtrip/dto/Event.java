package com.soundtrip.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Event")
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
	
	@Column
    private String city;
	
	@Column
    private String state;
	
	@Column
    private String pinCode;
	
	@Column
    private String genre;
	
	@Column
    private String image;
	
    /**
     * Deparameterized constructor
     */
    public Event() {
    }

    /**
     * Parameterized constructor
     * @param id
     * @param name
     * @param description
     * @param addressLine1
     * @param addressLine2
     * @param area
     * @param city
     * @param state
     * @param pinCode
     */
    public Event(int id, String name, String description, String addressLine1, String addressLine2, String area,
			String city, String state, String pinCode) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.addressLine1 = addressLine1;
		this.addressLine2 = addressLine2;
		this.area = area;
		this.city = city;
		this.state = state;
		this.pinCode = pinCode;
	}

	/**
	 * @return id
	 */
	public int getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(int id) {
        this.id = id;
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
	public String getCity() {
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
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
}

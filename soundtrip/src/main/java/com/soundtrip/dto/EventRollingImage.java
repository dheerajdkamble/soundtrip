package com.soundtrip.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "EventRollingImage")
public class EventRollingImage {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	@Column
	private String imageUrl;

	@Column
	private String routeUrl;

	/**
	 * @return the imageUrl
	 */
	public String getImageUrl() {
		return imageUrl;
	}

	/**
	 * @param imageUrl
	 *            the imageUrl to set
	 */
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	/**
	 * @return the routeUrl
	 */
	public String getRouteUrl() {
		return routeUrl;
	}

	/**
	 * @param routeUrl
	 *            the routeUrl to set
	 */
	public void setRouteUrl(String routeUrl) {
		this.routeUrl = routeUrl;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
}

package com.soundtrip.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "eventbanner")
@NamedQueries(value={
		@NamedQuery(name="allEventBanners", query="from EventBanner order by name asc")
})
public class EventBanner {
		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		private int id;

		@Column
		private String name;
		
		@Column
		private String url;
		
		@Column
		private String image;

		/**
		 * Deparameterized constructor
		 */
		public EventBanner() {
		}
		
		/**
		 * Parameterized constructor
		 * 
		 * @param name
		 * @param image
		 */
		public EventBanner(String name, String url, String image) {
			this.name = name;
			this.url = url;
			this.image = image;
		}
		
		/**
		 * @return the id
		 */
		public int getId() {
			return id;
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
}

package com.soundtrip.dto;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author Dheeraj
 *	Entity class for State
 */
@Entity
@Table(name="STATE")
public class State {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
	
	@Column
    private String name;

    @ManyToOne(fetch=FetchType.LAZY, targetEntity=Country.class)
    
    private Country country;
    
    @OneToMany(mappedBy="state", fetch=FetchType.LAZY)
    private List<City> cities;
    
    public State() {
    }

    public State(int id, String name, Country country) {
        this.id = id;
        this.name = name;
        this.country = country;
    }

    public int getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	/**
	 * @return the country
	 */
	public Country getCountry() {
		return country;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(Country country) {
		this.country = country;
	}

	/**
	 * @return the cities
	 */
	public List<City> getCities() {
		if(cities == null) {
			cities = new ArrayList<City>();
		}
		return cities;
	}

	/**
	 * @param cities the cities to set
	 */
	public void setCities(List<City> cities) {
		this.cities = cities;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "State [id=" + id + ", name=" + name + country + "]";
	}
}

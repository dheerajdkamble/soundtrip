package com.soundtrip.dto;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author Dheeraj
 *	Entity class for Country
 */
@Entity
@Table(name="COUNTRY")
public class Country {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

	@Column
	private String shortname;
	
	@Column
    private String name;
	
	@OneToMany(mappedBy="country", fetch=FetchType.LAZY)
	private List<State> states;

    public Country() {
    }

    public Country(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    /**
	 * @return the shortname
	 */
	public String getShortname() {
		return shortname;
	}

	/**
	 * @param shortname the shortname to set
	 */
	public void setShortname(String shortname) {
		this.shortname = shortname;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
	/**
	 * @return the states
	 */
	public List<State> getStates() {
		if(states == null) {
			states = new ArrayList<State>();
		}
		return states;
	}

	/**
	 * @param states the states to set
	 */
	public void setStates(List<State> states) {
		this.states = states;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Country [id=" + id + ", name=" + name + "]";
	}
}

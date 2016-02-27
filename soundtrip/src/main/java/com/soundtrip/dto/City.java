package com.soundtrip.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author Dheeraj
 *	Entity class for City
 */
@Entity
@Table(name="CITY")
public class City {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
	
	@Column
    private String name;

    @ManyToOne(fetch=FetchType.LAZY, targetEntity=State.class)
    private State state;
    
    public City() {
    }

    public City(int id, String name, State state) {
        this.id = id;
        this.name = name;
        this.state = state;
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
	 * @return the state
	 */
	public State getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(State state) {
		this.state = state;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "State [id=" + id + ", name=" + name + state + "]";
	}
}

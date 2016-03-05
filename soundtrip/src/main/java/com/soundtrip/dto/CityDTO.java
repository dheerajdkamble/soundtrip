package com.soundtrip.dto;

/**
 * @author Dheeraj
 *	Entity class for City
 */
public class CityDTO {
	
    private int id;
	
    private String name;
    
    public CityDTO() {
    }

    public CityDTO(int id, String name) {
        this.id = id;
        this.name = name;
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "State [id=" + id + ", name=" + name  + "]";
	}
}

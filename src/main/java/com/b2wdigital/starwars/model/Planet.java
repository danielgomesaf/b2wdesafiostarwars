package com.b2wdigital.starwars.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Planet {

	@Id
	private ObjectId id;
	
	private String name;
	
	private String climate;
	
	private String terrain;
	
	private int howManyTimesOnMovies;

	public String getId() {
		return id.toHexString();
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClimate() {
		return climate;
	}

	public void setClimate(String climate) {
		this.climate = climate;
	}

	public String getTerrain() {
		return terrain;
	}

	public void setTerrain(String terrain) {
		this.terrain = terrain;
	}

	public int getHowManyTimesOnMovies() {
		return howManyTimesOnMovies;
	}

	public void setHowManyTimesOnMovies(int howManyTimesOnMovies) {
		this.howManyTimesOnMovies = howManyTimesOnMovies;
	}
}

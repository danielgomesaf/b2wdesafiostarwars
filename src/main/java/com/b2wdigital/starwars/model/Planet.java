package com.b2wdigital.starwars.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

public class Planet {

	@Id
	private ObjectId id;
	
	private String name;
	
	private String weather;
	
	private String terrain;
	
	private int howManyTimesOnMovies;

	public ObjectId getId() {
		return id;
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

	public String getWeather() {
		return weather;
	}

	public void setWeather(String weather) {
		this.weather = weather;
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

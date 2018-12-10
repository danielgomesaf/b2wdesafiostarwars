package com.b2wdigital.starwars.controller;

import java.util.List;

import javax.validation.Valid;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.b2wdigital.starwars.model.Planet;
import com.b2wdigital.starwars.repository.PlanetRepository;

@RestController
@RequestMapping("/planets")
public class PlanetController {
	
	@Autowired
	PlanetRepository repository;
	
	@RequestMapping("/all")
	public List<Planet> getAll() {
		return repository.findAll();
	}

	@RequestMapping("{id}")
	public Planet getById(@PathVariable("id") ObjectId id) {
		return repository.findById(id);
	}
	
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public Planet createPlanet(@Valid @RequestBody Planet planet) {
		
		planet.setId(ObjectId.get());
		repository.save(planet);
		return planet;
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public void deletePlanet(@PathVariable ObjectId id) {
		repository.delete(repository.findById(id));
	}
}

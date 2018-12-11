package com.b2wdigital.starwars.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.b2wdigital.starwars.model.Planet;
import com.b2wdigital.starwars.repositories.PlanetRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

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
	
	@RequestMapping("/byname/{name}")
	public Planet getByName(@PathVariable("name") String name) {
		return repository.findByName(name);
	}
	
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public Planet createPlanet(@Valid @RequestBody Planet planet) throws Exception {
		
		Planet existingPlanet = repository.findByName(planet.getName());
		if (existingPlanet != null)
			throw new Exception("planet already existing");
		
		planet.setId(ObjectId.get());
		planet.setHowManyTimesOnMovies(getHowManyTimesPlanetAppearsOnMovies(planet));
		repository.save(planet);
		
		return planet;
	}

	private int getHowManyTimesPlanetAppearsOnMovies(Planet planet) throws Exception {
		int howManyTimesOnMovies = 0;
		try {
			RestTemplate restTemplate = new RestTemplate();
			for (int i = 1; i < 8; i++) {
				ResponseEntity<String> response = restTemplate.getForEntity("https://swapi.co/api/planets/?page=" + i, String.class);
				ObjectMapper mapper = new ObjectMapper();
				JsonNode responseBody = mapper.readTree(response.getBody());
				if (responseBody != null && !responseBody.isMissingNode()) {
					JsonNode planets = responseBody.get("results");
					if (planets.isArray()) {
						for (JsonNode p : planets) {
							String planetName = p.get("name").textValue();
							if (planet.getName().equals(planetName)) {
								JsonNode films = p.get("films");
								if (films == null || films.isMissingNode())
									howManyTimesOnMovies = 0;
								if (films.isArray())
									howManyTimesOnMovies = films.size();
								else
									howManyTimesOnMovies = 1;								
								break;
							}
						}
					}
				}				
			}
			
		} catch (IOException e) {
			throw new Exception("An error occurred on consulting the swapi API");
		}
		return howManyTimesOnMovies;
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public void deletePlanet(@PathVariable ObjectId id) {
		repository.delete(repository.findById(id));
	}
}

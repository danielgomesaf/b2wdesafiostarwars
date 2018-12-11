package com.b2wdigital.starwars.repositories;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import com.b2wdigital.starwars.model.Planet;

public interface PlanetRepository extends MongoRepository<Planet, String> {

	Planet findById(@Param("id") ObjectId id);
	Planet findByName(@Param("name") String name);
	List<Planet> findAll();
}

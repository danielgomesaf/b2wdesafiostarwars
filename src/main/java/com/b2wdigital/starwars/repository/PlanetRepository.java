package com.b2wdigital.starwars.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.b2wdigital.starwars.model.Planet;

@RepositoryRestResource(collectionResourceRel="planet", path="planet")
public interface PlanetRepository extends MongoRepository<Planet, String> {

	Planet findById(@Param("id") ObjectId id);
	Planet findByName(@Param("name") String name);
	List<Planet> findAll();
}

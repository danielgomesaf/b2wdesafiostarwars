package com.b2wdigital.starwars;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.List;

import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.b2wdigital.starwars.controller.PlanetController;
import com.b2wdigital.starwars.model.Planet;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(PlanetController.class)
public class StarwarsApplicationTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private PlanetController planetController;

	@Test
	public void getPlanets() throws Exception {
		Planet p = new Planet();
		p.setId(ObjectId.get());
		p.setName("Alderaan");
		p.setClimate("Dry");
		p.setTerrain("Mud");
		List<Planet> allPlanets = Collections.singletonList(p);
		given(planetController.getAll()).willReturn(allPlanets);

		mvc.perform(get("/planets/all").contentType(APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1))).andExpect(jsonPath("$[0].name", is("Alderaan")));
	}

	@Test
	public void getPlanetById() throws Exception {
		ObjectId objectId = ObjectId.get();
		Planet p = new Planet();
		p.setId(objectId);
		p.setName("Alderaan");
		p.setClimate("Dry");
		p.setTerrain("Mud");
		given(planetController.getById(objectId)).willReturn(p);

		mvc.perform(get("/planets/" + p.getId()).contentType(APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("id", is(p.getId())));
	}

	@Test
	public void getPlanetByName() throws Exception {
		Planet p = new Planet();
		p.setId(ObjectId.get());
		p.setName("Alderaan");
		p.setClimate("Dry");
		p.setTerrain("Mud");
		given(planetController.getByName(p.getName())).willReturn(p);

		mvc.perform(get("/planets/byname/" + p.getName()).contentType(APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("name", is(p.getName())));
	}
	
	@Test
	public void createPlanet() throws Exception {
		Planet p = new Planet();
		p.setId(ObjectId.get());
		p.setName("Tatooine");
		p.setClimate("Dry");
		p.setTerrain("Mud");
		p.setHowManyTimesOnMovies(5);
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		byte[] content = mapper.writeValueAsBytes(p);
		
		Planet pMock = new Planet();
		pMock.setId(ObjectId.get());
		pMock.setName("Tatooine");
		pMock.setClimate("Dry");
		pMock.setTerrain("Mud");
		pMock.setHowManyTimesOnMovies(5);
		
		when(planetController.createPlanet(Mockito.any(Planet.class))).thenReturn(pMock);
		
		mvc.perform(post("/planets/create")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(APPLICATION_JSON)
				.content(content))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name", is(p.getName())))
				.andExpect(jsonPath("$.howManyTimesOnMovies", is(5)));
	}
}

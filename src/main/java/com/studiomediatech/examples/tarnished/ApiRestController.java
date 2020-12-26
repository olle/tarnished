package com.studiomediatech.examples.tarnished;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.springframework.hateoas.Link;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiRestController {

	public static final Function<String, Link> API = rel -> linkTo(methodOn(ApiRestController.class).api())
			.withRel(rel);

	public static final Function<String, Link> API_V1 = rel -> linkTo(methodOn(ApiRestController.class).v1())
			.withRel(rel);

	public static final Function<String, Link> API_V1_FROBULATORS = rel -> linkTo(
			methodOn(ApiRestController.class).frobulators()).withRel(rel);

	@GetMapping("/api")
	public Map<String, Object> api() {

		return Collections.singletonMap("links", List.of(API.apply("self"), API_V1.apply("v1")));
	}

	@GetMapping("/api/v1")
	public Map<String, Object> v1() {

		return Collections.singletonMap("links",
				List.of(API.apply("parent"), API_V1.apply("self"), API_V1_FROBULATORS.apply("frobulators")));
	}

	@GetMapping("/api/v1/frobulators")
	public Map<String, Object> frobulators() {

		return Collections.singletonMap("hello", "world!");
	}

}

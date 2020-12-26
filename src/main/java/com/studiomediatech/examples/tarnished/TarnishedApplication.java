package com.studiomediatech.examples.tarnished;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Profiles;

@SpringBootApplication
public class TarnishedApplication {

	public static final Profiles DEVELOPMENT = Profiles.of("localhost");

	public static void main(String[] args) {
		SpringApplication.run(TarnishedApplication.class, args);
	}

}

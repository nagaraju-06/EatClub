package com.alpha.Eatclub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class EatClub {

	public static void main(String[] args) {
		SpringApplication.run(EatClub.class, args);
	}
	@Bean
	public RestTemplate getRestTemplste() {
		return new RestTemplate();
	}
}

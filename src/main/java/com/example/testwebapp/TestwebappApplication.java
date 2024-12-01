package com.example.testwebapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class TestwebappApplication {


	public static void main(String[] args) {
		System.setProperty("spring.config.name", "bootstrap");
		SpringApplication.run(TestwebappApplication.class, args);
	}

}

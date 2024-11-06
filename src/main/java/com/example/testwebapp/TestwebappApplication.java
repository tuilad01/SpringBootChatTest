package com.example.testwebapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class TestwebappApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestwebappApplication.class, args);
	}

}

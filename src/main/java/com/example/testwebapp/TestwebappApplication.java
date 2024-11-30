package com.example.testwebapp;

import com.example.testwebapp.common.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class TestwebappApplication {


	public static void main(String[] args) {
		System.setProperty("spring.config.name", "bootstrap");
		SpringApplication.run(TestwebappApplication.class, args);
	}

}

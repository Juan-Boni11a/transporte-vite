package com.example.transportsapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication()
public class TransportsApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransportsApiApplication.class, args);
	}

}

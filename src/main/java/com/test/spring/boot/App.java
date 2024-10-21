package com.test.spring.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.test.spring.boot", "com.test.spring.boot.config", "com.test.spring.boot.controller","com.test.spring.boot.handler","com.test.spring.boot.readfile"})  // Include your controller and service packages
public class App {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

}

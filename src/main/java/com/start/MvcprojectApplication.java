package com.start;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.ContextConfiguration;

@SpringBootApplication
@ImportResource("classpath:applicationContext.xml")
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})

public class MvcprojectApplication {

	public static void main(String[] args) {
		SpringApplication.run(MvcprojectApplication.class, args);
	}
}

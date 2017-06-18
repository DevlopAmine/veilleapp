package com.start;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.ContextConfiguration;

import com.start.config.JwtFilter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@ImportResource("classpath:applicationContext.xml")
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})

public class MvcprojectApplication {

	@Bean
	public FilterRegistrationBean jwtFilter() {
		final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		registrationBean.setFilter(new JwtFilter());
		registrationBean.addUrlPatterns("/secure/*");

		return registrationBean;
	}
	
	public static void main(String[] args) {
		SpringApplication.run(MvcprojectApplication.class, args);
	}
}

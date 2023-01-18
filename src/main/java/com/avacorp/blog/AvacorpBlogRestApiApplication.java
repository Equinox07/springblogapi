package com.avacorp.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class AvacorpBlogRestApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(AvacorpBlogRestApiApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedOrigins("http://localhost:8080")
						.allowedMethods("POST", "PUT", "GET", "OPTIONS", "DELETE", "PATCH" ,"HEAD");
			}
		};
	}

}

package com.scotiabank.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@SpringBootApplication(scanBasePackages = { "com.scotiabank.demo", "com.scotiabank.demo.config" })
public class DemoApplication implements WebMvcConfigurer {
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
				.allowedOrigins("http://localhost:4200") // Permite solicitudes CORS desde el frontend Angular
				.allowedMethods("GET", "POST", "PUT", "DELETE") // Permite los métodos HTTP que tu aplicación necesita
				.allowedHeaders("*")
				.allowCredentials(true); // Permite todos los encabezados
	}
}

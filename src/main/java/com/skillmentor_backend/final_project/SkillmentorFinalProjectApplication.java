package com.skillmentor_backend.final_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// --- IMPORT THIS CLASS ---
import org.springframework.boot.actuate.autoconfigure.metrics.SystemMetricsAutoConfiguration;

@SpringBootApplication(exclude = { SystemMetricsAutoConfiguration.class })
public class SkillmentorFinalProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(SkillmentorFinalProjectApplication.class, args);
	}

}
package com.skillmentor_backend.final_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.actuate.autoconfigure.metrics.SystemMetricsAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.metrics.web.tomcat.TomcatMetricsAutoConfiguration;

@SpringBootApplication(exclude = {
		SystemMetricsAutoConfiguration.class,
		TomcatMetricsAutoConfiguration.class
})
public class SkillmentorFinalProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(SkillmentorFinalProjectApplication.class, args);
	}

}
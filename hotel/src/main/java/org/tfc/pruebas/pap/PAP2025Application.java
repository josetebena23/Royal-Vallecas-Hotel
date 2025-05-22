package org.tfc.pruebas.pap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class PAP2025Application {

	public static void main(String[] args) {
		SpringApplication.run(PAP2025Application.class, args);
	}
}

package com.jeremi.starterjeremiappjwt;

import com.jeremi.starterjeremiappjwt.model.Role;
import com.jeremi.starterjeremiappjwt.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class StarterJeremiAppJwtApplication {

	public static void main(String[] args) {
		SpringApplication.run(StarterJeremiAppJwtApplication.class, args);
	}

	@Bean
	public CommandLineRunner runner(RoleRepository roleRepository) {
		return args -> {
			if (roleRepository.findRoleByName("USER").isEmpty()) {
				roleRepository.save(Role.builder().name("USER").build());
			}
		};
	}
}

package com.nest.epargne;

import com.nest.epargne.services.IAccountService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class EpargneApplication implements CommandLineRunner {
	@Autowired
     private IAccountService service ;
	public static void main(String[] args) {
		SpringApplication.run(EpargneApplication.class, args);
	}
	@Bean
	public BCryptPasswordEncoder getBCPE() {
		return new BCryptPasswordEncoder();
	}
	@Bean
	public ModelMapper Mapper() {
		return new ModelMapper();
	}


	@Override
	public void run(String... args) throws Exception {
		service.initAccount();
	}
}

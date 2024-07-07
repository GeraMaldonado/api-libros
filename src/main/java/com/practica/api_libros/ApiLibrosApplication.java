package com.practica.api_libros;

import com.practica.api_libros.principal.Principal;
import com.practica.api_libros.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiLibrosApplication implements CommandLineRunner {
	@Autowired
	private LibroRepository repository;
	public static void main(String[] args) {
		SpringApplication.run(ApiLibrosApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(repository);
		principal.muestraMenu();
	}
}

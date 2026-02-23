package com.uniquindio.edu.co.Taller_Software_2;

import com.uniquindio.edu.co.Taller_Software_2.domain.Book;
import com.uniquindio.edu.co.Taller_Software_2.service.BookService;
import com.uniquindio.edu.co.Taller_Software_2.service.SearchService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TallerSoftware2Application {

	public static void main(String[] args) {
		SpringApplication.run(TallerSoftware2Application.class, args);
	}

	@Bean
	CommandLineRunner testSimilitud(SearchService searchService, BookService bookService) {
		return args -> {

			Book libro = new Book();
			libro.setTitulo("Clean Code");
			libro.setAutor("Robert Martin");
			libro.setIsbn("1234567890123");

			double resultado = searchService.calcularPorcentajeSimilitud("Clean", libro);

			System.out.println("Porcentaje calculado: " + resultado + "%");
		};
	}
}

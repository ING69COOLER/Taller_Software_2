package com.uniquindio.edu.co.Taller_Software_2;

import com.uniquindio.edu.co.Taller_Software_2.domain.Book;
import com.uniquindio.edu.co.Taller_Software_2.dto.BookDTO;
import com.uniquindio.edu.co.Taller_Software_2.service.BookService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
public class TallerSoftware2Application implements CommandLineRunner {

	private final BookService bookService;

	public TallerSoftware2Application(BookService bookService) {
		this.bookService = bookService;
	}

	public static void main(String[] args) {
		SpringApplication.run(TallerSoftware2Application.class, args);
	}

	@Override
	public void run(String... args) {

		BookDTO dto = new BookDTO();
		dto.setTitulo("Cien Años de Soledad");
		dto.setAutor("Gabriel Garcia Marquez");
		dto.setAnoPublicacion(LocalDate.of(1967, 5, 30));
		dto.setIsbn("123456");

		BookDTO guardado = bookService.crearLibro(dto);

		System.out.println("Libro guardado con ID: " + guardado.getId());
	}
}

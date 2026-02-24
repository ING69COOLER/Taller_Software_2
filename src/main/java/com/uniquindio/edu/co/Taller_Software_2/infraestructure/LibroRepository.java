package com.uniquindio.edu.co.Taller_Software_2.infraestructure;

import com.uniquindio.edu.co.Taller_Software_2.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibroRepository extends JpaRepository<Book, Long> {

}

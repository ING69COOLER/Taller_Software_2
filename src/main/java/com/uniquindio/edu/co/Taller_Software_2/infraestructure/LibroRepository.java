package com.uniquindio.edu.co.Taller_Software_2.infraestructure;

import com.uniquindio.edu.co.Taller_Software_2.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LibroRepository extends JpaRepository<Book, Long> {

    /**
     * Carga el libro junto con sus RESEÑAS (FETCH JOIN).
     * Se separa de la consulta de calificaciones para evitar
     * MultipleBagFetchException (Hibernate no permite FETCH de
     * dos List al mismo tiempo).
     */
    @Query("""
            SELECT DISTINCT b
            FROM Book b
            LEFT JOIN FETCH b.reseñas
            WHERE b.id = :id
            """)
    Optional<Book> findByIdConReseñas(@Param("id") Long id);

    /**
     * Carga el libro junto con sus CALIFICACIONES (FETCH JOIN).
     */
    @Query("""
            SELECT DISTINCT b
            FROM Book b
            LEFT JOIN FETCH b.calificaciones
            WHERE b.id = :id
            """)
    Optional<Book> findByIdConCalificaciones(@Param("id") Long id);
}

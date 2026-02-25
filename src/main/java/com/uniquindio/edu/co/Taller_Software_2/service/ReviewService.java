package com.uniquindio.edu.co.Taller_Software_2.service;

import com.uniquindio.edu.co.Taller_Software_2.domain.Book;
import com.uniquindio.edu.co.Taller_Software_2.domain.Calificacion;
import com.uniquindio.edu.co.Taller_Software_2.domain.Reseña;
import com.uniquindio.edu.co.Taller_Software_2.infraestructure.LibroRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReviewService {
    private final LibroRepository libroRepository;

    public ReviewService(LibroRepository libroRepository) {
        this.libroRepository = libroRepository;
    }

    // Metodo para agregar una reseña
    @Transactional
    public void agregarReseña(long id, String descripcion) {
        Book libro = libroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));
        Reseña reseña = new Reseña();
        reseña.setDescripcion(descripcion);
        libro.agregarReseña(reseña);
        libroRepository.save(libro);
    }

    // Metodo para agregar una calificacion
    @Transactional
    public void agregarCalificacion(Long libroId, int puntaje) {

        Book libro = libroRepository.findById(libroId)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));

        Calificacion calificacion = new Calificacion();
        calificacion.setPuntaje(puntaje);

        libro.agregarCalificacion(calificacion);

        libroRepository.save(libro);
    }
}

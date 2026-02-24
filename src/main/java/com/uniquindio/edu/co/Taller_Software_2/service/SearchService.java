package com.uniquindio.edu.co.Taller_Software_2.service;

import com.uniquindio.edu.co.Taller_Software_2.domain.Book;
import com.uniquindio.edu.co.Taller_Software_2.domain.LibroSimilitudComparator;
import com.uniquindio.edu.co.Taller_Software_2.infraestructure.LibroRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.TreeSet;

@Service
public class SearchService {

    private final LibroRepository libroRepository;

    public SearchService(LibroRepository libroRepository) {
        this.libroRepository = libroRepository;
    }
    public TreeSet<Book> busquedaSimple(String txt){
        List<Book> libros = libroRepository.findAll();
        TreeSet<Book> resultado  = new TreeSet<>(new LibroSimilitudComparator());
        for(Book libro : libros){
            double similitud = calcularPorcentajeSimilitud(txt, libro);
            if(similitud > 0 ){
                libro.setPorcentajeSimilitud(similitud);
                resultado.add(libro);
            }
        }
        return resultado;
    }

    public TreeSet<Book> busquedaCombinada(String titulo, String autor, String isbn, String ano) {
        List<Book> todosLosLibros = libroRepository.findAll();
        TreeSet<Book> resultado = new TreeSet<>(new LibroSimilitudComparator());

        for (Book libro : todosLosLibros) {
            double similitud = calcularSimilitudCombinada(titulo, autor, isbn, ano, libro);
            if (similitud > 0) {
                libro.setPorcentajeSimilitud(similitud);
                resultado.add(libro);
            }
        }

        return resultado;
    }
    
    public double calcularSimilitudCombinada(String titulo, String autor, String isbn, String anio, Book libro) {
        double similitudObtenida = 0;
        double pesoTotal = 0;

        if (titulo != null && !titulo.isBlank()) {
            pesoTotal += 0.3;
            if (libro.getTitulo() != null && libro.getTitulo().toLowerCase().contains(titulo.toLowerCase()))
                similitudObtenida += 0.3;
        }

        if (autor != null && !autor.isBlank()) {
            pesoTotal += 0.2;
            if (libro.getAutor() != null && libro.getAutor().toLowerCase().contains(autor.toLowerCase()))
                similitudObtenida += 0.2;
        }

        if (isbn != null && !isbn.isBlank()) {
            pesoTotal += 0.4;
            if (libro.getIsbn() != null && libro.getIsbn().toLowerCase().contains(isbn.toLowerCase()))
                similitudObtenida += 0.4;
        }

        if (anio != null && !anio.isBlank()) {
            pesoTotal += 0.1;
            try {
                int anioBuscado = Integer.parseInt(anio.trim());
                if (libro.getAnoPublicacion() != null && libro.getAnoPublicacion().getYear() == anioBuscado)
                    similitudObtenida += 0.1;
            } catch (NumberFormatException e) { }
        }

        if (pesoTotal == 0) return 0;
        return similitudObtenida / pesoTotal;
    }
    public double calcularPorcentajeSimilitud(String textoBusqueda, Book libro) {
        if (textoBusqueda == null || textoBusqueda.isBlank()){
            return 0.0;
        }
        textoBusqueda = textoBusqueda.toLowerCase();
        double porcentajeSimilitud = 0;

        // Similitud por ISBN 40%
        if(libro.getIsbn() != null && libro.getIsbn().toLowerCase().contains(textoBusqueda)){
            porcentajeSimilitud +=  0.4;
        }

        //Similitud por Titulo 30%
        if(libro.getTitulo() != null && libro.getTitulo().toLowerCase().contains(textoBusqueda)){
            porcentajeSimilitud += 0.3;
        }

        //Similitud por Autor 20%
        if(libro.getAutor() != null && libro.getAutor().toLowerCase().contains(textoBusqueda)){
            porcentajeSimilitud += 0.2;
        }

        //Similitud por Año 10%
       try {
           int año = Integer.parseInt(textoBusqueda.replaceAll("[^0-9]", ""));
           if(libro.getAnoPublicacion() != null && libro.getAnoPublicacion().getYear() == año){
               porcentajeSimilitud += 0.1;
           }
       } catch (NumberFormatException e){

       }

        return porcentajeSimilitud;
    }
}

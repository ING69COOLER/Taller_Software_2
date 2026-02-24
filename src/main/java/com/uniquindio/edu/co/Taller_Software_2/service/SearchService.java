package com.uniquindio.edu.co.Taller_Software_2.service;

import com.uniquindio.edu.co.Taller_Software_2.domain.Book;
import org.springframework.stereotype.Service;

@Service
public class SearchService {

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

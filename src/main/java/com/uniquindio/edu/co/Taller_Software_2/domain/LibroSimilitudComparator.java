package com.uniquindio.edu.co.Taller_Software_2.domain;

import java.util.Comparator;

public class LibroSimilitudComparator implements Comparator<Book> {

    @Override
    public int compare(Book libro1, Book libro2) {
        int comparacion = Double.compare(libro2.getPorcentajeSimilitud(), libro1.getPorcentajeSimilitud());
        if (comparacion == 0) {
            return libro1.getId().compareTo(libro2.getId());
        }
        return comparacion;
    }
}

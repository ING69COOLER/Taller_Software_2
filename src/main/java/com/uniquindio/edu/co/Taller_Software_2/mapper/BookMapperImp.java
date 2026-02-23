package com.uniquindio.edu.co.Taller_Software_2.mapper;

import com.uniquindio.edu.co.Taller_Software_2.domain.Book;
import com.uniquindio.edu.co.Taller_Software_2.domain.Calificacion;
import com.uniquindio.edu.co.Taller_Software_2.domain.Reseña;
import com.uniquindio.edu.co.Taller_Software_2.dto.BookDTO;

import java.util.ArrayList;
import java.util.List;

public class BookMapperImp implements BookMapper{
    @Override
    public List<Book> getBooks(List<BookDTO> listarLibros) {

        ArrayList<Book> books = new ArrayList<Book>();
        for(BookDTO bookDTO : listarLibros){
            books.add(createBook(bookDTO));
        }
        return books;
    }



    public Book createBook(BookDTO bookDTO){

        if(bookDTO==null){
            return null;
        }
        Book book = new Book();
        book.setIsbn(bookDTO.getIsbn());
        Calificacion calificacion;
        book.agregarCalificacion(calificacion = new Calificacion());
        Reseña reseña;
        book.agregarReseña(reseña = new Reseña());
        book.setAutor(bookDTO.getAutor());
        book.setTitulo(bookDTO.getTitulo());
        return book;


    }


}

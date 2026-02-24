package com.uniquindio.edu.co.Taller_Software_2.mapper;

import com.uniquindio.edu.co.Taller_Software_2.domain.Book;
import com.uniquindio.edu.co.Taller_Software_2.dto.BookDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookMapperImp implements BookMapper {

    @Override
    public Book toEntity(BookDTO bookDTO) {
        if (bookDTO == null) {
            return null;
        }

        Book book = new Book();
        book.setId(bookDTO.getId());
        book.setTitulo(bookDTO.getTitulo());
        book.setAutor(bookDTO.getAutor());
        book.setAnoPublicacion(bookDTO.getAnoPublicacion());
        book.setIsbn(bookDTO.getIsbn());

        return book;
    }

    @Override
    public List<Book> toEntityList(List<BookDTO> dtos) {
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> getBooks(List<BookDTO> listarLibros) {
        return List.of();
    }

    @Override
    public  BookDTO toDTO(Book book) {
        BookDTO dto = new BookDTO();
        dto.setId(book.getId());
        dto.setTitulo(book.getTitulo());
        dto.setAutor(book.getAutor());
        dto.setAnoPublicacion(book.getAnoPublicacion());
        dto.setIsbn(book.getIsbn());
        return dto;
    }
}
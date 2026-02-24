package com.uniquindio.edu.co.Taller_Software_2.mapper;


import com.uniquindio.edu.co.Taller_Software_2.domain.Book;
import com.uniquindio.edu.co.Taller_Software_2.dto.BookDTO;

import java.util.List;

public interface BookMapper {

    // ======= DTO → ENTITY =======

    Book toEntity(BookDTO bookDTO);

    List<Book> toEntityList(List<BookDTO> dtos);

    public List<Book> getBooks(List<BookDTO> listarLibros);

    BookDTO toDTO(Book book);

}

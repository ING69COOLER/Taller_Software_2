package com.uniquindio.edu.co.Taller_Software_2.mapper;


import com.uniquindio.edu.co.Taller_Software_2.domain.Book;
import com.uniquindio.edu.co.Taller_Software_2.dto.BookDTO;

import java.util.List;

public interface BookMapper {

    // ======= DTO → ENTITY =======

    public List<Book> getBooks(List<BookDTO> listarLibros);
}

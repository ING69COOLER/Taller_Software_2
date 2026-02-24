package com.uniquindio.edu.co.Taller_Software_2.service;

import com.uniquindio.edu.co.Taller_Software_2.dto.BookDTO;

import java.sql.SQLException;

public interface BookService {

    BookDTO crearLibro(BookDTO book) throws SQLException;
}

package com.uniquindio.edu.co.Taller_Software_2.service;

import com.uniquindio.edu.co.Taller_Software_2.domain.Book;
import com.uniquindio.edu.co.Taller_Software_2.dto.BookDTO;
import com.uniquindio.edu.co.Taller_Software_2.infraestructure.LibroRepository;
import com.uniquindio.edu.co.Taller_Software_2.mapper.BookMapper;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements  BookService{

    private final LibroRepository libroRepository;
    private final BookMapper bookMapper;

    public BookServiceImpl(LibroRepository libroRepository, BookMapper bookMapper) {
        this.libroRepository = libroRepository;
        this.bookMapper = bookMapper;
    }

    @Override
    public BookDTO crearLibro(BookDTO bookDTO) {
        Book book = bookMapper.toEntity(bookDTO);
        Book savedBook = libroRepository.save(book);
        return bookMapper.toDTO(savedBook);
    }
}

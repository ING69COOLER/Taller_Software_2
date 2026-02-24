package com.uniquindio.edu.co.Taller_Software_2.dto;

import com.uniquindio.edu.co.Taller_Software_2.domain.Book;

import java.time.LocalDate;
import java.util.List;

public class  BookDTO {
    private Long id;
    private String titulo;
    private String autor;
    private LocalDate anoPublicacion;
    private String isbn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public LocalDate getAnoPublicacion() {
        return anoPublicacion;
    }

    public void setAnoPublicacion(LocalDate anoPublicacion) {
        this.anoPublicacion = anoPublicacion;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

 //   public BookDTO toDTO(Book book) {
 //       BookDTO dto = new BookDTO();
 //       dto.setId(book.getId());
 //       dto.setTitulo(book.getTitulo());
 //       dto.setAutor(book.getAutor());
 //       dto.setAnoPublicacion(book.getAnoPublicacion());
  //      dto.setIsbn(book.getIsbn());
  //      return dto;
    //}

    //public List<BookDTO> toDTOList(List<Book> books) {
    //    return books.stream()
     //           .map(this::toDTO)
    //            .toList();
    //}
}




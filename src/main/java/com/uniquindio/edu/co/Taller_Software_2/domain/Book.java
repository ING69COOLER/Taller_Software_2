package com.uniquindio.edu.co.Taller_Software_2.domain;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private String autor;

    @Column(name = "ano_publicacion")
    private LocalDate anoPublicacion;

    @Column(unique = true, nullable = false, length = 13)
    private String isbn;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reseña> reseñas = new ArrayList<>();

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Calificacion> calificaciones = new ArrayList<>();

    @Transient
    private double porcentajeSimilitud;

    public Book() {
    }

    // ==== Métodos ==== //



    /**
     * Método para agregar reseñas
     */
    public void agregarReseña(Reseña reseña){
        reseñas.add(reseña);
        reseña.setBook(this);
    }

    /**
     * Método para agregar una calificación
     */
    public void agregarCalificacion(Calificacion calificacion){
        calificaciones.add(calificacion);
        calificacion.setBook(this);
    }

    // ==== Getters y Setters == //
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

    public List<Reseña> getReseñas() {
        return reseñas;
    }

    public void setReseñas(List<Reseña> reseñas) {
        this.reseñas = reseñas;
    }

    public List<Calificacion> getCalificaciones() {
        return calificaciones;
    }

    public void setCalificaciones(List<Calificacion> calificaciones) {
        this.calificaciones = calificaciones;
    }

    public double getPorcentajeSimilitud() {
        return porcentajeSimilitud;
    }

    public void setPorcentajeSimilitud(double porcentajeSimilitud) {
        this.porcentajeSimilitud = porcentajeSimilitud;
    }

    // ==== equals y hashCode

    /**
     * equals basado en id
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        Book book = (Book) o;
        return id != null && id.equals(book.id);
    }
}

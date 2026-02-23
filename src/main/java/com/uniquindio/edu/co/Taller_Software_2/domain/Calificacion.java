package com.uniquindio.edu.co.Taller_Software_2.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "calificaciones")
public class Calificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int puntaje;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    public Calificacion() {
    }

    // ==== Getters y Setters ==== //

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(int puntaje) {
        if (puntaje < 1 || puntaje > 5){
            throw new IllegalArgumentException("El puntaje debe estar entre 1 y 5");
        }
        this.puntaje = puntaje;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    // ==== equals y hashCode ==== //

    /**
     * equals basado en el id
     */
    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if(!(o instanceof  Calificacion)) return false;
        Calificacion calificacion = (Calificacion) o;
        return id != null && id.equals(calificacion.id);
    }

    /**
     * hashCode
     */
    @Override
    public int hashCode(){
        return getClass().hashCode();
    }
}

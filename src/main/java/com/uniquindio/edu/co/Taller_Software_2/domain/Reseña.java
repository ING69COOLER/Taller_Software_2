package com.uniquindio.edu.co.Taller_Software_2.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "resenas")
public class Reseña {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String descripcion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    public Reseña() {
    }

    // ==== Getters y  Setters ==== //

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        if (descripcion == null || descripcion.isBlank()){
            throw new IllegalArgumentException("La descripción no puede estar vacía");
        }
        this.descripcion = descripcion;
    }

    // ==== equals y hashCode ====  //

    /**
     * equals basado en el id
     */
    @Override
    public boolean equals (Object o){
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        Reseña reseña = (Reseña) o;
        return id != null && id.equals(reseña);
    }

    /**
     * hashCode
     */
    @Override
    public int hashCode(){
        return getClass().hashCode();
    }
}

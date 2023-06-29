package com.biblioteca.biblioteca.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name="libros")
public class Libro {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Integer id;
    @Column(name="nombre")
    @Size(min = 5, max = 20)
    String nombre;

    @Column(name="codigo_isbn")
    @Size(min = 5, max = 20)
    String isbn;

    @Column(name="sinopsis")
    @Size(min = 5, max = 2000)
    String sinopsis;

    @Column(name="criticas")
    @Size(min = 5, max = 200)
    String criticas;

    @Column(name="foto")
    private String foto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_autores")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Autor autor;

    private static final long serialVersionUID = 1L;
}

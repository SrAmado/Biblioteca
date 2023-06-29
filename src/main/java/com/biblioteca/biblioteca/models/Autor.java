package com.biblioteca.biblioteca.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name="autores")
public class Autor {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Integer id_autores;

    @Column(name="nombre")
    @Size(min = 5, max = 20)
    String nombre;
}

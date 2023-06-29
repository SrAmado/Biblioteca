package com.biblioteca.biblioteca.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name="personas")
public class Persona {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Integer id;

    @Column(name="nombre")
    @Size(min = 5, max = 20)
    String nombre;

    @Column(name="apellidos")
    @Size(min = 5, max = 20)
    String apellidos;

    @Column(name="direccion")
    @Size(min = 5, max = 20)
    String direccion;

    @Column(name="ciudad")
    @Size(min = 5, max = 20)
    String ciudad;
}

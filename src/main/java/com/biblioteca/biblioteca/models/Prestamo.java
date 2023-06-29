package com.biblioteca.biblioteca.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name="prestamos")
public class Prestamo {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Integer id;
    @Column(name="fecha_prestamo")
    Date fechaPrestamo;

    @Column(name="fecha_devolucion")
    Date fechaDevolucion;

    @Column(name="devuelto")
    @Size(min = 2, max = 20)
    String devuelto;

    @Column(name="id_persona")
    Integer idPersona;

    @Column(name="id_libro")
    String idLibro;
}

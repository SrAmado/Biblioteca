package com.biblioteca.biblioteca.service;

import com.biblioteca.biblioteca.models.Autor;
import com.biblioteca.biblioteca.models.Libro;

import java.util.List;

public interface LibroService {

    public List<Libro> findAll();

    public Libro save(Libro libro);

    public void eliminar(Integer id);

    public Libro findById(Integer id);

    public  List<Autor> findAllAutores();
}

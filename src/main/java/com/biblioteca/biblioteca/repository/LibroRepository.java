package com.biblioteca.biblioteca.repository;

import com.biblioteca.biblioteca.models.Autor;
import com.biblioteca.biblioteca.models.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LibroRepository extends JpaRepository<Libro, Integer> {

    @Query("from Autor")
    public List<Autor> findAllAutores();
}

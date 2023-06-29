package com.biblioteca.biblioteca.service.impl;

import com.biblioteca.biblioteca.models.Autor;
import com.biblioteca.biblioteca.models.Libro;
import com.biblioteca.biblioteca.repository.LibroRepository;
import com.biblioteca.biblioteca.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LibroServiceImpl implements LibroService {

    @Autowired
    private LibroRepository libroRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Libro> findAll() {
        return libroRepository.findAll();
    }

    @Override
    @Transactional
    public Libro save(Libro libro) {
        return libroRepository.save(libro);
    }

    @Override
    @Transactional
    public void eliminar(Integer id) {
        libroRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Libro findById(Integer id) {
        return libroRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Autor> findAllAutores() {
        return libroRepository.findAllAutores();
    }
}

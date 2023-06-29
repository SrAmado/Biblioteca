package com.biblioteca.biblioteca.service.impl;

import com.biblioteca.biblioteca.models.Autor;
import com.biblioteca.biblioteca.repository.AutorRepository;
import com.biblioteca.biblioteca.service.AutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AutorServiceImpl implements AutorService {

    @Autowired
    private AutorRepository autorRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Autor> findAll() {
        return autorRepository.findAll();
    }
}

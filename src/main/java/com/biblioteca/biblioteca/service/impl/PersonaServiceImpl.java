package com.biblioteca.biblioteca.service.impl;

import com.biblioteca.biblioteca.models.Persona;
import com.biblioteca.biblioteca.repository.PersonaRepository;
import com.biblioteca.biblioteca.service.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PersonaServiceImpl implements PersonaService {

    @Autowired
    private PersonaRepository personaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Persona> findAll() {
        return personaRepository.findAll();
    }
}

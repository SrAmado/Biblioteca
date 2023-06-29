package com.biblioteca.biblioteca.repository;

import com.biblioteca.biblioteca.models.Persona;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonaRepository extends JpaRepository<Persona, Integer> {
}

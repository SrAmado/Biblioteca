package com.biblioteca.biblioteca.repository;

import com.biblioteca.biblioteca.models.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrestamoRepository extends JpaRepository<Prestamo, Integer> {

}

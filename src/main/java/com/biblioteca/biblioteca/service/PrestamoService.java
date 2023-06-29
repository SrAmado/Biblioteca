package com.biblioteca.biblioteca.service;

import com.biblioteca.biblioteca.models.Prestamo;

import java.util.List;

public interface PrestamoService {

    public List<Prestamo> findAll();

    public Prestamo save(Prestamo prestamo);

    public void eliminar(Integer id);

    public  Prestamo findById(Integer id);
}

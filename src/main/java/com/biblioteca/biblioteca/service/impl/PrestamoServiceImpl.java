package com.biblioteca.biblioteca.service.impl;

import com.biblioteca.biblioteca.models.Prestamo;
import com.biblioteca.biblioteca.repository.PrestamoRepository;
import com.biblioteca.biblioteca.service.PrestamoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PrestamoServiceImpl implements PrestamoService {

    @Autowired
    private PrestamoRepository prestamoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Prestamo> findAll() {
        return prestamoRepository.findAll();
    }

    @Override
    @Transactional
    public Prestamo save(Prestamo prestamo) {
        return prestamoRepository.save(prestamo);
    }

    @Override
    @Transactional
    public void eliminar(Integer id) {
        prestamoRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Prestamo findById(Integer id) {
        return prestamoRepository.findById(id).orElse(null);
    }
}

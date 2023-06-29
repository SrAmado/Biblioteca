package com.biblioteca.biblioteca.controller;

import com.biblioteca.biblioteca.service.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@CrossOrigin(origins = "http://localhost:3000")
@Controller
@RequestMapping("/api")
public class PersonaController {

    @Autowired
    private PersonaService personaService;

    @GetMapping("/listarPersonas")
    private ResponseEntity<?> listarPersonas() {return ResponseEntity.ok(personaService.findAll());}
}

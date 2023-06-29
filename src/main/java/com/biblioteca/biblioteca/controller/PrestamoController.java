package com.biblioteca.biblioteca.controller;

import com.biblioteca.biblioteca.models.Prestamo;
import com.biblioteca.biblioteca.service.PrestamoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:3000")
@Controller
@RequestMapping("/api")
public class PrestamoController {

    @Autowired
    private PrestamoService prestamoService;

    //METODO PARA CONSULTAR
    @GetMapping("/listarPrestamos")
    private ResponseEntity<?> listarPrestamos(){return ResponseEntity.ok(prestamoService.findAll());}

    //METODO PARA GUARDAR
    @PostMapping("/prestamos")
    private ResponseEntity<?> save(@Validated  @RequestBody Prestamo prestamo, BindingResult result){

        Prestamo prestamoNuevo = null;
        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()){

            List<String> errors=result.getFieldErrors()
                    .stream()
                    .map(err ->"el campo '"+err.getField()+"'"+ err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("error", errors);

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            prestamoNuevo = prestamoService.save(prestamo);
        }
        catch (DataAccessException e){
            response.put("mensaje: ", "Error al realizar el insert en la base de datos");
            response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
            return  new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje:", prestamoNuevo);
        response.put("mensaje: ", "Prestamo creado con exito");

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    //METODO ACTUALIZAR
    @PutMapping("/prestamos/{id}")
    private  ResponseEntity<?> actualizar(@Validated @RequestBody Prestamo prestamo, BindingResult result, @PathVariable Integer id ){
        Prestamo prestamoActual = prestamoService.findById(id);
        Prestamo prestamoUpdate=null;
        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()){

            List<String> errors=result.getFieldErrors()
                    .stream()
                    .map(err ->"el campo '"+err.getField()+"'"+ err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("error", errors);

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        if (prestamoActual == null){
            response.put("mensaje: ", "No se puede editar el prestamo con id :".concat(id.toString().concat("no existe")));
            return  new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        try {
            prestamoActual.setDevuelto(prestamo.getDevuelto());
            prestamoUpdate = prestamoService.save(prestamoActual);
        }
        catch (DataAccessException e){
            response.put("mensaje: ", "Error al actualizar el prestamo en la base de datos");
            response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
            return  new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje:", prestamoUpdate);
        response.put("mensaje: ", "Prestamo actualizado con exito");

        return new  ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    //METODO ELIMINAR
    @DeleteMapping("/prestamo/{id}")
    private ResponseEntity<?> eliminar(@PathVariable Integer id){

        Map<String, Object> response = new HashMap<>();

        try {
            prestamoService.eliminar(id);
        }
        catch (DataAccessException e){
            response.put("mensaje: ", "Error al eliminar el prestamo en la base de datos");
            response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
            return  new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "Prestamo eliminado con exito!");
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

    }
}

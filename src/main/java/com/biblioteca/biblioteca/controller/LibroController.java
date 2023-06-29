package com.biblioteca.biblioteca.controller;

import com.biblioteca.biblioteca.models.Libro;
import com.biblioteca.biblioteca.service.LibroService;
import com.biblioteca.biblioteca.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:3000")
@Controller
@RequestMapping("/api")
public class LibroController {

    @Autowired
    private LibroService libroService;

    @Autowired
    private UploadService uploadService;

    //METODO PARA CONSULTAR
    @GetMapping("/listarlibros")
    private ResponseEntity<?> listar(){
        return ResponseEntity.ok(libroService.findAll());
    }

    //METODO BUSCAR POR ID
    @GetMapping("/libros/{id}")
    private  ResponseEntity<?> buscarid(@PathVariable Integer id){
        Libro libro =null;
        Map<String, Object> reponse = new HashMap<>();

        try {
            libro = libroService.findById(id);
        }
        catch (DataAccessException e){
            reponse.put("mensaje: ", "Error al conectar con la base de datos");
            reponse.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
            return  new ResponseEntity<Map<String, Object>>(reponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (libro == null){
            reponse.put("mensaje: ", "El libro con id :".concat(id.toString().concat("no existe")));
            return  new ResponseEntity<Map<String, Object>>(reponse, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Libro>(libro, HttpStatus.OK);
    }

    //METODO GUARDAR
    @PostMapping("/libros")
    private  ResponseEntity<?> guardar(@Validated @RequestBody Libro libro, BindingResult result){
        Libro libroNuevo = null;
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
            libroNuevo = libroService.save(libro);
        }
        catch (DataAccessException e){
            response.put("mensaje: ", "Error al realizar el insert en la base de datos");
            response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
            return  new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje:", libroNuevo);
        response.put("mensaje: ", "Libro creado con exito");

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    //METODO ACTUALIZAR
    @PutMapping("/libros/{id}")
    private  ResponseEntity<?> actualizar(@Validated @RequestBody Libro libro, BindingResult result, @PathVariable Integer id ){
        Libro libroActual = libroService.findById(id);
        Libro libroUpdate=null;
        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()){

            List<String> errors=result.getFieldErrors()
                    .stream()
                    .map(err ->"el campo '"+err.getField()+"'"+ err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("error", errors);

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        if (libroActual == null){
            response.put("mensaje: ", "No se puede editar el libro con id :".concat(id.toString().concat("no existe")));
            return  new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        try {
            libroActual.setNombre(libro.getNombre());
            libroActual.setCriticas(libro.getCriticas());
            libroActual.setSinopsis(libro.getSinopsis());
            libroActual.setIsbn(libro.getIsbn());
            libroUpdate = libroService.save(libroActual);
        }
        catch (DataAccessException e){
            response.put("mensaje: ", "Error al actualizar el libro en la base de datos");
            response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
            return  new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje:", libroUpdate);
        response.put("mensaje: ", "Libro actualizado con exito");

        return new  ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }


    //METODO ELIMINAR
    @DeleteMapping("/libros/{id}")
    private ResponseEntity<?> eliminar(@PathVariable Integer id){
        Map<String, Object> response = new HashMap<>();

        try {
            Libro libro = libroService.findById(id);
            String nombreFotoAntigua = libro.getFoto();

            uploadService.eliminar(nombreFotoAntigua);
            libroService.eliminar(id);
        }
        catch (DataAccessException e){
            response.put("mensaje: ", "Error al eliminar el libro en la base de datos");
            response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
            return  new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "Libro eliminado con exito!");
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

    //METODO PARA SUBIR ARCHIVOS
    @PostMapping("/libros/upload")
    public  ResponseEntity<?> upload(@RequestParam("archivo") MultipartFile archivo, @RequestParam Integer id){
        Map<String, Object> response = new HashMap<>();
        Libro libro = libroService.findById(id);

        if (!archivo.isEmpty()){

            String nombreArchivo = null;
            try {
                nombreArchivo = uploadService.copiar(archivo);
            } catch (IOException e) {
                response.put("mensaje: ", "Error al subir la imagen");
                response.put("error", e.getMessage().concat(":").concat(e.getCause().getMessage()));
                return  new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }

            String nombreFotoAntigua = libro.getFoto();

            uploadService.eliminar(nombreFotoAntigua);

            libro.setFoto(nombreArchivo);
            libroService.save(libro);

            response.put("cliente", libro);
            response.put("mensaje:", "Imagen subida correctamente" + nombreArchivo);
        }
        return new  ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    //METODO PARA VISUALIZAR FOTO
    @GetMapping("/upload/img/{nombrefoto:.+}")
    public ResponseEntity<Resource> verFoto(@PathVariable String nombrefoto){

        Resource recurso = null;

        try {
            recurso = uploadService.cargar(nombrefoto);
        } catch (MalformedURLException e){
            e.printStackTrace();
        }

        HttpHeaders cabecera = new HttpHeaders();
        cabecera.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"");
        return new ResponseEntity<Resource>(recurso,cabecera, HttpStatus.OK);
    }

    //LISTAR AUTORES
    @GetMapping("/libros/autores")
    private ResponseEntity<?> listaAutores (){return ResponseEntity.ok(libroService.findAllAutores());}





}

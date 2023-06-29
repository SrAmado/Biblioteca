package com.biblioteca.biblioteca.service.impl;

import com.biblioteca.biblioteca.service.UploadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class UploadServiceImpl implements UploadService {

    private final Logger log =  LoggerFactory.getLogger(UploadServiceImpl.class);

    //Ruta externa de la app
    private final static String DIRECTORIO_UPLOAD = "C:\\Spring\\uploaps";

    @Override
    public Resource cargar(String nombreFoto) throws MalformedURLException {

        Path rutaArchivo = getPath(nombreFoto);
        log.info(rutaArchivo.toString());
        Resource recurso = null;

        recurso = new UrlResource(rutaArchivo.toUri());

        if (!recurso.exists() && !recurso.isReadable()){
            rutaArchivo = Paths.get(DIRECTORIO_UPLOAD).resolve("no-foto.png").toAbsolutePath();

            recurso = new UrlResource (rutaArchivo.toUri());

            log.error("No se puede ver la foto:" + nombreFoto);
        }
        return recurso;
    }

    @Override
    public String copiar(MultipartFile archivo) throws IOException {

        String nombreArchivo = UUID.randomUUID().toString()+ "_" + archivo.getOriginalFilename().replace("", "");
        Path rutaArchivo = getPath(nombreArchivo);
        Files.copy(archivo.getInputStream(),rutaArchivo);
        return nombreArchivo;
    }

    @Override
    public Boolean eliminar(String nombreFoto) {

        if (nombreFoto !=null && nombreFoto.length() > 0){
            Path rutaFotoAntigua = Paths.get(DIRECTORIO_UPLOAD).resolve(nombreFoto).toAbsolutePath();
            File archivoAnterior = rutaFotoAntigua.toFile();

            if(archivoAnterior.exists() & archivoAnterior.canRead()){
                archivoAnterior.delete();
                return true;
            };
        }
        return false;
    }

    @Override
    public Path getPath(String nombreFoto) {
        return Paths.get(DIRECTORIO_UPLOAD).resolve(nombreFoto).toAbsolutePath();
    }
}

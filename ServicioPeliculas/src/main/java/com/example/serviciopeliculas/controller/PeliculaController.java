package com.example.serviciopeliculas.controller;

import com.example.serviciopeliculas.entity.Pelicula;
import com.example.serviciopeliculas.service.PeliculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/peliculas")
@CrossOrigin(origins = "http://localhost:5173/", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.PATCH, RequestMethod.DELETE})
public class PeliculaController {

    @Autowired
    PeliculaService service;

    @GetMapping
    public List<Pelicula> getAll() {
        return service.getListPelicula();
    }

    @GetMapping("{id}")
    public Pelicula getByIdPelicula(@PathVariable Long id) {
        return service.getById(id).orElse(null);
    }

    @PostMapping
    public Pelicula savePelicula(@RequestBody Pelicula bean) {
        return service.savePelicula(bean);
    }

    @GetMapping("/buscarPeliculas")
    public Set<Pelicula> buscarPeliculasPorNombre(@RequestParam("nombre") String nombre){
        return service.searchPeliculasNombre(nombre);
    }
    @GetMapping("/searchDateRange")
    public List<Pelicula> searchMoviesBetweenDates(@RequestParam("dateOne") String startDate,
                                                   @RequestParam("dateTwo") String endDate) throws ParseException {
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
        Date fechaParseadaUno = formatoFecha.parse(startDate);
        Date fechaParseadaDos = formatoFecha.parse(endDate);
        return service.findMoviesBetweenPublicationDates(fechaParseadaUno, fechaParseadaDos);
    }
/*
    @GetMapping("/latest")
    public List<Movie> getLatestMovies() {
        return service.getMoviesOrderedByPublicationDateDesc();
    }

 */
    @PostMapping("/registrar-imagen")
    public ResponseEntity<String> cargarImagen(@RequestPart("anio") String anio,
                                               @RequestPart("descripcion") String descripcion,
                                               @RequestPart("genero") String genero,
                                               @RequestPart("imagen") MultipartFile imagen,
                                               @RequestPart("nombre") String nombre,
                                               @RequestPart("director") String director,
                                               @RequestPart("fecha") String fecha
                                               ) throws IOException {
        try {
            String rutaImagen = "C:/Users/marco/OneDrive/Escritorio/WEB/peliculas/src/img/peliculas/" + imagen.getOriginalFilename();
            File dest = new File(rutaImagen);
            imagen.transferTo(dest);
            Pelicula peli = new Pelicula();
            peli.setNombre(nombre);
            peli.setDescripcion(descripcion);
            peli.setAnio(anio);
            peli.setGenero(genero);
            peli.setNombreDirector(director);
            SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
            Date fechaParseada = formatoFecha.parse(fecha);
            peli.setFechaPublicacion(fechaParseada);
            System.out.println(fecha);
            System.out.println(imagen.getOriginalFilename() + "");
            peli.setImagen("../src/img/peliculas/" + imagen.getOriginalFilename() + "");
            service.savePelicula(peli);

            return ResponseEntity.ok("Imagen cargada exitosamente");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (IllegalStateException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}

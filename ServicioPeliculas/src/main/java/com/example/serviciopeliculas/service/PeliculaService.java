package com.example.serviciopeliculas.service;

import com.example.serviciopeliculas.entity.Pelicula;
import com.example.serviciopeliculas.repository.PeliculaRepository;
import jakarta.servlet.http.PushBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class PeliculaService {

    @Autowired
    private PeliculaRepository repo;

    public List<Pelicula> getListPelicula(){
        return repo.findAll();
    }

    public Optional<Pelicula> getById(Long id){
        return repo.findById(id);
    }

    public Pelicula savePelicula(Pelicula bean){
        return repo.save(bean);
    }

    public void deletePelicula (Long id){
        repo.deleteById(id);
    }

    public PeliculaService(PeliculaRepository peliculaRepo) {
        this.repo= peliculaRepo;
    }

    public List<Pelicula> findByNombre(String nombre){
        return repo.findByNombre(nombre);
    }

    public Set<Pelicula> searchPeliculasNombre(String buscar){
        List<Pelicula> peliculasNombre = repo.findByNombre(buscar);
        List<Pelicula> peliculasGenero = repo.findByGenero(buscar);
        List<Pelicula> peliculasDirector = repo.findByNombreDirectorContaining(buscar);
        List<Pelicula> peliculasAnio = repo.findByAnio(buscar);
        Set<Pelicula> respuesta = new HashSet<>();
        respuesta.addAll(peliculasNombre);
        respuesta.addAll(peliculasDirector);
        respuesta.addAll(peliculasGenero);
        respuesta.addAll(peliculasAnio);
        return respuesta;
    }

    public List<Pelicula> findMoviesBetweenPublicationDates(Date startDate, Date endDate) {
        return repo.findByFechaPublicacionBetween(startDate, endDate);
    }

    /*
    public List<Movie> getMoviesOrderedByPublicationDateDesc() {
        return repo.findByOrderByPublicationDateDesc();
    }
    */
}

package com.example.serviciopeliculas.repository;

import com.example.serviciopeliculas.entity.Pelicula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface PeliculaRepository extends JpaRepository<Pelicula,Long> {
    List<Pelicula> findByNombre(String nombre);

    List<Pelicula> findByNombreDirectorContaining(String director);

    List<Pelicula> findByFechaPublicacionBetween(Date fechaUno, Date fechaDos);

    List<Pelicula> findByGenero(String genero);

    List<Pelicula> findByAnio(String anio);
}

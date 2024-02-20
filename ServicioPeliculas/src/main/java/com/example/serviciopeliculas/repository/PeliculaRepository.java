package com.example.serviciopeliculas.repository;

import com.example.serviciopeliculas.entity.Pelicula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PeliculaRepository extends JpaRepository<Pelicula,Long> {

    @Query(value="SELECT * FROM pelicula WHERE nombre = ?", nativeQuery = true)
    List<Pelicula> findByNombre(String nombre);

    @Query(value="SELECT * FROM pelicula WHERE nombre_director =  ?",nativeQuery=true)
    List<Pelicula> findByNombreDirector(String nombreDirector);
}

package com.example.serviciopeliculas.service;

import com.example.serviciopeliculas.entity.Pelicula;
import com.example.serviciopeliculas.repository.PeliculaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public List<Pelicula> findByNombreDirector(String nombre_director){
        return repo.findByNombreDirector(nombre_director);
    }
}

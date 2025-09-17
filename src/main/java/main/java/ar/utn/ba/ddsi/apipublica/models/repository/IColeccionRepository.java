package main.java.ar.utn.ba.ddsi.apipublica.models.repository;


import main.java.ar.utn.ba.ddsi.apipublica.models.entities.Coleccion;

import java.util.List;

public interface IColeccionRepository {
    public void save(Coleccion coleccion);
    public List<Coleccion> findAll();
    public Coleccion findById(Long id);
    public void delete(Coleccion coleccion);
}

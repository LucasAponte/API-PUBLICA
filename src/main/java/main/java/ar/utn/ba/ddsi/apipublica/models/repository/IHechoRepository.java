package main.java.ar.utn.ba.ddsi.apipublica.models.repository;

import main.java.ar.utn.ba.ddsi.apipublica.models.entities.Hecho;

import java.util.List;

public interface IHechoRepository {
    public void save(Hecho hecho);
    public List<Hecho> findAll();
    public Hecho findById(Long id);
    public void delete(Hecho hecho);
}

package main.java.ar.utn.ba.ddsi.apipublica.models.repository;

import main.java.ar.utn.ba.ddsi.apipublica.models.entities.SolicitudEliminacion;

import java.util.List;

public interface ISolicitudRepository {
    public void save(SolicitudEliminacion solicitudEliminacion);
    public List<SolicitudEliminacion> findAll();
    public SolicitudEliminacion findById(Long id);
    public void delete(SolicitudEliminacion solicitudEliminacion);
}

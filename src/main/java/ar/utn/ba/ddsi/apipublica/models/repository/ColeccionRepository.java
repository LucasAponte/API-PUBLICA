package ar.utn.ba.ddsi.apipublica.models.repository;


import ar.utn.ba.ddsi.apipublica.models.entities.Coleccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ColeccionRepository extends JpaRepository<Coleccion,Long> {
}

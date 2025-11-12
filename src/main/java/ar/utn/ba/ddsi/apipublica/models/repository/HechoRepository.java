package ar.utn.ba.ddsi.apipublica.models.repository;

import ar.utn.ba.ddsi.apipublica.models.entities.Hecho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface HechoRepository extends JpaRepository<Hecho, Long> {
    //REVISAR la Consulta
    @Query("SELECT h FROM Hecho h " +
            "WHERE (:categoriaNombre IS NULL OR LOWER(h.categoria.nombre) = LOWER(:categoriaNombre)) " +
            "AND (:repDesde IS NULL OR h.fechaDeCarga >= :repDesde) " +
            "AND (:repHasta IS NULL OR h.fechaDeCarga <= :repHasta) " +
            "AND (:acaDesde IS NULL OR h.fecha >= :acaDesde) " +
            "AND (:acaHasta IS NULL OR h.fecha <= :acaHasta) " +
            "AND (:lat IS NULL OR :lon IS NULL OR (ABS(h.ubicacion.latitud - :lat) <= :tol AND ABS(h.ubicacion.longitud - :lon) <= :tol))")
    List<Hecho> buscarHechosSegun(
            @Param("categoriaNombre") String categoriaNombre,
            @Param("repDesde") LocalDate repDesde,
            @Param("repHasta") LocalDate repHasta,
            @Param("acaDesde") LocalDate acaDesde,
            @Param("acaHasta") LocalDate acaHasta,
            @Param("lat") Float lat,
            @Param("lon") Float lon
    );
}


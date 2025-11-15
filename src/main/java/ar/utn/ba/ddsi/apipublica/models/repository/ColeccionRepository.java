package ar.utn.ba.ddsi.apipublica.models.repository;


import ar.utn.ba.ddsi.apipublica.models.entities.Coleccion;
import ar.utn.ba.ddsi.apipublica.models.entities.Hecho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
@Repository
public interface ColeccionRepository extends JpaRepository<Coleccion,Long> {
    // Consulta que une con HechoXColeccion para filtrar por coleccion y opcionalmente por consensuado
    @Query("SELECT h FROM HechoXColeccion hxc JOIN hxc.hecho h " +
            "WHERE hxc.coleccion.id_coleccion = :coleccionId " +
            "AND (:categoriaNombre IS NULL OR LOWER(h.categoria.nombre) = LOWER(:categoriaNombre)) " +
            "AND (:repDesde IS NULL OR h.fechaDeCarga >= :repDesde) " +
            "AND (:repHasta IS NULL OR h.fechaDeCarga <= :repHasta) " +
            "AND (:acaDesde IS NULL OR h.fecha >= :acaDesde) " +
            "AND (:acaHasta IS NULL OR h.fecha <= :acaHasta) " +
            "AND (:lat IS NULL OR :lon IS NULL OR (ABS(h.ubicacion.latitud - :lat) <= :delta AND ABS(h.ubicacion.longitud - :lon) <= :delta)) " +
            "AND (:curado IS NULL OR hxc.consensuado = :curado)")
    List<Hecho> buscarEnColeccionSegun(
            @Param("coleccionId") Long coleccionId,
            @Param("categoriaNombre") String categoriaNombre,
            @Param("repDesde") LocalDate repDesde,
            @Param("repHasta") LocalDate repHasta,
            @Param("acaDesde") LocalDate acaDesde,
            @Param("acaHasta") LocalDate acaHasta,
            @Param("lat") Float lat,
            @Param("lon") Float lon,
            @Param("delta") Float delta,
            @Param("curado") Boolean curado
    );
}

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
    // Consulta JPQL que respeta los nombres de atributos de la entidad Hecho y Ubicacion

    //Mover todo esto al filtrador
    @Query("SELECT h FROM Hecho h " +
            "WHERE (:categoriaNombre IS NULL OR LOWER(h.categoria.nombre) = LOWER(:categoriaNombre)) " +
            "AND (:repDesde IS NULL OR h.fechaDeCarga >= :repDesde) " +
            "AND (:repHasta IS NULL OR h.fechaDeCarga <= :repHasta) " +
            "AND (:acaDesde IS NULL OR h.fecha >= :acaDesde) " +
            "AND (:acaHasta IS NULL OR h.fecha <= :acaHasta) " +
            // Para coordenadas: si ambas son NULL entonces no filtra; si no, compara con un rango (ej. ~1km -> 0.01 grados aprox)
            "AND (:lat IS NULL OR :lon IS NULL OR (ABS(h.ubicacion.latitud - :lat) <= :delta AND ABS(h.ubicacion.longitud - :lon) <= :delta))"+
            "AND (:texto IS NULL OR (" +
                "LOWER(h.titulo) LIKE LOWER(CONCAT('%', :texto, '%')) " +
                "OR LOWER(h.descripcion) LIKE LOWER(CONCAT('%', :texto, '%')) " +
                "OR (h.fuente IS NOT NULL AND LOWER(h.fuente.nombre) LIKE LOWER(CONCAT('%', :texto, '%')))" +
            "))")
    List<Hecho> buscarHechosSegun(
            @Param("categoriaNombre") String categoriaNombre,
            @Param("repDesde") LocalDate repDesde,
            @Param("repHasta") LocalDate repHasta,
            @Param("acaDesde") LocalDate acaDesde,
            @Param("acaHasta") LocalDate acaHasta,
            @Param("lat") Float lat,
            @Param("lon") Float lon,
            @Param("delta") Float delta,
            @Param("texto") String texto
    );
}

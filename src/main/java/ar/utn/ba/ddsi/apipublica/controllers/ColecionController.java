package ar.utn.ba.ddsi.apipublica.controllers;


import ar.utn.ba.ddsi.apipublica.models.dtos.HechoFilterDTO;
import ar.utn.ba.ddsi.apipublica.models.entities.*;
import ar.utn.ba.ddsi.apipublica.services.ColeccionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RestController
@RequestMapping("/colecciones")
public class ColecionController {
   // ● Consulta de hechos dentro de una colección.
    private final ColeccionService coleccionService;
    public ColecionController(ColeccionService coleccionService) {
        this.coleccionService = coleccionService;
    }

    @GetMapping("/{coleccionID}/hechos")
    public ResponseEntity<Object> listarHechosDeUnaColeccion(
            @PathVariable("coleccionID") Long coleccionID,
            @RequestParam(required = false) String modoNavegacion,
            @RequestParam(value = "categoria", required = false) String categoria,
            @RequestParam(value = "fecha_reporte_desde", required = false) String fechaReporteDesde,
            @RequestParam(value = "fecha_reporte_hasta", required = false) String fechaReporteHasta,
            @RequestParam(value = "fecha_acontecimiento_desde", required = false) String fechaAcontecimientoDesde,
            @RequestParam(value = "fecha_acontecimiento_hasta", required = false) String fechaAcontecimientoHasta,
            @RequestParam(value = "ubicacion_latitud", required = false) String latStr,
            @RequestParam(value = "ubicacion_longitud", required = false) String lonStr
    ) {
        HechoFilterDTO filter = new HechoFilterDTO(categoria, fechaReporteDesde, fechaReporteHasta,
                fechaAcontecimientoDesde, fechaAcontecimientoHasta, latStr, lonStr);

        try {
            List<Hecho> resultados = coleccionService.buscarHechosSegun(filter, modoNavegacion, coleccionID);
            return ResponseEntity.status(HttpStatus.OK).body(resultados);

        } catch (IllegalArgumentException iae) {
        } catch (Exception ex) {
            return ResponseEntity.status(500).body("Error buscando hechos: " + ex.getMessage());
        }
        return ResponseEntity.status(400).body("Error en los parámetros de búsqueda.");
    }
}

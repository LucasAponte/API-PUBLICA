package ar.utn.ba.ddsi.apipublica.controllers;


import ar.utn.ba.ddsi.apipublica.models.dtos.ColeccionOutputDTO;
import ar.utn.ba.ddsi.apipublica.models.dtos.HechoFilterDTO;
import ar.utn.ba.ddsi.apipublica.models.dtos.ColeccionFilterDTO;
import ar.utn.ba.ddsi.apipublica.models.dtos.HechoOutputDTO;
import ar.utn.ba.ddsi.apipublica.models.entities.*;
import ar.utn.ba.ddsi.apipublica.services.ColeccionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/colecciones")
public class ColeccionController {
    // ● Consulta de hechos dentro de una colección.
    private final ColeccionService coleccionService;

    public ColeccionController(ColeccionService coleccionService) {
        this.coleccionService = coleccionService;
    }

    @GetMapping
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<Object> listarColecciones(
            @RequestParam(value = "titulo", required = false) String titulo,
            @RequestParam(value = "descripcion", required = false) String descripcion,
            @RequestParam(value = "tipo_algoritmo", required = false) String tipoAlgoritmo,
            // permitir repetición: ?fuente_id=1&fuente_id=2 o ?fuente_id=1,2
            @RequestParam(value = "fuente_id", required = false) List<String> fuenteId
    ) {
        ColeccionFilterDTO filter = new ColeccionFilterDTO(titulo, descripcion, tipoAlgoritmo, fuenteId);

        try {
            List<ColeccionOutputDTO> colecciones = coleccionService.buscarColeccionesSegun(filter);
            return ResponseEntity.status(HttpStatus.OK).body(colecciones);
        } catch (IllegalArgumentException iae) {
            return ResponseEntity.status(400).body("Error en los parámetros de búsqueda: " + iae.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(500).body("Error buscando colecciones: " + ex.getMessage());
        }
    }

    @GetMapping("/{coleccionID}/hechos")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<Object> listarHechosDeUnaColeccion(
            @PathVariable("coleccionID") Long coleccionID,
            @RequestParam(required = false) String modoNavegacion,
            @RequestParam(value = "categoria", required = false) String categoria,
            @RequestParam(value = "fecha_reporte_desde", required = false) String fechaReporteDesde,
            @RequestParam(value = "fecha_reporte_hasta", required = false) String fechaReporteHasta,
            @RequestParam(value = "fecha_acontecimiento_desde", required = false) String fechaAcontecimientoDesde,
            @RequestParam(value = "fecha_acontecimiento_hasta", required = false) String fechaAcontecimientoHasta,
            @RequestParam(value = "ubicacion_latitud", required = false) String latStr,
            @RequestParam(value = "ubicacion_longitud", required = false) String lonStr,
            @RequestParam(value = "q", required = false) String textoLibre
    ) {
        HechoFilterDTO filter = new HechoFilterDTO(categoria, fechaReporteDesde, fechaReporteHasta,
                fechaAcontecimientoDesde, fechaAcontecimientoHasta, latStr, lonStr, textoLibre);

        try {
            List<HechoOutputDTO> resultados = coleccionService.buscarHechosSegun(filter, modoNavegacion, coleccionID);
            return ResponseEntity.status(HttpStatus.OK).body(resultados);

        } catch (IllegalArgumentException iae) {
            return ResponseEntity.status(400).body("Error en los parámetros de búsqueda: " + iae.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(500).body("Error buscando hechos: " + ex.getMessage());
        }
    }
    @GetMapping("/{coleccionID}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<Object> obtenerColeccionPorId(
            @PathVariable("coleccionID") Long coleccionID) {

        try {
            ColeccionOutputDTO coleccion = coleccionService.buscarColeccionPorId(coleccionID);

            if (coleccion == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("La colección con ID " + coleccionID + " no existe");
            }

            return ResponseEntity.ok(coleccion);

        } catch (Exception ex) {
            return ResponseEntity.status(500)
                    .body("Error obteniendo la colección: " + ex.getMessage());
        }
    }
}

package ar.utn.ba.ddsi.apipublica.controllers;


import ar.utn.ba.ddsi.apipublica.models.dtos.ColeccionOutputDTO;
import ar.utn.ba.ddsi.apipublica.models.dtos.HechoFilterDTO;
import ar.utn.ba.ddsi.apipublica.models.dtos.ColeccionFilterDTO;
import ar.utn.ba.ddsi.apipublica.models.dtos.HechoOutputDTO;
import ar.utn.ba.ddsi.apipublica.models.entities.*;
import ar.utn.ba.ddsi.apipublica.services.ColeccionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/colecciones")
@CrossOrigin(origins = "http://localhost:3000")
@Tag(
        name = "Colecciones",
        description = "Endpoints para la consulta de colecciones y hechos asociados"
)
public class ColeccionController {
    // ● Consulta de hechos dentro de una colección.
    private final ColeccionService coleccionService;

    public ColeccionController(ColeccionService coleccionService) {
        this.coleccionService = coleccionService;
    }

    @GetMapping
    @CrossOrigin(origins = "http://localhost:3000")
    @Operation(
            summary = "Buscar colecciones",
            description = "Permite buscar colecciones aplicando distintos filtros opcionales"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Listado de colecciones obtenido correctamente",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = ColeccionOutputDTO.class)
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Error en los parámetros de búsqueda"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor"
            )
    })
    public ResponseEntity<Object> listarColecciones(
            @Parameter(
                    description = "Filtrar por título de la colección",
                    example = "Colección histórica"
            )
            @RequestParam(value = "titulo", required = false) String titulo,
            @Parameter(
                    description = "Filtrar por descripción",
                    example = "hechos relevantes"
            )
            @RequestParam(value = "descripcion", required = false) String descripcion,
            @Parameter(
                    description = "Tipo de algoritmo utilizado",
                    example = "CONSENSO"
            )
            @RequestParam(value = "tipo_algoritmo", required = false) String tipoAlgoritmo,
            @Parameter(
                    description = "IDs de fuentes (puede repetirse: ?fuente_id=1&fuente_id=2)",
                    example = "1"
            )
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
    @Operation(
            summary = "Listar hechos de una colección",
            description = "Devuelve una colección con sus hechos asociados, aplicando filtros opcionales"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Hechos obtenidos correctamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ColeccionOutputDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Error en los parámetros de búsqueda"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Colección no encontrada"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor"
            )
    })
    public ResponseEntity<Object> listarHechosDeUnaColeccion(
            @Parameter(
                    description = "ID de la colección",
                    example = "10",
                    required = true
            )
            @PathVariable("coleccionID") Long coleccionID,
            @Parameter(
                    description = "Modo de navegación de los hechos",
                    example = "CURADA"
            )
            @RequestParam(value = "modoNavegacion" , required = false) String modoNavegacion,
            @Parameter(description = "Categoría del hecho", example = "Política")
            @RequestParam(value = "categoria", required = false) String categoria,
            @Parameter(description = "Fecha de reporte desde (YYYY-MM-DD)", example = "2024-01-01")
            @RequestParam(value = "fecha_reporte_desde", required = false) String fechaReporteDesde,
            @Parameter(description = "Fecha de reporte hasta (YYYY-MM-DD)", example = "2024-12-31")
            @RequestParam(value = "fecha_reporte_hasta", required = false) String fechaReporteHasta,
            @Parameter(description = "Fecha de acontecimiento desde", example = "2023-01-01")
            @RequestParam(value = "fecha_acontecimiento_desde", required = false) String fechaAcontecimientoDesde,
            @Parameter(description = "Fecha de acontecimiento hasta", example = "2023-12-31")
            @RequestParam(value = "fecha_acontecimiento_hasta", required = false) String fechaAcontecimientoHasta,
            @Parameter(description = "Provincia del hecho", example = "Buenos Aires")
            @RequestParam(value = "provincia", required = false) String provincia,
            @Parameter(description = "Texto libre de búsqueda", example = "elecciones")
            @RequestParam(value = "q", required = false) String textoLibre,
            @Parameter(description = "Tipo de fuente", example = "ESTATICA")
            @RequestParam(value = "fuenteTipo", required = false) String fuenteTipo)

    {
        ColeccionOutputDTO cole =  coleccionService.buscarColeccionPorId(coleccionID);
        HechoFilterDTO filter = new HechoFilterDTO(categoria, fechaReporteDesde, fechaReporteHasta,
                fechaAcontecimientoDesde, fechaAcontecimientoHasta, provincia, textoLibre,fuenteTipo);
        try {
            List<HechoOutputDTO> resultados = coleccionService.buscarHechosSegun(filter, modoNavegacion, coleccionID);
            cole.setHechos(resultados);
            return ResponseEntity.status(HttpStatus.OK).body(cole);
        } catch (IllegalArgumentException iae) {
            return ResponseEntity.status(400).body("Error en los parámetros de búsqueda: " + iae.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(500).body("Error buscando hechos: " + ex.getMessage());
        }
    }
    @GetMapping("/{coleccionID}")
    @CrossOrigin(origins = "http://localhost:3000")
    @Operation(
            summary = "Obtener colección por ID",
            description = "Devuelve una colección específica a partir de su identificador"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Colección encontrada",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ColeccionOutputDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Colección no encontrada"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor"
            )
    })
    public ResponseEntity<Object> obtenerColeccionPorId(
            @Parameter(
                    description = "ID de la colección",
                    example = "10",
                    required = true
            )
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

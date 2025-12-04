package ar.utn.ba.ddsi.apipublica.controllers;

import ar.utn.ba.ddsi.apipublica.models.dtos.HechoCreateDTO;
import ar.utn.ba.ddsi.apipublica.models.dtos.HechoFilterDTO;
import ar.utn.ba.ddsi.apipublica.models.dtos.HechoOutputDTO;
import ar.utn.ba.ddsi.apipublica.models.entities.Hecho;
import ar.utn.ba.ddsi.apipublica.services.IHechoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hechos")
@CrossOrigin(origins = "http://localhost:3000")
public class HechoController {

    private final IHechoService hechoService;

    public HechoController(IHechoService hechoService) {
        this.hechoService = hechoService;
    }

    @PostMapping//ResponseEntity???
    public ResponseEntity<?> crearHecho(@RequestBody HechoCreateDTO dto) {
        if (dto == null || dto.getTitulo() == null || dto.getTitulo().isBlank()) {
            return ResponseEntity.badRequest().body("Titulo es obligatorio");
        }
        try {
            Hecho hecho = hechoService.crearHecho(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(hecho);
        } catch (Exception ex) {
            return ResponseEntity.status(500).body("Error creando hecho: " + ex.getMessage());
        }
    }

    //GET /hechos con filtros por query params
    @GetMapping
    @CrossOrigin(origins = "http://localhost:3000") // ⬅️ Añadir esto
    public ResponseEntity<?> listarHechosSegun(
            @RequestParam(value = "categoria", required = false) String categoria,
            @RequestParam(value = "fecha_reporte_desde", required = false) String fechaReporteDesde,
            @RequestParam(value = "fecha_reporte_hasta", required = false) String fechaReporteHasta,
            @RequestParam(value = "fecha_acontecimiento_desde", required = false) String fechaAcontecimientoDesde,
            @RequestParam(value = "fecha_acontecimiento_hasta", required = false) String fechaAcontecimientoHasta,
            @RequestParam(value = "ubicacion_latitud", required = false) String latStr,
            @RequestParam(value = "ubicacion_longitud", required = false) String lonStr,
            @RequestParam(value = "q", required = false) String textoLibre //Titulo,Descripcion,Fuente, esasa cosas son las que busca el texto libre
    ) {
        HechoFilterDTO filter = new HechoFilterDTO(categoria, fechaReporteDesde, fechaReporteHasta,
                fechaAcontecimientoDesde, fechaAcontecimientoHasta, latStr, lonStr, textoLibre);

        try {
            List<HechoOutputDTO> resultados = hechoService.buscarConFiltro(filter);
            return ResponseEntity.status(HttpStatus.OK).body(resultados);

        } catch (IllegalArgumentException iae) {
            return ResponseEntity.badRequest().body(iae.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(500).body("Error buscando hechos: " + ex.getMessage());
        }
    }
    @GetMapping("/{id}")
    @CrossOrigin(origins = "http://localhost:3000") // ⬅️ Añadir esto
    public ResponseEntity<Object> obtenerHecho(@PathVariable("id") Long id) {
        try {
            HechoOutputDTO hechoDTO = hechoService.obtenerHechoPorId(id);
            return ResponseEntity.status(HttpStatus.OK).body(hechoDTO);

        } catch (RuntimeException ex) {
            // Asumimos que si salta la excepción del service es porque no existe (404)
            // Podrías refinar esto creando una excepción personalizada 'ResourceNotFoundException'
            if (ex.getMessage().contains("no encontrado")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error obteniendo el hecho: " + ex.getMessage());
        }
    }
}

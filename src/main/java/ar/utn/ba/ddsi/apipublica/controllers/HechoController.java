package ar.utn.ba.ddsi.apipublica.controllers;

import ar.utn.ba.ddsi.apipublica.models.dtos.HechoCreateDTO;
import ar.utn.ba.ddsi.apipublica.models.dtos.HechoFilterDTO;
import ar.utn.ba.ddsi.apipublica.models.dtos.HechoRtaDTO;
import ar.utn.ba.ddsi.apipublica.models.entities.Hecho;
import ar.utn.ba.ddsi.apipublica.services.IHechoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hechos")
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
    public ResponseEntity<?> listarHechosSegun(
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
            List<HechoRtaDTO> resultados = hechoService.buscarConFiltro(filter);
            return ResponseEntity.status(HttpStatus.OK).body(resultados);

        } catch (IllegalArgumentException iae) {
            return ResponseEntity.badRequest().body(iae.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(500).body("Error buscando hechos: " + ex.getMessage());
        }
    }
}

package ar.utn.ba.ddsi.apipublica.controllers;

import ar.utn.ba.ddsi.apipublica.models.dtos.SolicitudCreateDTO;
import ar.utn.ba.ddsi.apipublica.models.entities.SolicitudEliminacion;
import ar.utn.ba.ddsi.apipublica.services.ISolicitudService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/solicitudes")
public class SolicitudController {

    private final ISolicitudService solicitudService;

    public SolicitudController(ISolicitudService solicitudService) {
        this.solicitudService = solicitudService;
    }

    @PostMapping
    public ResponseEntity<?> crearSolicitud(@RequestBody SolicitudCreateDTO dto) {
        try {
            //ACÄ funca pero debería poder controlar si esiste o no el idcontribuyente por ahora safa
            SolicitudEliminacion solicitud = solicitudService.crearSolicitud(dto);
            return ResponseEntity.ok(solicitud);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(500).body("Error interno");
        }
    }

    @GetMapping
    public ResponseEntity<List<SolicitudEliminacion>> listar() {
        return ResponseEntity.ok(solicitudService.listarSolicitudes());
    }
}


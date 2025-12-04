package ar.utn.ba.ddsi.apipublica.controllers;

import ar.utn.ba.ddsi.apipublica.models.dtos.SolicitudCreateDTO;
import ar.utn.ba.ddsi.apipublica.models.dtos.SolicitudOutputDTO;
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
            System.out.println("Iniciando solicitud" + dto.getIdContribuyente() + " - " + dto.getIdHecho()
            + " - " + dto.getMotivo());
            //ACÄ funca pero debería poder controlar si esiste o no el idcontribuyente por ahora safa
            SolicitudEliminacion solicitud = solicitudService.crearSolicitud(dto);
            SolicitudOutputDTO solicitudOutputDTO = new SolicitudOutputDTO(solicitud);
            System.out.println(solicitudOutputDTO);
            return ResponseEntity.ok(solicitudOutputDTO);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(500).body("Error interno");
        }
    }

    @GetMapping
    public ResponseEntity<List<SolicitudOutputDTO>> listar() {
        return ResponseEntity.ok(solicitudService.listarSolicitudes());
    }

    @GetMapping ("/{id}")
    public ResponseEntity<SolicitudOutputDTO> obtenerEstadistica(@PathVariable Long id ){
        return ResponseEntity.ok(this.solicitudService.obtenerSolicitudPorId(id));
    }
}


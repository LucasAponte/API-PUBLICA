package ar.utn.ba.ddsi.apipublica.controllers;

import ar.utn.ba.ddsi.apipublica.models.dtos.SolicitudCreateDTO;
import ar.utn.ba.ddsi.apipublica.models.dtos.SolicitudOutputDTO;
import ar.utn.ba.ddsi.apipublica.models.entities.SolicitudEliminacion;
import ar.utn.ba.ddsi.apipublica.services.ISolicitudService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/solicitudes")
@CrossOrigin(origins = "http://localhost:3000")
@Tag(
        name = "Solicitudes",
        description = "Gestión de solicitudes de eliminación de hechos"
)
public class SolicitudController {

    private final ISolicitudService solicitudService;

    public SolicitudController(ISolicitudService solicitudService) {
        this.solicitudService = solicitudService;
    }

    @PostMapping
    @CrossOrigin(origins = "http://localhost:3000")
    @Operation(
            summary = "Crear una solicitud",
            description = "Crea una solicitud de eliminación asociada a un hecho y un contribuyente"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Solicitud creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
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
    @CrossOrigin(origins = "http://localhost:3000") // ⬅️ Añadir esto
    @Operation(
            summary = "Listar solicitudes",
            description = "Devuelve todas las solicitudes de eliminación registradas"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente")
    })
    public ResponseEntity<List<SolicitudOutputDTO>> listar() {
        return ResponseEntity.ok(solicitudService.listarSolicitudes());
    }

    @GetMapping ("/{id}")
    @CrossOrigin(origins = "http://localhost:3000")
    @Operation(
            summary = "Obtener solicitud por ID",
            description = "Devuelve el detalle de una solicitud según su identificador"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Solicitud encontrada"),
            @ApiResponse(responseCode = "404", description = "Solicitud no encontrada")
    })
    public ResponseEntity<SolicitudOutputDTO> obtenerEstadistica(@PathVariable Long id ){
        return ResponseEntity.ok(this.solicitudService.obtenerSolicitudPorId(id));
    }
}


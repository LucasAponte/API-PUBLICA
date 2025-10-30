package ar.utn.ba.ddsi.apipublica.controllers;

import ar.utn.ba.ddsi.apipublica.models.dtos.HechoCreateDTO;
import ar.utn.ba.ddsi.apipublica.models.entities.Coleccion;
import ar.utn.ba.ddsi.apipublica.models.entities.Hecho;
import ar.utn.ba.ddsi.apipublica.models.repository.ColeccionRepository;
import ar.utn.ba.ddsi.apipublica.models.repository.HechoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/hechos")
public class HechoController {

    private final HechoRepository hechoRepository;
    private final ColeccionRepository coleccionRepository;

    public HechoController(HechoRepository hechoRepository, ColeccionRepository coleccionRepository) {
        this.hechoRepository = hechoRepository;
        this.coleccionRepository = coleccionRepository;
    }

    @PostMapping//ResponseEntity???
    public ResponseEntity<?> crearHecho(@RequestBody HechoCreateDTO dto) {
        if (dto == null || dto.getTitulo() == null || dto.getTitulo().isBlank()) {
            return ResponseEntity.badRequest().body("Titulo es obligatorio");
        }
        try {
            Hecho hecho = new Hecho();
            hecho.setTitulo(dto.getTitulo());
            hecho.cambiarDescripcion(dto.getDescripcion());
            if (dto.getFecha() != null && !dto.getFecha().isBlank()) {
                hecho.setFecha(LocalDate.parse(dto.getFecha()));
            }

            Hecho saved = hechoRepository.save(hecho);
            return ResponseEntity.ok(saved);
        } catch (Exception ex) {
            return ResponseEntity.status(500).body("Error creando hecho: " + ex.getMessage());
        }
    }
}


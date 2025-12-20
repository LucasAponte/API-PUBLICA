package ar.utn.ba.ddsi.apipublica.controllers;
import ar.utn.ba.ddsi.apipublica.models.dtos.ProvinciaOutputDTO;
import ar.utn.ba.ddsi.apipublica.services.ProvinciaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/provincias")
@CrossOrigin(origins = "http://localhost:3000")
public class ProvinciaController {

    private final ProvinciaService provinciaService;

    public ProvinciaController(ProvinciaService provinciaService) {
        this.provinciaService = provinciaService;
    }

    @GetMapping
    public ResponseEntity<List<ProvinciaOutputDTO>> listarProvincias() {
        List<ProvinciaOutputDTO> provincias = provinciaService.findAll();
        return ResponseEntity.ok(provincias);
    }
}



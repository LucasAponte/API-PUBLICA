package ar.utn.ba.ddsi.apipublica.controllers;


import ar.utn.ba.ddsi.apipublica.models.entities.*;
import ar.utn.ba.ddsi.apipublica.models.entities.EnumModoNavegacion;
import ar.utn.ba.ddsi.apipublica.services.ColeccionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.ArrayList;
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
    public ResponseEntity<List<Hecho>> getHechosByColeccion(
            @PathVariable("coleccionID") Long coleccionID,
            @RequestParam(required = false) EnumModoNavegacion modoNavegacion
    )
    {
        List<Hecho> hechos = coleccionService.getHechosByColeccionAndModo(coleccionID, modoNavegacion);
        if (hechos == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(hechos);
    }

    @GetMapping("/{coleccionID}/hechos/filtrar")
    public ResponseEntity<List<Hecho>> filtrarHechos(
            @PathVariable("coleccionID") Long coleccionID,
            @RequestParam(required = false) String titulo,
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) String etiqueta,
            @RequestParam(required = false) String fechaDesde,
            @RequestParam(required = false) String fechaHasta,
            @RequestParam(required = false) Long fuenteId
    ) {
        List<InterfaceCondicion> condiciones = new ArrayList<>();

        if (titulo != null && !titulo.isBlank()) {
            condiciones.add(new CondicionTitulo(titulo));
        }
        if (categoria != null && !categoria.isBlank()) {
            condiciones.add(new CondicionCategoria(new Categoria(categoria)));
        }
        if (etiqueta != null && !etiqueta.isBlank()) {
            condiciones.add(new CondicionEtiqueta(new Etiqueta(etiqueta)));
        }

        try {
            LocalDate desde = fechaDesde != null ? LocalDate.parse(fechaDesde) : null;
            LocalDate hasta = fechaHasta != null ? LocalDate.parse(fechaHasta) : null;
            if (desde != null || hasta != null) {
                condiciones.add(new CondicionFechaRango(desde, hasta));
            }
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }

        if (fuenteId != null) {
            Fuente f = new Fuente();
            f.setId(fuenteId);
            condiciones.add(new CondicionOrigen(f));
        }

        List<Hecho> result = coleccionService.filtrarHechosConCondiciones(coleccionID, condiciones);
        if (result == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(result);
    }
}

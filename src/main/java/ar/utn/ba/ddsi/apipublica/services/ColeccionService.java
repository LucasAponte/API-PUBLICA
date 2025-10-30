package ar.utn.ba.ddsi.apipublica.services;

import ar.utn.ba.ddsi.apipublica.models.entities.*;
import ar.utn.ba.ddsi.apipublica.models.entities.FiltradorDeHechos;
import ar.utn.ba.ddsi.apipublica.models.repository.ColeccionRepository;
import ar.utn.ba.ddsi.apipublica.models.repository.HechoRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ColeccionService {
    private final ColeccionRepository coleccionRepository;
    private final HechoRepository hechoRepository;
    private final FiltradorDeHechos filtradorDeHechos;

    public ColeccionService(ColeccionRepository coleccionRepository, HechoRepository hechoRepository, FiltradorDeHechos filtradorDeHechos) {
        this.coleccionRepository = coleccionRepository;
        this.hechoRepository = hechoRepository;
        this.filtradorDeHechos = filtradorDeHechos;
    }

    public List<Hecho> getHechosByColeccionId(Long id) {
        Coleccion coleccion = coleccionRepository.findById(id).orElse(null);
        if (coleccion == null) return null;
        return coleccion.getHechos();
    }


    public List<Hecho> getHechosByColeccionAndModo(Long coleccionId, EnumModoNavegacion modo) {
        var coleccion = coleccionRepository.findById(coleccionId).orElse(null);
        if (coleccion == null) return null;

        // Interpretar null/empty como NOCURADO
        if (modo == null || modo == EnumModoNavegacion.NOCURADO) {
            return coleccion.getHechos();
        }

        // Si es CURADO, aplicar algoritmo de consenso si existe
        if (modo == EnumModoNavegacion.CURADO) {
            if (coleccion.obtenerHechosConsensuados() != null) {
                return coleccion.obtenerHechosConsensuados();
            }
            return coleccion.getHechos();
        }

        return coleccion.getHechos();
    }

    public List<Hecho> filtrarHechosConCondiciones(Long coleccionId, List<InterfaceCondicion> condiciones) {
        var coleccion = coleccionRepository.findById(coleccionId).orElse(null);
        if (coleccion == null) return null;
        return filtradorDeHechos.devolverHechosAPartirDe(condiciones, coleccion.getHechos());
    }
}
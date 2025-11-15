package ar.utn.ba.ddsi.apipublica.services;

import ar.utn.ba.ddsi.apipublica.models.dtos.HechoFilterDTO;
import ar.utn.ba.ddsi.apipublica.models.entities.*;
import ar.utn.ba.ddsi.apipublica.models.repository.ColeccionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ColeccionService {
    //No tengo el Filtrador de Hechos, asi que delego todo al Repository ESTARÁ BIEN?????
    private final ColeccionRepository coleccionRepository;

    public ColeccionService(ColeccionRepository coleccionRepository) {
        this.coleccionRepository = coleccionRepository;
    }

    // Nuevo: buscar hechos de una colección delegando al repository con parámetros parsed del DTO
    public List<Hecho> buscarHechosSegun(HechoFilterDTO filter, String modoDeNavegacion, Long coleccionId) {
        if (coleccionId == null) {
            throw new IllegalArgumentException("El ID de la colección no puede ser nulo");
        }
        if (coleccionRepository.findById(coleccionId).isEmpty()) {
            throw new IllegalArgumentException("Colección no existe: " + coleccionId);
        }

        if (filter == null) filter = new HechoFilterDTO();

        // Validar y parsear el DTO (mueve la lógica de validación al propio DTO)
        filter.validateAndParse();

        // Normalizar categoria
        String categoriaNombre = null;
        if (filter.getCategoria() != null && !filter.getCategoria().isBlank()) {
            categoriaNombre = filter.getCategoria().trim();
        }

        // Determinar delta si hay coordenadas
        Float delta = null;
        if (filter.getUbicacionLatitudParsed() != null && filter.getUbicacionLongitudParsed() != null) {
            delta = 0.01f; // tolerancia por defecto
        }

        Boolean curado = null; // null significa no filtrar por consensuado
        if (modoDeNavegacion != null) {
            if (modoDeNavegacion.equalsIgnoreCase("CURADO")) curado = Boolean.TRUE;
            else if (modoDeNavegacion.equalsIgnoreCase("NOCURADO")) curado = Boolean.FALSE;
            else throw new IllegalArgumentException("Modo Navegacion incorrecto: " + modoDeNavegacion);
        }

        return coleccionRepository.buscarEnColeccionSegun(
                coleccionId,
                categoriaNombre,
                filter.getFechaReporteDesdeParsed(),
                filter.getFechaReporteHastaParsed(),
                filter.getFechaAcontecimientoDesdeParsed(),
                filter.getFechaAcontecimientoHastaParsed(),
                filter.getUbicacionLatitudParsed(),
                filter.getUbicacionLongitudParsed(),
                delta,
                curado
        );
    }
}
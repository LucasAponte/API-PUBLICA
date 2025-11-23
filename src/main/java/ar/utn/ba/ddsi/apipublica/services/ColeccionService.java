package ar.utn.ba.ddsi.apipublica.services;

import ar.utn.ba.ddsi.apipublica.models.dtos.HechoFilterDTO;
import ar.utn.ba.ddsi.apipublica.models.dtos.ColeccionFilterDTO;
import ar.utn.ba.ddsi.apipublica.models.entities.*;
import ar.utn.ba.ddsi.apipublica.models.repository.ColeccionRepository;
import ar.utn.ba.ddsi.apipublica.models.repository.FuenteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ColeccionService {
    //No tengo el Filtrador de Hechos, asi que delego todo al Repository ESTARÁ BIEN?????
    private final ColeccionRepository coleccionRepository;
    private final FuenteRepository fuenteRepository;

    public ColeccionService(ColeccionRepository coleccionRepository, FuenteRepository fuenteRepository) {
        this.coleccionRepository = coleccionRepository;
        this.fuenteRepository = fuenteRepository;
    }

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
            else throw new IllegalArgumentException("Modo Navegacion incorrecto: " + modoDeNavegacion);}
            String textoLibre = null;
            if (filter.getTextoLibre() != null && !filter.getTextoLibre().isBlank()) {
                textoLibre = filter.getTextoLibre().trim();

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
                    curado,
                    textoLibre
            );
        }

    public List<Coleccion> buscarColeccionesSegun(ColeccionFilterDTO filter) {
        if (filter == null) filter = new ColeccionFilterDTO();

        List<Long> fuenteIds = filter.parseFuenteIdsOrNull();

        String tipo = filter.getTipoAlgoritmo();
        if (tipo != null && tipo.isBlank()) tipo = null;
        String titulo = filter.getTitulo(); if (titulo != null && titulo.isBlank()) titulo = null;
        String descripcion = filter.getDescripcion(); if (descripcion != null && descripcion.isBlank()) descripcion = null;

        // Si se pasó fuente, validar su existencia
        if (fuenteIds != null) {
            for (Long fid : fuenteIds) {
                if (fuenteRepository.findById(fid).isEmpty()) {
                    throw new IllegalArgumentException("Fuente no existe: " + fid);
                }
            }
        }

        // Parsear tipo a Enum
        EnumTipoDeAlgoritmo tipoEnum = null;
        if (tipo != null) {
            try {
                tipoEnum = EnumTipoDeAlgoritmo.valueOf(tipo.toUpperCase());
            } catch (IllegalArgumentException iae) {
                throw new IllegalArgumentException("tipo_algoritmo inválido: " + tipo);
            }
        }

        return coleccionRepository.buscarColeccionesSegun(titulo, descripcion, tipoEnum, fuenteIds);
    }
}

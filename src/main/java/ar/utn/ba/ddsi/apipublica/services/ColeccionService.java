package ar.utn.ba.ddsi.apipublica.services;

import ar.utn.ba.ddsi.apipublica.models.dtos.HechoFilterDTO;
import ar.utn.ba.ddsi.apipublica.models.entities.EnumModoNavegacion;
import ar.utn.ba.ddsi.apipublica.models.entities.Hecho;
import ar.utn.ba.ddsi.apipublica.models.repository.ColeccionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ColeccionService {
    private final ColeccionRepository coleccionRepository;

    public ColeccionService(ColeccionRepository coleccionRepository) {
        this.coleccionRepository = coleccionRepository;
    }

    public List<Hecho> buscarHechosSegun(HechoFilterDTO filter, String modoDeNavegacion, Long coleccionId) {
        if (coleccionId == null) {
            throw new IllegalArgumentException("El ID de la colección no puede ser nulo");
        }
        coleccionRepository.findById(coleccionId)
                .orElseThrow(() -> new IllegalArgumentException("La colección " + coleccionId + " no existe"));

        if (filter == null) {
            filter = new HechoFilterDTO();
        }
        filter.validateAndParse();

        String categoriaNombre = null;
        if (filter.getCategoria() != null && !filter.getCategoria().isBlank()) {
            categoriaNombre = filter.getCategoria().trim();
        }

        String textoLibre = null;
        if (filter.getTextoLibre() != null && !filter.getTextoLibre().isBlank()) {
            textoLibre = filter.getTextoLibre().trim();
        }

        Boolean curado = null;
        if (modoDeNavegacion != null && !modoDeNavegacion.isBlank()) {
            EnumModoNavegacion modoEnum;
            try {
                modoEnum = EnumModoNavegacion.valueOf(modoDeNavegacion.toUpperCase());
            } catch (IllegalArgumentException ex) {
                throw new IllegalArgumentException("Modo Navegacion incorrecto");
            }
            curado = (modoEnum == EnumModoNavegacion.CURADO);
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
                curado,
                textoLibre
        );
    }
}
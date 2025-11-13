package ar.utn.ba.ddsi.apipublica.services;

import ar.utn.ba.ddsi.apipublica.models.dtos.HechoFilterDTO;
import ar.utn.ba.ddsi.apipublica.models.entities.*;
import ar.utn.ba.ddsi.apipublica.models.entities.FiltradorDeHechos;
import ar.utn.ba.ddsi.apipublica.models.repository.ColeccionRepository;
import ar.utn.ba.ddsi.apipublica.models.repository.HechoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
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

    // Nuevo: buscar hechos de una colección delegando al repository con parámetros parsed del DTO
    public List<Hecho> buscarHechosSegun(HechoFilterDTO filter, String modoDeNavegacion, Long coleccionId) {
        if (filter == null) filter = new HechoFilterDTO();
        //ESta bien que siempre espere un coleccionId??
        if(coleccionId==null && !(coleccionRepository.findById(coleccionId).isPresent())){
            throw new IllegalArgumentException("El ID de la colección no puede ser nulo");
        }

        // Normalizar categoria
        String categoriaNombre = null;
        if (filter.getCategoria() != null && !filter.getCategoria().isBlank()) {
            categoriaNombre = filter.getCategoria(); //Debería ser categori.trim???
        }

        LocalDate repDesde = null, repHasta = null, acaDesde = null, acaHasta = null;
        try {
            if (filter.getFechaReporteDesde() != null && !filter.getFechaReporteDesde().isBlank()) repDesde = LocalDate.parse(filter.getFechaReporteDesde());
            if (filter.getFechaReporteHasta() != null && !filter.getFechaReporteHasta().isBlank()) repHasta = LocalDate.parse(filter.getFechaReporteHasta());
            if (filter.getFechaAcontecimientoDesde() != null && !filter.getFechaAcontecimientoDesde().isBlank()) acaDesde = LocalDate.parse(filter.getFechaAcontecimientoDesde());
            if (filter.getFechaAcontecimientoHasta() != null && !filter.getFechaAcontecimientoHasta().isBlank()) acaHasta = LocalDate.parse(filter.getFechaAcontecimientoHasta());
        } catch (DateTimeParseException dte) {
            throw new IllegalArgumentException("Formato de fecha inválido. Use yyyy-MM-dd");
        }

        Float lat = null, lon = null;
        try {
            if (filter.getUbicacionLatitud() != null && !filter.getUbicacionLatitud().isBlank()) lat = Float.parseFloat(filter.getUbicacionLatitud());
            if (filter.getUbicacionLongitud() != null && !filter.getUbicacionLongitud().isBlank()) lon = Float.parseFloat(filter.getUbicacionLongitud());
        } catch (NumberFormatException nfe) {
            throw new IllegalArgumentException("Latitud o longitud invalida");
        }

        if(!(modoDeNavegacion == "CURADO" || modoDeNavegacion=="NOCURADO")) {
            throw new IllegalArgumentException("Modo Navegacion incorrecto");
        }
        Boolean curado = Boolean.FALSE; // null = no filtrar por consensuado
        if (modoDeNavegacion != null && modoDeNavegacion.equalsIgnoreCase("CURADO")) {
            curado = Boolean.TRUE;
        }

        // Delegar al repository que arma la consulta en la DB
        return coleccionRepository.buscarEnColeccionSegun(coleccionId, categoriaNombre, repDesde, repHasta, acaDesde, acaHasta, lat, lon, curado);
    }
}
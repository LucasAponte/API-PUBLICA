package ar.utn.ba.ddsi.apipublica.services;

import ar.utn.ba.ddsi.apipublica.models.dtos.HechoCreateDTO;
import ar.utn.ba.ddsi.apipublica.models.dtos.HechoFilterDTO;
import ar.utn.ba.ddsi.apipublica.models.entities.Hecho;
import ar.utn.ba.ddsi.apipublica.models.repository.HechoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

@Service
public class HechoService implements IHechoService {

    private final HechoRepository hechoRepository;

    public HechoService(HechoRepository hechoRepository) {
        this.hechoRepository = hechoRepository;
    }

    @Override
    public Hecho crearHecho(HechoCreateDTO dto){
        // Lógica de conversión y creación
        Hecho hecho = new Hecho();
        hecho.setTitulo(dto.getTitulo());
        hecho.setDescripcion(dto.getDescripcion());

        // Conversión segura de fecha
        if (dto.getFecha() != null && !dto.getFecha().isBlank()) {
            try {
                hecho.setFecha(LocalDate.parse(dto.getFecha()));
            } catch (DateTimeParseException e) {
                // excepción personalizada si la fecha es inválida  VER ESTO DEL FORMATO
                throw new IllegalArgumentException("Formato de fecha inválido: " + dto.getFecha());
            }
        }

        // Guardar en la base de datos
        return hechoRepository.save(hecho);
    }

    @Override
    public List<Hecho> buscarConFiltro(HechoFilterDTO filter) {
        if (filter == null) {
            return hechoRepository.findAll();
        }

        String categoriaNombre = null;
        if(filter.getCategoria() != null && !filter.getCategoria().isBlank()) {
            categoriaNombre = filter.getCategoria();
        }

        LocalDate repDesde = null, repHasta = null, acaDesde = null, acaHasta = null;
        try {
            if (filter.getFechaReporteDesde() != null && !filter.getFechaReporteDesde().isBlank()) repDesde = LocalDate.parse(filter.getFechaReporteDesde());
            if (filter.getFechaReporteHasta() != null && !filter.getFechaReporteHasta().isBlank()) repHasta = LocalDate.parse(filter.getFechaReporteHasta());
            if (filter.getFechaAcontecimientoDesde() != null && !filter.getFechaAcontecimientoDesde().isBlank()) acaDesde = LocalDate.parse(filter.getFechaAcontecimientoDesde());
            if (filter.getFechaAcontecimientoHasta() != null && !filter.getFechaAcontecimientoHasta().isBlank()) acaHasta = LocalDate.parse(filter.getFechaAcontecimientoHasta());
        } catch (DateTimeParseException dte) {
            //VER Formato de fecha inválido
            throw new IllegalArgumentException("Formato de fecha inválido. Use yyyy-MM-dd");
        }

        Float lat = null, lon = null;
        try {
            if (filter.getUbicacionLatitud() != null && !filter.getUbicacionLatitud().isBlank()) lat = Float.parseFloat(filter.getUbicacionLatitud());
            if (filter.getUbicacionLongitud() != null && !filter.getUbicacionLongitud().isBlank()) lon = Float.parseFloat(filter.getUbicacionLongitud());
        } catch (NumberFormatException nfe) {
            throw new IllegalArgumentException("Latitud o longitud invalida");
        }
        //VER le mando solo la categoria nombre no el id
        return hechoRepository.buscarHechosSegun(categoriaNombre, repDesde, repHasta, acaDesde, acaHasta, lat, lon);
    }
}

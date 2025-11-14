package ar.utn.ba.ddsi.apipublica.services;

import ar.utn.ba.ddsi.apipublica.models.dtos.HechoCreateDTO;
import ar.utn.ba.ddsi.apipublica.models.dtos.HechoFilterDTO;
import ar.utn.ba.ddsi.apipublica.models.entities.*;
import ar.utn.ba.ddsi.apipublica.models.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class HechoService implements IHechoService {

    private final HechoRepository hechoRepository;
    private final CategoriaRepository categoriaRepository;
    private final FuenteRepository fuenteRepository;
    private final UbicacionRepository ubicacionRepository;
    private final AdjuntoRepository adjuntoRepository;

    public HechoService(HechoRepository hechoRepository,
                        CategoriaRepository categoriaRepository,
                        FuenteRepository fuenteRepository,
                        UbicacionRepository ubicacionRepository,
                        AdjuntoRepository adjuntoRepository) {
        this.hechoRepository = hechoRepository;
        this.categoriaRepository = categoriaRepository;
        this.fuenteRepository = fuenteRepository;
        this.ubicacionRepository = ubicacionRepository;
        this.adjuntoRepository = adjuntoRepository;
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

        // Categoria: buscar por nombre, si no existe -> error
        if (dto.getCategoria() != null && !dto.getCategoria().isBlank()) {
            Optional<Categoria> catOpt = categoriaRepository.findByNombreIgnoreCase(dto.getCategoria());
            if (catOpt.isEmpty()) {
                throw new IllegalArgumentException("Categoria no existe: " + dto.getCategoria());
            }
            hecho.setCategoria(catOpt.get());
        }
        // Fuente: viene id numerico, intento buscar-> error
        if (dto.getFuente() != null && !dto.getFuente().isBlank()) {
            boolean fuenteSet = false;
            try {
                long idFuente = Long.parseLong(dto.getFuente());
                Optional<Fuente> fOpt = fuenteRepository.findById(idFuente);
                if (fOpt.isPresent()) {
                    hecho.setFuente(fOpt.get());
                    fuenteSet = true;
                }
            } catch (NumberFormatException nfe) {
                // No es un id; intento buscar por nombre  //ES una buena opcion hablar conlos chicos
                Optional<Fuente> fOpt2 = fuenteRepository.findByNombreIgnoreCase(dto.getFuente());
                if (fOpt2.isPresent()) {
                    hecho.setFuente(fOpt2.get());
                    fuenteSet = true;
                }
            }
            if (!fuenteSet) {
                throw new IllegalArgumentException("Fuente no existe: " + dto.getFuente());
            }
        }
        if (dto.getUbicacionLat() != null && !dto.getUbicacionLat().isBlank()
                && dto.getUbicacionLon() != null && !dto.getUbicacionLon().isBlank()) {
            try {
                float lat = Float.parseFloat(dto.getUbicacionLat());
                float lon = Float.parseFloat(dto.getUbicacionLon());
                // Crear y guardar provincia con un genrador
                Ubicacion u = new Ubicacion(lat, lon,new Provincia("as","eeee"));
                hecho.setUbicacion(ubicacionRepository.save(u));
            } catch (NumberFormatException nfe) {
                throw new IllegalArgumentException("Latitud o longitud inválida");
            }
        }

        // Tipo de hecho: intentar parsear el enum
        if (dto.getTipoHecho() != null && !dto.getTipoHecho().isBlank()) {
            try {
                EnumTipoHecho tipo = EnumTipoHecho.valueOf(dto.getTipoHecho());
                hecho.setTipoHecho(tipo);
            } catch (IllegalArgumentException iae) {
                throw new IllegalArgumentException("Tipo de hecho inválido: " + dto.getTipoHecho());
            }
        }
        //PROBAR ESTO
        List<Adjunto> listaAdjuntos = new ArrayList<>();
        if (dto.getAdjuntos() != null) {
            for (String a : dto.getAdjuntos()) {
                if (a == null || a.isBlank()) continue;
                Adjunto adj = new Adjunto();
                adj.setTipo(null);
                adj.setUrl(a);
                listaAdjuntos.add(adjuntoRepository.save(adj));
            }
        }
        hecho.setAdjuntos(listaAdjuntos);

        // Fecha de carga
        hecho.setFechaDeCarga(LocalDate.now());

        // Guardar en la base de datos
        return hechoRepository.save(hecho);
    }

    @Override
    public List<Hecho> buscarConFiltro(HechoFilterDTO filter) {
        if (filter == null) {
            return hechoRepository.findAll();
        }

        // Validar y parsear usando el DTO
        filter.validateAndParse();

        String categoriaNombre = null;
        if(filter.getCategoria() != null && !filter.getCategoria().isBlank()) {
            categoriaNombre = filter.getCategoria().trim();
        }

        String textoLibre = null;
        if (filter.getTextoLibre() != null && !filter.getTextoLibre().isBlank()) {
            textoLibre = filter.getTextoLibre().trim();
        }

        // Usar campos parseados
        List<Hecho> resultados = hechoRepository.buscarHechosSegun(
                categoriaNombre,
                filter.getFechaReporteDesdeParsed(),
                filter.getFechaReporteHastaParsed(),
                filter.getFechaAcontecimientoDesdeParsed(),
                filter.getFechaAcontecimientoHastaParsed(),
                filter.getUbicacionLatitudParsed(),
                filter.getUbicacionLongitudParsed(),
                textoLibre
        );
        return resultados;
    }
}

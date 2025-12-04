package ar.utn.ba.ddsi.apipublica.services;

import ar.utn.ba.ddsi.apipublica.models.dtos.SolicitudCreateDTO;
import ar.utn.ba.ddsi.apipublica.models.dtos.SolicitudOutputDTO;
import ar.utn.ba.ddsi.apipublica.models.entities.Hecho;
import ar.utn.ba.ddsi.apipublica.models.entities.SolicitudEliminacion;
import ar.utn.ba.ddsi.apipublica.models.repository.ContribuyenteRepository;
import ar.utn.ba.ddsi.apipublica.models.repository.HechoRepository;
import ar.utn.ba.ddsi.apipublica.models.repository.SolicitudRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SolicitudService implements ISolicitudService{

    private final SolicitudRepository solicitudRepository;
    private final HechoRepository hechoRepository;

    public SolicitudService(SolicitudRepository solicitudRepository, HechoRepository hechoRepository) {
        this.solicitudRepository = solicitudRepository;
        this.hechoRepository = hechoRepository;
    }

    @Override
    public SolicitudEliminacion crearSolicitud(SolicitudCreateDTO dto) throws IllegalArgumentException{
        if (dto == null) throw new IllegalArgumentException("Datos de la solicitud vacios");
        if (dto.getMotivo() == null || dto.getMotivo().trim().isEmpty()) throw new IllegalArgumentException("Motivo requerido");

        Hecho hecho = hechoRepository.findById(dto.getIdHecho()).orElse(null);
        if (hecho == null) throw new IllegalArgumentException("Hecho no encontrado");
        //DEbería chequear que existe el id? Por ahora pensamos que si manda solicitud es porque etsá bien.

        //Contribuyente solicitante = contribuyenteRepository.findById(dto.getIdContribuyente()).orElse(null);
        //if (solicitante == null) throw new IllegalArgumentException("Contribuyente no encontrado");

        SolicitudEliminacion solicitud = new SolicitudEliminacion(dto.getIdContribuyente(), hecho, LocalDate.now(), dto.getMotivo());
        return solicitudRepository.save(solicitud);
    }

    @Override
    public List<SolicitudOutputDTO> listarSolicitudes() {
        List<SolicitudEliminacion> solicitudes = solicitudRepository.findAll();
        List<SolicitudOutputDTO> solicitudesDTO = new ArrayList<>();
        solicitudes.forEach(solicitud -> {
            solicitudesDTO.add(new SolicitudOutputDTO(solicitud));
        });
        return solicitudesDTO;
    }

    @Override
    public SolicitudOutputDTO obtenerSolicitudPorId(Long id) {
        SolicitudEliminacion solicitud = this.solicitudRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));
        return new SolicitudOutputDTO(solicitud);

    }
}

package ar.utn.ba.ddsi.apipublica.services;

import ar.utn.ba.ddsi.apipublica.models.dtos.SolicitudCreateDTO;
import ar.utn.ba.ddsi.apipublica.models.entities.Contribuyente;
import ar.utn.ba.ddsi.apipublica.models.entities.Hecho;
import ar.utn.ba.ddsi.apipublica.models.entities.SolicitudEliminacion;
import ar.utn.ba.ddsi.apipublica.models.repository.ContribuyenteRepository;
import ar.utn.ba.ddsi.apipublica.models.repository.HechoRepository;
import ar.utn.ba.ddsi.apipublica.models.repository.SolicitudRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class SolicitudService implements ISolicitudService{

    private final SolicitudRepository solicitudRepository;
    private final HechoRepository hechoRepository;
    private final ContribuyenteRepository contribuyenteRepository;

    public SolicitudService(SolicitudRepository solicitudRepository, HechoRepository hechoRepository, ContribuyenteRepository contribuyenteRepository) {
        this.solicitudRepository = solicitudRepository;
        this.hechoRepository = hechoRepository;
        this.contribuyenteRepository = contribuyenteRepository;
    }

    @Override
    public SolicitudEliminacion crearSolicitud(SolicitudCreateDTO dto) throws IllegalArgumentException{
        if (dto == null) throw new IllegalArgumentException("Datos de la solicitud vacios");
        if (dto.getMotivo() == null || dto.getMotivo().trim().isEmpty()) throw new IllegalArgumentException("Motivo requerido");

        Hecho hecho = hechoRepository.findById(dto.getIdHecho()).orElse(null);
        if (hecho == null) throw new IllegalArgumentException("Hecho no encontrado");

        Contribuyente solicitante = contribuyenteRepository.findById(dto.getIdContribuyente()).orElse(null);
        if (solicitante == null) throw new IllegalArgumentException("Contribuyente no encontrado");

        SolicitudEliminacion solicitud = new SolicitudEliminacion(solicitante, hecho, LocalDate.now(), dto.getMotivo());
        return solicitudRepository.save(solicitud);
    }

    @Override
    public List<SolicitudEliminacion> listarSolicitudes() {
        return solicitudRepository.findAll();
    }
}

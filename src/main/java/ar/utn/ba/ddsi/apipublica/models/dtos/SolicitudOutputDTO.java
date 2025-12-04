package ar.utn.ba.ddsi.apipublica.models.dtos;

import ar.utn.ba.ddsi.apipublica.models.entities.SolicitudEliminacion;
import lombok.Data;

import java.time.LocalDate;

@Data
public class SolicitudOutputDTO {
    private Long id_solicitud;
    private HechoOutputDTO hecho;
    private String motivo;
    private String estadoSolicitud;
    private Long id_contribuyente;
    private LocalDate fechaSolicitud;
    private boolean spam;

    public SolicitudOutputDTO(Long id_solicitud, HechoOutputDTO hecho, String estadoSolicitud, String motivo, Long id_contribuyente, boolean spam) {
        this.id_solicitud = id_solicitud;
        this.hecho = hecho;
        this.estadoSolicitud = estadoSolicitud;
        this.motivo = motivo;
        this.id_contribuyente = id_contribuyente;
        this.spam = spam;
    }
    // Constructor que recibe SolicitudEliminacion
    public SolicitudOutputDTO(SolicitudEliminacion solicitud) {
        this.id_solicitud = solicitud.getId_solicitud();
        // Asumiendo que HechoOutputDTO tiene un constructor que acepta un objeto Hecho
        this.hecho = new HechoOutputDTO(solicitud.getHecho());
        this.motivo = solicitud.getMotivo();
        // Convierte el EnumEstadoSol a su representación en String
        this.estadoSolicitud = solicitud.getEstado().name();
        // Asumiendo que 'solicitante' de SolicitudEliminacion no es nulo y tiene un método getId()
        this.id_contribuyente = solicitud.getId_solicitante() != null ? solicitud.getId_solicitante() : null;
        this.spam = solicitud.isSpam();
        this.fechaSolicitud = solicitud.getFecha();
    }
}

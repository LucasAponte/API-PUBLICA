package ar.utn.ba.ddsi.apipublica.models.entities;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "solicitud_eliminacion")
public class SolicitudEliminacion {
    //Revisar Relaciones
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSolicitud;

    @ManyToOne(optional = false)
    @JoinColumn(name = "contribuyente_id")
    private Contribuyente solicitante;

    @ManyToOne(optional = false)
    @JoinColumn(name = "hecho_id")
    private Hecho hecho;

    private LocalDate fecha;
    private String motivo;
    private EnumEstadoSol estado;

    public SolicitudEliminacion(){}

    public SolicitudEliminacion(Contribuyente solicitante, Hecho hecho, LocalDate fecha, String motivo) {
        this.solicitante = solicitante;
        this.hecho = hecho;
        this.fecha = fecha;
        this.motivo = motivo;
        this.estado = EnumEstadoSol.PENDIENTE;
    }

    public Long getId() { return this.idSolicitud; }
    public void setId(Long id) { this.idSolicitud = id; }

    public Contribuyente getContribuyente() {return this.solicitante;}
    public void setContribuyente(Contribuyente solicitante) { this.solicitante = solicitante;}

    public Hecho getHecho(){
        return this.hecho;
    }
    public void setHecho(Hecho hecho){this.hecho = hecho;}

    public LocalDate getFecha(){return this.fecha;}
    public void setFecha(LocalDate fecha){this.fecha = fecha;}

    public String getMotivo(){return this.motivo;}
    public void setMotivo(String motivo){this.motivo = motivo;}


    public EnumEstadoSol getEstado(){return estado;}
    public void setEstado(EnumEstadoSol nuevoEstado) {this.estado = nuevoEstado;}
}

/**
 * nota: al crearse una solicitud de eliminacion sobre un hecho, tendra x default el estado pendiente
 * hasta q se acepte o rechace (setEstado(...))
 */

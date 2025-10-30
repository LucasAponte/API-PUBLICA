package ar.utn.ba.ddsi.apipublica.models.dtos;

public class SolicitudCreateDTO {
    private Long idHecho;
    private Long idContribuyente;
    private String motivo;

    public Long getIdHecho() { return idHecho; }
    public void setIdHecho(Long idHecho) { this.idHecho = idHecho; }

    public Long getIdContribuyente() { return idContribuyente; }
    public void setIdContribuyente(Long idContribuyente) { this.idContribuyente = idContribuyente; }

    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }
}

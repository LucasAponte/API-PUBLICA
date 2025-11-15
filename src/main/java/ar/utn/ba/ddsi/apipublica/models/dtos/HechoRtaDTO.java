package ar.utn.ba.ddsi.apipublica.models.dtos;

import ar.utn.ba.ddsi.apipublica.models.entities.Adjunto;
import ar.utn.ba.ddsi.apipublica.models.entities.EnumTipoHecho;
import ar.utn.ba.ddsi.apipublica.models.entities.Hecho;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class HechoRtaDTO {
    private Long id;
    private String titulo;
    private String descripcion;
    private Long idCategoria;
    private LocalDate fecha;
    private LocalDate fechaDeCarga;
    private Long idFuente;
    private Long idUbicacion;
    private Long idEtiqueta;
    private EnumTipoHecho tipoHecho;
    private List<Long> idAdjuntos;

    public HechoRtaDTO() {
    }

    public HechoRtaDTO(Long id, String titulo, String descripcion, Long idCategoria, LocalDate fecha, LocalDate fechaDeCarga, Long idFuente, Long idUbicacion, Long idEtiqueta, EnumTipoHecho tipoHecho, List<Long> idAdjuntos) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.idCategoria = idCategoria;
        this.fecha = fecha;
        this.fechaDeCarga = fechaDeCarga;
        this.idFuente = idFuente;
        this.idUbicacion = idUbicacion;
        this.idEtiqueta = idEtiqueta;
        this.tipoHecho = tipoHecho;
        this.idAdjuntos = idAdjuntos;
    }
    public void HechoAHechoRtaDTO(Hecho hecho) {
        this.id = hecho.getId_hecho();
        this.titulo = hecho.getTitulo();
        this.descripcion = hecho.getDescripcion();
        this.idCategoria = hecho.getCategoria().getIdCategoria();
        this.fecha = hecho.getFecha();
        this.fechaDeCarga = hecho.getFechaDeCarga();
        this.idFuente = hecho.getFuente().getIdFuente();
        this.idUbicacion = hecho.getUbicacion().getIdUbicacion();
        this.idEtiqueta = hecho.getEtiqueta() != null ? hecho.getEtiqueta().getIdEtiqueta(): null;
        this.tipoHecho = hecho.getTipoHecho();
        this.idAdjuntos = hecho.getAdjuntos().stream().map(Adjunto::getId_Adjunto).collect(Collectors.toList());
    }
}


package ar.utn.ba.ddsi.apipublica.models.dtos;

import ar.utn.ba.ddsi.apipublica.models.entities.Adjunto;
import ar.utn.ba.ddsi.apipublica.models.entities.EnumTipoHecho;
import ar.utn.ba.ddsi.apipublica.models.entities.Hecho;
import ar.utn.ba.ddsi.apipublica.models.entities.Fuente;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class HechoOutputDTO {
    private Long id_hecho;
    private String titulo;
    private String descripcion;
    private String categoria;
    private String fecha;
    private String fechaDeCarga;
    private UbicacionOutputDTO ubicacion;
    private String etiqueta;
    private String tipoHecho;
    private String fuente; // nueva propiedad: fuente del hecho
    private List<AdjuntoDTO> adjuntos = new java.util.ArrayList<>();
    public HechoOutputDTO() {
    }

    public HechoOutputDTO(Hecho hecho) {
        this.id_hecho= hecho.getId_hecho();
        this.titulo = hecho.getTitulo();
        this.descripcion = hecho.getDescripcion();
        this.categoria = hecho.getCategoria().getNombre();
        this.fecha = hecho.getFecha().toString();
        this.fechaDeCarga = hecho.getFechaDeCarga().toString();
        this.etiqueta = hecho.getEtiqueta() != null ? hecho.getEtiqueta().getNombre(): null;
        this.ubicacion = new UbicacionOutputDTO(hecho.getUbicacion());
        this.fuente = hecho.getFuente().getNombre();
        if(hecho.getTipoHecho()!=null) {
            System.out.println("Tipo de hecho: " + hecho.getTipoHecho().name());
            this.tipoHecho = hecho.getTipoHecho().name();
        }else this.tipoHecho = EnumTipoHecho.TEXTO.name();
        if(hecho.getAdjuntos().size()>=1)hecho.getAdjuntos().forEach(adjunto -> this.adjuntos.add(new AdjuntoDTO(adjunto)));
    }
    @Override
    public String toString() {
        return "HechoOutputDTO{" +
                "titulo='" + titulo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", categoria='" + categoria + '\'' +
                ", fecha=" + fecha +
                ", fechaDeCarga=" + fechaDeCarga +
                ", etiqueta='" + etiqueta + '\'' +
                ", tipoHecho='" + tipoHecho + '\'' +
                ", fuente='" + (fuente!=null ? fuente : null) + '\'' +
                ", adjuntos=" + adjuntos +
                '}';
    }
}

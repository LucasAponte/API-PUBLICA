package ar.utn.ba.ddsi.apipublica.models.dtos;

import ar.utn.ba.ddsi.apipublica.models.entities.Coleccion;
import ar.utn.ba.ddsi.apipublica.models.entities.Fuente;
import ar.utn.ba.ddsi.apipublica.models.entities.Hecho;
import ar.utn.ba.ddsi.apipublica.models.entities.HechoXColeccion;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class ColeccionOutputDTO {

    private Long id;
    private String titulo;
    private String descripcion;
    private List<String> fuentes = new ArrayList<>(); // nombres de las fuentes
    private String tipoDeAlgoritmo;
    private Integer cantidadHechos = 0;
    private List<HechoOutputDTO> hechos = new ArrayList<>();
    private List<HechoOutputDTO> hechosConsensuados = new ArrayList<>();

    /**
     * Constructor que inicializa el DTO a partir de una entidad Coleccion.
     * Hace mapeos defensivos para evitar NullPointerException si faltan subobjetos.
     */
    public ColeccionOutputDTO(Coleccion coleccion) {
        if (coleccion == null) return;

        this.id = coleccion.getId_coleccion();
        this.titulo = coleccion.getTitulo();
        this.descripcion = coleccion.getDescripcion();
        this.tipoDeAlgoritmo = coleccion.getTipoDeAlgoritmo() != null ? coleccion.getTipoDeAlgoritmo().name() : null;

        // Fuentes: preferimos usar el listado de fuentes explícito si existe, si no intentamos obtenerlo desde los hechos
        List<Fuente> fuentesEntidades = coleccion.getFuentes();
        if (fuentesEntidades == null || fuentesEntidades.isEmpty()) {
            try {
                fuentesEntidades = coleccion.obtenerFuentes();
            } catch (Exception ignored) {
                fuentesEntidades = new ArrayList<>();
            }
        }
        if (fuentesEntidades != null) {
            this.fuentes = fuentesEntidades.stream()
                    .filter(Objects::nonNull)
                    .map(f -> f.getNombre())
                    .collect(Collectors.toList());
        }

        // Hechos: mapeo seguro desde HechoXColeccion a HechoOutputDTO
        if (coleccion.getHechos() != null) {
            for (HechoXColeccion hxc : coleccion.getHechos()) {
                if (hxc == null) continue;
                Hecho hecho = hxc.getHecho();
                if (hecho == null) continue;

                // intentamos construir con el constructor existente de HechoOutputDTO
                try {
                    HechoOutputDTO hechoDto = new HechoOutputDTO(hecho);
                    this.hechos.add(hechoDto);
                    if (Boolean.TRUE.equals(hxc.getConsensuado())) {
                        this.hechosConsensuados.add(hechoDto);
                    }
                } catch (Exception ex) {
                    // Si la construcción falla (p. ej. por algún campo null en Hecho), hacemos un mapeo mínimo seguro
                    HechoOutputDTO safe = new HechoOutputDTO();
                    safe.setTitulo(hecho.getTitulo());
                    safe.setDescripcion(hecho.getDescripcion());
                    safe.setFuente(hecho.getFuente() != null ? hecho.getFuente().getNombre() : null);
                    safe.setCategoria(hecho.getCategoria() != null ? hecho.getCategoria().getNombre() : null);
                    safe.setFecha(hecho.getFecha() != null ? hecho.getFecha().toString() : null);
                    safe.setFechaDeCarga(hecho.getFechaDeCarga() != null ? hecho.getFechaDeCarga().toString() : null);
                    if (hecho.getUbicacion() != null) {
                        safe.setUbicacionLat(String.valueOf(hecho.getUbicacion().getLatitud()));
                        safe.setUbicacionLon(String.valueOf(hecho.getUbicacion().getLongitud()));
                    }
                    safe.setEtiqueta(hecho.getEtiqueta() != null ? hecho.getEtiqueta().getNombre() : null);
                    safe.setTipoHecho(hecho.getTipoHecho() != null ? hecho.getTipoHecho().name() : null);
                    // adjuntos se omiten en este fallback por simplicidad
                    this.hechos.add(safe);
                    if (Boolean.TRUE.equals(hxc.getConsensuado())) {
                        this.hechosConsensuados.add(safe);
                    }
                }
            }
        }

        this.cantidadHechos = this.hechos.size();
    }

    @Override
    public String toString() {
        return "ColeccionOutputDTO{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", fuentes=" + fuentes +
                ", tipoDeAlgoritmo='" + tipoDeAlgoritmo + '\'' +
                ", cantidadHechos=" + cantidadHechos +
                ", hechos=" + hechos +
                ", hechosConsensuados=" + hechosConsensuados +
                '}';
    }
}

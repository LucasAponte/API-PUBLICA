package ar.utn.ba.ddsi.apipublica.models.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "fuente")
public class Fuente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFuente;

    private String nombre;
    private String url;
    private EnumTipoFuente tipoFuente;

    public Fuente() {}

    public Fuente(Long id,String nombre, String url, EnumTipoFuente tipoFuente) {
        this.idFuente = id;
        this.nombre = nombre;
        this.url = url;
        this.tipoFuente = tipoFuente;}

    public Long getId() {
        return this.idFuente;
    }
    public void setId(Long id) { this.idFuente = id; }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public EnumTipoFuente getTipoFuente() {
        return tipoFuente;
    }
    public void setTipoFuente(EnumTipoFuente tipoFuente) {
        this.tipoFuente = tipoFuente;
    }
}

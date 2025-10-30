package ar.utn.ba.ddsi.apipublica.models.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "coleccion")
public class Coleccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long handle;

    private String titulo;
    private String descripcion;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "coleccion_id")
    private List<Fuente> fuentes = new ArrayList<>();

    @Transient
    private List<InterfaceCondicion> criterioDePertenencia = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "coleccion_id")
    private List<Hecho> hechos = new ArrayList<>();

    @Transient
    private AlgoritmoDeConsenso algoritmoDeConsenso;

    public Coleccion() {
        // constructor por defecto requerido por JPA
    }

    public Coleccion(String titulo, String descripcion, List<Fuente> fuentes) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        if (fuentes != null) this.fuentes = fuentes;
    }

    public Coleccion(String titulo, String descripcion, List<Fuente> fuentes, List<InterfaceCondicion> criterio) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        if (fuentes != null) this.fuentes = fuentes;
        if (criterio != null) this.criterioDePertenencia = criterio;
    }

    public void cambiarCriterioDePertenencia(List<InterfaceCondicion> criterio) {
        criterioDePertenencia = criterio;
    }

    public List<Hecho> getHechos() {
        return this.hechos;
    }

    public List<Hecho> obtenerHechosConsensuados(){
        if (this.algoritmoDeConsenso == null) return new ArrayList<>(this.hechos);
        return this.algoritmoDeConsenso.aplicar(this.hechos, this.fuentes);
    }

    public List<Fuente> obtenerFuentes() {
        return this.fuentes;
    }

    public String getTitulo() { return this.titulo; }

    public Long getHandle() { return handle; }
    public void setHandle(Long handle) { this.handle = handle; }

    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

}


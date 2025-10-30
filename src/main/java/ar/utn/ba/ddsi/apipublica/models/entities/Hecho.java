package ar.utn.ba.ddsi.apipublica.models.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "hecho")
public class Hecho {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idHecho;

    private String titulo;
    private String descripcion;
    @OneToOne(cascade = CascadeType.ALL)
    private Categoria categoria;
    private LocalDate fecha;
    private LocalDate fechaDeCarga;
    @OneToOne(cascade = CascadeType.ALL)
    private Ubicacion lugarDeOcurrencia;
    @OneToOne(cascade = CascadeType.ALL)
    private Fuente fuente;
    @OneToOne(cascade = CascadeType.ALL)
    private Etiqueta etiqueta;
    private boolean visibilidad = true;

    public Hecho() {
        // constructor por defecto requerido por JPA
    }
    public Hecho(String titulo, String descripcion, Categoria categoria,Ubicacion lugarDeOcurrencia, LocalDate fecha) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.lugarDeOcurrencia = lugarDeOcurrencia;
        this.fecha = fecha;
    }

    public Long getId() { return this.idHecho; }

    public String getTitulo() {return titulo;}
    public void setTitulo(String titulo) {this.titulo= titulo;}
    public void cambiarDescripcion(String descripcion) {this.descripcion = descripcion;}
    public Categoria getCategoria() {return categoria;}
    public void cambiarCategoria(String categoria) {this.categoria.setNombre(categoria);}
    public Etiqueta getEtiqueta() {return etiqueta;}
    public void cambiarEtiqueta(String etiqueta) {this.etiqueta.setNombre(etiqueta);}

    //Así me vendría la info, en String?
    public void cambiarUbicacion(String dato, String dato1) {
        float nuevaLatitud = Float.parseFloat(dato);
        float nuevaLongitud = Float.parseFloat(dato1);
        lugarDeOcurrencia.setUbicacion(nuevaLatitud,nuevaLongitud);
    }

    //string a fecha??
    public LocalDate getFecha() {return fecha;}

    public void setFecha(LocalDate fecha) {this.fecha = fecha;}

    public LocalDate getFechaDeCarga() {return fechaDeCarga;}
    public void setFechaDeCarga(LocalDate fechaDeCarga) {this.fechaDeCarga = fechaDeCarga;}

    public Boolean esVisible() {return visibilidad;}
    public void cambiarVisibilidad() {visibilidad = true;} //asi por el momento

    public Fuente getFuente() {return this.fuente;}

}

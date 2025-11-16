package ar.utn.ba.ddsi.apipublica.models.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Etiqueta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idEtiqueta;
    private String nombre;

    public Etiqueta() {
    }
    public Etiqueta(long idEtiqueta, String nombre) {}
    public Etiqueta(String etiqueta) {
        this.nombre = etiqueta;
    }

    public String getNombre() {return nombre;}
    public void setNombre(String nombre) {this.nombre = nombre;}
    public long getIdEtiqueta() {return idEtiqueta;}
}

package ar.utn.ba.ddsi.apipublica.models.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idCategoria;

    private String nombre;

    public  Categoria() {}

    public Categoria(String nombre) {
        this.nombre = nombre;
    }
    public String getNombre(){return this.nombre;}
    public void setNombre(String dato) {this.nombre = dato;}
    public  long getIdCategoria(){return this.idCategoria;}



}

package ar.utn.ba.ddsi.apipublica.models.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "contribuyente")
public class Contribuyente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idContribuyente;

    private String nombre;
    private String apellido;
    private int edad;
    private Boolean anonimo;

    public Contribuyente() {}

    public Contribuyente(Long id, String nombre, String apellido, int edad) {
        if (edad < 0) {
            throw new IllegalArgumentException("La edad no puede ser negativa.");
        }
        this.idContribuyente = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.anonimo = false;
    }

    public Contribuyente(String nombre, String apellido, int edad) {
        this(null, nombre, apellido, edad);
    }

    public Long getIdContribuyente() { return idContribuyente; }
    public void setIdContribuyente(Long idContribuyente) { this.idContribuyente = idContribuyente; }

    public String getNombre() {return nombre;}
    public void setNombre(String nombre) {this.nombre = nombre;}

    public String getApellido() {return apellido;}
    public void setApellido(String apellido) {this.apellido = apellido;}

    public int getEdad() {return edad;}
    public void setEdad(int edad) {this.edad = edad;}

    public Boolean getAnonimo() {return anonimo;}
    public void setAnonimo(Boolean anonimo) {this.anonimo = anonimo;}

}

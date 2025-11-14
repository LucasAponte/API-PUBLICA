package ar.utn.ba.ddsi.apipublica.models.entities;

import jakarta.persistence.*;

@Entity
public class Ubicacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idUbicacion;
    private float latitud;
    private float longitud;
    @ManyToOne
    private Provincia provincia;

    public Ubicacion(float latitud, float longitud,Provincia provincia) {
        this.latitud = latitud;
        this.longitud = longitud;
        this.provincia=provincia;
    }

    public void setUbicacion(float newLatitud, float newLongitud) {
        this.latitud = newLatitud;
        this.longitud = newLongitud;
    }

    public float getLatitud() {return this.latitud;}

    public float getLongitud() {
        return longitud;
    }
}

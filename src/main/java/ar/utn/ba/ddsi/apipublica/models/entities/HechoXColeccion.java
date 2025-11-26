package ar.utn.ba.ddsi.apipublica.models.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "hecho_x_coleccion")
public class HechoXColeccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_hecho_x_coleccion;

    @ManyToOne
    @JoinColumn(name = "id_hecho")
    private Hecho hecho;

    @ManyToOne
    @JoinColumn(name = "id_coleccion")
    private Coleccion coleccion;

    @Column(name = "consensuado", nullable = false)
    private Boolean consensuado;

    public HechoXColeccion(Hecho hecho, Coleccion coleccion, Boolean consensuado) {
        this.hecho = hecho;
        this.coleccion = coleccion;
        this.consensuado = consensuado;
    }
}


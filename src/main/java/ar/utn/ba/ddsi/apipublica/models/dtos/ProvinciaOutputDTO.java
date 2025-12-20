package ar.utn.ba.ddsi.apipublica.models.dtos;

import lombok.Data;

@Data
public class ProvinciaOutputDTO {
    private String nombre;
    private String pais;

    public ProvinciaOutputDTO(String nombre,String pais) {
        this.nombre = nombre;
        this.pais = pais;
    }
}

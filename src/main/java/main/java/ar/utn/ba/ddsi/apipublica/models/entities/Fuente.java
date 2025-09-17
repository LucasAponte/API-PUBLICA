package main.java.ar.utn.ba.ddsi.apipublica.models.entities;


public class Fuente {
    private Long id;
    private String url;
    private EnumTipoFuente tipoFuenteProxy;

    public Fuente(Long id, String url, EnumTipoFuente tipoFuenteProxy) {
        this.id = id;
        this.url = url;
        this.tipoFuenteProxy = tipoFuenteProxy;}
}

package main.java.ar.utn.ba.ddsi.apipublica.models.entities;

public class CondicionTitulo implements InterfaceCondicion {
    private String titulo;

    @Override
    public boolean cumpleCondicion(Hecho hecho) {
        return hecho.getTitulo().equals(this.titulo);
    }
}

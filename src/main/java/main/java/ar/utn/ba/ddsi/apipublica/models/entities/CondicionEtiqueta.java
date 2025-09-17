package main.java.ar.utn.ba.ddsi.apipublica.models.entities;

public class CondicionEtiqueta implements InterfaceCondicion {
    private Etiqueta etiqueta;

    @Override
    public boolean cumpleCondicion(Hecho hecho) {
        return hecho.getEtiqueta().getNombre().equals(this.etiqueta.getNombre());
    }
}

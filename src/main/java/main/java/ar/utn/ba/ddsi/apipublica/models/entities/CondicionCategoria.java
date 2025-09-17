package main.java.ar.utn.ba.ddsi.apipublica.models.entities;

public class CondicionCategoria implements InterfaceCondicion {
    private Categoria categoria;

    @Override
    public boolean cumpleCondicion(Hecho hecho) {
        return hecho.getCategoria().getNombre().equals(this.categoria.getNombre());
    }
}

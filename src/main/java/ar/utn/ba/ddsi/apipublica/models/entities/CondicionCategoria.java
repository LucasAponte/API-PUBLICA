package ar.utn.ba.ddsi.apipublica.models.entities;

public class CondicionCategoria implements InterfaceCondicion {
    private Categoria categoria;

    public CondicionCategoria() {}
    public CondicionCategoria(Categoria categoria) { this.categoria = categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }

    @Override
    public boolean cumpleCondicion(Hecho hecho) {
        return hecho.getCategoria() != null && hecho.getCategoria().getNombre().equals(this.categoria.getNombre());
    }
}

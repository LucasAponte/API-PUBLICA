package ar.utn.ba.ddsi.apipublica.models.entities;

public class CondicionTitulo implements InterfaceCondicion {
    private String titulo;

    public CondicionTitulo() {}
    public CondicionTitulo(String titulo) { this.titulo = titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    @Override
    public boolean cumpleCondicion(Hecho hecho) {
        return hecho.getTitulo() != null && hecho.getTitulo().equals(this.titulo);
    }
}

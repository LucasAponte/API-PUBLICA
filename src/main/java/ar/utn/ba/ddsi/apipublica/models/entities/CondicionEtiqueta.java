package ar.utn.ba.ddsi.apipublica.models.entities;

public class CondicionEtiqueta implements InterfaceCondicion {
    private Etiqueta etiqueta;

    public CondicionEtiqueta() {}
    public CondicionEtiqueta(Etiqueta etiqueta) { this.etiqueta = etiqueta; }
    public void setEtiqueta(Etiqueta etiqueta) { this.etiqueta = etiqueta; }

    @Override
    public boolean cumpleCondicion(Hecho hecho) {
        return hecho.getEtiqueta() != null && hecho.getEtiqueta().getNombre().equals(this.etiqueta.getNombre());
    }
}

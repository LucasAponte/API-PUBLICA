package ar.utn.ba.ddsi.apipublica.models.entities;

public class CondicionOrigen implements InterfaceCondicion {
    private Fuente origen;

    public CondicionOrigen() {}
    public CondicionOrigen(Fuente origen) { this.origen = origen; }
    public void setOrigen(Fuente origen) { this.origen = origen; }

    @Override
    public boolean cumpleCondicion(Hecho hecho) {
        return hecho.getFuente() != null && hecho.getFuente().equals(this.origen);
    }
}

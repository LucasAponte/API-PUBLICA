package ar.utn.ba.ddsi.apipublica.models.entities;

import java.time.LocalDate;
//Consultar Revisar todo
public class CondicionFechaRango implements InterfaceCondicion {
    private LocalDate desde;
    private LocalDate hasta;

    public CondicionFechaRango() {}
    public CondicionFechaRango(LocalDate desde, LocalDate hasta) {
        this.desde = desde;
        this.hasta = hasta;
    }

    public void setDesde(LocalDate desde) { this.desde = desde; }
    public void setHasta(LocalDate hasta) { this.hasta = hasta; }

    @Override
    public boolean cumpleCondicion(Hecho hecho) {
        if (hecho == null || hecho.getFecha() == null) return false;
        LocalDate f = hecho.getFecha();
        if (desde != null && f.isBefore(desde)) return false;
        if (hasta != null && f.isAfter(hasta)) return false;
        return true;
    }
}


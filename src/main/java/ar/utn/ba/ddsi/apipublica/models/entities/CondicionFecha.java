package ar.utn.ba.ddsi.apipublica.models.entities;

import java.time.LocalDate;

/* podria variar si quiere: lo que paso antes de la fecha seleccionada, despues o el mismo dia */
public class CondicionFecha implements InterfaceCondicion {
    private LocalDate fecha;

    public CondicionFecha() {}
    public CondicionFecha(LocalDate fecha) { this.fecha = fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    //Por ahora 12/06 Pensado para que sea el mismo dia

    @Override
    public boolean cumpleCondicion(Hecho hecho) {
        return this.fecha != null && this.fecha.equals(hecho.getFecha());
    }
}

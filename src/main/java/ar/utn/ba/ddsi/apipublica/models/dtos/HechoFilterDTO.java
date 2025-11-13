package ar.utn.ba.ddsi.apipublica.models.dtos;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class HechoFilterDTO {
    private String categoria;
    private String fechaReporteDesde;
    private String fechaReporteHasta;
    private String fechaAcontecimientoDesde;
    private String fechaAcontecimientoHasta;
    private String ubicacionLatitud;
    private String ubicacionLongitud;

    // Campos parseados (resultado de la validación)
    private LocalDate fechaReporteDesdeParsed;
    private LocalDate fechaReporteHastaParsed;
    private LocalDate fechaAcontecimientoDesdeParsed;
    private LocalDate fechaAcontecimientoHastaParsed;
    private Float ubicacionLatitudParsed;
    private Float ubicacionLongitudParsed;

    // Constructor sin-argumentos necesario para usos donde no se pasan filtros
    public HechoFilterDTO() {}

    public HechoFilterDTO(
            String categoria,
            String fechaReporteDesde,
            String fechaReporteHasta,
            String fechaAcontecimientoDesde,
            String fechaAcontecimientoHasta,
            String ubicacionLatitud,
            String ubicacionLongitud
    ) {
        this.categoria = categoria;
        this.fechaReporteDesde = fechaReporteDesde;
        this.fechaReporteHasta = fechaReporteHasta;
        this.fechaAcontecimientoDesde = fechaAcontecimientoDesde;
        this.fechaAcontecimientoHasta = fechaAcontecimientoHasta;
        this.ubicacionLatitud = ubicacionLatitud;
        this.ubicacionLongitud = ubicacionLongitud;
    }
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getFechaReporteDesde() { return fechaReporteDesde; }
    public void setFechaReporteDesde(String fechaReporteDesde) { this.fechaReporteDesde = fechaReporteDesde; }

    public String getFechaReporteHasta() { return fechaReporteHasta; }
    public void setFechaReporteHasta(String fechaReporteHasta) { this.fechaReporteHasta = fechaReporteHasta; }

    public String getFechaAcontecimientoDesde() { return fechaAcontecimientoDesde; }
    public void setFechaAcontecimientoDesde(String fechaAcontecimientoDesde) { this.fechaAcontecimientoDesde = fechaAcontecimientoDesde; }

    public String getFechaAcontecimientoHasta() { return fechaAcontecimientoHasta; }
    public void setFechaAcontecimientoHasta(String fechaAcontecimientoHasta) { this.fechaAcontecimientoHasta = fechaAcontecimientoHasta; }

    public String getUbicacionLatitud() { return ubicacionLatitud; }
    public void setUbicacionLatitud(String ubicacionLatitud) { this.ubicacionLatitud = ubicacionLatitud; }

    public String getUbicacionLongitud() { return ubicacionLongitud; }
    public void setUbicacionLongitud(String ubicacionLongitud) { this.ubicacionLongitud = ubicacionLongitud; }

    // --- Getters para campos parseados ---
    public LocalDate getFechaReporteDesdeParsed() { return fechaReporteDesdeParsed; }
    public LocalDate getFechaReporteHastaParsed() { return fechaReporteHastaParsed; }
    public LocalDate getFechaAcontecimientoDesdeParsed() { return fechaAcontecimientoDesdeParsed; }
    public LocalDate getFechaAcontecimientoHastaParsed() { return fechaAcontecimientoHastaParsed; }
    public Float getUbicacionLatitudParsed() { return ubicacionLatitudParsed; }
    public Float getUbicacionLongitudParsed() { return ubicacionLongitudParsed; }

    /**
     * Valida y parsea los campos String del DTO. Si hay un formato inválido lanza
     * IllegalArgumentException con un mensaje claro. Al finalizar, los campos
     * parseados quedan disponibles mediante los getters correspondientes.
     */
    public void validateAndParse() {
        // Parseo de fechas
        try {
            if (this.fechaReporteDesde != null && !this.fechaReporteDesde.isBlank())
                this.fechaReporteDesdeParsed = LocalDate.parse(this.fechaReporteDesde);
            if (this.fechaReporteHasta != null && !this.fechaReporteHasta.isBlank())
                this.fechaReporteHastaParsed = LocalDate.parse(this.fechaReporteHasta);
            if (this.fechaAcontecimientoDesde != null && !this.fechaAcontecimientoDesde.isBlank())
                this.fechaAcontecimientoDesdeParsed = LocalDate.parse(this.fechaAcontecimientoDesde);
            if (this.fechaAcontecimientoHasta != null && !this.fechaAcontecimientoHasta.isBlank())
                this.fechaAcontecimientoHastaParsed = LocalDate.parse(this.fechaAcontecimientoHasta);
        } catch (DateTimeParseException dte) {
            throw new IllegalArgumentException("Formato de fecha inválido. Use yyyy-MM-dd", dte);
        }

        // Parseo de ubicacion
        try {
            if (this.ubicacionLatitud != null && !this.ubicacionLatitud.isBlank())
                this.ubicacionLatitudParsed = Float.parseFloat(this.ubicacionLatitud);
            if (this.ubicacionLongitud != null && !this.ubicacionLongitud.isBlank())
                this.ubicacionLongitudParsed = Float.parseFloat(this.ubicacionLongitud);
        } catch (NumberFormatException nfe) {
            throw new IllegalArgumentException("Latitud o longitud inválida", nfe);
        }
    }
}

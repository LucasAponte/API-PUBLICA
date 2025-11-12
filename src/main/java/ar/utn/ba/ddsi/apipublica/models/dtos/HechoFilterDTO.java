package ar.utn.ba.ddsi.apipublica.models.dtos;

public class HechoFilterDTO {
    private String categoria;
    private String fechaReporteDesde;
    private String fechaReporteHasta;
    private String fechaAcontecimientoDesde;
    private String fechaAcontecimientoHasta;
    private String ubicacionLatitud;
    private String ubicacionLongitud;

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
}

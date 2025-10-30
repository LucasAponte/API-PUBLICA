package ar.utn.ba.ddsi.apipublica.models.dtos;

public class HechoCreateDTO {
    private String titulo;
    private String descripcion;
    private String fecha; // ISO yyyy-MM-dd

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

}


package com.example.turismohuascom.ui.rutas;

public class Ruta {
    private String id;
    private String titulo;
    private String descripcion;
    private String ubicacion;
    private String imagen;

    public Ruta(String id, String titulo, String descripcion, String ubicacion, String imagen) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.ubicacion = ubicacion;
        this.imagen = imagen;
    }

    public String getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public String getImagen() {
        return imagen;
    }
}

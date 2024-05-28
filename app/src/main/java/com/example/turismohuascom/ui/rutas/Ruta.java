package com.example.turismohuascom.ui.rutas;

public class Ruta {
    private String titulo;
    private String descripcion;
    private String ubicacion;
    private int imagen;

    public Ruta(String titulo, String descripcion, String ubicacion, int imagen) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.ubicacion = ubicacion;
        this.imagen = imagen;
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

    public int getImagen() {
        return imagen;
    }
}


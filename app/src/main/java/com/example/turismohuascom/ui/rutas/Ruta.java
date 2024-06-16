package com.example.turismohuascom.ui.rutas;

public class Ruta {
    private String id;
    private String ubicacion;
    private String imagen;
    private String descripcion;
    private String bano;
    private String camping;

    public Ruta(String id, String ubicacion, String imagen, String descripcion) {
        this.id = id;
        this.ubicacion = ubicacion;
        this.imagen = imagen;
        this.descripcion = descripcion;
    }

    public String getId() {
        return id;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public String getImagen() {
        return imagen;
    }

    public String getDescripcion() {
        return descripcion;
    }

}

package com.example.turismohuascom.ui.rutas;

public class Ruta {
    private String id;
    private String ubicacion;
    private String imagen;
    private String descripcion;
    private String bano;
    private String camping;

    public Ruta(String id, String ubicacion, String imagen, String descripcion, String bano, String camping) {
        this.id = id;
        this.ubicacion = ubicacion;
        this.imagen = imagen;
        this.descripcion = descripcion;
        this.bano = bano;
        this.camping = camping;
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

    public String getBano() {
        return bano;
    }

    public String getCamping() {
        return camping;
    }
}

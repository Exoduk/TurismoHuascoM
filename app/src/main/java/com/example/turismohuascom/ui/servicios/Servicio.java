package com.example.turismohuascom.ui.servicios;

public class Servicio {
    private String id;
    private String imagen;
    private String descripcion;
    private String direccion;
    private String correo;
    private String telefono;

    public Servicio(String id, String descripcion, String direccion, String correo, String telefono, String imagen) {
        this.id = id;
        this.descripcion = descripcion;
        this.direccion = direccion;
        this.correo = correo;
        this.telefono = telefono;
        this.imagen = imagen;
    }

    public String getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getCorreo() {
        return correo;
    }

    public String getImagen() {
        return imagen;
    }

    public String getTelefono() {
        return telefono;
    }
}

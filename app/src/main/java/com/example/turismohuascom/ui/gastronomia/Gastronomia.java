package com.example.turismohuascom.ui.gastronomia;

public class Gastronomia {
    private String id;
    private String direccion;
    private String telefono;
    private String correo;
    private String imagen;

    public Gastronomia(String id, String direccion, String telefono, String correo, String imagen) {
        this.id = id;
        this.direccion = direccion;
        this.telefono = telefono;
        this.correo = correo;
        this.imagen = imagen;
    }

    public String getId() {
        return id;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public String getImagen() {
        return imagen;
    }
}

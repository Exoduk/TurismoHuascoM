package com.example.turismohuascom.ui.alojamiento;

public class Alojamiento {
    private String id;
    private String direccion;
    private String imagen;
    private String correo;
    private String telefono;

    public Alojamiento(String id, String direccion, String imagen, String correo, String telefono) {
        this.id = id;
        this.direccion = direccion;
        this.imagen = imagen;
        this.correo = correo;
        this.telefono = telefono;
    }

    public String getId() {
        return id;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getImagen() {
        return imagen;
    }

    public String getCorreo() {
        return correo;
    }

    public String getTelefono() {
        return telefono;
    }
}

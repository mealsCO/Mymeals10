package com.edopore.mymeals10.modelo;

public class Restaurantes {

    private String nombre, foto, direccion, id, telefono;
    private float calificacion, latitud, longitud;

    public Restaurantes(String nombre, String foto, String direccion, String id, String telefono, float calificacion, float latitud, float longitud) {
        this.nombre = nombre;
        this.foto = foto;
        this.direccion = direccion;
        this.id = id;
        this.telefono = telefono;
        this.calificacion = calificacion;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public Restaurantes() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public float getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(float calificacion) {
        this.calificacion = calificacion;
    }

    public float getLatitud() {
        return latitud;
    }

    public void setLatitud(float latitud) {
        this.latitud = latitud;
    }

    public float getLongitud() {
        return longitud;
    }

    public void setLongitud(float longitud) {
        this.longitud = longitud;
    }
}

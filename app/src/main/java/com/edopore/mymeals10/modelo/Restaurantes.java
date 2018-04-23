package com.edopore.mymeals10.modelo;

public class Restaurantes {

    String nombre, foto, direccion, id, telefono, calificacion;


    public Restaurantes(String nombre, String foto, String direccion, String id, String telefono, String calificacion) {
        this.nombre = nombre;
        this.foto = foto;
        this.direccion = direccion;
        this.id = id;
        this.telefono = telefono;
        this.calificacion = calificacion;
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

    public String getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(String calificacion) {
        this.calificacion = calificacion;
    }
}

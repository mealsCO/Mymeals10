package com.edopore.mymeals10.modelo;

public class Especiales {

    private String restaurante, nombre, foto, tiempo, id, precio;
    private float calificacion;

    public Especiales(String restaurante, String nombre, String foto, String tiempo, String id, String precio, float calificacion) {
        this.restaurante = restaurante;
        this.nombre = nombre;
        this.foto = foto;
        this.tiempo = tiempo;
        this.id = id;
        this.precio = precio;
        this.calificacion = calificacion;
    }

    public Especiales() {
    }

    public String getRestaurante() {
        return restaurante;
    }

    public void setRestaurante(String restaurante) {
        this.restaurante = restaurante;
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

    public String getTiempo() {
        return tiempo;
    }

    public void setTiempo(String tiempo) {
        this.tiempo = tiempo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public float getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(float calificacion) {
        this.calificacion = calificacion;
    }
}

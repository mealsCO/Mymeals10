package com.edopore.mymeals10.modelo;

public class Especiales {

    private String nombre, foto, tiempo, id;
    private float calificacion;

    public Especiales(String nombre, String foto, String tiempo, String id, float calificacion) {
        this.nombre = nombre;
        this.foto = foto;
        this.tiempo = tiempo;
        this.id = id;
        this.calificacion = calificacion;
    }

    public Especiales() {
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

    public float getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(float calificacion) {
        this.calificacion = calificacion;
    }
}

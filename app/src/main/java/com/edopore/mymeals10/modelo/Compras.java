package com.edopore.mymeals10.modelo;

public class Compras {
    private String restaurante, plato, foto, id, precio;

    public Compras(String restaurante, String plato, String foto, String id, String precio) {
        this.restaurante = restaurante;
        this.plato = plato;
        this.foto = foto;
        this.id = id;
        this.precio = precio;
    }

    public Compras() {
    }

    public String getRestaurante() {
        return restaurante;
    }

    public void setRestaurante(String restaurante) {
        this.restaurante = restaurante;
    }

    public String getPlato() {
        return plato;
    }

    public void setPlato(String plato) {
        this.plato = plato;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
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
}

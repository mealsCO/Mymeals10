package com.edopore.mymeals10.modelo;

import android.net.Uri;

public class Usuarios {
    private String id, nombre, telefono, foto, correo;
    private int saldo;

    public Usuarios(String id, String nombre, String telefono, String correo, int saldo) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.correo = correo;
        this.saldo = saldo;
    }

    public Usuarios(String id, String nombre, String telefono, String foto, String correo, int saldo) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.foto = foto;
        this.correo = correo;
        this.saldo = saldo;
    }


    public Usuarios() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public int getSaldo() {
        return saldo;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }
}

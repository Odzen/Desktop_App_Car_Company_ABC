package org.openjfx.Models.Automovil;

import java.util.Date;

public class Automovil {

    private String Placa;
    private String marca;
    private String cilindraje;
    private String color;
    private String modelo;
    private String año;
    private int precio;
    private boolean activo;
    private Date fecha_creacion;
    private Date fecha_modificado;
    private int id_creado_por;
    private String sede;


    public Automovil() {
        this.Placa = "";
        this.marca = "";
        this.cilindraje = "";
        this.color = "";
        this.modelo = "";
        this.año = "";
        this.precio = 0;
        this.fecha_modificado = new Date();
        this.fecha_creacion = new Date();
        this.activo = true;
        this.id_creado_por = 0;
        this.sede = "";
    }


    public String getPlaca() {
        return Placa;
    }

    public void setPlaca(String placa) {
        this.Placa = placa;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getCilindraje() {
        return cilindraje;
    }

    public void setCilindraje(String cilindraje) {
        this.cilindraje = cilindraje;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getModelo() {
        return modelo;
    }

    public void setAño(String año) {
        this.año = año;
    }

    public String getAño() {
        return año;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public Date getFecha_creacion() {
        return fecha_creacion;
    }

    public void setFecha_creacion(Date fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    public Date getFecha_modificado() {
        return fecha_modificado;
    }

    public void setFecha_modificado(Date fecha_modificado) {
        this.fecha_modificado = fecha_modificado;
    }

    public int getId_creado_por() {
        return id_creado_por;
    }

    public void setId_creado_por(int id_creado_por) {
        this.id_creado_por = id_creado_por;
    }

    public String getSede() {
        return sede;
    }

    public void setSede(String id_sede) {
        this.sede = id_sede;
    }
}

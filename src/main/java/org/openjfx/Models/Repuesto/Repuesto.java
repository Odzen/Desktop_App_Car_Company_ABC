package org.openjfx.Models.Repuesto;

import java.util.Date;

public class Repuesto {

    private int id_repuesto;
    private boolean activo;
    private String marca;
    private String nombre;
    private int cantidad;
    private String cedula_creado_por;
    private Date fecha_creacion;
    private Date fecha_modificado;

    private String sede;


    public Repuesto() {
        this.id_repuesto =0;
        this.activo = true;
        this.marca = "";
        this.nombre = "";
        this.sede = "";
        this.cantidad = 0;
        this.cedula_creado_por = "";
        this.fecha_creacion= new Date();
        this.fecha_modificado = new Date();
    }

    public int getId_repuesto() {
        return id_repuesto;
    }

    public void setId_repuesto(int id_repuesto) {
        this.id_repuesto = id_repuesto;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getCedula_creado_por() {
        return cedula_creado_por;
    }

    public void setCedula_creado_por(String cedula_creado_por) {
        this.cedula_creado_por = cedula_creado_por;
    }

    public Date getFecha_creacion() {
        return fecha_creacion;
    }

    public void setFecha_creacion(Date fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    public String getSede() {
        return sede;
    }

    public void setSede(String sede) {
        this.sede = sede;
    }

    public Date getFecha_modificado() {
        return fecha_modificado;
    }

    public void setFecha_modificado(Date fecha_modificado) {
        this.fecha_modificado = fecha_modificado;
    }
}

package org.openjfx.Models.Sede;

import java.util.Date;

public class Sede {

    private int id_sede;
    private String direccion;
    private String telefono;
    private String nombre_sede;
    private boolean activo;
    private String ciudad;
    private Date fecha_creacion;
    private Date fecha_modificado;
    private int id_creado_por;


    public Sede() {
        this.id_sede = 0;
        this.direccion = "";
        this.telefono = "";
        this.nombre_sede = "";
        this.ciudad = "";
        this.fecha_modificado = new Date();
        this.fecha_creacion = new Date();
        this.activo = true;
        this.id_creado_por = 0;
    }

    public int getId_sede() {
        return id_sede;
    }

    public void setId_sede(int id_sede) {
        this.id_sede = id_sede;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getNombre_sede() {
        return nombre_sede;
    }

    public void setNombre_sede(String nombre_sede) {
        this.nombre_sede = nombre_sede;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
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
}

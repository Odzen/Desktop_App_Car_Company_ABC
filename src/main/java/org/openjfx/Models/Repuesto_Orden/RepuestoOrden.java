package org.openjfx.Models.Repuesto_Orden;

import java.util.Date;

public class RepuestoOrden {

    private int id_orden;
    private int id_repuesto;
    private int cantidad;
    private String cedula_creado_por;
    private Date fecha_creacion;
    private Date fecha_modificado;
    private boolean activo;

    public RepuestoOrden() {
        this.id_orden = 0;
        this.id_repuesto = 0;
        this.cedula_creado_por = "";
        this.fecha_creacion = new Date();
        this.fecha_modificado = new Date();
        this.activo = true;
    }

    public int getId_orden() {
        return id_orden;
    }

    public void setId_orden(int id_orden) {
        this.id_orden = id_orden;
    }

    public int getId_repuesto() {
        return id_repuesto;
    }

    public void setId_repuesto(int id_repuesto) {
        this.id_repuesto = id_repuesto;
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

    public Date getFecha_modificado() {
        return fecha_modificado;
    }

    public void setFecha_modificado(Date fecha_modificado) {
        this.fecha_modificado = fecha_modificado;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}

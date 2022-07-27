package org.openjfx.Models.Cotizacion;

import java.util.Date;

//Clase para modelar la cotizacion en la base de datos

public class Cotizacion  {

    private int id_cotizacion;
    private int IVA;
    private int TOTAL_IVA;
    private int TOTAL_SIN_IVA;
    private String descripcion;
    private Date fecha_modificado;
    private Date fecha_creacion;
    private String cedula_cliente;
    private String cedula_vendedor;
    private String placa_automovil;
    private int id_orden_trabajo;




    public Cotizacion() {
        this.IVA = 0;
        this.TOTAL_IVA = 0;
        this.TOTAL_SIN_IVA = 0;
        this.descripcion = "";
        this.fecha_modificado = new Date();
        this.fecha_creacion = new Date();
        this.cedula_cliente = "";
        this.cedula_vendedor = "";
        this.placa_automovil= "";
        this.id_orden_trabajo= 0;

    }


    public int getId_cotizacion() {
        return id_cotizacion;
    }

    public void setId_cotizacion(int id_cotizacion) {
        this.id_cotizacion = id_cotizacion;
    }

    public String getCedula_cliente() {
        return cedula_cliente;
    }

    public void setCedula_cliente(String cedula_cliente) {
        this.cedula_cliente = cedula_cliente;
    }

    public Date getFecha_creacion() {
        return fecha_creacion;
    }

    public void setFecha_creacion(Date fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    public void setid_orden_trabajo(int id_orden_trabajo) {
        this.id_orden_trabajo = id_orden_trabajo;
    }

    public int getid_orden_trabajo() {
        return id_orden_trabajo;
    }

    public Date getFecha_modificado() {
        return fecha_modificado;
    }

    public void setFecha_modificado(Date fecha_modificado) {
        this.fecha_modificado = fecha_modificado;
    }

    public int getIVA() {
        return IVA;
    }

    public void setIVA(int IVA) {
        this.IVA = IVA;
    }

    public int getTOTAL_IVA() {
        return TOTAL_IVA;
    }

    public void setTOTAL_IVA(int TOTAL_IVA) {
        this.TOTAL_IVA = TOTAL_IVA;
    }


    public int getTOTAL_SIN_IVA() {
        return TOTAL_SIN_IVA;
    }

    public void setTOTAL_SIN_IVA(int TOTAL_SIN_IVA) {
        this.TOTAL_SIN_IVA = TOTAL_SIN_IVA;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCedula_vendedor() {
        return cedula_vendedor;
    }

    public void setCedula_vendedor(String cedula_vendedor) {
        this.cedula_vendedor = cedula_vendedor;
    }

    public String getPlaca_automovil() {
        return placa_automovil;
    }

    public void setPlaca_automovil(String placa_automovil) {
        this.placa_automovil = placa_automovil;
    }

    @Override
    public String toString() {
        return "{" +
                ", id_cotizacion='" + id_cotizacion + '\'' +
                ", IVA='" + IVA + '\'' +
                ", TOTAL_IVA='" + TOTAL_IVA + '\'' +
                ", TOTAL_SIN_IVA='" + TOTAL_SIN_IVA + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", fecha_modificado=" + fecha_modificado + '\'' +
                ", fecha_creacion=" + fecha_creacion +'\'' +
                ", cedula_cliente=" + cedula_cliente +'\'' +
                ", cedula_vendedor='" + cedula_vendedor + '\'' +
                ", placa_automovil='" + placa_automovil + '\'' +
                ", id_tipo_usuario='" + id_orden_trabajo + '\'' +
                '}';
    }

}
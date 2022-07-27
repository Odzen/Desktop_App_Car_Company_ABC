package org.openjfx.Models.Cotizacion;

import java.util.Date;

//Clase para modelar la cotizacion en la base de datos

public class Cotizacion  {

    private int id_cotizacion;
    private double iva;
    private double total_iva;
    private double total_sin_iva;
    private String descripcion;
    private Date fecha_modificado;
    private Date fecha_creacion;
    private String cedula_cliente;
    private String cedula_vendedor;
    private String placa_automovil;
    private int id_orden_trabajo;




    public Cotizacion() {
        this.iva = 0;
        this.total_iva = 0;
        this.total_sin_iva = 0;
        this.descripcion = "";
        this.fecha_modificado = new Date();
        this.fecha_creacion = new Date();
        this.cedula_cliente = "";
        this.cedula_vendedor = "";
        this.placa_automovil= "";
        this.id_orden_trabajo= 0;

    }

    public int getId_orden_trabajo() {
        return id_orden_trabajo;
    }

    public void setId_orden_trabajo(int id_orden_trabajo) {
        this.id_orden_trabajo = id_orden_trabajo;
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

    public Date getFecha_modificado() {
        return fecha_modificado;
    }

    public void setFecha_modificado(Date fecha_modificado) {
        this.fecha_modificado = fecha_modificado;
    }

    public double getIva() {
        return iva;
    }

    public void setIva(double iva) {
        this.iva = iva;
    }

    public double getTotal_iva() {
        return total_iva;
    }

    public void setTotal_iva(double total_iva) {
        this.total_iva = total_iva;
    }

    public double getTotal_sin_iva() {
        return total_sin_iva;
    }

    public void setTotal_sin_iva(double total_sin_iva) {
        this.total_sin_iva = total_sin_iva;
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
                ", IVA='" + iva + '\'' +
                ", TOTAL_IVA='" + total_iva + '\'' +
                ", TOTAL_SIN_IVA='" + total_sin_iva + '\'' +
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
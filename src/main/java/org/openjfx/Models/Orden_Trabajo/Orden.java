package org.openjfx.Models.Orden_Trabajo;

import org.openjfx.Models.Orden_Trabajo.Utils.Estado;
import org.openjfx.Models.Usuario.Utils.Rol;

import java.util.Date;

public class Orden {
    private int id_orden;
    private Date fecha_creacion;
    private Date fecha_modificado;
    private boolean activo;
    private String cedula_cliente;
    private String cedula_jefe_de_taller;
    private String placa_automovil;
    private int id_estado_orden;
    private Estado estado;

    public int getId_orden() {
        return id_orden;
    }

    public void setId_orden(int id_orden) {
        this.id_orden = id_orden;
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

    public String getCedula_cliente() {
        return cedula_cliente;
    }

    public void setCedula_cliente(String cedula_cliente) {
        this.cedula_cliente = cedula_cliente;
    }

    public String getCedula_jefe_de_taller() {
        return cedula_jefe_de_taller;
    }

    public void setCedula_jefe_de_taller(String cedula_jefe_de_taller) {
        this.cedula_jefe_de_taller = cedula_jefe_de_taller;
    }

    public String getPlaca_automovil() {
        return placa_automovil;
    }

    public void setPlaca_automovil(String placa_automovil) {
        this.placa_automovil = placa_automovil;
    }

    public int getId_estado_orden() {
        return id_estado_orden;
    }

    public void setId_estado_orden(int id_estado_orden) {
        this.id_estado_orden = id_estado_orden;
        if (this.id_estado_orden == 1) {
            this.setEstado(Estado.EN_ESPERA);
        } else if (this.id_estado_orden == 2) {
            this.setEstado(Estado.EN_PROGRESO);
        } else if (this.id_estado_orden == 3) {
            this.setEstado(Estado.TERMINADA);
        }
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }
}

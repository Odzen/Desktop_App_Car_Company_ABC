package org.openjfx.Models.Cliente;

import org.openjfx.Models.Usuario.Usuario;

import java.util.Date;

//Clase para modelar el Usuario en la base de datos
public class Cliente extends Usuario {

    private String cedula_cliente;
    private String email;
    private String nombre;
    private String apellido;
    private Date modificado;
    private boolean activo;
    private Date joined;
    private Date fecha_nacimiento;
    private String telefono;
    private String sede;
    private String last_session;
    private int id_tipo_usuario;
    private String cedula_creado_por;


    public Cliente() {
        this.cedula_cliente = "";
        this.email = "";
        this.nombre = "";
        this.apellido = "";
        this.modificado = new Date();
        this.joined = new Date();
        this.telefono = "";
        this.activo = true;
        this.fecha_nacimiento = null;
        this.last_session = "";
        this.id_tipo_usuario = 0;
        this.sede = "";
        this.cedula_creado_por= "";
    }


    public String getSede() {
        return sede;
    }

    public void setSede(String sede) {
        this.sede = sede;
    }

    public String getCedula() {
        return cedula_cliente;
    }

    public void setCedula(String cedula) {
        this.cedula_cliente = cedula;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Date getModificado() {
        return modificado;
    }

    public void setModificado(Date modificado) {
        this.modificado = modificado;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public Date getJoined() {
        return joined;
    }

    public void setJoined(Date joined) {
        this.joined = joined;
    }

    public Date getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(Date fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public String getLast_session() {
        return last_session;
    }

    public void setLast_session(String last_session) {
        this.last_session = last_session;
    }

    public int getId_tipo_usuario() {
        return id_tipo_usuario;
    }

    public String getCedula_creado_por() {
        return cedula_creado_por;
    }

    public void setCedula_creado_por(String cedula_creado_por) {
        this.cedula_creado_por = cedula_creado_por;
    }


    @Override
    public String toString() {
        return "{" +
                ", cedula_cliente='" + cedula_cliente + '\'' +
                ", email='" + email + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", modificado=" + modificado +
                ", activo=" + activo +
                ", joined=" + joined +
                ", fecha_nacimiento='" + fecha_nacimiento + '\'' +
                ", telefono='" + telefono + '\'' +
                ", last_session='" + last_session + '\'' +
                ", id_tipo_usuario='" + id_tipo_usuario + '\'' +
                ", creado por='" + cedula_creado_por + '\'' +
                '}';
    }
}
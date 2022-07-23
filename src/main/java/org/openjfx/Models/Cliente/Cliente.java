package org.openjfx.Models.Cliente;

import org.openjfx.Models.Usuario.Usuario;
import org.openjfx.Models.Usuario.Utils.Rol;

import java.util.Date;

//Clase para modelar el Usuario en la base de datos
public class Cliente extends Usuario {

    private String cedula_cliente;
    private String email;
    private String nombre;
    private String apellido;

    private Date fecha_modificado;

    private Date fecha_creacion;

    private String direccion;
    private boolean activo;
    private Date fecha_nacimiento;
    private String telefono;
    private int id_tipo_usuario;

    private String user_type;

    private String cedula_creado_por;
    private String sede;



    public Cliente() {
        this.cedula_cliente = "";
        this.email = "";
        this.nombre = "";
        this.apellido = "";
        this.fecha_modificado = new Date();
        this.fecha_creacion = new Date();
        this.direccion = "";
        this.activo = true;
        this.fecha_nacimiento = null;
        this.telefono = "";
        this.id_tipo_usuario = 0;
        this.user_type= "";
        this.cedula_creado_por= "";
        this.sede = "";

    }


    public String getSede() {
        return sede;
    }

    public void setSede(String sede) {
        this.sede = sede;
    }

    public String getCedula_cliente() {
        return cedula_cliente;
    }

    public void setCedula_cliente(String cedula_cliente) {
        this.cedula_cliente = cedula_cliente;
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

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Date getFecha_creacion() {
        return fecha_creacion;
    }

    public void setFecha_creacion(Date fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    public void setId_tipo_usuario(int id_tipo_usuario) {
        this.id_tipo_usuario = id_tipo_usuario;
    }

    public int getId_tipo_usuario() {
        return id_tipo_usuario;
    }

    public Date getFecha_modificado() {
        return fecha_modificado;
    }

    public void setFecha_modificado(Date fecha_modificado) {
        this.fecha_modificado = fecha_modificado;
    }

    public Date getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(Date fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }


    public String getCedula_creado_por() {
        return cedula_creado_por;
    }

    public void setCedula_creado_por(String cedula_creado_por) {
        this.cedula_creado_por = cedula_creado_por;
    }

    public Rol getUser_type() {return Rol.valueOf(user_type);}

    public void setUser_type(Rol user_type) {
        this.user_type = String.valueOf(user_type);
    }


    @Override
    public String toString() {
        return "{" +
                ", cedula_cliente='" + cedula_cliente + '\'' +
                ", email='" + email + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", fecha_modificado=" + fecha_modificado + '\'' +
                ", fecha_creacion=" + fecha_creacion +'\'' +
                ", direccion=" + direccion +'\'' +
                ", activo=" + activo +
                ", fecha_nacimiento='" + fecha_nacimiento + '\'' +
                ", telefono='" + telefono + '\'' +
                ", id_tipo_usuario='" + id_tipo_usuario + '\'' +
                ", user_type='" + user_type + '\'' +
                ", creado por='" + cedula_creado_por + '\'' +
                ", sede='" + sede + '\'' +
                '}';
    }
}
package org.openjfx.Models.Usuario;

import org.openjfx.Models.Usuario.Utils.Rol;
import java.util.Date;

//Clase para modelar el Usuario en la base de datos
public class Usuario {

    private int id_usuario;

    private String cedula;
    private String contraseña;
    private String email;
    private String nombre;
    private String apellido;
    private Date modificado;
    private String avatar;
    private boolean activo;
    private Date joined;
    private Date fecha_nacimiento;
    private String telefono;
    private String sede;
    private String last_session;
    private Rol user_type;
    private int id_tipo_usuario;
    private String cedula_creado_por;


    public Usuario() {
        this.id_usuario = 0;
        this.cedula = "";
        this.contraseña = "";
        this.email = "";
        this.nombre = "";
        this.apellido = "";
        this.modificado = new Date();
        this.avatar = "";
        this.joined = new Date();
        this.user_type = Rol.INDEFINIDO;
        this.telefono = "";
        this.activo = true;
        this.fecha_nacimiento = null;
        this.last_session = "";
        this.id_tipo_usuario = 0;
        this.sede = "";
        this.cedula_creado_por= "";
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public String getSede() {
        return sede;
    }

    public void setSede(String sede) {
        this.sede = sede;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getContraseña() {
        return contraseña;
    }

    public String getTelefono() {
        return telefono;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
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

    public Rol getUser_type() {
        return user_type;
    }

    public void setUser_type(Rol user_type) {
        this.user_type = user_type;
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

    public void setId_tipo_usuario(int id_tipo_usuario) {
        this.id_tipo_usuario = id_tipo_usuario;
        if (this.id_tipo_usuario == 1) {
            this.setUser_type(Rol.ADMIN);
        } else if (this.id_tipo_usuario == 2) {
            this.setUser_type(Rol.GERENTE);
        } else if (this.id_tipo_usuario == 3) {
            this.setUser_type(Rol.JEFE_TALLER);
        } else if (this.id_tipo_usuario == 4) {
            this.setUser_type(Rol.VENDEDOR);
        } else {
            this.setUser_type(Rol.INDEFINIDO);
        }
    }

    @Override
    public String toString() {
        return "{" +
                "id_usuario=" + id_usuario +  '\'' +
                ", cedula='" + cedula + '\'' +
                ", contraseña='" + contraseña + '\'' +
                ", email='" + email + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", modificado=" + modificado +
                ", avatar='" + avatar + '\'' +
                ", activo=" + activo +
                ", joined=" + joined +
                ", fecha_nacimiento='" + fecha_nacimiento + '\'' +
                ", telefono='" + telefono + '\'' +
                ", last_session='" + last_session + '\'' +
                ", user_type='" + user_type + '\'' +
                ", id_tipo_usuario='" + id_tipo_usuario + '\'' +
                ", creado por='" + cedula_creado_por + '\'' +
                '}';
    }
}
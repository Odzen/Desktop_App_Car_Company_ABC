package org.openjfx.Models.Usuario;

import Modelo.Usuario.Utils.Rol;
import java.time.LocalDateTime;
import java.util.Date;

//Clase para modelar el Usuario en la base de datos
public class Usuario {

    private int id_usuario;
    private String contraseña;
    private String email;
    private String nombre;
    private String apellido;
    private Date modificado;
    private String avatar;
    private boolean activo;
    private Date joined;
    private Date fecha_nacimiento;
    private LocalDateTime last_session;
    private Rol user_type;
    private int id_tipo_usuario;


    public Usuario() {
        this.id_usuario = 0;
        this.contraseña = "";
        this.email = "";
        this.nombre = "";
        this.apellido = "";
        this.modificado = new Date();
        this.avatar = "";
        this.joined = new Date();
        this.user_type = Rol.INDEFINIDO;
        this.activo = true;
        this.fecha_nacimiento = null;
        this.last_session = LocalDateTime.now();
        this.id_tipo_usuario = 0;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getContraseña() {
        return contraseña;
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

    public LocalDateTime getLast_session() {
        return last_session;
    }

    public void setLast_session(LocalDateTime last_session) {
        this.last_session = last_session;
    }

    public int getId_tipo_usuario() {
        return id_tipo_usuario;
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
                "id_usuario=" + id_usuario +
                ", contraseña='" + contraseña + '\'' +
                ", email='" + email + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", modificado=" + modificado +
                ", avatar='" + avatar + '\'' +
                ", activo=" + activo +
                ", joined=" + joined +
                ", fecha_nacimiento='" + fecha_nacimiento + '\'' +
                ", last_session='" + last_session + '\'' +
                ", user_type='" + user_type + '\'' +
                ", id_tipo_usuario='" + id_tipo_usuario + '\'' +
                '}';
    }
}
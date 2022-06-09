package src.Modelo.Usuario;

import java.util.Date;

//Clase para modelar el Usuario en la base de datos
public class Usuario {
    private String contraseña;
    private String email;
    private String nombre;
    private String apellido;
    private Date modificado;
    private String avatar;
    private boolean activo;
    private Date joined;
    private String user_type;

    public Usuario() {
        this.contraseña = "";
        this.email = "";
        this.nombre = "";
        this.apellido = "";
        this.modificado = new Date();
        this.avatar = "";
        this.joined = new Date();
        this.user_type = "";
        this.activo = true;

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

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }
}
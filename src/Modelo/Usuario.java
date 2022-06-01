package Modelo;

"Clase para modelar el Usuario en la base de datos"
public class Usuario {
    private int id_usuario;
    private char[] contraseña;
    private String email;
    private String nombre;
    private String apellido;
    private String modificado;
    private String avatar;
    private boolean activo;
    private String joined;
    private String user_type;


    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public char[] getContraseña() {
        return contraseña;
    }

    public void setContraseña(char[] contraseña) {
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

    public String getModificado() {
        return modificado;
    }

    public void setModificado(String modificado) {
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

    public String getJoined() {
        return joined;
    }

    public void setJoined(String joined) {
        this.joined = joined;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }
}
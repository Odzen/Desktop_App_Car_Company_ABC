package org.openjfx.Models.Usuario;

import org.openjfx.Models.Usuario.Utils.Rol;

import java.util.Date;

public class Sesion {

    private int id_usuario;

    public Sesion() {
        this.id_usuario = 0;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }
}

package src.Modelo.Usuario;

import src.Modelo.Conexion;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class CRUD_Usuario {

    private static final Conexion connection = new Conexion();
    public CRUD_Usuario(){
    }

    public void verUsuarios() {

    }

    public void insertarUsuario(Usuario usuario) {
        try {
            PreparedStatement sentencia = this.connection.getConnection().prepareStatement(
                    "INSERT INTO usuario " +
                            "(nombre, apellido, contraseña, email, joined, modificado, activo, avatar, user_type)" +
                            "VALUES  (?,?,?,?,?,?,?,?,?)");
            sentencia.setString(1, usuario.getNombre());
            sentencia.setString(2, usuario.getApellido());
            sentencia.setString(3, usuario.getContraseña());
            sentencia.setString(4, usuario.getEmail());
            sentencia.setDate(5,  new java.sql.Date(usuario.getJoined().getTime()));
            sentencia.setDate(6,  new java.sql.Date(usuario.getModificado().getTime()));
            sentencia.setBoolean(7, usuario.isActivo());
            sentencia.setString(8, usuario.getAvatar());
            sentencia.setString(9, usuario.getUser_type());

            sentencia.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void editarUsuarios() {

    }

    public void eliminarUsuarios() {

    }


}

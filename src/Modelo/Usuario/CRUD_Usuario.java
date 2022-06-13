package src.Modelo.Usuario;

import com.sun.tools.jconsole.JConsoleContext;
import src.Modelo.Conexion;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class CRUD_Usuario {

    private static final Conexion connection = new Conexion();
    public CRUD_Usuario(){
    }

    // Verifica si un usuario existe o no en la base de datos, basado en su ID
    public boolean existeUsuario(int id_usuario)  {
        try {
            PreparedStatement sentencia = this.connection.getConnection().prepareStatement(
                    "SELECT * FROM usuario WHERE id_usuario="+ id_usuario
            );
            ResultSet resultado = sentencia.executeQuery();
            if (resultado.next()) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Obtiene todos los registros de Usuario que están en la base de datos
    public ArrayList<Usuario> leerTodosLosUsuarios() {
        try {
            PreparedStatement sentencia = this.connection.getConnection().prepareStatement(
                    "SELECT * FROM usuario ORDER BY id_usuario"
            );
            ResultSet resultado = sentencia.executeQuery();

            ArrayList<Usuario> usuariosResultado = new ArrayList<Usuario>();
            int contador = 0;

            while (resultado.next()) {
                Usuario usuario = new Usuario();

                int id_usuario = resultado.getInt("id_usuario");
                usuario.setId_usuario(id_usuario);
                String contraseña = resultado.getString("contraseña");
                usuario.setContraseña(contraseña);
                String email = resultado.getString("email");
                usuario.setEmail(email);
                String nombre = resultado.getString("nombre");
                usuario.setNombre(nombre);
                String apellido = resultado.getString("apellido");
                usuario.setApellido(apellido);
                Date modificado = resultado.getDate("modificado");
                usuario.setModificado(modificado);
                String avatar = resultado.getString("avatar");
                usuario.setAvatar(avatar);
                boolean activo = resultado.getBoolean("activo");
                usuario.setActivo(activo);
                Date joined = resultado.getDate("joined");
                usuario.setJoined(joined);
                String user_type = resultado.getString("user_type");
                usuario.setUser_type(user_type);

                usuariosResultado.add(usuario);

                contador++;
            }
            System.out.println("Operación Exitosa: Lectura de Usuarios de la Base de datos!!");
            return usuariosResultado;

        } catch (SQLException e) {
            System.out.printf("Error al leer los usuarios", e);
            throw new RuntimeException(e);
        }
    }

    // Crea un usuario con la base de datos
    public void crearUsuario(Usuario usuario) {
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

            System.out.println("Operación Exitosa: Creación de Usuario");

        } catch (SQLException e) {
            System.out.printf("Error al crear el Usuario", e);
            throw new RuntimeException(e);
        }
    }


    // Edita un usuario en la base de datos
    public void editarUsuarios(int id_usuario, Usuario usuarioActualizado) {

        if ( this.existeUsuario(id_usuario)) {
            java.util.Date modificado = new java.util.Date();
            java.sql.Date modificadoSql = new java.sql.Date(modificado.getTime());
            try {
                PreparedStatement sentencia = connection.getConnection().prepareStatement(
                        "UPDATE usuario SET " +
                                "contraseña = ? , " +
                                "email = ? , " +
                                "nombre = ? , " +
                                "apellido = ? , " +
                                "modificado = ? , " +
                                "avatar= ? , " +
                                "user_type= ? " +
                                "WHERE id_usuario = ?");
                sentencia.setString(1, usuarioActualizado.getContraseña());
                sentencia.setString(2, usuarioActualizado.getEmail());
                sentencia.setString(3, usuarioActualizado.getNombre());
                sentencia.setString(4, usuarioActualizado.getApellido());
                sentencia.setDate(5, modificadoSql);
                sentencia.setString(6, usuarioActualizado.getAvatar());
                sentencia.setString(7, usuarioActualizado.getUser_type());
                sentencia.setInt(8, id_usuario);

                int filasAfectadas = sentencia.executeUpdate();

                if (filasAfectadas == 0) {
                    System.out.println("No se modificó nada !");
                } else {
                    System.out.println("Se modificaron exitosamente: " + filasAfectadas + " registros");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("El usuario con ese id NO existe, por favor dijiste un id correcto!");
        }
    }

    public void eliminarUsuarios() {

    }


}

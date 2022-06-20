package org.openjfx.Usuario;

import org.openjfx.Conexion;

import java.sql.*;
import java.util.ArrayList;


public class CRUD_Usuario {

    private static final Conexion conexion = new Conexion();
    private Connection connection = null;
    public CRUD_Usuario(){
        this.connection =  conexion.getConnection();
    }

    // Verifica si un usuario existe o no en la base de datos, basado en su ID
    public boolean existeUsuario_Id(int id_usuario)  {
        try {
            PreparedStatement sentencia = this.connection.prepareStatement(
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

    // Verifica si un usuario existe o no en la base de datos, basado en su nombre
    public boolean existeUsuario_Nombre(String nombre)  {
        try {
            PreparedStatement sentencia = this.connection.prepareStatement(
                    "SELECT * FROM usuario WHERE nombre="+ nombre
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
            PreparedStatement sentencia = this.connection.prepareStatement(
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
                Date fecha_nacimiento = resultado.getDate("fecha_nacimiento");
                usuario.setFecha_nacimiento(fecha_nacimiento);
                Timestamp last_session = resultado.getTimestamp("last_session");
                usuario.setLast_session(last_session.toLocalDateTime());
                int id_tipo_usuario = resultado.getInt("id_tipo_usuario");
                usuario.setId_tipo_usuario(id_tipo_usuario);

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
            PreparedStatement sentencia = this.connection.prepareStatement(
                    "INSERT INTO usuario " +
                            "(nombre, apellido, contraseña, email, joined, modificado, activo, avatar, fecha_nacimiento, last_session, user_type, id_tipo_usuario )" +
                            "VALUES  (?,?,?,?,?,?,?,?,?,?,?,?)");
            sentencia.setString(1, usuario.getNombre());
            sentencia.setString(2, usuario.getApellido());
            sentencia.setString(3, usuario.getContraseña());
            sentencia.setString(4, usuario.getEmail());
            sentencia.setDate(5,  new java.sql.Date(usuario.getJoined().getTime()));
            sentencia.setDate(6,  new java.sql.Date(usuario.getModificado().getTime()));
            sentencia.setBoolean(7, usuario.isActivo());
            sentencia.setString(8, usuario.getAvatar());
            sentencia.setDate(9, new java.sql.Date(usuario.getFecha_nacimiento().getTime()));
            sentencia.setTimestamp(10, Timestamp.valueOf(usuario.getLast_session()));
            sentencia.setString(11, usuario.getUser_type().name());
            sentencia.setInt(12, usuario.getId_tipo_usuario());

            sentencia.execute();

            System.out.println("Operación Exitosa: Creación de Usuario");

        } catch (SQLException e) {
            System.out.printf("Error al crear el Usuario", e);
            throw new RuntimeException(e);
        }
    }


    // Edita un usuario en la base de datos
    public void editarUsuarios(int id_usuario, Usuario usuarioActualizado) {

        if ( this.existeUsuario_Id(id_usuario)) {
            java.util.Date modificado = new java.util.Date();
            java.sql.Date modificadoSql = new java.sql.Date(modificado.getTime());
            try {
                PreparedStatement sentencia = connection.prepareStatement(
                        "UPDATE usuario SET " +
                                "contraseña = ? , " +
                                "email = ? , " +
                                "nombre = ? , " +
                                "apellido = ? , " +
                                "modificado = ? , " +
                                "avatar= ? , " +
                                "fecha_nacimiento= ?, " +
                                "last_session= ?, " +
                                "id_tipo_usuario= ?, " +
                                "user_type= ? " +
                                "WHERE id_usuario = ?");
                sentencia.setString(1, usuarioActualizado.getContraseña());
                sentencia.setString(2, usuarioActualizado.getEmail());
                sentencia.setString(3, usuarioActualizado.getNombre());
                sentencia.setString(4, usuarioActualizado.getApellido());
                sentencia.setDate(5, modificadoSql);
                sentencia.setString(6, usuarioActualizado.getAvatar());
                sentencia.setDate(7, new java.sql.Date(usuarioActualizado.getFecha_nacimiento().getTime()));
                sentencia.setTimestamp(8, Timestamp.valueOf(usuarioActualizado.getLast_session()));
                System.out.println(usuarioActualizado.getUser_type().toString());
                sentencia.setInt(9, usuarioActualizado.getId_tipo_usuario());
                sentencia.setString(10, usuarioActualizado.getUser_type().toString());
                sentencia.setInt(11, id_usuario);

                int filasAfectadas = sentencia.executeUpdate();
                System.out.println(filasAfectadas);

                if (filasAfectadas == 0) {
                    System.out.println("No se modificó nada !");
                } else {
                    System.out.println("Se modificaron exitosamente: " + filasAfectadas + " registros");
                }
            } catch (SQLException e) {
                System.err.println(e);
            }
        } else {
            System.out.println("El usuario con ese id NO existe, por favor dijiste un id correcto!");
        }
    }

    // Elimina al usuario poniendolo inactivo en la base de datos
    public void eliminarUsuario(int id_usuario) {
        if(this.existeUsuario_Id(id_usuario)) {
            java.util.Date modificado = new java.util.Date();
            java.sql.Date modificadoSql = new java.sql.Date(modificado.getTime());
            try {
                PreparedStatement sentencia = connection.prepareStatement(
                        "UPDATE usuario SET " +
                                "modificado = ? , " +
                                "activo= ?  " +
                                "WHERE id_usuario = ?");
                sentencia.setDate(1, modificadoSql);
                sentencia.setBoolean(2, false);
                sentencia.setInt(3, id_usuario);

                int filasAfectadas = sentencia.executeUpdate();

                if (filasAfectadas == 0) {
                    System.out.println("No se modificó nada !");
                } else {
                    System.out.println("Se cambio el estado a INACTIVO del usuario en la base de datos");
                }

            } catch (SQLException e) {
                System.err.println(e);
            }
        } else {
            System.out.println("El usuario con ese id NO existe, por favor dijiste un id correcto!");
        }
    }


    /* Metodos para DEV y NO produccion: Los metodos sgtes son para el ambiente de desarrollo pero no se deberían de usar en producción*/
    public void eliminarTodosUsuarios() {
        try {
            PreparedStatement sentencia = connection.prepareStatement(
                    "TRUNCATE usuario"
            );
            System.out.println("Borro TODOS LOS USUARIOS exitosamente!!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void eliminarUsuarioPorId(int id_usuario) {
        if (this.existeUsuario_Id(id_usuario)) {
            try {
                PreparedStatement sentencia = connection.prepareStatement(
                        "DELETE FROM usuario WHERE id_usuario=?"
                );
                sentencia.setInt(1, id_usuario);
                sentencia.executeUpdate();
                System.out.println("Borro el usuario con el id" + id_usuario + " exitosamente!");

            } catch (SQLException e) {
                System.err.println(e);
            }
        } else {
            System.out.println("El usuario con ese id NO existe, por favor dijiste un id correcto!");
        }
    }
}

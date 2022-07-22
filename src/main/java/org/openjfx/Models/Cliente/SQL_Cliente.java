package org.openjfx.Models.Cliente;

import org.openjfx.Models.Conexion;
import org.openjfx.Models.Cliente.Utils.*;
import org.openjfx.Models.Usuario.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class SQL_Cliente {

    private static Conexion conexion = new Conexion();
    private static Connection connection = conexion.getConnection();


    public static ResultSet obtenerTodosClienteSet() {
        try {
            PreparedStatement sentencia = connection.prepareStatement(
                    "SELECT * FROM cliente ORDER BY cedula_cliente"
            );

            ResultSet resultadoCliente = sentencia.executeQuery();
            return resultadoCliente;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Verifica si un usuario existe o no en la base de datos, basado en su cédula
    public static boolean existeCliente_Cedula(String cedula_cliente)  {
        try {
            PreparedStatement sentencia = connection.prepareStatement(
                    "SELECT * FROM cliente WHERE cedula_cliente= ?"
            );

            sentencia.setString(1, cedula_cliente);
            ResultSet resultadoCliente = sentencia.executeQuery();
            if (resultadoCliente.next()) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Verifica si un admin puede modificar
    public static boolean puedoModificarCliente(String cedula_cliente)  {
        try {
            PreparedStatement sentencia = connection.prepareStatement(
                    "SELECT * FROM cliente WHERE cedula_cliente= ? AND (user_type=? OR user_type=?)"
            );

            sentencia.setString(1, cedula_cliente);
            ResultSet resultadoCliente = sentencia.executeQuery();
            if (resultadoCliente.next()) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    // Obtiene un usuario buscandolo por su cédula
    public static ResultSet obtenerCliente_Cedula(String cedula_cliente)  {
        try {
            PreparedStatement sentencia = connection.prepareStatement(
                    "SELECT * FROM cliente WHERE cedula_cliente= ?"
            );

            sentencia.setString(1, cedula_cliente);
            ResultSet resultadoCliente = sentencia.executeQuery();
            return resultadoCliente;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    // Verifica si un usuario existe o no en la base de datos, basado en su nombre
    public static boolean existeCliente_Nombre(String nombre)  {
        try {
            PreparedStatement sentencia = connection.prepareStatement(
                    "SELECT * FROM cliente WHERE nombre="+ nombre
            );
            ResultSet resultadoCliente = sentencia.executeQuery();
            if (resultadoCliente.next()) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Obtiene todos los registros de Usuario que están en la base de datos

    public static ArrayList<Cliente> leerTodosLosClientes() {
        try {
            PreparedStatement sentencia = connection.prepareStatement(
                    "SELECT * FROM cliente ORDER BY cedula_cliente"
            );
            ResultSet resultadoCliente = sentencia.executeQuery();

            ArrayList<Cliente> usuariosResultado = new ArrayList<Cliente>();
            int contador = 0;

            while (resultadoCliente.next()) {
                Cliente cliente = new Cliente();

                String cedula_cliente = resultadoCliente.getString("cedula_cliente");
                cliente.setCedula(cedula_cliente);
                String email = resultadoCliente.getString("email");
                cliente.setEmail(email);
                String nombre = resultadoCliente.getString("nombre");
                cliente.setNombre(nombre);
                String apellido = resultadoCliente.getString("apellido");
                cliente.setApellido(apellido);
                Date modificado = resultadoCliente.getDate("modificado");
                cliente.setModificado(modificado);
                boolean activo = resultadoCliente.getBoolean("activo");
                cliente.setActivo(activo);
                Date joined = resultadoCliente.getDate("joined");
                cliente.setJoined(joined);
                Date fecha_nacimiento = resultadoCliente.getDate("fecha_nacimiento");
                cliente.setFecha_nacimiento(fecha_nacimiento);
                String telefono = resultadoCliente.getString("telefono");
                cliente.setTelefono(telefono);
                String last_session = resultadoCliente.getString("last_session");
                cliente.setLast_session(last_session);
                int id_tipo_usuario = resultadoCliente.getInt("id_tipo_usuario");
                cliente.setId_tipo_usuario(id_tipo_usuario);

                usuariosResultado.add(cliente);

                contador++;
            }
            System.out.println("Operación Exitosa: Lectura de clientes de la Base de datos!!");
            return usuariosResultado;

        } catch (SQLException e) {
            System.out.printf("Error al leer los clientes", e);
            throw new RuntimeException(e);
        }
    }

    // Crea un usuario con la base de datos
    public static void crearCliente(Cliente cliente) {
        try {
            PreparedStatement sentencia = connection.prepareStatement(
                    "INSERT INTO usuario " +
                            "(cedula_cliente, nombre, apellido, email, joined, modificado, activo, fecha_nacimiento, telefono, last_session, user_type, id_tipo_usuario, sede, cedula_creado_por )" +
                            "VALUES  (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            sentencia.setString(1, cliente.getCedula());
            sentencia.setString(2, cliente.getNombre());
            sentencia.setString(3, cliente.getApellido());
            sentencia.setString(4, cliente.getEmail());
            sentencia.setDate(5,  new java.sql.Date(cliente.getJoined().getTime()));
            sentencia.setDate(6,  new java.sql.Date(cliente.getModificado().getTime()));
            sentencia.setBoolean(7, cliente.isActivo());
            sentencia.setDate(8, new java.sql.Date(cliente.getFecha_nacimiento().getTime()));
            sentencia.setString(9, cliente.getTelefono());
            sentencia.setString(10, cliente.getLast_session());
            sentencia.setInt(12, cliente.getId_tipo_usuario());
            sentencia.setString(13, cliente.getSede());
            sentencia.setString(14, cliente.getCedula_creado_por());

            sentencia.execute();

            System.out.println("Operación Exitosa: Creación de Usuario");


        } catch (SQLException e) {
            System.out.printf("Error al crear el Usuario", e);
            throw new RuntimeException(e);
        }
    }


    // Edita un usuario en la base de datos
    public static void editarClientes(String cedula_cliente, Cliente usuarioActualizado) {

        if ( existeCliente_Cedula(cedula_cliente)) {
            java.util.Date modificado = new java.util.Date();
            java.sql.Date modificadoSql = new java.sql.Date(modificado.getTime());
            try {
                PreparedStatement sentencia = connection.prepareStatement(
                        "UPDATE cliente SET " +
                                "cedula_cliente = ? , " +
                                "email = ? , " +
                                "nombre = ? , " +
                                "apellido = ? , " +
                                "modificado = ? , " +
                                "fecha_nacimiento= ?, " +
                                "telefono = ?, " +
                                "last_session= ?, " +
                                "id_tipo_usuario= ?, " +
                                "user_type= ?, " +
                                "sede= ?, " +
                                "cedula_creado_por= ? " +
                                "WHERE cedula_cliente = ?");
                sentencia.setString(1, cedula_cliente);
                sentencia.setString(3, usuarioActualizado.getEmail());
                sentencia.setString(4, usuarioActualizado.getNombre());
                sentencia.setString(5, usuarioActualizado.getApellido());
                sentencia.setDate(6, modificadoSql);
                sentencia.setDate(8, new java.sql.Date(usuarioActualizado.getFecha_nacimiento().getTime()));
                sentencia.setString(9, usuarioActualizado.getTelefono());
                sentencia.setString(10, usuarioActualizado.getLast_session());
                sentencia.setInt(11, usuarioActualizado.getId_tipo_usuario());
                sentencia.setString(12, usuarioActualizado.getSede());
                sentencia.setString(13, usuarioActualizado.getCedula_creado_por());

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

    // Elimina al usuario poniendolo inactivo en la base de datos - SOFT DELETE
    public static void eliminarCliente(String cedula_cliente) {
        if(existeCliente_Cedula(cedula_cliente)) {
            java.util.Date modificado = new java.util.Date();
            java.sql.Date modificadoSql = new java.sql.Date(modificado.getTime());
            try {
                PreparedStatement sentencia = connection.prepareStatement(
                        "UPDATE cliente SET " +
                                "modificado = ? , " +
                                "activo= ?  " +
                                "WHERE cedula_cliente = ?");
                sentencia.setDate(1, modificadoSql);
                sentencia.setBoolean(2, false);
                sentencia.setString(3, cedula_cliente);

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

    // Elimina al usuario poniendolo inactivo en la base de datos
    public static void cambiarEstadoClientePorCedula(String cedula_cliente, boolean activo) {
        if(existeCliente_Cedula(cedula_cliente)) {
            java.util.Date modificado = new java.util.Date();
            java.sql.Date modificadoSql = new java.sql.Date(modificado.getTime());
            try {
                PreparedStatement sentencia = connection.prepareStatement(
                        "UPDATE usuario SET " +
                                "modificado = ? , " +
                                "activo= ?  " +
                                "WHERE cedula_cliente = ?");
                sentencia.setDate(1, modificadoSql);

                if (activo)
                    sentencia.setBoolean(2, false);
                else
                    sentencia.setBoolean(2, true);

                sentencia.setString(3, cedula_cliente);

                int filasAfectadas = sentencia.executeUpdate();

                if (filasAfectadas == 0) {
                    System.out.println("No se modificó nada !");
                } else {
                    System.out.println("Se cambio el estado del usuario en la base de datos!");
                }

            } catch (SQLException e) {
                System.err.println(e);
            }
        } else {
            System.out.println("El cliente con esa cédula NO existe, por favor dijiste una cédula correcto!");
        }
    }

    /* Metodos para DEV y NO produccion: Los metodos sgtes son para el ambiente de desarrollo pero no se deberían de usar en producción*/
    public static void eliminarTodosClientes() {
        try {
            PreparedStatement sentencia = connection.prepareStatement(
                    "TRUNCATE cedula_cliente"
            );
            PreparedStatement sentencia_alter_sequence = connection.prepareStatement(
                    "ALTER SEQUENCE cliente_cedula_cliente_seq RESTART;"
            );

            sentencia.executeUpdate();
            sentencia_alter_sequence.executeUpdate();
            System.out.println("Borro TODOS LOS USUARIOS exitosamente!!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void eliminarUsuarioPorCedula(String cedula_cliente) {
        if (existeCliente_Cedula(cedula_cliente)) {
            try {
                PreparedStatement sentencia = connection.prepareStatement(
                        "DELETE FROM cliente WHERE cedula_cliente=?"
                );
                sentencia.setString(1, cedula_cliente);
                sentencia.executeUpdate();
                System.out.println("Borro el usuario con la cedula" + cedula_cliente + " exitosamente!");

            } catch (SQLException e) {
                System.err.println(e);
            }
        } else {
            System.out.println("El usuario con ese id NO existe, por favor dijiste un id correcto!");
        }
    }
}

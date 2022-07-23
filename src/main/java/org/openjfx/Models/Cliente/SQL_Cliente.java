package org.openjfx.Models.Cliente;

import org.openjfx.Models.Conexion;
import org.openjfx.Models.Cliente.Utils.*;
import org.openjfx.Models.Usuario.Utils.Rol;

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

    // Verifica si un cliente existe o no en la base de datos, basado en su cédula
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

    // Verifica si un cliente puede modificar
    public static boolean puedoModificarCliente(String cedula_cliente)  {
        try {
            PreparedStatement sentencia = connection.prepareStatement(
                    "SELECT * FROM cliente WHERE cedula_cliente= ? AND (user_type=?)"
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


    // Obtiene un cliente buscandolo por su cédula
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


    // Verifica si un cliente existe o no en la base de datos, basado en su nombre
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

    // Obtiene todos los registros de cliente que están en la base de datos

    public static ArrayList<Cliente> leerTodosLosClientes() {
        try {
            PreparedStatement sentencia = connection.prepareStatement(
                    "SELECT * FROM cliente ORDER BY cedula_cliente"
            );
            ResultSet resultadoCliente = sentencia.executeQuery();

            ArrayList<Cliente> clientesResultado = new ArrayList<Cliente>();
            int contador = 0;

            while (resultadoCliente.next()) {
                Cliente cliente = new Cliente();

                String cedula_cliente = resultadoCliente.getString("cedula_cliente");
                cliente.setCedula_cliente(cedula_cliente);
                String email = resultadoCliente.getString("email");
                cliente.setEmail(email);
                String nombre = resultadoCliente.getString("nombre");
                cliente.setNombre(nombre);
                String apellido = resultadoCliente.getString("apellido");
                cliente.setApellido(apellido);
                Date fecha_modificado = resultadoCliente.getDate("fecha_modificado");
                cliente.setFecha_modificado(fecha_modificado);
                Date fecha_creacion = resultadoCliente.getDate("fecha_creacion");
                cliente.setFecha_creacion(fecha_creacion);
                String direccion = resultadoCliente.getString("direccion");
                cliente.setDireccion(direccion);
                boolean activo = resultadoCliente.getBoolean("activo");
                cliente.setActivo(activo);
                Date fecha_nacimiento = resultadoCliente.getDate("fecha_nacimiento");
                cliente.setFecha_nacimiento(fecha_nacimiento);
                String telefono = resultadoCliente.getString("telefono");
                cliente.setTelefono(telefono);
                int id_tipo_usuario = resultadoCliente.getInt("id_tipo_usuario");
                cliente.setId_tipo_usuario(id_tipo_usuario);
                String cedula_creado_por = resultadoCliente.getString("cedula_creado_por");
                cliente.setCedula_creado_por(cedula_creado_por);
                String sede = resultadoCliente.getString("sede");
                cliente.setSede(sede);

                clientesResultado.add(cliente);

                contador++;
            }
            System.out.println("Operación Exitosa: Lectura de clientes de la Base de datos!!");
            return clientesResultado;

        } catch (SQLException e) {
            System.out.printf("Error al leer los clientes", e);
            throw new RuntimeException(e);
        }
    }

    // Crea un cliente con la base de datos
    public static void crearCliente(Cliente cliente) {
        try {
            PreparedStatement sentencia = connection.prepareStatement(
                    "INSERT INTO cliente " +
                            "(cedula_cliente,email,nombre, apellido, fecha_modificado, fecha_creacion, direccion, activo, fecha_nacimiento, telefono, id_tipo_usuario,user_type , cedula_creado_por,sede )" +
                            "VALUES  (?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            sentencia.setString(1, cliente.getCedula_cliente());
            sentencia.setString(2, cliente.getEmail());
            sentencia.setString(3, cliente.getNombre());
            sentencia.setString(4, cliente.getApellido());
            sentencia.setDate(5,  new java.sql.Date(cliente.getFecha_modificado().getTime()));
            sentencia.setDate(6,  new java.sql.Date(cliente.getFecha_creacion().getTime()));
            sentencia.setString(7, cliente.getDireccion());
            sentencia.setBoolean(8, cliente.isActivo());
            sentencia.setDate(9, new java.sql.Date(cliente.getFecha_nacimiento().getTime()));
            sentencia.setString(10, cliente.getTelefono());
            sentencia.setInt(11, cliente.getId_tipo_usuario());
            sentencia.setString(12, Rol.CLIENTE.toString());
            sentencia.setString(13, cliente.getCedula_creado_por());
            sentencia.setString(14, cliente.getSede());

            sentencia.execute();

            System.out.println("Operación Exitosa: Creación de Usuario");


        } catch (SQLException e) {
            System.out.printf("Error al crear el Usuario", e);
            throw new RuntimeException(e);
        }
    }


    // Edita un cliente en la base de datos
    public static void editarClientes(String cedula_cliente, Cliente clienteActualizado) {

        if ( existeCliente_Cedula(cedula_cliente)) {
            java.util.Date fecha_modificado = new java.util.Date();
            java.sql.Date modificadoSql = new java.sql.Date(fecha_modificado.getTime());
            try {
                PreparedStatement sentencia = connection.prepareStatement(
                        "UPDATE cliente SET " +
                                "email = ? , " +
                                "nombre = ? , " +
                                "apellido = ? , " +
                                "fecha_modificado = ? , " +
                                "fecha_creacion = ? , " +
                                "direccion = ? , " +
                                "fecha_nacimiento= ?, " +
                                "telefono = ?, " +
                                "id_tipo_usuario= ?, " +
                                "user_type= ?, " +
                                "cedula_creado_por= ? " +
                                "sede= ?, " +
                                "WHERE cedula_cliente = ?");

                sentencia.setString(1, cedula_cliente);
                sentencia.setString(2, clienteActualizado.getEmail());
                sentencia.setString(3, clienteActualizado.getNombre());
                sentencia.setString(4, clienteActualizado.getApellido());
                sentencia.setDate(5,  new java.sql.Date(clienteActualizado.getFecha_modificado().getTime()));
                sentencia.setDate(6,  new java.sql.Date(clienteActualizado.getFecha_creacion().getTime()));
                sentencia.setString(7, clienteActualizado.getDireccion());
                sentencia.setBoolean(8, clienteActualizado.isActivo());
                sentencia.setDate(9, new java.sql.Date(clienteActualizado.getFecha_nacimiento().getTime()));
                sentencia.setString(10, clienteActualizado.getTelefono());
                sentencia.setInt(11, clienteActualizado.getId_tipo_usuario());
                sentencia.setString(12, Rol.CLIENTE.toString());
                sentencia.setString(13, clienteActualizado.getCedula_creado_por());
                sentencia.setString(14, clienteActualizado.getSede());

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
            System.out.println("El cliente con ese id NO existe, por favor dijiste un id correcto!");
        }
    }

    // Elimina al cliente poniendolo inactivo en la base de datos - SOFT DELETE
    public static void eliminarCliente(String cedula_cliente) {
        if(existeCliente_Cedula(cedula_cliente)) {
            java.util.Date fecha_modificado = new java.util.Date();
            java.sql.Date modificadoSql = new java.sql.Date(fecha_modificado.getTime());
            try {
                PreparedStatement sentencia = connection.prepareStatement(
                        "UPDATE cliente SET " +
                                "fecha_modificado = ? , " +
                                "activo= ?  " +
                                "WHERE cedula_cliente = ?");
                sentencia.setDate(1, modificadoSql);
                sentencia.setBoolean(2, false);
                sentencia.setString(3, cedula_cliente);

                int filasAfectadas = sentencia.executeUpdate();

                if (filasAfectadas == 0) {
                    System.out.println("No se modificó nada !");
                } else {
                    System.out.println("Se cambio el estado a INACTIVO del cliente en la base de datos");
                }

            } catch (SQLException e) {
                System.err.println(e);
            }
        } else {
            System.out.println("El cliente con ese id NO existe, por favor dijiste un id correcto!");
        }
    }

    // Elimina al cliente poniendolo inactivo en la base de datos
    public static void cambiarEstadoClientePorCedula(String cedula_cliente, boolean activo) {
        if(existeCliente_Cedula(cedula_cliente)) {
            java.util.Date fecha_modificado = new java.util.Date();
            java.sql.Date modificadoSql = new java.sql.Date(fecha_modificado.getTime());
            try {
                PreparedStatement sentencia = connection.prepareStatement(
                        "UPDATE cliente SET " +
                                "fecha_modificado = ? , " +
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
                    System.out.println("Se cambio el estado del cliente en la base de datos!");
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
            System.out.println("Borro TODOS LOS CLIENTES exitosamente!!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void eliminarclientePorCedula(String cedula_cliente) {
        if (existeCliente_Cedula(cedula_cliente)) {
            try {
                PreparedStatement sentencia = connection.prepareStatement(
                        "DELETE FROM cliente WHERE cedula_cliente=?"
                );
                sentencia.setString(1, cedula_cliente);
                sentencia.executeUpdate();
                System.out.println("Borro el cliente con la cedula" + cedula_cliente + " exitosamente!");

            } catch (SQLException e) {
                System.err.println(e);
            }
        } else {
            System.out.println("El cliente con ese id NO existe, por favor dijiste un id correcto!");
        }
    }
}

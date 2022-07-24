package org.openjfx.Models.Orden_Trabajo;

import org.openjfx.Models.Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQL_Orden {
    private static Conexion conexion = new Conexion();
    private static Connection connection = conexion.getConnection();

    // Verifica si una Orden existe o no en la base de datos, basado en su ID
    public static boolean existeOrden_Id(int id_orden)  {
        try {
            PreparedStatement sentencia = connection.prepareStatement(
                    "SELECT * FROM orden_de_trabajo WHERE id_orden="+ id_orden
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

    // Verifica si una Orden existe o no en la base de datos, basado en su cédula de cliente y placa
    public static boolean existeOrden_CedulaPlaca(String cedula, String placa)  {
        try {
            PreparedStatement sentencia = connection.prepareStatement(
                    "SELECT * FROM orden_de_trabajo WHERE cedula_cliente= ? AND placa_automovil=?"
            );

            sentencia.setString(1, cedula);
            sentencia.setString(2, placa);
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

    // Obtiene una Orden por su cédula de cliente y su placa de automovil
    public static ResultSet obtenerOrden_CedulaPlaca(String cedula_cliente, String placa_automovil)  {
        try {
            PreparedStatement sentencia = connection.prepareStatement(
                    "SELECT * FROM orden_de_trabajo WHERE cedula_cliente= ? AND placa_automovil=?"
            );

            sentencia.setString(1, cedula_cliente);
            sentencia.setString(2, placa_automovil);
            ResultSet resultado = sentencia.executeQuery();
            return resultado;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Obtiene una Orden por su id
    public static ResultSet obtenerOrden_Id(int id_orden)  {
        try {
            PreparedStatement sentencia = connection.prepareStatement(
                    "SELECT * FROM orden_de_trabajo WHERE id_Orden= ?"
            );

            sentencia.setInt(1, id_orden);
            ResultSet resultado = sentencia.executeQuery();
            return resultado;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Obtiene un Result set con todas las ordenes
    public static ResultSet obtenerTodasOrdenesSet() {
        try {
            PreparedStatement sentencia = connection.prepareStatement(
                    "SELECT * FROM orden_de_trabajo ORDER BY id_orden"
            );

            ResultSet resultado = sentencia.executeQuery();
            return resultado;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Crea un Orden en la base de datos
    public static void crearOrden(Orden orden) {
        try {
            PreparedStatement sentencia = connection.prepareStatement(
                    "INSERT INTO orden_de_trabajo " +
                            "(fecha_creacion, fecha_modificado, activo, cedula_cliente, cedula_jefe_de_taller, placa_automovil, id_estado_orden, estado)" +
                            "VALUES  (?,?,?,?,?,?,?,?)");

            sentencia.setDate(1,  new java.sql.Date(orden.getFecha_creacion().getTime()));
            sentencia.setDate(2,  new java.sql.Date(orden.getFecha_modificado().getTime()));
            sentencia.setBoolean(3, orden.isActivo());
            sentencia.setString(4, orden.getCedula_cliente());
            sentencia.setString(5, orden.getCedula_jefe_de_taller());
            sentencia.setString(6, orden.getPlaca_automovil());
            sentencia.setInt(7, orden.getId_estado_orden());
            sentencia.setString(8, orden.getEstado().name());

            sentencia.execute();

            System.out.println("Operación Exitosa: Creación de Orden");

        } catch (SQLException e) {
            System.out.printf("Error al crear la Orden", e);
            throw new RuntimeException(e);
        }
    }


    // Edita un Orden en la base de datos, por nombre y marca
    public static void editarOrden(String cedula_cliente, String placa_automovil,  Orden ordenActualizada) {

        if ( existeOrden_CedulaPlaca(cedula_cliente, placa_automovil)) {
            java.util.Date modificado = new java.util.Date();
            java.sql.Date modificadoSql = new java.sql.Date(modificado.getTime());
            try {
                PreparedStatement sentencia = connection.prepareStatement(
                        "UPDATE orden_de_trabajo SET " +
                                "id_estado_orden = ? , " +
                                "estado = ? , " +
                                "fecha_modificado= ?  " +
                                "WHERE cedula_cliente = ? AND placa_automovil=?");

                sentencia.setInt(1, ordenActualizada.getId_estado_orden());
                sentencia.setString(2, ordenActualizada.getEstado().toString());
                sentencia.setDate(3, modificadoSql);

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
            System.out.println("La Orden con esa cédula y con esa placa NO existe , por favor dijite datos correctos!");
        }
    }

    // Elimina la Orden poniendola inactiva en la base de datos - SOFT DELETE, basado en Id
    public static void eliminarOrden_Id(int id_orden) {
        if(existeOrden_Id(id_orden)) {
            java.util.Date modificado = new java.util.Date();
            java.sql.Date modificadoSql = new java.sql.Date(modificado.getTime());
            try {
                PreparedStatement sentencia = connection.prepareStatement(
                        "UPDATE orden_de_trabajo SET " +
                                "fecha_modificado = ? , " +
                                "activo= ?  " +
                                "WHERE id_orden = ?");
                sentencia.setDate(1, modificadoSql);
                sentencia.setBoolean(2, false);
                sentencia.setInt(3, id_orden);

                int filasAfectadas = sentencia.executeUpdate();

                if (filasAfectadas == 0) {
                    System.out.println("No se modificó nada !");
                } else {
                    System.out.println("Se cambio el estado a INACTIVO de la Orden en la base de datos");
                }

            } catch (SQLException e) {
                System.err.println(e);
            }
        } else {
            System.out.println("Una Orden con ese id NO existe, por favor dijite un id correcto!");
        }
    }

    // Elimina al Orden poniendolo inactivo en la base de datos, por cédula de cliente y placa
    public static void cambiarEstadoOrdenPorNombreMarca(String cedula_cliente, String placa_automovil, boolean activo) {
        if(existeOrden_CedulaPlaca(cedula_cliente, placa_automovil)) {
            java.util.Date modificado = new java.util.Date();
            java.sql.Date modificadoSql = new java.sql.Date(modificado.getTime());
            try {
                PreparedStatement sentencia = connection.prepareStatement(
                        "UPDATE orden_de_trabajo SET " +
                                "fecha_modificado = ? , " +
                                "activo= ?  " +
                                "WHERE cedula_cliente = ? AND placa_automovil=?");
                sentencia.setDate(1, modificadoSql);

                if (activo)
                    sentencia.setBoolean(2, false);
                else
                    sentencia.setBoolean(2, true);

                int filasAfectadas = sentencia.executeUpdate();

                if (filasAfectadas == 0) {
                    System.out.println("No se modificó nada !");
                } else {
                    System.out.println("Se cambio el estado de la orden en la base de datos!");
                }

            } catch (SQLException e) {
                System.err.println(e);
            }
        } else {
            System.out.println("Una Orden con ese nombre NO existe, por favor dijite un nombre de sede correcto!");
        }
    }
}

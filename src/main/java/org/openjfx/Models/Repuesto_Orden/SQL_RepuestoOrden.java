package org.openjfx.Models.Repuesto_Orden;

import org.openjfx.Models.Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class SQL_RepuestoOrden {
    private static Conexion conexion = new Conexion();
    private static Connection connection = conexion.getConnection();

    // Verifica si un repuesto por orden existe o no en la base de datos, basado en su ID
    public static boolean existeRepuestoOrden_Id(int id_repuesto, int id_orden)  {
        try {
            PreparedStatement sentencia = connection.prepareStatement(
                    "SELECT * FROM repuestos_por_ordenes WHERE id_repuesto=? AND id_orden= ?"
            );

            sentencia.setInt(1, id_repuesto);
            sentencia.setInt(2, id_orden);

            ResultSet resultado = sentencia.executeQuery();
            if (resultado.next()) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            System.out.printf("Error al comprobar existencia por Ids", e);
            throw new RuntimeException(e);
        }
    }

    // Obtiene un registro de repuesto por orden basado en sus ids
    public static ResultSet obtenerRepuestoOrden_Ids(int id_repuesto, int id_orden)  {
        try {
            PreparedStatement sentencia = connection.prepareStatement(
                    "SELECT * FROM repuestos_por_ordenes WHERE id_repuesto= ? AND id_orden=?"
            );

            sentencia.setInt(1, id_repuesto);
            sentencia.setInt(2, id_orden);
            ResultSet resultado = sentencia.executeQuery();
            return resultado;

        } catch (SQLException e) {
            System.out.printf("Error al obtener registro por Ids", e);
            throw new RuntimeException(e);
        }
    }

    // Obtiene un Result set con todos los repuestos por orden
    public static ResultSet obtenerTodosRepuestosOrdenesSet() {
        try {
            PreparedStatement sentencia = connection.prepareStatement(
                    "SELECT * FROM repuestos_por_ordenes ORDER BY id_repuesto"
            );

            ResultSet resultado = sentencia.executeQuery();
            return resultado;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Crea un registro de repuesto por orden en la base de datos
    public static void crearRepuestoOrden(RepuestoOrden repuestoOrden) {
        try {
            PreparedStatement sentencia = connection.prepareStatement(
                    "INSERT INTO repuestos_por_ordenes " +
                            "(id_orden, id_repuesto, cantidad, cedula_creado_por, fecha_creacion, fecha_modificado, activo)" +
                            "VALUES  (?,?,?,?,?,?,?)");
            sentencia.setInt(1, repuestoOrden.getId_orden());
            sentencia.setInt(2, repuestoOrden.getId_repuesto());
            sentencia.setInt(3, repuestoOrden.getCantidad());
            sentencia.setString(4, repuestoOrden.getCedula_creado_por());
            sentencia.setDate(5,  new java.sql.Date(repuestoOrden.getFecha_creacion().getTime()));
            sentencia.setDate(6,  new java.sql.Date(repuestoOrden.getFecha_modificado().getTime()));
            sentencia.setBoolean(7, repuestoOrden.isActivo());

            sentencia.execute();

            System.out.println("Operación Exitosa: Creación de Repuesto por orden");


        } catch (SQLException e) {
            System.out.printf("Error al crear el Repuesto por Orden", e);
            throw new RuntimeException(e);
        }
    }


    // Edita un Repuesto por orden en la base de datos, por nombre y marca
    public static void editarRepuestoOrden(int id_repuesto, int id_orden, RepuestoOrden repuestoOrdenActualizado) {

        if ( existeRepuestoOrden_Id(id_repuesto, id_orden)) {
            java.util.Date modificado = new java.util.Date();
            java.sql.Date modificadoSql = new java.sql.Date(modificado.getTime());
            try {
                PreparedStatement sentencia = connection.prepareStatement(
                        "UPDATE repuestos_por_ordenes SET " +
                                "cantidad = ? , " +
                                "fecha_modificado= ?  " +
                                "WHERE id_repuesto = ? AND id_orden=?");
                sentencia.setInt(1, repuestoOrdenActualizado.getCantidad());
                sentencia.setDate(2, modificadoSql);
                sentencia.setInt(3, id_repuesto);
                sentencia.setInt(4, id_orden);

                if (repuestoOrdenActualizado.getCantidad() == 0) {
                    cambiarEstadoRepuestoPorIds(id_repuesto, id_orden, false);
                } else {
                    cambiarEstadoRepuestoPorIds(id_repuesto, id_orden, true);
                }

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
            System.out.println("El repuesto con ese nombre y con esa marca NO existe , por favor dijite datos correctos!");
        }
    }

    // Elimina al repuesto poniendolo inactivo en la base de datos
    public static void cambiarEstadoRepuestoPorIds(int id_repuesto, int id_orden, boolean activo) {
        if(existeRepuestoOrden_Id(id_repuesto, id_orden)) {
            java.util.Date modificado = new java.util.Date();
            java.sql.Date modificadoSql = new java.sql.Date(modificado.getTime());
            try {
                PreparedStatement sentencia = connection.prepareStatement(
                        "UPDATE repuestos_por_ordenes SET " +
                                "fecha_modificado = ? , " +
                                "activo= ?  " +
                                "WHERE id_repuesto = ? AND id_orden=?");
                sentencia.setDate(1, modificadoSql);
                sentencia.setBoolean(2, activo);
                sentencia.setInt(3, id_repuesto);
                sentencia.setInt(4, id_orden);

                int filasAfectadas = sentencia.executeUpdate();

                if (filasAfectadas == 0) {
                    System.out.println("No se modificó nada !");
                } else {
                    System.out.println("Se cambio el estado del repuesto por orden en la base de datos!");
                }

            } catch (SQLException e) {
                System.err.println(e);
            }
        } else {
            System.out.println("Un repuesto por orden con esos Ids NO existe, por favor dijite un nombre de sede correcto!");
        }
    }
}


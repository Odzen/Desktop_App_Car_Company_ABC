package org.openjfx.Models.Repuesto;

import org.openjfx.Models.Conexion;
import org.openjfx.Models.Sede.Sede;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SQL_Repuesto {
    private static Conexion conexion = new Conexion();
    private static Connection connection = conexion.getConnection();

    // Verifica si un repuesto existe o no en la base de datos, basado en su ID
    public static boolean existeRepuesto_Id(int id_repuesto)  {
        try {
            PreparedStatement sentencia = connection.prepareStatement(
                    "SELECT * FROM repuesto WHERE id_repuesto="+ id_repuesto
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

    // Verifica si un repuesto existe o no en la base de datos, basado en su nombre y marca
    public static boolean existeRepuesto_NombreMarca(String nombre, String marca)  {
        try {
            PreparedStatement sentencia = connection.prepareStatement(
                    "SELECT * FROM repuesto WHERE nombre= ? AND marca=?"
            );

            sentencia.setString(1, nombre);
            sentencia.setString(2, marca);
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

    // Obtiene todos los registros de Repuesto que están en la base de datos
    public static ArrayList<Repuesto> leerTodosLosRepuestos() {
        try {
            PreparedStatement sentencia = connection.prepareStatement(
                    "SELECT * FROM repuesto"
            );
            ResultSet resultado = sentencia.executeQuery();

            ArrayList<Repuesto> repuestosResultado = new ArrayList<Repuesto>();
            int contador = 0;

            while (resultado.next()) {
                Repuesto repuesto = new Repuesto();

                int id_repuesto = resultado.getInt("id_repuesto");
                repuesto.setId_repuesto(id_repuesto);
                String marca = resultado.getString("marca");
                repuesto.setMarca(marca);
                String nombreRepuesto = resultado.getString("nombre");
                repuesto.setNombre(nombreRepuesto);
                int cantidad = resultado.getInt("cantidad");
                repuesto.setCantidad(cantidad);
                java.sql.Date modificado = resultado.getDate("fecha_modificado");
                repuesto.setFecha_modificado(modificado);
                String cedula_creado_por = resultado.getString("cedula_creado_por");
                repuesto.setCedula_creado_por(cedula_creado_por);
                boolean activo = resultado.getBoolean("activo");
                repuesto.setActivo(activo);
                java.sql.Date creado = resultado.getDate("fecha_creacion");
                repuesto.setFecha_creacion(creado);

                repuestosResultado.add(repuesto);

                contador++;
            }
            System.out.println("Operación Exitosa: Lectura de Repuestos de la Base de datos!!");
            return repuestosResultado;

        } catch (SQLException e) {
            System.out.printf("Error al leer los Repuestos", e);
            throw new RuntimeException(e);
        }
    }

    // Obtiene un repuesto por su nombre y marca
    public static ResultSet obtenerRepuesto_NombreMarca(String nombre, String marca)  {
        try {
            PreparedStatement sentencia = connection.prepareStatement(
                    "SELECT * FROM repuesto WHERE nombre= ? AND marca=?"
            );

            sentencia.setString(1, nombre);
            sentencia.setString(2, marca);
            ResultSet resultado = sentencia.executeQuery();
            return resultado;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Obtiene un repuesto por su id
    public static ResultSet obtenerSede_Id(int id_repuesto)  {
        try {
            PreparedStatement sentencia = connection.prepareStatement(
                    "SELECT * FROM repuesto WHERE id_repuesto= ?"
            );

            sentencia.setInt(1, id_repuesto);
            ResultSet resultado = sentencia.executeQuery();
            return resultado;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Obtiene un Result set con todos los repuestos
    public static ResultSet obtenerTodosRepuestosSet() {
        try {
            PreparedStatement sentencia = connection.prepareStatement(
                    "SELECT * FROM repuesto ORDER BY id_repuesto"
            );

            ResultSet resultado = sentencia.executeQuery();
            return resultado;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Crea un repuesto en la base de datos
    public static void crearRepuesto(Repuesto repuesto) {
        try {
            PreparedStatement sentencia = connection.prepareStatement(
                    "INSERT INTO repuesto " +
                            "(activo, marca, nombre, cantidad, cedula_creado_por, fecha_creacion, fecha_modificado)" +
                            "VALUES  (?,?,?,?,?,?,?)");
            sentencia.setBoolean(1, repuesto.isActivo());
            sentencia.setString(2, repuesto.getMarca());
            sentencia.setString(3, repuesto.getNombre());
            sentencia.setInt(4, repuesto.getCantidad());
            sentencia.setString(5, repuesto.getCedula_creado_por());
            sentencia.setDate(6,  new java.sql.Date(repuesto.getFecha_creacion().getTime()));
            sentencia.setDate(7,  new java.sql.Date(repuesto.getFecha_modificado().getTime()));

            sentencia.execute();

            System.out.println("Operación Exitosa: Creación de Repuesto");


        } catch (SQLException e) {
            System.out.printf("Error al crear el Repuesto", e);
            throw new RuntimeException(e);
        }
    }


    // Edita un Repuesto en la base de datos, por nombre y marca
    public static void editarRepuesto(String nombre,String marca,  Repuesto repuestoActualizado) {

        if ( existeRepuesto_NombreMarca(nombre, marca)) {
            java.util.Date modificado = new java.util.Date();
            java.sql.Date modificadoSql = new java.sql.Date(modificado.getTime());
            try {
                PreparedStatement sentencia = connection.prepareStatement(
                        "UPDATE repuesto SET " +
                                "cantidad = ? , " +
                                "fecha_modificado= ?  " +
                                "WHERE nombre = ? AND marca=?");
                sentencia.setInt(1, repuestoActualizado.getCantidad());
                sentencia.setDate(2, modificadoSql);
                sentencia.setString(3, nombre);
                sentencia.setString(4, marca);

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

    // Elimina la sede poniendola inactiva en la base de datos - SOFT DELETE
    public static void eliminarRepuesto(int id_repuesto) {
        if(existeRepuesto_Id(id_repuesto)) {
            java.util.Date modificado = new java.util.Date();
            java.sql.Date modificadoSql = new java.sql.Date(modificado.getTime());
            try {
                PreparedStatement sentencia = connection.prepareStatement(
                        "UPDATE repuesto SET " +
                                "fecha_modificado = ? , " +
                                "activo= ?  " +
                                "WHERE id_repuesto = ?");
                sentencia.setDate(1, modificadoSql);
                sentencia.setBoolean(2, false);
                sentencia.setInt(3, id_repuesto);

                int filasAfectadas = sentencia.executeUpdate();

                if (filasAfectadas == 0) {
                    System.out.println("No se modificó nada !");
                } else {
                    System.out.println("Se cambio el estado a INACTIVO de la sede en la base de datos");
                }

            } catch (SQLException e) {
                System.err.println(e);
            }
        } else {
            System.out.println("La sede con ese id NO existe, por favor dijite un id correcto!");
        }
    }

    // Elimina al repuesto poniendolo inactivo en la base de datos
    public static void cambiarEstadoRepuestoPorNombreMarca(String nombre, String marca, boolean activo) {
        if(existeRepuesto_NombreMarca(nombre, marca)) {
            java.util.Date modificado = new java.util.Date();
            java.sql.Date modificadoSql = new java.sql.Date(modificado.getTime());
            try {
                PreparedStatement sentencia = connection.prepareStatement(
                        "UPDATE repuesto SET " +
                                "fecha_modificado = ? , " +
                                "activo= ?  " +
                                "WHERE nombre = ? AND marca=?");
                sentencia.setDate(1, modificadoSql);

                if (activo)
                    sentencia.setBoolean(2, false);
                else
                    sentencia.setBoolean(2, true);

                sentencia.setString(3, nombre);
                sentencia.setString(4, marca);

                int filasAfectadas = sentencia.executeUpdate();

                if (filasAfectadas == 0) {
                    System.out.println("No se modificó nada !");
                } else {
                    System.out.println("Se cambio el estado de la sede en la base de datos!");
                }

            } catch (SQLException e) {
                System.err.println(e);
            }
        } else {
            System.out.println("La sede con ese nombre NO existe, por favor dijite un nombre de sede correcto!");
        }
    }
}

package org.openjfx.Models.Sede;

import org.openjfx.Models.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SQL_Sede {
    private static Conexion conexion = new Conexion();
    private static Connection connection = conexion.getConnection();

    // Verifica si una sede existe o no en la base de datos, basado en su ID
    public static boolean existeSede_Id(int id_sede)  {
        try {
            PreparedStatement sentencia = connection.prepareStatement(
                    "SELECT * FROM sede WHERE id_sede="+ id_sede
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

    // Verifica si una sede existe o no en la base de datos, basado en su nombre
    public static boolean existeSede_Nombre(String nombre)  {
        try {
            PreparedStatement sentencia = connection.prepareStatement(
                    "SELECT * FROM sede WHERE nombre_sede= ?"
            );

            sentencia.setString(1, nombre);
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

    // Obtiene todos los registros de Sede que están en la base de datos
    public static ArrayList<Sede> leerTodasLasSedes() {
        try {
            PreparedStatement sentencia = connection.prepareStatement(
                    "SELECT * FROM sede ORDER BY id_sede"
            );
            ResultSet resultado = sentencia.executeQuery();

            ArrayList<Sede> sedesResultado = new ArrayList<Sede>();
            int contador = 0;

            while (resultado.next()) {
                Sede sede = new Sede();

                int id_sede = resultado.getInt("id_sede");
                sede.setId_sede(id_sede);
                String direccion = resultado.getString("direccion");
                sede.setDireccion(direccion);
                String nombre_sede = resultado.getString("nombre_sede");
                sede.setNombre_sede(nombre_sede);
                java.sql.Date modificado = resultado.getDate("fecha_modificado");
                sede.setFecha_modificado(modificado);
                String ciudad = resultado.getString("ciudad");
                sede.setCiudad(ciudad);
                boolean activo = resultado.getBoolean("activo");
                sede.setActivo(activo);
                java.sql.Date creado = resultado.getDate("fecha_creacion");
                sede.setFecha_creacion(creado);

                sedesResultado.add(sede);

                contador++;
            }
            System.out.println("Operación Exitosa: Lectura de Sedes de la Base de datos!!");
            return sedesResultado;

        } catch (SQLException e) {
            System.out.printf("Error al leer las Sedes", e);
            throw new RuntimeException(e);
        }
    }

    // Crea una sede en la base de datos
    public static void crearSede(Sede sede) {
        try {
            PreparedStatement sentencia = connection.prepareStatement(
                    "INSERT INTO sede " +
                            "(direccion, telefono, nombre_sede, activo, ciudad, fecha_creacion, fecha_modificado)" +
                            "VALUES  (?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            sentencia.setString(1, sede.getDireccion());
            sentencia.setString(2, sede.getTelefono());
            sentencia.setString(3, sede.getNombre_sede());
            sentencia.setBoolean(4, sede.isActivo());
            sentencia.setString(5, sede.getCiudad());
            sentencia.setDate(6,  new java.sql.Date(sede.getFecha_creacion().getTime()));
            sentencia.setDate(7,  new java.sql.Date(sede.getFecha_modificado().getTime()));

            sentencia.execute();

            System.out.println("Operación Exitosa: Creación de Sede");


        } catch (SQLException e) {
            System.out.printf("Error al crear la Sede", e);
            throw new RuntimeException(e);
        }
    }


    // Edita una Sede en la base de datos
    public static void editarSede(String nombre, Sede sedeActualizada) {

        if ( existeSede_Nombre(nombre)) {
            java.util.Date modificado = new java.util.Date();
            java.sql.Date modificadoSql = new java.sql.Date(modificado.getTime());
            try {
                PreparedStatement sentencia = connection.prepareStatement(
                        "UPDATE sede SET " +
                                "direccion = ? , " +
                                "telefono = ? , " +
                                "nombre_sede = ? , " +
                                "ciudad = ? , " +
                                "fecha_modificado= ?  " +
                                "WHERE nombre_sede = ?");
                sentencia.setString(1, sedeActualizada.getDireccion());
                sentencia.setString(2, sedeActualizada.getTelefono());
                sentencia.setString(3, nombre);
                sentencia.setString(4, sedeActualizada.getCiudad());
                sentencia.setDate(5, modificadoSql);

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
            System.out.println("La sede con ese nombre NO existe, por favor dijite un nombre de sede correcto!");
        }
    }

    // Elimina la sede poniendola inactiva en la base de datos - SOFT DELETE
    public static void eliminarSede(int id_sede) {
        if(existeSede_Id(id_sede)) {
            java.util.Date modificado = new java.util.Date();
            java.sql.Date modificadoSql = new java.sql.Date(modificado.getTime());
            try {
                PreparedStatement sentencia = connection.prepareStatement(
                        "UPDATE sede SET " +
                                "fecha_modificado = ? , " +
                                "activo= ?  " +
                                "WHERE id_sede = ?");
                sentencia.setDate(1, modificadoSql);
                sentencia.setBoolean(2, false);
                sentencia.setInt(3, id_sede);

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

    // Elimina al usuario poniendolo inactivo en la base de datos
    public static void cambiarEstadoUsuarioPorNombre(String nombre, boolean activo) {
        if(existeSede_Nombre(nombre)) {
            java.util.Date modificado = new java.util.Date();
            java.sql.Date modificadoSql = new java.sql.Date(modificado.getTime());
            try {
                PreparedStatement sentencia = connection.prepareStatement(
                        "UPDATE sede SET " +
                                "fecha_modificado = ? , " +
                                "activo= ?  " +
                                "WHERE nombre_sede = ?");
                sentencia.setDate(1, modificadoSql);

                if (activo)
                    sentencia.setBoolean(2, false);
                else
                    sentencia.setBoolean(2, true);

                sentencia.setString(3, nombre);

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

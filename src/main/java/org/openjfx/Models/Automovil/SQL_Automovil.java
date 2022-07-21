package org.openjfx.Models.Automovil;

import org.openjfx.Models.Conexion;
import org.openjfx.Models.Usuario.Utils.Rol;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SQL_Automovil {
    private static Conexion conexion = new Conexion();
    private static Connection connection = conexion.getConnection();

    // Verifica si una automovil existe o no en la base de datos, basado en su placa
    public static boolean existeautomovil_placa(String placa)  {
        try {
            PreparedStatement sentencia = connection.prepareStatement(
                    "SELECT * FROM automovil WHERE placa= ?"
            );

            sentencia.setString(1, placa);
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
    public static ArrayList<Automovil> leerTodoslosAutomoviles() {
        try {
            PreparedStatement sentencia = connection.prepareStatement(
                    "SELECT * FROM Automovil"
            );
            ResultSet resultado = sentencia.executeQuery();

            ArrayList<Automovil> automovilResultado = new ArrayList<Automovil>();
            int contador = 0;

            while (resultado.next()) {
                Automovil Automovil = new Automovil();

                String placa = resultado.getString("placa");
                Automovil.setPlaca(placa);
                String marca = resultado.getString("marca");
                Automovil.setMarca(marca);
                String cilindraje = resultado.getString("cilindraje");
                Automovil.setCilindraje(cilindraje);
                java.sql.Date modificado = resultado.getDate("fecha_modificado");
                Automovil.setFecha_modificado(modificado);
                String color = resultado.getString("color");
                Automovil.setColor(color);
                String modelo = resultado.getString("modelo");
                Automovil.setModelo(modelo);
                String año = resultado.getString("año");
                Automovil.setAño(año);
                Integer precio = resultado.getInt("precio");
                Automovil.setPrecio(precio);
                boolean activo = resultado.getBoolean("activo");
                Automovil.setActivo(activo);
                java.sql.Date creado = resultado.getDate("fecha_creacion");
                Automovil.setFecha_creacion(creado);
                String sede = resultado.getString("sede");
                Automovil.setSede(sede);


                automovilResultado.add(Automovil);

                contador++;
            }
            System.out.println("Operación Exitosa: Lectura de automóvil de la Base de datos!!");
            return automovilResultado;

        } catch (SQLException e) {
            System.out.printf("Error al leer los automóviles", e);
            throw new RuntimeException(e);
        }
    }

    // Verifica si un automovil existe o no en la base de datos, basado en su nombre
    public static ResultSet obtenerAutomovil_placa(String placa)  {
        try {
            PreparedStatement sentencia = connection.prepareStatement(
                    "SELECT * FROM automovil WHERE placa= ?"
            );

            sentencia.setString(1, placa);
            ResultSet resultado = sentencia.executeQuery();
            return resultado;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static ResultSet obtenerTodosAutomovilSet() {
        try {
            PreparedStatement sentencia = connection.prepareStatement(
                    "SELECT * FROM automovil ORDER BY placa"
            );

            ResultSet resultado = sentencia.executeQuery();
            return resultado;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Crea una sede en la base de datos
    public static void crearAutomovil(Automovil automovil) {
        try {
            PreparedStatement sentencia = connection.prepareStatement(
                    "INSERT INTO automovil " +
                            "(placa, marca, cilindraje, color, modelo, año, precio,activo, cedula_creado_por,fecha_creacion,sede, fecha_modificado)" +
                            "VALUES  (?,?,?,?,?,?,?,?,?,?,?,?)");
            sentencia.setString(1, automovil.getPlaca());
            sentencia.setString(2, automovil.getMarca());
            sentencia.setString(3, automovil.getCilindraje());
            sentencia.setString(4, automovil.getColor());
            sentencia.setString(5, automovil.getModelo());
            sentencia.setString(6, automovil.getAño());
            sentencia.setInt(7, automovil.getPrecio());
            sentencia.setBoolean(8, automovil.isActivo());
            sentencia.setInt(9, automovil.getId_creado_por());
            sentencia.setDate(10,  new java.sql.Date(automovil.getFecha_creacion().getTime()));
            sentencia.setString(11, automovil.getSede());
            sentencia.setDate(12,  new java.sql.Date(automovil.getFecha_modificado().getTime()));

            sentencia.execute();

            System.out.println("Operación Exitosa: Creación de automovil");


        } catch (SQLException e) {
            System.out.printf("Error al crear la automovil", e);
            throw new RuntimeException(e);
        }
    }


    // Edita una Automovil en la base de datos
    public static void editarAutomovil(String placa, Automovil automovilActualizado) {

        if ( existeautomovil_placa(placa)) {
            java.util.Date modificado = new java.util.Date();
            java.sql.Date modificadoSql = new java.sql.Date(modificado.getTime());
            try {
                PreparedStatement sentencia = connection.prepareStatement(
                        "UPDATE automovil SET " +
                                "placa = ? , " +
                                "marca = ? , " +
                                "cilindraje = ? , " +
                                "color = ? , " +
                                "modelo = ? , " +
                                "año = ? , " +
                                "precio = ? , " +
                                "creado_por = ?," +
                                "sede = ?,"+
                               "fecha_modificado= ?  " +
                                "WHERE placa = ?");
                sentencia.setString(1, automovilActualizado.getPlaca());
                sentencia.setString(2, automovilActualizado.getMarca());
                sentencia.setString(3, automovilActualizado.getCilindraje());
                sentencia.setString(4, automovilActualizado.getColor());
                sentencia.setString(5, automovilActualizado.getModelo());
                sentencia.setString(6, automovilActualizado.getAño());
                sentencia.setInt(7, automovilActualizado.getPrecio());
                sentencia.setString(8, placa);
                sentencia.setDate(9, modificadoSql);
                sentencia.setString(10, placa);
                sentencia.setInt(11, automovilActualizado.getId_creado_por());
                sentencia.setString(11, automovilActualizado.getSede());


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
            System.out.println("El automovil  con esa placa NO existe, por favor dijite la placa correcta!");
        }
    }

    // Elimina automovil poniendola inactiva en la base de datos - SOFT DELETE
    public static void eliminarAutomovil(String placa) {
        if(existeautomovil_placa(placa)) {
            java.util.Date modificado = new java.util.Date();
            java.sql.Date modificadoSql = new java.sql.Date(modificado.getTime());
            try {
                PreparedStatement sentencia = connection.prepareStatement(
                        "UPDATE automovil SET " +
                                "fecha_modificado = ? , " +
                                "activo= ?  " +
                                "WHERE id_automovil = ?");
                sentencia.setDate(1, modificadoSql);
                sentencia.setBoolean(2, false);
                sentencia.setString(3, placa);

                int filasAfectadas = sentencia.executeUpdate();

                if (filasAfectadas == 0) {
                    System.out.println("No se modificó nada !");
                } else {
                    System.out.println("Se cambio el estado a INACTIVO de automovil en la base de datos");
                }

            } catch (SQLException e) {
                System.err.println(e);
            }
        } else {
            System.out.println("El automovil con ese id NO existe, por favor digite un id correcto!");
        }
    }

    // Elimina al usuario poniendolo inactivo en la base de datos
    public static void cambiarEstadoUsuarioPorNombre(String placa, boolean activo) {
        if(existeautomovil_placa(placa)) {
            java.util.Date modificado = new java.util.Date();
            java.sql.Date modificadoSql = new java.sql.Date(modificado.getTime());
            try {
                PreparedStatement sentencia = connection.prepareStatement(
                        "UPDATE automovil SET " +
                                "fecha_modificado = ? , " +
                                "activo= ?  " +
                                "WHERE placa = ?");
                sentencia.setDate(1, modificadoSql);

                if (activo)
                    sentencia.setBoolean(2, false);
                else
                    sentencia.setBoolean(2, true);

                sentencia.setString(3, placa);

                int filasAfectadas = sentencia.executeUpdate();

                if (filasAfectadas == 0) {
                    System.out.println("No se modificó nada !");
                } else {
                    System.out.println("Se cambio el estado del automovil en la base de datos!");
                }

            } catch (SQLException e) {
                System.err.println(e);
            }
        } else {
            System.out.println("El automovil con esa placa NO existe, por favor digite l correcto!");
        }
    }

}

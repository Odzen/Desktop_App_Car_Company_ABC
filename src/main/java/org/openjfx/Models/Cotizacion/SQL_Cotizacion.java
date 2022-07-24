package org.openjfx.Models.Cliente;

import org.openjfx.Models.Conexion;
import org.openjfx.Models.Cliente.Utils.*;
import org.openjfx.Models.Cotizacion.Cotizacion;
import org.openjfx.Models.Usuario.Usuario;
import org.openjfx.Models.Usuario.Utils.Rol;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class SQL_Cotizacion {

    private static Conexion conexion = new Conexion();
    private static Connection connection = conexion.getConnection();


    // Verifica si una cotizacion existe o no en la base de datos, basado en cedula_cliente
    public static boolean existeCotizacion_cedula(String cedula_cliente)  {
        try {
            PreparedStatement sentencia = connection.prepareStatement(
                    "SELECT * FROM cotizacion WHERE cedula_cliente= ?"
            );

            sentencia.setString(1, cedula_cliente);
            ResultSet resultadoCotizacion = sentencia.executeQuery();
            if (resultadoCotizacion.next()) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static ResultSet obtenerTodasCotizacionesSet() {
        try {
            PreparedStatement sentencia = connection.prepareStatement(
                    "SELECT * FROM cotizacion ORDER BY cedula_cliente "
            );

            ResultSet resultadoCotizacion = sentencia.executeQuery();
            return resultadoCotizacion;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Verifica si una cotizacion se puede modificar
    public static boolean puedoModificarCotizacion(String cedula_cliente)  {
        try {
            PreparedStatement sentencia = connection.prepareStatement(
                    "SELECT * FROM cotizacion WHERE cedula_cliente= ?"
            );

            sentencia.setString(1, cedula_cliente);
            ResultSet resultadoCotizacion = sentencia.executeQuery();
            if (resultadoCotizacion.next()) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    // Obtiene un usuario buscandolo por su cédula
    public static ResultSet obtenerCotizacion_Cedula(String cedula_cliente)  {
        try {
            PreparedStatement sentencia = connection.prepareStatement(
                    "SELECT * FROM cotizacion WHERE cedula_cliente= ?"
            );

            sentencia.setString(1, cedula_cliente);
            ResultSet resultadoCotizacion = sentencia.executeQuery();
            return resultadoCotizacion;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    // Verifica si un usuario existe o no en la base de datos, basado en su nombre
    public static boolean existeCotizacion_Nombre(String cedula_cliente)  {
        try {
            PreparedStatement sentencia = connection.prepareStatement(
                    "SELECT * FROM cotizacion WHERE cedula_cliente="+ cedula_cliente
            );
            ResultSet resultadoCotizacion = sentencia.executeQuery();
            if (resultadoCotizacion.next()) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Obtiene todos los registros de cotizacion que están en la base de datos

    public static ArrayList<Cotizacion> leerTodosLosCotizacion() {
        try {
            PreparedStatement sentencia = connection.prepareStatement(
                    "SELECT * FROM cotizacion ORDER BY cedula_cliente"
            );
            ResultSet resultadoCotizacion = sentencia.executeQuery();

            ArrayList<Cotizacion> cotizacionResultado = new ArrayList<Cotizacion>();
            int contador = 0;

            while (resultadoCotizacion.next()) {
                Cotizacion cotizacion = new Cotizacion();

                int id_cotizacion = resultadoCotizacion.getInt("id_cotizacion");
                cotizacion.setId_cotizacion(id_cotizacion);
                int IVA = resultadoCotizacion.getInt("IVA");
                cotizacion.setIVA(IVA);
                int TOTAL_IVA = resultadoCotizacion.getInt("TOTAL_IVA");
                cotizacion.setTOTAL_IVA(TOTAL_IVA);
                int TOTAL_SIN_IVA = resultadoCotizacion.getInt("TOTAL_SIN_IVA");
                cotizacion.setTOTAL_SIN_IVA(TOTAL_SIN_IVA);
                String descripcion = resultadoCotizacion.getString("descripcion");
                cotizacion.setDescripcion(descripcion);
                Date fecha_modificado = resultadoCotizacion.getDate("fecha_modificado");
                cotizacion.setFecha_modificado(fecha_modificado);
                Date fecha_creacion = resultadoCotizacion.getDate("fecha_creacion");
                cotizacion.setFecha_creacion(fecha_creacion);
                String cedula_cliente = resultadoCotizacion.getString("cedula_cliente");
                cotizacion.setCedula_cliente(cedula_cliente);
                String cedula_vendedor = resultadoCotizacion.getString("cedula_vendedor");
                cotizacion.setCedula_vendedor(cedula_vendedor);
                String placa_automovil = resultadoCotizacion.getString("placa_automovil");
                cotizacion.setPlaca_automovil(placa_automovil);
                int id_orden_trabajo = resultadoCotizacion.getInt("id_orden_trabajo");
                cotizacion.setid_orden_trabajo(id_orden_trabajo);

                cotizacionResultado.add(cotizacion);

                contador++;
            }
            System.out.println("Operación Exitosa: Lectura de clientes de la Base de datos!!");
            return cotizacionResultado;

        } catch (SQLException e) {
            System.out.printf("Error al leer los clientes", e);
            throw new RuntimeException(e);
        }
    }

    // Crea un usuario con la base de datos
    public static void crearCotizacion(Cotizacion cotizacion) {
        try {
            PreparedStatement sentencia = connection.prepareStatement(
                    "INSERT INTO cotizacion " +
                            "(IVA,TOTAL_IVA,TOTAL_SIN_IVA, descripcion, fecha_modificado, fecha_creacion, cedula_cliente, cedula_vendedor, placa_automovil, id_orden_trabajo )" +
                            "VALUES  (?,?,?,?,?,?,?,?,?,?)");
            sentencia.setInt(1, cotizacion.getIVA());
            sentencia.setInt(2, cotizacion.getTOTAL_IVA());
            sentencia.setInt(3, cotizacion.getTOTAL_SIN_IVA());
            sentencia.setString(4, cotizacion.getDescripcion());
            sentencia.setDate(5,  new java.sql.Date(cotizacion.getFecha_modificado().getTime()));
            sentencia.setDate(6,  new java.sql.Date(cotizacion.getFecha_creacion().getTime()));
            sentencia.setString(7, cotizacion.getCedula_cliente());
            sentencia.setString(8, cotizacion.getCedula_vendedor());
            sentencia.setString(9, cotizacion.getPlaca_automovil());
            sentencia.setInt(10, cotizacion.getid_orden_trabajo());

            sentencia.execute();

            System.out.println("Operación Exitosa: Creación de cotización");


        } catch (SQLException e) {
            System.out.printf("Error al crear la cotización", e);
            throw new RuntimeException(e);
        }
    }


    // Edita una cotizacion en la base de datos
    public static void editarCotizacion(String cedula_cliente, Cotizacion cotizacionActualizado) {

        if ( existeCotizacion_cedula(cedula_cliente)) {
            java.util.Date modificado = new java.util.Date();
            java.sql.Date modificadoSql = new java.sql.Date(modificado.getTime());
            try {
                PreparedStatement sentencia = connection.prepareStatement(
                        "UPDATE cotizacion SET " +
                                "IVA = ? , " +
                                "TOTAL_IVA= ?, " +
                                "TOTAL_SIN_IVA= ?, " +
                                "descripcion = ? , " +
                                "fecha_modificado = ? , " +
                                "fecha_creacion = ? , " +
                                "cedula_cliente = ? , " +
                                "cedula_vendedor = ? , " +
                                "placa_automovil = ?, " +
                                "id_orden_trabajo= ?, " +
                                "WHERE cedula_cliente = ?");

                sentencia.setInt(1, cotizacionActualizado.getIVA());
                sentencia.setInt(2, cotizacionActualizado.getTOTAL_IVA());
                sentencia.setInt(3, cotizacionActualizado.getTOTAL_SIN_IVA());
                sentencia.setString(4, cotizacionActualizado.getDescripcion());
                sentencia.setDate(5,  new java.sql.Date(cotizacionActualizado.getFecha_modificado().getTime()));
                sentencia.setDate(6,  new java.sql.Date(cotizacionActualizado.getFecha_creacion().getTime()));
                sentencia.setString(7, cedula_cliente);
                sentencia.setString(8, cotizacionActualizado.getCedula_vendedor());
                sentencia.setString(9, cotizacionActualizado.getPlaca_automovil());
                sentencia.setInt(10, cotizacionActualizado.getid_orden_trabajo());


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
            System.out.println("La cotización con ese cliente NO existe, por favor dijiste una cédula correcta!");
        }
    }
}

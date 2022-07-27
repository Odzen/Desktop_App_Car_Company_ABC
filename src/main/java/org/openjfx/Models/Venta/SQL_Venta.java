package org.openjfx.Models.Venta;

import org.openjfx.Models.Conexion;
import org.openjfx.Models.Cotizacion.Cotizacion;

import java.sql.*;


public class SQL_Venta {

    private static Conexion conexion = new Conexion();
    private static Connection connection = conexion.getConnection();


    public static boolean existeVenta_cedula_cliente(int cedula_cliente) {
        try {
            PreparedStatement sentencia = connection.prepareStatement(
                    "SELECT * FROM venta WHERE cedula_cliente= ?"
            );

            sentencia.setInt(1, cedula_cliente);
            ResultSet resultadoCotizacion = sentencia.executeQuery();
            return resultadoCotizacion.next();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean existeVenta_cedula_Placa(String cedula_cliente, String placa_automovil)  {
        try {
            PreparedStatement sentencia = connection.prepareStatement(
                    "SELECT * FROM venta WHERE cedula_cliente= ? AND placa_automovil=?"
            );

            sentencia.setString(1, cedula_cliente);
            sentencia.setString(2, placa_automovil);

            ResultSet resultadoCotizacion = sentencia.executeQuery();
            return resultadoCotizacion.next();

        } catch (SQLException e) {
            System.out.printf("Error al verificar la existencia por la cedula y placa", e);
            throw new RuntimeException(e);
        }
    }

    public static boolean existeVenta_cedula_Orden(String cedula_cliente, int id_orden)  {
        try {
            PreparedStatement sentencia = connection.prepareStatement(
                    "SELECT * FROM venta WHERE cedula_cliente= ? AND id_orden_trabajo=?"
            );

            sentencia.setString(1, cedula_cliente);
            sentencia.setInt(2, id_orden);

            ResultSet resultadoCotizacion = sentencia.executeQuery();
            return resultadoCotizacion.next();

        } catch (SQLException e) {
            System.out.printf("Error al verificar la existencia por la cedula y el id de orden", e);
            throw new RuntimeException(e);
        }
    }

    // Verifica si una venta existe o no en la base de datos, basado en cedula_cliente
    public static boolean existeVenta_cedula_Placa_orden(String cedula_cliente, String placa_automovil, int id_orden_trabajo)  {
        try {
            PreparedStatement sentencia = connection.prepareStatement(
                    "SELECT * FROM venta WHERE cedula_cliente= ? AND (placa_automovil=? OR id_orden_trabajo=?)"
            );

            sentencia.setString(1, cedula_cliente);
            sentencia.setString(2,placa_automovil);
            sentencia.setInt(3, id_orden_trabajo);
            ResultSet resultadoVenta = sentencia.executeQuery();
            return resultadoVenta.next();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static ResultSet obtenerTodasVentasSet() {
        try {
            PreparedStatement sentencia = connection.prepareStatement(
                    "SELECT * FROM venta ORDER BY id_venta "
            );

            ResultSet resultadoVenta = sentencia.executeQuery();
            return resultadoVenta;

        } catch (SQLException e) {
            System.out.printf("Error al obtener todas las ventas", e);
            throw new RuntimeException(e);        }
    }


    // Obtiene una venta buscandola por su cédula
    public static ResultSet obtenerVenta_Cedula_Placa_Orden(String cedula_cliente, String placa_automovil, int id_orden_trabajo)  {
        try {
            PreparedStatement sentencia = connection.prepareStatement(
                    "SELECT * FROM venta WHERE cedula_cliente= ? AND (placa_automovil=? OR id_orden_trabajo=? )"
            );

            sentencia.setString(1, cedula_cliente);
            sentencia.setString(2, placa_automovil);
            sentencia.setInt(3, id_orden_trabajo);
            ResultSet resultadoVenta = sentencia.executeQuery();
            return resultadoVenta;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Crea una venta con la base de datos
    public static void crearVenta(Venta venta) {
        try {
            PreparedStatement sentencia = connection.prepareStatement(
                    "INSERT INTO venta " +
                            "(iva,total_iva,total_sin_iva, descripcion, fecha_modificado, fecha_creacion, cedula_cliente, cedula_vendedor, placa_automovil, id_orden_trabajo, sede )" +
                            "VALUES  (?,?,?,?,?,?,?,?,?,?, ?)");
            sentencia.setDouble(1, venta.getIva());
            sentencia.setDouble(2, venta.getTotal_iva());
            sentencia.setDouble(3, venta.getTotal_sin_iva());
            sentencia.setString(4, venta.getDescripcion());
            sentencia.setDate(5,  new java.sql.Date(venta.getFecha_modificado().getTime()));
            sentencia.setDate(6,  new java.sql.Date(venta.getFecha_creacion().getTime()));
            sentencia.setString(7, venta.getCedula_cliente());
            sentencia.setString(8, venta.getCedula_vendedor());
            sentencia.setString(9, venta.getPlaca_automovil());
            sentencia.setInt(10, venta.getId_orden_trabajo());
            sentencia.setString(11, venta.getSede());

            sentencia.execute();

            System.out.println("Operación Exitosa: Creación de venta");


        } catch (SQLException e) {
            System.out.printf("Error al crear la venta", e);
            throw new RuntimeException(e);
        }
    }

    public static void editarVenta_cedula_placa_orden(String cedula_cliente, String placa_automovil, int id_orden, Venta ventaActualizada) {

        if ( existeVenta_cedula_Placa(cedula_cliente, placa_automovil) || existeVenta_cedula_Orden(cedula_cliente,id_orden)) {
            java.util.Date modificado = new java.util.Date();
            java.sql.Date modificadoSql = new java.sql.Date(modificado.getTime());
            try {
                PreparedStatement sentencia = connection.prepareStatement(
                        "UPDATE venta SET " +
                                "cedula_cliente = ?, " +
                                "placa_automovil = ?, " +
                                "id_orden_trabajo = ?, " +
                                "iva = ?, " +
                                "total_sin_iva = ?, " +
                                "total_iva = ?, " +
                                "fecha_modificado = ?, " +
                                "descripcion = ?, " +
                                "sede = ? " +
                                "WHERE cedula_cliente= ? AND (placa_automovil=? OR id_orden_trabajo=?)"

                );

                sentencia.setString(1, cedula_cliente);
                sentencia.setString(2, placa_automovil);
                sentencia.setInt(3, id_orden);
                sentencia.setDouble(4, ventaActualizada.getIva());
                sentencia.setDouble(5, ventaActualizada.getTotal_sin_iva());
                sentencia.setDouble(6, ventaActualizada.getTotal_iva());
                sentencia.setDate(7, modificadoSql);
                sentencia.setString(8, ventaActualizada.getDescripcion());
                sentencia.setString(9, ventaActualizada.getSede());
                sentencia.setString(10, cedula_cliente);
                sentencia.setString(11, placa_automovil);
                sentencia.setInt(12, id_orden);


                int filasAfectadas = sentencia.executeUpdate();
                System.out.println(filasAfectadas);

                if (filasAfectadas == 0) {
                    System.out.println("No se modificó nada !");
                } else {
                    System.out.println("Se modificaron exitosamente: " + filasAfectadas + " registros");
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            System.out.println("No existe , por favor dijite datos correctos!");

        }
    }



}

package org.openjfx.Models.Venta;

import org.openjfx.Models.Conexion;

import java.sql.*;
import java.util.ArrayList;


public class SQL_Venta {

    private static Conexion conexion = new Conexion();
    private static Connection connection = conexion.getConnection();


    // Verifica si una venta existe o no en la base de datos, basado en id_venta
    public static boolean existeVenta(int id_venta)  {
        try {
            PreparedStatement sentencia = connection.prepareStatement(
                    "SELECT * FROM venta WHERE id_venta= ?"
            );

            sentencia.setInt(1, id_venta);
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


    public static boolean existeVenta_cedula_cliente(String cedula_cliente) {
        try {
            PreparedStatement sentencia = connection.prepareStatement(
                    "SELECT * FROM venta WHERE cedula_cliente= ?"
            );

            sentencia.setString(1,cedula_cliente );
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


    // Verifica si una venta existe o no en la base de datos, basado en cedula_cliente
    public static boolean existeVenta_cedula_Placa_orden(String cedula_cliente, String placa_automovil, int id_orden_trabajo)  {
        try {
            PreparedStatement sentencia = connection.prepareStatement(
                    "SELECT * FROM venta WHERE cedula_cliente= ? AND (placa_automovil=? OR id_orden_trabajo=?)"
            );

            sentencia.setString(1, cedula_cliente);
            sentencia.setString(2,placa_automovil);
            sentencia.setInt(3,id_orden_trabajo);
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
    public static ResultSet obtenerTodasVentaSet() {
        try {
            PreparedStatement sentencia = connection.prepareStatement(
                    "SELECT * FROM venta ORDER BY id_venta "
            );

            ResultSet resultadoVenta = sentencia.executeQuery();
            return resultadoVenta;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Verifica si una venta se puede modificar
    public static boolean puedoModificarVenta(String cedula_cliente, String placa_automovil, int id_orden_trabajo)  {
        try {
            PreparedStatement sentencia = connection.prepareStatement(
                    "SELECT * FROM venta WHERE cedula_cliente= ? AND (placa_automovil=? OR id_orden_trabajo=?)"
            );

            sentencia.setString(1, cedula_cliente);
            sentencia.setString(2, placa_automovil);
            sentencia.setInt(3, id_orden_trabajo);
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



    // Obtiene todos los registros de cotizacion que están en la base de datos

    public static ArrayList<Venta> leerTodosLasVenta() {
        try {
            PreparedStatement sentencia = connection.prepareStatement(
                    "SELECT * FROM venta ORDER BY id_venta"
            );
            ResultSet resultadoVenta = sentencia.executeQuery();

            ArrayList<Venta> ventaResultado = new ArrayList<Venta>();
            int contador = 0;

            while (resultadoVenta.next()) {
                Venta venta = new Venta();

                int id_cotizacion = resultadoVenta.getInt("id_cotizacion");
                venta.setId_venta(id_cotizacion);
                int IVA = resultadoVenta.getInt("IVA");
                venta.setIVA(IVA);
                int TOTAL_IVA = resultadoVenta.getInt("TOTAL_IVA");
                venta.setTOTAL_IVA(TOTAL_IVA);
                int TOTAL_SIN_IVA = resultadoVenta.getInt("TOTAL_SIN_IVA");
                venta.setTOTAL_SIN_IVA(TOTAL_SIN_IVA);
                String descripcion = resultadoVenta.getString("descripcion");
                venta.setDescripcion(descripcion);
                Date fecha_modificado = resultadoVenta.getDate("fecha_modificado");
                venta.setFecha_modificado(fecha_modificado);
                Date fecha_creacion = resultadoVenta.getDate("fecha_creacion");
                venta.setFecha_creacion(fecha_creacion);
                String cedula_cliente = resultadoVenta.getString("cedula_cliente");
                venta.setCedula_cliente(cedula_cliente);
                String cedula_vendedor = resultadoVenta.getString("cedula_vendedor");
                venta.setCedula_vendedor(cedula_vendedor);
                String placa_automovil = resultadoVenta.getString("placa_automovil");
                venta.setPlaca_automovil(placa_automovil);
                int id_orden_trabajo = resultadoVenta.getInt("id_orden_trabajo");
                venta.setid_orden_trabajo(id_orden_trabajo);
                String sede = resultadoVenta.getString("sede");
                venta.setSede(sede);

                ventaResultado.add(venta);

                contador++;
            }
            System.out.println("Operación Exitosa: Lectura de clientes de la Base de datos!!");
            return ventaResultado;

        } catch (SQLException e) {
            System.out.printf("Error al leer los clientes", e);
            throw new RuntimeException(e);
        }
    }

    // Crea una venta con la base de datos
    public static void crearVenta(Venta venta) {
        try {
            PreparedStatement sentencia = connection.prepareStatement(
                    "INSERT INTO venta " +
                            "(IVA,TOTAL_IVA,TOTAL_SIN_IVA, descripcion, fecha_modificado, fecha_creacion, cedula_cliente, cedula_vendedor, placa_automovil, id_orden_trabajo )" +
                            "VALUES  (?,?,?,?,?,?,?,?,?,?)");
            sentencia.setInt(1, venta.getIVA());
            sentencia.setInt(2, venta.getTOTAL_IVA());
            sentencia.setInt(3, venta.getTOTAL_SIN_IVA());
            sentencia.setString(4, venta.getDescripcion());
            sentencia.setDate(5,  new java.sql.Date(venta.getFecha_modificado().getTime()));
            sentencia.setDate(6,  new java.sql.Date(venta.getFecha_creacion().getTime()));
            sentencia.setString(7, venta.getCedula_cliente());
            sentencia.setString(8, venta.getCedula_vendedor());
            sentencia.setString(9, venta.getPlaca_automovil());
            sentencia.setInt(10, venta.getid_orden_trabajo());

            sentencia.execute();

            System.out.println("Operación Exitosa: Creación de cotización");


        } catch (SQLException e) {
            System.out.printf("Error al crear la cotización", e);
            throw new RuntimeException(e);
        }
    }


    // Edita una cotizacion en la base de datos
    public static void editarVenta(String cedula_cliente, String placa_automovil, int id_orden_trabajo , Venta ventaActualizado) {

        if ( existeVenta_cedula_cliente(cedula_cliente)) {
            java.util.Date modificado = new java.util.Date();
            java.sql.Date modificadoSql = new java.sql.Date(modificado.getTime());
            try {
                PreparedStatement sentencia = connection.prepareStatement(
                        "UPDATE venta SET " +
                                "descripcion = ? , " +
                                "cedula_vendedor = ? , " +
                                "WHERE cedula_cliente = ? AND (placa_automovil=? OR id_orden_trabajo=? )");

                sentencia.setInt(1, ventaActualizado.getIVA());
                sentencia.setInt(2, ventaActualizado.getTOTAL_IVA());
                sentencia.setInt(3, ventaActualizado.getTOTAL_SIN_IVA());
                sentencia.setString(4, ventaActualizado.getDescripcion());
                sentencia.setDate(5,  new java.sql.Date(ventaActualizado.getFecha_modificado().getTime()));
                sentencia.setDate(6,  new java.sql.Date(ventaActualizado.getFecha_creacion().getTime()));
                sentencia.setString(7, cedula_cliente);
                sentencia.setString(8, ventaActualizado.getCedula_vendedor());
                sentencia.setString(9, ventaActualizado.getPlaca_automovil());
                sentencia.setInt(10, ventaActualizado.getid_orden_trabajo());


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


    public static boolean editarVenta(String cedula_cliente, String placa_automovil, int id_orden_trabajo) {
        try {
            PreparedStatement sentencia = connection.prepareStatement(
                    "SELECT * FROM venta WHERE cedula_cliente= ? AND (placa_automovil=? OR id_orden_trabajo=?)"
            );

            sentencia.setString(1, cedula_cliente);
            sentencia.setString(2, placa_automovil);
            sentencia.setInt(3, id_orden_trabajo);

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


}

package org.openjfx.Models.Usuario;

import org.openjfx.Models.Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQL_Sesion {

    private static Conexion conexion = new Conexion();
    private static Connection connection = conexion.getConnection();

    // Verifica si un usuario existe o no en la base de datos, basado en su ID
    public static boolean existeUsuario_Id(int id_usuario)  {
        try {
            PreparedStatement sentencia = connection.prepareStatement(
                    "SELECT * FROM usuario WHERE id_usuario="+ id_usuario
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
}

package src.Modelo;

// Clase para conectar con la base de datos en postgreSQL

import java.sql.Connection;
import java.sql.SQLException;

public class Conexion {
    private final String base = "";
    private final String user = "";
    private final String password = "";
    private final String url = "";
    private Connection con = null;

    public Connection getConexion(){

        try{
            System.out.println("Aqui va la conexion a PostgreSQL");
        }catch( Exception e/*SQLException e*/){
            System.err.println(e);
        }
        return con;
    }
}
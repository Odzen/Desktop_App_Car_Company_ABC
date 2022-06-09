package src.Modelo.CreacionTablas;

import src.Modelo.Conexion;

import java.sql.Statement;

public class CreacionTablas {

    private Conexion con = new Conexion();

    public CreacionTablas(){
        con.getConnection();
    }

    public void crearTablaUsuario() {
        Statement sentenciaSql;
        try {
            String consulta = "CREATE TABLE usuario" ;
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}

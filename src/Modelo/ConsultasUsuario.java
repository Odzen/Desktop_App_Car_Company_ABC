package src.Modelo;

import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.SQLException;

public class ConsultasUsuario  {

    private Conexion con = new Conexion();

    public ConsultasUsuario(){
        con.getConnection();
    }
    public boolean registrar(Usuario usr) {


        return true;
    }
}
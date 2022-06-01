package Modelo;

public class ConsultasUsuario extends Conexion{

    public boolean registrar(Usuario usr) {
        Connection con = getConexion();

        String sql = "INSERT INTO usuario (...)  VALUES (?,?,?...)";
    }
}
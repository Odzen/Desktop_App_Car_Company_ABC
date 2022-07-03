package src.Modelo;

// Clase para conectar con la base de datos en postgreSQL

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private String username = "";
    private String password = "";
    private String dbUrl = "";
    private URI dbUri = URI.create("");
    public static Connection con = null;

    //private final URI dbUri = "postgres://frztblycjqgdss:39b247709a9689db8bb5671d4c65bdf8051ff7883fc5e931b9bd97d804bd19d1@ec2-34-231-221-151.compute-1.amazonaws.com:5432/d3liet8hanl9f3";


    public Conexion() {
        try {
            //this.dbUri = new URI(System.getenv("DATABASE_URL"));
            this.dbUri = new URI("postgres://frztblycjqgdss:39b247709a9689db8bb5671d4c65bdf8051ff7883fc5e931b9bd97d804bd19d1@ec2-34-231-221-151.compute-1.amazonaws.com:5432/d3liet8hanl9f3");
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        this.username = this.dbUri.getUserInfo().split(":")[0];
        this.password = dbUri.getUserInfo().split(":")[1];
        this.dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();

    }

    public String getUsername() {
        return this.username;
    }
    public String getPassword() {
        return this.password;
    }

    public String getDbUrl() {
        return this.dbUrl;
    }

    public URI getDbUri() {
        return dbUri;
    }

    public static Connection getCon() {
        return con;
    }

    public Connection getConnection(){
        try{
            con = DriverManager.getConnection(this.getDbUrl(), this.getUsername(), this.getPassword());
            System.out.println("Conexión Exitosa a la Base de Datos en Heroku!!");
            return con;
        } catch (SQLException e){
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

}
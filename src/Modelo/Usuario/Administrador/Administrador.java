package src.Modelo.Usuario.Administrador;

public class Administrador {

    private String cedula_administrador;

    private int id_administrador;

    private String id_creado_por;

    public Administrador() {
        this.cedula_administrador = cedula_administrador;
        this.id_administrador = id_administrador;
        this.id_creado_por = id_creado_por;
    }

    public String getCedula() {
        return cedula_administrador;
    }

    public void setCedula(String cedula) {
        this.cedula_administrador = cedula;
    }

    public int getId_administrador() {
        return id_administrador;
    }

    public void setId_administrador(int id_administrador) {
        this.id_administrador = id_administrador;
    }

    public String getId_creado_por() {
        return id_creado_por;
    }

    public void setId_creado_por(String id_creado_por) {
        this.id_creado_por = id_creado_por;
    }
}

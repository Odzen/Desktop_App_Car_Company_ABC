package src.Modelo.Usuario.Vendedor;

public class Vendedor {

    private String cedula_vendedor;

    private int id_vendedor;

    private String id_creado_por;

    public Vendedor() {
        this.cedula_vendedor = cedula_vendedor;
        this.id_vendedor = id_vendedor;
        this.id_creado_por = id_creado_por;
    }

    public String getCedula_vendedor() {
        return cedula_vendedor;
    }

    public void setCedula_vendedor(String cedula_vendedor) {
        this.cedula_vendedor = cedula_vendedor;
    }

    public int getId_vendedor() {
        return id_vendedor;
    }

    public void setId_vendedor(int id_vendedor) {
        this.id_vendedor = id_vendedor;
    }

    public String getId_creado_por() {
        return id_creado_por;
    }

    public void setId_creado_por(String id_creado_por) {
        this.id_creado_por = id_creado_por;
    }
}

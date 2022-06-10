package src.Modelo.Usuario.Gerente;

public class Gerente {

    private String cedula_gerente;

    private int id_gerente;

    private String id_creado_por;

    public Gerente() {
        this.cedula_gerente = cedula_gerente;
        this.id_gerente = id_gerente;
        this.id_creado_por = id_creado_por;
    }

    public String getCedula_gerente() {
        return cedula_gerente;
    }

    public void setCedula_gerente(String cedula_gerente) {
        this.cedula_gerente = cedula_gerente;
    }

    public int getId_gerente() {
        return id_gerente;
    }

    public void setId_gerente(int id_gerente) {
        this.id_gerente = id_gerente;
    }

    public String getId_creado_por() {
        return id_creado_por;
    }

    public void setId_creado_por(String id_creado_por) {
        this.id_creado_por = id_creado_por;
    }
}

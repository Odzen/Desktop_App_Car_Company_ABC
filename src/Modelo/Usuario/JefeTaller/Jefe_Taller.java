package src.Modelo.Usuario.JefeTaller;

public class Jefe_Taller {

    private String cedula_jefe_taller;

    private int id_jefe_taller;

    private String id_creado_por;

    public Jefe_Taller() {
        this.cedula_jefe_taller = cedula_jefe_taller;
        this.id_jefe_taller = id_jefe_taller;
        this.id_creado_por = id_creado_por;
    }

    public String getCedula_jefe_taller() {
        return cedula_jefe_taller;
    }

    public void setCedula_jefe_taller(String cedula_jefe_taller) {
        this.cedula_jefe_taller = cedula_jefe_taller;
    }

    public int getId_jefe_taller() {
        return id_jefe_taller;
    }

    public void setId_jefe_taller(int id_jefe_taller) {
        this.id_jefe_taller = id_jefe_taller;
    }

    public String getId_creado_por() {
        return id_creado_por;
    }

    public void setId_creado_por(String id_creado_por) {
        this.id_creado_por = id_creado_por;
    }
}

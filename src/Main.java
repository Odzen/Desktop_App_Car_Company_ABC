package src;

import src.Controlador.Controlador;
import src.Modelo.ConsultasUsuario;
import src.Modelo.Usuario;
import src.Vista.Vista;

public class Main {
    public static void main(String[] args){
        Usuario usr = new Usuario();
        ConsultasUsuario consultasUsuario = new ConsultasUsuario();
        Vista vista = new Vista();

        // Deberia de recibir solo el modelo y la vista. Entonces ConsultasUsuario se deberian de manejar dentro de Usuario
        Controlador ctrl = new Controlador(usr, consultasUsuario, vista);
        ctrl.iniciar();

        System.out.println("ABC App");

    }
}
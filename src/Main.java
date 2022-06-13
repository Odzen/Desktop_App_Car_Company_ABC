package src;

import src.Controlador.Controlador;
import src.Modelo.Usuario.CRUD_Usuario;
import src.Modelo.Usuario.Usuario;
import src.Vista.Vista;

public class Main {
    public static void main(String[] args){
        System.out.println("ABC App Inicio Main\n");
        Usuario usr = new Usuario();
        CRUD_Usuario consultasUsuario = new CRUD_Usuario();
        Vista vista = new Vista();

        // Deberia de recibir solo el modelo y la vista. Entonces ConsultasUsuario se deberian de manejar dentro de Usuario
        Controlador ctrl = new Controlador(usr, consultasUsuario, vista);
        ctrl.iniciar();


    }
}
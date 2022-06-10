package src.Controlador;

import src.Modelo.Usuario.CRUD_Usuario;
import src.Modelo.Usuario.Usuario;
import src.Vista.Vista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Controlador implements ActionListener {
    private Usuario usr;
    private CRUD_Usuario usrConsulta;
    private Vista vista;

    //Scanner para simular la vista en consola
    private static final Scanner scanner = new Scanner(System.in);
    private int opcion = 0;

    public Controlador(Usuario usr, CRUD_Usuario usrConsulta, Vista vista){
        this.usr = usr;
        this.usrConsulta = usrConsulta;
        this.vista = vista;
        /* Botones*/
        /*this.vista.nameButtons.addActionListener(this) ...*/
    }

    // Para iniciar la vista
    public void iniciar(){
        this.leerOpcion();
    }

    // Metodos que escuchan los clikcs a los botones
    public void actionPerformed(ActionEvent e){

    }

    public void leerOpcion() {
        System.out.println("MENU USUARIO! Elija la opción que desea");
        do {
            System.out.println("\n 1. Ver usuarios");
            System.out.println("\n 2. Crear usuarios");
            System.out.println("\n 3. Editar usuarios");
            System.out.println("\n 4. Eliminar usuarios");
            System.out.println("\n 5. Salir");
            this.opcion = scanner.nextInt();

            if (opcion == 1) {
                ArrayList<Usuario>  usuarios = usrConsulta.leerTodosLosUsuarios();
                for (Usuario usuario : usuarios) {
                    System.out.printf(usuario.toString()+"\n");
                }
            } else if( opcion == 2) {
                usrConsulta.crearUsuario(this.pedirDatosUsuario());
            } else if( opcion == 3) {
                usrConsulta.editarUsuarios();
            } else if( opcion == 4) {
                usrConsulta.eliminarUsuarios();
            } else if( opcion == 5) {
                System.out.println("Salió del menu Usuario!!");
            }

        } while (opcion != 5);

    }

    public Usuario pedirDatosUsuario() {

        Usuario usuario = new Usuario();

        System.out.println("Ingrese Nombre");
        scanner.nextLine();
        String nombre = scanner.nextLine();
        usuario.setNombre(nombre);


        System.out.println("Ingrese Apellido");
        String apellido = scanner.nextLine();
        usuario.setApellido(apellido);


        System.out.println("Ingrese Contraseña");
        String contraseña = scanner.nextLine();
        usuario.setContraseña(contraseña);

        System.out.println("Ingrese Email");
        String email = scanner.nextLine();
        usuario.setEmail(email);


        System.out.println("Ingrese Avatar");
        String avatar = scanner.nextLine();
        usuario.setAvatar(avatar);

        System.out.println("Ingrese el tipo de Usuario (Admin, Jefe de Taller, Vendedor o Gerente)");
        String tipo_usuario = scanner.nextLine();
        usuario.setUser_type(tipo_usuario);


        return usuario;
    }

}
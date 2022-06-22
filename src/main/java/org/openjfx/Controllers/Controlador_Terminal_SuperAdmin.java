package org.openjfx.Controllers;

import org.openjfx.Models.Conexion;
import org.openjfx.Models.Usuario.CRUD_Usuario;
import org.openjfx.Models.Usuario.Usuario;
import org.openjfx.Models.Usuario.Utils.Hash;
import org.openjfx.Models.Usuario.Utils.Validaciones;

import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Controlador_Terminal_SuperAdmin {
    private Usuario usr;
    private CRUD_Usuario usrConsulta;

    //Scanner para simular la vista en consola
    private static final Scanner scanner = new Scanner(System.in);
    private int opcion = 0;

    public Controlador_Terminal_SuperAdmin(Usuario usr, CRUD_Usuario usrConsulta){
        this.usr = usr;
        this.usrConsulta = usrConsulta;
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
            System.out.println("\n 4. Eliminar usuarios - Soft Delete (Poner inactivo)");
            System.out.println("\n 5. DEV Eliminar TODOS LOS USUARIOS");
            System.out.println("\n 6. Salir");
            this.opcion = scanner.nextInt();

            if (opcion == 1) {
                ArrayList<Usuario>  usuarios = usrConsulta.leerTodosLosUsuarios();
                for (Usuario usuario : usuarios) {
                    System.out.printf(usuario.toString()+"\n");
                }
            } else if( opcion == 2) {
                usrConsulta.crearUsuario(this.pedirDatosUsuario());
            } else if( opcion == 3) {
                System.out.println("Ingrese Id del Usuario que quiere modificar");
                int id_usuario = scanner.nextInt();
                Usuario usuarioActualizado = pedirDatosUsuario();
                usrConsulta.editarUsuarios(id_usuario,usuarioActualizado);
            } else if( opcion == 4) {
                System.out.println("Ingrese Id del Usuario que quiere eliminar");
                int id_usuario = scanner.nextInt();
                usrConsulta.eliminarUsuario(id_usuario);
            }else if( opcion == 5) {
                usrConsulta.eliminarTodosUsuarios();
            } else if( opcion == 6) {
                System.out.println("Salió del menu Usuario!!");
                Conexion.closeConnection();
            }

        } while (opcion != 6);

    }

    public Usuario pedirDatosUsuario() {
        try {
            Usuario usuario = new Usuario();

            System.out.println("Ingrese Cedula");
            scanner.nextLine();
            String cedula = scanner.nextLine();
            usuario.setCedula(cedula);

            System.out.println("Ingrese Nombre");
            String nombre = scanner.nextLine();
            usuario.setNombre(nombre);

            System.out.println("Ingrese Apellido");
            String apellido = scanner.nextLine();
            usuario.setApellido(apellido);

            boolean contraseña_Valida = false;
            while (!contraseña_Valida)
            {
                System.out.println("Ingrese Contraseña");
                String contraseña = scanner.nextLine();
                if (Validaciones.validarPassword(contraseña)) {
                    usuario.setContraseña(Hash.md5(contraseña));
                    contraseña_Valida = true;
                } else {
                    System.err.println("Contraseña inválida");
                    contraseña_Valida = false;
                }
            }

            boolean email_Valido = false;
            while (!email_Valido)
            {
                System.out.println("Ingrese Email");
                String email = scanner.nextLine();
                if (Validaciones.validarEmail(email)) {
                    usuario.setEmail(email);
                    email_Valido = true;
                } else {
                    System.err.println("Email inválido");
                    email_Valido = false;
                }
            }

            System.out.println("Ingrese Avatar");
            String avatar = scanner.nextLine();
            usuario.setAvatar(avatar);

            System.out.println("Ingrese la fecha de nacimiento del usuario");
            String fecha_nacimiento = scanner.nextLine();

            boolean telefono_Valido = false;
            while (!telefono_Valido)
            {
                System.out.println("Ingrese el telefono del usuario");
                String telefono = scanner.nextLine();
                if (Validaciones.validarTelefono(telefono)) {
                    usuario.setTelefono(telefono);
                    telefono_Valido = true;
                } else {
                    System.err.println("Telefono inválido");
                    telefono_Valido = false;
                }
            }

            System.out.println("Ingrese el tipo de Usuario (1. Admin, 2. Gerente, 3. Jefe de Taller o 4. Vendedor)");
            int id_tipo_usuario = scanner.nextInt();
            usuario.setId_tipo_usuario(id_tipo_usuario);

            Date fecha_nacimiento_format = new SimpleDateFormat("dd/MM/yyyy").parse(fecha_nacimiento);
            usuario.setFecha_nacimiento(fecha_nacimiento_format);

            return usuario;

        } catch (Exception e) {
            System.err.println(e);

        }
        return null;
    }
}

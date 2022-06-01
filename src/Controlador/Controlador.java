package src.Controlador;

import src.Modelo.ConsultasUsuario;
import src.Modelo.Usuario;
import src.Vista.Vista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controlador implements ActionListener {
    private Usuario usr;
    private ConsultasUsuario usrConsulta;
    private Vista vista;

    public Controlador(Usuario usr, ConsultasUsuario usrConsulta, Vista vista){
        this.usr = usr;
        this.usrConsulta = usrConsulta;
        this.vista = vista;
        /* Botones*/
        /*this.vista.nameButtons.addActionListener(this) ...*/
    }

    // Para iniciar la vista
    public void iniciar(){

    }

    // Metodos que escuchan los clikcs a los botones
    public void actionPerformed(ActionEvent e){

    }

}
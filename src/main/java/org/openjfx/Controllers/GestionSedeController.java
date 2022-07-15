/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package org.openjfx.Controllers;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import animatefx.animation.FadeIn;
import animatefx.animation.Shake;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.openjfx.EmpresaAutosABC;
import org.openjfx.Models.Usuario.Utils.Validaciones;

import javax.swing.*;

/**
 * FXML Controller class
 *
 * @author mavel
 */
public class GestionSedeController implements Initializable {

    private String mensajeExito = String.format("-fx-text-fill: GREEN;");

    private String estiloMensajeExito = String.format("-fx-border-color: #A9A9A9; -fx-border-width: 2; -fx-border-radius: 5;");

    private String mensaje = String.format("-fx-text-fill: black;");
    private String mensajeError = String.format("-fx-text-fill: RED;");
    private String estiloMensajeError = String.format("-fx-border-color: RED; -fx-border-width: 2; -fx-border-radius: 5;");


    @FXML
    private Button btnCancelar;

    @FXML
    private TextField  txtNombreSede, txtTelSede, txtDirSede, txtCiudad;
     @FXML
    private SplitMenuButton estadoSede;
    @FXML
    private Label lbinvalidoRegistro;
    
     
    //Para validar los campos de usuario y contraseña
    @FXML
    void btnAgregarClick() throws IOException{

        lbinvalidoRegistro.setText("");
        txtNombreSede.setStyle(null);
        txtTelSede.setStyle(null);
        txtDirSede.setStyle(null);
        txtCiudad.setStyle(null);

        // Cuando los campos están en blanco
        if( txtNombreSede.getText().isEmpty()||
                txtTelSede.getText().isEmpty() || txtDirSede.getText().isEmpty() ||
                txtCiudad.getText().isEmpty())
        {
                        lbinvalidoRegistro.setStyle(mensajeError);
                        
            if( txtNombreSede.getText().isEmpty() &&
                    txtTelSede.getText().isEmpty() && txtDirSede.getText().isEmpty() &&
                    txtCiudad.getText().isEmpty()){

                lbinvalidoRegistro.setText("Se requieren todos los campos!");

                txtNombreSede.setStyle(mensajeError);
                txtTelSede.setStyle(mensajeError);
                txtDirSede.setStyle(mensajeError);
                txtCiudad.setStyle(mensajeError);
                new Shake(txtNombreSede).play();
                new Shake(txtTelSede).play();
                new Shake(txtDirSede).play();
                new Shake(txtCiudad).play();

        } else {
            lbinvalidoRegistro.setText("Algunos campos están vacíos!");
            boolean validado = this.validaciones();
            if (validado) {
                this.guardarSede();
            }
        }
    } else {
        boolean validado = this.validaciones();
        if (validado) {
            this.guardarSede();
        }
    }
}



    @FXML
    protected void btnCancelarClick() throws IOException {
        int input = JOptionPane.showConfirmDialog(null, "¿Está seguro que quiere cancelar el registro?",
                "txtDirSede una opción", JOptionPane.YES_NO_OPTION);
        if (input == 0) {
            EmpresaAutosABC.setRoot("menu");
        }
    }
    @FXML
    protected void btnInicio() throws IOException {
        EmpresaAutosABC.setRoot("menu");
    }

    private boolean validaciones() {

        boolean validado = true;
        lbinvalidoRegistro.setText("");

        // Validacion de telefono
        if (!Validaciones.validarTelefono(txtTelSede.getText()))
        {
            validado = false;
            String textoError = "Formato de telefono incorrecto!";
            //System.out.println(textoError);
            lbinvalidoRegistro.setText(lbinvalidoRegistro.getText() + textoError + '\n');
            lbinvalidoRegistro.setStyle(mensajeError);
            txtTelSede.setStyle(estiloMensajeError);
            new FadeIn(txtTelSede).play();
        }
        // Se comprueba la longitud del nombre de la sede
        if (txtNombreSede.getText().length() < 4 ||  txtNombreSede.getText().length() > 10)
        {
            validado = false;
            String textoError = "El nombre de la sede debe tener de 4 a 10 caracteres!";
            //System.out.println(textoError);
            lbinvalidoRegistro.setText(lbinvalidoRegistro.getText() + textoError + '\n');
            lbinvalidoRegistro.setStyle(mensajeError);
            txtNombreSede.setStyle(estiloMensajeError);
            new FadeIn(txtNombreSede).play();
        }
        // Se comprueba la longitud de la ciudad
        if (txtCiudad.getText().length() < 3 ||  txtCiudad.getText().length() > 20)
        {
            validado = false;
            String textoError = "La ciudad debe tener de 4 a 10 caracteres!";
            //System.out.println(textoError);
            lbinvalidoRegistro.setText(lbinvalidoRegistro.getText() + textoError + '\n');
            lbinvalidoRegistro.setStyle(mensajeError);
            txtNombreSede.setStyle(estiloMensajeError);
            new FadeIn(txtCiudad).play();
        }
        // Mensaje si el ingreso es correcto
        return validado;
    }

        public void limpiar() {
            txtNombreSede.setText("");
            txtTelSede.setText("");
            txtDirSede.setText("");
            txtCiudad.setText("");
        }


    public void guardarSede(){

    }
        @Override
    public void initialize(URL url, ResourceBundle rb) {

        }

     }



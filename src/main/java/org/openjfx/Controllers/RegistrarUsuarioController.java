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
import animatefx.animation.Tada;
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
public class RegistrarUsuarioController implements Initializable {
    
    private String mensajeExito = String.format("-fx-text-fill: GREEN;");
    private String estiloMensajeExito = String.format("-fx-border-color: #A9A9A9; -fx-border-width: 2; -fx-border-radius: 5;");

    private String mensaje = String.format("-fx-text-fill: black;");
    private String mensajeError = String.format("-fx-text-fill: RED;");
    private String estiloMensajeError = String.format("-fx-border-color: RED; -fx-border-width: 2; -fx-border-radius: 5;");
    @FXML
    private TextField txtNombre, txtApellido;
    @FXML
    private TextField txtPassword, txtPasswordConfirm;
    @FXML
    private TextField txtMail;
    @FXML
    private TextField txtDocumento;
    @FXML
    private TextField txtTelefono;
    @FXML
    private DatePicker dtpNacimiento;
     @FXML
    private SplitMenuButton cargo;
    @FXML
    MenuItem firstItem;
    @FXML
    MenuItem secondItem;
    @FXML
    private Label validacionRegistroLabel;

    //Para validar los campos de usuario y contraseña
    @FXML
    protected void btnAgregarClick() throws IOException{
    
        // Cuando los campos están en blanco
        if(txtNombre.getText().isEmpty() || txtPasswordConfirm.getText().isEmpty() ||
            txtApellido.getText().isEmpty() || txtPassword.getText().isEmpty() ||
            txtMail.getText().isEmpty()|| txtDocumento.getText().isEmpty() ||
            txtTelefono.getText().isEmpty() || dtpNacimiento.getAccessibleText().isEmpty() ||
            cargo.getText().isEmpty())
        {
                validacionRegistroLabel.setStyle(mensajeError);
                if(txtNombre.getText().isEmpty() && txtPasswordConfirm.getText().isEmpty() &&
                    txtApellido.getText().isEmpty() && txtPassword.getText().isEmpty() &&
                    txtMail.getText().isEmpty()  && txtDocumento.getText().isEmpty() &&
                    txtTelefono.getText().isEmpty() && dtpNacimiento.getAccessibleText().isEmpty() &&
                    cargo.getText().isEmpty())
                {
                    validacionRegistroLabel.setText("Se requieren todos los campos!");
                    txtNombre.setStyle(mensajeError);
                    txtPasswordConfirm.setStyle(mensajeError);
                    txtApellido.setStyle(mensajeError);
                    txtPassword.setStyle(mensajeError);
                    txtMail.setStyle(mensajeError);
                    txtDocumento.setStyle(mensajeError);
                    txtTelefono.setStyle(mensajeError);
                    dtpNacimiento.setStyle(mensajeError);
                    cargo.setStyle(mensajeError);
                    new Shake(txtNombre).play();
                    new Shake(txtPasswordConfirm).play();
                    new Shake(txtApellido).play();
                    new Shake(txtPassword).play();
                    new Shake(txtMail).play();
                    new Shake(txtDocumento).play();
                    new Shake(txtTelefono).play();
                    new Shake(dtpNacimiento).play();
                    new Shake(cargo).play();
                }
        }
        // Validacion contraseña
        else if (!Validaciones.validarPassword(txtPassword.getText()))
        {
            validacionRegistroLabel.setText("Formato de contraseña incorrecto!");
            validacionRegistroLabel.setStyle(mensajeError);
            txtPassword.setStyle(estiloMensajeError);
            new FadeIn(txtPassword).play();
        }
        // Validacion confirmación de contraseña
        else if (!Validaciones.validarPassword(txtPasswordConfirm.getText()))
        {
            validacionRegistroLabel.setText("Formato de contraseña incorrecto!");
            validacionRegistroLabel.setStyle(mensajeError);
            txtPasswordConfirm.setStyle(estiloMensajeError);
            new FadeIn(txtPasswordConfirm).play();
        }
        // Validacion de telefono
        else if (!Validaciones.validarTelefono(txtTelefono.getText()))
        {
            validacionRegistroLabel.setText("Formato de telefono incorrecto!");
            validacionRegistroLabel.setStyle(mensajeError);
            txtTelefono.setStyle(estiloMensajeError);
            new FadeIn(txtTelefono).play();
        }
        // Se comprueba la longitud del nombre del usuario
        else if (txtNombre.getText().length() < 6 ||  txtNombre.getText().length() > 20)
        {
            validacionRegistroLabel.setText("El usuario debe tener de 6 a 20 caracteres!");
            validacionRegistroLabel.setStyle(mensajeError);
            txtNombre.setStyle(estiloMensajeError);
            new FadeIn(txtNombre).play();
        }
        // Validación Email
        else if (Validaciones.validarEmail(txtMail.getText()))
        {
            validacionRegistroLabel.setText("Formato de email incorrecto!");
            validacionRegistroLabel.setStyle(mensajeError);
            txtMail.setStyle(estiloMensajeError);
            new FadeIn(txtMail).play();
        }
        // Mensaje si el ingreso es correcto
        else
        {
            validacionRegistroLabel.setText("Ingreso éxitoso!");
            validacionRegistroLabel.setStyle(mensajeExito);
            txtNombre.setStyle(estiloMensajeExito);
            txtPassword.setStyle(estiloMensajeExito);
            new Tada(validacionRegistroLabel).play();
        }
    }

    @FXML
    protected void btnCancelarClick() throws IOException {
        int input = JOptionPane.showConfirmDialog(null, "¿Está seguro que quiere cancelar el registro?",
                "Seleccione una opción", JOptionPane.YES_NO_OPTION);
        if (input == 0) {
            EmpresaAutosABC.setRoot("menu");
        }
    }

    @FXML
    protected void btnInicio() throws IOException {
        EmpresaAutosABC.setRoot("menu");
    }
    @FXML
    private void setFirstItem() throws IOException {
        cargo.setText(firstItem.getText());
    }

    @FXML
    private void setSecondItem() throws IOException {
        cargo.setText(secondItem.getText());
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

}

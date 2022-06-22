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
        validacionRegistroLabel.setText("");
        txtNombre.setStyle(null);
        txtPasswordConfirm.setStyle(null);
        txtApellido.setStyle(null);
        txtPassword.setStyle(null);
        txtMail.setStyle(null);
        txtDocumento.setStyle(null);
        txtTelefono.setStyle(null);
        dtpNacimiento.setStyle(null);
        cargo.setStyle(null);
        //System.out.println("Presionó Confirmar");
        // Cuando los campos están en blanco
        if(txtNombre.getText().isEmpty() || txtPasswordConfirm.getText().isEmpty() ||
            txtApellido.getText().isEmpty() || txtPassword.getText().isEmpty() ||
            txtMail.getText().isEmpty()|| txtDocumento.getText().isEmpty() ||
            txtTelefono.getText().isEmpty() || dtpNacimiento.getValue()==null ||
            cargo.getText().equals("Seleccionar Cargo"))
        {
            validacionRegistroLabel.setStyle(mensajeError);
            if(txtNombre.getText().isEmpty() && txtPasswordConfirm.getText().isEmpty() &&
                txtApellido.getText().isEmpty() && txtPassword.getText().isEmpty() &&
                txtMail.getText().isEmpty()  && txtDocumento.getText().isEmpty() &&
                txtTelefono.getText().isEmpty() && dtpNacimiento.getValue()==null &&
                cargo.getText().equals("Seleccionar Cargo"))
            {
                validacionRegistroLabel.setText("Se requieren todos los campos!");
                txtNombre.setStyle(estiloMensajeError);
                txtPasswordConfirm.setStyle(estiloMensajeError);
                txtApellido.setStyle(estiloMensajeError);
                txtPassword.setStyle(estiloMensajeError);
                txtMail.setStyle(estiloMensajeError);
                txtDocumento.setStyle(estiloMensajeError);
                txtTelefono.setStyle(estiloMensajeError);
                dtpNacimiento.setStyle(estiloMensajeError);
                cargo.setStyle(estiloMensajeError);
                new Shake(txtNombre).play();
                new Shake(txtPasswordConfirm).play();
                new Shake(txtApellido).play();
                new Shake(txtPassword).play();
                new Shake(txtMail).play();
                new Shake(txtDocumento).play();
                new Shake(txtTelefono).play();
                new Shake(dtpNacimiento).play();
                new Shake(cargo).play();
            } else {
                validacionRegistroLabel.setText("Algunos campos están vacíos!");
                boolean validado = this.validaciones();
                if (validado) {
                    this.validadoLabelSet();
                }
            }
        } else {
            boolean validado = this.validaciones();
            if (validado) {
                this.validadoLabelSet();
            }
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
    private void setFirstItem() {
        cargo.setText(firstItem.getText());
    }

    @FXML
    private void setSecondItem() {
        cargo.setText(secondItem.getText());
    }

    private boolean validaciones() {
        // Validacion contraseña
        boolean validado = true;
        validacionRegistroLabel.setText("");
        if (!Validaciones.validarPassword(txtPassword.getText()))
        {
            validado = false;
            String textoError = "Formato de contraseña incorrecto!";
            //System.out.println(textoError);
            validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
            validacionRegistroLabel.setStyle(mensajeError);
            txtPassword.setStyle(estiloMensajeError);
            new FadeIn(txtPassword).play();
        }
        // Validacion confirmación de contraseña
        if (!Validaciones.validarPassword(txtPasswordConfirm.getText()))
        {
            validado = false;
            String textoError = "Formato de confirmación de contraseña incorrecto!";
            //System.out.println(textoError);
            validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
            validacionRegistroLabel.setStyle(mensajeError);
            txtPasswordConfirm.setStyle(estiloMensajeError);
            new FadeIn(txtPasswordConfirm).play();
        }

        // Validacion contraseñas iguales
        if (!txtPasswordConfirm.getText().equals(txtPassword.getText()))
        {
            validado = false;
            String textoError = "Contraseñas no coinciden!";
            //System.out.println(textoError);
            validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
            validacionRegistroLabel.setStyle(mensajeError);
            txtPasswordConfirm.setStyle(estiloMensajeError);
            new FadeIn(txtPasswordConfirm).play();
            txtPassword.setStyle(estiloMensajeError);
            new FadeIn(txtPassword).play();
        }
        // Validacion de telefono
        if (!Validaciones.validarTelefono(txtTelefono.getText()))
        {
            validado = false;
            String textoError = "Formato de telefono incorrecto!";
            //System.out.println(textoError);
            validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
            validacionRegistroLabel.setStyle(mensajeError);
            txtTelefono.setStyle(estiloMensajeError);
            new FadeIn(txtTelefono).play();
        }
        // Se comprueba la longitud del nombre del usuario
        if (txtNombre.getText().length() < 4 ||  txtNombre.getText().length() > 20)
        {
            validado = false;
            String textoError = "El usuario debe tener de 4 a 20 caracteres!";
            //System.out.println(textoError);
            validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
            validacionRegistroLabel.setStyle(mensajeError);
            txtNombre.setStyle(estiloMensajeError);
            new FadeIn(txtNombre).play();
        }
        // Se comprueba la longitud del nombre del usuario
        if (txtApellido.getText().length() < 4 ||  txtApellido.getText().length() > 20)
        {
            validado = false;
            String textoError = "El apellido debe tener de 4 a 20 caracteres!";
            //System.out.println(textoError);
            validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
            validacionRegistroLabel.setStyle(mensajeError);
            txtApellido.setStyle(estiloMensajeError);
            new FadeIn(txtApellido).play();
        }
        // Se comprueba la longitud de la cedula del usuario
        if (txtDocumento.getText().length() < 10)
        {
            validado = false;
            String textoError = "La cedula debe de tener al menos 10 caracteres!";
            //System.out.println(textoError);
            validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
            validacionRegistroLabel.setStyle(mensajeError);
            txtDocumento.setStyle(estiloMensajeError);
            new FadeIn(txtDocumento).play();
        }
        // Validación Email
        if (!Validaciones.validarEmail(txtMail.getText()))
        {
            validado = false;
            String textoError = "Formato de email incorrecto!";
            //System.out.println(textoError);
            validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
            validacionRegistroLabel.setStyle(mensajeError);
            txtMail.setStyle(estiloMensajeError);
            new FadeIn(txtMail).play();
        }
        // Validación Fecha
        if (dtpNacimiento.getValue()==null)
        {
            validado = false;
            String textoError = "Formato de fecha incorrecto!";
            //System.out.println(textoError);
            validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
            validacionRegistroLabel.setStyle(mensajeError);
            dtpNacimiento.setStyle(estiloMensajeError);
            new FadeIn(dtpNacimiento).play();
        }
        // Validacion Cargo
        if (cargo.getText().equals("Seleccionar Cargo") || (!cargo.getText().equals("Gerente") && !cargo.getText().equals("Administrador")) )
        {
            //System.out.println(cargo.getText());
            validado = false;
            String textoError = "Formato de cargo incorrecto!";
            //System.out.println(textoError);
            validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
            validacionRegistroLabel.setStyle(mensajeError);
            cargo.setStyle(estiloMensajeError);
            new FadeIn(cargo).play();
        }
        // Mensaje si el ingreso es correcto
        return validado;
    }

    private void validadoLabelSet() {
        validacionRegistroLabel.setText("");
        System.out.println("Pasó Validaciones");
        validacionRegistroLabel.setStyle(mensajeExito);
        validacionRegistroLabel.setText("Ingreso éxitoso!");
        txtNombre.setStyle(estiloMensajeExito);
        txtPassword.setStyle(estiloMensajeExito);
        new Tada(validacionRegistroLabel).play();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }


}

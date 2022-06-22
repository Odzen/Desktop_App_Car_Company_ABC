/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package org.openjfx.Controllers;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.openjfx.EmpresaAutosABC;

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
    private TextField txtNombreUsuario;
    @FXML
    private TextField txtNombre, txtApellido;
    @FXML
    private TextField txtPassword1;
    @FXML
    private TextField txtMail;
    @FXML
    private TextField txtDocumento;
    @FXML
    private TextField txtTel;
    
    @FXML
    private DatePicker dtpNacimiento;
     @FXML
    private SplitMenuButton cargo;
    @FXML
    MenuItem firstItem;
    @FXML
    MenuItem secondItem;
    @FXML
    private Label lbinvalidoRegistro;

    //Para validar los campos de usuario y contraseña
    @FXML
    protected void btnAgregarClick() throws IOException{
    
        // Cuando los campos están en blanco
        if(txtNombreUsuario.getText().isEmpty() || txtNombre.getText().isEmpty()||
           txtApellido.getText().isEmpty() || txtPassword1.getText().isEmpty() ||
            txtMail.getText().isEmpty()||  txtMail.getText().isEmpty() ||
            txtDocumento.getText().isEmpty() || txtTel.getText().isEmpty())
        {
                lbinvalidoRegistro.setStyle(mensajeError);
                        
                if(txtNombreUsuario.getText().isEmpty() && txtNombre.getText().isEmpty() &&
                   txtApellido.getText().isEmpty() && txtPassword1.getText().isEmpty() &&
                    txtMail.getText().isEmpty() && txtMail.getText().isEmpty() &&
                    txtDocumento.getText().isEmpty() && txtTel.getText().isEmpty())
                {
                        lbinvalidoRegistro.setText("Se requieren todos los campos!");
                        txtNombreUsuario.setStyle(mensajeError);
                        txtNombre.setStyle(mensajeError);
                        txtApellido.setStyle(mensajeError);
                        txtMail.setStyle(mensajeError);
                        txtTel.setStyle(mensajeError);
                        txtDocumento.setStyle(mensajeError);
                        txtPassword1.setStyle(mensajeError);
                        new animatefx.animation.Shake(txtNombreUsuario).play();
                        new animatefx.animation.Shake(txtPassword1).play();
                }
        }
        // Se comprueba la longitud de la contraseña
        else if (txtPassword1.getText().length() < 5)
        {
            lbinvalidoRegistro.setText("La contraseña tiene es menos  de 5 caracteres!");
            lbinvalidoRegistro.setStyle(mensajeError);
            txtPassword1.setStyle(estiloMensajeError);
            new animatefx.animation.FadeIn(txtPassword1).play();
        }
        // Se comprueba la longitud del usuario
        else if (txtNombreUsuario.getText().length() < 6)
        {
            lbinvalidoRegistro.setText("El usuario tiene es menos de 6 caracteres!");
            lbinvalidoRegistro.setStyle(mensajeError);
            txtNombreUsuario.setStyle(estiloMensajeError);
            new animatefx.animation.FadeIn(txtNombreUsuario).play();
        }
        // Se comprueba la longitud del mail
        else if (txtMail.getText().length() < 12)
        {
            lbinvalidoRegistro.setText("El correo tiene es menos de 12 caracteres!");
            lbinvalidoRegistro.setStyle(mensajeError);
            txtMail.setStyle(estiloMensajeError);
            new animatefx.animation.FadeIn(txtMail).play();
        }
        // Mensaje si el ingreso es correcto
        else
        {
            lbinvalidoRegistro.setText("Ingreso éxitoso!");
            lbinvalidoRegistro.setStyle(mensajeExito);
            txtNombreUsuario.setStyle(estiloMensajeExito);
            txtPassword1.setStyle(estiloMensajeExito);
           new animatefx.animation.Tada(lbinvalidoRegistro).play();
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

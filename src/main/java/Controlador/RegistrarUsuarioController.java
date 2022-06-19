/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controlador;

import Modelo.Usuario.Usuario;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

/**
 * FXML Controller class
 *
 * @author mavel
 */
public class RegistrarUsuarioController implements Initializable {
    
    String mensajeExito = String.format("-fx-text-fill: GREEN;");
    String estiloMensajeExito = String.format("-fx-border-color: #A9A9A9; -fx-border-width: 2; -fx-border-radius: 5;");

    String mensaje = String.format("-fx-text-fill: black;");
    String mensajeError = String.format("-fx-text-fill: RED;");
    String estiloMensajeError = String.format("-fx-border-color: RED; -fx-border-width: 2; -fx-border-radius: 5;");

    
    @FXML
    private Button btnAgregar;
    @FXML
    private Button btnCancelar;
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
    private ComboBox<Usuario> cmbCargo;
     
      @FXML
    private Label lbinvalidoRegistro;
    
     
    //Para validar los campos de usuario y contraseña
    @FXML
    void btnAgregarClick() throws IOException{
    
        // Cuando los campos están en blanco
        if(txtNombreUsuario.getText().isEmpty() || txtNombre.getText().isEmpty()||
               txtApellido.getText().isEmpty() || txtPassword1.getText().isEmpty() ||
                txtMail.getText().isEmpty()||  txtMail.getText().isEmpty() ||
                txtDocumento.getText().isEmpty() || txtTel.getText().isEmpty()){
            
            
                        lbinvalidoRegistro.setStyle(mensajeError);
                        
            if(txtNombreUsuario.getText().isEmpty() && txtNombre.getText().isEmpty() &&
               txtApellido.getText().isEmpty() && txtPassword1.getText().isEmpty() &&
                txtMail.getText().isEmpty() && txtMail.getText().isEmpty() &&
                txtDocumento.getText().isEmpty() && txtTel.getText().isEmpty()){
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
           } else // Se comprueba la longitud de la contraseña
            if (txtPassword1.getText().length() < 5) {
                lbinvalidoRegistro.setText("La contraseña tiene es menos  de 5 caracteres!");
                lbinvalidoRegistro.setStyle(mensajeError);
                txtPassword1.setStyle(estiloMensajeError);
                new animatefx.animation.FadeIn(txtPassword1).play();
                
    } else // Se comprueba la longitud del usuario
            if (txtNombreUsuario.getText().length() < 6) {
                lbinvalidoRegistro.setText("El usuario tiene es menos de 6 caracteres!");
                lbinvalidoRegistro.setStyle(mensajeError);
                txtNombreUsuario.setStyle(estiloMensajeError);
                new animatefx.animation.FadeIn(txtNombreUsuario).play();
            }    
             else // Se comprueba la longitud del mail
            if (txtMail.getText().length() < 12) {
                lbinvalidoRegistro.setText("El correo tiene es menos de 12 caracteres!");
                lbinvalidoRegistro.setStyle(mensajeError);
                txtMail.setStyle(estiloMensajeError);
                new animatefx.animation.FadeIn(txtMail).play();
            } 
            // Mensaje si el ingreso es correcto
            else {
                lbinvalidoRegistro.setText("Ingreso éxitoso!");
                lbinvalidoRegistro.setStyle(mensajeExito);
                txtNombreUsuario.setStyle(estiloMensajeExito);
                txtPassword1.setStyle(estiloMensajeExito);
                new animatefx.animation.Tada(lbinvalidoRegistro).play();
                 
               
            }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
    

                
                

        
    }

   


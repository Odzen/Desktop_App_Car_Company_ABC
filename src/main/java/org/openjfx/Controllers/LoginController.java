package org.openjfx.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.scene.control.Label;
import org.openjfx.EmpresaAutosABC;
import org.openjfx.Models.Usuario.CRUD_Usuario;
import org.openjfx.Models.Usuario.Usuario;
import org.openjfx.Models.Usuario.Utils.Hash;

import javax.print.DocFlavor.URL;

/**
 * FXML Controller class
 *
 * @author Mavel Sterling
 * @author Juan Velasquez
 */
public class LoginController  {

    private Stage stage;
    protected String mensajeExito = String.format("-fx-text-fill: GREEN;");
    protected String estiloMensajeExito = String.format("-fx-border-color: #A9A9A9; -fx-border-width: 2; -fx-border-radius: 5;");
    protected String mensaje = String.format("-fx-text-fill: black;");
    protected String mensajeError = String.format("-fx-text-fill: RED;");
    protected String estiloMensajeError = String.format("-fx-border-color: RED; -fx-border-width: 2; -fx-border-radius: 5;");

    @FXML
    private Button btnSalir;

    @FXML
    private Label invalidoUser;
    @FXML
    private Label validoUser;

    @FXML
    private PasswordField txtContraseña;

    @FXML
    private TextField txtUser;
    
    // Para salir de la aplicación
    @FXML
    protected void btnSalirClick() {
        Stage stage = (Stage) btnSalir.getScene().getWindow();
        stage.close();
     }

    //Para validar los campos de usuario(cédula) y contraseña
    @FXML
    void btnLoginClick() throws IOException{
        // Cuando ambos campos están vacíos
        if(txtUser.getText().isEmpty() || txtContraseña.getText().isEmpty()) {
            invalidoUser.setStyle(mensajeError);
            if (txtUser.getText().isEmpty() && txtContraseña.getText().isEmpty()) {
                invalidoUser.setText("Se requiere el usuario y la contraseña!");
                txtUser.setStyle(mensaje);
                txtContraseña.setStyle(mensajeError);
                new animatefx.animation.Shake(txtUser).play();
                new animatefx.animation.Shake(txtContraseña).play();
            }
            // Cuando solo el usuario esta vacío
            else if (txtUser.getText().isEmpty()) {
                    txtUser.setStyle(mensaje);
                    invalidoUser.setText("Se requiere el usuario (cédula)!");
                    txtContraseña.setStyle(estiloMensajeExito);
                    new animatefx.animation.Shake(txtUser).play();
            }
            // Cuando solo la contraseña esta vacía
            else
                if (txtContraseña.getText().isEmpty()) {
                    txtContraseña.setStyle(estiloMensajeError);
                    invalidoUser.setText("Se requiere la contraseña!");
                    txtUser.setStyle(estiloMensajeExito);
                    new animatefx.animation.Shake(txtContraseña).play();
                }
        }
        // Se comprueba la longitud de la contraseña
        else if (txtContraseña.getText().length() < 8) {
            invalidoUser.setText("Al menos 8 caracteres!");
            invalidoUser.setStyle(mensajeError);
            txtContraseña.setStyle(estiloMensajeError);
            new animatefx.animation.FadeIn(txtContraseña).play();
            new animatefx.animation.Shake(txtContraseña).play();
        }
        // Se comprueba la longitud de la cedula
        else if (txtUser.getText().length() < 10) {
            invalidoUser.setText("Al menos 10 caracteres!");
            invalidoUser.setStyle(mensajeError);
            txtUser.setStyle(estiloMensajeError);
            new animatefx.animation.FadeIn(txtUser).play();
            new animatefx.animation.Shake(txtUser).play();
        }
        // Se comprueba que ambos campos no sean iguales
        else if (txtUser.getText().equals(txtContraseña.getText())) {
            invalidoUser.setText("Cédula y Contraseña no pueden ser iguales!");
            invalidoUser.setStyle(mensajeError);
            txtUser.setStyle(estiloMensajeError);
            new animatefx.animation.FadeIn(txtUser).play();
            new animatefx.animation.FadeIn(txtContraseña).play();
            new animatefx.animation.Shake(txtUser).play();
            new animatefx.animation.Shake(txtContraseña).play();
        }
        // Si el ingreso es correcto
        else {
            CRUD_Usuario usuarioSql = new CRUD_Usuario();
            Usuario usuarioLogin = new Usuario();
            String contraseña = txtContraseña.getText();
            String contraseñaCifrada = Hash.md5(contraseña);

            Date date = new Date();
            DateFormat fechaHora = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
            usuarioLogin.setLast_session(fechaHora.format(date));

            usuarioLogin.setCedula(txtUser.getText());
            usuarioLogin.setContraseña(contraseñaCifrada);

            // Check si existe un usuario con esa cedula y compara contraseñas
            if(usuarioSql.login(usuarioLogin)) {
                validoUser.setText("Ingreso éxitoso!");
                validoUser.setStyle(mensajeExito);
                txtUser.setStyle(estiloMensajeExito);
                txtContraseña.setStyle(estiloMensajeExito);
                new animatefx.animation.Tada(validoUser).play();
                this.btnLogin_MouseClicked();
            }
            else {
                //JOptionPane.showMessageDialog(null, "Datos Incorrectos!");
                invalidoUser.setText("Cedula o Contraseña incorrectos!");
                invalidoUser.setStyle(mensajeError);
                txtUser.setStyle(estiloMensajeError);
                new animatefx.animation.FadeIn(txtUser).play();
                new animatefx.animation.FadeIn(txtContraseña).play();
                new animatefx.animation.Shake(txtUser).play();
                new animatefx.animation.Shake(txtContraseña).play();
            }
        }
    }

    // Cuando el usuario hace click en el boton Login, pasa al menú
    @FXML
    private void btnLogin_MouseClicked() throws IOException {
        EmpresaAutosABC.setRoot("menu");
    }
     @FXML
    public void initialize (URL url, ResourceBundle rb){
        //TODO
    }
}

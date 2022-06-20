package org.openjfx;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javax.print.DocFlavor.URL;


/**
 * FXML Controller class
 *
 * @author mavel
 */
public class LoginController  {

        
    private Stage stage;  
        
        protected
    String mensajeExito = String.format("-fx-text-fill: GREEN;");
    String estiloMensajeExito = String.format("-fx-border-color: #A9A9A9; -fx-border-width: 2; -fx-border-radius: 5;");

    String mensaje = String.format("-fx-text-fill: black;");
    String mensajeError = String.format("-fx-text-fill: RED;");
    String estiloMensajeError = String.format("-fx-border-color: RED; -fx-border-width: 2; -fx-border-radius: 5;");

    @FXML
    private Button btnLogin;

    @FXML
    private Button btnSalir;

    @FXML
    private Label invalidoUser;

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
    
    
    //Para validar los campos de usuario y contraseña
    @FXML
    void btnLoginClick() throws IOException{
    
        // Cuando los campos están en blanco
        if(txtUser.getText().isEmpty() || txtContraseña.getText().isEmpty()){
                        invalidoUser.setStyle(mensajeError);
                        
            if (txtUser.getText().isEmpty() && txtContraseña.getText().isEmpty()) {
                invalidoUser.setText("Se requiere el usuario y la contraseña!");
                txtUser.setStyle(mensaje);
                txtContraseña.setStyle(mensajeError);
                
             new animatefx.animation.Shake(txtUser).play();
             new animatefx.animation.Shake(txtContraseña).play();
            

        }
            else // Cuando el usuario esta en blanco
                if (txtUser.getText().isEmpty()) {
                    txtUser.setStyle(mensaje);
                    invalidoUser.setText("Se requiere el usuario!");
                    txtContraseña.setStyle(estiloMensajeExito);
                    new animatefx.animation.Shake(txtUser).play();
                    
                } else // Cuando la contraseña queda en blanco
                    if (txtContraseña.getText().isEmpty()) {
                        txtContraseña.setStyle(estiloMensajeError);
                        invalidoUser.setText("Se requiere la contraseña!");
                        txtUser.setStyle(estiloMensajeExito);
                        new animatefx.animation.Shake(txtContraseña).play();
                    }
            
    } else // Se comprueba la longitud de la contraseña
            if (txtContraseña.getText().length() < 5) {
                invalidoUser.setText("La contraseña tiene es menos caracteres!");
                invalidoUser.setStyle(mensajeError);
                txtContraseña.setStyle(estiloMensajeError);
                new animatefx.animation.FadeIn(txtContraseña).play();
                
    } else // Se comprueba la longitud del usuario
            if (txtUser.getText().length() < 6) {
                invalidoUser.setText("El usuario tiene es menos caracteres!");
                invalidoUser.setStyle(mensajeError);
                txtUser.setStyle(estiloMensajeError);
                new animatefx.animation.FadeIn(txtUser).play();
            }        
            // Mensaje si el ingreso es correcto
            else {
                invalidoUser.setText("Ingreso éxitoso!");
                invalidoUser.setStyle(mensajeExito);
                txtUser.setStyle(estiloMensajeExito);
                txtContraseña.setStyle(estiloMensajeExito);
                //new animatefx.animation.Tada(invalidoUser).play();
                this.btnLogin_MouseClicked();
               
            }
    }
     
    @FXML
    private void btnLogin_MouseClicked() throws IOException {
        EmpresaAutosABC.setRoot("menu");
    }


    
    
    
     @FXML
    public void initialize (URL url, ResourceBundle rb){
        //TODO
    }

    

    
 

}

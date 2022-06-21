package org.openjfx;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author mavel
 */



public class GestionUsuarioController implements Initializable {

    @FXML
    private Button bttnNuevoUsuario, btnSalir;
    
    
    // Para salir de la aplicación
    @FXML
    protected void btnSalirClick() {
    Stage stage = (Stage) btnSalir.getScene().getWindow();
    stage.close();
    }
   
     @FXML
     private void bttnNuevoUsuarioClicked() throws IOException {
         EmpresaAutosABC.setRoot("registrarUsuario");
    // Animación
    //new animatefx.animation.BounceIn(root).play();

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}

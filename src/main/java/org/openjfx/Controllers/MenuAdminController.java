package org.openjfx.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import GlobalUtils.Dialogs;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.openjfx.EmpresaAutosABC;

public class MenuAdminController implements Initializable {
    @FXML
    private Label labelNameUsuario;

    @FXML
    private Button btnSalir;

    // Para salir de la aplicación
    @FXML
    private void btnSalirClickmenu() {
        if (Dialogs.showConfirm("Seleccione una opción", "¿Está seguro que quiere salir de la aplicación?", Dialogs.YES, Dialogs.NO).equals(Dialogs.YES)) {
            Stage stage = (Stage) btnSalir.getScene().getWindow();
            stage.close();
        }
    }
    @FXML
    private void btnGestationUsuariosClick() throws IOException{
        EmpresaAutosABC.setRoot("GestionUsuAdmin");
    }

    @FXML
    private void bttnGestionSedes() throws IOException{
        EmpresaAutosABC.setRoot("GestionSede");
    }
    @FXML
    private void btnCerrarSesionClick() throws IOException{
        EmpresaAutosABC.setRoot("Login");
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}

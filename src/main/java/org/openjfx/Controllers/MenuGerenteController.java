
package org.openjfx.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import GlobalUtils.Dialogs;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.openjfx.EmpresaAutosABC;


public class MenuGerenteController implements Initializable {
    @FXML
    private Button btnCerrarSesion;

    @FXML
    private Button btnSalir;

    @FXML
    private Button bttnBodegaGer;

    @FXML
    private Button btnGerente;

    @FXML
    private Button bttnGestionSede;

    @FXML
    private Button bttnReporteGer;

    @FXML
    private Label bttnSalir;

    @FXML
    private Label lblSaludo;

    // Para salir de la aplicación
    @FXML
    private void btnSalirClickmenu() {
        if (Dialogs.showConfirm("Seleccione una opción", "¿Está seguro que quiere salir de la aplicación?", Dialogs.YES, Dialogs.NO).equals(Dialogs.YES)) {
            Stage stage = (Stage) btnSalir.getScene().getWindow();
            stage.close();
        }
    }
    @FXML
    private void btnGestionGerenteClick() throws IOException{
        EmpresaAutosABC.setRoot("GestionUsuGerente");
    }

    @FXML
    private void btnGestionAutomovilClick() throws IOException{
        EmpresaAutosABC.setRoot("GestionAutomovil");
    }

    @FXML
    private void btnCerrarSesionClick() throws IOException{
        EmpresaAutosABC.setRoot("Login");
    }
    @FXML
    private void btnReportes() throws IOException{
        EmpresaAutosABC.setRoot("ReportesGerente");
    }


    @FXML
    private void btnResumenVendedorClick() throws IOException {
        EmpresaAutosABC.setRoot("ResumenGerente");
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }


}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
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


public class MenuVendedorController implements Initializable {
    @FXML
    private Button btnCerrarSesion;

    @FXML
    private Button btnSalir;

    @FXML
    private Button btnVendedor;


    @FXML
    private Button bttnBodegaVendedor;

    @FXML
    private Button bttnReporteVendedor;

    @FXML
    private  Button bttnCotizacion;

    @FXML
    private  Button bttnVentas;
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
    private void btnGestionVendedorClick() throws IOException{
        EmpresaAutosABC.setRoot("GestionVendedor");
    }

    @FXML
    private void btnCotizacionClick() throws IOException{
        EmpresaAutosABC.setRoot("Cotizacion");
    }

    @FXML
    private void btnVentasClick() throws IOException{
        EmpresaAutosABC.setRoot("Venta");
    }

    @FXML
    private void btnReporteVendedorClick() throws IOException{
        EmpresaAutosABC.setRoot("ReporteVendedor");
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
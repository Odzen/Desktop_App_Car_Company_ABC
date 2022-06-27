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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.openjfx.EmpresaAutosABC;
import org.openjfx.Models.Usuario.Usuario;
import org.openjfx.Models.Usuario.Utils.Rol;

/**
 * FXML Controller class
 *
 * @author mavel
 */
public class MenuAdminController implements Initializable {
    @FXML
    private Button bttnAdmin, bttnRegistroAdmin, btnCerrarSesion;

    @FXML
    private Label labelNameUsuario;

    @FXML
    private Button btnSalir;

    public MenuAdminController(Usuario usuarioLogin) {
        labelNameUsuario.setText("Bienvenido Administrador " + usuarioLogin.getNombre() + " !");
    }


    // Para salir de la aplicación
    @FXML
    private void btnSalirClickmenu() {
        Stage stage = (Stage) btnSalir.getScene().getWindow();
        stage.close();
    }
    @FXML
    private void btnGestationUsuariosClick() throws IOException{
        EmpresaAutosABC.setRoot("GestionUsuario");
    }

    @FXML
    private void btnRegistroAdminClick() throws IOException{
        EmpresaAutosABC.setRoot("registrarUsuario");
    }
    @FXML
    private void btnCerrarSesionClick() throws IOException{
        EmpresaAutosABC.setRoot("Login");
    }
    /*
    @FXML
    void btnRegistroAdminClick() throws IOException{
        this.btnRegistroAdmin_MouseClicked();

    }
    @FXML
    private void btnRegistroAdmin_MouseClicked() throws IOException {
        EmpresaAutosABC.setRoot("registrarUsuario");

        // Animación
        // new animatefx.animation.BounceIn(root).play();
    }
    */

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}

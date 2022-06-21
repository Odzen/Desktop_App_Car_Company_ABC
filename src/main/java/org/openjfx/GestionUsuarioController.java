/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package org.openjfx;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
     private void bttnNuevoUsuarioClicked() throws Exception {
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

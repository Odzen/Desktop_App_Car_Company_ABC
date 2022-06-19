/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controlador;

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
public class MenuController implements Initializable {

    
    @FXML
    private Button bttnAdmin, bttnGerente, bttnVendedor, bttnJefeTaller;
    
    @FXML
    private Button bttnInventario, bttnReporte, bttnSedes, bttnAutos;
    
    @FXML
    private Button bttnCotizaciones, bttnFacturacion, bttnOrdenTr, btnSalir;
    
    

    
    
    // Para salir de la aplicación
    @FXML
    private void btnSalirClickmenu() {
    Stage stage = (Stage) btnSalir.getScene().getWindow();
    stage.close();
     }
    
    
    
     @FXML
     private void btnAdmin_MouseClicked() throws Exception {
         
    //Para crear una ventana necesitas un nuevo Stage (Escenario)
    Stage stage = new Stage();
    
    //Cargas el FXML que queres que abra en un Parent
    Parent root = FXMLLoader.load(getClass().getResource("/Vista/GestionUsuario.fxml"));
    
    //Se declara una Scene y se le asigna el FXML (Una Scene es la ventana)
    Scene scene = new Scene(root);
    
    //Establecemos la scena en el Stage
    stage.setScene(scene);
    
    //titulo para la ventana
    stage.setTitle("Gestión de usuario");
   
    // Se muestra
    stage.show();
    
    // Animación
    new animatefx.animation.BounceIn(root).play();
    }
    
    
    
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}

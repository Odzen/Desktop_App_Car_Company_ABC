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



public class GestionUsuarioController implements Initializable {

    @FXML
    private Button bttnNuevoUsuario, bttnVolverInicio;
    
    
    // Para salir de la aplicación
    @FXML
    protected void btnSalirClickGestion() {
    Stage stage = (Stage) bttnVolverInicio.getScene().getWindow();
    stage.close();
     }
   
     @FXML
     private void bttnNuevoUsuario_MouseClicked() throws Exception {
         
    //Para crear una ventana necesitas un nuevo Stage (Escenario)
    Stage stage = new Stage();
    //Cargas el FXML que queres que abra en un Parent
    Parent root = FXMLLoader.load(getClass().getResource("/Vista/registrarUsuario.fxml"));
    //Se declara una Scene y se le asigna el FXML (Una Scene es la ventana)
    Scene scene = new Scene(root);
    //Establecemos la scena en el Stage
    stage.setScene(scene);
    //titulo para la ventana
    stage.setTitle("Registrar de usuario");
    
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

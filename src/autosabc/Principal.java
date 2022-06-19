
package autosabc;


import Controlador.LoginController;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 *
 * @author mavel
 */
public class Principal extends Application {
    
  
    @Override
    public void start(Stage stage) throws IOException  {
        
        System.out.println(getClass());
        
        //Ventana de Login
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/Login.fxml"));
        Parent root = loader.load();
        
        // Escena para JavaFX
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        
        new animatefx.animation.BounceIn(root).play();
                
    }
   
  /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
                
    }    

    


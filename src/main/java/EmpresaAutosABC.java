/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */



import java.io.IOException;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.application.Application;

/**
 *
 * @author mavel
 */
public class EmpresaAutosABC extends Application {

    public void start(Stage stage) throws IOException  {
        
        System.out.println(getClass());
        
        //Ventana de Login
        FXMLLoader loader = new FXMLLoader(getClass().getResource("src/main/java/Vista/Login.fxml"));
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

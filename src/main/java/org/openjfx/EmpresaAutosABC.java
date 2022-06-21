package org.openjfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
/**
 *
 * @author mavel
 */
public class EmpresaAutosABC extends Application {
    private static Scene scene;
    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("Login"));
        stage.setScene(scene);
        stage.show();
    }
    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(EmpresaAutosABC.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}







    /*
    @Override
    public void start(Stage stage) throws Exception  {

        try {
        //Ventana de Login
        FXMLLoader loader = new FXMLLoader(EmpresaAutosABC.class.getResource("/Vista/Login.fxml"));
        Scene scene = new Scene(loader.load());
        // Escena para JavaFX
        stage.setScene(scene);
        stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }


         new animatefx.animation.BounceIn(scene).play();
                
    }*/



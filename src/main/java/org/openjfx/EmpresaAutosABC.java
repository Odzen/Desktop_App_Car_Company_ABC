package org.openjfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.openjfx.Controllers.Controlador_Terminal_SuperAdmin;
import org.openjfx.Models.Usuario.CRUD_Usuario;
import org.openjfx.Models.Usuario.Usuario;

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
        //new animatefx.animation.BounceIn(scene).play();
    }
    public static void setRoot(String fxml) throws IOException {
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
        // Para iniciar controlador super_admin en terminal
        System.out.println("ABC App Inicio Main\n");
        Usuario usr = new Usuario();
        CRUD_Usuario consultasUsuario = new CRUD_Usuario();
        Controlador_Terminal_SuperAdmin ctrl = new Controlador_Terminal_SuperAdmin(usr, consultasUsuario);
        ctrl.iniciar();

        // Para iniciar la vista en JavaFx
        launch(args);
    }

}

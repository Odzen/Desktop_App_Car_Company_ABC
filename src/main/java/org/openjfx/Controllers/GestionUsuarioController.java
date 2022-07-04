package org.openjfx.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.openjfx.EmpresaAutosABC;

public class GestionUsuarioController implements Initializable {

    @FXML
    private Button btnSalir;

    @FXML
    private TableView<?> tableGestionAdmin;

    @FXML
    private TableColumn<?,?> col_idGestionAdmin;

    @FXML
    private TableColumn<?,?> col_nombreGestionAdmin;

    @FXML
    private TableColumn<?,?> col_cargoGestionAdmin;

    @FXML
    private TableColumn col_modificarGestionAdmin;

    @FXML
    private TableColumn<?,?> col_inhabilitarGestionAdmin;
    
    // Para salir de la aplicación
    @FXML
    protected void btnSalirClick() {
    Stage stage = (Stage) btnSalir.getScene().getWindow();
    stage.close();
    }
   
     @FXML
     private void bttnNuevoUsuarioClicked() throws IOException {
         EmpresaAutosABC.setRoot("registrarUsuario");
    // Animación
    //new animatefx.animation.BounceIn(root).play();

    }
    @FXML
    private void btnInicio() throws IOException {
        EmpresaAutosABC.setRoot("menuAdmin");
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    /**
     * Aux Class to Model the Table
     */
    public class ModelTable {
        private String id, nombre, cargo;
        private boolean modificar, inhabilitar;

        public ModelTable(String id, String nombre, String cargo, boolean modificar, boolean inhabilitar) {
            this.id = id;
            this.nombre = nombre;
            this.cargo = cargo;
            this.modificar = modificar;
            this.inhabilitar = inhabilitar;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getCargo() {
            return cargo;
        }

        public void setCargo(String cargo) {
            this.cargo = cargo;
        }

        public boolean isModificar() {
            return modificar;
        }

        public void setModificar(boolean modificar) {
            this.modificar = modificar;
        }

        public boolean isInhabilitar() {
            return inhabilitar;
        }

        public void setInhabilitar(boolean inhabilitar) {
            this.inhabilitar = inhabilitar;
        }
    }
    
}

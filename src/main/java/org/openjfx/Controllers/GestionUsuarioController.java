package org.openjfx.Controllers;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.openjfx.EmpresaAutosABC;
import org.openjfx.Models.Usuario.SQL_Usuario;
import org.openjfx.Models.Usuario.Utils.Rol;

public class GestionUsuarioController implements Initializable {

    @FXML
    private Button btnSalir;

    @FXML
    private TableView<ModelTable> tableGestionAdmin;

    @FXML
    private TableColumn<ModelTable,String> col_idGestionAdmin;

    @FXML
    private TableColumn<ModelTable,String> col_nombreGestionAdmin;

    @FXML
    private TableColumn<ModelTable, String> col_cargoGestionAdmin;

    @FXML
    private TableColumn<ModelTable, Date> col_modificarGestionAdmin;

    @FXML
    private TableColumn<ModelTable, String> editCol;


    @FXML
    private TableColumn<ModelTable, Boolean> col_inhabilitarGestionAdmin;

    private ObservableList<ModelTable> usuariosList = FXCollections.observableArrayList();
    
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

    private void loadDate() {
        col_idGestionAdmin.setCellValueFactory(new PropertyValueFactory<>("cedula"));
        col_nombreGestionAdmin.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        col_cargoGestionAdmin.setCellValueFactory(new PropertyValueFactory<>("cargo"));
        col_modificarGestionAdmin.setCellValueFactory(new PropertyValueFactory<>("modificado"));
        col_inhabilitarGestionAdmin.setCellValueFactory(new PropertyValueFactory<>("activo"));
    }

    private void readUsers() {
        try {
            ResultSet result = SQL_Usuario.obtenerTodosUsuariosPorRol(Rol.ADMIN);
            while (result.next()) {
                usuariosList.add(new ModelTable(result.getString("cedula"), result.getString("nombre"), result.getString("user_type"), result.getDate("modificado"), result.getBoolean("activo")));
            }
        } catch(SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.readUsers();
        this.loadDate();
        tableGestionAdmin.setItems(usuariosList);
    }

    //@FXML
    private void refreshTable() {
        usuariosList.clear();
        this.readUsers();
    }

    //@FXML
    private void print(MouseEvent event) {
    }


    /**
     * Aux Class to Model the Table
     */
    public class ModelTable {
        private String cedula, nombre, cargo;
        private Date modificado;
        private boolean activo;

        public ModelTable(String cedula, String nombre, String cargo, Date modificado, boolean activo) {
            this.cedula = cedula;
            this.nombre = nombre;
            this.cargo = cargo;
            this.modificado = modificado;
            this.activo = activo;
        }

        public String getCedula() {
            return cedula;
        }

        public void setCedula(String id) {
            this.cedula = id;
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

        public Date getModificado() {
            return modificado;
        }

        public void setModificado(Date modificado) {
            this.modificado = modificado;
        }

        public boolean isActivo() {
            return activo;
        }

        public void setActivo(boolean activo) {
            this.activo = activo;
        }
    }
    
}

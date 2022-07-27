package org.openjfx.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.openjfx.EmpresaAutosABC;
import org.openjfx.Models.Automovil.Automovil;
import org.openjfx.Models.Automovil.SQL_Automovil;
import org.openjfx.Models.Cliente.Cliente;
import org.openjfx.Models.Cliente.SQL_Cliente;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

public class ReportesVendedorController  implements Initializable {
    @FXML
    private TableView<Automovil> tableGestionAutomovil;
    @FXML
    private TableColumn<Automovil,String> col_PlacaAuto;
    @FXML
    private TableColumn<Automovil,String> col_marcaAuto;
    @FXML
    private TableColumn<Automovil,Integer> col_cilindrajeAuto;
    @FXML
    private TableColumn<Automovil,String> col_colorAuto;
    @FXML
    private TableColumn<Automovil, String> col_modeloAuto;
    @FXML
    private TableColumn<Automovil, String> col_yearAuto;
    @FXML
    private TableColumn<Automovil, Integer> col_precioAuto;
    @FXML
    private TableColumn<Automovil, Date> col_fecha_modificacion_Auto;
    @FXML
    private TableColumn<Automovil, Date> col_fecha_creacion_Auto;
    @FXML
    private TableColumn<Automovil, Boolean> col_activo_Auto;
    @FXML
    private TableColumn<Automovil, String> col_creadoPorAutomovil;

    @FXML
    private TableColumn<Automovil, String> col_Sede_Auto;

    private ObservableList<Automovil> automovilList = FXCollections.observableArrayList();


    private void refreshTableAuto() {
        automovilList.clear();
        this.readAuto();
    }

    private void loadDataAuto() {
        this.refreshTableAuto();

        col_PlacaAuto.setCellValueFactory(new PropertyValueFactory<>("placa"));
        col_marcaAuto.setCellValueFactory(new PropertyValueFactory<>("marca"));
        col_cilindrajeAuto.setCellValueFactory(new PropertyValueFactory<>("cilindraje"));
        col_colorAuto.setCellValueFactory(new PropertyValueFactory<>("color"));
        col_modeloAuto.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        col_yearAuto.setCellValueFactory(new PropertyValueFactory<>("año"));
        col_precioAuto.setCellValueFactory(new PropertyValueFactory<>("precio"));
        col_activo_Auto.setCellValueFactory(new PropertyValueFactory<>("activo"));
        col_fecha_creacion_Auto.setCellValueFactory(new PropertyValueFactory<>("fecha_creacion"));
        col_fecha_modificacion_Auto.setCellValueFactory(new PropertyValueFactory<>("fecha_modificado"));
        col_creadoPorAutomovil.setCellValueFactory(new PropertyValueFactory<>("cedula_creado_por"));
        col_Sede_Auto.setCellValueFactory(new PropertyValueFactory<>("sede"));

        tableGestionAutomovil.setItems(automovilList.sorted());

    }

    private void readAuto() {
        try {
            ResultSet result = SQL_Automovil.obtenerTodosAutomovilSet();
            while (result.next()) {
                Automovil readAutomovil = new Automovil();

                readAutomovil.setMarca(result.getString("marca"));
                readAutomovil.setPlaca(result.getString("placa"));
                readAutomovil.setCilindraje(result.getInt("cilindraje"));
                readAutomovil.setColor(result.getString("color"));
                readAutomovil.setModelo(result.getString("modelo"));
                readAutomovil.setPrecio(result.getInt("precio"));
                readAutomovil.setAño(result.getString("año"));
                readAutomovil.setActivo(result.getBoolean("activo"));
                readAutomovil.setSede(result.getString("sede"));
                readAutomovil.setFecha_creacion(result.getDate("fecha_creacion"));
                readAutomovil.setFecha_modificado(result.getDate("fecha_modificado"));
                readAutomovil.setCedula_creado_por(result.getString("cedula_creado_por"));

                automovilList.add(readAutomovil);
            }
            automovilList.sorted();
        } catch(SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    @FXML
    protected void btnInicio() throws IOException {
        EmpresaAutosABC.setRoot("menuVendedor");
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        this.readAuto();
        this.loadDataAuto();
        tableGestionAutomovil.setItems(automovilList.sorted());
    }
}


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
import org.openjfx.Models.Venta.SQL_Venta;
import org.openjfx.Models.Venta.Venta;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

public class ReportesJefeController  implements Initializable {
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
    @FXML
    private TableView<Cliente> tableGestionVendedor;
    @FXML
    private TableColumn<Cliente, Boolean> col_activoGestionVendedor;

    @FXML
    private TableColumn<Cliente, String> col_apellidoGestionVendedor;

    @FXML
    private TableColumn<Cliente, String> col_cedula_clienteGestionVendedor;

    @FXML
    private TableColumn<Cliente, String> col_creadoPorGestionVendedor;

    @FXML
    private TableColumn<Cliente, String> col_direccionGestionVendedor;

    @FXML
    private TableColumn<Cliente, String> col_emailGestionVendedor;

    @FXML
    private TableColumn<Cliente, Date> col_fecha_creacionGestionVendedor1;

    @FXML
    private TableColumn<Cliente, Date> col_fecha_modificadoGestionVendedor;

    @FXML
    private TableColumn<Cliente, Integer> col_tipo_usuarioGestionVendedor;

    @FXML
    private TableColumn<Cliente, Date> col_nacimientoGestionVendedor;

    @FXML
    private TableColumn<Cliente, String> col_nombreGestionVendedor;

    @FXML
    private TableColumn<Cliente, String> col_sedeGestionVendedor;

    @FXML
    private TableColumn<Cliente, String> col_telefonoGestionVendedor;


    private ObservableList<Cliente> clientesList = FXCollections.observableArrayList();

    private void refreshTableCliente() {
        clientesList.clear();
        this.readCliente();
    }

    private void loadDataCliente() {
        this.refreshTableCliente();

        col_cedula_clienteGestionVendedor.setCellValueFactory(new PropertyValueFactory<>("cedula_cliente"));
        col_emailGestionVendedor.setCellValueFactory(new PropertyValueFactory<>("email"));
        col_nombreGestionVendedor.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        col_apellidoGestionVendedor.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        col_fecha_modificadoGestionVendedor.setCellValueFactory(new PropertyValueFactory<>("fecha_modificado"));
        col_fecha_creacionGestionVendedor1.setCellValueFactory(new PropertyValueFactory<>("fecha_creacion"));
        col_direccionGestionVendedor.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        col_activoGestionVendedor.setCellValueFactory(new PropertyValueFactory<>("activo"));
        col_nacimientoGestionVendedor.setCellValueFactory(new PropertyValueFactory<>("fecha_nacimiento"));
        col_telefonoGestionVendedor.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        col_tipo_usuarioGestionVendedor.setCellValueFactory(new PropertyValueFactory<>("user_type"));
        col_creadoPorGestionVendedor.setCellValueFactory(new PropertyValueFactory<>("cedula_creado_por"));
        col_sedeGestionVendedor.setCellValueFactory(new PropertyValueFactory<>("sede"));
        tableGestionVendedor.setItems(clientesList.sorted());

    }

    private void readCliente() {
        try {
            ResultSet result = SQL_Cliente.obtenerTodosClienteSet();
            while (result.next()) {
                Cliente readCliente = new Cliente();

                readCliente.setCedula_cliente(result.getString("cedula_cliente"));
                readCliente.setEmail(result.getString("email"));
                readCliente.setNombre(result.getString("nombre"));
                readCliente.setApellido(result.getString("apellido"));
                readCliente.setFecha_modificado(result.getDate("fecha_modificado"));
                readCliente.setFecha_creacion(result.getDate("fecha_creacion"));
                readCliente.setDireccion(result.getString("direccion"));
                readCliente.setActivo(result.getBoolean("activo"));
                readCliente.setFecha_nacimiento(result.getDate("fecha_nacimiento"));
                readCliente.setTelefono(result.getString("telefono"));
                readCliente.setId_tipo_usuario(result.getInt("id_tipo_usuario"));
                readCliente.setCedula_creado_por(result.getString("cedula_creado_por"));
                readCliente.setSede(result.getString("sede"));

                clientesList.add(readCliente);
            }
            clientesList.sorted();
        } catch(SQLException exception) {
            throw new RuntimeException(exception);
        }
    }


    private void refreshTableAuto() {
        clientesList.clear();
        this.readCliente();
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
        EmpresaAutosABC.setRoot("menuJefeTaller");
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.readCliente();
        this.loadDataCliente();
        tableGestionVendedor.setItems(clientesList.sorted());

        this.readAuto();
        this.loadDataAuto();
        tableGestionAutomovil.setItems(automovilList.sorted());
    }
}


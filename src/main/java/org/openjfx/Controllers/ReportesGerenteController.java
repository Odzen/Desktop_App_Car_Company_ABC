package org.openjfx.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.openjfx.EmpresaAutosABC;
import org.openjfx.Models.Venta.SQL_Venta;
import org.openjfx.Models.Venta.Venta;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

public class ReportesGerenteController  implements Initializable {

    @FXML
    private TableView<Venta> tablaVenta;
    @FXML
    private TableColumn<Venta, String> col_id_venta;
    @FXML
    private TableColumn<Venta,String> col_Id_Cliente_Venta;
    @FXML
    private TableColumn<Venta,String> col_Id_Vendedor_Venta;
    @FXML
    private TableColumn<Venta,String> col_placa_Venta;
    @FXML
    private TableColumn<Venta,Double> col_Descripcion_Venta;
    @FXML
    private TableColumn<Venta, Double> col_iva_Venta;
    @FXML
    private TableColumn<Venta, Double> col_total_sin_iva_Venta;
    @FXML
    private TableColumn<Venta, String> col_total_iva_Venta;
    @FXML
    private TableColumn<Venta, Date> col_fecha_modificacion_Venta;
    @FXML
    private TableColumn<Venta, Date> col_fecha_creacion_Venta;
    @FXML
    private TableColumn<Venta, Integer> col_id_orden_trabajo_Venta;
    @FXML
    private TableColumn<Venta, String> col_sede_Venta;

    private ObservableList<Venta> ventasList = FXCollections.observableArrayList();

    private void refreshTable() {
        ventasList.clear();
        this.readVenta();
    }

    private void loadData() {
        this.refreshTable();

        col_id_venta.setCellValueFactory(new PropertyValueFactory<>("id_venta"));
        col_Id_Cliente_Venta.setCellValueFactory(new PropertyValueFactory<>("cedula_cliente"));
        col_Id_Vendedor_Venta.setCellValueFactory(new PropertyValueFactory<>("cedula_vendedor"));
        col_placa_Venta.setCellValueFactory(new PropertyValueFactory<>("placa_automovil"));
        col_Descripcion_Venta.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        col_iva_Venta.setCellValueFactory(new PropertyValueFactory<>("iva"));
        col_total_sin_iva_Venta.setCellValueFactory(new PropertyValueFactory<>("total_sin_iva"));
        col_total_iva_Venta.setCellValueFactory(new PropertyValueFactory<>("total_iva"));
        col_fecha_modificacion_Venta.setCellValueFactory(new PropertyValueFactory<>("fecha_modificado"));
        col_fecha_creacion_Venta.setCellValueFactory(new PropertyValueFactory<>("fecha_creacion"));
        col_id_orden_trabajo_Venta.setCellValueFactory(new PropertyValueFactory<>("id_orden_trabajo"));
        col_sede_Venta.setCellValueFactory(new PropertyValueFactory<>("sede"));
        tablaVenta.setItems(ventasList.sorted());

    }

    private void readVenta() {
        try {
            ResultSet result = SQL_Venta.obtenerTodasVentasSet();
            while (result.next()) {
                Venta readVenta = new Venta();

                readVenta.setId_venta(result.getInt("id_venta"));
                readVenta.setCedula_cliente(result.getString("cedula_cliente"));
                readVenta.setCedula_vendedor(result.getString("cedula_vendedor"));
                readVenta.setTotal_sin_iva(result.getDouble("total_sin_iva"));
                readVenta.setFecha_modificado(result.getDate("fecha_modificado"));
                readVenta.setFecha_creacion(result.getDate("fecha_creacion"));
                readVenta.setIva(result.getDouble("iva"));
                readVenta.setDescripcion(result.getString("descripcion"));
                readVenta.setTotal_iva(result.getDouble("total_iva"));
                readVenta.setPlaca_automovil(result.getString("placa_automovil"));
                readVenta.setId_orden_trabajo(result.getInt("id_orden_trabajo"));
                readVenta.setSede(result.getString("sede"));

                ventasList.add(readVenta);
            }
            ventasList.sorted();
        } catch(SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    @FXML
    protected void btnInicio() throws IOException {
        EmpresaAutosABC.setRoot("menuGerente");
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.readVenta();
        this.loadData();
        tablaVenta.setItems(ventasList.sorted());
    }
}

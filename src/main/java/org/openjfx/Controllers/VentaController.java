package org.openjfx.Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

import GlobalUtils.Dialogs;
import animatefx.animation.FadeIn;
import animatefx.animation.Shake;
import animatefx.animation.Tada;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.openjfx.EmpresaAutosABC;
import org.openjfx.Models.Automovil.SQL_Automovil;
import org.openjfx.Models.Cotizacion.SQL_Cotizacion;
import org.openjfx.Models.Venta.SQL_Venta;
import org.openjfx.Models.Venta.Utils.ValidacionesVenta;
import org.openjfx.Models.Venta.Venta;


import javax.swing.*;

public class VentaController implements Initializable {

    // Variables para Actualizar, Leer y Borrar Usuarios
    @FXML
    private Button btnSalir;
    @FXML
    private TableView<Venta> tablaVenta;
    @FXML
    private TableColumn<Venta,String> col_Id_Cliente_Venta;
    @FXML
    private TableColumn<Venta,String> col_Id_Vendedor_Venta;
    @FXML
    private TableColumn<Venta,String> col_placa_Venta;
    @FXML
    private TableColumn<Venta,Integer> col_iva_Venta;
    @FXML
    private TableColumn<Venta, Integer> col_total_sin_iva_Venta;
    @FXML
    private TableColumn<Venta, Integer> col_total_iva_Venta;


    @FXML
    private TableColumn<Venta, String> col_Descripcion_Venta;
    @FXML
    private TableColumn<Venta, Date> col_fecha_modificacion_Venta;

    @FXML
    private TableColumn<Venta, Date> col_fecha_creacion_Venta;
    @FXML
    private TableColumn<Venta, Integer> col_id_orden_trabajo_Venta;

    @FXML
    private TableColumn<Venta,String> col_sede_Venta;



    private ObservableList<Venta> ventaList = FXCollections.observableArrayList();

    private Venta venta = null;

    // Variables para registrar cotizacion
    private String mensajeExito = String.format("-fx-text-fill: GREEN;");
    private String estiloMensajeExito = String.format("-fx-border-color: #A9A9A9; -fx-border-width: 2; -fx-border-radius: 5;");

    private String mensaje = String.format("-fx-text-fill: black;");
    private String mensajeError = String.format("-fx-text-fill: RED;");
    private String estiloMensajeError = String.format("-fx-border-color: RED; -fx-border-width: 2; -fx-border-radius: 5;");
    @FXML
    private TextField txtDocumentoCliente;
    @FXML
    private TextField txtPlacaVenta;
    @FXML
    private TextField txtDescripcionVenta;
    @FXML
    private TextField txtid_orden_trabajo;

    @FXML
    MenuItem firstItem;
    @FXML
    MenuItem secondItem;

    @FXML
    private Label validacionRegistroLabel;

    /**
     * CREATE - Registrar Venta
     * @throws IOException
     */
    private void crearActualizarVenta(boolean crear) {

        validacionRegistroLabel.setText("");
        txtDocumentoCliente.setStyle(null);
        txtDescripcionVenta.setStyle(null);
        txtPlacaVenta.setStyle(null);
        txtid_orden_trabajo.setStyle(null);
        // Cuando los campos están en blanco
        if(txtDocumentoCliente.getText().isEmpty() ||
                txtPlacaVenta.getText().isEmpty()|| txtid_orden_trabajo.getText().isEmpty() )
        {
            validacionRegistroLabel.setStyle(mensajeError);
            if(txtDocumentoCliente.getText().isEmpty() && txtPlacaVenta.getText().isEmpty() )
            {
                validacionRegistroLabel.setText("Algunos campos están vacíos!");
                txtDocumentoCliente.setStyle(estiloMensajeError);
                txtDescripcionVenta.setStyle(estiloMensajeError);
                new Shake(txtDocumentoCliente).play();
                new Shake(txtPlacaVenta).play();
            }

        } else {
            boolean validado = this.validacionesVenta(crear);
            if (validado) {
                this.guardarActualizarVenta(crear);
                this.refreshTable();
            }
        }
    }

    @FXML
    protected void btnNuevaVentaClicked() throws IOException{
        this.crearActualizarVenta(true);
    }


    private boolean validacionesVenta(boolean crear) {
        boolean validado = true;
        validacionRegistroLabel.setText("");

        // Validación Cédula cliente
        if (!ValidacionesVenta.validarCedula(txtDocumentoCliente.getText()))
        {
            validado = false;
            String textoError = "Formato de la cédula incorrecto!";
            validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
            validacionRegistroLabel.setStyle(mensajeError);
            txtDocumentoCliente.setStyle(estiloMensajeError);
            new FadeIn(txtDocumentoCliente).play();
        }
        else if (SQL_Cotizacion.existeCotizacion_cedula_Placa_orden(txtDocumentoCliente.getText(), txtPlacaVenta.getText(),Integer.parseInt(txtid_orden_trabajo.getText()))) {
            // Validacion para saber si el cliente con esa cédula ya existe
            validado = false;
            String textoError = "Un usuario con ese número de cédula ya existe!";
            validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
            validacionRegistroLabel.setStyle(mensajeError);
            txtDocumentoCliente.setStyle(estiloMensajeError);
            new FadeIn(txtDocumentoCliente).play();

        }
        // Validación placa
        if (!ValidacionesVenta.validarPlaca(txtPlacaVenta.getText()))
        {
            validado = false;
            String textoError = "Formato de email incorrecto!";
            System.out.println(textoError);
            validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
            validacionRegistroLabel.setStyle(mensajeError);
            txtPlacaVenta.setStyle(estiloMensajeError);
            new FadeIn(txtPlacaVenta).play();
        }

        // Mensaje si el ingreso es correcto
        return validado;
    }

    private void validadoLabelSet() {
        validacionRegistroLabel.setText("");
        System.out.println("Pasó Validaciones");
        validacionRegistroLabel.setStyle(mensajeExito);
        validacionRegistroLabel.setText("Operación Exitosa!");
        txtDocumentoCliente.setStyle(estiloMensajeExito);
        new Tada(validacionRegistroLabel).play();
    }

    public void guardarActualizarVenta(boolean crear) {
        try {
            Venta ventaModelo = new Venta();


            ventaModelo.setCedula_vendedor(txtDocumentoCliente.getText());

            //Traer cedula vendedor
            ventaModelo.setCedula_vendedor(LoginController.obtenerUsuarioLogeado().getCedula());

            ventaModelo.setDescripcion(txtDescripcionVenta.getText());
            ventaModelo.setPlaca_automovil(txtPlacaVenta.getText());
            ventaModelo.setid_orden_trabajo(Integer.parseInt(txtid_orden_trabajo.getText()));

           //Traer sede vendedor
            ventaModelo.setCedula_vendedor(LoginController.obtenerUsuarioLogeado().getSede());

            //Traer el precio del automovil
            String placa = txtPlacaVenta.getText();
            ResultSet result = SQL_Automovil.obtenerAutomovil_placa(placa);
            int precio_sin_iva = result.getInt("precio");
            result.next();

            ventaModelo.setTOTAL_SIN_IVA(precio_sin_iva);

            int iva = precio_sin_iva * (19/100);
            ventaModelo.setIVA(iva);

            int precio_total = precio_sin_iva + iva;
            ventaModelo.setTOTAL_IVA(precio_total);

            // SI la orden es para crear, o para actualizar, llamo al metodo respectivo
            if (crear)
                SQL_Venta.crearVenta(ventaModelo);
            else
                SQL_Venta.editarVenta(ventaModelo.getCedula_cliente(), ventaModelo.getPlaca_automovil(),
                        ventaModelo.getid_orden_trabajo(), ventaModelo);

            this.validadoLabelSet();
            this.limpiar();

        } catch (Exception e) {
            System.err.println(e);
            JOptionPane.showMessageDialog(null,"Error registrando la venta");
        }
    }

    public void limpiar() {
        txtDocumentoCliente.setText("");
        txtPlacaVenta.setText("");
        txtDescripcionVenta.setText("");
        txtid_orden_trabajo.setText("");
    }

    /**
     * UPDATE - READ - DELETE
     */
    private void loadData() {
        refreshTable();

        col_Id_Cliente_Venta.setCellValueFactory(new PropertyValueFactory<>("cedula_cliente"));
        col_Id_Vendedor_Venta.setCellValueFactory(new PropertyValueFactory<>("cedula_vendedor"));
        col_Descripcion_Venta.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        col_fecha_modificacion_Venta.setCellValueFactory(new PropertyValueFactory<>("fecha_modificado"));
        col_fecha_creacion_Venta.setCellValueFactory(new PropertyValueFactory<>("fecha_creacion"));
        col_placa_Venta.setCellValueFactory(new PropertyValueFactory<>("placa_automovil"));
        col_id_orden_trabajo_Venta.setCellValueFactory(new PropertyValueFactory<>("id_orden_trabajo"));
        col_iva_Venta.setCellValueFactory(new PropertyValueFactory<>("IVA"));
        col_total_sin_iva_Venta.setCellValueFactory(new PropertyValueFactory<>("TOTAL_SIN_IVA"));
        col_total_iva_Venta.setCellValueFactory(new PropertyValueFactory<>("TOTAL_IVA"));
        col_sede_Venta.setCellValueFactory(new PropertyValueFactory<>("sede"));
        tablaVenta.setItems(ventaList.sorted());

    }

    private void readVenta() {
        try {
            ResultSet result = SQL_Venta.obtenerTodasVentaSet();
            while (result.next()) {
                Venta readVenta = new Venta();


                readVenta.setCedula_cliente(result.getString("cedula_cliente"));
                readVenta.setCedula_vendedor(result.getString("cedula_vendedor"));
                readVenta.setTOTAL_SIN_IVA(result.getInt("TOTAL_SIN_IVA"));
                readVenta.setFecha_modificado(result.getDate("fecha_modificado"));
                readVenta.setFecha_creacion(result.getDate("fecha_creacion"));
                readVenta.setIVA(result.getInt("IVA"));
                readVenta.setTOTAL_IVA(result.getInt("TOTAL_IVA"));
                readVenta.setPlaca_automovil(result.getString("placa_automovil"));
                readVenta.setid_orden_trabajo(result.getInt("id_orden_trabajo"));
                readVenta.setSede(result.getString("sede"));

                ventaList.add(readVenta);
            }
            ventaList.sorted();
        } catch(SQLException exception) {
            throw new RuntimeException(exception);
        }
    }


    //@FXML
    private void refreshTable() {
        ventaList.clear();
        this.readVenta();
    }

    /**
     * Botones
     * @throws IOException
     */
    @FXML
    protected void btnCancelarClick() throws IOException {
        if (Dialogs.showConfirm("Seleccione una opción", "¿Está seguro que quiere cancelar el registro?",
                Dialogs.YES, Dialogs.NO).equals(Dialogs.YES)) {
            EmpresaAutosABC.setRoot("menuVendedor");
        }
    }
    @FXML
    protected void btnInicio() throws IOException {
        EmpresaAutosABC.setRoot("menuVendedor");
    }
    // Para salir de la aplicación
    @FXML
    protected void btnSalirClick() {
        if (Dialogs.showConfirm("Seleccione una opción", "¿Está seguro que quiere salir de la aplicación?",
                Dialogs.YES, Dialogs.NO).equals(Dialogs.YES)) {
            Stage stage = (Stage) btnSalir.getScene().getWindow();
            stage.close();
        }
    }
    @FXML
    protected void btnLimpiar() {
        this.limpiar();
    }

    // Buscar por cedula para llenar campos y registrar o borrar
    @FXML
    protected void btnBuscarCedula() {
        validacionRegistroLabel.setText("");
        txtDocumentoCliente.setStyle(null);
        txtDescripcionVenta.setStyle(null);
        txtPlacaVenta.setStyle(null);
        txtid_orden_trabajo.setStyle(null);
        if(txtDocumentoCliente.getText().isEmpty() || txtPlacaVenta.getText().isEmpty())
        {
            validacionRegistroLabel.setStyle(mensajeError);
            new Shake(txtDocumentoCliente).play();
            new Shake(txtPlacaVenta).play();
            validacionRegistroLabel.setText("La cédula del cliente o la placa están vacias!");
        } else {
            boolean validado = this.validacionCedula();
            if (validado) {
                this.llenarCamposPorCedula();
            }
        }
    }

    private boolean validacionCedula() {
        // Validación Cédula
        boolean validado = true;
        validacionRegistroLabel.setText("");
        if (!ValidacionesVenta.validarCedula(txtDocumentoCliente.getText())) {
            validado = false;
            String textoError = "Formato de la cédula incorrecto!";
            validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
            validacionRegistroLabel.setStyle(mensajeError);
            txtDocumentoCliente.setStyle(estiloMensajeError);
            new FadeIn(txtDocumentoCliente).play();
        }

        if (!SQL_Venta.existeVenta_cedula_cliente(txtDocumentoCliente.getText())) {
            // Validacion para saber si el usuario con esa cédula ya existe
            validado = false;
            String textoError = "Una venta con ese número de cédula NO existe!";
            validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
            validacionRegistroLabel.setStyle(mensajeError);
            txtDocumentoCliente.setStyle(estiloMensajeError);
            new FadeIn(txtDocumentoCliente).play();
        }
        return validado;
    }

    private void llenarCamposPorCedula() {
        String cedula_cliente = txtDocumentoCliente.getText();
        String placa_automovil = txtPlacaVenta.getText();
        int id_orden_trabajo = Integer.parseInt(txtid_orden_trabajo.getText());
        try {
            ResultSet result = SQL_Venta.obtenerVenta_Cedula_Placa_Orden(cedula_cliente, placa_automovil, id_orden_trabajo);
            while (result.next()) {
                Venta readVenta = new Venta();

                readVenta.setCedula_vendedor(result.getString("cedula_vendedor"));
                readVenta.setTOTAL_SIN_IVA(result.getInt("TOTAL_SIN_IVA"));
                readVenta.setTOTAL_IVA(result.getInt("TOTAL_IVA"));
                readVenta.setIVA(result.getInt("IVA"));
                readVenta.setFecha_creacion(result.getDate("fecha_creacion"));
                readVenta.setFecha_modificado(result.getDate("fecha_modificado"));
                readVenta.setid_orden_trabajo(result.getInt("id_orden_trabajo"));
                readVenta.setSede(result.getString("sede"));

                // Cambio valores en los labels
                txtDocumentoCliente.setText(readVenta.getCedula_cliente());
                txtDescripcionVenta.setText(readVenta.getDescripcion());
                txtPlacaVenta.setText(readVenta.getPlaca_automovil());

            }
        } catch(SQLException exception) {
            throw new RuntimeException(exception);
        }

    }

    // Borrar - poner inactivo
    @FXML
    private void btnBorrarClicked() {
       // String placa = txtPlacaVenta.getText();
        String cedula_cliente= txtDocumentoCliente.getText();

        if (SQL_Venta.existeVenta_cedula_cliente(cedula_cliente)) {
            try {
                ResultSet result = SQL_Venta.obtenerVenta_Cedula_Placa_Orden(txtDocumentoCliente.getText(),
                        txtPlacaVenta.getText(), Integer.parseInt(txtid_orden_trabajo.getText()));
                result.next();
               // boolean activo = result.getBoolean("activo");
                //SQL_Venta.cambiarEstadoVentaPorPlaca(placa, activo);
                this.validadoLabelSet();
                this.limpiar();

            } catch (SQLException exception) {
                throw new RuntimeException(exception);
            }
        }
        else {
            String textoError = "No existe un automovil con esa placa!";
            validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
            validacionRegistroLabel.setStyle(mensajeError);
            txtPlacaVenta.setStyle(estiloMensajeError);
            new FadeIn(txtPlacaVenta).play();
        }
        this.refreshTable();

    }





    // Actualizar
    @FXML
    private void btnActualizarClicked() {
        this.crearActualizarVenta(false);
    }

    @FXML
    private void btnImprimirpdf() {
        // TODO export to pdf or csv
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.readVenta();
        this.loadData();
        tablaVenta.setItems(ventaList.sorted());
    }

}


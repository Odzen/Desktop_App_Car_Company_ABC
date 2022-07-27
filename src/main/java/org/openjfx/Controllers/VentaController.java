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
import org.openjfx.EmpresaAutosABC;
import org.openjfx.Models.Automovil.SQL_Automovil;
import org.openjfx.Models.Cliente.SQL_Cliente;
import org.openjfx.Models.Orden_Trabajo.SQL_Orden;
import org.openjfx.Models.Venta.SQL_Venta;
import org.openjfx.Models.Venta.Utils.ValidacionesVenta;
import org.openjfx.Models.Venta.Venta;


public class VentaController implements Initializable {

    // Variables para Actualizar, Leer y Borrar Usuarios
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

    // Variables para registrar venta
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
    private TextField txtid_orden_trabajo;
    @FXML
    private TextField txtDescripcionVenta;
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
        if(txtDocumentoCliente.getText().isEmpty() || txtid_orden_trabajo.getText().isEmpty() || txtPlacaVenta.getText().isEmpty())
        {
            validacionRegistroLabel.setStyle(mensajeError);
            if(txtDocumentoCliente.getText().isEmpty())
            {
                validacionRegistroLabel.setText("Se tiene que seleccionar una cédula de un cliente!");
                txtDocumentoCliente.setStyle(estiloMensajeError);
                new Shake(txtDocumentoCliente).play();
                return;
            }
            else if(txtid_orden_trabajo.getText().isEmpty() && txtPlacaVenta.getText().isEmpty() )
            {
                validacionRegistroLabel.setText("Se tiene que seleccionar una placa o una orden de trabajo!");
                txtPlacaVenta.setStyle(estiloMensajeError);
                txtid_orden_trabajo.setStyle(estiloMensajeError);
                new Shake(txtid_orden_trabajo).play();
                new Shake(txtPlacaVenta).play();
                return;
            }
            else {
                boolean validado = this.validacionesVenta(crear);
                if (validado) {
                    this.guardarActualizarVenta(crear);
                    this.refreshTable();
                }
            }
        }
        else {
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

    private boolean checkPlacaOrden()
    {
        if(!txtid_orden_trabajo.getText().isEmpty() && txtPlacaVenta.getText().isEmpty())
        {
            return true;
        }
        else if(txtid_orden_trabajo.getText().isEmpty() && !txtPlacaVenta.getText().isEmpty())
        {
            return true;
        }
        else {
            return false;
        }
    }

    private boolean validacionesVenta(boolean crear) {
        boolean validado = true;
        validacionRegistroLabel.setText("");
        // Validación campos vacíos
        if(!txtPlacaVenta.getText().isEmpty() && !txtid_orden_trabajo.getText().isEmpty())
        {
            validado = false;
            String textoError = "Se debe de escoger o placa u orden. NO las dos!";
            validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
            validacionRegistroLabel.setStyle(mensajeError);
            txtPlacaVenta.setStyle(estiloMensajeError);
            new FadeIn(txtPlacaVenta).play();
            txtid_orden_trabajo.setStyle(estiloMensajeError);
            new FadeIn(txtid_orden_trabajo).play();
        }

        if (!ValidacionesVenta.validarCedula(txtDocumentoCliente.getText()))
        {
            validado = false;
            String textoError = "Formato de la cédula incorrecto!";
            validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
            validacionRegistroLabel.setStyle(mensajeError);
            txtDocumentoCliente.setStyle(estiloMensajeError);
            new FadeIn(txtDocumentoCliente).play();

        }
        // Valida si existe un cliente con esa cédula
        else if(!SQL_Cliente.existeCliente_Cedula(txtDocumentoCliente.getText()))
        {
            validado = false;
            String textoError = "No existe un cliente con esa cédula!";
            validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
            validacionRegistroLabel.setStyle(mensajeError);
            txtDocumentoCliente.setStyle(estiloMensajeError);
            new FadeIn(txtDocumentoCliente).play();
        }
        // Valida si el cliente está activo
        else if(!SQL_Cliente.isActivoCliente_Cedula(txtDocumentoCliente.getText()))
        {
            validado = false;
            String textoError = "El cliente con está inactivo!";
            validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
            validacionRegistroLabel.setStyle(mensajeError);
            txtDocumentoCliente.setStyle(estiloMensajeError);
            new FadeIn(txtDocumentoCliente).play();
        }
        // Validación placa
        if(!txtPlacaVenta.getText().isEmpty())
        {
            if (!ValidacionesVenta.validarPlaca(txtPlacaVenta.getText()) && this.checkPlacaOrden())
            {
                validado = false;
                String textoError = "Formato de placa incorrecto!";
                System.out.println(textoError);
                validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
                validacionRegistroLabel.setStyle(mensajeError);
                txtPlacaVenta.setStyle(estiloMensajeError);
                new FadeIn(txtPlacaVenta).play();
            }
            // Valida si existe un carro con esa placa
            else if(!SQL_Automovil.existeautomovil_placa(txtPlacaVenta.getText()))
            {
                validado = false;
                String textoError = "No existe un automóvil con esa placa!";
                validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
                validacionRegistroLabel.setStyle(mensajeError);
                txtPlacaVenta.setStyle(estiloMensajeError);
                new FadeIn(txtPlacaVenta).play();
            // Valida si el carro está activo
            } else if (!SQL_Automovil.isActivoAutomovil_placa(txtPlacaVenta.getText())) {
                validado = false;
                String textoError = "El automovil está inactivo!";
                validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
                validacionRegistroLabel.setStyle(mensajeError);
                txtPlacaVenta.setStyle(estiloMensajeError);
                new FadeIn(txtPlacaVenta).play();

            } else if (SQL_Venta.existeVenta_cedula_Placa(txtDocumentoCliente.getText(), txtPlacaVenta.getText())&& crear) {
                // Validacion para saber si ya existe un registro con esa cedula y esa placa
                validado = false;
                String textoError = "Una venta con ese número de cédula de cliente y placa ya existe!";
                validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
                validacionRegistroLabel.setStyle(mensajeError);
                txtDocumentoCliente.setStyle(estiloMensajeError);
                txtPlacaVenta.setStyle(estiloMensajeError);
                new FadeIn(txtDocumentoCliente).play();
                new FadeIn(txtPlacaVenta).play();

            }
        }
        // Validación orden de trabajo
        if(!txtid_orden_trabajo.getText().isEmpty())
        {
            if(!ValidacionesVenta.validarIdOrden(txtid_orden_trabajo.getText()) && this.checkPlacaOrden())
            {
                validado = false;
                String textoError = "Formato del id de la orden incorrecto!";
                validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
                validacionRegistroLabel.setStyle(mensajeError);
                txtid_orden_trabajo.setStyle(estiloMensajeError);
                new FadeIn(txtid_orden_trabajo).play();
            }
            else if(!SQL_Orden.existeOrden_Id(Integer.parseInt(txtid_orden_trabajo.getText())))
            {
                validado = false;
                String textoError = "No existe una orden con ese Id!";
                validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
                validacionRegistroLabel.setStyle(mensajeError);
                txtid_orden_trabajo.setStyle(estiloMensajeError);
                new FadeIn(txtid_orden_trabajo).play();
            }
            // Verifica si está activa la orden
            else if(!SQL_Orden.isActivoOrden_Id(Integer.parseInt(txtid_orden_trabajo.getText())))
            {
                validado = false;
                String textoError = "La orden está inactiva!";
                validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
                validacionRegistroLabel.setStyle(mensajeError);
                txtid_orden_trabajo.setStyle(estiloMensajeError);
                new FadeIn(txtid_orden_trabajo).play();
            }
            else if (SQL_Venta.existeVenta_cedula_Orden(txtDocumentoCliente.getText(), Integer.parseInt(txtid_orden_trabajo.getText()))&& crear) {
                // Validacion para saber si ya existe un registro con esa cedula y esa placa
                validado = false;
                String textoError = "Una venta con ese número de cédula de cliente y ese id de orden ya existe!";
                validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
                validacionRegistroLabel.setStyle(mensajeError);
                txtDocumentoCliente.setStyle(estiloMensajeError);
                txtid_orden_trabajo.setStyle(estiloMensajeError);
                new FadeIn(txtDocumentoCliente).play();
                new FadeIn(txtid_orden_trabajo).play();

            }
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

            ventaModelo.setCedula_cliente(txtDocumentoCliente.getText());

            //Traer cedula vendedor
            ventaModelo.setCedula_vendedor(LoginController.obtenerUsuarioLogeado().getCedula());
            //Traer sede vendedor
            ventaModelo.setSede(LoginController.obtenerUsuarioLogeado().getSede());

            ventaModelo.setDescripcion(txtDescripcionVenta.getText());

            if(!txtPlacaVenta.getText().isEmpty())
            {
                ventaModelo.setPlaca_automovil(txtPlacaVenta.getText());
                //Traer el precio del automovil
                String placa = txtPlacaVenta.getText();
                ResultSet result = SQL_Automovil.obtenerAutomovil_placa(placa);
                result.next();
                double precio_sin_iva = result.getDouble("precio");
                ventaModelo.setTotal_sin_iva(precio_sin_iva);
                double iva = precio_sin_iva * 0.19;
                ventaModelo.setIva(iva);
                double precio_total = precio_sin_iva + iva;
                ventaModelo.setTotal_iva(precio_total);

                System.out.println("Precio sin Iva: " + precio_sin_iva );
                System.out.println("Iva: " + iva );
                System.out.println("Precio con Iva: " + precio_total );
            }

            if(!txtid_orden_trabajo.getText().isEmpty())
            {
                ventaModelo.setId_orden_trabajo(Integer.parseInt(txtid_orden_trabajo.getText()));
            }

            // SI la orden es para crear, o para actualizar, llamo al metodo respectivo
            if (crear)
            {
                SQL_Venta.crearVenta(ventaModelo);
            }
            else
            {
                SQL_Venta.editarVenta_cedula_placa_orden(ventaModelo.getCedula_cliente(), ventaModelo.getPlaca_automovil(), ventaModelo.getId_orden_trabajo(),  ventaModelo);
            }


            this.validadoLabelSet();
            this.limpiar();

        } catch (Exception e) {
            System.err.println(e);
            Dialogs.showError("Error en la base de datos", "Error registrando la venta");

        }
    }

    public void limpiar() {

        txtDocumentoCliente.setStyle(null);
        txtDescripcionVenta.setStyle(null);
        txtPlacaVenta.setStyle(null);
        txtid_orden_trabajo.setStyle(null);
        txtDocumentoCliente.setText("");
        txtDescripcionVenta.setText("");
        txtPlacaVenta.setText("");
        txtid_orden_trabajo.setText("");
    }

    /**
     * UPDATE - READ - DELETE
     */
    private void loadData() {
        refreshTable();

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


    //@FXML
    private void refreshTable() {
        ventasList.clear();
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
    @FXML
    protected void btnLimpiar() {
        this.limpiar();
    }

    // Buscar por cedula para llenar campos y registrar o borrar
    @FXML
    protected void btnBuscarVenta() {
        validacionRegistroLabel.setText("");
        txtDocumentoCliente.setStyle(null);
        txtDescripcionVenta.setStyle(null);
        txtPlacaVenta.setStyle(null);
        txtid_orden_trabajo.setStyle(null);
        if(txtDocumentoCliente.getText().isEmpty())
        {
            validacionRegistroLabel.setStyle(mensajeError);
            new Shake(txtDocumentoCliente).play();
            validacionRegistroLabel.setText("La cédula del cliente está vacía!");
        }  else {
            boolean validado = this.validacionCedulaPlacaOrden();
            if (validado) {
                this.llenarCamposPorCedulaOrdenPlaca();
            }
        }
    }

    private boolean validacionCedulaPlacaOrden() {
        // Validación Cédula
        boolean validado = true;
        validacionRegistroLabel.setText("");
        // Validación campos vacíos
        if(!txtPlacaVenta.getText().isEmpty() && !txtid_orden_trabajo.getText().isEmpty())
        {
            validado = false;
            String textoError = "Se debe de escoger o placa u orden. NO las dos!";
            validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
            validacionRegistroLabel.setStyle(mensajeError);
            txtPlacaVenta.setStyle(estiloMensajeError);
            new FadeIn(txtPlacaVenta).play();
            txtid_orden_trabajo.setStyle(estiloMensajeError);
            new FadeIn(txtid_orden_trabajo).play();
        }
        if (!ValidacionesVenta.validarCedula(txtDocumentoCliente.getText())) {
            validado = false;
            String textoError = "Formato de la cédula incorrecto!";
            validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
            validacionRegistroLabel.setStyle(mensajeError);
            txtDocumentoCliente.setStyle(estiloMensajeError);
            new FadeIn(txtDocumentoCliente).play();
        }
        // Validación placa
        if(!txtPlacaVenta.getText().isEmpty())
        {
            if (!ValidacionesVenta.validarPlaca(txtPlacaVenta.getText()) && this.checkPlacaOrden())
            {
                validado = false;
                String textoError = "Formato de placa incorrecto!";
                System.out.println(textoError);
                validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
                validacionRegistroLabel.setStyle(mensajeError);
                txtPlacaVenta.setStyle(estiloMensajeError);
                new FadeIn(txtPlacaVenta).play();
            }
            else if (!SQL_Venta.existeVenta_cedula_Placa(txtDocumentoCliente.getText(), txtPlacaVenta.getText())) {
                // Validacion para saber si ya existe un registro con esa cedula y esa placa
                validado = false;
                String textoError = "Una venta con ese número de cédula de cliente y placa NO existe!";
                validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
                validacionRegistroLabel.setStyle(mensajeError);
                txtDocumentoCliente.setStyle(estiloMensajeError);
                txtPlacaVenta.setStyle(estiloMensajeError);
                new FadeIn(txtDocumentoCliente).play();
                new FadeIn(txtPlacaVenta).play();

            }
        }
        // Validación orden de trabajo
        if(!txtid_orden_trabajo.getText().isEmpty())
        {
            if(!ValidacionesVenta.validarIdOrden(txtid_orden_trabajo.getText()))
            {
                validado = false;
                String textoError = "Formato del id de la orden incorrecto!";
                validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
                validacionRegistroLabel.setStyle(mensajeError);
                txtid_orden_trabajo.setStyle(estiloMensajeError);
                new FadeIn(txtid_orden_trabajo).play();
            }
            else if (!SQL_Venta.existeVenta_cedula_Orden(txtDocumentoCliente.getText(), Integer.parseInt(txtid_orden_trabajo.getText()))
            ) {
                // Validacion para saber si ya existe un registro con esa cedula y ese id de orden
                validado = false;
                String textoError = "Una venta con ese número de cédula de cliente y ese id de orden NO existe!";
                validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
                validacionRegistroLabel.setStyle(mensajeError);
                txtDocumentoCliente.setStyle(estiloMensajeError);
                txtid_orden_trabajo.setStyle(estiloMensajeError);
                new FadeIn(txtDocumentoCliente).play();
                new FadeIn(txtid_orden_trabajo).play();

            }
        }
        if (!SQL_Venta.existeVenta_cedula_Placa(txtDocumentoCliente.getText(), txtPlacaVenta.getText())) {
            // Validacion para saber si el registro ya existe
            validado = false;
            String textoError = "No existe una venta con esos datos!";
            validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
            validacionRegistroLabel.setStyle(mensajeError);
            txtDocumentoCliente.setStyle(estiloMensajeError);
            new FadeIn(txtDocumentoCliente).play();
            txtPlacaVenta.setStyle(estiloMensajeError);
            new FadeIn(txtPlacaVenta).play();
            txtid_orden_trabajo.setStyle(estiloMensajeError);
            new FadeIn(txtid_orden_trabajo).play();

        }
        return validado;
    }

    private void llenarCamposPorCedulaOrdenPlaca() {
        String cedula_cliente = txtDocumentoCliente.getText();
        String placa_automovil = txtPlacaVenta.getText();
        int id_orden_trabajo = 0;
        if(!txtid_orden_trabajo.getText().isEmpty())
            id_orden_trabajo = Integer.parseInt(txtid_orden_trabajo.getText());

        try {
            ResultSet result = SQL_Venta.obtenerVenta_Cedula_Placa_Orden(cedula_cliente, placa_automovil, id_orden_trabajo);

            while (result.next()) {
                Venta readVenta = new Venta();

                readVenta.setCedula_vendedor(result.getString("cedula_vendedor"));
                readVenta.setCedula_cliente(result.getString("cedula_cliente"));
                readVenta.setTotal_iva(result.getInt("total_sin_iva"));
                readVenta.setTotal_iva(result.getInt("total_iva"));
                readVenta.setIva(result.getInt("iva"));
                readVenta.setDescripcion(result.getString("descripcion"));
                readVenta.setPlaca_automovil(result.getString("placa_automovil"));
                readVenta.setId_orden_trabajo(result.getInt("id_orden_trabajo"));
                readVenta.setFecha_creacion(result.getDate("fecha_creacion"));
                readVenta.setFecha_modificado(result.getDate("fecha_modificado"));
                readVenta.setSede(result.getString("sede"));

                // Cambio valores en los labels
                txtDocumentoCliente.setText(readVenta.getCedula_cliente());
                txtPlacaVenta.setText(readVenta.getPlaca_automovil());

                if(readVenta.getId_orden_trabajo() == 0)
                    txtid_orden_trabajo.setText("");
                else
                    txtid_orden_trabajo.setText(String.valueOf(readVenta.getId_orden_trabajo()));

                txtDescripcionVenta.setText(readVenta.getDescripcion());

            }
        } catch(SQLException exception) {
            System.err.println(exception);
            Dialogs.showError("Error llenando datos por BD", "Error obteniendo las ventas");

        }

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
        tablaVenta.setItems(ventasList.sorted());
    }

}






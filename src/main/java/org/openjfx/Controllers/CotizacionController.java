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
import org.openjfx.Models.Cliente.SQL_Cliente;
import org.openjfx.Models.Cotizacion.SQL_Cotizacion;
import org.openjfx.Models.Cotizacion.Utils.*;
import org.openjfx.Models.Cotizacion.Cotizacion;
import org.openjfx.Models.Orden_Trabajo.SQL_Orden;


public class CotizacionController implements Initializable {

    // Variables para Actualizar, Leer y Borrar Usuarios
    @FXML
    private Button btnSalir;
    @FXML
    private TableView<Cotizacion> tablaCotizacion;
    @FXML
    private TableColumn<Cotizacion, String> col_id_cotizacion;
    @FXML
    private TableColumn<Cotizacion,String> col_Id_Cliente_Cotizacion;
    @FXML
    private TableColumn<Cotizacion,String> col_Id_Vendedor_Cotizacion;
    @FXML
    private TableColumn<Cotizacion,String> col_placa_Cotizacion;
    @FXML
    private TableColumn<Cotizacion,Double> col_iva_Cotizacion;
    @FXML
    private TableColumn<Cotizacion, Double> col_total_sin_iva_Cotizacion;
    @FXML
    private TableColumn<Cotizacion, Double> col_total_iva_cotizacion;
    @FXML
    private TableColumn<Cotizacion, String> col_Descripcion_Cotizacion;
    @FXML
    private TableColumn<Cotizacion, Date> col_fecha_modificacion_cotizacion;
    @FXML
    private TableColumn<Cotizacion, Date> col_fecha_creacion_Cotizacion;
    @FXML
    private TableColumn<Cotizacion, Integer> col_id_orden_trabajo_Cotizacion;

    private ObservableList<Cotizacion> cotizacionList = FXCollections.observableArrayList();

    // Variables para registrar cotizacion
    private String mensajeExito = String.format("-fx-text-fill: GREEN;");
    private String estiloMensajeExito = String.format("-fx-border-color: #A9A9A9; -fx-border-width: 2; -fx-border-radius: 5;");

    private String mensaje = String.format("-fx-text-fill: black;");
    private String mensajeError = String.format("-fx-text-fill: RED;");
    private String estiloMensajeError = String.format("-fx-border-color: RED; -fx-border-width: 2; -fx-border-radius: 5;");
    @FXML
    private TextField txtDocumentoCliente;
    @FXML
    private TextField txtPlacaCotizacion;
    @FXML
    private TextField txtDescripcionCotizacion;
    @FXML
    private TextField txtid_orden_trabajo;
    @FXML
    private Label validacionRegistroLabel;

    /**
     * CREATE - Registrar Cotización
     * @throws IOException
     */
    private void crearActualizarCotizacion(boolean crear) {
        validacionRegistroLabel.setText("");
        txtDocumentoCliente.setStyle(null);
        txtDescripcionCotizacion.setStyle(null);
        txtPlacaCotizacion.setStyle(null);
        txtid_orden_trabajo.setStyle(null);
        // Cuando los campos están en blanco
        if(txtDocumentoCliente.getText().isEmpty() || txtid_orden_trabajo.getText().isEmpty() || txtPlacaCotizacion.getText().isEmpty())
        {
            validacionRegistroLabel.setStyle(mensajeError);
            if(txtDocumentoCliente.getText().isEmpty())
            {
                validacionRegistroLabel.setText("Se tiene que seleccionar una cédula de un cliente!");
                txtDocumentoCliente.setStyle(estiloMensajeError);
                new Shake(txtDocumentoCliente).play();
                return;
            }
            else if(txtid_orden_trabajo.getText().isEmpty() && txtPlacaCotizacion.getText().isEmpty() )
            {
                validacionRegistroLabel.setText("Se tiene que seleccionar una placa o una orden de trabajo!");
                txtPlacaCotizacion.setStyle(estiloMensajeError);
                txtid_orden_trabajo.setStyle(estiloMensajeError);
                new Shake(txtid_orden_trabajo).play();
                new Shake(txtPlacaCotizacion).play();
                return;
            }
            else {
                boolean validado = this.validacionesCotizacion(crear);
                if (validado) {
                    this.guardarActualizarCotizacion(crear);
                    this.refreshTable();
                }
            }
        }
        else {
            boolean validado = this.validacionesCotizacion(crear);
            if (validado) {
                this.guardarActualizarCotizacion(crear);
                this.refreshTable();
            }
        }
    }

    @FXML
    protected void btnNuevaCotizacionClicked() throws IOException{
        this.crearActualizarCotizacion(true);
    }

    private boolean checkPlacaOrden()
    {
        if(!txtid_orden_trabajo.getText().isEmpty() && txtPlacaCotizacion.getText().isEmpty())
        {
            return true;
        }
        else if(txtid_orden_trabajo.getText().isEmpty() && !txtPlacaCotizacion.getText().isEmpty())
        {
            return true;
        }
        else {
            return false;
        }
    }

    private boolean validacionesCotizacion(boolean crear) {
        boolean validado = true;
        validacionRegistroLabel.setText("");
        // Validación campos vacíos
        if(!txtPlacaCotizacion.getText().isEmpty() && !txtid_orden_trabajo.getText().isEmpty())
        {
            validado = false;
            String textoError = "Se debe de escoger o placa u orden. NO las dos!";
            validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
            validacionRegistroLabel.setStyle(mensajeError);
            txtPlacaCotizacion.setStyle(estiloMensajeError);
            new FadeIn(txtPlacaCotizacion).play();
            txtid_orden_trabajo.setStyle(estiloMensajeError);
            new FadeIn(txtid_orden_trabajo).play();
        }

        if (!ValidacionesCotizacion.validarCedula(txtDocumentoCliente.getText()))
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
        // Validación placa
        if(!txtPlacaCotizacion.getText().isEmpty())
        {
            if (!ValidacionesCotizacion.validarPlaca(txtPlacaCotizacion.getText()) && this.checkPlacaOrden())
            {
                validado = false;
                String textoError = "Formato de placa incorrecto!";
                System.out.println(textoError);
                validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
                validacionRegistroLabel.setStyle(mensajeError);
                txtPlacaCotizacion.setStyle(estiloMensajeError);
                new FadeIn(txtPlacaCotizacion).play();
            }
            // Valida si existe un carro con esa placa
            else if(!SQL_Automovil.existeautomovil_placa(txtPlacaCotizacion.getText()))
            {
                validado = false;
                String textoError = "No existe un automóvil con esa placa!";
                validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
                validacionRegistroLabel.setStyle(mensajeError);
                txtPlacaCotizacion.setStyle(estiloMensajeError);
                new FadeIn(txtPlacaCotizacion).play();
            }
            else if (SQL_Cotizacion.existeCotizacion_cedula_Placa(txtDocumentoCliente.getText(), txtPlacaCotizacion.getText())&& crear) {
                // Validacion para saber si ya existe un registro con esa cedula y esa placa
                validado = false;
                String textoError = "Una cotizacion con ese número de cédula de cliente y placa ya existe!";
                validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
                validacionRegistroLabel.setStyle(mensajeError);
                txtDocumentoCliente.setStyle(estiloMensajeError);
                txtPlacaCotizacion.setStyle(estiloMensajeError);
                new FadeIn(txtDocumentoCliente).play();
                new FadeIn(txtPlacaCotizacion).play();

            }
        }
        // Validación orden de trabajo
        if(!txtid_orden_trabajo.getText().isEmpty())
        {
            if(!ValidacionesCotizacion.validarIdOrden(txtid_orden_trabajo.getText()) && this.checkPlacaOrden())
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
            else if (SQL_Cotizacion.existeCotizacion_cedula_Orden(txtDocumentoCliente.getText(), Integer.parseInt(txtid_orden_trabajo.getText()))&& crear) {
                // Validacion para saber si ya existe un registro con esa cedula y esa placa
                validado = false;
                String textoError = "Una cotizacion con ese número de cédula de cliente y ese id de orden ya existe!";
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

    public void guardarActualizarCotizacion(boolean crear) {
        try {
            Cotizacion cotizacionModelo = new Cotizacion();

            cotizacionModelo.setCedula_cliente(txtDocumentoCliente.getText());

           //Traer cedula vendedor
            cotizacionModelo.setCedula_vendedor(LoginController.obtenerUsuarioLogeado().getCedula());

            cotizacionModelo.setDescripcion(txtDescripcionCotizacion.getText());

            if(!txtPlacaCotizacion.getText().isEmpty())
            {
                cotizacionModelo.setPlaca_automovil(txtPlacaCotizacion.getText());
                //Traer el precio del automovil
                String placa = txtPlacaCotizacion.getText();
                ResultSet result = SQL_Automovil.obtenerAutomovil_placa(placa);
                result.next();
                double precio_sin_iva = result.getDouble("precio");
                cotizacionModelo.setTotal_sin_iva(precio_sin_iva);
                double iva = precio_sin_iva * 0.19;
                cotizacionModelo.setIva(iva);
                double precio_total = precio_sin_iva + iva;
                cotizacionModelo.setTotal_iva(precio_total);

                System.out.println("Precio sin Iva: " + precio_sin_iva );
                System.out.println("Iva: " + iva );
                System.out.println("Precio con Iva: " + precio_total );
            }

            if(!txtid_orden_trabajo.getText().isEmpty())
            {
                cotizacionModelo.setId_orden_trabajo(Integer.parseInt(txtid_orden_trabajo.getText()));
            }


            // SI la orden es para crear, o para actualizar, llamo al metodo respectivo
            if (crear)
            {
                SQL_Cotizacion.crearCotizacion(cotizacionModelo);
            }
            else
            {
                SQL_Cotizacion.editarCotizacion_cedula_placa_orden(cotizacionModelo.getCedula_cliente(), cotizacionModelo.getPlaca_automovil(), cotizacionModelo.getId_orden_trabajo(),  cotizacionModelo);
            }


            this.validadoLabelSet();
            this.limpiar();

        } catch (Exception e) {
            System.err.println(e);
            Dialogs.showError("Error en la base de datos", "Error registrando la cotización");

        }
    }

    public void limpiar() {

        txtDocumentoCliente.setStyle(null);
        txtDescripcionCotizacion.setStyle(null);
        txtPlacaCotizacion.setStyle(null);
        txtid_orden_trabajo.setStyle(null);
        txtDocumentoCliente.setText("");
        txtDescripcionCotizacion.setText("");
        txtPlacaCotizacion.setText("");
        txtid_orden_trabajo.setText("");
    }

    /**
     * UPDATE - READ - DELETE
     */
    private void loadData() {
        refreshTable();

        col_id_cotizacion.setCellValueFactory(new PropertyValueFactory<>("id_cotizacion"));
        col_Id_Cliente_Cotizacion.setCellValueFactory(new PropertyValueFactory<>("cedula_cliente"));
        col_Id_Vendedor_Cotizacion.setCellValueFactory(new PropertyValueFactory<>("cedula_vendedor"));
        col_Descripcion_Cotizacion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        col_fecha_modificacion_cotizacion.setCellValueFactory(new PropertyValueFactory<>("fecha_modificado"));
        col_fecha_creacion_Cotizacion.setCellValueFactory(new PropertyValueFactory<>("fecha_creacion"));
        col_placa_Cotizacion.setCellValueFactory(new PropertyValueFactory<>("placa_automovil"));
        col_id_orden_trabajo_Cotizacion.setCellValueFactory(new PropertyValueFactory<>("id_orden_trabajo"));
        col_iva_Cotizacion.setCellValueFactory(new PropertyValueFactory<>("iva"));
        col_total_sin_iva_Cotizacion.setCellValueFactory(new PropertyValueFactory<>("total_sin_iva"));
        col_total_iva_cotizacion.setCellValueFactory(new PropertyValueFactory<>("total_iva"));
        tablaCotizacion.setItems(cotizacionList.sorted());

    }

    private void readCotizacion() {
        try {
            ResultSet result = SQL_Cotizacion.obtenerTodasCotizacionesSet();
            while (result.next()) {
                Cotizacion readCotizacion = new Cotizacion();

                readCotizacion.setId_cotizacion(result.getInt("id_cotizacion"));
                readCotizacion.setCedula_cliente(result.getString("cedula_cliente"));
                readCotizacion.setCedula_vendedor(result.getString("cedula_vendedor"));
                readCotizacion.setTotal_sin_iva(result.getDouble("total_sin_iva"));
                readCotizacion.setFecha_modificado(result.getDate("fecha_modificado"));
                readCotizacion.setFecha_creacion(result.getDate("fecha_creacion"));
                readCotizacion.setIva(result.getDouble("iva"));
                readCotizacion.setDescripcion(result.getString("descripcion"));
                readCotizacion.setTotal_iva(result.getDouble("total_iva"));
                readCotizacion.setPlaca_automovil(result.getString("placa_automovil"));
                readCotizacion.setId_orden_trabajo(result.getInt("id_orden_trabajo"));

                cotizacionList.add(readCotizacion);
            }
            cotizacionList.sorted();
        } catch(SQLException exception) {
            throw new RuntimeException(exception);
        }
    }


    //@FXML
    private void refreshTable() {
        cotizacionList.clear();
        this.readCotizacion();
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
        if (Dialogs.showConfirm("Seleccione una opción", "¿Está seguro que quiere salir de la aplicación?", Dialogs.YES, Dialogs.NO).equals(Dialogs.YES)) {
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
    protected void btnBuscarCedulaPlacaOrden() {
        validacionRegistroLabel.setText("");
        txtDocumentoCliente.setStyle(null);
        txtDescripcionCotizacion.setStyle(null);
        txtPlacaCotizacion.setStyle(null);
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
        if(!txtPlacaCotizacion.getText().isEmpty() && !txtid_orden_trabajo.getText().isEmpty())
        {
            validado = false;
            String textoError = "Se debe de escoger o placa u orden. NO las dos!";
            validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
            validacionRegistroLabel.setStyle(mensajeError);
            txtPlacaCotizacion.setStyle(estiloMensajeError);
            new FadeIn(txtPlacaCotizacion).play();
            txtid_orden_trabajo.setStyle(estiloMensajeError);
            new FadeIn(txtid_orden_trabajo).play();
        }
        if (!ValidacionesCotizacion.validarCedula(txtDocumentoCliente.getText())) {
            validado = false;
            String textoError = "Formato de la cédula incorrecto!";
            validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
            validacionRegistroLabel.setStyle(mensajeError);
            txtDocumentoCliente.setStyle(estiloMensajeError);
            new FadeIn(txtDocumentoCliente).play();
        }
        // Validación placa
        if(!txtPlacaCotizacion.getText().isEmpty())
        {
            if (!ValidacionesCotizacion.validarPlaca(txtPlacaCotizacion.getText()) && this.checkPlacaOrden())
            {
                validado = false;
                String textoError = "Formato de placa incorrecto!";
                System.out.println(textoError);
                validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
                validacionRegistroLabel.setStyle(mensajeError);
                txtPlacaCotizacion.setStyle(estiloMensajeError);
                new FadeIn(txtPlacaCotizacion).play();
            }
            else if (!SQL_Cotizacion.existeCotizacion_cedula_Placa(txtDocumentoCliente.getText(), txtPlacaCotizacion.getText())) {
                // Validacion para saber si ya existe un registro con esa cedula y esa placa
                validado = false;
                String textoError = "Una cotizacion con ese número de cédula de cliente y placa NO existe!";
                validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
                validacionRegistroLabel.setStyle(mensajeError);
                txtDocumentoCliente.setStyle(estiloMensajeError);
                txtPlacaCotizacion.setStyle(estiloMensajeError);
                new FadeIn(txtDocumentoCliente).play();
                new FadeIn(txtPlacaCotizacion).play();

            }
        }
        // Validación orden de trabajo
        if(!txtid_orden_trabajo.getText().isEmpty())
        {
            if(!ValidacionesCotizacion.validarIdOrden(txtid_orden_trabajo.getText()))
            {
                validado = false;
                String textoError = "Formato del id de la orden incorrecto!";
                validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
                validacionRegistroLabel.setStyle(mensajeError);
                txtid_orden_trabajo.setStyle(estiloMensajeError);
                new FadeIn(txtid_orden_trabajo).play();
            }
            else if (!SQL_Cotizacion.existeCotizacion_cedula_Orden(txtDocumentoCliente.getText(), Integer.parseInt(txtid_orden_trabajo.getText()))
            ) {
                // Validacion para saber si ya existe un registro con esa cedula y ese id de orden
                validado = false;
                String textoError = "Una cotizacion con ese número de cédula de cliente y ese id de orden NO existe!";
                validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
                validacionRegistroLabel.setStyle(mensajeError);
                txtDocumentoCliente.setStyle(estiloMensajeError);
                txtid_orden_trabajo.setStyle(estiloMensajeError);
                new FadeIn(txtDocumentoCliente).play();
                new FadeIn(txtid_orden_trabajo).play();

            }
        }
        if (!SQL_Cotizacion.existeCotizacion_cedula_Placa(txtDocumentoCliente.getText(), txtPlacaCotizacion.getText())) {
            // Validacion para saber si el registro ya existe
            validado = false;
            String textoError = "No existe una cotización con esos datos!";
            validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
            validacionRegistroLabel.setStyle(mensajeError);
            txtDocumentoCliente.setStyle(estiloMensajeError);
            new FadeIn(txtDocumentoCliente).play();
            txtPlacaCotizacion.setStyle(estiloMensajeError);
            new FadeIn(txtPlacaCotizacion).play();
            txtid_orden_trabajo.setStyle(estiloMensajeError);
            new FadeIn(txtid_orden_trabajo).play();

        }
        return validado;
    }

    private void llenarCamposPorCedulaOrdenPlaca() {
        String cedula_cliente = txtDocumentoCliente.getText();
        String placa_automovil = txtPlacaCotizacion.getText();
        int id_orden_trabajo = 0;
        if(!txtid_orden_trabajo.getText().isEmpty())
            id_orden_trabajo = Integer.parseInt(txtid_orden_trabajo.getText());

        try {
            ResultSet result = SQL_Cotizacion.obtenerCotizacion_Cedula_Placa_Orden(cedula_cliente, placa_automovil, id_orden_trabajo);

            while (result.next()) {
                Cotizacion readCotizacion = new Cotizacion();

                readCotizacion.setCedula_vendedor(result.getString("cedula_vendedor"));
                readCotizacion.setCedula_cliente(result.getString("cedula_cliente"));
                readCotizacion.setTotal_iva(result.getInt("total_sin_iva"));
                readCotizacion.setTotal_iva(result.getInt("total_iva"));
                readCotizacion.setIva(result.getInt("iva"));
                readCotizacion.setDescripcion(result.getString("descripcion"));
                readCotizacion.setPlaca_automovil(result.getString("placa_automovil"));
                readCotizacion.setId_orden_trabajo(result.getInt("id_orden_trabajo"));
                readCotizacion.setFecha_creacion(result.getDate("fecha_creacion"));
                readCotizacion.setFecha_modificado(result.getDate("fecha_modificado"));

                // Cambio valores en los labels
                txtDocumentoCliente.setText(readCotizacion.getCedula_cliente());
                txtPlacaCotizacion.setText(readCotizacion.getPlaca_automovil());

                if(readCotizacion.getId_orden_trabajo() == 0)
                    txtid_orden_trabajo.setText("");
                else
                    txtid_orden_trabajo.setText(String.valueOf(readCotizacion.getId_orden_trabajo()));

                txtDescripcionCotizacion.setText(readCotizacion.getDescripcion());

            }
        } catch(SQLException exception) {
            System.err.println(exception);
            Dialogs.showError("Error llenando datos por BD", "Error obteniendo las cotizaciones");

        }

    }

    // Borrar - poner inactivo
    @FXML
    private void btnBorrarClicked() {
        validacionRegistroLabel.setText("");
        txtPlacaCotizacion.setStyle(null);
        txtDocumentoCliente.setStyle(null);
        String cedulaCliente = txtDocumentoCliente.getText();
        String placa = txtPlacaCotizacion.getText();
        int id_orden = Integer.parseInt(txtid_orden_trabajo.getText());
        if (SQL_Orden.existeOrden_CedulaPlaca(cedulaCliente, placa)) {
            try {
                ResultSet result = SQL_Cotizacion.obtenerCotizacion_Cedula_Placa_Orden(cedulaCliente, placa, id_orden);
                result.next();
                this.validadoLabelSet();
                this.limpiar();

            } catch (SQLException exception) {
                Dialogs.showError("Error en BD", "Error borrando la cotización");
            }
        }
        else {
            String textoError = "No existe una orden con esa cédula o placa!";
            validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
            validacionRegistroLabel.setStyle(mensajeError);
            txtDocumentoCliente.setStyle(estiloMensajeError);
            new FadeIn(txtDocumentoCliente).play();
            txtPlacaCotizacion.setStyle(estiloMensajeError);
            new FadeIn(txtPlacaCotizacion).play();
        }

        this.refreshTable();
    }




    // Actualizar
    @FXML
    private void btnActualizarClicked() {
        this.crearActualizarCotizacion(false);
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
        this.readCotizacion();
        this.loadData();
        tablaCotizacion.setItems(cotizacionList.sorted());
    }

}


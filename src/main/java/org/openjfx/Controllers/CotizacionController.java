package org.openjfx.Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import org.openjfx.Models.Cliente.SQL_Cliente;
import org.openjfx.Models.Cliente.SQL_Cotizacion;
import org.openjfx.Models.Cliente.Utils.ValidacionesClientes;
import org.openjfx.Models.Cliente.Utils.ValidacionesCotizacion;
import org.openjfx.Models.Cotizacion.Cotizacion;
import org.openjfx.Models.Repuesto.Repuesto;
import org.openjfx.Models.Repuesto.SQL_Repuesto;


import javax.swing.*;

public class CotizacionController implements Initializable {

    // Variables para Actualizar, Leer y Borrar Usuarios
    @FXML
    private Button btnSalir;
    @FXML
    private TableView<Cotizacion> tablaCotizacion;
    @FXML
    private TableColumn<Cotizacion,String> col_Id_Cliente_Cotizacion;
    @FXML
    private TableColumn<Cotizacion,String> col_Id_Vendedor_Cotizacion;
    @FXML
    private TableColumn<Cotizacion,String> col_placa_Cotizacion;
    @FXML
    private TableColumn<Cotizacion,Integer> col_iva_Cotizacion;
    @FXML
    private TableColumn<Cotizacion, Integer> col_total_sin_iva_Cotizacion;
    @FXML
    private TableColumn<Cotizacion, Integer> col_total_iva_cotizacion;


    @FXML
    private TableColumn<Cotizacion, String> col_Descripcion_Cotizacion;
    @FXML
    private TableColumn<Cotizacion, Date> col_fecha_modificacion_cotizacion;

    @FXML
    private TableColumn<Cotizacion, Date> col_fecha_creacion_Cotizacion;
    @FXML
    private TableColumn<Cotizacion, Integer> col_id_orden_trabajo_Cotizacion;



    private ObservableList<Cotizacion> cotizacionList = FXCollections.observableArrayList();

    private Cotizacion cotizacion = null;

    // Variables para registrar cotizacion
    private String mensajeExito = String.format("-fx-text-fill: GREEN;");
    private String estiloMensajeExito = String.format("-fx-border-color: #A9A9A9; -fx-border-width: 2; -fx-border-radius: 5;");

    private String mensaje = String.format("-fx-text-fill: black;");
    private String mensajeError = String.format("-fx-text-fill: RED;");
    private String estiloMensajeError = String.format("-fx-border-color: RED; -fx-border-width: 2; -fx-border-radius: 5;");
    @FXML
    private TextField txtDocumentoCliente, txtDocumentoVendedor;
    @FXML
    private TextField txtPlacaCotizacion;
    @FXML
    private TextField txtDescripcionCotizacion;
    @FXML
    private TextField txtid_orden_trabajo;
    @FXML
    private DatePicker dtpCotizacion;
    @FXML
    MenuItem firstItem;
    @FXML
    MenuItem secondItem;

    @FXML
    private Label validacionRegistroLabel;

    /**
     * CREATE - Registrar Cotización
     * @throws IOException
     */
    private void crearActualizarCotizacion(boolean crear) {

        validacionRegistroLabel.setText("");
        txtDocumentoCliente.setStyle(null);
        txtDocumentoVendedor.setStyle(null);
        txtDescripcionCotizacion.setStyle(null);
        txtPlacaCotizacion.setStyle(null);
        txtid_orden_trabajo.setStyle(null);
        dtpCotizacion.setStyle(null);
        // Cuando los campos están en blanco
        if(txtDocumentoCliente.getText().isEmpty() ||
                txtDocumentoVendedor.getText().isEmpty() ||txtDescripcionCotizacion.getText().isEmpty()||
                txtPlacaCotizacion.getText().isEmpty()|| txtid_orden_trabajo.getText().isEmpty() ||
                dtpCotizacion.getValue()==null )
        {
            validacionRegistroLabel.setStyle(mensajeError);
            if(txtDocumentoCliente.getText().isEmpty() &&
                    txtDocumentoVendedor.getText().isEmpty() && txtDescripcionCotizacion.getText().isEmpty() &&
                    txtPlacaCotizacion.getText().isEmpty() && dtpCotizacion.getValue()==null )
            {
                validacionRegistroLabel.setText("Algunos campos están vacíos!");
                txtDocumentoCliente.setStyle(estiloMensajeError);
                txtDocumentoVendedor.setStyle(estiloMensajeError);
                txtDescripcionCotizacion.setStyle(estiloMensajeError);
                dtpCotizacion.setStyle(estiloMensajeError);
                new Shake(txtDocumentoCliente).play();
                new Shake(txtDocumentoVendedor).play();
                new Shake(txtDescripcionCotizacion).play();
                new Shake(txtPlacaCotizacion).play();
                new Shake(dtpCotizacion).play();
            } else if (txtDocumentoCliente.getText().isEmpty() &&
                    txtDocumentoVendedor.getText().isEmpty() && txtDescripcionCotizacion.getText().isEmpty() &&
                    txtid_orden_trabajo.getText().isEmpty() && dtpCotizacion.getValue()==null ) {

                validacionRegistroLabel.setText("Algunos campos están vacíos!");
                txtDocumentoCliente.setStyle(estiloMensajeError);
                txtDocumentoVendedor.setStyle(estiloMensajeError);
                txtDescripcionCotizacion.setStyle(estiloMensajeError);
                dtpCotizacion.setStyle(estiloMensajeError);
                new Shake(txtDocumentoCliente).play();
                new Shake(txtDocumentoVendedor).play();
                new Shake(txtDescripcionCotizacion).play();
                new Shake(txtid_orden_trabajo).play();
                new Shake(dtpCotizacion).play();

            } else {
                validacionRegistroLabel.setText("Algunos campos están vacíos!");
                boolean validado = this.validacionesCotizacion(crear);
                if (validado) {
                    this.guardarActualizarCotizacion(crear);
                    this.refreshTable();

                }
            }

        } else {
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


    private boolean validacionesCotizacion(boolean crear) {
        boolean validado = true;
        validacionRegistroLabel.setText("");

        // Validación Cédula cliente
        if (!ValidacionesCotizacion.validarCedula(txtDocumentoCliente.getText()))
        {
            validado = false;
            String textoError = "Formato de la cédula incorrecto!";
            validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
            validacionRegistroLabel.setStyle(mensajeError);
            txtDocumentoCliente.setStyle(estiloMensajeError);
            new FadeIn(txtDocumentoCliente).play();
        } else if (SQL_Cotizacion.existeCotizacion_cedula_Placa_orden(txtDocumentoCliente.getText(), String.valueOf(txtPlacaCotizacion),Integer.parseInt(String.valueOf(txtid_orden_trabajo)))) {
            // Validacion para saber si el cliente con esa cédula ya existe
            System.out.println(SQL_Cotizacion.existeCotizacion_cedula_Placa_orden(txtDocumentoVendedor.getText(), txtPlacaCotizacion.getText(), Integer.valueOf(txtid_orden_trabajo.getText())));
            validado = false;
            String textoError = "Un usuario con ese número de cédula ya existe!";
            validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
            validacionRegistroLabel.setStyle(mensajeError);
            txtDocumentoCliente.setStyle(estiloMensajeError);
            new FadeIn(txtDocumentoCliente).play();

        }
        // Validación Cédula vendedor
        if (!ValidacionesCotizacion.validarCedula(txtDocumentoVendedor.getText()))
        {
            validado = false;
            String textoError = "Formato de la cédula incorrecto!";
            validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
            validacionRegistroLabel.setStyle(mensajeError);
            txtDocumentoVendedor.setStyle(estiloMensajeError);
            new FadeIn(txtDocumentoVendedor).play();
        } else if (SQL_Cotizacion.existeCotizacion_cedula_Placa_orden(txtDocumentoVendedor.getText(), txtPlacaCotizacion.getText(), Integer.valueOf(txtid_orden_trabajo.getText())) && crear) {
            // Validacion para saber si el cliente con esa cédula ya existe
            System.out.println(SQL_Cotizacion.existeCotizacion_cedula_Placa_orden(txtDocumentoVendedor.getText(), txtPlacaCotizacion.getText(), Integer.valueOf(txtid_orden_trabajo.getText())));
            validado = false;
            String textoError = "Un usuario con ese número de cédula ya existe!";
            validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
            validacionRegistroLabel.setStyle(mensajeError);
            txtDocumentoCliente.setStyle(estiloMensajeError);
            new FadeIn(txtDocumentoCliente).play();
        }
        // Validación placa
        if (!ValidacionesCotizacion.validarPlaca(txtPlacaCotizacion.getText()))
        {
            validado = false;
            String textoError = "Formato de email incorrecto!";
            System.out.println(textoError);
            validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
            validacionRegistroLabel.setStyle(mensajeError);
            txtPlacaCotizacion.setStyle(estiloMensajeError);
            new FadeIn(txtPlacaCotizacion).play();
        }
        // Validación Fecha
        if (dtpCotizacion.getValue()==null && !ValidacionesCotizacion.validarFecha(String.valueOf(dtpCotizacion.getValue())))
        {
            validado = false;
            String textoError = "Formato de fecha incorrecto!";
            validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
            validacionRegistroLabel.setStyle(mensajeError);
            dtpCotizacion.setStyle(estiloMensajeError);
            new FadeIn(dtpCotizacion).play();
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


            cotizacionModelo.setCedula_vendedor(txtDocumentoCliente.getText());
            cotizacionModelo.setCedula_vendedor(txtDocumentoVendedor.getText());
            cotizacionModelo.setDescripcion(txtDescripcionCotizacion.getText());
            cotizacionModelo.setPlaca_automovil(txtPlacaCotizacion.getText());
            cotizacionModelo.setid_orden_trabajo(Integer.parseInt(txtid_orden_trabajo.getText()));

            DateTimeFormatter fechaHoraFormato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String stringDataFormateada = dtpCotizacion.getValue().format(fechaHoraFormato);
            Date cotizacionFormat = new SimpleDateFormat("dd/MM/yyyy").parse(stringDataFormateada);
            cotizacionModelo.setFecha_creacion(cotizacionFormat);


            //Traer cedula vendedor
            cotizacionModelo.setCedula_vendedor(LoginController.obtenerUsuarioLogeado().getCedula());



            // SI la orden es para crear, o para actualizar, llamo al metodo respectivo
            if (crear)
                SQL_Cotizacion.crearCotizacion(cotizacionModelo);
            else
                SQL_Cotizacion.editarCotizacion(cotizacionModelo.getCedula_cliente(), cotizacionModelo.getPlaca_automovil(),
                        cotizacionModelo.getid_orden_trabajo(), cotizacionModelo);

            this.validadoLabelSet();
            this.limpiar();

        } catch (Exception e) {
            System.err.println(e);
            JOptionPane.showMessageDialog(null,"Error registrando el usuario");
        }
    }

    public void limpiar() {
        txtDocumentoCliente.setText("");
        txtDocumentoVendedor.setText("");
        txtDescripcionCotizacion.setText("");
        txtPlacaCotizacion.setText("");
        txtid_orden_trabajo.setText("");
        dtpCotizacion.setValue(null);
    }

    /**
     * UPDATE - READ - DELETE
     */
    private void loadData() {
        refreshTable();

        col_Id_Cliente_Cotizacion.setCellValueFactory(new PropertyValueFactory<>("cedula_cliente"));
        col_Id_Vendedor_Cotizacion.setCellValueFactory(new PropertyValueFactory<>("cedula_vendedor"));
        col_Descripcion_Cotizacion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        col_fecha_modificacion_cotizacion.setCellValueFactory(new PropertyValueFactory<>("fecha_modificado"));
        col_fecha_creacion_Cotizacion.setCellValueFactory(new PropertyValueFactory<>("fecha_creacion"));
        col_placa_Cotizacion.setCellValueFactory(new PropertyValueFactory<>("placa_automovil"));
        col_id_orden_trabajo_Cotizacion.setCellValueFactory(new PropertyValueFactory<>("id_orden_trabajo"));
        col_iva_Cotizacion.setCellValueFactory(new PropertyValueFactory<>("IVA"));
        col_total_sin_iva_Cotizacion.setCellValueFactory(new PropertyValueFactory<>("TOTAL_SIN_IVA"));
        col_total_iva_cotizacion.setCellValueFactory(new PropertyValueFactory<>("TOTAL_IVA"));
        tablaCotizacion.setItems(cotizacionList.sorted());

    }

    private void readCotizacion() {
        try {
            ResultSet result = SQL_Cotizacion.obtenerTodasCotizacionesSet();
            while (result.next()) {
                Cotizacion readCotizacion = new Cotizacion();


                readCotizacion.setCedula_cliente(result.getString("cedula_cliente"));
                readCotizacion.setCedula_vendedor(result.getString("cedula_vendedor"));
                readCotizacion.setTOTAL_SIN_IVA(result.getInt("TOTAL_SIN_IVA"));
                readCotizacion.setFecha_modificado(result.getDate("fecha_modificado"));
                readCotizacion.setFecha_creacion(result.getDate("fecha_creacion"));
                readCotizacion.setIVA(result.getInt("IVA"));
                readCotizacion.setTOTAL_IVA(result.getInt("TOTAL_IVA"));
                readCotizacion.setPlaca_automovil(result.getString("placa_automovil"));
                readCotizacion.setid_orden_trabajo(result.getInt("id_orden_trabajo"));

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
    protected void btnBuscarCedula() {
        validacionRegistroLabel.setText("");
        txtDocumentoCliente.setStyle(null);
        txtDocumentoVendedor.setStyle(null);
        txtDescripcionCotizacion.setStyle(null);
        txtPlacaCotizacion.setStyle(null);
        txtid_orden_trabajo.setStyle(null);
        dtpCotizacion.setStyle(null);
        if(txtDocumentoCliente.getText().isEmpty() || txtPlacaCotizacion.getText().isEmpty())
        {
            validacionRegistroLabel.setStyle(mensajeError);
            new Shake(txtDocumentoCliente).play();
            new Shake(txtPlacaCotizacion).play();
            validacionRegistroLabel.setText("La cédula del cliente y la placa están vacias!");
        } else if (txtDocumentoCliente.getText().isEmpty() || txtid_orden_trabajo.getText().isEmpty()) {
            validacionRegistroLabel.setStyle(mensajeError);
            new Shake(txtDocumentoCliente).play();
            new Shake(txtid_orden_trabajo).play();
            validacionRegistroLabel.setText("La cédula del cliente y la orden de trabajo están vacias!");

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
        if (!ValidacionesClientes.validarCedula(txtDocumentoCliente.getText())) {
            validado = false;
            String textoError = "Formato de la cédula incorrecto!";
            validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
            validacionRegistroLabel.setStyle(mensajeError);
            txtDocumentoCliente.setStyle(estiloMensajeError);
            new FadeIn(txtDocumentoCliente).play();
        }

        if (!SQL_Cotizacion.existeCotizacion_cedula_Placa_orden(txtDocumentoCliente.getText(), txtPlacaCotizacion.getText(), Integer.valueOf(txtid_orden_trabajo.getText()))) {
            // Validacion para saber si el usuario con esa cédula ya existe
            validado = false;
            String textoError = "Un usuario con ese número de cédula NO existe!";
            validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
            validacionRegistroLabel.setStyle(mensajeError);
            txtDocumentoCliente.setStyle(estiloMensajeError);
            new FadeIn(txtDocumentoCliente).play();
        }
        return validado;
    }

    private void llenarCamposPorCedula() {
        String cedula_cliente = txtDocumentoCliente.getText();
        String placa_automovil = txtPlacaCotizacion.getText();
        Integer id_orden_trabajo = Integer.valueOf(txtid_orden_trabajo.getText());
        try {
            ResultSet result = SQL_Cotizacion.obtenerCotizacion_Cedula_Placa_Orden(cedula_cliente, placa_automovil, String.valueOf(id_orden_trabajo));
            while (result.next()) {
                Cotizacion readCotizacion = new Cotizacion();

                readCotizacion.setCedula_cliente(result.getString("cedula_cliente"));
                readCotizacion.setCedula_vendedor(result.getString("cedula_vendedor"));
                readCotizacion.setDescripcion(result.getString("descripcion"));
                readCotizacion.setTOTAL_SIN_IVA(result.getInt("TOTAL_SIN_IVA"));
                readCotizacion.setTOTAL_IVA(result.getInt("TOTAL_IVA"));
                readCotizacion.setIVA(result.getInt("IVA"));
                readCotizacion.setPlaca_automovil(result.getString("placa_automovil"));
                readCotizacion.setFecha_creacion(result.getDate("fecha_creacion"));
                readCotizacion.setFecha_modificado(result.getDate("fecha_modificado"));
                readCotizacion.setid_orden_trabajo(result.getInt("id_orden_trabajo"));

                // Cambio valores en los labels
                txtDocumentoCliente.setText(readCotizacion.getCedula_cliente());
                txtDocumentoVendedor.setText(readCotizacion.getCedula_vendedor());
                txtDescripcionCotizacion.setText(readCotizacion.getDescripcion());
                txtPlacaCotizacion.setText(readCotizacion.getPlaca_automovil());
                dtpCotizacion.setValue(LocalDate.parse(readCotizacion.getFecha_creacion().toString()));


            }
        } catch(SQLException exception) {
            throw new RuntimeException(exception);
        }

    }

    // Borrar - poner inactivo
    @FXML
    private void btnBorrarClicked() {
        if(txtDocumentoCliente.getText().isEmpty() || txtDocumentoVendedor.getText().isEmpty() ||
                txtPlacaCotizacion.getText().isEmpty()|| txtDescripcionCotizacion.getText().isEmpty()||
        txtid_orden_trabajo.getText().isEmpty())
        {
            validacionRegistroLabel.setStyle(mensajeError);
            new Shake(txtDocumentoCliente).play();
            new Shake(txtDocumentoVendedor).play();
            new Shake(txtPlacaCotizacion).play();
            new Shake(txtDescripcionCotizacion).play();
            new Shake(txtid_orden_trabajo).play();
            validacionRegistroLabel.setText("El nombre, la marca o la cantidad del repuesto están vacias!");
        }
        else {
            this.borrarCotizacion();
        }
    }

    private boolean borrarCotizacion() {
        boolean validado = true;
        validacionRegistroLabel.setText("");
        // Validacion existencia
        if (SQL_Cotizacion.editarCotizacion(txtDocumentoCliente.getText(), txtPlacaCotizacion.getText(), txtid_orden_trabajo.getText())) {
            // Validación cantidad
            ResultSet resultado =  SQL_Cotizacion.obtenerCotizacion_Cedula_Placa_Orden(txtDocumentoCliente.getText(), txtPlacaCotizacion.getText(), txtid_orden_trabajo.getText());
            try {
                resultado.next();
            } catch (SQLException e) {
                throw new RuntimeException(e);}
            }
        this.refreshTable();

        return validado;
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


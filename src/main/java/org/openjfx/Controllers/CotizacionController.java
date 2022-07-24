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
import org.openjfx.Models.Cliente.Cliente;
import org.openjfx.Models.Cliente.SQL_Cliente;
import org.openjfx.Models.Cliente.SQL_Cotizacion;
import org.openjfx.Models.Cliente.Utils.ValidacionesClientes;
import org.openjfx.Models.Cliente.Utils.ValidacionesCotizacion;
import org.openjfx.Models.Cotizacion.Cotizacion;
import org.openjfx.Models.Usuario.Usuario;


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

    private TableColumn<Cotizacion, Date> col_modificar_cotizacion;



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
    private TextField txtPlacaCotizacion, txtDescripcionCotizacion;
    @FXML
    private TextField txtOrdenCotizacion;
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
        txtOrdenCotizacion.setStyle(null);
        dtpCotizacion.setStyle(null);
        // Cuando los campos están en blanco
        if(txtDocumentoCliente.getText().isEmpty() ||
                txtDocumentoVendedor.getText().isEmpty() ||txtDescripcionCotizacion.getText().isEmpty()||
                txtPlacaCotizacion.getText().isEmpty()|| txtOrdenCotizacion.getText().isEmpty() ||
                dtpCotizacion.getValue()==null )
        {
            validacionRegistroLabel.setStyle(mensajeError);
            if(txtDocumentoCliente.getText().isEmpty() &&
                    txtDocumentoVendedor.getText().isEmpty() && txtDescripcionCotizacion.getText().isEmpty() &&
                    txtPlacaCotizacion.getText().isEmpty() && txtOrdenCotizacion.getText().isEmpty() &&
                    dtpCotizacion.getValue()==null )
            {
                validacionRegistroLabel.setText("Se requieren todos los campos!");
                txtDocumentoCliente.setStyle(estiloMensajeError);
                txtDocumentoVendedor.setStyle(estiloMensajeError);
                txtOrdenCotizacion.setStyle(estiloMensajeError);
                txtPlacaCotizacion.setStyle(estiloMensajeError);
                txtDescripcionCotizacion.setStyle(estiloMensajeError);
                dtpCotizacion.setStyle(estiloMensajeError);
                new Shake(txtOrdenCotizacion).play();
                new Shake(txtDocumentoCliente).play();
                new Shake(txtDocumentoVendedor).play();
                new Shake(txtPlacaCotizacion).play();
                new Shake(txtDescripcionCotizacion).play();
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
    protected void bttnNuevaCotizacionClicked() throws IOException{
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
        } else if (SQL_Cotizacion.existeCotizacion_cedula(txtDocumentoCliente.getText()) && crear) {
            // Validacion para saber si el cliente con esa cédula ya existe
            System.out.println(SQL_Cliente.existeCliente_Cedula(txtDocumentoCliente.getText()));
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
        } else if (SQL_Cotizacion.existeCotizacion_cedula(txtDocumentoVendedor.getText()) && crear) {
            // Validacion para saber si el cliente con esa cédula ya existe
            System.out.println(SQL_Cliente.existeCliente_Cedula(txtDocumentoVendedor.getText()));
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

            DateTimeFormatter fechaHoraFormato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String stringDataFormateada = dtpCotizacion.getValue().format(fechaHoraFormato);
            Date cotizacionFormat = new SimpleDateFormat("dd/MM/yyyy").parse(stringDataFormateada);
            cotizacionModelo.setFecha_creacion(cotizacionFormat);


            //Traer sede y cedula de creado por
           // clienteModelo.setSede(LoginController.obtenerUsuarioLogeado().getSede());



            // SI la orden es para crear, o para actualizar, llamo al metodo respectivo
            if (crear)
                SQL_Cotizacion.crearCotizacion(cotizacionModelo);
            else
                SQL_Cliente.editarClientes(cotizacionModelo.getCedula_cliente(), cotizacionModelo);

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
        txtOrdenCotizacion.setText("");
        dtpCotizacion.setValue(null);
    }

    /**
     * UPDATE - READ - DELETE
     */
    private void loadData() {
        refreshTable();

        col_Id_Cliente_Cotizacion.setCellValueFactory(new PropertyValueFactory<>("cedula_cliente"));
        col_Id_Vendedor_Cotizacion.setCellValueFactory(new PropertyValueFactory<>("email"));
        col_placa_Cotizacion.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        col_Descripcion_Cotizacion.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        col_modificar_cotizacion.setCellValueFactory(new PropertyValueFactory<>("modificado"));
        col_placa_Cotizacion.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        col_iva_Cotizacion.setCellValueFactory(new PropertyValueFactory<>("activo"));
        col_total_sin_iva_Cotizacion.setCellValueFactory(new PropertyValueFactory<>("fecha_nacimiento"));
        col_total_iva_cotizacion.setCellValueFactory(new PropertyValueFactory<>("last_session"));
        tablaCotizacion.setItems(cotizacionList.sorted());

    }

    private void readUsers() {
        try {
            ResultSet result = SQL_Cotizacion.obtenerTodasCotizacionesSet();
            while (result.next()) {
                Cotizacion readCotizacion = new Cotizacion();

                Cotizacion.setEmail(result.getString("cedula_vendedor"));
                Cotizacion.setNombre(result.getString("nombre"));
                Cotizacion.setApellido(result.getString("apellido"));
                Cotizacion.setModificado(result.getDate("modificado"));
                Cotizacion.setJoined(result.getDate("joined"));
                Cotizacion.setTelefono(result.getString("telefono"));
                Cotizacion.setActivo(result.getBoolean("activo"));
                Cotizacion.setFecha_nacimiento(result.getDate("fecha_nacimiento"));

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
        this.readUsers();
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
        dtpCotizacion.setStyle(null);
        if(txtDocumentoCliente.getText().isEmpty())
        {
            validacionRegistroLabel.setStyle(mensajeError);
            new Shake(txtDocumentoCliente).play();
            validacionRegistroLabel.setText("La cédula está vacía!");
        }
        else {
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

        if (!SQL_Cotizacion.existeCotizacion_cedula(txtDocumentoCliente.getText())) {
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
        try {
            ResultSet result = SQL_Cotizacion.obtenerCotizacion_Cedula(cedula_cliente);
            while (result.next()) {
                Cliente readCotizacion = new Cliente();

                readCotizacion.setCedula(result.getString("cedula_cliente"));
                readCotizacion.setEmail(result.getString("email"));
                readCotizacion.setNombre(result.getString("nombre"));
                readCotizacion.setApellido(result.getString("apellido"));
                readCotizacion.setModificado(result.getDate("modificado"));
                readCotizacion.setJoined(result.getString("descripcion"));
                readCotizacion.setTelefono(result.getString("telefono"));
                readCotizacion.setFecha_nacimiento(result.getDate("fecha_nacimiento"));
                readCotizacion.setId_tipo_usuario(result.getInt("id_tipo_usuario"));

                // Cambio valores en los labels
                txtDocumentoCliente.setText(readCotizacion.getCedula_cliente());
                txtDocumentoVendedor.setText(readCotizacion.getCedula());
                txtDescripcionCotizacion.setText(readCotizacion);
                txtPlacaCotizacion.setText(readCotizacion);
                dtpCotizacion.setValue(LocalDate.parse(readCotizacion.getFecha_nacimiento().toString()));


            }
        } catch(SQLException exception) {
            throw new RuntimeException(exception);
        }

    }

    // Borrar - poner inactivo
    @FXML
    private void btnBorrarClicked() {
        String cedula_cliente = txtDocumentoCliente.getText();
        if (SQL_Cotizacion.existeCotizacion_cedula(cedula_cliente)) {
            try {
                ResultSet result = SQL_Cotizacion.obtenerCotizacion_Cedula(cedula_cliente);
                result.next();
                boolean activo = result.getBoolean("activo");
                SQL_Cliente.cambiarEstadoClientePorCedula(cedula_cliente, activo);
                this.validadoLabelSet();
                this.limpiar();

            } catch (SQLException exception) {
                throw new RuntimeException(exception);
            }
        }
        else {
            String textoError = "No existe un usuario con esa cédula!";
            validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
            validacionRegistroLabel.setStyle(mensajeError);
            txtDocumentoCliente.setStyle(estiloMensajeError);
            new FadeIn(txtDocumentoCliente).play();
        }
        this.refreshTable();
    }

    // Actualizar
    @FXML
    private void btnActualizarClicked() {
        this.crearActualizarCotizacion(false);
    }

    @FXML
    private void btnImprimirCsv() {
        // TODO export to pdf or csv
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.readUsers();
        this.loadData();
        tablaCotizacion.setItems(cotizacionList.sorted());
    }

}


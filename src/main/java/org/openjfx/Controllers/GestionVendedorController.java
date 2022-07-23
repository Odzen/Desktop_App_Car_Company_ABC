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
import org.openjfx.Models.Cliente.Utils.ValidacionesClientes;
import org.openjfx.Models.Sede.SQL_Sede;
import org.openjfx.Models.Sede.Utils.ValidacionesSede;
import org.openjfx.Models.Usuario.SQL_Usuario;
import org.openjfx.Models.Usuario.Usuario;

import javax.swing.*;

public class GestionVendedorController implements Initializable {

    // Variables para Actualizar, Leer y Borrar Usuarios
    @FXML
    private Button btnSalir;
    @FXML
    private TableView<Usuario> tableGestionVendedor;
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
    private TableColumn<Cliente, Integer> col_id_tipo_usuarioGestionVendedor;

    @FXML
    private TableColumn<Cliente, Date> col_nacimientoGestionVendedor;

    @FXML
    private TableColumn<Cliente, String> col_nombreGestionVendedor;

    @FXML
    private TableColumn<Cliente, String> col_sedeGestionVendedor;

    @FXML
    private TableColumn<Cliente, String> col_telefonoGestionVendedor;


    private ObservableList<Usuario> clientesList = FXCollections.observableArrayList();

    private Usuario usuario = null;

    // Variables para registrar usuarios
    private String mensajeExito = String.format("-fx-text-fill: GREEN;");
    private String estiloMensajeExito = String.format("-fx-border-color: #A9A9A9; -fx-border-width: 2; -fx-border-radius: 5;");

    private String mensaje = String.format("-fx-text-fill: black;");
    private String mensajeError = String.format("-fx-text-fill: RED;");
    private String estiloMensajeError = String.format("-fx-border-color: RED; -fx-border-width: 2; -fx-border-radius: 5;");
    @FXML
    private TextField txtNombre, txtApellido;

    @FXML
    private TextField txtMail;
    @FXML
    private TextField txtDocumento;
    @FXML
    private TextField txtTelefono;
    @FXML
    private TextField txtDireccion_cliente;
    @FXML
    private DatePicker dtpNacimiento;
    @FXML
    MenuItem firstItem;
    @FXML
    MenuItem secondItem;

    @FXML
    private Label validacionRegistroLabel;

    /**
     * CREATE - Registrar Usuario
     * @throws IOException
     */
    //Para validar los campos de usuario y contraseña

    private void crearActualizarUsuario(boolean crear) {

        validacionRegistroLabel.setText("");
        txtNombre.setStyle(null);
        txtApellido.setStyle(null);
        txtMail.setStyle(null);
        txtDocumento.setStyle(null);
        txtTelefono.setStyle(null);
        dtpNacimiento.setStyle(null);
        txtDireccion_cliente.setStyle(null);
        // Cuando los campos están en blanco
        if(txtNombre.getText().isEmpty() ||
                txtApellido.getText().isEmpty() ||
                txtMail.getText().isEmpty()|| txtDocumento.getText().isEmpty() ||
                txtTelefono.getText().isEmpty() || dtpNacimiento.getValue()==null ||
                txtDireccion_cliente.getText().isEmpty())
        {
            validacionRegistroLabel.setStyle(mensajeError);
            if(txtNombre.getText().isEmpty() &&
                    txtApellido.getText().isEmpty() &&
                    txtMail.getText().isEmpty()  && txtDocumento.getText().isEmpty() &&
                    txtTelefono.getText().isEmpty() && dtpNacimiento.getValue()==null &&
                    txtDireccion_cliente.getText().isEmpty() )
            {
                validacionRegistroLabel.setText("Se requieren todos los campos!");
                txtNombre.setStyle(estiloMensajeError);
                txtApellido.setStyle(estiloMensajeError);
                txtMail.setStyle(estiloMensajeError);
                txtDocumento.setStyle(estiloMensajeError);
                txtTelefono.setStyle(estiloMensajeError);
                dtpNacimiento.setStyle(estiloMensajeError);
                txtDireccion_cliente.setStyle(estiloMensajeError);
                new Shake(txtNombre).play();
                new Shake(txtApellido).play();
                new Shake(txtMail).play();
                new Shake(txtDocumento).play();
                new Shake(txtTelefono).play();
                new Shake(dtpNacimiento).play();
                new Shake(txtDireccion_cliente).play();
            } else {
                validacionRegistroLabel.setText("Algunos campos están vacíos!");
                boolean validado = this.validaciones(crear);
                if (validado) {
                    this.guardarActualizarUsuario(crear);
                    this.refreshTable();

                }
            }
        } else {
            boolean validado = this.validaciones(crear);
            if (validado) {
                this.guardarActualizarUsuario(crear);
                this.refreshTable();
            }
        }
    }

    @FXML
    protected void bttnNuevoUsuarioClicked() throws IOException{
        this.crearActualizarUsuario(true);
    }


    private boolean validaciones(boolean crear) {
        // Validacion contraseña
        boolean validado = true;
        validacionRegistroLabel.setText("");

        // Validacion de telefono
        if (!ValidacionesClientes.validarTelefono(txtTelefono.getText()))
        {
            validado = false;
            String textoError = "Formato de telefono incorrecto!";
            System.out.println(textoError);
            validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
            validacionRegistroLabel.setStyle(mensajeError);
            txtTelefono.setStyle(estiloMensajeError);
            new FadeIn(txtTelefono).play();
        }
        // Se comprueba la longitud del nombre del usuario
        if (txtNombre.getText().length() < 4 ||  txtNombre.getText().length() > 20)
        {
            validado = false;
            String textoError = "El usuario debe tener de 4 a 20 caracteres!";
            validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
            validacionRegistroLabel.setStyle(mensajeError);
            txtNombre.setStyle(estiloMensajeError);
            new FadeIn(txtNombre).play();
        }
        // Se comprueba la longitud del nombre del usuario
        if (txtApellido.getText().length() < 4 ||  txtApellido.getText().length() > 20)
        {
            validado = false;
            String textoError = "El apellido debe tener de 4 a 20 caracteres!";
            validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
            validacionRegistroLabel.setStyle(mensajeError);
            txtApellido.setStyle(estiloMensajeError);
            new FadeIn(txtApellido).play();
        }
        // Validación Cédula
        if (!ValidacionesClientes.validarCedula(txtDocumento.getText()))
        {
            validado = false;
            String textoError = "Formato de la cédula incorrecto!";
            validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
            validacionRegistroLabel.setStyle(mensajeError);
            txtDocumento.setStyle(estiloMensajeError);
            new FadeIn(txtDocumento).play();
        } else if (SQL_Cliente.existeCliente_Cedula(txtDocumento.getText()) && crear) {
            // Validacion para saber si el usuario con esa cédula ya existe
            System.out.println(SQL_Cliente.existeCliente_Cedula(txtDocumento.getText()));
            validado = false;
            String textoError = "Un usuario con ese número de cédula ya existe!";
            validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
            validacionRegistroLabel.setStyle(mensajeError);
            txtDocumento.setStyle(estiloMensajeError);
            new FadeIn(txtDocumento).play();
        }
        // Validación Email
        if (!ValidacionesClientes.validarEmail(txtMail.getText()))
        {
            validado = false;
            String textoError = "Formato de email incorrecto!";
            System.out.println(textoError);
            validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
            validacionRegistroLabel.setStyle(mensajeError);
            txtMail.setStyle(estiloMensajeError);
            new FadeIn(txtMail).play();
        }
        // Validación Direccion
        if (!ValidacionesClientes.validarDireccion(txtDireccion_cliente.getText()))
        {
            validado = false;
            String textoError = "Formato de direccion incorrecto!";
            System.out.println(textoError);
            validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
            validacionRegistroLabel.setStyle(mensajeError);
            txtDireccion_cliente.setStyle(estiloMensajeError);
            new FadeIn(txtDireccion_cliente).play();
        }
        // Validación Fecha
        if (dtpNacimiento.getValue()==null && !ValidacionesClientes.validarFecha(String.valueOf(dtpNacimiento.getValue())))
        {
            validado = false;
            String textoError = "Formato de fecha incorrecto!";
            validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
            validacionRegistroLabel.setStyle(mensajeError);
            dtpNacimiento.setStyle(estiloMensajeError);
            new FadeIn(dtpNacimiento).play();
        }

        // Mensaje si el ingreso es correcto
        return validado;
    }

    private void validadoLabelSet() {
        validacionRegistroLabel.setText("");
        System.out.println("Pasó Validaciones");
        validacionRegistroLabel.setStyle(mensajeExito);
        validacionRegistroLabel.setText("Operación Exitosa!");
        txtNombre.setStyle(estiloMensajeExito);
        new Tada(validacionRegistroLabel).play();
    }

    public void guardarActualizarUsuario(boolean crear) {
        try {
            Cliente clienteModelo = new Cliente();


            clienteModelo.setNombre(txtNombre.getText());
            clienteModelo.setApellido(txtApellido.getText());
            clienteModelo.setCedula_cliente(txtDocumento.getText());
            clienteModelo.setEmail(txtMail.getText());
            clienteModelo.setDireccion(txtDireccion_cliente.getText());


            DateTimeFormatter fechaHoraFormato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String stringDataFormateada = dtpNacimiento.getValue().format(fechaHoraFormato);
            Date fechaNacimientoFormat = new SimpleDateFormat("dd/MM/yyyy").parse(stringDataFormateada);
            clienteModelo.setFecha_nacimiento(fechaNacimientoFormat);
            clienteModelo.setTelefono(txtTelefono.getText());


            //Traer sede y cedula de creado por
            clienteModelo.setCedula_creado_por(LoginController.obtenerUsuarioLogeado().getCedula());
            clienteModelo.setSede(LoginController.obtenerUsuarioLogeado().getSede());



            // SI la orden es para crear, o para actualizar, llamo al metodo respectivo
            if (crear)
                SQL_Cliente.crearCliente(clienteModelo);
            else
                SQL_Cliente.editarClientes(clienteModelo.getCedula_cliente(), clienteModelo);

            this.validadoLabelSet();
            this.limpiar();

        } catch (Exception e) {
            System.err.println(e);
            JOptionPane.showMessageDialog(null,"Error registrando el usuario");
        }
    }

    public void limpiar() {
        txtNombre.setText("");
        txtApellido.setText("");
        txtDocumento.setText("");
        txtMail.setText("");
        txtTelefono.setText("");
        txtDireccion_cliente.setText("");
        dtpNacimiento.setValue(null);
    }

    /**
     * UPDATE - READ - DELETE
     */
    private void loadData() {
        refreshTable();

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
        col_id_tipo_usuarioGestionVendedor.setCellValueFactory(new PropertyValueFactory<>("id_tipo_usuario"));
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

    //@FXML
    private void refreshTable() {
        clientesList.clear();
        this.readCliente();
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
        txtNombre.setStyle(null);
        txtApellido.setStyle(null);
        txtMail.setStyle(null);
        txtDocumento.setStyle(null);
        txtTelefono.setStyle(null);
        dtpNacimiento.setStyle(null);
        txtDireccion_cliente.setStyle(null);
        if(txtDocumento.getText().isEmpty())
        {
            validacionRegistroLabel.setStyle(mensajeError);
            new Shake(txtDocumento).play();
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
        if (!ValidacionesClientes.validarCedula(txtDocumento.getText())) {
            validado = false;
            String textoError = "Formato de la cédula incorrecto!";
            validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
            validacionRegistroLabel.setStyle(mensajeError);
            txtDocumento.setStyle(estiloMensajeError);
            new FadeIn(txtDocumento).play();
        }

        if (!SQL_Cliente.existeCliente_Cedula(txtDocumento.getText())) {
            // Validacion para saber si el usuario con esa cédula ya existe
            validado = false;
            String textoError = "Un usuario con ese número de cédula NO existe!";
            validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
            validacionRegistroLabel.setStyle(mensajeError);
            txtDocumento.setStyle(estiloMensajeError);
            new FadeIn(txtDocumento).play();
        }
        return validado;
    }

    private void llenarCamposPorCedula() {
        String cedula_cliente = txtDocumento.getText();
        try {
            ResultSet result = SQL_Cliente.obtenerCliente_Cedula(cedula_cliente);
            while (result.next()) {
                Cliente readCliente = new Cliente();

                readCliente.setCedula_cliente(result.getString("cedula_cliente"));
                readCliente.setEmail(result.getString("email"));
                readCliente.setNombre(result.getString("nombre"));
                readCliente.setApellido(result.getString("apellido"));
                readCliente.setFecha_modificado(result.getDate("fecha_modificado"));
                readCliente.setFecha_creacion(result.getDate("fecha_creacion"));
                readCliente.setDireccion(result.getString("direccion"));
                readCliente.setTelefono(result.getString("telefono"));
                readCliente.setActivo(result.getBoolean("activo"));
                readCliente.setFecha_nacimiento(result.getDate("fecha_nacimiento"));
                readCliente.setId_tipo_usuario(result.getInt("id_tipo_usuario"));
                readCliente.setCedula_creado_por(result.getString("cedula_creado_por"));
                readCliente.setSede(result.getString("sede"));

                // Cambio valores en los labels
                txtNombre.setText(readCliente.getNombre());
                txtApellido.setText(readCliente.getApellido());
                txtMail.setText(readCliente.getEmail());
                txtTelefono.setText(readCliente.getTelefono());
                dtpNacimiento.setValue(LocalDate.parse(readCliente.getFecha_nacimiento().toString()));
                txtDireccion_cliente.setText(readCliente.getDireccion());


            }
        } catch(SQLException exception) {
            throw new RuntimeException(exception);
        }

    }

    // Borrar - poner inactivo
    @FXML
    private void btnBorrarClicked() {
        String cedula_cliente = txtDocumento.getText();
        if (SQL_Cliente.existeCliente_Cedula(cedula_cliente)) {
            try {
                ResultSet result = SQL_Cliente.obtenerCliente_Cedula(cedula_cliente);
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
            txtDocumento.setStyle(estiloMensajeError);
            new FadeIn(txtDocumento).play();
        }
        this.refreshTable();
    }

    // Actualizar
    @FXML
    private void btnActualizarClicked() {
        this.crearActualizarUsuario(false);
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
        this.readCliente();
        this.loadData();
        tableGestionVendedor.setItems(clientesList.sorted());
    }

}


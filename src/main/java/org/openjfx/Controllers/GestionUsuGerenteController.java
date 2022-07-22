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
import org.openjfx.Models.Sede.SQL_Sede;
import org.openjfx.Models.Usuario.SQL_Usuario;
import org.openjfx.Models.Usuario.Usuario;
import org.openjfx.Models.Usuario.Utils.Hash;
import org.openjfx.Models.Usuario.Utils.Rol;
import org.openjfx.Models.Usuario.Utils.Validaciones;

import javax.swing.*;

public class GestionUsuGerenteController implements Initializable {

    // Variables para Actualizar, Leer y Borrar Usuarios
    @FXML
    private Button btnSalir;
    @FXML
    private TableView<Usuario> tableGestionGerente;
    @FXML
    private TableColumn<Usuario,String> col_idGestionGerente;
    @FXML
    private TableColumn<Usuario,String> col_cedulaGestionGerente;
    @FXML
    private TableColumn<Usuario,String> col_emailGestionGerente;
    @FXML
    private TableColumn<Usuario,String> col_nombreGestionGerente;
    @FXML
    private TableColumn<Usuario,String> col_apellidoGestionGerente;
    @FXML
    private TableColumn<Usuario, Date> col_modificarGestionGerente;
    @FXML
    private TableColumn<Usuario, Date> col_joinedGestionGerente;
    @FXML
    private TableColumn<Usuario, String> col_cargoGestionGerente;

    @FXML
    private TableColumn<Usuario, String> col_sedeGestionGerente;

    @FXML
    private TableColumn<Usuario, String> col_telefonoGestionGerente;
    @FXML
    private TableColumn<Usuario, Boolean> col_activoGestionGerente;
    @FXML
    private TableColumn<Usuario, Date> col_nacimientoGestionGerente;
    @FXML
    private TableColumn<Usuario, String> col_last_sessionGestionGerente;
    @FXML
    private TableColumn<Usuario, String> col_creadoPorGestionGerente;



    private ObservableList<Usuario> usuariosList = FXCollections.observableArrayList();

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
    private TextField txtPassword, txtPasswordConfirm;
    @FXML
    private TextField txtMail;
    @FXML
    private TextField txtDocumento;
    @FXML
    private TextField txtTelefono;
    @FXML
    private DatePicker dtpNacimiento;
    @FXML
    private SplitMenuButton cargo;
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
        txtPasswordConfirm.setStyle(null);
        txtApellido.setStyle(null);
        txtPassword.setStyle(null);
        txtMail.setStyle(null);
        txtDocumento.setStyle(null);
        txtTelefono.setStyle(null);
        dtpNacimiento.setStyle(null);
        cargo.setStyle(null);
        // Cuando los campos están en blanco
        if(txtNombre.getText().isEmpty() || txtPasswordConfirm.getText().isEmpty() ||
                txtApellido.getText().isEmpty() || txtPassword.getText().isEmpty() ||
                txtMail.getText().isEmpty()|| txtDocumento.getText().isEmpty() ||
                txtTelefono.getText().isEmpty() || dtpNacimiento.getValue()==null ||
                cargo.getText().equals("Cargo"))
        {
            validacionRegistroLabel.setStyle(mensajeError);
            if(txtNombre.getText().isEmpty() && txtPasswordConfirm.getText().isEmpty() &&
                    txtApellido.getText().isEmpty() && txtPassword.getText().isEmpty() &&
                    txtMail.getText().isEmpty()  && txtDocumento.getText().isEmpty() &&
                    txtTelefono.getText().isEmpty() && dtpNacimiento.getValue()==null &&
                    cargo.getText().equals("Cargo"))
            {
                validacionRegistroLabel.setText("Se requieren todos los campos!");
                txtNombre.setStyle(estiloMensajeError);
                txtPasswordConfirm.setStyle(estiloMensajeError);
                txtApellido.setStyle(estiloMensajeError);
                txtPassword.setStyle(estiloMensajeError);
                txtMail.setStyle(estiloMensajeError);
                txtDocumento.setStyle(estiloMensajeError);
                txtTelefono.setStyle(estiloMensajeError);
                dtpNacimiento.setStyle(estiloMensajeError);
                cargo.setStyle(estiloMensajeError);
                new Shake(txtNombre).play();
                new Shake(txtPasswordConfirm).play();
                new Shake(txtApellido).play();
                new Shake(txtPassword).play();
                new Shake(txtMail).play();
                new Shake(txtDocumento).play();
                new Shake(txtTelefono).play();
                new Shake(dtpNacimiento).play();
                new Shake(cargo).play();
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
    @FXML
    private void setFirstItem() {
        cargo.setText(firstItem.getText());
    }

    @FXML
    private void setSecondItem() {
        cargo.setText(secondItem.getText());
    }

    private boolean validaciones(boolean crear) {
        // Validacion contraseña
        boolean validado = true;
        validacionRegistroLabel.setText("");
        if (!Validaciones.validarPassword(txtPassword.getText()))
        {
            validado = false;
            String textoError = "Formato de contraseña incorrecto!";
            validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
            validacionRegistroLabel.setStyle(mensajeError);
            txtPassword.setStyle(estiloMensajeError);
            new FadeIn(txtPassword).play();
        }
        // Validacion confirmación de contraseña
        if (!Validaciones.validarPassword(txtPasswordConfirm.getText()))
        {
            validado = false;
            String textoError = "Formato de confirmación de contraseña incorrecto!";
            validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
            validacionRegistroLabel.setStyle(mensajeError);
            txtPasswordConfirm.setStyle(estiloMensajeError);
            new FadeIn(txtPasswordConfirm).play();
        }
        // Validacion contraseñas iguales
        if (!txtPasswordConfirm.getText().equals(txtPassword.getText()))
        {
            validado = false;
            String textoError = "Contraseñas no coinciden!";
            validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
            validacionRegistroLabel.setStyle(mensajeError);
            txtPasswordConfirm.setStyle(estiloMensajeError);
            new FadeIn(txtPasswordConfirm).play();
            txtPassword.setStyle(estiloMensajeError);
            new FadeIn(txtPassword).play();
        }
        // Validacion de telefono
        if (!Validaciones.validarTelefono(txtTelefono.getText()))
        {
            validado = false;
            String textoError = "Formato de telefono incorrecto!";
            //System.out.println(textoError);
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
        if (!Validaciones.validarCedula(txtDocumento.getText()))
        {
            validado = false;
            String textoError = "Formato de la cédula incorrecto!";
            validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
            validacionRegistroLabel.setStyle(mensajeError);
            txtDocumento.setStyle(estiloMensajeError);
            new FadeIn(txtDocumento).play();
        } else if (SQL_Usuario.existeUsuario_Cedula(txtDocumento.getText()) && crear) {
            // Validacion para saber si el usuario con esa cédula ya existe
            System.out.println(SQL_Usuario.existeUsuario_Cedula(txtDocumento.getText()));
            validado = false;
            String textoError = "Un usuario con ese número de cédula ya existe!";
            validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
            validacionRegistroLabel.setStyle(mensajeError);
            txtDocumento.setStyle(estiloMensajeError);
            new FadeIn(txtDocumento).play();
        }
        else if (!SQL_Usuario.puedoModificarGerente(txtDocumento.getText()) && !crear) {
            // Validacion para saber si tengo permisos para modificar este usuario
            System.out.println(SQL_Usuario.existeUsuario_Cedula(txtDocumento.getText()));
            validado = false;
            String textoError = "No tiene permisos para modificar este usuario!";
            validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
            validacionRegistroLabel.setStyle(mensajeError);
            txtDocumento.setStyle(estiloMensajeError);
            new FadeIn(txtDocumento).play();
        }
        // Validación Email
        if (!Validaciones.validarEmail(txtMail.getText()))
        {
            validado = false;
            String textoError = "Formato de email incorrecto!";
            //System.out.println(textoError);
            validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
            validacionRegistroLabel.setStyle(mensajeError);
            txtMail.setStyle(estiloMensajeError);
            new FadeIn(txtMail).play();
        }
        // Validación Fecha
        if (dtpNacimiento.getValue()==null && !Validaciones.validarFecha(String.valueOf(dtpNacimiento.getValue())))
        {
            validado = false;
            String textoError = "Formato de fecha incorrecto!";
            validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
            validacionRegistroLabel.setStyle(mensajeError);
            dtpNacimiento.setStyle(estiloMensajeError);
            new FadeIn(dtpNacimiento).play();
        }
        // Validacion Cargo
        if (cargo.getText().equals("Seleccionar Cargo") || (!cargo.getText().equals("Vendedor") && !cargo.getText().equals("Jefe de taller")) )
        {
            validado = false;
            String textoError = "Formato de cargo incorrecto!";
            validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
            validacionRegistroLabel.setStyle(mensajeError);
            cargo.setStyle(estiloMensajeError);
            new FadeIn(cargo).play();
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
        txtPassword.setStyle(estiloMensajeExito);
        new Tada(validacionRegistroLabel).play();
    }

    public void guardarActualizarUsuario(boolean crear) {
        try {
            Usuario usuarioModelo = new Usuario();

            String contraseña = txtPassword.getText();
            String contraseñaCifrada = Hash.encrypt(contraseña);


            usuarioModelo.setNombre(txtNombre.getText());
            usuarioModelo.setApellido(txtApellido.getText());
            usuarioModelo.setCedula(txtDocumento.getText());
            usuarioModelo.setContraseña(contraseñaCifrada);
            usuarioModelo.setEmail(txtMail.getText());

            DateTimeFormatter fechaHoraFormato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String stringDataFormateada = dtpNacimiento.getValue().format(fechaHoraFormato);
            Date fechaNacimientoFormat = new SimpleDateFormat("dd/MM/yyyy").parse(stringDataFormateada);
            usuarioModelo.setFecha_nacimiento(fechaNacimientoFormat);
            usuarioModelo.setTelefono(txtTelefono.getText());

            int idTipoUsuario = 0;
            if (cargo.getText().equals("Vendedor")) {
                idTipoUsuario = 4;
            } else if (cargo.getText().equals("Jefe de taller")) {
                idTipoUsuario = 3;
            }
            usuarioModelo.setId_tipo_usuario(idTipoUsuario);

            usuarioModelo.setCedula_creado_por(LoginController.obtenerUsuarioLogeado().getCedula());
            usuarioModelo.setSede(LoginController.obtenerUsuarioLogeado().getSede());


            // SI la orden es para crear, o para actualizar, llamo al metodo respectivo
            if (crear)
                SQL_Usuario.crearUsuario(usuarioModelo);
            else
                SQL_Usuario.editarUsuarios(usuarioModelo.getCedula(), usuarioModelo);

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
        txtPassword.setText("");
        txtPasswordConfirm.setText("");
        txtMail.setText("");
        txtTelefono.setText("");
        cargo.setText("Seleccionar Cargo");
        dtpNacimiento.setValue(null);
    }

    /**
     * UPDATE - READ - DELETE
     */
    private void loadData() {
        refreshTable();

        col_idGestionGerente.setCellValueFactory(new PropertyValueFactory<>("id_usuario"));
        col_cedulaGestionGerente.setCellValueFactory(new PropertyValueFactory<>("cedula"));
        col_emailGestionGerente.setCellValueFactory(new PropertyValueFactory<>("email"));
        col_nombreGestionGerente.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        col_apellidoGestionGerente.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        col_modificarGestionGerente.setCellValueFactory(new PropertyValueFactory<>("modificado"));
        col_cargoGestionGerente.setCellValueFactory(new PropertyValueFactory<>("user_type"));
        col_telefonoGestionGerente.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        col_joinedGestionGerente.setCellValueFactory(new PropertyValueFactory<>("joined"));
        col_activoGestionGerente.setCellValueFactory(new PropertyValueFactory<>("activo"));
        col_nacimientoGestionGerente.setCellValueFactory(new PropertyValueFactory<>("fecha_nacimiento"));
        col_last_sessionGestionGerente.setCellValueFactory(new PropertyValueFactory<>("last_session"));
        col_creadoPorGestionGerente.setCellValueFactory(new PropertyValueFactory<>("cedula_creado_por"));
//        col_sedeGestionGerente.setCellValueFactory(new PropertyValueFactory<>("sede"));
        tableGestionGerente.setItems(usuariosList.sorted());

    }

    private void readUsers() {
        try {
            ResultSet result = SQL_Usuario.obtenerTodosUsuariosPorRol(Rol.GERENTE);
            while (result.next()) {
                Usuario readUsuario = new Usuario();

                readUsuario.setId_usuario(result.getInt("id_usuario"));
                readUsuario.setCedula(result.getString("cedula"));
                readUsuario.setContraseña(result.getString("contraseña"));
                readUsuario.setEmail(result.getString("email"));
                readUsuario.setNombre(result.getString("nombre"));
                readUsuario.setApellido(result.getString("apellido"));
                readUsuario.setModificado(result.getDate("modificado"));
                readUsuario.setAvatar(result.getString("avatar"));
                readUsuario.setJoined(result.getDate("joined"));
                readUsuario.setUser_type(Rol.valueOf(result.getString("user_type")));
                readUsuario.setTelefono(result.getString("telefono"));
                readUsuario.setActivo(result.getBoolean("activo"));
                readUsuario.setFecha_nacimiento(result.getDate("fecha_nacimiento"));
                readUsuario.setLast_session(result.getString("last_session"));
                readUsuario.setCedula_creado_por(result.getString("cedula_creado_por"));
                readUsuario.setSede(result.getString("sede"));

                usuariosList.add(readUsuario);
            }
            usuariosList.sorted();
        } catch(SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    //@FXML
    private void refreshTable() {
        usuariosList.clear();
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
            EmpresaAutosABC.setRoot("menuGerente");
        }
    }
    @FXML
    protected void btnInicio() throws IOException {
        EmpresaAutosABC.setRoot("menuGerente");
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
        txtPasswordConfirm.setStyle(null);
        txtApellido.setStyle(null);
        txtPassword.setStyle(null);
        txtMail.setStyle(null);
        txtDocumento.setStyle(null);
        txtTelefono.setStyle(null);
        dtpNacimiento.setStyle(null);
        cargo.setStyle(null);
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
        if (!Validaciones.validarCedula(txtDocumento.getText())) {
            validado = false;
            String textoError = "Formato de la cédula incorrecto!";
            validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
            validacionRegistroLabel.setStyle(mensajeError);
            txtDocumento.setStyle(estiloMensajeError);
            new FadeIn(txtDocumento).play();
        }

        if (!SQL_Usuario.existeUsuario_Cedula(txtDocumento.getText())) {
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
        String cedula = txtDocumento.getText();
        try {
            ResultSet result = SQL_Usuario.obtenerUsuario_CedulaGerente(cedula);

            while (result.next()) {
                Usuario readUsuario = new Usuario();

                readUsuario.setId_usuario(result.getInt("id_usuario"));
                readUsuario.setCedula(result.getString("cedula"));
                readUsuario.setContraseña(result.getString("contraseña"));
                readUsuario.setEmail(result.getString("email"));
                readUsuario.setNombre(result.getString("nombre"));
                readUsuario.setApellido(result.getString("apellido"));
                readUsuario.setModificado(result.getDate("modificado"));
                readUsuario.setAvatar(result.getString("avatar"));
                readUsuario.setJoined(result.getDate("joined"));
                readUsuario.setUser_type(Rol.valueOf(result.getString("user_type")));
                readUsuario.setTelefono(result.getString("telefono"));
                readUsuario.setActivo(result.getBoolean("activo"));
                readUsuario.setFecha_nacimiento(result.getDate("fecha_nacimiento"));
                readUsuario.setLast_session(result.getString("last_session"));
                readUsuario.setId_tipo_usuario(result.getInt("id_tipo_usuario"));
                readUsuario.setCedula_creado_por(result.getString("cedula_creado_por"));
                readUsuario.setSede(result.getString("sede"));

                // Cambio valores en los labels
                txtNombre.setText(readUsuario.getNombre());
                txtPasswordConfirm.setText(Hash.decrypt(readUsuario.getContraseña()));
                txtApellido.setText(readUsuario.getApellido());
                txtPassword.setText(Hash.decrypt(readUsuario.getContraseña()));
                txtMail.setText(readUsuario.getEmail());
                txtTelefono.setText(readUsuario.getTelefono());
                dtpNacimiento.setValue(LocalDate.parse(readUsuario.getFecha_nacimiento().toString()));

                String rol = "";
                if (readUsuario.getUser_type().toString().equals(Rol.JEFE_TALLER.toString())) {
                    rol = "Jefe de Taller";
                }
                else {
                    rol = "Vendedor";
                }
                cargo.setText(rol);

            }
        } catch(SQLException exception) {
            throw new RuntimeException(exception);
        }

    }

    // Borrar - poner inactivo
    @FXML
    private void btnBorrarClicked() {
        String cedula = txtDocumento.getText();
        if (SQL_Usuario.existeUsuario_Cedula(cedula)) {
            try {
                ResultSet result = SQL_Usuario.obtenerUsuario_Cedula(cedula);
                result.next();
                boolean activo = result.getBoolean("activo");
                SQL_Usuario.cambiarEstadoUsuarioPorCedula(cedula, activo);
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
        this.readUsers();
        this.loadData();
        tableGestionGerente.setItems(usuariosList.sorted());
    }

}


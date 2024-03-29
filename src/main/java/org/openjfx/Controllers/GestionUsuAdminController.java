package org.openjfx.Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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

public class GestionUsuAdminController implements Initializable {

    // Variables para Actualizar, Leer y Borrar Usuarios
    @FXML
    private Button btnSalir;
    @FXML
    private TableView<Usuario> tableGestionAdmin;
    @FXML
    private TableColumn<Usuario,String> col_idGestionAdmin;
    @FXML
    private TableColumn<Usuario,String> col_cedulaGestionAdmin;
    @FXML
    private TableColumn<Usuario,String> col_emailGestionAdmin;
    @FXML
    private TableColumn<Usuario,String> col_nombreGestionAdmin;
    @FXML
    private TableColumn<Usuario,String> col_apellidoGestionAdmin;
    @FXML
    private TableColumn<Usuario, Date> col_modificarGestionAdmin;
    @FXML
    private TableColumn<Usuario, Date> col_joinedGestionAdmin;
    @FXML
    private TableColumn<Usuario, String> col_cargoGestionAdmin;
    @FXML
    private TableColumn<Usuario, String> col_sedeGestionAdmin;
    @FXML
    private TableColumn<Usuario, String> col_telefonoGestionAdmin;
    @FXML
    private TableColumn<Usuario, Boolean> col_activoGestionAdmin;
    @FXML
    private TableColumn<Usuario, Date> col_nacimientoGestionAdmin;
    @FXML
    private TableColumn<Usuario, String> col_last_sessionGestionAdmin;
    @FXML
    private TableColumn<Usuario, String> col_creadoPorGestionAdmin;

    private ObservableList<Usuario> usuariosList = FXCollections.observableArrayList();

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
    private SplitMenuButton sede;

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
        sede.setStyle(null);
        // Cuando los campos están en blanco
        if(txtNombre.getText().isEmpty() || txtPasswordConfirm.getText().isEmpty() ||
                txtApellido.getText().isEmpty() || txtPassword.getText().isEmpty() ||
                txtMail.getText().isEmpty()|| txtDocumento.getText().isEmpty() ||
                txtTelefono.getText().isEmpty() || dtpNacimiento.getValue()==null ||
                cargo.getText().equals("Cargo") || sede.getText().equals("Sede"))
        {
            validacionRegistroLabel.setStyle(mensajeError);
            if(txtNombre.getText().isEmpty() && txtPasswordConfirm.getText().isEmpty() &&
                    txtApellido.getText().isEmpty() && txtPassword.getText().isEmpty() &&
                    txtMail.getText().isEmpty()  && txtDocumento.getText().isEmpty() &&
                    txtTelefono.getText().isEmpty() && dtpNacimiento.getValue()==null &&
                    cargo.getText().equals("Cargo") && sede.getText().equals("Sede")) {

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
                sede.setStyle(estiloMensajeError);
                new Shake(txtNombre).play();
                new Shake(txtPasswordConfirm).play();
                new Shake(txtApellido).play();
                new Shake(txtPassword).play();
                new Shake(txtMail).play();
                new Shake(txtDocumento).play();
                new Shake(txtTelefono).play();
                new Shake(dtpNacimiento).play();
                new Shake(cargo).play();
                new Shake(sede).play();
            }
            else {
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

    private void setTextSedeSplitButton() {
        for (MenuItem item:
                sede.getItems()) {
            item.setOnAction(e -> {
                sede.setText(item.getText());
            });
        }
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
            validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
            validacionRegistroLabel.setStyle(mensajeError);
            txtTelefono.setStyle(estiloMensajeError);
            new FadeIn(txtTelefono).play();
        }
        // Validacion de sede cuando el rol es admin
        if (cargo.getText().equals("Administrador") && !sede.getText().equals("Sede"))
        {
            validado = false;
            String textoError = "Adminstrador no puede tener sede asociada!";
            validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
            validacionRegistroLabel.setStyle(mensajeError);
            sede.setStyle(estiloMensajeError);
            new FadeIn(sede).play();
        }
        // Validacion de sede cuando el rol es gerente
        if (cargo.getText().equals("Gerente") && sede.getText().equals("Sede"))
        {
            validado = false;
            String textoError = "Gerente debe de tener una sede asociada!";
            validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
            validacionRegistroLabel.setStyle(mensajeError);
            sede.setStyle(estiloMensajeError);
            new FadeIn(sede).play();
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
        // Validación Cédula, primero revisa el formato
        // Luego si el formato está correcto, entonces revisa que el usuario con ese número de cédula no exista si la orden es crear
        // Si la orden es modificar, revisa que la cédula sea de un gerente o un administrador
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
        } else if (!SQL_Usuario.puedoModificarAdmin(txtDocumento.getText()) && !crear) {
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
        if (cargo.getText().equals("Cargo") || (!cargo.getText().equals("Gerente") && !cargo.getText().equals("Administrador")) )
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

            String sedeNombre = sede.getText();
            ResultSet resultSede = SQL_Sede.obtenerSede_Nombre(sedeNombre);

            if(resultSede.next())
            {
                usuarioModelo.setSede(sede.getText());
            } else {
                usuarioModelo.setSede("");
            }

            int idTipoUsuario = 0;
            if (cargo.getText().equals("Administrador")) {
                idTipoUsuario = 1;
            } else if (cargo.getText().equals("Gerente")) {
                idTipoUsuario = 2;
            }
            usuarioModelo.setId_tipo_usuario(idTipoUsuario);


            usuarioModelo.setCedula_creado_por(LoginController.obtenerUsuarioLogeado().getCedula());

            // SI la orden es para crear, o para actualizar, llamo al metodo respectivo
            if (crear)
                SQL_Usuario.crearUsuario(usuarioModelo);
            else
                SQL_Usuario.editarUsuarios(usuarioModelo.getCedula(), usuarioModelo);

            this.validadoLabelSet();
            this.limpiar();

        } catch (Exception e) {
            System.err.println(e);
            Dialogs.showError("Error en la base de datos", "Error registrando el usuario");
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
        cargo.setText("Cargo");
        sede.setText("Sede");
        dtpNacimiento.setValue(null);
    }

    /**
     * UPDATE - READ - DELETE
     */
    private void loadData() {
        refreshTable();

        col_idGestionAdmin.setCellValueFactory(new PropertyValueFactory<>("id_usuario"));
        col_cedulaGestionAdmin.setCellValueFactory(new PropertyValueFactory<>("cedula"));
        col_emailGestionAdmin.setCellValueFactory(new PropertyValueFactory<>("email"));
        col_nombreGestionAdmin.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        col_apellidoGestionAdmin.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        col_modificarGestionAdmin.setCellValueFactory(new PropertyValueFactory<>("modificado"));
        col_cargoGestionAdmin.setCellValueFactory(new PropertyValueFactory<>("user_type"));
        col_telefonoGestionAdmin.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        col_joinedGestionAdmin.setCellValueFactory(new PropertyValueFactory<>("joined"));
        col_activoGestionAdmin.setCellValueFactory(new PropertyValueFactory<>("activo"));
        col_nacimientoGestionAdmin.setCellValueFactory(new PropertyValueFactory<>("fecha_nacimiento"));
        col_last_sessionGestionAdmin.setCellValueFactory(new PropertyValueFactory<>("last_session"));
        col_sedeGestionAdmin.setCellValueFactory(new PropertyValueFactory<>("sede"));
        col_creadoPorGestionAdmin.setCellValueFactory(new PropertyValueFactory<>("cedula_creado_por"));

        tableGestionAdmin.setItems(usuariosList.sorted());

    }


    private void loadSedes() {
        ResultSet respuesta = SQL_Sede.obtenerTodasSedesSet();
        try {
            ArrayList<MenuItem> itemSedes = new ArrayList<MenuItem>();
            while (respuesta.next())
            {
                MenuItem sedeItem = new MenuItem(respuesta.getString("nombre_sede"));
                itemSedes.add(sedeItem);
            }
            sede.getItems().addAll(itemSedes);

        } catch (SQLException e) {
                throw new RuntimeException(e);
        }
    }

    private void readUsers() {
        try {
            ResultSet result = SQL_Usuario.obtenerTodosUsuariosPorRol(Rol.ADMIN);
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
                readUsuario.setSede(result.getString("sede"));
                readUsuario.setCedula_creado_por(result.getString("cedula_creado_por"));
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
        if (Dialogs.showConfirm("Seleccione una opción", "¿Está seguro que quiere cancelar el registro?", Dialogs.YES, Dialogs.NO).equals(Dialogs.YES)) {
            EmpresaAutosABC.setRoot("menuAdmin");
        }
    }
    @FXML
    protected void btnInicio() throws IOException {
        EmpresaAutosABC.setRoot("menuAdmin");
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
        sede.setStyle(null);
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
            ResultSet result = SQL_Usuario.obtenerUsuario_CedulaAdmin(cedula);
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
                readUsuario.setSede(result.getString("sede"));
                readUsuario.setCedula_creado_por(result.getString("cedula_creado_por"));

                // Cambio valores en los labels
                txtNombre.setText(readUsuario.getNombre());
                txtPasswordConfirm.setText(Hash.decrypt(readUsuario.getContraseña()));
                txtApellido.setText(readUsuario.getApellido());
                txtPassword.setText(Hash.decrypt(readUsuario.getContraseña()));
                txtMail.setText(readUsuario.getEmail());
                txtTelefono.setText(readUsuario.getTelefono());
                dtpNacimiento.setValue(LocalDate.parse(readUsuario.getFecha_nacimiento().toString()));

                String rol = "";
                if (readUsuario.getUser_type().toString().equals(Rol.ADMIN.toString())) {
                    rol = "Administrador";
                }
                else {
                    rol = "Gerente";
                }
                cargo.setText(rol);

                String sedeUsuario = readUsuario.getSede();

                if(sedeUsuario.equals(""))
                    sede.setText("Sede");
                else
                    sede.setText(readUsuario.getSede());

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
        this.loadSedes();
        this.setTextSedeSplitButton();
        tableGestionAdmin.setItems(usuariosList.sorted());
    }

}

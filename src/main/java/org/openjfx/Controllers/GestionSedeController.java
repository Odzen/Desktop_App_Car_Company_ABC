/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
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
import org.openjfx.Models.Sede.Sede;
import org.openjfx.Models.Sede.Utils.ValidacionesSede;

import javax.swing.*;

/**
 * FXML Controller class
 *
 * @author mavel
 */
public class GestionSedeController implements Initializable {// Variables para Actualizar, Leer y Borrar Usuarios
    @FXML
    private TableView<Sede> tableGestionSedes;
    @FXML
    private TableColumn<Sede,String> col_idSede;
    @FXML
    private TableColumn<Sede,String> col_nombreSede;
    @FXML
    private TableColumn<Sede,String> col_telefonoSede;
    @FXML
    private TableColumn<Sede,String> col_direccionSede;
    @FXML
    private TableColumn<Sede, String> col_ciudadSede;
    @FXML
    private TableColumn<Sede, Date> col_fecha_modificacion_sede;
    @FXML
    private TableColumn<Sede, Date> col_fecha_creacion_sede;
    @FXML
    private TableColumn<Sede, Boolean> col_activo_sede;

    private ObservableList<Sede> sedesList = FXCollections.observableArrayList();


    // Variables para registrar sedes
    private String mensajeExito = String.format("-fx-text-fill: GREEN;");
    private String estiloMensajeExito = String.format("-fx-border-color: #A9A9A9; -fx-border-width: 2; -fx-border-radius: 5;");

    private String mensaje = String.format("-fx-text-fill: black;");
    private String mensajeError = String.format("-fx-text-fill: RED;");
    private String estiloMensajeError = String.format("-fx-border-color: RED; -fx-border-width: 2; -fx-border-radius: 5;");
    @FXML
    private TextField txtNombreSede, txtTelSede, txtDirSede, txtCiudad;

    @FXML
    private Label validacionRegistroLabel;

    /**
     * CREATE - Registrar Sede
     * @throws IOException
     */
    //Para validar los campos de sede

    private void crearActualizarSede(boolean crear) {

        validacionRegistroLabel.setText("");
        txtNombreSede.setStyle(null);
        txtTelSede.setStyle(null);
        txtDirSede.setStyle(null);
        txtCiudad.setStyle(null);
        // Cuando los campos están en blanco
        if(txtNombreSede.getText().isEmpty() || txtTelSede.getText().isEmpty() ||
                txtDirSede.getText().isEmpty() || txtCiudad.getText().isEmpty())
        {
            validacionRegistroLabel.setStyle(mensajeError);
            if(txtNombreSede.getText().isEmpty() && txtTelSede.getText().isEmpty() &&
                    txtDirSede.getText().isEmpty() && txtCiudad.getText().isEmpty())
            {
                validacionRegistroLabel.setText("Se requieren todos los campos!");
                txtNombreSede.setStyle(estiloMensajeError);
                txtTelSede.setStyle(estiloMensajeError);
                txtDirSede.setStyle(estiloMensajeError);
                txtCiudad.setStyle(estiloMensajeError);
                new Shake(txtNombreSede).play();
                new Shake(txtTelSede).play();
                new Shake(txtDirSede).play();
                new Shake(txtCiudad).play();
            } else {
                validacionRegistroLabel.setText("Algunos campos están vacíos!");
                boolean validado = this.validaciones(crear);
                if (validado) {
                    this.guardarActualizarUsuario(crear);
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
    protected void bttnNuevaSedeClicked() throws IOException{
        this.crearActualizarSede(true);
    }

    private boolean validaciones(boolean crear) {
        boolean validado = true;
        validacionRegistroLabel.setText("");
        // Validacion de telefono
        if (!ValidacionesSede.validarTelefono(txtTelSede.getText()))
        {
            validado = false;
            String textoError = "Formato de telefono incorrecto!";
            //System.out.println(textoError);
            validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
            validacionRegistroLabel.setStyle(mensajeError);
            txtTelSede.setStyle(estiloMensajeError);
            new FadeIn(txtTelSede).play();
        }
        // Se comprueba la longitud del nombre de la sede
        if (!ValidacionesSede.validarNombre(txtNombreSede.getText()))
        {
            validado = false;
            String textoError = "El nombre de la sede debe tener de 4 a 20 caracteres!";
            validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
            validacionRegistroLabel.setStyle(mensajeError);
            txtNombreSede.setStyle(estiloMensajeError);
            new FadeIn(txtNombreSede).play();
        }
        else if (SQL_Sede.existeSede_Nombre(txtNombreSede.getText()) && crear) {
            // Validacion para saber si el la sede con ese nombre ya existe
            System.out.println(SQL_Sede.existeSede_Nombre(txtNombreSede.getText()));
            validado = false;
            String textoError = "Una sede con ese nombre ya existe!";
            validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
            validacionRegistroLabel.setStyle(mensajeError);
            txtNombreSede.setStyle(estiloMensajeError);
            new FadeIn(txtNombreSede).play();
        }
        // Validación Ciudad
        if (!ValidacionesSede.validarCiudad(txtCiudad.getText()))
        {
            validado = false;
            String textoError = "Formato de ciudad incorrecto!";
            //System.out.println(textoError);
            validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
            validacionRegistroLabel.setStyle(mensajeError);
            txtCiudad.setStyle(estiloMensajeError);
            new FadeIn(txtCiudad).play();
        }
        // Validación Direccion
        if (!ValidacionesSede.validarDireccion(txtDirSede.getText()))
        {
            validado = false;
            String textoError = "Formato de direccion incorrecto!";
            //System.out.println(textoError);
            validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
            validacionRegistroLabel.setStyle(mensajeError);
            txtDirSede.setStyle(estiloMensajeError);
            new FadeIn(txtDirSede).play();
        }


        // Mensaje si el ingreso es correcto
        return validado;
    }

    private void validadoLabelSet() {
        validacionRegistroLabel.setText("");
        System.out.println("Pasó Validaciones");
        validacionRegistroLabel.setStyle(mensajeExito);
        validacionRegistroLabel.setText("Operación Exitosa!");
        new Tada(validacionRegistroLabel).play();
    }

    public void guardarActualizarUsuario(boolean crear) {
        try {
            Sede sede = new Sede();

            sede.setNombre_sede(txtNombreSede.getText());
            sede.setCiudad(txtCiudad.getText());
            sede.setDireccion(txtDirSede.getText());
            sede.setTelefono(txtTelSede.getText());

            // SI la orden es para crear, o para actualizar, llamo al metodo respectivo
            if (crear)
                SQL_Sede.crearSede(sede);
            else
                SQL_Sede.editarSede(sede.getNombre_sede(), sede);

            this.validadoLabelSet();
            this.limpiar();

        } catch (Exception e) {
            System.err.println(e);
            JOptionPane.showMessageDialog(null,"Error registrando la sede");
        }
    }

    public void limpiar() {
        txtNombreSede.setText("");
        txtCiudad.setText("");
        txtDirSede.setText("");
        txtTelSede.setText("");
    }

    /**
     * UPDATE - READ - DELETE
     */
    private void loadData() {
        refreshTable();

        col_idSede.setCellValueFactory(new PropertyValueFactory<>("id_sede"));
        col_nombreSede.setCellValueFactory(new PropertyValueFactory<>("nombre_sede"));
        col_direccionSede.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        col_telefonoSede.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        col_activo_sede.setCellValueFactory(new PropertyValueFactory<>("activo"));
        col_ciudadSede.setCellValueFactory(new PropertyValueFactory<>("ciudad"));
        col_fecha_creacion_sede.setCellValueFactory(new PropertyValueFactory<>("fecha_creacion"));
        col_fecha_modificacion_sede.setCellValueFactory(new PropertyValueFactory<>("fecha_modificados"));

        tableGestionSedes.setItems(sedesList.sorted());

    }
    private void loadSedes() {

        //respuesta = SQL_Sedes.getAllSedes()

        // while (respuesta.next())
        //      MenuItem firstItem;


    }

    private void readSedes() {
        try {
            ResultSet result = SQL_Sede.obtenerTodasSedesSet();
            while (result.next()) {
                Sede readSede = new Sede();

                readSede.setId_sede(result.getInt("id_sede"));
                readSede.setDireccion(result.getString("direccion"));
                readSede.setTelefono(result.getString("telefono"));
                readSede.setNombre_sede(result.getString("nombre_sede"));
                readSede.setActivo(result.getBoolean("activo"));
                readSede.setCiudad(result.getString("ciudad"));
                readSede.setFecha_creacion(result.getDate("fecha_creacion"));
                readSede.setFecha_modificado(result.getDate("fecha_modificado"));

                sedesList.add(readSede);
            }
            sedesList.sorted();
        } catch(SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    //@FXML
    private void refreshTable() {
        sedesList.clear();
        this.readSedes();
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
    protected void btnLimpiar() {
        this.limpiar();
    }

    // Buscar por cedula para llenar campos y registrar o borrar
    @FXML
    protected void btnBuscarNombre() {
        validacionRegistroLabel.setText("");
        txtDirSede.setStyle(null);
        txtNombreSede.setStyle(null);
        txtCiudad.setStyle(null);
        txtTelSede.setStyle(null);
        if(txtNombreSede.getText().isEmpty())
        {
            validacionRegistroLabel.setStyle(mensajeError);
            new Shake(txtNombreSede).play();
            validacionRegistroLabel.setText("El nombre está vacío!");
        }
        else {
            boolean validado = this.validacionNombre();
            if (validado) {
                this.llenarCamposPorNombreSede();
            }
        }
    }

    private boolean validacionNombre() {
        // Validación Cédula
        boolean validado = true;
        validacionRegistroLabel.setText("");
        if (!ValidacionesSede.validarNombre(txtNombreSede.getText())) {
            validado = false;
            String textoError = "Formato del nombre de la sede incorrecto!";
            validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
            validacionRegistroLabel.setStyle(mensajeError);
            txtNombreSede.setStyle(estiloMensajeError);
            new FadeIn(txtNombreSede).play();
        }

        if (!SQL_Sede.existeSede_Nombre(txtNombreSede.getText())) {
            // Validacion para saber si una sede con esa nombre ya existe
            validado = false;
            String textoError = "Una sede con ese nombre NO existe!";
            validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
            validacionRegistroLabel.setStyle(mensajeError);
            txtNombreSede.setStyle(estiloMensajeError);
            new FadeIn(txtNombreSede).play();
        }
        return validado;
    }

    private void llenarCamposPorNombreSede() {
        String nombre_sede = txtNombreSede.getText();
        try {
            ResultSet result = SQL_Sede.obtenerSede_Nombre(nombre_sede);
            while (result.next()) {
                Sede readSede = new Sede();

                readSede.setId_sede(result.getInt("id_sede"));
                readSede.setDireccion(result.getString("direccion"));
                readSede.setTelefono(result.getString("telefono"));
                readSede.setNombre_sede(result.getString("nombre_sede"));
                readSede.setActivo(result.getBoolean("activo"));
                readSede.setCiudad(result.getString("ciudad"));
                readSede.setFecha_creacion(result.getDate("fecha_creacion"));
                readSede.setFecha_modificado(result.getDate("fecha_modificado"));

                // Cambio valores en los labels
                txtNombreSede.setText(readSede.getNombre_sede());
                txtTelSede.setText(readSede.getTelefono());
                txtCiudad.setText(readSede.getCiudad());
                txtDirSede.setText(readSede.getDireccion());

            }
        } catch(SQLException exception) {
            throw new RuntimeException(exception);
        }

    }

    // Borrar - poner inactivo
    @FXML
    private void btnBorrarClicked() {
        String nombre = txtNombreSede.getText();
        if (SQL_Sede.existeSede_Nombre(nombre)) {
            try {
                ResultSet result = SQL_Sede.obtenerSede_Nombre(nombre);
                result.next();
                boolean activo = result.getBoolean("activo");
                SQL_Sede.cambiarEstadoUsuarioPorNombre(nombre, activo);
                this.validadoLabelSet();
                this.limpiar();

            } catch (SQLException exception) {
                throw new RuntimeException(exception);
            }
        }
        else {
            String textoError = "No existe una sede con ese nombre!";
            validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
            validacionRegistroLabel.setStyle(mensajeError);
            txtNombreSede.setStyle(estiloMensajeError);
            new FadeIn(txtNombreSede).play();
        }
        this.refreshTable();
    }

    // Actualizar
    @FXML
    private void btnActualizarClicked() {
        this.crearActualizarSede(false);
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
        this.readSedes();
        this.loadData();
        this.loadSedes();
        tableGestionSedes.setItems(sedesList.sorted());
    }
}



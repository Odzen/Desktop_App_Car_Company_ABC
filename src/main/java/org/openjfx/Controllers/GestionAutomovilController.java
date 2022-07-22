/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
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
import org.openjfx.Models.Automovil.Automovil;
import org.openjfx.Models.Automovil.SQL_Automovil;
import org.openjfx.Models.Automovil.Utils.ValidacionesAutomovil;

import javax.swing.*;

/**
 * FXML Controller class
 *
 * @author mavel
 */
public class GestionAutomovilController implements Initializable {// Variables para Actualizar, Leer y Borrar Usuarios
    @FXML
    private TableView<Automovil> tableGestionAutomovil;
    @FXML
    private TableColumn<Automovil,String> col_PlacaAuto;
    @FXML
    private TableColumn<Automovil,String> col_marcaAuto;
    @FXML
    private TableColumn<Automovil,Integer> col_cilindrajeAuto;
    @FXML
    private TableColumn<Automovil,String> col_colorAuto;
    @FXML
    private TableColumn<Automovil, String> col_modeloAuto;
    @FXML
    private TableColumn<Automovil, String> col_yearAuto;
    @FXML
    private TableColumn<Automovil, Integer> col_precioAuto;
    @FXML
    private TableColumn<Automovil, Date> col_fecha_modificacion_Auto;
    @FXML
    private TableColumn<Automovil, Date> col_fecha_creacion_Auto;
    @FXML
    private TableColumn<Automovil, Boolean> col_activo_Auto;
    @FXML
    private TableColumn<Automovil, String> col_creadoPorAutomovil;

    @FXML
    private TableColumn<Automovil, String> col_Sede_Auto;

    private ObservableList<Automovil> automovilList = FXCollections.observableArrayList();


    // Variables para registrar sedes
    private String mensajeExito = String.format("-fx-text-fill: GREEN;");
    private String estiloMensajeExito = String.format("-fx-border-color: #A9A9A9; -fx-border-width: 2; -fx-border-radius: 5;");

    private String mensaje = String.format("-fx-text-fill: black;");
    private String mensajeError = String.format("-fx-text-fill: RED;");
    private String estiloMensajeError = String.format("-fx-border-color: RED; -fx-border-width: 2; -fx-border-radius: 5;");
    @FXML
    private TextField txtPlacaAuto, txtMarcaAuto, txtCilindrajeAuto, txtColorAuto, txtModeloAuto, txtYearAuto, txtPrecioAuto;

    @FXML
    private Label validacionRegistroLabel;

    /**
     * CREATE - Registrar Auto
     * @throws IOException
     */
    //Para validar los campos de Autos

    private void crearActualizarAutomovil(boolean crear) {

        validacionRegistroLabel.setText("");
        txtPlacaAuto.setStyle(null);
        txtMarcaAuto.setStyle(null);
        txtCilindrajeAuto.setStyle(null);
        txtModeloAuto.setStyle(null);
        txtYearAuto.setStyle(null);
        txtColorAuto.setStyle(null);
        txtPrecioAuto.setStyle(null);

        // Cuando los campos están en blanco
        if(txtPlacaAuto.getText().isEmpty() || txtMarcaAuto.getText().isEmpty() ||
                txtCilindrajeAuto.getText().isEmpty() || txtColorAuto.getText().isEmpty() ||
        txtPrecioAuto.getText().isEmpty()||txtModeloAuto.getText().isEmpty())
        {
            validacionRegistroLabel.setStyle(mensajeError);
            if(txtPlacaAuto.getText().isEmpty() && txtMarcaAuto.getText().isEmpty() &&
                    txtCilindrajeAuto.getText().isEmpty() && txtColorAuto.getText().isEmpty() &&
                    txtPrecioAuto.getText().isEmpty() && txtModeloAuto.getText().isEmpty())
            {
                validacionRegistroLabel.setText("Se requieren todos los campos!");
                txtPlacaAuto.setStyle(estiloMensajeError);
                txtMarcaAuto.setStyle(estiloMensajeError);
                txtCilindrajeAuto.setStyle(estiloMensajeError);
                txtColorAuto.setStyle(estiloMensajeError);
                txtModeloAuto.setStyle(estiloMensajeError);
                txtColorAuto.setStyle(estiloMensajeError);
                txtPrecioAuto.setStyle(estiloMensajeError);


                new Shake(txtPlacaAuto).play();
                new Shake(txtMarcaAuto).play();
                new Shake(txtCilindrajeAuto).play();
                new Shake(txtColorAuto).play();
                new Shake(txtModeloAuto).play();
                new Shake(txtPrecioAuto).play();
                new Shake(txtYearAuto).play();



            } else {
                validacionRegistroLabel.setText("Algunos campos están vacíos!");
                boolean validado = this.validaciones(crear);
                if (validado) {
                    this.guardarActualizarAutomovil(crear);
                }
            }
        } else {
            boolean validado = this.validaciones(crear);
            if (validado) {
                this.guardarActualizarAutomovil(crear);
                this.refreshTable();
            }
        }
    }

    @FXML
    protected void bttnNuevoAutomovilClicked() throws IOException{
        this.crearActualizarAutomovil(true);
    }

    private boolean validaciones(boolean crear) {
        boolean validado = true;
        validacionRegistroLabel.setText("");
        // Validacion del modelo
        if (!ValidacionesAutomovil.validarModelo(txtModeloAuto.getText()))
        {
            validado = false;
            String textoError = "Formato del modelo es incorrecto!";
            //System.out.println(textoError);
            validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
            validacionRegistroLabel.setStyle(mensajeError);
            txtModeloAuto.setStyle(estiloMensajeError);
           new FadeIn(txtModeloAuto).play();
        }
        // Se comprueba la longitud del modelo del automovil
        if (!ValidacionesAutomovil.validarModelo(txtModeloAuto.getText()))
        {
            validado = false;
            String textoError = "El modelo del automovil debe tener menos de 6 caracteres!";
            validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
            validacionRegistroLabel.setStyle(mensajeError);
            txtModeloAuto.setStyle(estiloMensajeError);
            new FadeIn(txtModeloAuto).play();
        }
        else if (SQL_Automovil.existeautomovil_placa(txtPlacaAuto.getText()) && crear) {
            // Validacion para saber si un automovil con esa placa ya existe
            System.out.println(SQL_Automovil.existeautomovil_placa(txtPlacaAuto.getText()));
            validado = false;
            String textoError = "Ya hay un automovil con esa placa!";
            validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
            validacionRegistroLabel.setStyle(mensajeError);
            txtPlacaAuto.setStyle(estiloMensajeError);
           new FadeIn(txtPlacaAuto).play();
        }
        // Validación cilindraje
        if (!ValidacionesAutomovil.validarCilindraje(txtCilindrajeAuto.getText()))
        {
            validado = false;
            String textoError = "Formato de cilindraje incorrecto!";
            validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
            validacionRegistroLabel.setStyle(mensajeError);
            txtCilindrajeAuto.setStyle(estiloMensajeError);
           // new FadeIn(txtCilindrajeAuto).play();
        }
        // Validación marca
        if (!ValidacionesAutomovil.validarMarca(txtMarcaAuto.getText()))
        {
            validado = false;
            String textoError = "Formato de marca incorrecto!";
            validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
            validacionRegistroLabel.setStyle(mensajeError);
            txtMarcaAuto.setStyle(estiloMensajeError);
            new FadeIn(txtMarcaAuto).play();
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

    public void guardarActualizarAutomovil(boolean crear) {
        try {
            Automovil automovil = new Automovil();

            automovil.setPlaca(txtPlacaAuto.getText());
            automovil.setModelo(txtModeloAuto.getText());
            automovil.setMarca(txtMarcaAuto.getText());
            automovil.setCilindraje(Integer.parseInt(txtCilindrajeAuto.getText()));
            automovil.setColor(txtColorAuto.getText());
            automovil.setAño(txtYearAuto.getText());
            automovil.setPrecio(Integer.parseInt(txtPrecioAuto.getText()));

            // SI la orden es para crear, o para actualizar, llamo al metodo respectivo
            if (crear)
                SQL_Automovil.crearAutomovil(automovil);
            else
                SQL_Automovil.editarAutomovil(automovil.getPlaca(), automovil);

            automovil.setCedula_creado_por(LoginController.obtenerUsuarioLogeado().getCedula());
            automovil.setSede(LoginController.obtenerUsuarioLogeado().getSede());

            this.validadoLabelSet();
            this.limpiar();

        } catch (Exception e) {
            System.err.println(e);
            JOptionPane.showMessageDialog(null,"Error registrando el automovil");
        }
    }

    public void limpiar() {
        txtPlacaAuto.setText("");
        txtModeloAuto.setText("");
        txtMarcaAuto.setText("");
        txtCilindrajeAuto.setText("");
        txtColorAuto.setText("");
        txtYearAuto.setText("");
        txtPrecioAuto.setText("");


    }

    /**
     * UPDATE - READ - DELETE
     */
    private void loadData() {
        refreshTable();

        col_PlacaAuto.setCellValueFactory(new PropertyValueFactory<>("placa"));
        col_marcaAuto.setCellValueFactory(new PropertyValueFactory<>("marca"));
        col_cilindrajeAuto.setCellValueFactory(new PropertyValueFactory<>("cilindraje"));
        col_colorAuto.setCellValueFactory(new PropertyValueFactory<>("color"));
        col_modeloAuto.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        col_yearAuto.setCellValueFactory(new PropertyValueFactory<>("año"));
        col_precioAuto.setCellValueFactory(new PropertyValueFactory<>("precio"));
        col_activo_Auto.setCellValueFactory(new PropertyValueFactory<>("activo"));
        col_Sede_Auto.setCellValueFactory(new PropertyValueFactory<>("fecha_creacion"));
        col_fecha_modificacion_Auto.setCellValueFactory(new PropertyValueFactory<>("fecha_modificado"));
        col_creadoPorAutomovil.setCellValueFactory(new PropertyValueFactory<>("cedula_creado_por"));
        col_Sede_Auto.setCellValueFactory(new PropertyValueFactory<>("sede"));

        tableGestionAutomovil.setItems(automovilList.sorted());

    }

    private void readAutomovil() {
        try {
            ResultSet result = SQL_Automovil.obtenerTodosAutomovilSet();
            while (result.next()) {
                Automovil readAutomovil = new Automovil();

                readAutomovil.setMarca(result.getString("marca"));
                readAutomovil.setPlaca(result.getString("placa"));
                readAutomovil.setCilindraje(result.getInt("cilindraje"));
                readAutomovil.setColor(result.getString("color"));
                readAutomovil.setModelo(result.getString("modelo"));
                readAutomovil.setPrecio(result.getInt("precio"));
                readAutomovil.setAño(result.getString("año"));
                readAutomovil.setActivo(result.getBoolean("activo"));
                readAutomovil.setFecha_creacion(result.getDate("fecha_creacion"));
                readAutomovil.setFecha_modificado(result.getDate("fecha_modificado"));
                readAutomovil.setCedula_creado_por(result.getString("cedula_creado_por"));

                automovilList.add(readAutomovil);
            }
            automovilList.sorted();
        } catch(SQLException exception) {
            throw new RuntimeException(exception);
        }
    }




    //@FXML
    private void refreshTable() {
        automovilList.clear();
        this.readAutomovil();
    }

    /**
     * Botones
     * @throws IOException
     */
    @FXML
    protected void btnCancelarClick() throws IOException {
        if (Dialogs.showConfirm("Seleccione una opción", "¿Está seguro que quiere cancelar el registro?", Dialogs.YES, Dialogs.NO).equals(Dialogs.YES)) {
            EmpresaAutosABC.setRoot("menuGerente");
        }
    }
    @FXML
    protected void btnInicio() throws IOException {
        EmpresaAutosABC.setRoot("menuGerente");
    }
    // Para salir de la aplicación
    @FXML
    protected void btnLimpiar() {
        this.limpiar();
    }



    // Buscar por cedula para llenar campos y registrar o borrar
    @FXML
    protected void btnBuscarPlaca() {
        validacionRegistroLabel.setText("");
        txtMarcaAuto.setStyle(null);
        txtCilindrajeAuto.setStyle(null);
        txtModeloAuto.setStyle(null);
        txtYearAuto.setStyle(null);
        txtColorAuto.setStyle(null);
        txtPrecioAuto.setStyle(null);

        if(txtPlacaAuto.getText().isEmpty())
        {
            validacionRegistroLabel.setStyle(mensajeError);
            new Shake(txtPlacaAuto).play();
            validacionRegistroLabel.setText("La placa está vacía!");
        }
        else {
            boolean validado = this.validacionPlaca();
            if (validado) {
                this.llenarCamposPorPlaca();
            }
        }
    }

    private boolean validacionPlaca() {
        // Validación placa
        boolean validado = true;
        validacionRegistroLabel.setText("");
        if (!ValidacionesAutomovil.validarPlaca(txtPlacaAuto.getText())) {
            validado = false;
            String textoError = "Formato de la placa es incorrecto!";
            validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
            validacionRegistroLabel.setStyle(mensajeError);
            txtPlacaAuto.setStyle(estiloMensajeError);
            new FadeIn(txtPlacaAuto).play();
        }

        if (!SQL_Automovil.existeautomovil_placa(txtPlacaAuto.getText())) {
            // Validacion para saber si una sede con esa nombre ya existe
            validado = false;
            String textoError = "Un Automovil con esa placa NO existe!";
            validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
            validacionRegistroLabel.setStyle(mensajeError);
            txtPlacaAuto.setStyle(estiloMensajeError);
             new FadeIn(txtPlacaAuto).play();
        }
        return validado;
    }

    private void llenarCamposPorPlaca() {
        String placa = txtPlacaAuto.getText();
        try {
            ResultSet result = SQL_Automovil.obtenerAutomovil_placa(placa);
            while (result.next()) {
                Automovil readAutomovil = new Automovil();

                readAutomovil.setMarca (result.getString("marca"));
                readAutomovil.setPlaca(result.getString("placa"));
                readAutomovil.setCilindraje(result.getInt("cilindraje"));
                readAutomovil.setColor(result.getString("color"));
                readAutomovil.setModelo(result.getString("modelo"));
                readAutomovil.setPrecio(result.getInt("precio"));
                readAutomovil.setAño(result.getString("año"));
                readAutomovil.setActivo(result.getBoolean("activo"));
                readAutomovil.setFecha_creacion(result.getDate("fecha_creacion"));
                readAutomovil.setFecha_modificado(result.getDate("fecha_modificado"));
                readAutomovil.setSede(result.getString("sede"));

                // Cambio valores en los labels
                txtPlacaAuto.setText(readAutomovil.getPlaca());
                txtMarcaAuto.setText(readAutomovil.getMarca());
                txtPrecioAuto.setText(Integer.toString(readAutomovil.getPrecio()));
                txtModeloAuto.setText(readAutomovil.getModelo());
                txtColorAuto.setText(readAutomovil.getColor());
                txtCilindrajeAuto.setText(Integer.toString(readAutomovil.getCilindraje()));
                txtYearAuto.setText(readAutomovil.getAño());


            }
        } catch(SQLException exception) {
            throw new RuntimeException(exception);
        }

    }

    // Borrar - poner inactivo
    @FXML
    private void btnBorrarClicked() {
        String placa = txtPlacaAuto.getText();
        if (SQL_Automovil.existeautomovil_placa(placa)) {
            try {
                ResultSet result = SQL_Automovil.obtenerAutomovil_placa(placa);
                result.next();
                boolean activo = result.getBoolean("activo");
                SQL_Automovil.cambiarEstadoAutomovilPorPlaca(placa, activo);
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
            txtPlacaAuto.setStyle(estiloMensajeError);
            new FadeIn(txtPlacaAuto).play();
        }
        this.refreshTable();
    }

    // Actualizar
    @FXML
    private void btnActualizarClicked() {
        this.crearActualizarAutomovil(false);
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
        this.readAutomovil();
        this.loadData();
       tableGestionAutomovil.setItems(automovilList.sorted());
    }
}



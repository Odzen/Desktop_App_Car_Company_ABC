package org.openjfx.Controllers;

import GlobalUtils.Dialogs;
import animatefx.animation.FadeIn;
import animatefx.animation.Shake;
import animatefx.animation.Tada;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.openjfx.EmpresaAutosABC;
import org.openjfx.Models.Repuesto.Repuesto;
import org.openjfx.Models.Repuesto.Utils.ValidacionesRepuesto;
import org.openjfx.Models.Sede.SQL_Sede;
import org.openjfx.Models.Sede.Sede;
import org.openjfx.Models.Sede.Utils.ValidacionesSede;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;


public class GestionUsuJefeTallerRespuestosController implements Initializable {
    // Variables para Crear, Actualizar, Leer y Borrar Repuestos
    @FXML
    private TableView<Repuesto> tableGestionRepuestos;
    @FXML
    private TableColumn<Repuesto,Integer> col_idRepuesto;
    @FXML
    private TableColumn<Repuesto,String> col_marcaRepuesto;
    @FXML
    private TableColumn<Repuesto,String> col_nombreRepuesto;
    @FXML
    private TableColumn<Repuesto,Integer> col_cantidadRepuesto;
    @FXML
    private TableColumn<Repuesto, String> col_sedeRepuesto;
    @FXML
    private TableColumn<Repuesto, String> col_creado_por;
    @FXML
    private TableColumn<Repuesto, Date> col_fecha_creacion_repuesto;
    @FXML
    private TableColumn<Repuesto, Date> col_fecha_modificacion_repuesto;
    @FXML
    private TableColumn<Repuesto, Boolean> col_activo_repuesto;

    private ObservableList<Repuesto> repuestosList = FXCollections.observableArrayList();


    // Variables para registrar repuestos
    private String mensajeExito = String.format("-fx-text-fill: GREEN;");
    private String estiloMensajeExito = String.format("-fx-border-color: #A9A9A9; -fx-border-width: 2; -fx-border-radius: 5;");

    private String mensaje = String.format("-fx-text-fill: black;");
    private String mensajeError = String.format("-fx-text-fill: RED;");
    private String estiloMensajeError = String.format("-fx-border-color: RED; -fx-border-width: 2; -fx-border-radius: 5;");
    @FXML
    private TextField txtMarcaRepuesto, txtNombreRepuesto, txtCantidadRepuesto;

    @FXML
    private Label validacionRegistroLabel;

    /**
     * CREATE - Registrar Repuesto
     * @throws IOException
     */
    //Para validar los campos de repuesto

    private void crearActualizarRepuesto(boolean crear) {

        validacionRegistroLabel.setText("");
        txtMarcaRepuesto.setStyle(null);
        txtNombreRepuesto.setStyle(null);
        txtCantidadRepuesto.setStyle(null);
        // Cuando los campos están en blanco
        if(txtMarcaRepuesto.getText().isEmpty() || txtNombreRepuesto.getText().isEmpty() ||
                txtCantidadRepuesto.getText().isEmpty())
        {
            validacionRegistroLabel.setStyle(mensajeError);
            if(txtMarcaRepuesto.getText().isEmpty() && txtNombreRepuesto.getText().isEmpty() &&
                    txtCantidadRepuesto.getText().isEmpty())
            {
                validacionRegistroLabel.setText("Se requieren todos los campos!");
                txtMarcaRepuesto.setStyle(estiloMensajeError);
                txtNombreRepuesto.setStyle(estiloMensajeError);
                txtCantidadRepuesto.setStyle(estiloMensajeError);
                new Shake(txtMarcaRepuesto).play();
                new Shake(txtNombreRepuesto).play();
                new Shake(txtCantidadRepuesto).play();
            } else {
                validacionRegistroLabel.setText("Algunos campos están vacíos!");
                boolean validado = this.validaciones(crear);
                if (validado) {
                    this.guardarActualizarRepuesto(crear);
                }
            }
        } else {
            boolean validado = this.validaciones(crear);
            if (validado) {
                this.guardarActualizarRepuesto(crear);
                this.refreshTable();
            }
        }
    }

    @FXML
    protected void bttnNuevoRepuesto() throws IOException{
        this.crearActualizarRepuesto(true);
    }

    private boolean validaciones(boolean crear) {
        boolean validado = true;
        validacionRegistroLabel.setText("");
        // Validacion de cantidad
        if (!ValidacionesRepuesto.validarCantidadRepuesto(txtCantidadRepuesto.getText()))
        {
            validado = false;
            String textoError = "Formato de la cantidad está incorrecto!";
            validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
            validacionRegistroLabel.setStyle(mensajeError);
            txtCantidadRepuesto.setStyle(estiloMensajeError);
            new FadeIn(txtCantidadRepuesto).play();
        }
        // Se comprueba la longitud del nombre del repuesto
        if (!ValidacionesRepuesto.validarNombreRepuesto(txtNombreRepuesto.getText()))
        {
            validado = false;
            String textoError = "El nombre del repuesto debe tener entre 4 a 20 caracteres!";
            validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
            validacionRegistroLabel.setStyle(mensajeError);
            txtNombreRepuesto.setStyle(estiloMensajeError);
            new FadeIn(txtNombreRepuesto).play();
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
        // Validación Marca
        if (!ValidacionesRepuesto.validarMarcaRepuesto(txtMarcaRepuesto.getText()))
        {
            validado = false;
            String textoError = "La marca del repuesto debe tener entre 4 a 20 caracteres!";
            validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
            validacionRegistroLabel.setStyle(mensajeError);
            txtMarcaRepuesto.setStyle(estiloMensajeError);
            new FadeIn(txtMarcaRepuesto).play();
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

    public void guardarActualizarRepuesto(boolean crear) {
        try {
            Repuesto repuesto = new Repuesto();

            repuesto.setMarca(txtMarcaRepuesto.getText());
            repuesto.setNombre(txtNombreRepuesto.getText());
            repuesto.set(txtCantidadRepuesto.getText());

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
        txtCantidadRepuesto.setText("");
        txtMarcaRepuesto.setText("");
        txtMarcaRepuesto.setText("");
    }

    /**
     * UPDATE - READ - DELETE
     */
    private void loadData() {
        refreshTable();

        col_activo_repuesto.setCellValueFactory(new PropertyValueFactory<>("activo"));
        col_cantidadRepuesto.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        col_creado_por.setCellValueFactory(new PropertyValueFactory<>("cedula_creado_por"));
        col_fecha_creacion_repuesto.setCellValueFactory(new PropertyValueFactory<>("fecha_creacion"));
        col_idRepuesto.setCellValueFactory(new PropertyValueFactory<>("id_repuesto"));
        col_fecha_modificacion_repuesto.setCellValueFactory(new PropertyValueFactory<>("fecha_modificado"));
        col_marcaRepuesto.setCellValueFactory(new PropertyValueFactory<>("marca"));
        col_nombreRepuesto.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        tableGestionRepuestos.setItems(repuestosList.sorted());

    }

    private void readRepuestos() {
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
        repuestosList.clear();
        this.readRepuestos();
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
    protected void btnbuscarNombreMarcaRepuesto() {
        validacionRegistroLabel.setText("");
        txtNombreRepuesto.setStyle(null);
        txtMarcaRepuesto.setStyle(null);
        txtCantidadRepuesto.setStyle(null);
        if(txtNombreRepuesto.getText().isEmpty() || txtMarcaRepuesto.getText().isEmpty())
        {
            validacionRegistroLabel.setStyle(mensajeError);
            new Shake(txtNombreRepuesto).play();
            new Shake(txtMarcaRepuesto).play();
            validacionRegistroLabel.setText("El nombre o la marca del repuesto están vacias!");
        }
        else {
            boolean validado = this.validacionNombreMarca();
            if (validado) {
                this.llenarCamposPorNombreMarcaRepuesto();
            }
        }
    }

    private boolean validacionNombreMarca() {
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

    private void llenarCamposPorNombreMarcaRepuesto() {
        String nombreRepuesto = txtNombreRepuesto.getText();
        String marcaRepuesto = txtMarcaRepuesto.getText();
        try {
            ResultSet result = SQL_Sede.obtenerSede_Nombre(nombreRepuesto);
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
    private void btnBorrarRepuestoClicked() {
        String nombreRepuesto = txtNombreRepuesto.getText();
        String marcaRepuesto = txtNombreRepuesto.getText();
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
    private void btnActualizarRepuestoClicked() {
        this.crearActualizarRepuesto(false);
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
        this.readRepuestos();
        this.loadData();
        tableGestionRepuestos.setItems(repuestosList.sorted());
    }
}





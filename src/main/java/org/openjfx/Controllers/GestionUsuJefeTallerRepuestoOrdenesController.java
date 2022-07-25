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
import org.openjfx.Models.Orden_Trabajo.SQL_Orden;
import org.openjfx.Models.Repuesto.Repuesto;
import org.openjfx.Models.Repuesto.SQL_Repuesto;
import org.openjfx.Models.Repuesto.Utils.ValidacionesRepuesto;
import org.openjfx.Models.Repuesto_Orden.RepuestoOrden;
import org.openjfx.Models.Repuesto_Orden.SQL_RepuestoOrden;
import org.openjfx.Models.Repuesto_Orden.Utils.ValidacionesRepuestosOrdenes;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

public class GestionUsuJefeTallerRepuestoOrdenesController implements Initializable {
    // Variables para Crear, Actualizar, Leer y Borrar Repuestos por Ordeness
    @FXML
    private TableView<RepuestoOrden> tableGestionRepuestosOrdenes;
    @FXML
    private TableColumn<RepuestoOrden,Integer> col_idRepuesto;
    @FXML
    private TableColumn<RepuestoOrden,Integer> col_idOrden;
    @FXML
    private TableColumn<RepuestoOrden,Integer> col_cantidadRepuestoOrden;
    @FXML
    private TableColumn<RepuestoOrden,String> col_cedulaCreadoPor;
    @FXML
    private TableColumn<RepuestoOrden,Date> col_fecha_creacion_repuesto_orden;
    @FXML
    private TableColumn<RepuestoOrden,Date> col_fecha_modificacion_repuesto_orden;
    @FXML
    private TableColumn<RepuestoOrden, Boolean> col_activo_repuesto_orden;

    private ObservableList<RepuestoOrden> repuestosOrdenesList = FXCollections.observableArrayList();

    // Variables para registrar repuestos por orden
    private String mensajeExito = String.format("-fx-text-fill: GREEN;");
    private String estiloMensajeExito = String.format("-fx-border-color: #A9A9A9; -fx-border-width: 2; -fx-border-radius: 5;");

    private String mensaje = String.format("-fx-text-fill: black;");
    private String mensajeError = String.format("-fx-text-fill: RED;");
    private String estiloMensajeError = String.format("-fx-border-color: RED; -fx-border-width: 2; -fx-border-radius: 5;");
    @FXML
    private TextField txtIdRepuesto, txtIdOrden, txtCantidadRepuesto;

    @FXML
    private Label validacionRegistroLabel;

    /**
     * CREATE - Registrar Repuesto por orden
     * @throws IOException
     */
    //Para validar los campos de repuesto por orden

    private void crearActualizarRepuestoPorOrden(boolean crear) {
        validacionRegistroLabel.setText("");
        txtIdRepuesto.setStyle(null);
        txtCantidadRepuesto.setStyle(null);
        txtIdOrden.setStyle(null);
        // Cuando los campos están en blanco
        if(txtIdRepuesto.getText().isEmpty() || txtCantidadRepuesto.getText().isEmpty() ||
                txtIdOrden.getText().isEmpty())
        {
            validacionRegistroLabel.setStyle(mensajeError);
            if(txtIdRepuesto.getText().isEmpty() && txtCantidadRepuesto.getText().isEmpty() &&
                    txtIdOrden.getText().isEmpty())
            {
                validacionRegistroLabel.setText("Se requieren todos los campos!");
                txtIdRepuesto.setStyle(estiloMensajeError);
                txtCantidadRepuesto.setStyle(estiloMensajeError);
                txtIdOrden.setStyle(estiloMensajeError);
                new Shake(txtIdRepuesto).play();
                new Shake(txtCantidadRepuesto).play();
                new Shake(txtIdOrden).play();
            } else {
                validacionRegistroLabel.setText("Algunos campos están vacíos!");
                boolean validado = this.validaciones(crear);
                if (validado) {
                    this.guardarActualizarRepuestoPorOrden(crear);
                }
            }
        } else {
            boolean validado = this.validaciones(crear);
            if (validado) {
                this.guardarActualizarRepuestoPorOrden(crear);
                this.refreshTable();
            }
        }
    }

    @FXML
    protected void bttnNuevoRepuestoOrden() throws IOException{
        this.crearActualizarRepuestoPorOrden(true);
    }

    private boolean validaciones(boolean crear) {
        boolean validado = true;
        validacionRegistroLabel.setText("");
        // Validacion de cantidad
        if (!ValidacionesRepuestosOrdenes.validarCantidadRepuestoOrden(txtCantidadRepuesto.getText()))
        {
            validado = false;
            String textoError = "Formato de la cantidad está incorrecto!";
            validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
            validacionRegistroLabel.setStyle(mensajeError);
            txtCantidadRepuesto.setStyle(estiloMensajeError);
            new FadeIn(txtCantidadRepuesto).play();
        }
        // Valida el id del repuesto
        if (!ValidacionesRepuestosOrdenes.validarIds(txtIdRepuesto.getText()))
        {
            validado = false;
            String textoError = "Formato del id del repuesto está incorrecto!";
            validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
            validacionRegistroLabel.setStyle(mensajeError);
            txtIdRepuesto.setStyle(estiloMensajeError);
            new FadeIn(txtIdRepuesto).play();
        }
        else if(!SQL_Repuesto.existeRepuesto_Id(Integer.parseInt(txtIdRepuesto.getText())))
        {
            validado = false;
            String textoError = "No existe un repuesto con ese Id en la base de datos!";
            validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
            validacionRegistroLabel.setStyle(mensajeError);
            txtIdRepuesto.setStyle(estiloMensajeError);
            new FadeIn(txtIdRepuesto).play();
        }
        // Valida el id de la orden
        if (!ValidacionesRepuestosOrdenes.validarIds(txtIdOrden.getText()))
        {
            validado = false;
            String textoError = "Formato del id de la orden está incorrecto!";
            validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
            validacionRegistroLabel.setStyle(mensajeError);
            txtIdOrden.setStyle(estiloMensajeError);
            new FadeIn(txtIdOrden).play();
        }
        else if(!SQL_Orden.existeOrden_Id(Integer.parseInt(txtIdOrden.getText())))
        {
            validado = false;
            String textoError = "No existe una orden con ese Id en la base de datos!";
            validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
            validacionRegistroLabel.setStyle(mensajeError);
            txtIdOrden.setStyle(estiloMensajeError);
            new FadeIn(txtIdOrden).play();
        }
        else if (SQL_RepuestoOrden.existeRepuestoOrden_Id(Integer.parseInt(txtIdRepuesto.getText()), Integer.parseInt(txtIdOrden.getText())) && crear) {
            // Validacion para saber si un repuesto por orden con esos Ids ya existe
            validado = false;
            String textoError = "Un repuesto por orden con esos Ids ya existe!";
            validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
            validacionRegistroLabel.setStyle(mensajeError);
            txtIdRepuesto.setStyle(estiloMensajeError);
            new FadeIn(txtIdRepuesto).play();
            txtIdOrden.setStyle(estiloMensajeError);
            new FadeIn(txtIdOrden).play();
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

    public void guardarActualizarRepuestoPorOrden(boolean crear) {
        try {
            RepuestoOrden repuestoOrden = new RepuestoOrden();

            repuestoOrden.setId_orden(Integer.parseInt(txtIdOrden.getText()));
            repuestoOrden.setId_repuesto(Integer.parseInt(txtIdRepuesto.getText()));
            repuestoOrden.setCantidad(Integer.parseInt(txtCantidadRepuesto.getText()));
            repuestoOrden.setCedula_creado_por(LoginController.obtenerUsuarioLogeado().getCedula());

            // SI la orden es para crear, o para actualizar, llamo al metodo respectivo
            if (crear)
                SQL_RepuestoOrden.crearRepuestoOrden(repuestoOrden);
            else
                SQL_RepuestoOrden.editarRepuestoOrden(repuestoOrden.getId_repuesto(),repuestoOrden.getId_orden(), repuestoOrden);

            this.validadoLabelSet();
            this.limpiar();

        } catch (Exception e) {
            System.err.println(e);
            Dialogs.showError("Error en la base de datos", "Error registrando el repuesto por orden");
        }
    }

    public void limpiar() {
        txtCantidadRepuesto.setText("");
        txtIdOrden.setText("");
        txtIdRepuesto.setText("");
        txtCantidadRepuesto.setStyle(null);
        txtIdRepuesto.setStyle(null);
        txtIdOrden.setStyle(null);
    }

    /**
     * UPDATE - READ - DELETE
     */
    private void loadData() {
        refreshTable();

        col_idOrden.setCellValueFactory(new PropertyValueFactory<>("id_repuesto"));
        col_idRepuesto.setCellValueFactory(new PropertyValueFactory<>("id_orden"));
        col_cantidadRepuestoOrden.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        col_cedulaCreadoPor.setCellValueFactory(new PropertyValueFactory<>("cedula_creado_por"));
        col_fecha_modificacion_repuesto_orden.setCellValueFactory(new PropertyValueFactory<>("fecha_modificado"));
        col_fecha_creacion_repuesto_orden.setCellValueFactory(new PropertyValueFactory<>("fecha_creacion"));
        col_activo_repuesto_orden.setCellValueFactory(new PropertyValueFactory<>("activo"));

        tableGestionRepuestosOrdenes.setItems(repuestosOrdenesList.sorted());

    }

    private void readRepuestos() {
        try {
            ResultSet result = SQL_Repuesto.obtenerTodosRepuestosSet();
            while (result.next()) {
                RepuestoOrden readRepuestoOrden = new RepuestoOrden();

                readRepuestoOrden.setId_repuesto(result.getInt("id_repuesto"));
                readRepuestoOrden.setId_orden(result.getInt("id_orden"));
                readRepuestoOrden.setCantidad(result.getInt("cantidad"));
                readRepuestoOrden.setCedula_creado_por(result.getString("cedula_creado_por"));
                readRepuestoOrden.setFecha_modificado(result.getDate("fecha_modificado"));
                readRepuestoOrden.setFecha_creacion(result.getDate("fecha_creacion"));
                readRepuestoOrden.setActivo(result.getBoolean("activo"));

                repuestosOrdenesList.add(readRepuestoOrden);
            }
            repuestosOrdenesList.sorted();
        } catch(SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    //@FXML
    private void refreshTable() {
        repuestosOrdenesList.clear();
        this.readRepuestos();
    }

    /**
     * Botones
     * @throws IOException
     */
    @FXML
    protected void btnCancelarClick() throws IOException {
        if (Dialogs.showConfirm("Seleccione una opción", "¿Está seguro que quiere cancelar el registro?", Dialogs.YES, Dialogs.NO).equals(Dialogs.YES)) {
            EmpresaAutosABC.setRoot("menuJefeTaller");
        }
    }
    @FXML
    protected void btnInicio() throws IOException {
        EmpresaAutosABC.setRoot("menuJefeTaller");
    }
    // Para limpiar datos
    @FXML
    protected void btnLimpiar() {
        this.limpiar();
    }

    // Buscar por Ids del repuesto y orden para llenar campos y así poder registrar o borrar
    @FXML
    protected void btnbuscarIdRepuestoOrden() {
        validacionRegistroLabel.setText("");
        txtIdRepuesto.setStyle(null);
        txtIdOrden.setStyle(null);
        txtCantidadRepuesto.setStyle(null);
        if(txtIdRepuesto.getText().isEmpty() || txtIdOrden.getText().isEmpty())
        {
            validacionRegistroLabel.setStyle(mensajeError);
            new Shake(txtIdRepuesto).play();
            new Shake(txtIdOrden).play();
            validacionRegistroLabel.setText("El id de repuesto u orden están vacíos!");
        }
        else {
            boolean validado = this.validacionIds();
            if (validado) {
                this.llenarCampos();
            }
        }
    }

    private boolean validacionIds() {
        // Validación nombre
        boolean validado = true;
        validacionRegistroLabel.setText("");
        if (!ValidacionesRepuestosOrdenes.validarIds(txtIdOrden.getText())) {
            validado = false;
            String textoError = "Formato del id de la orden está incorrecto!";
            validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
            validacionRegistroLabel.setStyle(mensajeError);
            txtIdOrden.setStyle(estiloMensajeError);
            new FadeIn(txtIdOrden).play();
        }
        // Validación Marca
        if (!ValidacionesRepuestosOrdenes.validarIds(txtIdRepuesto.getText()))
        {
            validado = false;
            String textoError = "Formato del id del repuesto está incorrecto!";
            validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
            validacionRegistroLabel.setStyle(mensajeError);
            txtIdRepuesto.setStyle(estiloMensajeError);
            new FadeIn(txtIdRepuesto).play();
        }
        if (!SQL_RepuestoOrden.existeRepuestoOrden_Id(Integer.parseInt(txtIdRepuesto.getText()), Integer.parseInt(txtIdOrden.getText()) )) {
            validado = false;
            String textoError = "Una repuesto por orden con esos Ids NO existe!";
            validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
            validacionRegistroLabel.setStyle(mensajeError);
            txtIdRepuesto.setStyle(estiloMensajeError);
            new FadeIn(txtIdRepuesto).play();
            txtIdOrden.setStyle(estiloMensajeError);
            new FadeIn(txtIdOrden).play();
        }
        return validado;
    }

    private void llenarCampos() {
        int id_repuesto = Integer.parseInt(txtIdRepuesto.getText());
        int id_orden = Integer.parseInt(txtIdOrden.getText());
        try {
            ResultSet result = SQL_RepuestoOrden.obtenerRepuestoOrden_Ids(id_repuesto, id_orden);
            while (result.next()) {
                RepuestoOrden readRepuestoOrden = new RepuestoOrden();

                readRepuestoOrden.setId_repuesto(result.getInt("id_repuesto"));
                readRepuestoOrden.setId_orden(result.getInt("id_orden"));
                readRepuestoOrden.setCantidad(result.getInt("cantidad"));
                readRepuestoOrden.setCedula_creado_por(result.getString("cedula_creado_por"));
                readRepuestoOrden.setFecha_modificado(result.getDate("fecha_modificado"));
                readRepuestoOrden.setFecha_creacion(result.getDate("fecha_creacion"));
                readRepuestoOrden.setActivo(result.getBoolean("activo"));

                // Cambio valores en los labels
                txtIdOrden.setText(Integer.toString(readRepuestoOrden.getId_orden()));
                txtIdRepuesto.setText(Integer.toString(readRepuestoOrden.getId_repuesto()));
                txtCantidadRepuesto.setText(Integer.toString(readRepuestoOrden.getCantidad()));

            }
        } catch(SQLException exception) {
            throw new RuntimeException(exception);
        }

    }

    // Borrar - se hace SOFT DELETE solo cuando la cantidad del repuesto es = 0
    // Borrar realmente lo que hace es borrar (actualizar) una determinada cantidad
    // que se resta a la cantidad actual
    // Esta cantidad NO puede ser mayor a la cantidad actual
    // Si la cantidad actualizada queda en 0, entonces el repuesto se pone como inactivo (SOFT DELETE)
    @FXML
    private void btnBorrarRepuestoOrdenClicked() {
        if(txtNombreRepuesto.getText().isEmpty() || txtMarcaRepuesto.getText().isEmpty() ||  txtCantidadRepuesto.getText().isEmpty())
        {
            validacionRegistroLabel.setStyle(mensajeError);
            new Shake(txtNombreRepuesto).play();
            new Shake(txtMarcaRepuesto).play();
            new Shake(txtCantidadRepuesto).play();
            validacionRegistroLabel.setText("El nombre, la marca o la cantidad del repuesto están vacias!");
        }
        else {
            this.borrarRepuesto();
        }
    }

    private boolean borrarRepuesto() {
        boolean validado = true;
        validacionRegistroLabel.setText("");
        // Validacion existencia
        if (SQL_Repuesto.existeRepuesto_NombreMarca(txtNombreRepuesto.getText(), txtMarcaRepuesto.getText())) {
            // Validación cantidad
            int cantidadActual = 0;
            ResultSet resultado =  SQL_Repuesto.obtenerRepuesto_NombreMarca(txtNombreRepuesto.getText(), txtMarcaRepuesto.getText());
            try {
                resultado.next();
                cantidadActual = resultado.getInt("cantidad");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Cantidad para cambiar: " + txtCantidadRepuesto.getText());
            System.out.println("Cantidad actual: " + cantidadActual);
            if (Integer.parseInt(txtCantidadRepuesto.getText()) > cantidadActual) {
                validado = false;
                String textoError = "La cantidad que quiere borrar NO puede ser mayor a la cantidad actual!";
                System.out.println(textoError);
                validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
                validacionRegistroLabel.setStyle(mensajeError);
                txtCantidadRepuesto.setStyle(estiloMensajeError);
                new FadeIn(txtCantidadRepuesto).play();
            } else {
                System.out.println("La cantidad es correcta");
                Repuesto repuestoActualizado = new Repuesto();
                int cantidadDespuesDeBorrar = cantidadActual - Integer.parseInt(txtCantidadRepuesto.getText());
                try {
                    repuestoActualizado.setNombre(resultado.getString("nombre"));
                    repuestoActualizado.setMarca(resultado.getString("marca"));
                    repuestoActualizado.setCantidad(cantidadDespuesDeBorrar);
                    SQL_Repuesto.editarRepuesto(txtNombreRepuesto.getText(), txtMarcaRepuesto.getText(), repuestoActualizado);
                    this.validadoLabelSet();
                    this.limpiar();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        else {
            validado = false;
            String textoError = "No existe un repuesto con ese nombre y marca!";
            validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
            validacionRegistroLabel.setStyle(mensajeError);
            txtNombreRepuesto.setStyle(estiloMensajeError);
            new FadeIn(txtNombreRepuesto).play();
            txtMarcaRepuesto.setStyle(estiloMensajeError);
            new FadeIn(txtMarcaRepuesto).play();
        }
        this.refreshTable();
        return validado;
    }

    // Actualizar
    @FXML
    private void btnActualizarRepuestoOrdenClicked() {
        this.crearActualizarRepuestoPorOrden(false);
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
        tableGestionRepuestosOrdenes.setItems(repuestosOrdenesList.sorted());
    }
}







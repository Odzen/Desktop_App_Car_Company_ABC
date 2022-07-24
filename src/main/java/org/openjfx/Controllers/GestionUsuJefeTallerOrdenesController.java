package org.openjfx.Controllers;

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
import org.openjfx.Models.Automovil.SQL_Automovil;
import org.openjfx.Models.Cliente.SQL_Cliente;
import org.openjfx.Models.Orden_Trabajo.Orden;
import org.openjfx.Models.Orden_Trabajo.SQL_Orden;
import org.openjfx.Models.Orden_Trabajo.Utils.Estado;
import org.openjfx.Models.Orden_Trabajo.Utils.ValidacionesOrden;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;


public class GestionUsuJefeTallerOrdenesController implements Initializable {
    // Variables para Crear, Actualizar, Leer y Borrar Ordenes
    @FXML
    private TableView<Orden> tableGestionOrdenes;
    @FXML
    private TableColumn<Orden,Integer> col_idOrden;
    @FXML
    private TableColumn<Orden,String> col_cedulaCliente;
    @FXML
    private TableColumn<Orden,String> col_cedulaJefe;
    @FXML
    private TableColumn<Orden,String> col_placa;
    @FXML
    private TableColumn<Orden, String> col_estadoOrden;
    @FXML
    private TableColumn<Orden, Date> col_fecha_creacion;
    @FXML
    private TableColumn<Orden, Date> col_fecha_modificacion;
    @FXML
    private TableColumn<Orden, Boolean> col_activo_orden;

    private ObservableList<Orden> ordenesList = FXCollections.observableArrayList();


    // Variables para registrar ordenes
    private String mensajeExito = String.format("-fx-text-fill: GREEN;");
    private String estiloMensajeExito = String.format("-fx-border-color: #A9A9A9; -fx-border-width: 2; -fx-border-radius: 5;");

    private String mensaje = String.format("-fx-text-fill: black;");
    private String mensajeError = String.format("-fx-text-fill: RED;");
    private String estiloMensajeError = String.format("-fx-border-color: RED; -fx-border-width: 2; -fx-border-radius: 5;");
    @FXML
    private TextField txtCedulaCliente, txtPlaca;
    @FXML
    private SplitMenuButton estado;
    @FXML
    MenuItem firstItem;
    @FXML
    MenuItem secondItem;
    @FXML
    MenuItem thirdItem;

    @FXML
    private Label validacionRegistroLabel;

    /**
     * CREATE - Registrar Orden
     * @throws IOException
     */

    //Para validar los campos de repuesto
    private void crearActualizarOrden(boolean crear) {

        validacionRegistroLabel.setText("");
        txtCedulaCliente.setStyle(null);
        txtPlaca.setStyle(null);
        estado.setStyle(null);
        // Cuando los campos están en blanco
        if(txtCedulaCliente.getText().isEmpty() || txtPlaca.getText().isEmpty() || estado.getText().equals("Estado"))
        {
            validacionRegistroLabel.setStyle(mensajeError);
            if(txtCedulaCliente.getText().isEmpty() && txtPlaca.getText().isEmpty() &&
                    estado.getText().equals("Estado"))
            {
                validacionRegistroLabel.setText("Se requieren todos los campos!");
                txtCedulaCliente.setStyle(estiloMensajeError);
                txtPlaca.setStyle(estiloMensajeError);
                estado.setStyle(estiloMensajeError);
                new Shake(txtCedulaCliente).play();
                new Shake(txtPlaca).play();
                new Shake(estado).play();
            } else {
                validacionRegistroLabel.setText("Algunos campos están vacíos!");
                boolean validado = this.validaciones(crear);
                if (validado) {
                    this.guardarActualizarOrden(crear);
                }
            }
        } else {
            boolean validado = this.validaciones(crear);
            if (validado) {
                this.guardarActualizarOrden(crear);
                this.refreshTable();
            }
        }
    }

    @FXML
    protected void bttnNuevaOrden() throws IOException{
        this.crearActualizarOrden(true);
    }

    private boolean validaciones(boolean crear) {
        boolean validado = true;
        validacionRegistroLabel.setText("");
        // Validacion de cédula del cliente
        if (!ValidacionesOrden.validarCedulaCliente(txtCedulaCliente.getText()))
        {
            validado = false;
            String textoError = "Formato de la cédula está incorrecto!";
            validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
            validacionRegistroLabel.setStyle(mensajeError);
            txtCedulaCliente.setStyle(estiloMensajeError);
            new FadeIn(txtCedulaCliente).play();
        }
        else if(!SQL_Cliente.existeCliente_Cedula(txtCedulaCliente.getText())) {
            validado = false;
            String textoError = "Un cliente con esa cédula no existe!";
            validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
            validacionRegistroLabel.setStyle(mensajeError);
            txtCedulaCliente.setStyle(estiloMensajeError);
            new FadeIn(txtCedulaCliente).play();
        }
        // Validacion de la placa
        if (!ValidacionesOrden.validarPlaca(txtPlaca.getText()))
        {
            validado = false;
            String textoError = "Formato de la placa está incorrecto!";
            validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
            validacionRegistroLabel.setStyle(mensajeError);
            txtPlaca.setStyle(estiloMensajeError);
            new FadeIn(txtPlaca).play();
        }
        else if(!SQL_Automovil.existeautomovil_placa(txtPlaca.getText())) {
            validado = false;
            String textoError = "Un automóvil con esa placa no existe!";
            validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
            validacionRegistroLabel.setStyle(mensajeError);
            txtPlaca.setStyle(estiloMensajeError);
            new FadeIn(txtPlaca).play();
        }
        // Validacion estado
        if (estado.getText().equals("Estado") || (!estado.getText().equals("En espera") && !estado.getText().equals("En progreso") && !estado.getText().equals("Terminada")))
        {
            validado = false;
            String textoError = "Formato de estado incorrecto!";
            validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
            validacionRegistroLabel.setStyle(mensajeError);
            estado.setStyle(estiloMensajeError);
            new FadeIn(estado).play();
        }
        else if( (estado.getText().equals("En progreso") || estado.getText().equals("Terminada")) && crear) {
            validado = false;
            String textoError = "Al momento de crear, el estado debe ser siempre 'En espera'!";
            validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
            validacionRegistroLabel.setStyle(mensajeError);
            estado.setStyle(estiloMensajeError);
            new FadeIn(estado).play();
        }
        else if (SQL_Orden.existeOrden_CedulaPlaca(txtCedulaCliente.getText(), txtPlaca.getText()) && crear) {
            // Validacion para saber si la orden con esa cédula de cliente y esa placa ya existe
            validado = false;
            String textoError = "Una orden con esa cédula y esa placa ya existe!";
            validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
            validacionRegistroLabel.setStyle(mensajeError);
            txtCedulaCliente.setStyle(estiloMensajeError);
            new FadeIn(txtCedulaCliente).play();
            txtPlaca.setStyle(estiloMensajeError);
            new FadeIn(txtPlaca).play();
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

    public void guardarActualizarOrden(boolean crear) {
        try {
            Orden orden = new Orden();

            orden.setCedula_cliente(txtCedulaCliente.getText());
            orden.setPlaca_automovil(txtPlaca.getText());
            orden.setCedula_jefe_de_taller(LoginController.obtenerUsuarioLogeado().getCedula());

            int idTipoEstado = 0;
            if (estado.getText().equals("En espera")) {
                idTipoEstado = 1;
            } else if (estado.getText().equals("En progreso")) {
                idTipoEstado = 2;
            }else if (estado.getText().equals("Terminada")) {
                idTipoEstado = 3;
            }
            orden.setId_estado_orden(idTipoEstado);

            // SI la orden es para crear, o para actualizar, llamo al metodo respectivo
            if (crear)
                SQL_Orden.crearOrden(orden);
            else
                SQL_Orden.editarOrden(orden.getCedula_cliente(),orden.getPlaca_automovil(), orden);

            this.validadoLabelSet();
            this.limpiar();

        } catch (Exception e) {
            System.err.println(e);
            Dialogs.showError("Error en la base de datos", "Error registrando la orden");
        }
    }

    @FXML
    private void setFirstItem() {
        estado.setText(firstItem.getText());
    }

    @FXML
    private void setSecondItem() {
        estado.setText(secondItem.getText());
    }

    @FXML
    private void setThirdItem() {
        estado.setText(thirdItem.getText());
    }

    public void limpiar() {
        txtPlaca.setText("");
        txtCedulaCliente.setText("");
        estado.setText("Estado");
        txtPlaca.setStyle(null);
        txtCedulaCliente.setStyle(null);
        estado.setStyle(null);
    }

    /**
     * UPDATE - READ - DELETE
     */
    private void loadData() {
        refreshTable();

        col_idOrden.setCellValueFactory(new PropertyValueFactory<>("id_orden"));
        col_fecha_creacion.setCellValueFactory(new PropertyValueFactory<>("fecha_creacion"));
        col_fecha_modificacion.setCellValueFactory(new PropertyValueFactory<>("fecha_modificado"));
        col_activo_orden.setCellValueFactory(new PropertyValueFactory<>("activo"));
        col_cedulaCliente.setCellValueFactory(new PropertyValueFactory<>("cedula_cliente"));
        col_cedulaJefe.setCellValueFactory(new PropertyValueFactory<>("cedula_jefe_de_taller"));
        col_placa.setCellValueFactory(new PropertyValueFactory<>("placa_automovil"));
        col_estadoOrden.setCellValueFactory(new PropertyValueFactory<>("estado"));

        tableGestionOrdenes.setItems(ordenesList.sorted());

    }

    private void readOrdenes() {
        try {
            ResultSet result = SQL_Orden.obtenerTodasOrdenesSet();
            while (result.next()) {
                Orden readOrden = new Orden();

                readOrden.setId_orden(result.getInt("id_orden"));
                readOrden.setFecha_creacion(result.getDate("fecha_creacion"));
                readOrden.setFecha_modificado(result.getDate("fecha_modificado"));
                readOrden.setActivo(result.getBoolean("activo"));
                readOrden.setCedula_cliente(result.getString("cedula_cliente"));
                readOrden.setCedula_jefe_de_taller(result.getString("cedula_jefe_de_taller"));
                readOrden.setPlaca_automovil(result.getString("placa_automovil"));
                readOrden.setId_estado_orden(result.getInt("id_estado_orden"));
                readOrden.setEstado(Estado.valueOf(result.getString("estado")));

                ordenesList.add(readOrden);
            }
            ordenesList.sorted();
        } catch(SQLException exception) {
            System.err.println(exception);
            Dialogs.showError("Error en la base de datos", "Error leyendo las ordenes");
        }
    }

    //@FXML
    private void refreshTable() {
        ordenesList.clear();
        this.readOrdenes();
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

    // Buscar por cedula y placa de la orden para llenar campos y así poder registrar o borrar
    @FXML
    protected void btnCedulaPlacaOrden() {
        validacionRegistroLabel.setText("");
        txtPlaca.setStyle(null);
        txtCedulaCliente.setStyle(null);
        estado.setStyle(null);
        if(txtPlaca.getText().isEmpty() || txtCedulaCliente.getText().isEmpty())
        {
            validacionRegistroLabel.setStyle(mensajeError);
            new Shake(txtPlaca).play();
            new Shake(txtCedulaCliente).play();
            validacionRegistroLabel.setText("La cédula o la placa del repuesto están vacias!");
        }
        else {
            boolean validado = this.validacionCedulaPlaca();
            if (validado) {
                this.llenarCamposPorCedulaPlaca();
            }
        }
    }

    private boolean validacionCedulaPlaca() {
        // Validación cedula
        boolean validado = true;
        validacionRegistroLabel.setText("");
        if (!ValidacionesOrden.validarCedulaCliente(txtCedulaCliente.getText())) {
            validado = false;
            String textoError = "Formato de la cédula del cliente es incorrecto!";
            validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
            validacionRegistroLabel.setStyle(mensajeError);
            txtCedulaCliente.setStyle(estiloMensajeError);
            new FadeIn(txtCedulaCliente).play();
        }
        // Validación placa
        if (!ValidacionesOrden.validarPlaca(txtPlaca.getText()))
        {
            validado = false;
            String textoError = "Formato de la placa del vehículo está incorrecto!";
            validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
            validacionRegistroLabel.setStyle(mensajeError);
            txtPlaca.setStyle(estiloMensajeError);
            new FadeIn(txtPlaca).play();
        }
        if (!SQL_Orden.existeOrden_CedulaPlaca(txtCedulaCliente.getText(), txtPlaca.getText())) {
            // Validacion para saber si una orden con esa cedula y placa ya existe
            validado = false;
            String textoError = "Una orden con esa cedula y placa NO existe!";
            validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
            validacionRegistroLabel.setStyle(mensajeError);
            txtCedulaCliente.setStyle(estiloMensajeError);
            new FadeIn(txtCedulaCliente).play();
            txtPlaca.setStyle(estiloMensajeError);
            new FadeIn(txtPlaca).play();
        }
        return validado;
    }

    private void llenarCamposPorCedulaPlaca() {
        String cedulaCliente = txtCedulaCliente.getText();
        String placa = txtPlaca.getText();
        try {
            ResultSet result = SQL_Orden.obtenerOrden_CedulaPlaca(cedulaCliente, placa);
            while (result.next()) {
                Orden readOrden = new Orden();

                readOrden.setId_orden(result.getInt("id_orden"));
                readOrden.setFecha_creacion(result.getDate("fecha_creacion"));
                readOrden.setFecha_modificado(result.getDate("fecha_modificado"));
                readOrden.setActivo(result.getBoolean("activo"));
                readOrden.setCedula_cliente(result.getString("cedula_cliente"));
                readOrden.setCedula_jefe_de_taller(result.getString("cedula_jefe_de_taller"));
                readOrden.setPlaca_automovil(result.getString("placa_automovil"));
                readOrden.setId_estado_orden(result.getInt("id_estado_orden"));
                readOrden.setEstado(Estado.valueOf(result.getString("estado")));

                // Cambio valores en los labels
                txtCedulaCliente.setText(readOrden.getCedula_cliente());
                txtPlaca.setText(readOrden.getPlaca_automovil());
                estado.setText(readOrden.getEstado().toString());

            }
        } catch(SQLException exception) {
            System.err.println(exception);
            Dialogs.showError("Error llenando datos por BD", "Error obteniendo las ordenes");
        }

    }

    // Borrar - poner inactivo
    @FXML
    private void btnBorrarOrdenClicked() {
        String cedulaCliente = txtCedulaCliente.getText();
        String placa = txtPlaca.getText();
        if (SQL_Orden.existeOrden_CedulaPlaca(cedulaCliente, placa)) {
            try {
                ResultSet result = SQL_Orden.obtenerOrden_CedulaPlaca(cedulaCliente, placa);
                result.next();
                boolean activo = result.getBoolean("activo");
                SQL_Orden.cambiarEstadoOrdenPorNombreMarca(cedulaCliente, placa, activo);
                this.validadoLabelSet();
                this.limpiar();

            } catch (SQLException exception) {
                System.err.println(exception);
                Dialogs.showError("Error en BD", "Error borrando orden");
            }
        }
        else {
            String textoError = "No existe una orden con esa cédula o placa!";
            validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
            validacionRegistroLabel.setStyle(mensajeError);
            txtCedulaCliente.setStyle(estiloMensajeError);
            new FadeIn(txtCedulaCliente).play();
            txtPlaca.setStyle(estiloMensajeError);
            new FadeIn(txtPlaca).play();
        }

        this.refreshTable();
    }


    // Actualizar
    @FXML
    private void btnActualizarOrdenClicked() {
        this.crearActualizarOrden(false);
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
        this.readOrdenes();
        this.loadData();
        tableGestionOrdenes.setItems(ordenesList.sorted());
    }
}





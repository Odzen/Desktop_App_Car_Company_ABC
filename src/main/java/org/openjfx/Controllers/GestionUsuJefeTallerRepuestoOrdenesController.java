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
        int cantidadActualRepuesto = 0;
        try{
            ResultSet resultRepuesto = SQL_Repuesto.obtenerRepuesto_Id(Integer.parseInt(txtIdRepuesto.getText()));
            resultRepuesto.next();
            cantidadActualRepuesto = resultRepuesto.getInt("cantidad");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        int cantidadActualRepuestoOrden = 0;
        try{
            ResultSet resultRepuesto = SQL_RepuestoOrden.obtenerRepuestoOrden_Ids(Integer.parseInt(txtIdRepuesto.getText()), Integer.parseInt(txtIdOrden.getText()));
            resultRepuesto.next();
            cantidadActualRepuestoOrden = resultRepuesto.getInt("cantidad");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        /*
        if(Integer.parseInt(txtCantidadRepuesto.getText()) > cantidadActualRepuesto)
        {
            validado = false;
            String textoError = "La cantidad es mayor que la cantidad existente!";
            validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
            validacionRegistroLabel.setStyle(mensajeError);
            txtCantidadRepuesto.setStyle(estiloMensajeError);
            new FadeIn(txtCantidadRepuesto).play();
        }
        */
        if(Integer.parseInt(txtCantidadRepuesto.getText()) == cantidadActualRepuestoOrden)
        {
            validado = false;
            String textoError = "La cantidad es igual a la ya registrada!";
            validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
            validacionRegistroLabel.setStyle(mensajeError);
            txtCantidadRepuesto.setStyle(estiloMensajeError);
            new FadeIn(txtCantidadRepuesto).play();
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
            repuestoOrden.setCedula_creado_por(LoginController.obtenerUsuarioLogeado().getCedula());


            // SI la orden es para crear, o para actualizar, llamo al metodo respectivo
            if (crear)
            {
                repuestoOrden.setCantidad(Integer.parseInt(txtCantidadRepuesto.getText()));
                SQL_RepuestoOrden.crearRepuestoOrden(repuestoOrden);
            }
            else {
                int cantidadActualRepuestoOrden = 0;
                try{
                    ResultSet resultRepuesto = SQL_RepuestoOrden.obtenerRepuestoOrden_Ids(Integer.parseInt(txtIdRepuesto.getText()), Integer.parseInt(txtIdOrden.getText()));
                    resultRepuesto.next();
                    cantidadActualRepuestoOrden = resultRepuesto.getInt("cantidad");

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                int cantidadRestante = Integer.parseInt(txtCantidadRepuesto.getText()) - cantidadActualRepuestoOrden;


                // Actualizar repuesto

                ResultSet resultSet = SQL_Repuesto.obtenerRepuesto_Id(Integer.parseInt(txtIdRepuesto.getText()));
                resultSet.next();
                int cantidadRepuesto = resultSet.getInt("cantidad");
                String nombreRepuesto = resultSet.getString("nombre");
                String marcaRepuesto = resultSet.getString("marca");
                String cedula_creado_por = resultSet.getString("cedula_creado_por");
                Date fecha_creacion = resultSet.getDate("fecha_creacion");
                Date fecha_modificado = resultSet.getDate("fecha_modificado");
                boolean activo = resultSet.getBoolean("activo");
                String sedeRepuesto = resultSet.getString("sede");

                int nuevaCantidadRepuesto = cantidadRepuesto - cantidadRestante;


                if (nuevaCantidadRepuesto < 0)
                {
                    String textoError = "No hay suficientes repuestos!";
                    validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
                    validacionRegistroLabel.setStyle(mensajeError);
                    txtIdRepuesto.setStyle(estiloMensajeError);
                    new FadeIn(txtIdRepuesto).play();
                }
                else {
                    Repuesto repuestoActualizado = new Repuesto();

                    repuestoActualizado.setId_repuesto(Integer.parseInt(txtIdRepuesto.getText()));
                    repuestoActualizado.setNombre(nombreRepuesto);
                    repuestoActualizado.setMarca(marcaRepuesto);
                    repuestoActualizado.setCedula_creado_por(cedula_creado_por);
                    repuestoActualizado.setFecha_creacion(fecha_creacion);
                    repuestoActualizado.setFecha_modificado(fecha_modificado);
                    repuestoActualizado.setActivo(activo);
                    repuestoActualizado.setSede(sedeRepuesto);
                    repuestoActualizado.setCantidad(nuevaCantidadRepuesto);


                    SQL_Repuesto.editarRepuesto(nombreRepuesto, marcaRepuesto, repuestoActualizado);

                    repuestoOrden.setCantidad(Integer.parseInt(txtCantidadRepuesto.getText()));
                    SQL_RepuestoOrden.editarRepuestoOrden(repuestoOrden.getId_repuesto(),repuestoOrden.getId_orden(), repuestoOrden);
                    this.validadoLabelSet();
                }

            }


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

        col_idOrden.setCellValueFactory(new PropertyValueFactory<>("id_orden"));
        col_idRepuesto.setCellValueFactory(new PropertyValueFactory<>("id_repuesto"));
        col_cantidadRepuestoOrden.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        col_cedulaCreadoPor.setCellValueFactory(new PropertyValueFactory<>("cedula_creado_por"));
        col_fecha_modificacion_repuesto_orden.setCellValueFactory(new PropertyValueFactory<>("fecha_modificado"));
        col_fecha_creacion_repuesto_orden.setCellValueFactory(new PropertyValueFactory<>("fecha_creacion"));
        col_activo_repuesto_orden.setCellValueFactory(new PropertyValueFactory<>("activo"));

        tableGestionRepuestosOrdenes.setItems(repuestosOrdenesList.sorted());

    }

    private void readRepuestos() {
        try {
            ResultSet result = SQL_RepuestoOrden.obtenerTodosRepuestosOrdenesSet();
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
        if(txtIdOrden.getText().isEmpty() || txtIdRepuesto.getText().isEmpty() ||  txtCantidadRepuesto.getText().isEmpty())
        {
            validacionRegistroLabel.setStyle(mensajeError);
            new Shake(txtIdOrden).play();
            new Shake(txtIdRepuesto).play();
            new Shake(txtCantidadRepuesto).play();
            validacionRegistroLabel.setText("Los Ids o la cantidad del repuesto están vacias!");
        }
        else {
            this.borrarRepuestoOrden();
        }
    }

    private boolean borrarRepuestoOrden() {
        int id_repuesto = Integer.parseInt(txtIdRepuesto.getText());
        int id_orden = Integer.parseInt(txtIdOrden.getText());
        boolean validado = true;
        validacionRegistroLabel.setText("");
        // Validacion existencia
        if (SQL_RepuestoOrden.existeRepuestoOrden_Id(id_repuesto, id_orden)) {
            // Validación cantidad
            int cantidadActual = 0;
            ResultSet resultado =  SQL_RepuestoOrden.obtenerRepuestoOrden_Ids(id_repuesto, id_orden);
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
                RepuestoOrden repuestoOrdenActualizado = new RepuestoOrden();
                int cantidadDespuesDeBorrar = cantidadActual - Integer.parseInt(txtCantidadRepuesto.getText());
                try {
                    repuestoOrdenActualizado.setId_repuesto(resultado.getInt("id_repuesto"));
                    repuestoOrdenActualizado.setId_orden(resultado.getInt("id_orden"));

                    // Actualizar Repuesto
                    int cantidadActualRepuestoOrden = 0;
                    try{
                        ResultSet resultRepuesto = SQL_RepuestoOrden.obtenerRepuestoOrden_Ids(Integer.parseInt(txtIdRepuesto.getText()), Integer.parseInt(txtIdOrden.getText()));
                        resultRepuesto.next();
                        cantidadActualRepuestoOrden = resultRepuesto.getInt("cantidad");

                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }


                    ResultSet resultSet = SQL_Repuesto.obtenerRepuesto_Id(Integer.parseInt(txtIdRepuesto.getText()));
                    resultSet.next();
                    int cantidadRepuesto = resultSet.getInt("cantidad");
                    String nombreRepuesto = resultSet.getString("nombre");
                    String marcaRepuesto = resultSet.getString("marca");
                    String cedula_creado_por = resultSet.getString("cedula_creado_por");
                    Date fecha_creacion = resultSet.getDate("fecha_creacion");
                    Date fecha_modificado = resultSet.getDate("fecha_modificado");
                    boolean activo = resultSet.getBoolean("activo");
                    String sedeRepuesto = resultSet.getString("sede");

                    int nuevaCantidadRepuesto = Integer.parseInt(txtCantidadRepuesto.getText()) + cantidadRepuesto;

                    if (nuevaCantidadRepuesto < 0)
                    {
                        String textoError = "No hay suficientes repuestos!";
                        validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
                        validacionRegistroLabel.setStyle(mensajeError);
                        txtIdRepuesto.setStyle(estiloMensajeError);
                        new FadeIn(txtIdRepuesto).play();
                    }
                    else {
                        Repuesto repuestoActualizado = new Repuesto();

                        repuestoActualizado.setId_repuesto(Integer.parseInt(txtIdRepuesto.getText()));
                        repuestoActualizado.setNombre(nombreRepuesto);
                        repuestoActualizado.setMarca(marcaRepuesto);
                        repuestoActualizado.setCedula_creado_por(cedula_creado_por);
                        repuestoActualizado.setFecha_creacion(fecha_creacion);
                        repuestoActualizado.setFecha_modificado(fecha_modificado);
                        repuestoActualizado.setActivo(activo);
                        repuestoActualizado.setSede(sedeRepuesto);
                        repuestoActualizado.setCantidad(nuevaCantidadRepuesto);


                        SQL_Repuesto.editarRepuesto(nombreRepuesto, marcaRepuesto, repuestoActualizado);

                        repuestoOrdenActualizado.setCantidad(cantidadDespuesDeBorrar);
                        SQL_RepuestoOrden.editarRepuestoOrden(id_repuesto, id_orden, repuestoOrdenActualizado);
                        this.validadoLabelSet();
                        this.limpiar();
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        else {
            validado = false;
            String textoError = "No existe un repuesto con esos Ids!";
            validacionRegistroLabel.setText(validacionRegistroLabel.getText() + textoError + '\n');
            validacionRegistroLabel.setStyle(mensajeError);
            txtIdRepuesto.setStyle(estiloMensajeError);
            new FadeIn(txtIdRepuesto).play();
            txtIdOrden.setStyle(estiloMensajeError);
            new FadeIn(txtIdOrden).play();
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







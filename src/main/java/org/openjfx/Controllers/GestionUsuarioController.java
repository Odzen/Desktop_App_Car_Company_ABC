package org.openjfx.Controllers;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import org.openjfx.EmpresaAutosABC;
import org.openjfx.Models.Usuario.SQL_Usuario;
import org.openjfx.Models.Usuario.Usuario;
import org.openjfx.Models.Usuario.Utils.Rol;

public class GestionUsuarioController implements Initializable {

    @FXML
    private Button btnSalir;
    @FXML
    private TableView<Usuario> tableGestionAdmin;
    @FXML
    private TableColumn<Usuario,String> col_idGestionAdmin;
    @FXML
    private TableColumn<Usuario,String> col_cedulaGestionAdmin;
    @FXML
    private TableColumn<Usuario,String> col_contraseñaGestionAdmin;
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
    private TableColumn<Usuario, String> col_telefonoGestionAdmin;
    @FXML
    private TableColumn<Usuario, Boolean> col_activoGestionAdmin;
    @FXML
    private TableColumn<Usuario, Date> col_nacimientoGestionAdmin;
    @FXML
    private TableColumn<Usuario, String> col_last_sessionGestionAdmin;
    @FXML
    private TableColumn<Usuario, String> editCol;

    private ObservableList<Usuario> usuariosList = FXCollections.observableArrayList();

    private Usuario usuario = null;
    
    // Para salir de la aplicación
    @FXML
    protected void btnSalirClick() {
    Stage stage = (Stage) btnSalir.getScene().getWindow();
    stage.close();
    }
   
    @FXML
    private void bttnNuevoUsuarioClicked() throws IOException {
     EmpresaAutosABC.setRoot("registrarUsuario");
    // Animación
    //new animatefx.animation.BounceIn(root).play();
    }
    @FXML
    private void btnInicio() throws IOException {
        EmpresaAutosABC.setRoot("menuAdmin");
    }

    private void loadDate() {
        refreshTable();

        col_idGestionAdmin.setCellValueFactory(new PropertyValueFactory<>("id_usuario"));
        col_cedulaGestionAdmin.setCellValueFactory(new PropertyValueFactory<>("cedula"));
        col_contraseñaGestionAdmin.setCellValueFactory(new PropertyValueFactory<>("contraseña"));
        col_emailGestionAdmin.setCellValueFactory(new PropertyValueFactory<>("contraseña"));
        col_nombreGestionAdmin.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        col_apellidoGestionAdmin.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        col_modificarGestionAdmin.setCellValueFactory(new PropertyValueFactory<>("modificado"));
        col_cargoGestionAdmin.setCellValueFactory(new PropertyValueFactory<>("user_type"));
        col_telefonoGestionAdmin.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        col_joinedGestionAdmin.setCellValueFactory(new PropertyValueFactory<>("joined"));
        col_activoGestionAdmin.setCellValueFactory(new PropertyValueFactory<>("activo"));
        col_nacimientoGestionAdmin.setCellValueFactory(new PropertyValueFactory<>("Fecha_Nacimiento"));
        col_last_sessionGestionAdmin.setCellValueFactory(new PropertyValueFactory<>("last_session"));

        //add cell of button edit
        Callback<TableColumn<Usuario, String>, TableCell<Usuario, String>> cellFoctory = (TableColumn<Usuario, String> param) -> {
            // make cell containing buttons
            final TableCell<Usuario, String> cell = new TableCell<Usuario, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //that cell created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {

                        FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
                        FontAwesomeIconView editIcon = new FontAwesomeIconView(FontAwesomeIcon.PENCIL_SQUARE);

                        deleteIcon.setStyle(
                                " -fx-cursor: hand ;"
                                        + "-glyph-size:28px;"
                                        + "-fx-fill:#ff1744;"
                        );
                        editIcon.setStyle(
                                " -fx-cursor: hand ;"
                                        + "-glyph-size:28px;"
                                        + "-fx-fill:#00E676;"
                        );
                        deleteIcon.setOnMouseClicked((event) -> {
                            try {
                                usuario = tableGestionAdmin.getSelectionModel().getSelectedItem();
                                SQL_Usuario.eliminarUsuarioPorCedula(usuario.getCedula());
                                refreshTable();
                            } catch (Exception e) {
                                Logger.getLogger(GestionUsuarioController.class.getName()).log(Level.SEVERE, null, e);
                            }
                        });
                        editIcon.setOnMouseClicked((event) -> {
                            try{
                                FXMLLoader loader = new FXMLLoader ();
                                loader.setLocation(getClass().getResource("registrarUsuario.fxml"));
                                usuario = tableGestionAdmin.getSelectionModel().getSelectedItem();
                                loader.load();
                                //AddStudentController addStudentController = loader.getController();
                                //addStudentController.setUpdate(true);
                                //addStudentController.setTextField(student.getId(), student.getName(),
                                //        usuario.getBirth().toLocalDate(),student.getAdress(), student.getEmail());

                                Parent parent = loader.getRoot();
                                Stage stage = new Stage();
                                stage.setScene(new Scene(parent));
                                stage.initStyle(StageStyle.UTILITY);
                                stage.show();
                            }catch(Exception e) {
                                Logger.getLogger(GestionUsuarioController.class.getName()).log(Level.SEVERE, null, e);
                            }
                        });

                        HBox managebtn = new HBox(editIcon, deleteIcon);
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(deleteIcon, new Insets(2, 2, 0, 3));
                        HBox.setMargin(editIcon, new Insets(2, 3, 0, 2));

                        setGraphic(managebtn);

                        setText(null);
                    }
                }

            };

            return cell;
        };
        editCol.setCellFactory(cellFoctory);
        tableGestionAdmin.setItems(usuariosList);

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
                usuariosList.add(readUsuario);
            }
        } catch(SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.readUsers();
        this.loadDate();
        tableGestionAdmin.setItems(usuariosList);
    }

    @FXML
    private void getAddView(MouseEvent event) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("registrarUsuario.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(GestionUsuarioController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    //@FXML
    private void refreshTable() {
        usuariosList.clear();
        this.readUsers();
    }

    //@FXML
    private void print(MouseEvent event) {
    }

    /**
     * Aux Class to Model the Table
     */
    public class ModelTable {
        private String cedula, nombre, cargo;
        private Date modificado;
        private boolean activo;

        public ModelTable(String cedula, String nombre, String cargo, Date modificado, boolean activo) {
            this.cedula = cedula;
            this.nombre = nombre;
            this.cargo = cargo;
            this.modificado = modificado;
            this.activo = activo;
        }

        public String getCedula() {
            return cedula;
        }

        public void setCedula(String id) {
            this.cedula = id;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getCargo() {
            return cargo;
        }

        public void setCargo(String cargo) {
            this.cargo = cargo;
        }

        public Date getModificado() {
            return modificado;
        }

        public void setModificado(Date modificado) {
            this.modificado = modificado;
        }

        public boolean isActivo() {
            return activo;
        }

        public void setActivo(boolean activo) {
            this.activo = activo;
        }
    }
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package org.openjfx.Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.*;

import GlobalUtils.Dialogs;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.openjfx.EmpresaAutosABC;
import org.openjfx.Models.Automovil.Automovil;
import org.openjfx.Models.Automovil.SQL_Automovil;
import org.openjfx.Models.Conexion;


public class ResumenVendedorController implements Initializable {
    @FXML
    private Button btnCerrarSesion;

    @FXML
    private Button btnSalir;

    @FXML
    private Label bttnSalir;


    @FXML
    private PieChart grafico_año_Vendedor;



    @FXML
    private PieChart grafico_color_Vendedor;

    @FXML
    private PieChart grafico_marca_Vendedor;
    @FXML
    private Label lblSaludo;

    @FXML
    private Tab ventana_año_autos_vendedor;

    @FXML
    private Tab ventana_color_autos_vendedor;

    @FXML
    private Tab ventana_marca_autos_vendedor;

    @FXML
    private AnchorPane ventana_marca_vendedor;

    @FXML
    private AnchorPane ventana_marca_vendedor1;


    private void dataMarca() {
        try {
            ResultSet result = SQL_Automovil.obtenerTodosAutomovilSet();
            while (result.next()) {
                Automovil dataMarca = new Automovil();

                dataMarca.setMarca (result.getString("marca"));

            }
        } catch(SQLException exception) {
            throw new RuntimeException(exception);
        }

    }


    // Para salir de la aplicación
    @FXML
    private void btnSalirClickmenu() {
        if (Dialogs.showConfirm("Seleccione una opción", "¿Está seguro que quiere salir de la aplicación?", Dialogs.YES, Dialogs.NO).equals(Dialogs.YES)) {
            Stage stage = (Stage) btnSalir.getScene().getWindow();
        stage.close();}
    }

    @FXML
    protected void btnInicio() throws IOException {
        EmpresaAutosABC.setRoot("menuVendedor");
    }

    @FXML
    private void btnCerrarSesionClick() throws IOException{
        if (Dialogs.showConfirm("Seleccione una opción", "¿Está seguro que quiere cerrar sesión?", Dialogs.YES, Dialogs.NO).equals(Dialogs.YES)) {
            EmpresaAutosABC.setRoot("Login");
        }
    }



    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        ObservableList<PieChart.Data> pieChartDataMarca =
                FXCollections.observableArrayList(
                  new PieChart.Data("Mazda", 50),
                  new PieChart.Data("Chevrolet", 50)
                );
        grafico_marca_Vendedor.setData(pieChartDataMarca);



        ObservableList<PieChart.Data> pieChartDataAño =
                FXCollections.observableArrayList(
                        new PieChart.Data("2021", 50),
                        new PieChart.Data("2020", 25),
                        new PieChart.Data("2022",25)
                );
        grafico_año_Vendedor.setData(pieChartDataAño);

        ObservableList<PieChart.Data> pieChartDataColor =
                FXCollections.observableArrayList(
                        new PieChart.Data("Rojo", 25),
                        new PieChart.Data("Azul", 25),
                        new PieChart.Data("Blanco", 25),
                        new PieChart.Data("Negro",25)
                );
        grafico_color_Vendedor.setData(pieChartDataColor);


        // loadData();

    }
   /* public static ResultSet obtenerTodosMarcasSet() {
        try {
            PreparedStatement sentencia = connection.prepareStatement(
                    "SELECT COUNT(*) C, marca M FROM automovil"
            );

            ResultSet resultado = sentencia.executeQuery();
            return resultado;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }*/


    /*public int contar_marca() throws SQLException {
        int n = 0;
        private static Conexion conexion = new Conexion();
        private static Connection connection = conexion.getConnection();
        Statement stm = connection.createStatement();

        // almaceno resultado de consulta en ResultSet
        ResultSet result = stm.executeQuery("SELECT count(*) FROM automovil");

        if (result.next()) {
            //Si hay resultados obtengo el valor.
            n = result.getInt(1);
        }
        // libero recursos
        stm.close();
        connection.close();
        return n;
    }*/


    ArrayList < String > p = new ArrayList <String> ();
    ArrayList < Double > c = new ArrayList <> ();

    public void loadData() {
        ObservableList < PieChart.Data > piechartdata;
        piechartdata = FXCollections.observableArrayList();

        try {
            Conexion conexion = new Conexion();
            Connection connection = conexion.getConnection();
            //Sql
            Statement consulta= connection.createStatement();
            ResultSet cantidad = consulta.executeQuery("SELECT COUNT(*) AS CANTIDAD, marca FROM automovil");
            if(cantidad.next()){

                //System.out.println(cantidad.getString("CANTIDAD"));

                piechartdata.add(new PieChart.Data(cantidad.getString("marca"), cantidad.getDouble("CANTIDAD")));
                p.add(cantidad.getString("marca"));
                c.add(cantidad.getDouble("CANTIDAD"));

                grafico_marca_Vendedor.setData(piechartdata);
                grafico_marca_Vendedor.setVisible(true);

            }else{
                System.out.println("Ningun resultado encontrado");
            }

        } catch (SQLException e) {
            e.printStackTrace();

        }
    }
}
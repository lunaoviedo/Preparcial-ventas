package com.example.taller_5preparcial.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class GeneralController{

    @FXML
    private Button btnClientes;

    @FXML
    private Button btnProductos;

    @FXML
    private Button btnVentas;

    @FXML
    private BorderPane mainBorderPane;

    @FXML
    private AnchorPane panelContenido;

    @FXML
    void onClientes() throws IOException {
        AnchorPane panelConsultarClientes = FXMLLoader.load(
                getClass().getResource("/com/example/taller_5preparcial/RegistroClientes.fxml"));
        panelContenido.getChildren().clear();
        panelContenido.getChildren().add(panelConsultarClientes);
    }

    @FXML
    void onProductos() throws IOException {
        AnchorPane panelConsultarProductos = FXMLLoader.load(
                getClass().getResource("/com/example/taller_5preparcial/RegistroProductos.fxml"));
        panelContenido.getChildren().clear();
        panelContenido.getChildren().add(panelConsultarProductos);

    }

    @FXML
    void onVentas() throws IOException {
        AnchorPane panelConsultarVentas = FXMLLoader.load(
                getClass().getResource("/com/example/taller_5preparcial/RegistroVentas.fxml"));
        panelContenido.getChildren().clear();
        panelContenido.getChildren().add(panelConsultarVentas);

    }

}

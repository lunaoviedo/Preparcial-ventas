package com.example.taller_5preparcial.Controllers;

import com.example.taller_5preparcial.Models.Cliente;
import com.example.taller_5preparcial.Repository.ClienteRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.util.Optional;

public class ClientesController {
    @FXML
    private Button btnAgregar;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnModificar;

    @FXML
    private TableColumn<Cliente,String> colCorreo;

    @FXML
    private TableColumn<Cliente, String> colId;

    @FXML
    private TableColumn<Cliente, String> colNombre;
    @FXML
    private TableView<Cliente> tablaClientes;
    @FXML
    private TextField txtCorreo;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtTel;


    @FXML
    private TableColumn<Cliente, String> colTel;
    private AnchorPane panelContenido;
    private ClienteRepository clienteRepository;
    private ObservableList<Cliente> clientesObservable;
    @FXML
    public void initialize() {
        clienteRepository = ClienteRepository.getInstancia();
        configurarTabla();
        cargarClientes();
        btnEliminar.setDisable(true);
        btnModificar.setDisable(true);
        tablaClientes.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    boolean isSelected = newValue != null;
                    btnEliminar.setDisable(isSelected);
                    btnModificar.setDisable(!isSelected);
                    mostrarCliente(newValue);
                    txtId.setDisable(isSelected);
                }
        );
    }
    private void configurarTabla() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colTel.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colCorreo.setCellValueFactory(new PropertyValueFactory<>("email"));
    }
    private void cargarClientes() {
        clientesObservable = FXCollections.observableArrayList(
                clienteRepository.getClientes());
        tablaClientes.setItems(clientesObservable);
    }
    private void limpiarCampos() {
        txtId.clear();
        txtNombre.clear();
        txtTel.clear();
        txtCorreo.clear();
        txtId.setDisable(false);
        tablaClientes.getSelectionModel().clearSelection();
    }


    @FXML
    void onAgregar() {
        if(!validarCampos()){
            return;
        } try{
            String id = txtId.getText();
            String name = txtNombre.getText();
            String phone = txtTel.getText();
            String email = txtCorreo.getText();
            Cliente cliente= new Cliente(id, name, phone, email);
            clienteRepository.addCliente(cliente);
            mostrarAlerta("Éxito", "Cliente agregado correctamente.", Alert.AlertType.INFORMATION);
            actualizarTabla();
            limpiarCampos();
        }catch (Exception e) {
            mostrarAlerta("Error", "Error al agregar el cliente: " + e.getMessage(), Alert.AlertType.ERROR);
        }

    }
    private boolean validarCampos() {
        if(txtId.getText().isEmpty() || txtNombre.getText().isEmpty() || txtTel.getText().isEmpty()||
                txtCorreo.getText().isEmpty()) {
            mostrarAlerta("Error", "Todos los campos deben ser diligenciados obligatoriamente.", Alert.AlertType.WARNING);
            return false;
        } return true;
    }

    @FXML
    void onEliminar() {
        Cliente cliente = tablaClientes.getSelectionModel().getSelectedItem();
        if(cliente == null){
            mostrarAlerta("Error", "Seleccione un cliente para eliminar.", Alert.AlertType.WARNING);
            return;
        }
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setHeaderText("¿Estás seguro?");
        confirmacion.setContentText("¿Deseas eliminar el cliente: " + cliente.getNombre() + "?");

        Optional<ButtonType> resultado = confirmacion.showAndWait();
        if(resultado.isPresent() && resultado.get() == ButtonType.OK){
            try{
                clienteRepository.removeCliente(cliente);
                cargarClientes();
                mostrarAlerta("Exito", "cliente eliminado correctamente.", Alert.AlertType.INFORMATION);
                limpiarCampos();
            } catch (Exception e) {
                mostrarAlerta("Exito", "Error al eliminar el Cliente: "+ e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }
    public void actualizarTabla() {
        cargarClientes();
    }

    private void mostrarCliente(Cliente cliente) {
        if(cliente != null){
            txtId.setText(cliente.getId());
            txtNombre.setText(cliente.getNombre());
            txtTel.setText(cliente.getTelefono());
            txtCorreo.setText(cliente.getEmail());
            txtId.setDisable(true);
        } else {
            limpiarCampos();
            txtId.setDisable(false);
        }
    }

    @FXML
    void onModificar() {
        if(!validarCampos()){
            return;
        }
        Cliente cliente = tablaClientes.getSelectionModel().getSelectedItem();
        if(cliente == null){
            mostrarAlerta("Error", "Seleccione un cliente para modificar.", Alert.AlertType.WARNING);
            return;
        }
        try{
            cliente.setNombre(txtNombre.getText());
            cliente.setTelefono(txtTel.getText());
            cliente.setEmail(txtCorreo.getText());
            actualizarTabla();
            tablaClientes.refresh();
            mostrarAlerta("Éxito", "Cliente modificado correctamente.", Alert.AlertType.INFORMATION);
            limpiarCampos();
        } catch (Exception e) {
            mostrarAlerta("Error", "Error al modificar el cliente: " + e.getMessage(), Alert.AlertType.ERROR);
        }


    }
    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

}


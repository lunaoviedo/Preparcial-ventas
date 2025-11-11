package com.example.taller_5preparcial.Controllers;

import com.example.taller_5preparcial.Models.Producto;
import com.example.taller_5preparcial.Repository.ProductoRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TextField;
import java.awt.*;
import java.util.Optional;

public class ProductosController {
    @FXML
    private Button btnAgregar;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnModificar;

    @FXML
    private TableColumn<Producto, String> colCodigo;

    @FXML
    private TableColumn<Producto, String> colNombre;

    @FXML
    private TableColumn<Producto, Double> colPrecio;

    @FXML
    private TableColumn<Producto, Integer> colStock;
    @FXML
    private TableView<Producto> tablaProductos;
    @FXML
    private TextField txtCodigo;

    @FXML
    private javafx.scene.control.TextField txtNombre;

    @FXML
    private TextField txtPrecio;

    @FXML
    private TextField txtStock;
    private ObservableList<Producto> productosObservable;
    private ProductoRepository productoRepository;
    @FXML
    public void initialize(){
        productoRepository =ProductoRepository.getInstancia();
        configurarTabla();
        cargarProductos();
    }
    private void configurarTabla(){
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        colStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
    }
    private void cargarProductos(){
        productosObservable = FXCollections.observableArrayList(
                productoRepository.getProductos());

        tablaProductos.setItems(productosObservable);
    }
    private void limpiarCampos(){
        txtCodigo.clear();
        txtNombre.clear();
        txtPrecio.clear();
        txtStock.clear();
        txtCodigo.setDisable(false);
        txtNombre.setDisable(false);
        tablaProductos.getSelectionModel().clearSelection();
    }

    @FXML
    void onAgregar() {
        if(!validarCampos()){
            return;
        } try{
            String codigo = txtCodigo.getText();
            String name = txtNombre.getText();
            Double precio;
            int stock;
            try{
                precio = Double.parseDouble(txtPrecio.getText());
                stock = Integer.parseInt(txtStock.getText());
            }catch (NumberFormatException e) {
                mostrarAlerta("Error de Formato", "El precio y el stock deben ser números válidos.", Alert.AlertType.ERROR);
                return;
            }
            Producto producto = new Producto(codigo, name, precio, stock);
            productoRepository.agregarProducto(producto);
            mostrarAlerta("Éxito", "Producto agregado correctamente.", Alert.AlertType.INFORMATION);
            actualizarTabla();
            limpiarCampos();
        } catch (Exception e) {
            mostrarAlerta("Error", "Error al agregar el Producto: " + e.getMessage(), Alert.AlertType.ERROR);
        }

    }
    private boolean validarCampos(){
        if(txtCodigo.getText().isEmpty() || txtNombre.getText().isEmpty() || txtPrecio.getText().isEmpty()|| txtStock.getText().isEmpty()){
            mostrarAlerta("Error", "Todos los campos deben ser diligenciados obligatoriamente.", Alert.AlertType.WARNING);
            return false;
        } return  true;
    }

    @FXML
    void onEliminar() {
        Producto producto = tablaProductos.getSelectionModel().getSelectedItem();
        if(producto == null){
            mostrarAlerta("Error", "Seleccione un producto para eliminar.", Alert.AlertType.WARNING);
            return;
        }
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setHeaderText("¿Estás seguro?");
        confirmacion.setContentText("¿Deseas eliminar el producto: " + producto.getNombre() + "?");

        Optional<ButtonType> resultado = confirmacion.showAndWait();
        if(resultado.isPresent() && resultado.get() == ButtonType.OK){
            try{
                productoRepository.eliminarProducto(producto);
                cargarProductos();
                mostrarAlerta("Exito", "producto eliminado correctamente.", Alert.AlertType.INFORMATION);
                limpiarCampos();
            } catch (Exception e) {
                mostrarAlerta("Error", "Error al eliminar el producto: "+ e.getMessage(), Alert.AlertType.ERROR);
            }
        }

    }
    public void actualizarTabla(){cargarProductos();}
    private void mostrarProducto(Producto producto){
        if(producto !=null){
            txtCodigo.setText(producto.getCodigo());
            txtNombre.setText(producto.getNombre());
            txtPrecio.setText(String.valueOf(producto.getPrecio()));
            txtStock.setText(String.valueOf(producto.getStock()));
        } else{
            limpiarCampos();
        }
    }

    @FXML
    void onModificar() {
        if(!validarCampos()){
            return;
        }
        Producto producto = tablaProductos.getSelectionModel().getSelectedItem();
        if(producto == null){
            mostrarAlerta("Error", "Seleccione un producto para modificar.", Alert.AlertType.WARNING);
            return;
        }try{
            producto.setPrecio(Double.parseDouble(txtPrecio.getText()));
            producto.setStock(Integer.parseInt(txtStock.getText()));
            actualizarTabla();
            tablaProductos.refresh();
            mostrarAlerta("Éxito", "Producto modificado correctamente.", Alert.AlertType.INFORMATION);
            limpiarCampos();
        }catch (Exception e) {
            mostrarAlerta("Error", "Error al modificar el Producto: " + e.getMessage(), Alert.AlertType.ERROR);
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

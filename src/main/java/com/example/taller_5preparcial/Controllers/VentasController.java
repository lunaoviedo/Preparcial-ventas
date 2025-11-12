package com.example.taller_5preparcial.Controllers;

import com.example.taller_5preparcial.Models.Cliente;
import com.example.taller_5preparcial.Models.DetalleVenta;
import com.example.taller_5preparcial.Models.Producto;
import com.example.taller_5preparcial.Repository.ClienteRepository;
import com.example.taller_5preparcial.Repository.ProductoRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.StringConverter;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.event.ActionEvent;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class VentasController {
    @FXML
    private Button btnVender;
    @FXML
    private ComboBox<Cliente> cmbCliente;
    @FXML
    private ComboBox<Producto> cmbProducto;
    @FXML
    private TableColumn<DetalleVenta, Integer> colCantidad;
    @FXML
    private TableColumn<DetalleVenta, String> colCliente;
    @FXML
    private TableColumn<DetalleVenta, String> colProducto;
    @FXML
    private TableColumn<DetalleVenta, LocalDate> colFecha;
    @FXML
    private TableColumn<DetalleVenta, Double> colSubtotal;
    @FXML
    private Spinner<Integer> spinnerCantidad;
    @FXML
    private Label lblHora;
    @FXML
    private Label lblPrecio;
    @FXML
    private Label lblSubtotal;

    @FXML
    private TableView<DetalleVenta> tablaVentas;
    private ObservableList<DetalleVenta> listaVentas;
    private ProductoRepository productoRepository;
    private ClienteRepository clienteRepository;
    private double precioUnitario = 0.0;
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    @FXML
    public void initialize() {
        productoRepository = ProductoRepository.getInstancia();
        clienteRepository = ClienteRepository.getInstancia();
        listaVentas = FXCollections.observableArrayList();
        configurarTabla();
        inicializarComponentes();
        configListeners();
        actualizarHora();
    }
    @FXML
    public void inicializarComponentes() {
        cargarClientes();
        cargarProductos();
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1, 1);
        spinnerCantidad.setValueFactory(valueFactory);
        lblPrecio.setText("$ 0.00");
    }
    private void configListeners() {
        cmbProducto.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null){
                precioUnitario = newValue.getPrecio();
                int stock = newValue.getStock();
                lblPrecio.setText("$ "+ String.format("%.2f", precioUnitario));
                actualizarSpinner(stock);
                calcularSubtotal();

            } else{
                lblPrecio.setText("$ 0.00");
                precioUnitario = 0.0;
                actualizarSpinner(1);
            }
        });
        spinnerCantidad.valueProperty().addListener((observable, oldValue, newValue) -> {
            calcularSubtotal();
        });

    }
    private void actualizarHora(){
        lblHora.setText(LocalDateTime.now().format(TIME_FORMATTER));
    }

    private void actualizarSpinner(int stock){
        SpinnerValueFactory.IntegerSpinnerValueFactory factory= (SpinnerValueFactory.IntegerSpinnerValueFactory) spinnerCantidad.getValueFactory();
        factory.setMax(Math.max(1, stock));
        if(spinnerCantidad.getValue()>stock){
            spinnerCantidad.getValueFactory().setValue(stock);
        }
    }
    private void calcularSubtotal() {
        if (precioUnitario > 0 && spinnerCantidad.getValue() != null) {
            int cantidad = spinnerCantidad.getValue();
            double subtotal = precioUnitario * cantidad;
            lblSubtotal.setText("$ " + String.format("%.2f", subtotal)); // ✅ actualiza el label
        } else {
            lblSubtotal.setText("$ 0.00"); // por si no hay producto
        }
    }

    private void cargarClientes() {
        cmbCliente.setItems(FXCollections.observableArrayList(clienteRepository.getClientes()));

        cmbCliente.setConverter(new StringConverter<Cliente>() {
            @Override
            public String toString(Cliente cliente) {
                return (cliente != null) ? cliente.getNombre() : "";
            }
            @Override
            public Cliente fromString(String string) {
                return null;
            }
        });
    }
    private void cargarProductos() {
        cmbProducto.setItems(FXCollections.observableArrayList(productoRepository.getProductos()));
        cmbProducto.setConverter(new StringConverter<Producto>() {
            @Override
            public String toString(Producto producto) {
                return (producto != null) ? producto.getNombre(): "";
            }

            @Override
            public Producto fromString(String s) {
                return null;
            }
        });
    }

    @FXML
    void onVender(ActionEvent event) {
        Cliente cliente = cmbCliente.getSelectionModel().getSelectedItem();
        if(cliente == null){
            mostrarAlerta("Error de Venta", "Debe seleccionar un cliente.", Alert.AlertType.WARNING);
            return;
        }
        Producto producto = cmbProducto.getSelectionModel().getSelectedItem();
        if (producto == null) {
            mostrarAlerta("Error de Venta", "Debe seleccionar un producto.", Alert.AlertType.WARNING);
            return;
        }
        int cantidad = spinnerCantidad.getValue();
        if (cantidad > producto.getStock()) {
            mostrarAlerta("Error de Stock", "No hay suficiente stock. Disponible: " + producto.getStock(), Alert.AlertType.ERROR);
            return;
        } try{
            int nuevoStock = producto.getStock()-cantidad;
            producto.setStock(nuevoStock);
            double subtotal = precioUnitario*cantidad;
            DetalleVenta detalle = new DetalleVenta(
                    LocalDate.now(),
                    cliente.getNombre(),
                    producto.getNombre(),
                    cantidad,
                    subtotal
            );
            listaVentas.add(detalle);
            limpiarCampos();
            mostrarAlerta("Venta Exitosa", "Venta registrada en la tabla.", Alert.AlertType.INFORMATION);

        } catch (Exception e) {
            mostrarAlerta("Error", "Error al procesar la venta: " + e.getMessage(), Alert.AlertType.ERROR);
        }

    }
    private void limpiarCampos() {
        cmbProducto.getSelectionModel().clearSelection();
        lblPrecio.setText("$ 0.00");
        lblSubtotal.setText("$ 0.00"); // ✅ reinicia el subtotal

        SpinnerValueFactory.IntegerSpinnerValueFactory factory =
                (SpinnerValueFactory.IntegerSpinnerValueFactory) spinnerCantidad.getValueFactory();
        factory.setMax(1);
        factory.setValue(1);
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    private void configurarTabla() {
        tablaVentas.setItems(listaVentas);
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        colCliente.setCellValueFactory(new PropertyValueFactory<>("cliente"));
        colProducto.setCellValueFactory(new PropertyValueFactory<>("producto"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        colSubtotal.setCellValueFactory(new PropertyValueFactory<>("subtotal"));
        actualizarHora();
        cmbCliente.getSelectionModel().clearSelection();

    }
}

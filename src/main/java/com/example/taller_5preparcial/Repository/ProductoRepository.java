package com.example.taller_5preparcial.Repository;

import com.example.taller_5preparcial.Models.Producto;

import java.util.ArrayList;

public class ProductoRepository {

    private static ProductoRepository instancia;
    private ArrayList<Producto> productos;

    private ProductoRepository() {
        productos = new ArrayList<>();
        cargarDatosEjemplo();
    }


    public static ProductoRepository getInstancia() {
        if (instancia == null) {
            instancia = new ProductoRepository();
        }
        return instancia;
    }


    private void cargarDatosEjemplo() {
        productos.add(new Producto("123659", "Barra de Chocolate", 5000.0, 23));
        productos.add(new Producto("155441", "Bocadillo", 2000.0, 30));

    }


    public ArrayList<Producto> getProductos() {

        return productos;
    }


    public void agregarProducto(Producto producto) {

        productos.add(producto);

    }


    public boolean eliminarProducto(Producto producto) {

        return productos.remove(producto);

    }


    public int getCantidadProductos() {
        return productos.size();
    }
}

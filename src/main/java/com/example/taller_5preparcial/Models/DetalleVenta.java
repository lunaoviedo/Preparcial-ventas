package com.example.taller_5preparcial.Models;

import java.time.LocalDate;

public class DetalleVenta {
    private String fecha;
    private String cliente;
    private String producto;
    private int cantidad;
    private double subtotal;

    public DetalleVenta(LocalDate fecha, String cliente, String producto, int cantidad, double subtotal) {
        this.fecha = fecha.toString();
        this.cliente = cliente;
        this.producto = producto;
        this.cantidad = cantidad;
        this.subtotal = subtotal;
    }

    public String getFecha() {
        return fecha;
    }

    public String getCliente() {
        return cliente;
    }

    public String getProducto() {
        return producto;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public int getCantidad() {
        return cantidad;
    }
}

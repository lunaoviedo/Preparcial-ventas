package com.example.taller_5preparcial.Models;

public class Producto {
    private String id;
    private String nombre;
    private Double precio;
    private Integer stock;

    public Producto() {}

    public Producto (String id, String nombre, Double precio, Integer stock) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getCodigo() {
        return null;
    }
}


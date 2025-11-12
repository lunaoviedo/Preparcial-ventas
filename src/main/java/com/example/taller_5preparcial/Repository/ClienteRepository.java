package com.example.taller_5preparcial.Repository;

import com.example.taller_5preparcial.Models.Cliente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ClienteRepository {

    private static ClienteRepository instancia;
    private final ObservableList<Cliente> clientes;

    private ClienteRepository() {
        clientes = FXCollections.observableArrayList();
        cargarDatosEjemplo();
    }

    public static ClienteRepository getInstancia() {
        if (instancia == null) {
            instancia = new ClienteRepository();
        }
        return instancia;
    }

    private void cargarDatosEjemplo() {
        clientes.add(new Cliente("1056829202", "Daniela Vega", "3104250997", "DanieVega@gmail.com"));
        clientes.add(new Cliente("109097508", "Simon Veraniega", "3207411485", "Simonettapatineta@gmail.com"));
    }

    public ObservableList<Cliente> getClientes() {
        return clientes;
    }

    public void addCliente(Cliente cliente) {
        clientes.add(cliente);
    }

    public void removeCliente(Cliente cliente) {
        clientes.remove(cliente);
    }
}

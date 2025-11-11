package com.example.taller_5preparcial.Repository;

import com.example.taller_5preparcial.Models.Cliente;

import java.util.ArrayList;

public class ClienteRepository {

        private static ClienteRepository instancia;
        private ArrayList<Cliente> clientes;

        private ClienteRepository() {
            clientes = new ArrayList<>();
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


        public ArrayList<Cliente> getClientes() {

            return clientes;
        }


        public void agregarCliente(Cliente cliente) {

            clientes.add(cliente);

        }


    public boolean eliminarCliente(Cliente cliente) {
        return false;
    }


        public int getCantidadClientes() {
            return clientes.size();
        }

    public void addCliente(Cliente cliente) {
    }

    public void removeCliente(Cliente cliente) {
    }
}


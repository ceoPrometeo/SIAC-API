package org.example.siecapi.controllers.User.dto;

import jakarta.persistence.*;
import org.example.siecapi.models.cliente.Cliente;
import org.example.siecapi.models.usuarios.Roles.Roles;

import java.util.List;

public class AsesorDto {

    private String nombre;

    private String correo;

    private String telefono;

    private boolean estado;

    private List<Cliente> clientes;


    private List<Roles> rol;

    private String password;

    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public boolean isEstado() {
        return estado;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public List<Roles> getRol() {
        return rol;
    }

    public String getPassword() {
        return password;
    }
}

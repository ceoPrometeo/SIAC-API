package org.example.siecapi.controllers.Cliente.dto;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.example.siecapi.models.cliente.Cliente;
import org.example.siecapi.models.usuarios.Usuarios;

import java.time.LocalDate;

public class ContratoDto {
    private String cuentaMT5;
    private Double monto;
    private LocalDate fecha_inicio;
    private LocalDate fecha_renovacion;
    private boolean estatus_renovacion;
    private Cliente cliente;
    private Usuarios usuario;

    public String getCuentaMT5() {
        return cuentaMT5;
    }

    public Double getMonto() {
        return monto;
    }

    public LocalDate getFecha_inicio() {
        return fecha_inicio;
    }

    public LocalDate getFecha_renovacion() {
        return fecha_renovacion;
    }

    public boolean isEstatus_renovacion() {
        return estatus_renovacion;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Usuarios getUsuario() {
        return usuario;
    }
}

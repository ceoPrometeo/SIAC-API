package org.example.siecapi.controllers.Cliente.dto;

import org.example.siecapi.models.usuarios.Usuarios;

import java.time.LocalDate;

public class ContratoResponseDto {

    private String cuentaMT5;
    private Double monto;
    private LocalDate fechaInicio;
    private LocalDate fechaRenovacion;
    private String estatusRenovacion;
    private String tipoContrato;
    private Usuarios usuario;

    // Getters y Setters

    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }

    public String getCuentaMT5() {
        return cuentaMT5;
    }
    public void setCuentaMT5(String cuentaMT5) {
        this.cuentaMT5 = cuentaMT5;
    }

    public Double getMonto() {
        return monto;
    }
    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }
    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaRenovacion() {
        return fechaRenovacion;
    }
    public void setFechaRenovacion(LocalDate fechaRenovacion) {
        this.fechaRenovacion = fechaRenovacion;
    }

    public String getEstatusRenovacion() {
        return estatusRenovacion;
    }

    public void setEstatusRenovacion(String estatusRenovacion) {
        this.estatusRenovacion = estatusRenovacion;
    }

    public String getTipoContrato() {
        return tipoContrato;
    }
    public void setTipoContrato(String tipoContrato) {
        this.tipoContrato = tipoContrato;
    }
}

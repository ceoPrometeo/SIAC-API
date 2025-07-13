package org.example.siecapi.controllers.Cliente.dto;

import java.time.LocalDate;

public class ContratoDto {

    private String cuentaMT5;
    private Double monto;
    private LocalDate fecha_inicio;
    private LocalDate fecha_renovacion;
    private String estatus_renovacion;
    private String tipo_de_Contrato;

    // Getters y setters

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

    public LocalDate getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(LocalDate fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public LocalDate getFecha_renovacion() {
        return fecha_renovacion;
    }

    public void setFecha_renovacion(LocalDate fecha_renovacion) {
        this.fecha_renovacion = fecha_renovacion;
    }

    public String getEstatus_renovacion() {
        return estatus_renovacion;
    }

    public void setEstatus_renovacion(String estatus_renovacion) {
        this.estatus_renovacion = estatus_renovacion;
    }

    public String getTipo_de_Contrato() {
        return tipo_de_Contrato;
    }

    public void setTipo_de_Contrato(String tipo_de_Contrato) {
        this.tipo_de_Contrato = tipo_de_Contrato;
    }
}

package org.example.siecapi.controllers.Cliente.dto;

import java.util.List;

public class ClienteResponseDto {

    private Long id;
    private String nombre;
    private String correo;
    private String telefono;
    private boolean estado;
    private Double carteraMonto;
    private List<ContratoResponseDto> contratos;

    // Getters y Setters

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public boolean isEstado() {
        return estado;
    }
    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public Double getCarteraMonto() {
        return carteraMonto;
    }
    public void setCarteraMonto(Double carteraMonto) {
        this.carteraMonto = carteraMonto;
    }

    public List<ContratoResponseDto> getContratos() {
        return contratos;
    }
    public void setContratos(List<ContratoResponseDto> contratos) {
        this.contratos = contratos;
    }
}

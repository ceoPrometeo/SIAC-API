package org.example.siecapi.controllers.Cliente.dto;

import org.example.siecapi.models.cateras.Cartera;
import org.example.siecapi.models.contratos.Contratos;
import org.example.siecapi.models.usuarios.Roles.Roles;
import org.example.siecapi.models.usuarios.Usuarios;

import java.util.Date;
import java.util.List;

public class ClienteDto {

    private Long id;
    private String nombre;
    private boolean estado;
    private String correo;
    private String telefono;
    private Date fechaContrato;
    private Usuarios asesor;
    private List<ContratoDto> contratos;
    private Cartera cartera;
    private List<Roles> rol;




    // get y sets


    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public boolean isEstado() {
        return estado;
    }

    public String getCorreo() {
        return correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public Date getFechaContrato() {
        return fechaContrato;
    }

    public Usuarios getAsesor() {
        return asesor;
    }

    public List<ContratoDto> getContratos() {
        return contratos;
    }

    public Cartera getCartera() {
        return cartera;
    }

    public List<Roles> getRol() {
        return rol;
    }
}

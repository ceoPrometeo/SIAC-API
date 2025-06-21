package org.example.siecapi.controllers.asesores;

import jakarta.validation.constraints.NotBlank;
import org.example.siecapi.models.usuarios.Roles.Roles;

import java.util.Date;
import java.util.List;

public class AsesorDto {

    private Long id;
    @NotBlank(message = "Es necesario que coloques un nunero de cuenta MT5 para continuar")
    private String cuentaMt5;
    private String nombre;
    private Double monto;
    private boolean estado;
    private String correo;
    private String telefono;
    private Date fechaContrato;
    private String password;
    private List<Roles> rol;

    //GET Y SETS


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank(message = "Es necesario que coloques un nunero de cuenta MT5 para continuar") String getCuentaMt5() {
        return cuentaMt5;
    }

    public void setCuentaMt5(@NotBlank(message = "Es necesario que coloques un nunero de cuenta MT5 para continuar") String cuentaMt5) {
        this.cuentaMt5 = cuentaMt5;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
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

    public Date getFechaContrato() {
        return fechaContrato;
    }

    public void setFechaContrato(Date fechaContrato) {
        this.fechaContrato = fechaContrato;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Roles> getRol() {
        return rol;
    }

    public void setRol(List<Roles> rol) {
        this.rol = rol;
    }
}

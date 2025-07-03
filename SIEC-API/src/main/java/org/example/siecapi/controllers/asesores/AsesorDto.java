package org.example.siecapi.controllers.asesores;

import jakarta.validation.constraints.NotBlank;
import org.example.siecapi.models.usuarios.Roles.Roles;

import java.util.Date;
import java.util.List;

public class AsesorDto {

    private Long id;
    @NotBlank(message = "Es necesario que coloques un numero de cuenta MT5 para continuar")
    private String cuentaMt5;
    @NotBlank(message = "Es necesario que coloques un nombre para continuar")
    private String nombre;
    @NotBlank(message = "Es necesario que coloques un monto para continuar")
    private Double monto;
    @NotBlank(message = "Es necesario que coloques un estado para continuar")
    private boolean estado;
    @NotBlank(message = "Es necesario que coloques un correo para continuar")
    private String correo;
    @NotBlank(message = "Es necesario que coloques un telefono para continuar")
    private String telefono;
    @NotBlank(message = "Es necesario que coloques una fecha de contrato para continuar")
    private Date fechaContrato;
    @NotBlank(message = "Es necesario que coloques una contraseña para continuar")
    private String password;
    @NotBlank(message = "El setteo del rol ha fallado")
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

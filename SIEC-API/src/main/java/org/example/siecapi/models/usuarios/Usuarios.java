package org.example.siecapi.models.usuarios;

import jakarta.persistence.*;
import org.example.siecapi.models.usuarios.Roles.Roles;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Usuarios")
public class Usuarios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "Numero_Cuenta_MT5", nullable = false)
    private String numeroCuentaMT5;
    @Column(name = "Nombre", nullable = false, length = 100)
    private String nombre;
    @Column(name = "Monto")
    private Double monto;
    @Column(name = "Correo", nullable = false, length = 40)
    private String correo;
    @Column(name = "Telefono", nullable = false, length = 10)
    private String telefono;
    @Column(name = "Estado", nullable = false)
    private boolean estado;
    @Column(name = "Fecha_Contrato", nullable = false)
    private Date fechaContrato;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "Usuarios_Roles", // tabla intermedia
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "rol_id")
    )
    private List<Roles> rol;
    @Column(name = "Password")
    private String password;


    //Getters y setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroCuentaMT5() {
        return numeroCuentaMT5;
    }

    public void setNumeroCuentaMT5(String numeroCuentaMT5) {
        this.numeroCuentaMT5 = numeroCuentaMT5;
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

    public Date getFechaContrato() {
        return fechaContrato;
    }

    public void setFechaContrato(Date fechaContrato) {
        this.fechaContrato = fechaContrato;
    }

    public List<Roles> getRol() {
        return rol;
    }

    public void setRol(List<Roles> rol) {
        this.rol = rol;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

package org.example.siecapi.models.contratos;

import jakarta.persistence.*;
import org.example.siecapi.models.cliente.Cliente;
import org.example.siecapi.models.usuarios.Usuarios;

import java.time.LocalDate;

@Entity
@Table(name = "contrato")
public class Contratos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "cuentaMT5", nullable = false)
    private String cuentaMT5;
    @Column(name = "monto", nullable = false)
    private Double monto;
    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fecha_inicio;
    @Column(name = "fecha_renovacion", nullable = false)
    private LocalDate fecha_renovacion;
    @Column(name = "estatus_renovacion", nullable = false)
    private boolean estatus_renovacion;
    @ManyToOne
    @JoinColumn(name = "cliente")
    private Cliente cliente;
    @ManyToOne
    @JoinColumn(name = "asesor")
    private Usuarios usuario;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public boolean isEstatus_renovacion() {
        return estatus_renovacion;
    }

    public void setEstatus_renovacion(boolean estatus_renovacion) {
        this.estatus_renovacion = estatus_renovacion;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }
}

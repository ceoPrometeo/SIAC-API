package org.example.siecapi.models.contratos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @Column(name = "cuentaMT5", nullable = true)
    private String cuentaMT5;
    @Column(name = "monto", nullable = true)
    private Double monto;
    @Column(name = "fecha_inicio", nullable = true)
    private LocalDate fecha_inicio;
    @Column(name = "fecha_renovacion", nullable = true)
    private LocalDate fechaRenovacion;
    //renovado,pendiente y vencidos.
    @Column(name = "estatus_renovacion", nullable = true)
    private String estatusRenovacion;
    //agresivo, moderado, conservador y liquidity
    @Column(name = "tipo_contrato" , nullable = true, length = 20)
    private String tipoContrato;
    @ManyToOne
    @JoinColumn(name = "cliente")
    @JsonIgnore
    private Cliente cliente;
    @ManyToOne
    @JoinColumn(name = "asesor")
    @JsonIgnore
    private Usuarios usuario;

    public String getTipoContrato() {
        return tipoContrato;
    }

    public void setTipoContrato(String tipoContrato) {
        this.tipoContrato = tipoContrato;
    }

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

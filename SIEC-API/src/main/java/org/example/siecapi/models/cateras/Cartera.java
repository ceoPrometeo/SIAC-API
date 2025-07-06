package org.example.siecapi.models.cateras;

import jakarta.persistence.*;
import org.example.siecapi.models.cliente.Cliente;
import org.example.siecapi.models.usuarios.Usuarios;

@Entity
@Table(name ="Cartera")
public class Cartera {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JoinColumn(name = "numero_de_Clientes", nullable = false)
    private Integer numero_de_Clientes;
    @JoinColumn(name = "monto", nullable = false)
    private Double monto;
    @ManyToOne
    @JoinColumn(name = "cliente", nullable = false)
    private Cliente cliente;
    @ManyToOne
    @JoinColumn(name="usuario")
    private Usuarios usuario;


    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumero_de_Clientes() {
        return numero_de_Clientes;
    }

    public void setNumero_de_Clientes(Integer numero_de_Clientes) {
        this.numero_de_Clientes = numero_de_Clientes;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}

package org.example.siecapi.models.cliente;

import jakarta.persistence.*;
import org.example.siecapi.models.cateras.Cartera;
import org.example.siecapi.models.contratos.Contratos;
import org.example.siecapi.models.usuarios.Roles.Roles;
import org.example.siecapi.models.usuarios.Usuarios;

import java.util.List;

@Entity
@Table(name = "Cliente")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "Nombre", nullable = true, length = 100)
    private String nombre;
    @Column(name = "Correo", nullable = true, length = 40)
    private String correo;
    @Column(name = "Telefono", nullable = true, length = 10)
    private String telefono;
    @Column(name = "Estado", nullable = true)
    private boolean estado;
    @ManyToOne
    @JoinColumn(name = "asesor")
    private Usuarios asesor;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "contratos_cliente", // tabla intermedia
            joinColumns = @JoinColumn(name = "cliente_id"),
            inverseJoinColumns = @JoinColumn(name = "contrato_id")
    )
    private List<Contratos> contratos;

    @ManyToOne
    @JoinColumn(name = "cartera")
    private Cartera cartera;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "Usuarios_Roles", // tabla intermedia
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "rol_id")
    )
    private List<Roles> rol;

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

    public Usuarios getAsesor() {
        return asesor;
    }

    public void setAsesor(Usuarios asesor) {
        this.asesor = asesor;
    }

    public List<Contratos> getContratos() {
        return contratos;
    }

    public void setContratos(List<Contratos> contratos) {
        this.contratos = contratos;
    }

    public Cartera getCartera() {
        return cartera;
    }

    public void setCartera(Cartera cartera) {
        this.cartera = cartera;
    }

    public List<Roles> getRol() {
        return rol;
    }

    public void setRol(List<Roles> rol) {
        this.rol = rol;
    }
}

package org.example.siecapi.models.historial;

import jakarta.persistence.*;
import org.example.siecapi.models.cliente.Cliente;
import org.example.siecapi.models.contratos.Contratos;

import java.time.LocalDate;

@Entity
@Table(name = "Historial_de_Incremento")
public class Historial_de_Incrementos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer monto;
    private LocalDate fecha;
    @ManyToOne
    @JoinColumn(name = "contrato")
    private Contratos contrato;
    @ManyToOne
    @JoinColumn(name = "cliente")
    private Cliente cliente;

}

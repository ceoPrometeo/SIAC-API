package org.example.siecapi.models.cateras;

import jakarta.persistence.*;

@Entity
@Table(name ="Cartera")
public class Cartera {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
}

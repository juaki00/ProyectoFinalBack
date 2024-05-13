package com.jrs.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "cliente")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "nombre")
    String nombre;
    @Column(name = "total")
    Double total;
    @Column(name = "estado")
    String estado;

}

package com.jrs.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "partida_guardada")
public class Partida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonIgnore
    private Long id;
    @Column(name = "puntuacion")
    private int puntuacion;
    @Column(name = "fecha")
    private LocalDateTime fecha;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "nombre_jugador")
    private Usuario jugador;
}

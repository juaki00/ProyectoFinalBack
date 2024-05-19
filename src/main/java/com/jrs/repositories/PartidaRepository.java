package com.jrs.repositories;

import com.jrs.models.Partida;
import com.jrs.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartidaRepository extends JpaRepository<Partida, Integer> {

    @Query("SELECT p FROM Partida p ORDER BY p.puntuacion DESC")
    List<Partida> getRankingPartidas();

    @Query("SELECT p FROM Partida p WHERE p.jugador.nombre = :nombre ORDER BY p.fecha DESC")
    List<Partida> getPartidasDeUsuario(String nombre);
}

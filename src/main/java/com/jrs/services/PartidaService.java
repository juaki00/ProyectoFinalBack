package com.jrs.services;

import com.jrs.bean.ExisteUsuarioResponse;
import com.jrs.bean.LoginResponse;
import com.jrs.bean.RankingResponse;
import com.jrs.models.Partida;
import com.jrs.models.Usuario;
import com.jrs.repositories.PartidaRepository;
import com.jrs.repositories.UsuarioRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
public class PartidaService {

    @Autowired
    PartidaRepository partidaRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    SecurityService securityService;

    public void insertPartida( String nombre , Integer puntuacion, String token ) throws BadRequestException {
        if(securityService.validateToken( token )){
            Optional<Usuario> usuarioOpt = usuarioRepository.findById(nombre);
            if(usuarioOpt.isPresent()){
                Usuario usuario = usuarioOpt.get();
                Partida partida = new Partida();
                partida.setPuntuacion(puntuacion);
                partida.setJugador(usuario);
                partida.setFecha(LocalDateTime.now());
                partidaRepository.save( partida );
            }else{
                throw new RuntimeException( "Error. El usuario no existe" );
            }
        }
        else{
            throw new RuntimeException( "Error. No autorizado, token no valido" );
        }
    }

    public RankingResponse ranking(String token ) {
        RankingResponse rankingResponse = new RankingResponse();
        List<Partida> partidas = new ArrayList<>();
        if(securityService.validateToken( token )){
            partidas = partidaRepository.getRankingPartidas();
        }
        else{
            throw new RuntimeException( "No autorizado, token no valido" );
        }
        rankingResponse.setPartidas(partidas);
        return rankingResponse;
    }

    public RankingResponse partidasDeUsuario(String nombre, String token) {
        RankingResponse rankingResponse = new RankingResponse();
        List<Partida> partidas = new ArrayList<>();
        if(securityService.validateToken( token )){
            partidas = partidaRepository.getPartidasDeUsuario(nombre);
        }
        else{
            throw new RuntimeException( "No autorizado, token no valido" );
        }
        rankingResponse.setPartidas(partidas);
        return rankingResponse;
    }
}
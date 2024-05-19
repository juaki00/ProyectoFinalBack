package com.jrs.web;

import com.jrs.bean.ExisteUsuarioResponse;
import com.jrs.bean.LoginResponse;
import com.jrs.bean.RankingResponse;
import com.jrs.models.Usuario;
import com.jrs.services.PartidaService;
import com.jrs.services.SecurityService;
import com.jrs.services.UsuarioSerive;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/partidas")
public class PartidaController {

    @Autowired
    private PartidaService partidaService;

    @Operation(description = "Insertar partida",
            summary = "Insertar una nueva partida", responses = {
            @ApiResponse(description = "Datos obtenidos correctamente", responseCode = "200") ,
            @ApiResponse(description = "Error en el cliente", responseCode = "400") ,
            @ApiResponse(description = "Error en el servidor", responseCode = "500")
    })
    @PostMapping("insertPartida")
    public void insertPartida(
            @Parameter(in = ParameterIn.QUERY, description = "Nombre del usuario que ha jugado la partdia",
                    example = "Juan", required = true)
            @RequestParam(name = "nombre")
            String nombre ,
            @Parameter(in = ParameterIn.QUERY, description = "Puntuacion adquirida en la partida",
                    example = "60", required = true)
            @RequestParam(name = "puntuacion")
            Integer contrasena,
            @RequestParam String token,
            HttpServletResponse response
            ) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        try {
            partidaService.insertPartida( nombre, contrasena, token );
        } catch (BadRequestException e) {
            try {
                response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    @Operation(description = "Devuelve un ranking de partidas",
            summary = "Devuelve una lista con todas las partidas ordenadas por puntos", responses = {
            @ApiResponse(description = "Datos obtenidos correctamente", responseCode = "200") ,
            @ApiResponse(description = "Error en el cliente", responseCode = "400") ,
            @ApiResponse(description = "Error en el servidor", responseCode = "500")
    })
    @GetMapping("ranking")
    public RankingResponse ranking(
            @RequestParam String token,
            HttpServletResponse response ) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        return partidaService.ranking( token );
    }

    @Operation(description = "Devuelve un ranking de partidas",
            summary = "Devuelve una lista con todas las partidas ordenadas por puntos", responses = {
            @ApiResponse(description = "Datos obtenidos correctamente", responseCode = "200") ,
            @ApiResponse(description = "Error en el cliente", responseCode = "400") ,
            @ApiResponse(description = "Error en el servidor", responseCode = "500")
    })
    @GetMapping("deUsuario")
    public RankingResponse partidasDeUsuario(
            @Parameter(in = ParameterIn.QUERY, description = "Nombre del usuario",
                    example = "Juan", required = true)
            @RequestParam(name = "nombre")
            String nombre ,
            @RequestParam String token,
            HttpServletResponse response ) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        return partidaService.partidasDeUsuario(nombre, token );
    }

}

package com.jrs.web;

import com.jrs.bean.ExisteUsuarioResponse;
import com.jrs.bean.LoginResponse;
import com.jrs.models.Usuario;
import com.jrs.services.UsuarioSerive;
import com.jrs.services.SecurityService;
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
@RequestMapping("/clientes")
public class UsuarioController {

    @Autowired
    private UsuarioSerive usuarioSerive;

    @Autowired
    SecurityService securityService;

    @Operation(description = "Todos los clientes",
            summary = "Obtener todos los clientes", responses = {
            @ApiResponse(description = "Datos obtenidos correctamente", responseCode = "200") ,
            @ApiResponse(description = "Error en el cliente", responseCode = "400") ,
            @ApiResponse(description = "Error en el servidor", responseCode = "500")
    })
    @GetMapping("getAll")
    public List<Usuario> getAll(@RequestParam String token,
                                HttpServletResponse response ) {
        response.setHeader("Access-Control-Allow-Origin", "*");
            return usuarioSerive.getAll( token );
    }
    @CrossOrigin(origins = "http://localhost:4200")
    @Operation(description = "Insertar un nuevo usuario",
            summary = "Insertar un nuevo usuario", responses = {
            @ApiResponse(description = "Todo ok", responseCode = "200")
    })
    @PostMapping("insertCliente")
    public void insertCliente(
            @Parameter(in = ParameterIn.QUERY, description = "Nombre del nuevo usuario",
                    example = "Juan", required = true)
            @RequestParam(name = "nombre")
            String nombre ,
            @Parameter(in = ParameterIn.QUERY, description = "contraseña del nuevo usuario",
                    example = "123", required = true)
            @RequestParam(name = "contrasena")
            String contrasena ,
            @Parameter(in = ParameterIn.QUERY, description = "email del nuevo usuario",
                    example = "email@eail.com", required = true)
            @RequestParam(name = "email")
            String email ,
            @RequestParam String token,
            HttpServletResponse response
            ) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        try {
            usuarioSerive.insertUsuario( nombre, contrasena, email, token );
        } catch (BadRequestException e) {
            try {
                response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    @Operation(description = "Devuelve si existe el usuario o no",
            summary = "Devuelve si existe el usuario o no", responses = {
            @ApiResponse(description = "Datos obtenidos correctamente", responseCode = "200") ,
            @ApiResponse(description = "Error en el cliente", responseCode = "400") ,
            @ApiResponse(description = "Error en el servidor", responseCode = "500")
    })
    @GetMapping("existeUsuario")
    public ExisteUsuarioResponse getClienteById(
            @Parameter(in = ParameterIn.QUERY, description = "Nombre del usuario",
                    example = "joaquin", required = true)
            @RequestParam(name = "nombre")
            String nombre ,
            @RequestParam String token,
            HttpServletResponse response ) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        return usuarioSerive.existeUsuario( nombre, token );
    }

    @Operation(description = "Obtener clientes activos mejores a una cantidad",
            summary = "Obtener clientes activos mejores a una cantidad", responses = {
            @ApiResponse(description = "Datos obtenidos correctamente", responseCode = "200") ,
            @ApiResponse(description = "Error en el cliente", responseCode = "400") ,
            @ApiResponse(description = "Error en el servidor", responseCode = "500")
    })
    @GetMapping("login")
    public LoginResponse login(
            @Parameter(in = ParameterIn.QUERY, description = "Nombre del usuario",
                    example = "200", required = true)
            @RequestParam(name = "nombre")
            String nombre,
            @Parameter(in = ParameterIn.QUERY, description = "contrasena del usuario ",
                    example = "200", required = true)
            @RequestParam(name = "contrasena")
            String contrasena,
            @RequestParam String token,
            HttpServletResponse response ) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        return usuarioSerive.login( nombre, contrasena, token );
    }

}

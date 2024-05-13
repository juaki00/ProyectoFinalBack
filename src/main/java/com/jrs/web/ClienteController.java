package com.jrs.web;

import com.jrs.models.Cliente;
import com.jrs.models.EstadisticasResponse;
import com.jrs.services.ClienteSerive;
import com.jrs.services.SecurityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteSerive clienteSerive;

    @Autowired
    SecurityService securityService;

    @Operation(description = "Todos los clientes",
            summary = "Obtener todos los clientes", responses = {
            @ApiResponse(description = "Datos obtenidos correctamente", responseCode = "200") ,
            @ApiResponse(description = "Error en el cliente", responseCode = "400") ,
            @ApiResponse(description = "Error en el servidor", responseCode = "500")
    })
    @GetMapping("getAll")
    public List<Cliente> getAll( @RequestParam String token ) {

            return clienteSerive.getAll( token );
    }

    @Operation(description = "Insertar un nuevo tipo de ocupacion. Introducir mes, tipo y cantidad",
            summary = "Insertar un nuevo tipo de ocupacion", responses = {
            @ApiResponse(description = "Todo ok", responseCode = "200")
    })
    @PostMapping("insertCliente")
    public void insertCliente(
            @Parameter(in = ParameterIn.QUERY, description = "Nombre del nuevo cliente",
                    example = "Juan", required = true)
            @RequestParam(name = "nombre")
            String nombre ,
            @Parameter(in = ParameterIn.QUERY, description = "Valor total",
                    example = "100", required = true)
            @RequestParam(name = "total")
            Double total ,
            @Parameter(in = ParameterIn.QUERY, description = "Estado del cliente, activo o inactivo",
                    example = "activo", required = true)
            @Valid @Pattern(
                    regexp = "^(activo|inactivo)$",
                    message = "Debe introducir Activo o Inactivo")
            @RequestParam(name = "estado")
            String estado ,
            @RequestParam String token
            ) {
        clienteSerive.insertOcupacion( nombre, total, estado, token );
    }

    @Operation(description = "Obtener cliente por su id",
            summary = "Obtener el cliente que corresponda con el id", responses = {
            @ApiResponse(description = "Datos obtenidos correctamente", responseCode = "200") ,
            @ApiResponse(description = "Error en el cliente", responseCode = "400") ,
            @ApiResponse(description = "Error en el servidor", responseCode = "500")
    })
    @GetMapping("getClienteById")
    public Cliente getClienteById(
            @Parameter(in = ParameterIn.QUERY, description = "Id del cliente a buscar",
                    example = "1", required = true)
            @RequestParam(name = "id")
            Integer id ,
            @RequestParam String token ) {

        return clienteSerive.getClienteById( id, token );
    }

    @Operation(description = "Obtener clientes activos mejores a una cantidad",
            summary = "Obtener clientes activos mejores a una cantidad", responses = {
            @ApiResponse(description = "Datos obtenidos correctamente", responseCode = "200") ,
            @ApiResponse(description = "Error en el cliente", responseCode = "400") ,
            @ApiResponse(description = "Error en el servidor", responseCode = "500")
    })
    @GetMapping("getMejores")
    public List<Cliente> getMejores(
            @Parameter(in = ParameterIn.QUERY, description = "Cantidad minima",
                    example = "200", required = true)
            @RequestParam(name = "cantidad")
            Integer cantidad ,
            @RequestParam String token ) {

        return clienteSerive.getMejores( cantidad, token );
    }


    @Operation(description = "Obtener estadisticas de los clientes",
            summary = "Obtener estadisticas de los clientes", responses = {
            @ApiResponse(description = "Datos obtenidos correctamente", responseCode = "200") ,
            @ApiResponse(description = "Error en el cliente", responseCode = "400") ,
            @ApiResponse(description = "Error en el servidor", responseCode = "500")
    })
    @GetMapping("getEstadidsticas")
    public EstadisticasResponse getEstadidsticas(
            @RequestParam String token ) {

        return clienteSerive.getEstadidsticas( token );
    }





}

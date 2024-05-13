package com.jrs.services;

import com.jrs.extra.AutenticacionException;
import com.jrs.extra.NotFoundException;
import com.jrs.models.Cliente;
import com.jrs.models.EstadisticasResponse;
import com.jrs.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class ClienteSerive {

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    SecurityService securityService;

    public List<Cliente> getAll( String token ) {
        if(securityService.validateToken( token )) {
            return clienteRepository.findAll( );
        }
        else{
            throw new AutenticacionException( "No autorizado, token no valido" );
        }
    }

    public void insertOcupacion( String nombre , Double total , String estado , String token ) {
        if(securityService.validateToken( token )){
            Cliente cliente = new Cliente();
            cliente.setNombre( nombre );
            cliente.setTotal( total );
            cliente.setEstado( estado );
            clienteRepository.save( cliente );
        }
        else{
            throw new AutenticacionException( "Token incorrecto" );
        }
    }

    public Cliente getClienteById( Integer id , String token ) {
        Cliente cliente;
        if(securityService.validateToken( token )){
            if( clienteRepository.findById( id ).isPresent()){
                return clienteRepository.findById( id ).get();
            }
            else{
                throw new NotFoundException( "No existe ningun cliente con id "+id );
            }
        }
        else{
            throw new AutenticacionException( "Token incorrecto" );
        }
    }

    public List<Cliente> getMejores( Integer cantidad , String token ) {
        if(securityService.validateToken( token )){
            List<Cliente> clientes = clienteRepository.findAll( );
            if( clientes.size()==0){
                throw new NotFoundException( "No existe ningun cliente activo con cantidad mayor a " + cantidad );

            }
            else{
                List<Cliente> clientesMejores = new ArrayList<>(  );
                for(Cliente c: clientes){
                    if(c.getTotal()>cantidad && c.getEstado().equals( "activo" )){
                        clientesMejores.add( c );
                    }
                }
                return clientesMejores;
            }
        }
        else{
            throw new AutenticacionException( "Token incorrecto" );
        }
    }

    public EstadisticasResponse getEstadidsticas( String token ) {
        EstadisticasResponse response = new EstadisticasResponse();
        List<Cliente> clientes = clienteRepository.findAll();
        Integer inactivos = 0;
        Double total = 0.0;
        Double promedio = 0.0;
        Integer activos = 0;

        for(Cliente c: clientes){
            total += c.getTotal();
            if(c.getEstado().equals( "activo" )){
                activos++;
                promedio = promedio + c.getTotal();
            }
            else{
                if(c.getTotal()>0){
                    inactivos++;
                }
            }
            promedio = promedio / activos;
        }
        response.setInactivos( inactivos );
        response.setPromedio( promedio );
        response.setTotal( total );
        return response;
    }


}

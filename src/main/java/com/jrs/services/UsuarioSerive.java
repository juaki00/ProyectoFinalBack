package com.jrs.services;

import com.jrs.bean.ExisteUsuarioResponse;
import com.jrs.bean.LoginResponse;
import com.jrs.models.Usuario;
import com.jrs.repositories.UsuarioRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class UsuarioSerive {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    SecurityService securityService;

    public List<Usuario> getAll(String token ) {
        if(securityService.validateToken( token )) {
            return usuarioRepository.findAll( );
        }
        else{
            throw new RuntimeException( "No autorizado, token no valido" );
        }
    }

    public void insertUsuario( String nombre , String contrasena, String email, String token ) throws BadRequestException {
        if(existeUsuario(nombre,token).isExiste()){
            throw new BadRequestException( "Error, el usuario ya existe" );
        }
        else if(securityService.validateToken( token )){
            Usuario usuario = new Usuario();
            usuario.setNombre( nombre );
            usuario.setContrasena( contrasena );
            usuario.setEmail(email);
            usuario.setListaPartidas(new ArrayList<>());
            usuarioRepository.save( usuario );
        }
        else{
            throw new RuntimeException( "No autorizado, token no valido" );
        }
    }

    public ExisteUsuarioResponse existeUsuario(String nombre , String token ) {
        ExisteUsuarioResponse existeUsuarioResponse;
        if(securityService.validateToken( token )) {
            existeUsuarioResponse = new ExisteUsuarioResponse();
            existeUsuarioResponse.setExiste(usuarioRepository.findById(nombre).isPresent());
        }
        else{
            throw new RuntimeException( "No autorizado, token no valido" );
        }
       return existeUsuarioResponse;
    }

    public LoginResponse login(String nombre, String contrasena, String token ) {
        boolean logeado = false;
        LoginResponse loginResponse = new LoginResponse();
        if(securityService.validateToken( token )){
            Optional<Usuario> usuario = usuarioRepository.findById(nombre);
            if(usuario.isPresent()){
                if(usuario.get().getContrasena().equals(contrasena)){
                    logeado = true;
                }
            }
            loginResponse.setExiste(logeado);
        }
        else{
            throw new RuntimeException( "No autorizado, token no valido" );
        }
        return loginResponse;
    }
}

package com.jrs.services;

import org.springframework.stereotype.Service;

@Service
public class SecurityService {

    public Boolean validateToken(String token){
        return (token.equals("1234"));
    }

}

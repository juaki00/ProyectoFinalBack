package com.jrs.repositories;

import com.jrs.models.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ClienteRepository extends JpaRepository<Cliente, Integer> {



}

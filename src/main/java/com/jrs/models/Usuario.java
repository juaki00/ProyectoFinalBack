package com.jrs.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.Objects;

@Data
@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "email")
    private String email;
    @Column(name = "contrasena")
    @JsonIgnore
    private String contrasena;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "jugador", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Partida> ListaPartidas;

    @Override
    public String toString() {
        return "Usuario{" +
                ", nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                ", NumeroPartidas=" + ListaPartidas.size() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(nombre, usuario.nombre) && Objects.equals(email, usuario.email) && Objects.equals(ListaPartidas.size(), usuario.ListaPartidas.size());
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, email, ListaPartidas.size());
    }


}

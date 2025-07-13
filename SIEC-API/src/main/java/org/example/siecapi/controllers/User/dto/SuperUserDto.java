package org.example.siecapi.controllers.User.dto;

import org.example.siecapi.models.usuarios.Roles.Roles;

import java.util.ArrayList;
import java.util.List;

public class SuperUserDto {

    private String correo;
    private String password;

    private List<Roles> roles = new ArrayList<>();

    public String getCorreo() {
        return correo;
    }

    public String getPassword() {
        return password;
    }

    public List<Roles> getRoles() {
        return roles;
    }
}

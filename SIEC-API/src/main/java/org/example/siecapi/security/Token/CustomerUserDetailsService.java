package org.example.siecapi.security.Token;

import org.example.siecapi.models.usuarios.Roles.Roles;
import org.example.siecapi.models.usuarios.Usuarios;
import org.example.siecapi.models.usuarios.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuariosRepository userRepository;

    public CustomerUserDetailsService(UsuariosRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Metodo para asginar roles
    public Collection<GrantedAuthority> mapToAuthorities(List<Roles> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getRol())).collect(Collectors.toList());
    }

    // Autenticación basada en correo electrónico
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuarios usuario = userRepository.findByCorreo(email)
                .orElseThrow(() -> new UsernameNotFoundException(email + "No existe la sesion"));

        return new User(usuario.getCorreo(), usuario.getPassword(), mapToAuthorities((List<Roles>) usuario.getRol()));
    }
}

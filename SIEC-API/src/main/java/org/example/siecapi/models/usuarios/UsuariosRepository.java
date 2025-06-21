package org.example.siecapi.models.usuarios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public interface UsuariosRepository extends JpaRepository<Usuarios,Long> {



    Optional<Usuarios> findByCorreo(String correo);
}

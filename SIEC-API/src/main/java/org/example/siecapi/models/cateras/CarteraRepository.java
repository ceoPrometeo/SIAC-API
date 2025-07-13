package org.example.siecapi.models.cateras;

import org.example.siecapi.models.usuarios.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarteraRepository  extends JpaRepository<Cartera, Long> {
    List<Cartera> findByUsuario(Usuarios usuario);
}

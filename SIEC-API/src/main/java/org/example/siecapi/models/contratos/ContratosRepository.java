package org.example.siecapi.models.contratos;

import org.example.siecapi.models.cateras.Cartera;
import org.example.siecapi.models.usuarios.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContratosRepository extends JpaRepository<Contratos,Long> {

    List<Contratos> findByEstatusRenovacion(String estatus);

    List<Contratos> findByTipoContrato(String tipoContrato);

}


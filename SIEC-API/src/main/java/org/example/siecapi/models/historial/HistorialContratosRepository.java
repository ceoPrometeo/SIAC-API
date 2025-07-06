package org.example.siecapi.models.historial;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistorialContratosRepository extends JpaRepository<HistorialContratos, Long> {

}

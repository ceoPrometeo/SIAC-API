package org.example.siecapi.models.historial;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Historial_de_IncrementosRepository extends JpaRepository<Historial_de_Incrementos, Long> {

}

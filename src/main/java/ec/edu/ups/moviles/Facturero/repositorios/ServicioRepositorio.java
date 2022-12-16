package ec.edu.ups.moviles.Facturero.repositorios;


import ec.edu.ups.moviles.Facturero.entidades.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface ServicioRepositorio extends JpaRepository<Servicio, Long> {

    @Query(value = "SELECT s FROM Servicio s WHERE s.usuario.id = ?1")
    Optional<List<Servicio>> findServiciosByUsuarioId(Long usuarioId);

}

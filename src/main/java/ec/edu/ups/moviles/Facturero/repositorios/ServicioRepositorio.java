package ec.edu.ups.moviles.Facturero.repositorios;


import ec.edu.ups.moviles.Facturero.entidades.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface ServicioRepositorio extends JpaRepository<Servicio, Long> {
}

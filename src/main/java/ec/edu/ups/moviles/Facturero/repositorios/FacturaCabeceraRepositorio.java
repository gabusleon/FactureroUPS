package ec.edu.ups.moviles.Facturero.repositorios;

import ec.edu.ups.moviles.Facturero.entidades.FacturaCabecera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface FacturaCabeceraRepositorio extends JpaRepository<FacturaCabecera, Long> {
}

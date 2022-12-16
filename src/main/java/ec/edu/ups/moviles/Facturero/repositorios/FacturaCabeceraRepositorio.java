package ec.edu.ups.moviles.Facturero.repositorios;

import ec.edu.ups.moviles.Facturero.entidades.FacturaCabecera;
import ec.edu.ups.moviles.Facturero.entidades.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface FacturaCabeceraRepositorio extends JpaRepository<FacturaCabecera, Long> {

    @Query(value = "SELECT f FROM FacturaCabecera f WHERE f.usuario.id = ?1 AND f.estadoFactura = 'EMITIDA'")
    Optional<List<FacturaCabecera>> findFacturasCabeceraByUsuarioIdAndEstadoEmitido(Long usuarioId);

    @Query(value = "SELECT f FROM FacturaCabecera f WHERE f.usuario.id = ?1 AND f.estadoFactura = 'ANULADA'")
    Optional<List<FacturaCabecera>> findFacturasCabeceraByUsuarioIdAndEstadoAnulado(Long usuarioId);

    @Query(value = "SELECT f FROM FacturaCabecera f WHERE f.usuario.id = ?1")
    Optional<List<FacturaCabecera>> findFacturasCabeceraByUsuarioId(Long usuarioId);
}

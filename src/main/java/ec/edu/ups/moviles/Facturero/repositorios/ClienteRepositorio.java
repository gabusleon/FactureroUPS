package ec.edu.ups.moviles.Facturero.repositorios;

import ec.edu.ups.moviles.Facturero.entidades.Cliente;
import ec.edu.ups.moviles.Facturero.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface ClienteRepositorio extends JpaRepository<Cliente, Long> {

    @Query(value = "SELECT c FROM Cliente c WHERE c.identificacionNumero = ?1")
    Optional<Cliente> findClienteByIdentificacionNumero(String identificacionNumero);
}

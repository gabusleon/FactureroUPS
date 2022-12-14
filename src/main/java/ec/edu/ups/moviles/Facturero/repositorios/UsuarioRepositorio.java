package ec.edu.ups.moviles.Facturero.repositorios;

import ec.edu.ups.moviles.Facturero.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Repository
@Transactional
public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {

    @Query(value = "SELECT u FROM Usuario u WHERE u.username = ?1 AND u.password = ?2")
    Optional<Usuario> findUsuarioByUsernameAndPassword(String username, String password);

}

package ec.edu.ups.moviles.Facturero.controladores;

import ec.edu.ups.moviles.Facturero.entidades.Usuario;
import ec.edu.ups.moviles.Facturero.entidades.peticiones.usuario.CrearUsuario;
import ec.edu.ups.moviles.Facturero.entidades.peticiones.usuario.LoginUsuario;
import ec.edu.ups.moviles.Facturero.repositorios.UsuarioRepositorio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/usuario")
public class UsuarioControlador {

    private static final Logger log = LoggerFactory.getLogger(UsuarioControlador.class);

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @PostMapping("/create")
    public ResponseEntity<?> crearUsuario(@RequestBody CrearUsuario crearUsuarioRequest) {
        try {
            Usuario usuario = new Usuario();
            if (crearUsuarioRequest.getPassword().length() < 7) {
                String mensaje = "La contraseña debe contener al menos 7 caracteres";
                return ResponseEntity.badRequest().body(mensaje);
            }
            if (!crearUsuarioRequest.getPassword().equals(crearUsuarioRequest.getConfirmPassword())) {
                String mensaje = "Las contraseñas ingresadas no coinciden";
                return ResponseEntity.badRequest().body(mensaje);
            }
            usuario.setUsername(crearUsuarioRequest.getUsername());
            usuario.setPassword(crearUsuarioRequest.getPassword());
            usuarioRepositorio.save(usuario);
            return ResponseEntity.ok(usuario);
        } catch (DataIntegrityViolationException ex1) {
            log.error("No se puede crear usuario: '{}'. Error de SQL: '{}'", crearUsuarioRequest.getUsername(), ex1.getMessage());
            return ResponseEntity.badRequest().body("El usuario ingresado ya existe. Por favor, inicie sesión o recupere la contraseña.");
        } catch (Exception ex2) {
            log.error("No se puede crear usuario: '{}'. Error: '{}'", crearUsuarioRequest.getUsername(), ex2.getMessage());
            return ResponseEntity.badRequest().body("Ha ocurrido un error inesperado. Por favor, inténtelo nuevamente.");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> iniciarSesionUsuario(@RequestBody LoginUsuario loginUsuarioRequest) {
        try {
            Optional<Usuario> userOptional = usuarioRepositorio.findUsuarioByUsernameAndPassword(loginUsuarioRequest.getUsername(), loginUsuarioRequest.getPassword());
            if(userOptional.isEmpty()){
                return ResponseEntity.badRequest().body("Las credenciales ingresadas son incorrectas. Por favor, inténtelo nuevamente.");
            }
            return ResponseEntity.ok(userOptional.get());
        } catch (Exception ex) {
            log.error("No se puede validar el usuario: '{}'. Error: '{}'", loginUsuarioRequest.getUsername(), ex.getMessage());
            return ResponseEntity.badRequest().body("Ha ocurrido un error al iniciar sesión. Por favor, inténtelo nuevamente.");
        }
    }

}

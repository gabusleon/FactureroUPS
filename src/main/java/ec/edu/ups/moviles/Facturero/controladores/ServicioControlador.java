package ec.edu.ups.moviles.Facturero.controladores;

import ec.edu.ups.moviles.Facturero.entidades.Cliente;
import ec.edu.ups.moviles.Facturero.entidades.Servicio;
import ec.edu.ups.moviles.Facturero.entidades.Usuario;
import ec.edu.ups.moviles.Facturero.entidades.peticiones.servicio.CrearServicio;
import ec.edu.ups.moviles.Facturero.entidades.peticiones.servicio.EditarServicio;
import ec.edu.ups.moviles.Facturero.repositorios.ServicioRepositorio;
import ec.edu.ups.moviles.Facturero.repositorios.UsuarioRepositorio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/servicio")
public class ServicioControlador {
    private static final Logger log = LoggerFactory.getLogger(ServicioControlador.class);

    @Autowired
    private ServicioRepositorio servicioRepositorio;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @PostMapping("/create")
    public ResponseEntity<?> crearServicioUsuario(@RequestBody CrearServicio crearServicioRequest){
        try{
            Optional<Usuario> usuarioOptional = usuarioRepositorio.findById(crearServicioRequest.getUsuarioId());
            if (usuarioOptional.isEmpty()) {
                String mensaje = "El usuario con id " + crearServicioRequest.getUsuarioId() +" no existe";
                log.info(mensaje);
                return ResponseEntity.badRequest().body(mensaje);
            }
            Servicio servicio = new Servicio();
            servicio.setDescripcion(crearServicioRequest.getDescripcion());
            servicio.setPrecioUnitario(crearServicioRequest.getPrecioUnitario());
            servicio.setUsuario(usuarioOptional.get());

            servicioRepositorio.save(servicio);
            return ResponseEntity.ok(servicio);

        }catch (Exception ex){
            log.error("No se puede crear el servicio '{}' del usuario '{}'. Error: {}", crearServicioRequest.getDescripcion(), crearServicioRequest.getUsuarioId(), ex.getMessage());
            return ResponseEntity.badRequest().body("Ha ocurrido un error inesperado. Por favor, inténtelo nuevamente.");
        }
    }

    @PostMapping("/update")
    public ResponseEntity<?> actualizarServicioUsuario(@RequestBody EditarServicio editarServicioRequest){
        try{
            Optional<Servicio> servicioOptional = servicioRepositorio.findById(editarServicioRequest.getId());
            if (servicioOptional.isEmpty()) {
                String mensaje = "El servicio con id " + editarServicioRequest.getId() +" no existe";
                log.info(mensaje);
                return ResponseEntity.badRequest().body(mensaje);
            }
            Servicio servicio = servicioOptional.get();
            servicio.setDescripcion(editarServicioRequest.getDescripcion());
            servicio.setPrecioUnitario(editarServicioRequest.getPrecioUnitario());

            servicioRepositorio.save(servicio);
            return ResponseEntity.ok(servicio);

        }catch (Exception ex){
            log.error("No se puede actualizar el servicio '{}' del usuario. Error: '{}'", editarServicioRequest.getId(), ex.getMessage());
            return ResponseEntity.badRequest().body("Ha ocurrido un error inesperado. Por favor, inténtelo nuevamente.");
        }
    }

    @GetMapping("/delete/{servicioId}")
    public ResponseEntity<?> eliminarServicioUsuario(@PathVariable("servicioId") Long servicioId){
        try{
            Optional<Servicio> servicioOptional = servicioRepositorio.findById(servicioId);
            if(servicioOptional.isEmpty()){
                String mensaje = "El servicio con id " + servicioId +" no existe";
                log.info(mensaje);
                return ResponseEntity.badRequest().body(mensaje);
            }

            servicioRepositorio.deleteById(servicioId);
            return ResponseEntity.ok().build();

        }catch (Exception ex){
            log.error("No se puede eliminar el servicio con id: '{}'. Error: {}", servicioId, ex.getMessage());
            return ResponseEntity.badRequest().body("Ha ocurrido un error inesperado. Por favor, inténtelo nuevamente.");
        }
    }

    @GetMapping("/findAll/{usuarioId}")
    public ResponseEntity<?> obtenerServiciosPorUsuario(@PathVariable("usuarioId") Long usuarioId){
        try{
            return ResponseEntity.ok(servicioRepositorio.findServiciosByUsuarioId(usuarioId));
        }catch (Exception ex){
            log.error("No se puede listar todos los servicios del usuario '{}'. Error: {}", usuarioId, ex.getMessage());
            return ResponseEntity.badRequest().body("Ha ocurrido un error inesperado. Por favor, inténtelo nuevamente.");
        }
    }

}



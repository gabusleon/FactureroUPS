package ec.edu.ups.moviles.Facturero.controladores;

import ec.edu.ups.moviles.Facturero.entidades.Cliente;
import ec.edu.ups.moviles.Facturero.entidades.peticiones.cliente.CrearCliente;
import ec.edu.ups.moviles.Facturero.entidades.peticiones.cliente.EditarCliente;
import ec.edu.ups.moviles.Facturero.entidades.tipos.TipoIdentificacion;
import ec.edu.ups.moviles.Facturero.repositorios.ClienteRepositorio;
import ec.edu.ups.moviles.Facturero.validaciones.ValidacionCorreoElectronico;
import ec.edu.ups.moviles.Facturero.validaciones.ValidacionIdentificacion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cliente")
public class ClienteControlador {

    private static final Logger log = LoggerFactory.getLogger(ClienteControlador.class);
    /*
    TODO: metodo para eliminar, listar y buscar por cedula datos de cliente
     */

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @PostMapping("/create")
    public ResponseEntity<?> crearCliente(@RequestBody CrearCliente crearClienteRequest){
        try{
            Cliente cliente = new Cliente();

            //validar correo electronico
            if(validarCorreoElectronico(crearClienteRequest.getCorreoElectronico())) {
                cliente.setCorreoElectronico(crearClienteRequest.getCorreoElectronico());
            }else{
                String mensaje = "El correo electrónico '"+ crearClienteRequest.getCorreoElectronico() + "' no es valido";
                log.info(mensaje);
                return ResponseEntity.badRequest().body(mensaje);
            }

            //validar identificacion
            if(crearClienteRequest.getTipoIdentificacion().equals("CEDULA")){
                cliente.setTipoIdentificacion(TipoIdentificacion.CEDULA);
                if(validarCedula(crearClienteRequest.getIdentificacionNumero())) {
                    cliente.setIdentificacionNumero(crearClienteRequest.getIdentificacionNumero());
                }else {
                    String mensaje = "La cedula '"+ crearClienteRequest.getIdentificacionNumero() + "' no es valida";
                    log.info(mensaje);
                    return ResponseEntity.badRequest().body(mensaje);
                }
            }else if(crearClienteRequest.getTipoIdentificacion().equals("RUC")){
                cliente.setTipoIdentificacion(TipoIdentificacion.RUC);
                if(validarRuc(crearClienteRequest.getIdentificacionNumero())) {
                    cliente.setIdentificacionNumero(crearClienteRequest.getIdentificacionNumero());
                }else{
                    String mensaje = "El ruc '"+ crearClienteRequest.getIdentificacionNumero() + "' no es valido";
                    log.info(mensaje);
                    return ResponseEntity.badRequest().body(mensaje);
                }
            }

            cliente.setNombre(crearClienteRequest.getNombre());
            cliente.setDireccion(crearClienteRequest.getDireccion());
            cliente.setTelefono(crearClienteRequest.getTelefono());

            clienteRepositorio.save(cliente);
            return ResponseEntity.ok(cliente);

        }catch (Exception ex){
            log.error("No se puede crear cliente: '{}'", crearClienteRequest.getIdentificacionNumero() + " - " + crearClienteRequest.getNombre());
            return ResponseEntity.badRequest().body("Ha ocurrido un error inesperado. Por favor, inténtelo nuevamente.");
        }
    }

    @PostMapping("/update")
    public ResponseEntity<?> actualizarCliente(@RequestBody EditarCliente editarClienteRequest){
        try{
            Optional<Cliente> clienteOptional = clienteRepositorio.findById(editarClienteRequest.getId());
            if(clienteOptional.isEmpty()){
                String mensaje = "El cliente con id " + editarClienteRequest.getId() +" no existe";
                log.info(mensaje);
                return ResponseEntity.badRequest().body(mensaje);
            }

            Cliente cliente = clienteOptional.get();
            //validar correo electronico
            if(validarCorreoElectronico(editarClienteRequest.getCorreoElectronico())) {
                cliente.setCorreoElectronico(editarClienteRequest.getCorreoElectronico());
            }else{
                String mensaje = "El correo electrónico '"+ editarClienteRequest.getCorreoElectronico() + "' no es valido";
                log.info(mensaje);
                return ResponseEntity.badRequest().body(mensaje);
            }

            //validar identificacion
            if(editarClienteRequest.getTipoIdentificacion().equals("CEDULA")){
                cliente.setTipoIdentificacion(TipoIdentificacion.CEDULA);
                if(validarCedula(editarClienteRequest.getIdentificacionNumero())) {
                    cliente.setIdentificacionNumero(editarClienteRequest.getIdentificacionNumero());
                }else {
                    String mensaje = "La cedula '"+ editarClienteRequest.getIdentificacionNumero() + "' no es valida";
                    log.info(mensaje);
                    return ResponseEntity.badRequest().body(mensaje);
                }
            }else if(editarClienteRequest.getTipoIdentificacion().equals("RUC")){
                cliente.setTipoIdentificacion(TipoIdentificacion.RUC);
                if(validarRuc(editarClienteRequest.getIdentificacionNumero())) {
                    cliente.setIdentificacionNumero(editarClienteRequest.getIdentificacionNumero());
                }else{
                    String mensaje = "El ruc '"+ editarClienteRequest.getIdentificacionNumero() + "' no es valido";
                    log.info(mensaje);
                    return ResponseEntity.badRequest().body(mensaje);
                }
            }

            cliente.setNombre(editarClienteRequest.getNombre());
            cliente.setDireccion(editarClienteRequest.getDireccion());
            cliente.setTelefono(editarClienteRequest.getTelefono());

            clienteRepositorio.save(cliente);
            return ResponseEntity.ok(cliente);

        }catch (Exception ex){
            log.error("No se puede actualizar el cliente: '{}'", editarClienteRequest.getIdentificacionNumero() + " - " + editarClienteRequest.getNombre());
            return ResponseEntity.badRequest().body("Ha ocurrido un error inesperado. Por favor, inténtelo nuevamente.");
        }
    }

    @GetMapping("/delete/{clienteId}")
    public ResponseEntity<?> eliminarCliente(@PathVariable("clienteId") Long clienteId){
        try{
            Optional<Cliente> clienteOptional = clienteRepositorio.findById(clienteId);
            if(clienteOptional.isEmpty()){
                String mensaje = "El cliente con id " + clienteId +" no existe";
                log.info(mensaje);
                return ResponseEntity.badRequest().body(mensaje);
            }

            clienteRepositorio.deleteById(clienteId);
            return ResponseEntity.ok().build();

        }catch (Exception ex){
            log.error("No se puede eliminar el cliente con id: '{}'", clienteId);
            return ResponseEntity.badRequest().body("Ha ocurrido un error inesperado. Por favor, inténtelo nuevamente.");
        }
    }

    @GetMapping("/findAll")
    public ResponseEntity<?> obtenerClientes(){
        try{
            return ResponseEntity.ok(clienteRepositorio.findAll());
        }catch (Exception ex){
            log.error("No se puede listar todos los clientes: '{}'", ex.getMessage());
            return ResponseEntity.badRequest().body("Ha ocurrido un error inesperado. Por favor, inténtelo nuevamente.");
        }
    }

    @GetMapping("/findByCedula/{identificacionNumero}")
    public ResponseEntity<?> obtenerClientePorIdentificacionNumero(@PathVariable("identificacionNumero") String identificacionNumero){
        try{
            Optional<Cliente> clienteOptional = clienteRepositorio.findClienteByIdentificacionNumero(identificacionNumero);
            if(clienteOptional.isEmpty()){
                String mensaje = "El cliente con identificación número " + identificacionNumero + " no existe";
                log.info(mensaje);
                return ResponseEntity.badRequest().body(mensaje);
            }

            return ResponseEntity.ok(clienteOptional.get());

        }catch (Exception ex){
            log.error("No se puede encontrar el cliente con identificación número : '{}'", identificacionNumero);
            return ResponseEntity.badRequest().body("Ha ocurrido un error inesperado. Por favor, inténtelo nuevamente.");
        }
    }

    private boolean validarRuc(String ruc){
        ValidacionIdentificacion val = new ValidacionIdentificacion();
        return (val.validarRucPersonaNatural(ruc) || val.validarRucSociedadPrivada(ruc) || val.validarRucSociedadPublica(ruc));
    }

    private boolean validarCedula(String cedula){
        ValidacionIdentificacion val = new ValidacionIdentificacion();
        return val.validarCedula(cedula);
    }

    private boolean validarCorreoElectronico(String correo){
        ValidacionCorreoElectronico val = new ValidacionCorreoElectronico();
        return val.validarCorreoElectronico(correo);
    }
}

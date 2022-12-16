package ec.edu.ups.moviles.Facturero.controladores;

import ec.edu.ups.moviles.Facturero.entidades.*;
import ec.edu.ups.moviles.Facturero.entidades.peticiones.factura.CrearFactura;
import ec.edu.ups.moviles.Facturero.entidades.peticiones.factura.CrearFacturaDetalle;
import ec.edu.ups.moviles.Facturero.entidades.tipos.EstadoFactura;
import ec.edu.ups.moviles.Facturero.repositorios.ClienteRepositorio;
import ec.edu.ups.moviles.Facturero.repositorios.FacturaCabeceraRepositorio;
import ec.edu.ups.moviles.Facturero.repositorios.ServicioRepositorio;
import ec.edu.ups.moviles.Facturero.repositorios.UsuarioRepositorio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/factura")
public class FacturaControlador {
    private static final Logger log = LoggerFactory.getLogger(FacturaControlador.class);

    @Autowired
    private FacturaCabeceraRepositorio facturaCabeceraRepositorio;
    @Autowired
    private ServicioRepositorio servicioRepositorio;
    @Autowired
    private ClienteRepositorio clienteRepositorio;
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @PostMapping("/create")
    public ResponseEntity<?> crearFactura(@RequestBody CrearFactura crearFacturaRequest){
        try{

            FacturaCabecera facturaCabecera = new FacturaCabecera();

            //verifico que el cliente sea correcto
            Optional<Cliente> clienteOptional = clienteRepositorio.findById(crearFacturaRequest.getClienteId());
            if (clienteOptional.isEmpty()) {
                String mensaje = "El cliente con id " + crearFacturaRequest.getClienteId() + " no existe";
                log.info(mensaje);
                return ResponseEntity.badRequest().body(mensaje);
            }

            //verifico que el usuario sea correcto
            Optional<Usuario> usuarioOptional = usuarioRepositorio.findById(crearFacturaRequest.getUsuarioId());
            if (usuarioOptional.isEmpty()) {
                String mensaje = "El usuario con id " + crearFacturaRequest.getUsuarioId() + " no existe";
                log.info(mensaje);
                return ResponseEntity.badRequest().body(mensaje);
            }

            //establezco los datos de la factura cabecera
            facturaCabecera.setFechaDeEmision(crearFacturaRequest.getFechaDeEmision());
            facturaCabecera.setSubtotal(crearFacturaRequest.getSubtotal());
            facturaCabecera.setImpuesto(crearFacturaRequest.getImpuesto());
            facturaCabecera.setTotal(crearFacturaRequest.getTotal());
            facturaCabecera.setCliente(clienteOptional.get());
            facturaCabecera.setUsuario(usuarioOptional.get());

            // cargo los datos del detalle
            List<FacturaDetalle> listaFacturaDetalles = new ArrayList<>();
            for(CrearFacturaDetalle crearFacturaDetalleRequest : crearFacturaRequest.getDetalles()){
                //verifico que el servicio sea correcto
                Optional<Servicio> servicioOptional = servicioRepositorio.findServicioByIdAndUsuarioId(crearFacturaRequest.getUsuarioId(), crearFacturaDetalleRequest.getServicioId());
                if (servicioOptional.isEmpty()) {
                    String mensaje = "El servicio del usuario con id " + crearFacturaDetalleRequest.getServicioId() +" no existe";
                    log.info(mensaje);
                    return ResponseEntity.badRequest().body(mensaje);
                }
                //establezco los datos del detalle
                FacturaDetalle facturaDetalle = new FacturaDetalle();
                facturaDetalle.setCantidad(crearFacturaDetalleRequest.getCantidad());
                facturaDetalle.setPrecioUnitario(crearFacturaDetalleRequest.getPrecioUnitario());
                facturaDetalle.setTotal(crearFacturaDetalleRequest.getTotal());
                facturaDetalle.setServicio(servicioOptional.get());
                facturaDetalle.setFacturaCabecera(facturaCabecera);
                listaFacturaDetalles.add(facturaDetalle);
            }
            facturaCabecera.setDetalles(listaFacturaDetalles);
            facturaCabecera.setEstadoFactura(EstadoFactura.EMITIDA);
            //guardar en el repositorio
            facturaCabeceraRepositorio.save(facturaCabecera);
            return ResponseEntity.ok(facturaCabecera);

        }catch (Exception ex){
            log.error("No se puede crear la factura. Error: {}", ex.getMessage());
            return ResponseEntity.badRequest().body("Ha ocurrido un error inesperado. Por favor, inténtelo nuevamente.");
        }
    }

    @GetMapping("/cancel/{facturaCabeceraId}")
    public ResponseEntity<?> anularFactura(@PathVariable("facturaCabeceraId") Long facturaCabeceraId){
        try{

            Optional<FacturaCabecera> facturaCabeceraOptional = facturaCabeceraRepositorio.findById(facturaCabeceraId);
            if (facturaCabeceraOptional.isEmpty()) {
                String mensaje = "La factura con id " + facturaCabeceraId + " no existe";
                log.info(mensaje);
                return ResponseEntity.badRequest().body(mensaje);
            }
            FacturaCabecera facturaCabecera = facturaCabeceraOptional.get();
            facturaCabecera.setEstadoFactura(EstadoFactura.ANULADA);
            //guardar en el repositorio
            facturaCabeceraRepositorio.save(facturaCabecera);
            return ResponseEntity.ok(facturaCabecera);

        }catch (Exception ex){
            log.error("No se puede anular la factura. Error: {}", ex.getMessage());
            return ResponseEntity.badRequest().body("Ha ocurrido un error inesperado. Por favor, inténtelo nuevamente.");
        }
    }

    @GetMapping("/findAll/{usuarioId}")
    public ResponseEntity<?> obtenerFacturasCabeceraPorUsuario(@PathVariable("usuarioId") Long usuarioId){
        try{
            return ResponseEntity.ok(facturaCabeceraRepositorio.findFacturasCabeceraByUsuarioId(usuarioId));
        }catch (Exception ex){
            log.error("No se puede listar todas las facturas del usuario '{}'. Error: {}", usuarioId, ex.getMessage());
            return ResponseEntity.badRequest().body("Ha ocurrido un error inesperado. Por favor, inténtelo nuevamente.");
        }
    }

    @GetMapping("/findAll/issued/{usuarioId}")
    public ResponseEntity<?> obtenerFacturasCabeceraEmitidasPorUsuario(@PathVariable("usuarioId") Long usuarioId){
        try{
            return ResponseEntity.ok(facturaCabeceraRepositorio.findFacturasCabeceraByUsuarioIdAndEstadoEmitido(usuarioId));
        }catch (Exception ex){
            log.error("No se puede listar todas las facturas EMITIDAS del usuario '{}'. Error: {}", usuarioId, ex.getMessage());
            return ResponseEntity.badRequest().body("Ha ocurrido un error inesperado. Por favor, inténtelo nuevamente.");
        }
    }

    @GetMapping("/findAll/cancel/{usuarioId}")
    public ResponseEntity<?> obtenerFacturasCabeceraAnuladasPorUsuario(@PathVariable("usuarioId") Long usuarioId){
        try{
            return ResponseEntity.ok(facturaCabeceraRepositorio.findFacturasCabeceraByUsuarioIdAndEstadoAnulado(usuarioId));
        }catch (Exception ex){
            log.error("No se puede listar todas las facturas ANULADAS del usuario '{}'. Error: {}", usuarioId, ex.getMessage());
            return ResponseEntity.badRequest().body("Ha ocurrido un error inesperado. Por favor, inténtelo nuevamente.");
        }
    }

}

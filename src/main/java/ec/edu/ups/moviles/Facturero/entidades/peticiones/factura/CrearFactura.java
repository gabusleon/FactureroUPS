package ec.edu.ups.moviles.Facturero.entidades.peticiones.factura;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class CrearFactura {

    @JsonProperty
    private LocalDate fechaDeEmision;
    @JsonProperty
    private BigDecimal subtotal;
    @JsonProperty
    private BigDecimal impuesto;
    @JsonProperty
    private BigDecimal total;
    @JsonProperty
    private long clienteId;
    @JsonProperty
    private long usuarioId;
    @JsonProperty
    private List<CrearFacturaDetalle> detalles;

    public LocalDate getFechaDeEmision() {
        return fechaDeEmision;
    }

    public void setFechaDeEmision(LocalDate fechaDeEmision) {
        this.fechaDeEmision = fechaDeEmision;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(BigDecimal impuesto) {
        this.impuesto = impuesto;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public long getClienteId() {
        return clienteId;
    }

    public void setClienteId(long clienteId) {
        this.clienteId = clienteId;
    }

    public long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public List<CrearFacturaDetalle> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<CrearFacturaDetalle> detalles) {
        this.detalles = detalles;
    }
}

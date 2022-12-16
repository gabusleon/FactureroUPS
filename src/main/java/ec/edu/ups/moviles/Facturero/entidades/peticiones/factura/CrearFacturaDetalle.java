package ec.edu.ups.moviles.Facturero.entidades.peticiones.factura;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.math.BigDecimal;

public class CrearFacturaDetalle implements Serializable {

    @JsonProperty
    private int cantidad;
    @JsonProperty
    private BigDecimal precioUnitario;
    @JsonProperty
    private BigDecimal total;
    @JsonProperty
    private long servicioId;

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public long getServicioId() {
        return servicioId;
    }

    public void setServicioId(long servicioId) {
        this.servicioId = servicioId;
    }
}

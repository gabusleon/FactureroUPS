package ec.edu.ups.moviles.Facturero.entidades.peticiones;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class CrearServicio {

    @JsonProperty
    private String descripcion;
    @JsonProperty
    private BigDecimal precioUnitario;
    @JsonProperty
    private long usuarioId;

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(long usuarioId) {
        this.usuarioId = usuarioId;
    }
}

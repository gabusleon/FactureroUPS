package ec.edu.ups.moviles.Facturero.entidades.peticiones.servicio;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class EditarServicio {

    @JsonProperty
    private long id;
    @JsonProperty
    private String descripcion;
    @JsonProperty
    private BigDecimal precioUnitario;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

}
